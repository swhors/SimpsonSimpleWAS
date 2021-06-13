package com.simpson.domain.property;

public class Controller {
    private String path;
    private String className;
    
    public Controller(String path, String className) {
        this.path = path;
        this.className = className;
    }
    
    public String getUrn() {
        return path;
    }
    
    public String getClassName() {
        return className;
    }
}
