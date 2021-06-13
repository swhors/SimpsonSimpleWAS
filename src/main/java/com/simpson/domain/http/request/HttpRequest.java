package com.simpson.domain.http.request;

import com.simpson.domain.http.common.HeaderFields;
import com.simpson.domain.http.common.HttpVersion;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HeaderFields headerFields;
    private final RequestLineBody datas;

    public HttpRequest(RequestLine requestLine, HeaderFields headerFields, RequestLineBody datas) {
        this.requestLine = requestLine;
        this.headerFields = headerFields;
        this.datas = datas;
    }
    
    public HttpVersion getVersion() {
        return this.requestLine.getVersion();
    }

    public boolean isGetMethod() {
        return requestLine.isGetMethod();
    }

    public boolean isPostMethod() {
        return requestLine.isPostMethod();
    }

    public String getHeader(String fieldName) {
        return headerFields.getHeader(fieldName);
    }
    
    public HeaderFields getHeaders() {
        return this.headerFields;
    }

    public String getParameter(String fieldName) {
        if (requestLine.isExistValue(fieldName)) {
            return requestLine.getParameter(fieldName);
        }
        return datas.getData(fieldName);
    }

    public String getPath() {
        return requestLine.getPath();
    }

}