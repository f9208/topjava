package ru.javawebinar.topjava.Storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.CountGenerator;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMealDao implements MealDao {
    private static Map<Long, Meal> meals = new ConcurrentHashMap<>();

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
    public Meal add(Meal inputMeal) {
        return meals.put(CountGenerator.getCurrentValue(), inputMeal);
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
}
