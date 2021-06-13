package com.simpson.domain.http.mapper;

import com.simpson.domain.controller.error.Error;
import com.simpson.domain.controller.SimpsonServlet;

import java.util.Map;

public class SimpsonControllerMapper extends ControllerMapper {
    private static final Map<String, SimpsonServlet> controllers
        = loadContollers("simpson.com");

    public static SimpsonServlet mappingController(String path) {
        return controllers.getOrDefault(path, new Error(404));
    }
}
