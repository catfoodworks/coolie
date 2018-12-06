package com.catfoodworks.coolie.generator.core.ftl.directive;

import com.catfoodworks.coolie.generator.core.CodeTemplateConfig;
import com.catfoodworks.coolie.generator.core.ftl.DirectiveBodyProvider;
import freemarker.core.Environment;
import freemarker.template.DefaultMapAdapter;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

public class ProviderDirective extends AbstractDirective {

    protected static final String P_PROVIDER = "provider";
    protected static final String P_METHOD = "method";

    public ProviderDirective(CodeTemplateConfig config) {
        super(config);
    }

    @Override
    protected void doExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        String provider = getParamAsString(params, P_PROVIDER);
        String method = getParamAsString(params, P_METHOD);

        DefaultMapAdapter providerMap = (DefaultMapAdapter)env.getVariable("directiveBodyProvider");
        if(providerMap == null)
            return;

        Map<String, DirectiveBodyProvider> wrappedObject = (Map<String, DirectiveBodyProvider>)providerMap.getWrappedObject();
        DirectiveBodyProvider bodyProvider = wrappedObject.get(provider);
        if(bodyProvider == null)
            return;

        bodyProvider.provide(method, env, params, loopVars, body);

        if(body != null)
            body.render(env.getOut());

    }
}
