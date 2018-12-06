package com.catfoodworks.coolie.generator.core;

import com.catfoodworks.coolie.generator.core.ftl.ComponentMethodProvider;
import com.catfoodworks.coolie.generator.core.ftl.DirectiveBodyProvider;
import com.catfoodworks.coolie.generator.core.ftl.directive.*;
import com.catfoodworks.coolie.generator.core.ftl.method.JavaBeanUtilMethodModel;
import com.catfoodworks.coolie.generator.exception.CodeTemplateException;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerTemplateFactory implements CodeTemplateFactory {

    private Logger logger = LoggerFactory.getLogger(FreemarkerTemplateFactory.class);

    private CodeTemplateConfig config;

    private Configuration templateEngine;

    public FreemarkerTemplateFactory() {

    }

    public FreemarkerTemplateFactory(File templateDir) throws CodeTemplateException {
        this.init(templateDir);
    }

    public void init(File templateDir) throws CodeTemplateException {
        try {
            this.config = new CodeTemplateConfig();

            HashMap<String, DirectiveBodyProvider> providerHashMap = new HashMap<>();
            providerHashMap.put("componentMethodProvider", new ComponentMethodProvider());

            this.templateEngine = new Configuration(Configuration.VERSION_2_3_22);
            templateEngine.setDirectoryForTemplateLoading(templateDir);
            templateEngine.setDefaultEncoding("UTF-8");
            templateEngine.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            templateEngine.setSharedVariable("context", new ContextDirective(config));
            templateEngine.setSharedVariable("columns", new ColumnsDirective(config));
            templateEngine.setSharedVariable("definition", new DefinitionDirective(config));
            templateEngine.setSharedVariable("import", new ImportDirective(config));
            templateEngine.setSharedVariable("class", new ClassDirective(config));
            templateEngine.setSharedVariable("property", new PropertyDirective(config));
            templateEngine.setSharedVariable("method", new MethodDirective(config));
            templateEngine.setSharedVariable("provider", new ProviderDirective(config));
            templateEngine.setSharedVariable("JavaBeanUtil", new JavaBeanUtilMethodModel());
            templateEngine.setSharedVariable("directiveBodyProvider", providerHashMap);

        } catch (TemplateModelException e) {
            logger.error("FreemarkerTemplateFactory init fault", e);
            throw new CodeTemplateException(e);

        } catch (IOException e) {
            logger.error("FreemarkerTemplateFactory init fault", e);
            throw new CodeTemplateException(e);
        }
    }

    @Override
    public void initTypeMap(Map<String, String> typeMap) {
        for (Map.Entry<String, String> entry : typeMap.entrySet()) {
            this.config.mapColumnTypeProperty(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void initNameMap(Map<String, String> nameMap) {
        for (Map.Entry<String, String> entry : nameMap.entrySet()) {
            this.config.mapColumnNameProperty(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public CodeTemplate getCodeTemplate(String template) {
        return new FreemarkerTemplate(template, this.templateEngine);
    }
}
