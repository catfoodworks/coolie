package com.catfoodworks.coolie.generator.core.ftl.directive;

import com.catfoodworks.coolie.generator.core.CodeTemplateConfig;
import com.github.abel533.database.FullyQualifiedJavaType;
import com.github.abel533.database.IntrospectedColumn;
import com.github.abel533.database.IntrospectedTable;
import freemarker.core.Environment;
import freemarker.ext.beans.CollectionModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ImportDirective extends AbstractDirective {

    private static final String P_FULL_TYPE_NAME = "fullTypeName";
    private static final String P_AUTO = "auto";

    private static final String AUTO_COLUMNS = "columns";

    public ImportDirective(CodeTemplateConfig config) {
        super(config);
    }

    @Override
    protected void doExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        IntrospectedTable table = DirectiveUtil.getTable(env);
        String fullTypeName = getParamAsString(params, P_FULL_TYPE_NAME);
        String auto = getParamAsString(params, P_AUTO);
        Set<String> allImports = new HashSet<>();

        if(AUTO_COLUMNS.equalsIgnoreCase(auto)) {
            allImports = table.getAllColumns()
                    .stream()
                    .map(IntrospectedColumn::getFullyQualifiedJavaType)
                    .map(FullyQualifiedJavaType::getImportList)
                    .flatMap(t -> t.stream())
                    .sorted()
                    .collect(Collectors.toSet());
        }

        if(StringUtils.isNotBlank(fullTypeName))
            allImports.add(fullTypeName);

        BodyReader bodyReader = new BodyReader();
        if(body != null) {
            body.render(bodyReader);
            for (String st : bodyReader.getString().split("\n")) {
                allImports.add(StringUtils.trim(st));
            }
        }

        CollectionModel collectionModel = (CollectionModel) env.getVariable(IMPORT_LIST);
        if(collectionModel != null) {
            TemplateModelIterator imports = collectionModel.iterator();
            while (imports.hasNext()) {
                allImports.add(imports.next().toString());
            }
        }

        env.setVariable(IMPORT_LIST, beansWrapper.wrap(allImports.stream().sorted().collect(Collectors.toList())));
    }

    private static class BodyReader extends Writer {

        private StringBuilder stringBuilder = new StringBuilder();

        public String getString() {
            return stringBuilder.toString();
        }

        public void write(char[] cbuf, int off, int len)  throws IOException {
            char[] readBuf = new char[len];
            for (int i = 0; i < len; i++) {
                readBuf[i] = cbuf[i + off];
            }
            stringBuilder.append(readBuf);
        }

        public void flush() throws IOException {

        }

        public void close() throws IOException {

        }
    }
}
