package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudMealRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository,
                                 CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = new User();
        user.setId(userId);
        meal.setUser(user);
        if (!meal.isNew() && crudMealRepository.findByIdAndUserId(meal.getId(), userId) == null) {
            return null;
        }
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudMealRepository.findById(id).orElse(null);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getAllByUserId(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
    }
}
