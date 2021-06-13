package com.simpson.domain.controller;


import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.response.HttpResponse;

public class Resources extends AbstractSimpsonServlet {
    
    public Resources(String rootDir) {
        super(rootDir);
    }
    
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.build(getRootDir() + request.getPath());
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        doGet(request, response);
    }
}
