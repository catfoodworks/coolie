package com.catfoodworks.coolie.generator.shell.command;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.util.Arrays;

@ShellComponent
@ShellCommandGroup("template")
public class TemplateCommand extends Command {

    private Logger logger = LoggerFactory.getLogger(TemplateCommand.class);

    @ShellMethod(value = "add a code template\n" +
            "\t\t\t-t, --template: path of template directory\n" +
            "\t\t\t-n, --name: specify a name for template"
            , key = "template add")
    public void add(
            @ShellOption({"-t", "--template"}) String template
            , @ShellOption({"-n", "--name"}) @NotEmpty String name) {

        try {

            initTemplateHome();
            File templateFile = new File(template);
            File directory = new File(String.join(File.separator, getTemplateHome(), name));
            FileUtils.copyDirectory(templateFile, directory);

        } catch (Exception e) {
            logger.error("add template fail", e);
            logger.info("add template fail {}", e.getMessage());
        }

    }

    @ShellMethod(value = "remove a code template\n" +
            "\t\t\t-n, --name: specify the name for template which will be remove"
            , key = "template remove")
    public void remove(@ShellOption({"-n", "--name"}) @NotEmpty String name) {
        try {

            initTemplateHome();
            File directory = new File(String.join(File.separator, getTemplateHome(),  name));
            FileUtils.deleteDirectory(directory);

        } catch (Exception e) {
            logger.error("remove template fail", e);
            logger.info("remove template fail {}", e.getMessage());
        }
    }

    @ShellMethod(value = "list code templates\n" +
            "\t\t\t-d: show template file in every template directory"
            , key = "template list")
    public void list(@ShellOption("-d") boolean detail) {
        initTemplateHome();
        File templateHome = new File(getTemplateHome());
        if (detail) {

            for (File dir : templateHome.listFiles()) {
                System.out.println(dir.getName());
                for (File file  : dir.listFiles()) {
                    System.out.println(String.format("    %s", file.getName()));
                }
            }

        } else {
            System.out.println(String.join("\n", Arrays.stream(templateHome.listFiles()).map(File::getName).toArray(String[]::new)));
        }

    }
}
