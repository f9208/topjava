package ru.javawebinar.topjava.Storage;

import ru.javawebinar.topjava.model.Meal;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMealDao implements MealDao {
    private static Map<Long, Meal> meals = new ConcurrentHashMap<>();
    private static volatile AtomicLong counter = new AtomicLong(0);

    static {
        new InMemoryMealDao().add(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        new InMemoryMealDao().add(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        new InMemoryMealDao().add(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        new InMemoryMealDao().add(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        new InMemoryMealDao().add(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        new InMemoryMealDao().add(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        new InMemoryMealDao().add(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    }

    @Override
    public Meal add(LocalDateTime localDateTime, String description, int calories) {
        long currentCounterValue = incrementCounter();
        return meals.put(currentCounterValue, new Meal(currentCounterValue, localDateTime, description, calories));
    }

    @Override
    public Meal deleteById(long id) {
        return meals.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getById(long id) {
        return meals.get(id);
    }

    @Override
    public Meal update(long id, Meal inputMeal) {
        meals.remove(id);
        return meals.put(id, inputMeal);
    }

    private static Long incrementCounter() {
        return counter.incrementAndGet();
    }
}
