package com.crm.cache;

import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class Cache {
    private final Map<String, Object> cache;

    public Cache() {
        this.cache = new HashMap<>();
    }

    public void add(String key, Object value) {
        this.cache.put(key, value);
    }

    public Object get(String key) {
        return this.cache.get(key);
    }

    public <T> Optional<T> getValueAsType(String key, Class<T> type) {
        Object val = this.cache.get(key);
        if (type.isInstance(val)) {
            return Optional.of(type.cast(val));
        }
        return Optional.empty();
    }


    public void clear() {
        this.cache.clear();
    }

    public void remove(String key) {
        this.cache.remove(key);
    }

    public boolean contains(String key) {
        return this.cache.containsValue(key);
    }

    public int size() {
        return this.cache.size();
    }
}
