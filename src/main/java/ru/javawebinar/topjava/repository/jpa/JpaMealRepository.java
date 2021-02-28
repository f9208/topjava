package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        User refUser = em.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(refUser);
            em.persist(meal);
        } else {
            Meal ownerMeal = em.getReference(Meal.class, meal.getId());
            if (ownerMeal.getUser().getId() != userId) {
                return null;
            }
            meal.setUser(refUser);
            em.merge(meal);
        }
        return meal;
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE).setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result = em.find(Meal.class, id);
        if (result == null) {
            return null;
        } else
            return result.getUser().getId() != userId ? null : result;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GETALL, Meal.class).setParameter("user_id", userId).getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("id", userId)
                .setParameter("start_time", startDateTime)
                .setParameter("end_time", endDateTime).getResultList();
    }
}