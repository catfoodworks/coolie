package com.catfoodworks.coolie.generator.core.ftl.directive;

import com.catfoodworks.coolie.generator.core.CodeTemplateConfig;
import com.github.abel533.database.FullyQualifiedJavaType;
import com.github.abel533.database.IntrospectedColumn;
import com.github.abel533.database.IntrospectedTable;
import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateModelException;

public class DirectiveUtil {

    public static FullyQualifiedJavaType mapJdbcTypeToJavaType(CodeTemplateConfig config, IntrospectedColumn column) {

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(config.getPropertyByColumnType(column.getJdbcTypeName()
                , column.getFullyQualifiedJavaType().getFullyQualifiedName()));

        type = new FullyQualifiedJavaType(config.getPropertyByColumnName(column.getName()
                , type.getFullyQualifiedName()));

        return type;
    }

    public static IntrospectedTable getTable(Environment environment) throws TemplateModelException {
        return (IntrospectedTable)((StringModel)environment.getVariable("table")).getWrappedObject();
    }

}
