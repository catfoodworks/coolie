package com.catfoodworks.coolie.generator.core;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerTemplate implements CodeTemplate {

    private String template;

    private Configuration templateEngine;

    private Map<String, Object> variables;

    public FreemarkerTemplate(String template, Configuration templateEngine) {
        this.template = template;
        this.templateEngine = templateEngine;
        this.variables = new HashMap<>();
    }

    public void setVariable(String name, Object val) {
        this.variables.put(name, val);
    }

    public Object getVariable(String name) {
        return this.variables.get(name);
    }

    public void addVariables(Map<String, Object> variables) {
        this.variables.putAll(variables);
    }

    public void print(File output) throws IOException, TemplateException {
        Template modelTpl = templateEngine.getTemplate(template);
        modelTpl.process(this.variables, new OutputStreamWriter(new FileOutputStream(output)));
    }
}
