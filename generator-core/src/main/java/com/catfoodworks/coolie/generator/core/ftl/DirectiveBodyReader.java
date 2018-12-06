package com.catfoodworks.coolie.generator.core.ftl;

import java.io.IOException;
import java.io.Writer;

public class DirectiveBodyReader extends Writer {

    private StringBuilder stringBuilder = new StringBuilder();

    public String getString() {
        return stringBuilder.toString();
    }

    public void write(char[] cbuf, int off, int len)  throws IOException {
        char[] readBuf = new char[len];
        for (int i = 0; i < len; i++) {
            readBuf[i] = cbuf[i + off];
        }
        stringBuilder.append(readBuf);
    }

    public void flush() throws IOException {

    }

    public void close() throws IOException {

    }

}
