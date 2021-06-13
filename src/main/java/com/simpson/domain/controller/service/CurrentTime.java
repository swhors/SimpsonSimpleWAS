package com.simpson.domain.controller.service;

import com.simpson.domain.controller.AbstractSimpsonServlet;
import com.simpson.domain.http.exception.InvalidHeaderException;
import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CurrentTime extends AbstractSimpsonServlet {
    private static final Logger log = LoggerFactory.getLogger(CurrentTime.class);
    
    public CurrentTime(String rootDir) {
        super(rootDir);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            log.info("{} {}", CurrentTime.class.getName(), request.getPath());
            response.build(getRootDir() + "/currenttime.html");
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
