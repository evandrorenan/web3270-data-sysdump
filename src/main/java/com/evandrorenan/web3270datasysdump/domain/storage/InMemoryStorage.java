package com.evandrorenan.web3270datasysdump.domain.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorage {
    private final Map<String, Object> store = new ConcurrentHashMap<>();

    public void save(String key, Object value) {
        store.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) store.get(key);
    }

    public void remove(String key) {
        store.remove(key);
    }

    public boolean contains(String key) {
        return store.containsKey(key);
    }
}
