package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    @Autowired
    MealService mealService;

    @Test
    public void get() {
        Meal newMeal = mealService.get(BREAKFAST_ID, USER_ID);
        assertMatch(newMeal, BREAKFAST);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(2, USER_ID));
    }

    @Test
    public void getNotOwned() {
        assertThrows(NotFoundException.class, () -> mealService.get(LUNCH_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = mealService.getBetweenInclusive(
                LocalDate.of(2020, 02, 03),
                LocalDate.of(2020, 02, 04),
                USER_ID);
        assertMatch(meals, DINNER, FIVEOCLOCK, LUNCH, BREAKFAST, LIGHT_LUNCH, MORNING_COFFEE);
        meals = mealService.getBetweenInclusive(null, null, USER_ID);
        assertMatch(meals, DINNER, FIVEOCLOCK, LUNCH, BREAKFAST, LIGHT_LUNCH, MORNING_COFFEE, NEW_YEAR_BRUNCH);
        meals = mealService.getBetweenInclusive(LocalDate.of(2020, 02, 04), null, USER_ID);
        assertMatch(meals, DINNER, FIVEOCLOCK, LUNCH, BREAKFAST);
        meals = mealService.getBetweenInclusive(null, LocalDate.of(2015, 02, 01), USER_ID);
        assertMatch(meals, NEW_YEAR_BRUNCH);
    }

    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(USER_ID);
        //expected in order by date reverse
        assertMatch(meals, DINNER, FIVEOCLOCK, LUNCH, BREAKFAST, LIGHT_LUNCH, MORNING_COFFEE, NEW_YEAR_BRUNCH);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(NEW_YEAR_BRUNCH_ID, USER_ID), updated);
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
    }

    @Test
    public void delete() {
        mealService.delete(BREAKFAST_ID, USER_ID);
        assertThrows(NotFoundException.class, () ->
                mealService.get(BREAKFAST_ID, USER_ID));
    }

    @Test
    public void deleteNotOwned() {
        assertThrows(NotFoundException.class, () ->
                mealService.delete(BREAKFAST_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(1, USER_ID));
    }

    @Test
    public void duplicateDate() {
        assertThrows(DataAccessException.class, () ->
                mealService.create(new Meal(null,
                        LocalDateTime.of(2020, 02, 04, 12, 00, 00),
                        "bad lunch", 1000), USER_ID));
    }
}