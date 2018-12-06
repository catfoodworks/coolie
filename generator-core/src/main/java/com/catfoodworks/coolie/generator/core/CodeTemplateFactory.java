package com.catfoodworks.coolie.generator.core;

import com.catfoodworks.coolie.generator.exception.CodeTemplateException;

import java.io.File;
import java.util.Map;

public interface CodeTemplateFactory {

    void initTypeMap(Map<String, String> typeMap);

    void initNameMap(Map<String, String> nameMap);

    void init(File templateDir) throws CodeTemplateException;

    CodeTemplate getCodeTemplate(String template);

}
