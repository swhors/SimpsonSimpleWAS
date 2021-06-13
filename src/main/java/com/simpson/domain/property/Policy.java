package com.simpson.domain.property;

import java.util.List;
import java.util.Optional;

public class Policy {
    private List<String> blacklist;
    private List<Url> urls;
    
    public List<String> getBlacklist() {
        return blacklist;
    }

    public List<Url> getPolicies() {
        return urls;
    }
    
    public boolean includeBlackList(String path) {
        boolean find = false;
        for (String bl : blacklist) {
            if (path.contains(bl)) {
                find = true;
                break;
            }
        }
        return find;
    }
    
    public boolean hasPolicy(String address) {
        for (Url url : urls) {
            if (url.getAddress().compareTo(address) == 0) {
                return true;
            }
        }
        return false;
    }
    
    public Optional<Url> getPolicy(String address) {
        for (Url url : urls) {
            if (url.getAddress().compareTo(address) == 0) {
                return Optional.of(url);
            }
        }
        return Optional.empty();
    }
}
