package com.ua07.search.strategy;

import java.util.EnumMap;
import java.util.Map;

public class SortingStrategyFactory {
    private final Map<SortType, SortingStrategy> strategies = new EnumMap<>(SortType.class);

    public SortingStrategyFactory() {
        strategies.put(SortType.PRICE, new PriceSortStrategy());
        strategies.put(SortType.POPULARITY, new PopularitySortStrategy());
        strategies.put(SortType.RECENCY, new RecencySortStrategy());
    }

    public SortingStrategy getStrategy(SortType type) {
        return strategies.getOrDefault(type, new PriceSortStrategy());
    }
}
