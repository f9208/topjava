package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService mealService;
    @Autowired
    private UserService userService;

    public List<MealTo> getAll() {
        log.info("getAll");
        return mealService.getAll(userService.get(SecurityUtil.authUserId()));
    }

    public List<MealTo> getAllBetweenTime(LocalDate startDate,
                                          LocalDate endDate,
                                          LocalTime startTime,
                                          LocalTime endTime) {
        log.info("getAllBetweenTime: startData {}, endData {}, startTime {}, endTime {}",
                startDate, endDate, startTime, endTime);
        if (startDate == null) startDate = LocalDate.MIN;
        if (endDate == null) endDate = LocalDate.MAX;
        if (startTime == null) startTime = LocalTime.MIN;
        if (endTime == null) endTime = LocalTime.MAX;
        return mealService.getAllWithinRange(LocalDateTime.of(startDate, startTime),
                LocalDateTime.of(endDate, endTime), userService.get(SecurityUtil.authUserId()));
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return mealService.get(mealId, SecurityUtil.authUserId());
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        mealService.delete(mealId, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return mealService.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int mealIdByUrl) {
        log.info("update {} with id={}", meal, mealIdByUrl);
        assureIdConsistent(meal, mealIdByUrl);
        mealService.update(meal, SecurityUtil.authUserId());
    }
}