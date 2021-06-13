package com.simpson;

import com.simpson.domain.WebServer.HttpServer;
import com.simpson.domain.property.Property;
import com.simpson.domain.utils.PropertyUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpsonWeb {
    private static final Logger logger = LoggerFactory.getLogger(SimpsonWeb.class);
    private static final int DEFAULT_PORT = 8080;
    
    private static HttpServer webServer = null;
    private static Property property = null;
    
    public static HttpServer getWebServer() {return webServer;}
    public static Property getProperty() {return property;}
    
    
    public static void main(String[] args) {
        int port;
        try {
            property = PropertyUtil.loadPropertiesFromJson();
            
            if (args.length > 0) {
                port = Integer.parseInt(args[ 0 ]);
            } else {
                port = property.getServer().getPort();
            }
            
            if (port < 0 || port > 65535) port = DEFAULT_PORT;
    
            webServer = new HttpServer(port);
            webServer.start();
        } catch (RuntimeException | IOException ex) {
            logger.error(ex.getMessage());
        }
    }
}