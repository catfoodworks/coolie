package com.catfoodworks.coolie.generator.core.ftl.directive;

import com.catfoodworks.coolie.generator.core.CodeTemplateConfig;
import com.github.abel533.database.IntrospectedColumn;
import com.github.abel533.utils.JavaBeansUtil;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.ext.beans.BooleanModel;
import freemarker.ext.beans.StringModel;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractDirective implements TemplateDirectiveModel {

    protected static final String VAR_PACKAGE = "package";
    protected static final String VAR_TABLE = "table";

    protected static final String P_TYPE = "type";
    protected static final String P_DEFAULT = "default";

    protected static final String TABLE_NAME = "tableName";
    protected static final String COLUMN_NAME = "columnName";
    protected static final String COLUMN_TYPE = "columnType";
    protected static final String COLUMN_IS_PK = "columnIsPk";

    protected static final String CLASS_NAME = "className";
    protected static final String CLASS_NAME_FCUC = "classNameFcUc";
    protected static final String CLASS_NAME_PREFIX = "classNamePrefix";
    protected static final String CLASS_NAME_SUFFIX = "classNameSuffix";
    protected static final String CLASS_NAME_FCLC = "classNameFcLc";
    protected static final String PROPERTY_NAME = "propertyName";
    protected static final String PROPERTY_NAME_FCUC = "propertyNameFcUc";
    protected static final String PROPERTY_NAME_FULC = "propertyNameFcLc";
    protected static final String PROPERTY_TYPE = "propertyType";
    protected static final String PROPERTY_FULL_TYPE = "propertyFullType";
    protected static final String PROPERTY_GETTER = "getter";
    protected static final String PROPERTY_SETTER = "setter";

    protected static final String IMPORT_LIST = "imports";
    protected static final String CUSTOM_IMPORTS = "custom_imports";

    protected static final BeansWrapper beansWrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_22).build();

    protected final CodeTemplateConfig config;

    public AbstractDirective(CodeTemplateConfig config) {
        this.config = config;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        this.doExecute(env, params, loopVars, body);
    }

    protected abstract void doExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException;

    protected void initColumnVariable(Environment env, IntrospectedColumn column) {
        env.setVariable(COLUMN_NAME, new StringModel(column.getName(), beansWrapper));
        env.setVariable(COLUMN_TYPE, new StringModel(column.getJdbcTypeName(), beansWrapper));
        env.setVariable(COLUMN_IS_PK, new BooleanModel(column.isPk(), beansWrapper));
        env.setVariable(PROPERTY_NAME, new StringModel(column.getJavaProperty(), beansWrapper));
        env.setVariable(PROPERTY_NAME_FCUC, new StringModel(JavaBeansUtil.getCamelCaseString(column.getName(), true), beansWrapper));
        env.setVariable(PROPERTY_NAME_FULC, new StringModel(column.getJavaProperty(), beansWrapper));
        env.setVariable(PROPERTY_TYPE, new StringModel(DirectiveUtil.mapJdbcTypeToJavaType(config, column).getShortName(), beansWrapper));
        env.setVariable(PROPERTY_FULL_TYPE, new StringModel(DirectiveUtil.mapJdbcTypeToJavaType(config, column).getFullyQualifiedName(), beansWrapper));
        env.setVariable(PROPERTY_GETTER, new StringModel(JavaBeansUtil.getGetterMethodName(column.getJavaProperty(), DirectiveUtil.mapJdbcTypeToJavaType(config, column)), beansWrapper));
        env.setVariable(PROPERTY_SETTER, new StringModel(JavaBeansUtil.getSetterMethodName(column.getJavaProperty()), beansWrapper));
    }

    protected Number getParamAsNumber(Map param, String name) {
        SimpleNumber number = (SimpleNumber) param.get(name);
        if(number == null)
            return 0;
        else
            return number.getAsNumber();
    }

    protected int getParamAsInt(Map param, String name) {
        return getParamAsNumber(param, name).intValue();
    }

    protected long getParamAsLong(Map param, String name) {
        return getParamAsNumber(param, name).longValue();
    }

    protected float getParamAsFloat(Map param, String name) {
        return getParamAsNumber(param, name).floatValue();
    }

    protected double getParamAsDouble(Map param, String name) {
        return getParamAsNumber(param, name).doubleValue();
    }

    protected String getParamAsString(Map param, String name) {
        return getParamAsString(param, name, "");
    }

    protected String getParamAsString(Map param, String name, String defaultStr) {
        SimpleScalar scalar = (SimpleScalar) param.get(name);
        if(scalar == null)
            return defaultStr;
        else
            return scalar.getAsString();
    }

    protected boolean getParamAsBoolean(Map param, String name) throws TemplateModelException {

        return getParamAsBoolean(param, name, false);

    }

    protected boolean getParamAsBoolean(Map param, String name, boolean defaultVal) throws TemplateModelException {
        TemplateBooleanModel model = (TemplateBooleanModel) param.get(name);
        if(model == null)
            return defaultVal;
        else
            return model.getAsBoolean();
    }

}
