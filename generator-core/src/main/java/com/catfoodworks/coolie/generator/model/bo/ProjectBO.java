package com.catfoodworks.coolie.generator.model.bo;

import java.util.List;

public class ProjectBO {

    private String name;

    private String location;

    private List<ConnectionBO> connections;

    private List<ComponentBO> components;

    private List<ModuleBO> modules;

    private ConfigurationBO configuration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<ConnectionBO> getConnections() {
        return connections;
    }

    public void setConnections(List<ConnectionBO> connections) {
        this.connections = connections;
    }

    public List<ComponentBO> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentBO> components) {
        this.components = components;
    }

    public List<ModuleBO> getModules() {
        return modules;
    }

    public void setModules(List<ModuleBO> modules) {
        this.modules = modules;
    }

    public ConfigurationBO getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ConfigurationBO configuration) {
        this.configuration = configuration;
    }
}
