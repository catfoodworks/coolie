package com.catfoodworks.coolie.generator.model.bo;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ComponentBO {

    private String name;

    private String path;

    private String namespace;

    private String template;

    private String subFix;

    private Map<String, Object> variables;

    private boolean enable;

    public ComponentBO() {
        this.name = "";
        this.path = "";
        this.namespace = "";
        this.template = "";
        this.subFix = "";
        this.variables = Collections.emptyMap();
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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSubFix() {
        return subFix;
    }

    public void setSubFix(String subFix) {
        this.subFix = subFix;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
