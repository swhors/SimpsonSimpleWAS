package com.simpson.domain.http.mapper;

import com.simpson.SimpsonWeb;
import com.simpson.domain.controller.SimpsonServlet;
import com.simpson.domain.property.Policy;
import com.simpson.domain.property.Url;
import com.simpson.domain.property.Property;
import com.simpson.domain.property.Controller;
import com.simpson.domain.utils.ClassUtil;
import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;
import java.util.Optional;

public class ControllerMapper {
    public static Map<String, SimpsonServlet> loadContollers(String address) {
        Map<String, SimpsonServlet> controllers = new HashedMap<>();
        
        Property property = SimpsonWeb.getProperty();
        
        Policy urlPolicy = property.getPolicy();
        
        Optional<Url> policy = urlPolicy.getPolicy(address);
        
        policy.ifPresent( p -> {
            for (Controller controller : p.getcontrollers()) {
                SimpsonServlet simpleServlet =
                    (SimpsonServlet) ClassUtil.createInstance(
                        controller.getClassName(),
                        p.getRootDir());
                if (simpleServlet != null) {
                    controllers.put(controller.getUrn(), simpleServlet);
                }
            }
        });
        return controllers;
    }
}
