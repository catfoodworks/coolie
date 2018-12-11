package com.catfoodworks.coolie.generator.shell.command;

import java.io.File;

public abstract class Command {

    protected String getCoolieHome() {
        return String.join(File.separator, System.getProperty("user.home"), ".coolie");
    }

    protected String getTemplateHome() {
        return String.join(File.separator, getCoolieHome(), "template");
    }

    protected void initCoolieHome() {
        initHome(getCoolieHome());
    }

    protected void initTemplateHome() {
        initHome(getTemplateHome());
    }

    private void initHome(String homePath) {
        File home = new File(homePath);
        if (!home.exists()) {
            home.mkdirs();
        }
    }

}
