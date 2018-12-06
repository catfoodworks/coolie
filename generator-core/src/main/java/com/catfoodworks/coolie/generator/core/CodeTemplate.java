package com.catfoodworks.coolie.generator.core;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface CodeTemplate {

    void setVariable(String name, Object val);

    Object getVariable(String name);

    void addVariables(Map<String, Object> variables);

    void print(File output) throws IOException, TemplateException;

}
