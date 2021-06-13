package com.simpson.domain.controller.error;

import com.simpson.SimpsonWeb;
import com.simpson.domain.controller.AbstractSimpsonServlet;
import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.response.HttpResponse;
import com.simpson.domain.property.ErrorFile;
import com.simpson.domain.property.Html;

import java.util.Optional;

public class Error extends AbstractSimpsonServlet {

    private int errorCode = 0;
    
    private static Html htmls = SimpsonWeb.getProperty().getHtml();
    private static String ERROR_ROOT_DIR = "/error";

    public Error() {
        super(ERROR_ROOT_DIR);
        this.errorCode = 0;
    }

    public Error(int errorCode) {
        super(ERROR_ROOT_DIR);
        this.errorCode = errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        sendError(request, response);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        sendError(request, response);
    }
    
    private void sendError(HttpRequest request, HttpResponse response) {
        Optional<ErrorFile> html = htmls.getErrorFile(errorCode);
        if (html.isPresent()) {
            response.build(html.get().getFileName());
        } else {
            response.build(request.getVersion(), errorCode);
        }
    }
}
