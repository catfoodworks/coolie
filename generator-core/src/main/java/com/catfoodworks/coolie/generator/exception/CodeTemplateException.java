package com.catfoodworks.coolie.generator.exception;

public class CodeTemplateException extends Exception {

    public CodeTemplateException() {
    }

    public CodeTemplateException(String message) {
        super(message);
    }

    public CodeTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeTemplateException(Throwable cause) {
        super(cause);
    }

    public CodeTemplateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
