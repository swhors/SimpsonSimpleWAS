package com.simpson.domain.url.urlloader;

import com.simpson.domain.http.request.HttpRequest;

import java.net.Socket;

public interface UrlLoader {
    void doAct(HttpRequest httpRequest, Socket connection);
}
