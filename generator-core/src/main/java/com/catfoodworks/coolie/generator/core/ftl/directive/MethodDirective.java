package com.catfoodworks.coolie.generator.core.ftl.directive;

import com.catfoodworks.coolie.generator.core.CodeTemplateConfig;
import com.github.abel533.database.FullyQualifiedJavaType;
import com.github.abel533.database.IntrospectedColumn;
import com.github.abel533.database.IntrospectedTable;
import com.github.abel533.utils.JavaBeansUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MethodDirective extends AbstractDirective {

    private static final String TYPE_GETTER = "getter";
    private static final String TYPE_SETTER = "setter";
    private static final String TYPE_GETTER_SETTER = "getter&setter";
    private static final String TYPE_CONSTRUCT = "construct";
    private static final String TYPE_GENERAL = "general";

    public MethodDirective(CodeTemplateConfig config) {
        super(config);
    }

    @Override
    protected void doExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        String pType = getParamAsString(params, P_TYPE, TYPE_GENERAL);
        boolean pDefault = getParamAsBoolean(params, P_DEFAULT);

        IntrospectedTable table = DirectiveUtil.getTable(env);
        List<IntrospectedColumn> allColumns = table.getAllColumns();

        if(TYPE_GENERAL.equalsIgnoreCase(pType)) {

            if(body != null) {
                body.render(env.getOut());
            }

        } else if(TYPE_CONSTRUCT.equalsIgnoreCase(pType)) {

            env.getOut()
                    .append("    ")
                    .append(String.format("public %s(){", "" + env.getVariable(CLASS_NAME_PREFIX) + env.getVariable(CLASS_NAME) + env.getVariable(CLASS_NAME_SUFFIX)))
                    .append("\n")
                    .flush();

            if(body != null) {
                body.render(env.getOut());
            }

            env.getOut()
                    .append("    ")
                    .append("}")
                    .append("\n\n")
                    .flush();

        } else {
            for (IntrospectedColumn column : allColumns) {
                initColumnVariable(env, column);
                String propertyType = String.valueOf(env.getVariable(PROPERTY_TYPE));
                String propertyName = String.valueOf(env.getVariable(PROPERTY_NAME));

                if(TYPE_GETTER.equalsIgnoreCase(pType) && pDefault) {
                    rendeGetter(env, propertyType, propertyName);

                } else if(TYPE_SETTER.equalsIgnoreCase(pType) && pDefault) {
                    rendeSetter(env, propertyType, propertyName);

                } else if(TYPE_GETTER_SETTER.equalsIgnoreCase(pType) && pDefault) {
                    rendeGetter(env, propertyType, propertyName);
                    rendeSetter(env, propertyType, propertyName);

                } else if(body != null) {
                    body.render(env.getOut());
                }
            }
        }
    }

    private void rendeGetter(Environment env, String propertyType, String propertyName) throws IOException {
        String methodName = JavaBeansUtil.getGetterMethodName(propertyName, new FullyQualifiedJavaType(propertyType));
        env.getOut()
                .append("    ")
                .append(String.format("public %s %s(){", propertyType, methodName))
                .append("\n        ")
                .append(String.format("return this.%s;", propertyName))
                .append("\n    ")
                .append("}")
                .append("\n\n");
    }

    private void rendeSetter(Environment env, String propertyType, String propertyName) throws IOException {
        String methodName = JavaBeansUtil.getSetterMethodName(propertyName);
        env.getOut()
                .append("    ")
                .append(String.format("public void %s(%s %s){", methodName, propertyType, propertyName))
                .append("\n        ")
                .append(String.format("this.%1$s = %1$s;", propertyName))
                .append("\n    ")
                .append("}")
                .append("\n\n");
    }


}
