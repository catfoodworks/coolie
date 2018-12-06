package com.catfoodworks.coolie.generator.core.ftl.directive;

import com.catfoodworks.coolie.generator.core.CodeTemplateConfig;
import com.catfoodworks.coolie.generator.core.ftl.DirectiveBodyReader;
import freemarker.core.Environment;
import freemarker.ext.beans.MapModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DefinitionDirective extends AbstractDirective {

    protected static final String P_FOR = "for";
    protected static final String P_PARAM = "param";
    protected static final String P_VALUE = "value";

    public DefinitionDirective(CodeTemplateConfig config) {
        super(config);
    }

    @Override
    protected void doExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        boolean readed = false;
        String pFor = getParamAsString(params, P_FOR);
        String pPram = getParamAsString(params, P_PARAM);
        String pValue = getParamAsString(params, P_VALUE);
        if(StringUtils.isBlank(pValue) && body != null) {
            DirectiveBodyReader bodyReader = new DirectiveBodyReader();
            body.render(bodyReader);
            pValue = bodyReader.getString().trim();
            readed = true;
        }

        MapModel definition = (MapModel)env.getVariable("def");
        if(definition == null) {
            env.setVariable("def", beansWrapper.wrap(new HashMap<>()));
            definition = (MapModel)env.getVariable("def");
        }

        Map<String, Object> wrappedObject = (Map<String, Object>)definition.getWrappedObject();
        Map<String, Object> map = (Map<String, Object>)wrappedObject.get(pFor);
        if(map == null) {
            map = new HashMap<>();
            wrappedObject.put(pFor, map);
        } else {
            map.clear();
        }
        map.put(pPram, pValue);

        if(!readed && body != null) {
            body.render(env.getOut());
        }

    }
}
