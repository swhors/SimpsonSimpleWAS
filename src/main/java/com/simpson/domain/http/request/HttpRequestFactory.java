package com.simpson.domain.http.request;

import com.simpson.domain.http.common.HeaderFields;
import com.simpson.domain.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequestFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestFactory.class);

    public static HttpRequest createHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
        String firstLine = br.readLine();
        RequestLine requestLine = RequestLine.buildParameter(firstLine);
        HeaderFields headerFields = parseHeaderFields(br);
        RequestLineBody datas = parseRequestData(br, requestLine, headerFields.getContentLength());

        return new HttpRequest(requestLine, headerFields, datas);
    }

    private static HeaderFields parseHeaderFields(BufferedReader br) throws IOException {
        List<String> headerFields = new ArrayList<>();
        String line = br.readLine();
        headerFields.add(line);
        while (!StringUtils.isEmpty(line)) {
            logger.debug("headerField : {}", line);
            headerFields.add(line);
            line = br.readLine();
        }
        return new HeaderFields(headerFields);
    }

    private static RequestLineBody parseRequestData(BufferedReader br, RequestLine requestLine, int contentLength) throws IOException {
        String bodyData = "";
        if (contentLength != 0) {
            bodyData = IOUtils.readData(br, contentLength);
        }
        return new RequestLineBody(bodyData);
    }
}
