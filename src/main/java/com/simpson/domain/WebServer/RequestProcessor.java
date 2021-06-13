package com.simpson.domain.WebServer;

import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.request.HttpRequestFactory;
import com.simpson.domain.url.mapper.UrlMapper;
import com.simpson.domain.url.urlloader.UrlLoader;
import java.io.*;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RequestProcessor implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
    final private Socket connection;
    
    public RequestProcessor(Socket connection) {
        this.connection = connection;
    }
    
    @Override
    public void run() {
        logger.info("New Client Connect! Connected IP : " +
            connection.getInetAddress().getHostAddress()+
            ", Port : ",
            + connection.getPort());
        try {
            HttpRequest request = HttpRequestFactory.createHttpRequest(connection.getInputStream());
    
            String requestedHost = request.getHeader("Host");
            if (requestedHost != null) {
                System.out.println("host : " + requestedHost);
                UrlLoader policy = UrlMapper.mappingPolicy(requestedHost, request.getPath());
                policy.doAct(request, connection);
            }
        } catch (IOException e) {
            logger.error("{}", e);
        }

    }
}
