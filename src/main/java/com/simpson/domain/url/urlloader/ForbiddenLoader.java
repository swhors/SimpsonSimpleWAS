package com.simpson.domain.url.urlloader;

import com.simpson.domain.controller.SimpsonServlet;
import com.simpson.domain.controller.error.Error;
import com.simpson.domain.http.request.HttpRequest;

public class ForbiddenLoader extends AbstractUrlLoader {
    @Override
    public SimpsonServlet getServlet(HttpRequest request) {
        return new Error(403);
    }
}
