package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    //map<userId, List<mealId>
    private final Map<Integer, List<Integer>> userRepository = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            userRepository.computeIfAbsent(userId, k -> new ArrayList<>());
            userRepository.get(userId).add(meal.getId());

            return meal;
        }
        if (isBelong(meal.getId(), userId))
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        else
            return null;
    }

    @Override
    public boolean delete(int mealId, int userId) {
        return isBelong(mealId, userId) && repository.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        return isBelong(mealId, userId) ? repository.get(mealId) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filter(LocalDate.MIN, LocalDate.MAX, userId);
    }

    @Override
    public List<Meal> getAllWithinDateRange(LocalDate dataStart, LocalDate dateEnd, int userId) {
        return filter(dataStart, dateEnd, userId);
    }

    private boolean isBelong(Integer mealId, Integer userId) {
        if (userRepository.get(userId) == null) return false;
        else return userRepository.get(userId).contains(mealId);
    }

    private List<Meal> filter(LocalDate start, LocalDate end, int userId) {
        return repository.values()
                .stream()
                .filter(meal -> isBelong(meal.getId(), userId))
                .filter(meal -> DateTimeUtil.isBetweenHalfOpenDate(meal.getDate(),
                        start, end))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

