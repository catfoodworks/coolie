package com.catfoodworks.coolie.generator.util;

import com.catfoodworks.coolie.generator.model.bo.ComponentBO;
import com.catfoodworks.coolie.generator.model.bo.ModuleBO;
import com.catfoodworks.coolie.generator.model.bo.ProjectBO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProjectLoader {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ProjectBO load(File file) throws IOException {
        ProjectBO projectBO = OBJECT_MAPPER.readValue(file, ProjectBO.class);

        Map<String, ComponentBO> componentMap = projectBO.getComponents()
                .stream()
                .collect(Collectors.toMap(ComponentBO::getName, c -> c));

        for (ModuleBO module : projectBO.getModules()) {

            projectBO.getConfiguration().getTypeMap().putAll(module.getConfiguration().getTypeMap());
            projectBO.getConfiguration().getNameMap().putAll(module.getConfiguration().getNameMap());

            for (ComponentBO component : module.getComponents()) {
                ComponentBO componentTemplate = componentMap.get(component.getName());
                if (Objects.isNull(componentTemplate))
                    continue;

                if (isBlank(component.getTemplate()))
                    component.setTemplate(componentTemplate.getTemplate());

                if (isBlank(component.getSubFix()))
                    component.setSubFix(componentTemplate.getSubFix());

                if (isBlank(component.getPath()))
                    component.setPath(componentTemplate.getPath());

                if (isBlank(component.getNamespace()))
                    component.setNamespace(componentTemplate.getNamespace());

                HashMap<String, Object> copyed = new HashMap<>();
                copyed.putAll(componentTemplate.getVariables());
                copyed.putAll(component.getVariables());
                component.setVariables(copyed);
            }

        }

        return projectBO;
    }

    private static boolean isBlank(String str) {
        return Objects.isNull(str) || str.trim().length() == 0;
    }

}
