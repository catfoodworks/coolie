package com.catfoodworks.coolie.generator.core.ftl;

import com.catfoodworks.coolie.generator.core.ftl.directive.DirectiveUtil;
import com.github.abel533.database.IntrospectedColumn;
import com.github.abel533.database.IntrospectedTable;
import com.github.abel533.utils.JavaBeansUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ComponentMethodProvider implements DirectiveBodyProvider {

    private static final String METHOD_CREATE = "create";
    private static final String METHOD_UPDATE = "update";
    private static final String METHOD_QUERY_ACTIVE = "queryActive";

    @Override
    public void provide(String method, Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateModelException, IOException {

        if(METHOD_CREATE.equalsIgnoreCase(method)) {
            provideCreateMethod(env, params, loopVars, body);
        } else if(METHOD_UPDATE.equalsIgnoreCase(method)) {
            provideUpdateMethod(env, params, loopVars, body);
        } else if(METHOD_QUERY_ACTIVE.equalsIgnoreCase(method)) {
            provideQueryActiveMethod(env, params, loopVars, body);
        }

    }

    public void provideCreateMethod(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateModelException, IOException {
        IntrospectedTable table = DirectiveUtil.getTable(env);
        List<IntrospectedColumn> allColumns = table.getAllColumns();
        Optional<IntrospectedColumn> optional = allColumns
                .stream()
                .filter(t -> t.getName().equalsIgnoreCase(String.format("%s_id", table.getName())))
                .findFirst();

        if(optional.isPresent()) {
            IntrospectedColumn column = optional.get();
            env.getOut()
                    .append("        ")
                    .append(String.format("if(%s.%s() == null)"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)
                            , JavaBeansUtil.getGetterMethodName(column.getJavaProperty(), column.getFullyQualifiedJavaType())))
                    .append("\n            ")
                    .append(String.format("%s.%s(IdUtil.create());"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)
                            , JavaBeansUtil.getSetterMethodName(column.getJavaProperty())))
            ;
        }

        optional = allColumns
                .stream()
                .filter(t -> t.getName().equalsIgnoreCase("create_time"))
                .findFirst();

        if(optional.isPresent()) {
            IntrospectedColumn column = optional.get();
            env.getOut()
                    .append("\n        ")
                    .append("Date currentDatetime = new Date();")
                    .append("\n        ")
                    .append(String.format("if(%s.%s() == null)"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)
                            , JavaBeansUtil.getGetterMethodName(column.getJavaProperty(), column.getFullyQualifiedJavaType())))
                    .append("\n            ")
                    .append(String.format("%s.%s(currentDatetime);"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)
                            , JavaBeansUtil.getSetterMethodName(column.getJavaProperty())))
            ;
        }

        optional = allColumns
                .stream()
                .filter(t -> t.getName().equalsIgnoreCase("update_time"))
                .findFirst();

        if(optional.isPresent()) {
            IntrospectedColumn column = optional.get();
            env.getOut()
                    .append("\n        ")
                    .append(String.format("if(%s.%s() == null)"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)
                            , JavaBeansUtil.getGetterMethodName(column.getJavaProperty(), column.getFullyQualifiedJavaType())))
                    .append("\n            ")
                    .append(String.format("%s.%s(currentDatetime);"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)
                            , JavaBeansUtil.getSetterMethodName(column.getJavaProperty())))
            ;
        }

        optional = allColumns
                .stream()
                .filter(t -> t.getName().equalsIgnoreCase("active"))
                .findFirst();

        if(optional.isPresent()) {
            IntrospectedColumn column = optional.get();
            env.getOut()
                    .append("\n        ")
                    .append(String.format("if(%s.%s() == null)"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)
                            , JavaBeansUtil.getGetterMethodName(column.getJavaProperty(), column.getFullyQualifiedJavaType())))
                    .append("\n            ")
                    .append(String.format("%s.%s(1);"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)
                            , JavaBeansUtil.getSetterMethodName(column.getJavaProperty())))
            ;
        }

        env.getOut().append("\n").flush();
    }

    public void provideUpdateMethod(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateModelException, IOException {
        IntrospectedTable table = DirectiveUtil.getTable(env);
        List<IntrospectedColumn> allColumns = table.getAllColumns();


        Optional<IntrospectedColumn> optional = allColumns
                .stream()
                .filter(t -> t.getName().equalsIgnoreCase("update_time"))
                .findFirst();

        if(optional.isPresent()) {
            IntrospectedColumn column = optional.get();
            env.getOut()
                    .append("        ")
                    .append("Date currentDatetime = new Date();")
                    .append("\n        ")
                    .append(String.format("if(%s.%s() == null)"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)
                            , JavaBeansUtil.getGetterMethodName(column.getJavaProperty(), column.getFullyQualifiedJavaType())))
                    .append("\n            ")
                    .append(String.format("%s.%s(currentDatetime);"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)
                            , JavaBeansUtil.getSetterMethodName(column.getJavaProperty())))
            ;
        }

        env.getOut().append("\n").flush();
    }

    public void provideQueryActiveMethod(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateModelException, IOException {
        IntrospectedTable table = DirectiveUtil.getTable(env);
        List<IntrospectedColumn> allColumns = table.getAllColumns();


        Optional<IntrospectedColumn> optional = allColumns
                .stream()
                .filter(t -> t.getName().equalsIgnoreCase("active"))
                .findFirst();

        if(optional.isPresent()) {
            IntrospectedColumn column = optional.get();
            env.getOut()
                    .append("        ")
                    .append(String.format("for (%sExample.Criteria criteria : %sExample.getOredCriteria()) {"
                            , JavaBeansUtil.getCamelCaseString(table.getName(), true)
                            , JavaBeansUtil.getCamelCaseString(table.getName(), false)))
                    .append("\n            ")
                    .append("criteria.andActiveEqualTo(1);")
                    .append("\n")
                    .append("        ")
                    .append("}")
            ;
        }

        env.getOut().append("\n").flush();
    }
}
