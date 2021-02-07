package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealsStorageServiceHardImp implements MealStorageService {
    static Map<Long, Meal> meals = new ConcurrentHashMap<>();
    static final int caloriesLimit = 2000;

    static {
        meals.put(CountGenerator.incrementCounter(), new Meal(CountGenerator.getCurrentValue(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(CountGenerator.incrementCounter(), new Meal(CountGenerator.getCurrentValue(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(CountGenerator.incrementCounter(), new Meal(CountGenerator.getCurrentValue(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(CountGenerator.incrementCounter(), new Meal(CountGenerator.getCurrentValue(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(CountGenerator.incrementCounter(), new Meal(CountGenerator.getCurrentValue(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(CountGenerator.incrementCounter(), new Meal(CountGenerator.getCurrentValue(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(CountGenerator.incrementCounter(), new Meal(CountGenerator.getCurrentValue(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Map<Long, Meal> getAllAsMap() {
        return meals;
    }

    @Override
    public int getCaloriesLimit() {
        return caloriesLimit;
    }

    @Override
    public boolean add(Meal inputMeal) {
        meals.put(CountGenerator.getCurrentValue(), inputMeal);
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
