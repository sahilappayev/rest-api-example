package com.example.rest.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Slf4j
public class LogFilter extends OncePerRequestFilter {

//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        Map<String, String[]> parameterMap = request.getParameterMap();
//
//        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
//            String key = entry.getKey();
//            String[] value = entry.getValue();
//            log.error("LogFilter Parameters: {} {}", key, value);
//        }
//
//        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//
////        log.error("LogFilter Body: {}", request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
//
//        filterChain.doFilter(request, response);
//        logResponse(requestWrapper, responseWrapper);
//    }
//
//    private void logResponse(ContentCachingRequestWrapper requestWrapper,
//                             ContentCachingResponseWrapper responseWrapper) throws IOException {
//
//        log.info("Request {}", new String(requestWrapper.getContentAsByteArray()));
//        log.info("Response {}", new String(responseWrapper.getContentAsByteArray()));
//        responseWrapper.copyBodyToResponse();
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        RepeatableContentCachingRequestWrapper requestWrapper = new RepeatableContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        logRequest(requestWrapper);
        filterChain.doFilter(requestWrapper, responseWrapper);
        logResponse(responseWrapper);
    }

    private void logRequest(RepeatableContentCachingRequestWrapper requestWrapper) throws IOException {
        String body = requestWrapper.readInputAndDuplicate();
        log.info("Request {}", body);
    }

    private void logResponse(ContentCachingResponseWrapper responseWrapper) throws IOException {
        log.info("Response {}", new String(responseWrapper.getContentAsByteArray()));
        responseWrapper.copyBodyToResponse();
    }

}
