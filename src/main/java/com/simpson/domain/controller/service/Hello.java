package com.simpson.domain.controller.service;

import com.simpson.domain.controller.AbstractSimpsonServlet;
import com.simpson.domain.controller.CurrentTime;
import com.simpson.domain.http.exception.InvalidHeaderException;
import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.response.HttpResponse;
import com.simpson.domain.http.util.SimpleHtmlFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


public class Hello extends AbstractSimpsonServlet {
    private static final Logger log = LoggerFactory.getLogger(Hello.class);
    
    public Hello(String rootDir) {
        super(rootDir);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            log.info("{} {}", Hello.class.getName(), request.getPath());
            String name = request.getParameter("name");
            response.build(SimpleHtmlFormat.getSimpleHtml("Hello", "Hello, " + name).getBytes(StandardCharsets.UTF_8));
        } catch (InvalidHeaderException e) {
            log.error("Error code = 400");
            log.error(e.getMessage());
            response.build(request.getVersion(), 400);
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        response.build(request.getVersion(), 405);
    }
}
