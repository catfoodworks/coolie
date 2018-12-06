package com.catfoodworks.coolie.generator.core.ftl;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.util.Map;

public interface DirectiveBodyProvider {

    void provide(String method, Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateModelException, IOException;

}
