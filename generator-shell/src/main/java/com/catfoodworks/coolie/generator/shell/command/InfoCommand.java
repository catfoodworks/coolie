package com.catfoodworks.coolie.generator.shell.command;

import com.catfoodworks.coolie.generator.shell.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("info")
public class InfoCommand extends Command {

    @Autowired
    private Info info;

    @ShellMethod("show info of coolie generator")
    public void info() {
        System.out.println(String.join("\n"
                , ""
                , String.format("%s", info.getName())
                , String.format("%s", info.getDescription())
                , ""
                , String.format("version: %s", info.getVersion())
                , String.format("author: %s", info.getAuthor())
                , String.format("mail: %s", info.getMail())
                , String.format("github: %s", info.getGithub())
                , ""
                , String.format("coolie home: %s",  getCoolieHome())
                , String.format("template home: %s", getTemplateHome())));
    }

}
