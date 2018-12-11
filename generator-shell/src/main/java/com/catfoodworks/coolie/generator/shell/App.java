package com.catfoodworks.coolie.generator.shell;

import com.catfoodworks.coolie.generator.Generator;
import com.catfoodworks.coolie.generator.core.CodeTemplateFactory;
import com.catfoodworks.coolie.generator.core.FreemarkerTemplateFactory;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public PromptProvider promptProvider() {
        return () -> new AttributedString("coolie:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
    }

    @Bean
    public CodeTemplateFactory codeTemplateFactory() {
        return new FreemarkerTemplateFactory();
    }

    @Bean
    public Generator generator(CodeTemplateFactory codeTemplateFactory) {
        return new Generator(codeTemplateFactory);
    }

    @Bean
    public Info info() {
        return new Info();
    }

}
