package com.catfoodworks.coolie.generator.model.bo;

import java.util.ArrayList;
import java.util.List;

public class ModuleBO {

    private String name;

    private String path;

    private String namespace;

    private String table;

    private List<ComponentBO> components;

    private ConfigurationBO configuration;

    boolean enable;

    public ModuleBO() {
        this.name = "";
        this.path = "";
        this.namespace = "";
        this.table = "";
        this.components = new ArrayList<>();
        this.configuration = new ConfigurationBO();
        this.enable = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<ComponentBO> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentBO> components) {
        this.components = components;
    }

    public ConfigurationBO getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ConfigurationBO configuration) {
        this.configuration = configuration;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
