package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    //map<mealId, userId>
    private final Map<Integer, Integer> userRepository = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            userRepository.put(meal.getId(), authUserId());
            return meal;
        }
        if (isBelong(meal.getId(), authUserId()))
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        else
            return null;
    }

    @Override
    public boolean delete(int id) {
        return isBelong(id, authUserId()) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return isBelong(id,authUserId()) ? repository.get(id) : null;
    }

    @Override
    public List<Meal> getAll() {
        return getAllWithinDateRange(LocalDate.MIN, LocalDate.MAX);
    }

    @Override
    public List<Meal> getAllWithinDateRange(LocalDate dataStart, LocalDate dateEnd) {
        return repository.values()
                .stream()
                .filter(meal -> isBelong(meal.getId(), authUserId()))
                .filter(meal -> DateTimeUtil.isBetweenHalfOpenDate(meal.getDate(),
                        LocalDate.from(dataStart),
                        LocalDate.from(dateEnd)))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private boolean isBelong(Integer mealId, Integer id) {
        Integer userId = userRepository.get(mealId);
        return userId != null && userId.equals(id);
    }
}

