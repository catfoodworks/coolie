package com.catfoodworks.coolie.generator.core.ftl.directive;

import com.catfoodworks.coolie.generator.core.CodeTemplateConfig;
import com.github.abel533.database.IntrospectedTable;
import com.github.abel533.utils.JavaBeansUtil;
import freemarker.core.Environment;
import freemarker.ext.beans.CollectionModel;
import freemarker.ext.beans.MapModel;
import freemarker.ext.beans.StringModel;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClassDirective extends AbstractDirective {

    private static final String P_TYPE = "type";
    private static final String P_NAME = "name";
    private static final String P_PREFIX = "prefix";
    private static final String P_SUFFIX = "suffix";
    private static final String P_ANNOTATION = "annotation";
    private static final String P_BASE = "base";
    private static final String P_INTERFACE = "interface";

    private static final String TYPE_CLASS = "class";
    private static final String TYPE_INTERFACE = "interface";
    private static final String TYPE_ENUM = "enum";

    public ClassDirective(CodeTemplateConfig config) {
        super(config);
    }

    @Override
    protected void doExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        IntrospectedTable table = DirectiveUtil.getTable(env);
        String type = getParamAsString(params, P_TYPE);
        String prefix = getParamAsString(params, P_PREFIX);
        String suffix = getParamAsString(params, P_SUFFIX);
        String annotation = getParamAsString(params, P_ANNOTATION);
        String baseClassName = getParamAsString(params, P_BASE);
        String interfaceName = getParamAsString(params, P_INTERFACE);

        if(StringUtils.isBlank(type))
            type = TYPE_CLASS;

        MapModel def = (MapModel)env.getVariable("def");
        if(StringUtils.isBlank(annotation) && def != null) {
            Map<String, Object> wrappedObject = (Map<String, Object>)def.getWrappedObject();
            Map<String, Object> map = (Map<String, Object>)wrappedObject.get("class");

            if(map != null) {
                annotation = (String)map.getOrDefault(P_ANNOTATION, "");
            }
        }

        env.setVariable(CLASS_NAME_PREFIX, new StringModel(prefix, beansWrapper));
        env.setVariable(CLASS_NAME_SUFFIX, new StringModel(suffix, beansWrapper));

        env.getOut().write(String.format("package %s;\n", env.getVariable(VAR_PACKAGE)));
        env.getOut().write("\n");

        DefaultListAdapter customImports = (DefaultListAdapter)env.getVariable(CUSTOM_IMPORTS);
        List<String> javaPackages = new ArrayList<>();
        if(customImports != null) {
            for(int i = 0; i < customImports.size(); i++) {
                TemplateModel model = customImports.get(i);
                javaPackages.add(model.toString());
            }
        }

        CollectionModel collectionModel = (CollectionModel) env.getVariable(IMPORT_LIST);
        if(collectionModel != null) {
            TemplateModelIterator imports = collectionModel.iterator();
            while (imports.hasNext()) {
                String imp = imports.next().toString();
                if(imp.indexOf("java") != 0)
                    env.getOut().append(String.format("import %s;\n", imp));
                else
                    javaPackages.add(imp);
            }
            env.getOut().flush();
            env.getOut().write("\n");
        }

        for (String imp : javaPackages) {
            env.getOut().append(String.format("import %s;\n", imp));
        }
        env.getOut().flush();
        env.getOut().write("\n");

        if(StringUtils.isNotBlank(annotation)) {
            env.getOut().write(String.format("@%s\n", annotation));
        }

        String classType = type;
        if(TYPE_CLASS.equals(type))
            classType = "class";
        else if(TYPE_INTERFACE.equals(type))
            classType = "interface";
        else if(TYPE_ENUM.equals(type))
            classType = "enum";

        String className = prefix + JavaBeansUtil.getCamelCaseString(table.getName(), true) + suffix;

        if(StringUtils.isNotBlank(baseClassName))
            className = String.format("%s extends %s", className, baseClassName);

        if(StringUtils.isNotBlank(interfaceName))
            className = String.format("%s implements %s", className, interfaceName);


        env.getOut().write(String.format("public %s %s {\n\n", classType, className));

        if(body != null)
            body.render(env.getOut());

        env.getOut().write("}");
    }
}
