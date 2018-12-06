package com.catfoodworks.coolie.generator.core.ftl.method;

import com.github.abel533.database.FullyQualifiedJavaType;
import com.github.abel533.utils.JavaBeansUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.util.List;

public class JavaBeanUtilMethodModel implements TemplateMethodModel {

    @Override
    public Object exec(List list) throws TemplateModelException {
        String method = (String)list.get(0);
        String value = (String)list.get(1);
        String type =  list.size() > 2 ? (String)list.get(2) : "";
        String upcase = list.size() > 3 ? (String)list.get(3) : "true";

        if("Getter".equalsIgnoreCase(method))
            return JavaBeansUtil.getGetterMethodName(value, new FullyQualifiedJavaType(type));
        else if("Setter".equalsIgnoreCase(method))
            return JavaBeansUtil.getSetterMethodName(value);
        else if("Camel".equalsIgnoreCase(method))
            return JavaBeansUtil.getCamelCaseString(value, Boolean.valueOf(upcase));
        else if("Property".equalsIgnoreCase(method))
            return JavaBeansUtil.getValidPropertyName(value);
        else
            return value;
    }
}
