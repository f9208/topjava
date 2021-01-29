package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        System.out.println("Use Cycles:");
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(23, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println("Use Streams:");
        mealsTo = filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
                                                            LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> resultList = new ArrayList<>();
        Map<LocalDate, Integer> mapEatenCaloriesPerDay = new HashMap<>();

        for (UserMeal meal : meals) {
            int sum = mapEatenCaloriesPerDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) + meal.getCalories();
            mapEatenCaloriesPerDay.put(meal.getDateTime().toLocalDate(), sum);
        }
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean isExcess = mapEatenCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
                resultList.add(new UserMealWithExcess(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        isExcess));
            }
        }
        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime,
                                                             LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapEatenCaloriesPerDay = meals.stream()
                .collect(Collectors
                        .toMap(key -> key.getDateTime().toLocalDate(),
                                UserMeal::getCalories,
                                Integer::sum));
        List<UserMealWithExcess> resultList = meals.stream()
                .filter(value -> TimeUtil.isBetweenHalfOpen(value.getDateTime().toLocalTime(), startTime, endTime))
                .map(value -> new UserMealWithExcess(value.getDateTime(),
                        value.getDescription(),
                        value.getCalories(),
                        mapEatenCaloriesPerDay.get(value.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        return resultList;
    }
}
