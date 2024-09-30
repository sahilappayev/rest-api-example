package com.example.rest.filter;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.web.util.ContentCachingRequestWrapper;

public class RepeatableContentCachingRequestWrapper extends ContentCachingRequestWrapper {
    private SimpleServletInputStream inputStream;

    public RepeatableContentCachingRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() {
        return this.inputStream;
    }

    public String readInputAndDuplicate() throws IOException {
        if (inputStream == null) {
            byte[] body = super.getInputStream().readAllBytes();
            this.inputStream = new SimpleServletInputStream(body);
        }
        return new String(super.getContentAsByteArray());
    }
}
