package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Map;

public interface MealDAO {
    Map<Long, Meal> getAllAsMap();

    int getCaloriesLimit();

    boolean add(Meal meal);

    boolean deleteById(long id);

    List<Meal> getAll();

    Meal getById(Long id);
}