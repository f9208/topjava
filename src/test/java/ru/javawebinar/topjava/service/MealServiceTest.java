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
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.testUtils.MealTestData.*;
import static ru.javawebinar.topjava.testUtils.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.testUtils.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-main.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    @Autowired
    MealService mealService;

    @Test
    public void get() {
        Meal newMeal = mealService.get(BREAKFAST_USER_ID, USER_ID);
        assertMatch(newMeal, breakfastUser);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.get(2, USER_ID));
    }

    @Test
    public void getNoOwnAdmin() {
        assertThrows(NotFoundException.class, () -> mealService.get(LUNCH_USER_ID, ADMIN_ID));
    }

    @Test
    public void getNoOwnUser() {
        assertThrows(NotFoundException.class, () -> mealService.get(SNACK_ADMIN_ID, USER_ID));
    }

    @Test
    public void getNoExistMealsByNoExistUser() {
        assertThrows(NotFoundException.class, () -> mealService.get(223, 4));
    }

    @Test
    public void getBetweenInclusiveBothNotNull() {
        List<Meal> meals = mealService.getBetweenInclusive(LocalDate.of(2020, 02, 03),
                LocalDate.of(2020, 02, 04), USER_ID);
        assertMatch(meals, dinnerUser, fiveoclockUser, lunchUser, breakfastUser, lightLunchUser, morningCoffeeUser);
    }

    @Test
    public void getBetweenInclusiveBothNull() {
        List<Meal> meals = mealService.getBetweenInclusive(null, null, USER_ID);
        assertMatch(meals, dinnerUser, fiveoclockUser, lunchUser, breakfastUser, lightLunchUser, morningCoffeeUser, newYearBrunchUser);
    }

    @Test
    public void getBetweenInclusiveEndIsNull() {
        List<Meal> meals = mealService.getBetweenInclusive(LocalDate.of(2020, 02, 04), null, USER_ID);
        assertMatch(meals, dinnerUser, fiveoclockUser, lunchUser, breakfastUser);
    }

    @Test
    public void getBetweenInclusiveStarIsNull() {
        List<Meal> meals = mealService.getBetweenInclusive(null, LocalDate.of(2015, 02, 01), USER_ID);
        assertMatch(meals, newYearBrunchUser);
    }

    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(USER_ID);
        //expected in order by date reverse
        assertMatch(meals, dinnerUser, fiveoclockUser, lunchUser, breakfastUser, lightLunchUser, morningCoffeeUser, newYearBrunchUser);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        Meal newUpdated = new Meal(updated);
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(NEW_YEAR_BRUNCH_USER_ID, USER_ID), newUpdated);
    }

    @Test
    public void updateNotOwnedByAdmin() {
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
        mealService.delete(BREAKFAST_USER_ID, USER_ID);
        assertThrows(NotFoundException.class, () ->
                mealService.get(BREAKFAST_USER_ID, USER_ID));
    }

    @Test
    public void deleteNotOwned() {
        assertThrows(NotFoundException.class, () ->
                mealService.delete(BREAKFAST_USER_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> mealService.delete(1, USER_ID));
    }

    @Test
    public void duplicateDate() {
        assertThrows(DataAccessException.class, () ->
                mealService.create(new Meal(null,
                        lunchUser.getDateTime(),
                        "bad lunchUser", 1000), USER_ID));
    }
}