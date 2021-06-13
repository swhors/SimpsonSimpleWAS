package com.simpson.domain.http.request;

import com.simpson.domain.http.common.HttpMethod;
import com.simpson.domain.http.common.HttpVersion;
import com.simpson.domain.http.exception.InvalidHeaderException;

import java.util.*;

import static com.simpson.domain.http.common.HttpMethod.GET;
import static com.simpson.domain.http.common.HttpMethod.POST;

public class RequestLine {
    private static final String QUERY_STRING_DELIMITER_REGEX = "\\?";
    private static final String REQUEST_LINE_DELIMITER_REGEX = " ";
    private static final String PARAMETERS_DELIMITER = "&";
    private static final String PARAMETER_KEY_VALUE_DELIMITER = "=";

    private final HttpMethod method;
    private String path;
    private Map<String, String> paramValues = new HashMap<>();
    private final HttpVersion version;
    
    static public RequestLine buildParameter(String requestLine) {
        List<String> tokens = Arrays.asList(requestLine.split(REQUEST_LINE_DELIMITER_REGEX));
        
        validateRequestLine(requestLine, tokens);
    
        HttpMethod method = HttpMethod.valueOf(tokens.get(0));
        String path = tokens.get(1);
        HttpVersion version = HttpVersion.getVersion(tokens.get(2));

        return new RequestLine(method, path, version);
    }

    public RequestLine(HttpMethod httpMethod, String paths, HttpVersion version) {
        this.method = httpMethod;
        parsePathAndQuery(paths);
        this.version = version;
    }

    private void parseData(String queryString) {
        List<String> tokens = Arrays.asList(queryString.split(PARAMETERS_DELIMITER));
        tokens.forEach(token -> {
            paramValues.put(token.split(PARAMETER_KEY_VALUE_DELIMITER)[0], token.split(PARAMETER_KEY_VALUE_DELIMITER)[1]);
        });
    }

    private void parsePathAndQuery(String token) {
        String []paths = token.split(QUERY_STRING_DELIMITER_REGEX);
        path = paths[0];
        if (paths.length > 1) {
            parseData(paths[1]);
        }
    }

    private static void validateRequestLine(String requestLine, List<String> tokens) {
        if (tokens.size() != 3 || !HttpMethod.matches(tokens.get(0)) || !HttpVersion.isExistVersion(tokens.get(2))) {
            throw new InvalidHeaderException("Invalid HttpRequest : " + requestLine);
        }
    }

    public boolean isGetMethod() {
        return GET.equals(method);
    }

    public boolean isPostMethod() {
        return POST.equals(method);
    }

    public boolean isExistValue(String fieldName) {
        return paramValues.containsKey(fieldName);
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String name) {
        if (!isExistValue(name)) {
            throw new InvalidHeaderException(name + "don't to be existed query.");
        }
        return paramValues.get(name);
    }

    public HttpVersion getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method &&
                Objects.equals(path, that.path) &&
                Objects.equals(paramValues, that.paramValues) &&
                version == that.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, paramValues, version);
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", path='" + path + '\'' +
                ", paramValues=" + paramValues +
                ", version=" + version +
                '}';
    }
}
