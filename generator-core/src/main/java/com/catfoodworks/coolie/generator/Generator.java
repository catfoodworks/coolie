package com.catfoodworks.coolie.generator;

import com.catfoodworks.coolie.generator.core.CodeTemplate;
import com.catfoodworks.coolie.generator.core.CodeTemplateFactory;
import com.catfoodworks.coolie.generator.exception.GenerateException;
import com.catfoodworks.coolie.generator.model.bo.ComponentBO;
import com.catfoodworks.coolie.generator.model.bo.ConnectionBO;
import com.catfoodworks.coolie.generator.model.bo.ModuleBO;
import com.catfoodworks.coolie.generator.model.bo.ProjectBO;
import com.catfoodworks.coolie.generator.util.BeanUtil;
import com.catfoodworks.coolie.generator.util.DbLoader;
import com.catfoodworks.coolie.generator.util.ProjectLoader;
import com.github.abel533.database.DatabaseConfig;
import com.github.abel533.database.Dialect;
import com.github.abel533.database.IntrospectedTable;
import com.github.abel533.utils.DBMetadataUtils;
import com.github.abel533.utils.JavaBeansUtil;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Generator {

    private final static Logger logger = LoggerFactory.getLogger(Generator.class);

    private CodeTemplateFactory codeTemplateFactory;

    public Generator(CodeTemplateFactory codeTemplateFactory) {
        this.codeTemplateFactory = codeTemplateFactory;
    }

    public void generate(File projectJson) throws GenerateException {

        try {
            ProjectBO projectBO = ProjectLoader.load(projectJson);

            ConnectionBO connection = projectBO.getConnections().get(0);
            DbLoader.Config config = DbLoader.Config.fromMap(BeanUtil.toMap(connection));
            DBMetadataUtils dbMetadataUtils = DbLoader.getDBMetadataUtils(Dialect.MYSQL, config);
            this.codeTemplateFactory.initTypeMap(projectBO.getConfiguration().getTypeMap());
            this.codeTemplateFactory.initNameMap(projectBO.getConfiguration().getNameMap());

            for (ModuleBO module : projectBO.getModules()) {

                if (!module.isEnable())
                    continue;

                IntrospectedTable introspectedTable = null;
                if (StringUtils.isNotBlank(module.getTable())) {
                    DatabaseConfig dbConfig = new DatabaseConfig(null, null, module.getTable());
                    introspectedTable = dbMetadataUtils.introspectTables(dbConfig).get(0);
                }

                for (ComponentBO component : module.getComponents()) {
                    if (!component.isEnable())
                        continue;

                    if (StringUtils.isBlank(module.getTable())) {
                        introspectedTable = new IntrospectedTable();
                        introspectedTable.setName(component.getName());
                    }

                    CodeTemplate codeTemplate = this.codeTemplateFactory.getCodeTemplate(component.getTemplate());
                    String namespace = String.join(".", module.getNamespace(), component.getNamespace());
                    String packagePath = String.join("/", component.getPath(), namespace.replace(".", "/"));

                    File dir = new File(String.join("/", projectBO.getLocation(), module.getPath(), packagePath));
                    if(!dir.exists()) {
                        dir.mkdirs();
                    }

                    String fileName;
                    if (StringUtils.isBlank(module.getTable())) {
                        fileName = JavaBeansUtil.getCamelCaseString(component.getName(), true);
                    } else {
                        fileName = JavaBeansUtil.getCamelCaseString(introspectedTable.getName(), true);
                    }

                    String pathName = dir.getAbsolutePath() + "/" + fileName + component.getSubFix();
                    File outputFile = new File(pathName);

                    codeTemplate.setVariable("table", introspectedTable);
                    codeTemplate.setVariable("package", namespace);
                    codeTemplate.addVariables(component.getVariables());

                    codeTemplate.print(outputFile);
                    System.out.println(String.format("file generated: %s", outputFile.getAbsolutePath()));
                }
            }
        } catch (IllegalAccessException e) {
            logger.error("generate fail", e);
            throw new GenerateException(e);

        } catch (TemplateException e) {
            logger.error("generate fail", e);
            throw new GenerateException(e);

        } catch (IOException e) {
            logger.error("generate fail", e);
            throw new GenerateException(e);

        } catch (SQLException e) {
            logger.error("generate fail", e);
            throw new GenerateException(e);
        }
    }

    public CodeTemplateFactory getCodeTemplateFactory() {
        return codeTemplateFactory;
    }

    public void setCodeTemplateFactory(CodeTemplateFactory codeTemplateFactory) {
        this.codeTemplateFactory = codeTemplateFactory;
    }
}
