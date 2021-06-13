package com.simpson.domain.http.mapper;

import com.simpson.domain.controller.SimpsonServlet;
import com.simpson.domain.controller.error.Error;

import java.util.Map;

public class LocalControllerMapper extends ControllerMapper {
    private static final Map<String, SimpsonServlet> controllers
        = loadContollers("localhost");

    public static SimpsonServlet mappingController(String path) {
        return controllers.getOrDefault(path, new Error(404));
    }
}
