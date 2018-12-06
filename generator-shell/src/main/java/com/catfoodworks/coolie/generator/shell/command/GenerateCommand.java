package com.catfoodworks.coolie.generator.shell.command;

import com.catfoodworks.coolie.generator.Generator;
import com.catfoodworks.coolie.generator.exception.CodeTemplateException;
import com.catfoodworks.coolie.generator.exception.GenerateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.validation.constraints.NotEmpty;
import java.io.File;

@ShellComponent
public class GenerateCommand {

    private Logger logger = LoggerFactory.getLogger(GenerateCommand.class);

    @Autowired
    private Generator generator;

    @ShellMethod("generate source code by template and project.json")
    public void gen(
            @ShellOption({"-t", "--template"}) String template
            ,@ShellOption({"-p", "--project"}) @NotEmpty String project) {

        File templateFile = new File(template);
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
