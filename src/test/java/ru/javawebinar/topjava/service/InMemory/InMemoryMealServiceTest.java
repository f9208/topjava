package ru.javawebinar.topjava.service.InMemory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static ru.javawebinar.topjava.testUtils.MealTestData.*;
import static ru.javawebinar.topjava.testUtils.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring-app-test-in-memory.xml"
})
@RunWith(SpringRunner.class)
public class InMemoryMealServiceTest {
    @Autowired
    MealService mealService;

    @Test
    public void get() {
        Meal newMeal = mealService.get(START_SEQ + 1, USER_ID);
        assertMatch(newMeal, MealsUtil.meals.get(0));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(2, USER_ID));
    }

    @Test
    public void getNotOwned() {
        assertThrows(NotFoundException.class, () -> mealService.get(START_SEQ + 4, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> mealsActual = new ArrayList<>(MealsUtil.meals);
        Collections.reverse(mealsActual);

        List<Meal> meals = mealService.getBetweenInclusive(LocalDate.of(2020, 01, 30),
                LocalDate.of(2020, 01, 31), USER_ID);
        assertMatch(meals, mealsActual);

        meals = mealService.getBetweenInclusive(null, null, USER_ID);
        assertMatch(meals, mealsActual);

        meals = mealService.getBetweenInclusive(LocalDate.of(2020, 01, 31), null, USER_ID);
        assertMatch(meals, mealsActual.subList(0, 4));

        meals = mealService.getBetweenInclusive(null, LocalDate.of(2020, 01, 30), USER_ID);
        assertMatch(meals, mealsActual.subList(4, mealsActual.size()));
    }

    @Test
    public void getAllForAdmin() {
        List<Meal> expected = mealService.getAll(ADMIN_ID);
        List<Meal> actual = new ArrayList<>();
        actual.add(new Meal(LocalDateTime.of(2000, 01, 01, 10, 00, 00),
                "someday", 123));
        assertMatch(actual, expected);
    }

    @Test
    public void getAll() {
        List<Meal> expected = mealService.getAll(USER_ID);
        List<Meal> actual = new ArrayList<>(MealsUtil.meals);
        //actual in order by date reverse
        Collections.reverse(actual);
        assertMatch(actual, expected);
    }

    @Test
    public void update() {
        Meal updated = new Meal(mealService.get(START_SEQ + 2, USER_ID));
        updated.setCalories(123);
        updated.setDateTime(LocalDateTime.of(2030, 9, 12, 12, 00, 00));
        updated.setDescription("holiday");
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(START_SEQ + 2, USER_ID), updated);
    }

    @Test
    public void updateNotOwned() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> mealService.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = mealService.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(mealService.get(newId, USER_ID), newMeal);
        mealService.delete(created.getId(), USER_ID);
    }

    @Test
    public void delete() {
        Meal bufferMeal = new Meal(mealService.get(START_SEQ + 4, USER_ID));

        mealService.delete(START_SEQ + 4, USER_ID);
        assertThrows(NotFoundException.class, () ->
                mealService.get(START_SEQ + 4, USER_ID));

        bufferMeal.setId(null);
        mealService.create(bufferMeal, USER_ID);
        //checking out just for case
        assertMatchIgnoreId(bufferMeal,
                mealService.get(bufferMeal.getId(), USER_ID));
    }

    @Test
    public void deleteNotOwned() {
        assertThrows(NotFoundException.class, () ->
                mealService.delete(START_SEQ + 3, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(1, USER_ID));
    }
}