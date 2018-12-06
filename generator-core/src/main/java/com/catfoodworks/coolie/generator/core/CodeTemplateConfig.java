package com.catfoodworks.coolie.generator.core;

import java.util.HashMap;
import java.util.Map;

public class CodeTemplateConfig {

    private Map<String, String> columnNamePropertyMap;
    private Map<String, String> columnTypePropertyMap;

    public CodeTemplateConfig() {
        this.columnNamePropertyMap = new HashMap<>();
        this.columnTypePropertyMap = new HashMap<>();
    }

    public void mapColumnNameProperty(String columnName, String property) {
        this.columnNamePropertyMap.put(columnName, property);
    }

    public void mapColumnTypeProperty(String columnType, String property) {
        this.columnTypePropertyMap.put(columnType, property);
    }

    public String getPropertyByColumnName(String columnName, String defaultVal) {
        return this.columnNamePropertyMap.getOrDefault(columnName, defaultVal);
    }

    public String getPropertyByColumnType(String columnName, String defaultVal) {
        return this.columnTypePropertyMap.getOrDefault(columnName, defaultVal);
    }
}
