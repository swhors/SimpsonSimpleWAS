package com.simpson.domain.http.common;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    FOUND(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    NOT_ALLOWED(405, "Method Not Allowed"),
    MIS_REQUEST(421, "Misdirected Request");

    private final int statusCode;
    private final String statusName;

    HttpStatus(int statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusCode + " " + statusName;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }
    
    static public HttpStatus getHttpStatusByCode(int code) {
        switch (code) {
            case 421: return MIS_REQUEST;
            case 400: return BAD_REQUEST;
            case 401: return UNAUTHORIZED;
            case 403: return FORBIDDEN;
            case 404: return NOT_FOUND;
            case 405: return NOT_ALLOWED;
            case 201: return CREATED;
            case 302: return FOUND;
            default: return OK;
        }
    }
}