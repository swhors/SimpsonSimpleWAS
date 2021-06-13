package com.simpson.domain.url.urlloader;

import com.simpson.domain.controller.SimpsonServlet;
import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.mapper.SimpsonControllerMapper;

public class SimpsonLoader extends AbstractUrlLoader {

    @Override
    public SimpsonServlet getServlet(HttpRequest request) {
        return SimpsonControllerMapper.mappingController(request.getPath());
    }
}
