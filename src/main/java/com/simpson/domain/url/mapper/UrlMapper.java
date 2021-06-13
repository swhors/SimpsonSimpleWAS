package com.simpson.domain.url.mapper;

import com.simpson.SimpsonWeb;
import com.simpson.domain.property.Url;
import com.simpson.domain.property.Property;
import com.simpson.domain.property.Policy;
import com.simpson.domain.url.urlloader.ForbiddenLoader;
import com.simpson.domain.url.urlloader.UnsupportedLoader;
import com.simpson.domain.url.urlloader.UrlLoader;
import com.simpson.domain.utils.ClassUtil;
import org.apache.commons.collections4.map.HashedMap;

import java.util.List;
import java.util.Map;

public class UrlMapper {
    private static final Map<String, UrlLoader> policies = new HashedMap<>();
    
    static {
        Property property = SimpsonWeb.getProperty();
        Policy policy = property.getPolicy();
        List<Url> urlList = policy.getPolicies();
        for (Url url : urlList) {
            UrlLoader urlLoader = (UrlLoader) ClassUtil.createInstance(url.getClassName(), null);
            if (url != null) {
                policies.put(url.getAddress(), urlLoader);
            }
        }
    }

    public static UrlLoader mappingPolicy(String address, String path) {
        String localAddress = address;
        if (localAddress.contains(":")) {
            localAddress = localAddress.split(":")[0];
        }
        if (checkPathSecurity(path)) {
            return policies.getOrDefault(localAddress, new UnsupportedLoader());
        } else {
            return new ForbiddenLoader();
        }
    }

    /*
      Path에 Property URL의 blacklist 중 하나를 포함하면 false를 반환 합니다.
        예,
           1) http://localhost:8000/../../../../etc/passwd
           2) 확장자가 .exe 인 파일을 요청받았을 때
    */
    public static boolean checkPathSecurity(String path) {
        return !SimpsonWeb.getProperty().getPolicy().includeBlackList(path);
    }
}
