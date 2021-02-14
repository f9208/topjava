package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        if (repository.save(meal) == null) throw new NotFoundException("meat not found or you are not owner one");
        else return meal;
    }

    public void delete(int mealId) {
        checkNotFoundWithId(repository.delete(mealId), mealId);
    }

    public Meal get(int mealId) {
        return checkNotFoundWithId(repository.get(mealId), mealId);
    }

    public List<Meal> getAll() {
        return repository.getAll();
    }

    public List<MealTo> getAllWithinRange(LocalDateTime beginning, LocalDateTime end) {
        return MealsUtil.getFilteredTos(repository.getAllWithinDateRange(beginning.toLocalDate(),
                end.toLocalDate()), MealsUtil.DEFAULT_CALORIES_PER_DAY,
                beginning.toLocalTime(),
                end.toLocalTime());
    }
}