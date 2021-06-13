package com.simpson.domain.WebServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private static final int NUM_THREADS = 20;
    private final int port;
    
    public HttpServer(int port) {
        this.port = port;
    }
    
    public void start() {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try {
            ServerSocket server = new ServerSocket(port);
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Accepting connections on port " + server.getLocalPort());
            try {
                Socket request;
                while ((request = server.accept()) != null) {
                        Runnable r = new RequestProcessor(request);
                        pool.submit(r);
                        
                }
            } catch (IOException ex) {
                logger.error("Error accepting connection", ex);
            }
        } catch (Exception e) {
            logger.error("Error accepting connection", e);
        }
    }
}
