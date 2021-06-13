package com.simpson.domain.url.urlloader;

import com.simpson.domain.controller.SimpsonServlet;
import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.response.HttpResponse;

import java.io.*;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


abstract public class AbstractUrlLoader implements UrlLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractUrlLoader.class);

    public abstract SimpsonServlet getServlet(HttpRequest request);

    @Override
    public void doAct(HttpRequest httpRequest, Socket connection) {
        try {
            logger.info("New Request----------------------");
            logger.info("From = {}:{}", connection.getInetAddress().getHostAddress(),
                                        connection.getPort());
            logger.info("To   = {}", httpRequest.getHeader("Host"));
            OutputStream raw =
                new BufferedOutputStream(connection.getOutputStream());
            Writer out = new OutputStreamWriter(raw);

            HttpResponse response = new HttpResponse(httpRequest);
            SimpsonServlet simpleServlet = getServlet(httpRequest);
            simpleServlet.service(httpRequest, response);
            
            out.write(response.convert());
            out.flush();
        } catch (IOException e) {
            logger.error("{}", e);
        }
    }
}
