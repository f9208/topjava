package ru.javawebinar.topjava.Storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDao {

    Meal add(LocalDateTime dateTime, String description, int calories);

    Meal deleteById(long id);

    List<Meal> getAll();

    Meal getById(long id);

    Meal update(long id, Meal meal);
}
