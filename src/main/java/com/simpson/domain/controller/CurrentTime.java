package com.simpson.domain.controller;

import com.simpson.domain.http.exception.InvalidHeaderException;
import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.http.response.HttpResponse;
import com.simpson.domain.http.util.SimpleHtmlFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CurrentTime extends AbstractSimpsonServlet {
    private static final Logger log = LoggerFactory.getLogger(CurrentTime.class);
    
    public CurrentTime(String rootDir) {
        super(rootDir);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        try {
            logger.info("{} {}", CurrentTime.class.getName(), request.getPath());
            response.build(buildHtml().getBytes(StandardCharsets.UTF_8));
        } catch (InvalidHeaderException e) {
            log.error("Error code = 400");
            log.error(e.getMessage());
            response.build(request.getVersion(), 400);
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        response.build(request.getVersion(), 405);
    }

    private String buildHtml() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<String> components = new ArrayList<>();
        components.add(SimpleHtmlFormat.htmlTagStart);
        components.add(SimpleHtmlFormat.headTagStart);
        components.add(SimpleHtmlFormat.titleTagStart);
        components.add("CurrentTime");
        components.add(SimpleHtmlFormat.titleTagEnd);
        components.add(SimpleHtmlFormat.headTagEnd);
        components.add(SimpleHtmlFormat.bodyTagStart);
        components.add(SimpleHtmlFormat.pTagStart);
        components.add("Today is " + dateTime.getDayOfWeek() + ".\n");
        components.add(SimpleHtmlFormat.nextLineTag);
        components.add("\tCurrent date => " + dateTime.getYear() +
            "-" + dateTime.getMonthValue() +
            "-" + dateTime.getDayOfMonth() + "\n");
        components.add(SimpleHtmlFormat.nextLineTag);
        components.add("\tCurrent time => " + dateTime.getHour() +
            ":" + dateTime.getMinute() +
            ":" + dateTime.getSecond() + "\n");
        components.add(SimpleHtmlFormat.pTagEnd);
        components.add(SimpleHtmlFormat.bodyTagEnd);
        components.add(SimpleHtmlFormat.htmlTagEnd);
        
        StringBuilder htmlText = new StringBuilder();
        for(String component : components) {
                htmlText.append(component);
        }
        return htmlText.toString();
    }
}
