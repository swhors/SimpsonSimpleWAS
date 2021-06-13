package com.simpson.domain.property;

import java.util.List;
import java.util.Optional;

public class Url {
    private String address;
    private String rootDir;
    private String className;
    private List<Controller> controllers;
    
    public String getAddress() {
        return address;
    }
    
    public String getRootDir() {
        return rootDir;
    }
    
    public Url(String address,
               String rootDir,
               String className,
               List<Controller> controllers) {
        this.address = address;
        this.rootDir = rootDir;
        this.className = className;
        this.controllers = controllers;
    }
    
    public String getClassName() {
        return className;
    }
    
    public List<Controller> getcontrollers () {
        return controllers;
    }
    
    public Optional<Controller> getService(String urn) {
        for (Controller controller : controllers) {
            if (controller.getUrn().compareTo(urn) == 0)
                return Optional.of(controller);
        }
        return Optional.empty();
    }
}
