package com.simpson.domain.http.common;

import com.simpson.domain.http.exception.InvalidHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class HeaderFields {
    private static final Logger logger = LoggerFactory.getLogger(HeaderFields.class);
    private static final String HEADER_FIELD_KEY_VALUE_DELIMITER = ":";

    private final Map<String, String> headerFields;

    public HeaderFields(List<String> headerFields) {
        if (headerFields == null) {
            throw new InvalidHeaderException("Empty header fields.");
        }
        this.headerFields = new HashMap<>();
        for (String headerField : headerFields) {
            parsingField(headerField);
        }
    }

    private void parsingField(String headerField) {
        String key = headerField.substring(0, headerField.indexOf(HEADER_FIELD_KEY_VALUE_DELIMITER));
        String value = headerField.substring(headerField.indexOf(HEADER_FIELD_KEY_VALUE_DELIMITER) + 2);
        headerFields.put(key, value);
    }
    public String convert() {
        StringBuilder sb = new StringBuilder();
        for (String field : headerFields.keySet()) {
            sb.append(field).append(": ").append(headerFields.get(field)).append("\r\n");
        }
        return sb.toString();
    }

    public String getHeader(String fieldName) {
        if (headerFields.containsKey(fieldName)) {
            return headerFields.get(fieldName);
        }
        return null;
    }

    public int getContentLength() {
        return Integer.parseInt(headerFields.getOrDefault("Content-Length", String.valueOf(0)));
    }

    public void addHeader(String fieldName, String field) {
        headerFields.put(fieldName, field);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderFields fields = (HeaderFields) o;
        return headerFields.equals(fields.headerFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerFields);
    }

    @Override
    public String toString() {
        return convert();
    }
}
