package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return ValidationUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public void delete(int mealId, int userId) {
        checkNotFoundWithId(repository.delete(mealId, userId), mealId);
    }

    public Meal get(int mealId, int userId) {
        return checkNotFoundWithId(repository.get(mealId, userId), mealId);
    }

    public List<MealTo> getAll(User user) {
        return MealsUtil.getTos(repository.getAll(user.getId()), user.getCaloriesPerDay());
    }

    public List<MealTo> getAllWithinRange(LocalDateTime beginning, LocalDateTime end, User user) {
        return MealsUtil.getFilteredTos(repository.getAllWithinDateRange(beginning.toLocalDate(),
                end.toLocalDate(), user.getId()), user.getCaloriesPerDay(),
                beginning.toLocalTime(),
                end.toLocalTime());
    }

    public void update(Meal meal, int userId) {
        repository.save(meal, userId);
    }
}