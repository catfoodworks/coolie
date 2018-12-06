package com.catfoodworks.coolie.generator.exception;

public class GenerateException extends Exception {

    public GenerateException() {
    }

    public GenerateException(String message) {
        super(message);
    }

    public GenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerateException(Throwable cause) {
        super(cause);
    }

    public GenerateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
