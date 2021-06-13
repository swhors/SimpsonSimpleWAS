package com.simpson.domain.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIoUtils {
    private static final String HTML_DEFAULT_PATH = "templates";
    private static final String STATIC_DEFAULT_PATH = "static";

    public static byte[] loadHtmlFile(String path)
        throws IOException, URISyntaxException, ClassNotFoundException, FileNotFoundException {
        String fileFullPath = (path.endsWith(".html")?HTML_DEFAULT_PATH:STATIC_DEFAULT_PATH) + path;
        Class cls = Class.forName("com.simpson.SimpsonWeb");
        URL url = cls.getClassLoader().getResource(fileFullPath);
        if (url == null) {
            throw new FileNotFoundException("Error : Can not find " + path);
        }
        return Files.readAllBytes(Paths.get(url.toURI()));
    }
}
