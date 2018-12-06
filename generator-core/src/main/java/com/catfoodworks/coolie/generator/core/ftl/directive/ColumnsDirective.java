package com.catfoodworks.coolie.generator.core.ftl.directive;

import com.catfoodworks.coolie.generator.core.CodeTemplateConfig;
import com.github.abel533.database.IntrospectedColumn;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ColumnsDirective extends AbstractDirective {

    private static final String P_DELIMITER = "delimiter";
    private static final String P_NEWLINE = "newline";

    public ColumnsDirective(CodeTemplateConfig config) {
        super(config);
    }

    @Override
    protected void doExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        String delimiter = getParamAsString(params, P_DELIMITER);
        int newline = getParamAsInt(params, P_NEWLINE);

        int count = 0;
        List<IntrospectedColumn> allColumns = DirectiveUtil.getTable(env).getAllColumns();
        for (IntrospectedColumn column : allColumns) {
            count++;
            initColumnVariable(env, column);
            if(body != null) {
                body.render(env.getOut());
            }
            if(count < allColumns.size() && StringUtils.isNotBlank(delimiter))
                env.getOut().write(delimiter);

            if(newline != 0 && count % newline == 0) {
                env.getOut().write("\n        ");
            }
        }

    }
}
