package com.crm.application.mapper;

import jakarta.persistence.criteria.From;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Mapper {
    private static final Map<Class<?>, Map<Type, Function<?, ?>>> mappers = new HashMap<>();

    public static <From, To> void createMapper(Class<From> fromClazz, Class<To> toClazz, Function<From, To> mapper) {
        mappers.computeIfAbsent(fromClazz,
                k -> new HashMap<>()).put(toClazz, mapper);
    }

    public static <To> To map(Object from, Class<To> toClazz) {
        Class<?> fromClazz = from.getClass();
        Map<Type, Function<?, ?>> availableMappings = mappers.get(fromClazz);
        if (availableMappings == null) {
            throw new IllegalArgumentException("No mappings registered for class: " + fromClazz.getName());
        }

        @SuppressWarnings("unchecked")
        Function<Object, To> mapper = (Function<Object, To>) availableMappings.get(toClazz);
        if (mapper == null) {
            throw new IllegalArgumentException("No mapping registered for class: " + toClazz.getName());
        }

        return mapper.apply(from);
    }

    public static <To> To map(Object from) {
        Class<?> fromClazz = from.getClass();
        Map<Type, Function<?, ?>> availableMappings = mappers.get(fromClazz);
        if (availableMappings == null) {
            throw new IllegalArgumentException("No mappings registered for class: " + fromClazz.getName());
        }

        @SuppressWarnings("unchecked")
        Function<Object, To> mapper = (Function<Object, To>) availableMappings.values().iterator().next();
        return mapper.apply(from);
    }
}
