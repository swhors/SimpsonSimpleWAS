package com.simpson.domain.controller;

import com.simpson.domain.http.exception.NotFoundHtmlFileException;
import com.simpson.domain.http.exception.NotFoundParamException;
import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSimpsonServlet implements SimpsonServlet {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractSimpsonServlet.class);
    
    private String rootDir = "";
    
    public AbstractSimpsonServlet(String rootDir) {
        this.rootDir = rootDir;
    }
    
    public String getRootDir() {
        return rootDir;
    }
    
    public abstract void doGet(HttpRequest request, HttpResponse response);
    public abstract void doPost(HttpRequest request, HttpResponse response);
    
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        try {
            if (request.isGetMethod()) {
                doGet(request, response);
                return;
            }
            if (request.isPostMethod()) {
                doPost(request, response);
            }
        } catch (NotFoundParamException e) {
            logger.error("Error code = 400");
            logger.error("{}", e);
            response.build(request.getVersion(), 400);
        } catch (NotFoundHtmlFileException e) {
            logger.error("Error code = 404");
            logger.error("{}", e);
            response.build(request.getVersion(), 404);
        } catch (RuntimeException e) {
            logger.error("Error code = 500");
            logger.error("{}", e);
            response.build(request.getVersion(), 500);
        }
    }
}
