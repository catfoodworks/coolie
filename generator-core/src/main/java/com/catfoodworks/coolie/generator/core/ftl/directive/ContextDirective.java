package com.catfoodworks.coolie.generator.core.ftl.directive;

import com.catfoodworks.coolie.generator.core.CodeTemplateConfig;
import com.github.abel533.database.IntrospectedTable;
import com.github.abel533.utils.JavaBeansUtil;
import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

public class ContextDirective extends AbstractDirective {

    protected static final String TYPE_JAVA = "java";
    protected static final String TYPE_MAPPER_XML = "mapper_xml";
    protected static final String TYPE_HTML = "html";

    public ContextDirective(CodeTemplateConfig config) {
        super(config);
    }

    @Override
    protected void doExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        IntrospectedTable table = DirectiveUtil.getTable(env);
        env.setVariable(TABLE_NAME, new StringModel(table.getName(), beansWrapper));
        env.setVariable(CLASS_NAME, new StringModel(JavaBeansUtil.getCamelCaseString(table.getName(), true), beansWrapper));
        env.setVariable(CLASS_NAME_FCUC, new StringModel(JavaBeansUtil.getCamelCaseString(table.getName(), true), beansWrapper));
        env.setVariable(CLASS_NAME_FCLC, new StringModel(JavaBeansUtil.getCamelCaseString(table.getName(), false), beansWrapper));

        String type = getParamAsString(params, P_TYPE);
        if(StringUtils.isBlank(type))
            type = TYPE_JAVA;

        if(TYPE_JAVA.equalsIgnoreCase(type)) {

        } else if(TYPE_MAPPER_XML.equalsIgnoreCase(type)) {

        } else if(TYPE_HTML.equalsIgnoreCase(type)) {

        }

        if(body != null)
            body.render(env.getOut());
    }
}
