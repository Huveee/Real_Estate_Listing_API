package com.example.CRUDApplicationRealEstate.config;

import org.springframework.stereotype.Component;

@Component
public class RequestContext {

    private static final ThreadLocal<String> currentApiKey = new ThreadLocal<>();

    public void setApiKey(String apiKey) {
        currentApiKey.set(apiKey);
    }

    public String getApiKey() {
        return currentApiKey.get();
    }

    public void clear() {
        currentApiKey.remove();
    }
}

