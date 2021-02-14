package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
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
        return service.getAllWithinRange(LocalDateTime.of(startDate, startTime),
                LocalDateTime.of(endDate, endTime));
    }

    public Meal get(int mealId) {
        log.info("get {}", mealId);
        return service.get(mealId);
    }

    public void delete(int mealId) {
        log.info("delete {}", mealId);
        service.delete(mealId);
    }

    public Meal save(Meal meal) {
        log.info("save {}", meal);
        return service.create(meal);
    }

    public Meal update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        try {
            assureIdConsistent(meal, mealId);
            return service.create(meal);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}