package com.catfoodworks.coolie.generator.core.ftl.directive;

import com.catfoodworks.coolie.generator.core.CodeTemplateConfig;
import com.github.abel533.database.IntrospectedColumn;
import com.github.abel533.database.IntrospectedTable;
import freemarker.core.Environment;
import freemarker.ext.beans.MapModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

public class PropertyDirective extends AbstractDirective {

    private static final String TYPE_FIELD = "field";

    public PropertyDirective(CodeTemplateConfig config) {
        super(config);
    }

    @Override
    protected void doExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String pType = getParamAsString(params, P_TYPE);
        boolean pDefault = getParamAsBoolean(params, P_DEFAULT);

        if(TYPE_FIELD.equalsIgnoreCase(pType) && !pDefault) {
            IntrospectedTable table = DirectiveUtil.getTable(env);
            for (IntrospectedColumn column : table.getAllColumns()) {
                initColumnVariable(env, column);
                if(body != null) {
                    body.render(env.getOut());
                    env.getOut().write("\n");
                }
            }

        } else if(TYPE_FIELD.equalsIgnoreCase(pType)) {
            IntrospectedTable table = DirectiveUtil.getTable(env);
            for (IntrospectedColumn column : table.getAllColumns()) {
                initColumnVariable(env, column);
                String propertyType = String.valueOf(env.getVariable(PROPERTY_TYPE));
                String propertyName = String.valueOf(env.getVariable(PROPERTY_NAME));
                env.getOut()
                        .append("    ")
                        .append(String.format("private %s %s;", propertyType, propertyName))
                        .append("\n\n")
                ;
            }
            env.getOut().flush();

        } else {
            MapModel def = (MapModel)env.getVariable("def");
            if(def != null) {
                Map<String, Object> wrappedObject = (Map<String, Object>)def.getWrappedObject();
                Map<String, Object> map = (Map<String, Object>)wrappedObject.get("property");

                if(map != null) {
                    String propertyAccess = (String)map.getOrDefault("access", "");
                    String propertyType = getParamAsString(params, "t");
                    String propertyName = getParamAsString(params, "n");

                    env.getOut()
                            .append("    ")
                            .append(String.format("%s %s %s;", propertyAccess, propertyType, propertyName))
                            .append("\n\n")
                            .flush();
                }
            }

            if(body != null) {
                body.render(env.getOut());
            }
            env.getOut().write("\n");
        }
    }
}
