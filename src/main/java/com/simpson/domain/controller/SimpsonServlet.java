package com.simpson.domain.controller;

import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.response.HttpResponse;

public interface SimpsonServlet {
    void service(HttpRequest request, HttpResponse response);
}
