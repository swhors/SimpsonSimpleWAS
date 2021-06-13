package com.simpson.domain.http.response;

import com.simpson.domain.http.common.HeaderFields;
import com.simpson.domain.http.common.HttpStatus;
import com.simpson.domain.http.common.HttpVersion;
import com.simpson.domain.http.exception.NotFoundHtmlFileException;
import com.simpson.domain.http.request.HttpRequest;
import com.simpson.domain.utils.FileIoUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CHARSET_UTF_8 = "charset=utf-8";

    private HttpRequest request;
    private HttpStatus status;
    private final HeaderFields headerFields;
    private byte[] body;

    public HttpResponse(HttpRequest request) {
        this.request = request;
        headerFields = new HeaderFields(new ArrayList<>());
    }

    public void build(String path) {
        try {
            status = HttpStatus.OK;
            body = FileIoUtils.loadHtmlFile(path);
            String type = new Tika().detect(path);

            headerFields.addHeader(CONTENT_TYPE, type + ";" + CHARSET_UTF_8);
            headerFields.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
        } catch (IOException | URISyntaxException | NotFoundHtmlFileException e) {
            logger.error("Error Code = 404");
            logger.error(e.getMessage());
            build(HttpVersion.HTTP_1_1, 404);
        } catch (ClassNotFoundException e) {
            logger.error("Error Code = 500");
            logger.error(e.getMessage());
            build(HttpVersion.HTTP_1_1, 500);
        }
    }

    public void build(byte[] body) {
        status = HttpStatus.OK;
        this.body = body;
        String type = new Tika().detect(body);

        headerFields.addHeader(CONTENT_TYPE, type + ";" + CHARSET_UTF_8);
        headerFields.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }
    
    public void build(HttpVersion version, int errorCode) {
        status = HttpStatus.getHttpStatusByCode(errorCode);
        this.body = String.format("%s %s", version.toString(), status.toString()).getBytes(StandardCharsets.UTF_8);
        String type = new Tika().detect(body);
        
        headerFields.addHeader(CONTENT_TYPE, type + ";" + CHARSET_UTF_8);
        headerFields.addHeader(CONTENT_LENGTH, String.valueOf(body.length));
    }

    public String convert() {
        if (ObjectUtils.isEmpty(body)) {
            return convertHeader();
        }
        return convertHeader() + new String(body);
    }

    private String convertHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(HttpVersion.HTTP_1_1.getVersion()).append(" ")
                .append(status.getStatusCode()).append(" ")
                .append(status.getStatusName()).append("\r\n");
        sb.append(headerFields.convert());
        sb.append("\r\n");

        logger.debug("\n--response Header--\n{}", sb.toString());
        return sb.toString();
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse response = (HttpResponse) o;
        return status == response.status &&
                Objects.equals(headerFields, response.headerFields) &&
                Arrays.equals(body, response.body);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(status, headerFields);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }

    @Override
    public String toString() {
        return convert();
    }
}
