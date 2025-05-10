package com.ua07.search.strategy;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class ReflectionBasedStrategyFactory {
    private final Map<SortType, SortingStrategy> strategies = new EnumMap<>(SortType.class);
    private static final String BASE_PACKAGE = "com.ua07.search.strategy.sorting";

    public ReflectionBasedStrategyFactory() {
        for (SortType type : SortType.values()) {
            String className = BASE_PACKAGE + "." + formatClassName(type) + "SortStrategy";

            try {
                Class<?> clazz = Class.forName(className);
                SortingStrategy strategy = (SortingStrategy) clazz.getDeclaredConstructor().newInstance();
                strategies.put(type, strategy);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load strategy class: " + className, e);
            }
        }
    }

    public SortingStrategy getStrategy(SortType type) {
        return strategies.getOrDefault(type, new PriceSortStrategy());
    }

    private String formatClassName(SortType type) {
        String name = type.name().toLowerCase(); // e.g. "price"
        return name.substring(0, 1).toUpperCase() + name.substring(1); // → "Price"
    }
}
