package com.catfoodworks.coolie.generator.shell.command;

import com.catfoodworks.coolie.generator.Generator;
import com.catfoodworks.coolie.generator.exception.CodeTemplateException;
import com.catfoodworks.coolie.generator.exception.GenerateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.validation.constraints.NotEmpty;
import java.io.File;

@ShellComponent
@ShellCommandGroup("generate")
public class GenerateCommand extends Command {

    private Logger logger = LoggerFactory.getLogger(GenerateCommand.class);

    @Autowired
    private Generator generator;

    @ShellMethod("generate source code by template and project.json\n" +
            "\t\t\t-n, --name: specify a template to use for code generation by template name.\n" +
            "\t\t\t-p, --project: specify the path of json format configure for code generation.\n" +
            "\t\t\t-t, --template: specify a template to use for code generation by template path")
    public void gen(
            @ShellOption({"-n", "--name"}) String name
            , @ShellOption({"-p", "--project"}) @NotEmpty String project
            , @ShellOption(value = {"-t", "--template"}, defaultValue = "") String template) {

        File templateFile;
        if (StringUtils.isNotBlank(name)) {
            templateFile = new File(String.join(File.separator, getTemplateHome(), name));
        } else {
            templateFile = new File(template);
        }

        File projectFile = new File(project);

        try {

            generator.getCodeTemplateFactory().init(templateFile);
            generator.generate(projectFile);

        } catch (CodeTemplateException e) {
            logger.info("gen init fail {}", e.getMessage());

        } catch (GenerateException e) {
            logger.info("gen file fail {}", e.getMessage());
        }
    }

}
