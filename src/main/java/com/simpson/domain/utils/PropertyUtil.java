package com.simpson.domain.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import com.google.gson.Gson;
import com.simpson.domain.property.Property;

public class PropertyUtil {
    final static private String jSonFileName = "src/main/resources/application.json";
    
    static public Property loadPropertiesFromJson() throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Gson gson = new Gson();
    
        Reader reader = new FileReader(jSonFileName);
        
        Property property = gson.fromJson(reader, Property.class);
        System.out.println(property);
        return property;
    }
}
