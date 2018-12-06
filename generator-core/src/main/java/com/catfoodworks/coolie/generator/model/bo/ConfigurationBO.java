package com.catfoodworks.coolie.generator.model.bo;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationBO {

    private Map<String, String> typeMap;

    private Map<String, String> nameMap;

    public ConfigurationBO() {
        this.typeMap = new HashMap<>();
        this.nameMap = new HashMap<>();
    }

    public Map<String, String> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<String, String> typeMap) {
        this.typeMap = typeMap;
    }

    public Map<String, String> getNameMap() {
        return nameMap;
    }

    public void setNameMap(Map<String, String> nameMap) {
        this.nameMap = nameMap;
    }
}
