package ru.javawebinar.topjava.testUtils;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int BREAKFAST_USER_ID = START_SEQ + 2;
    public static final int LUNCH_USER_ID = BREAKFAST_USER_ID + 1;
    public static final int FIVEOCLOCK_USER_ID = LUNCH_USER_ID + 1;
    public static final int DINNER_USER_ID = FIVEOCLOCK_USER_ID + 1;
    public static final int MORNING_COFFEE_USER_ID = DINNER_USER_ID + 1;
    public static final int LIGHT_LUNCH_USER_ID = MORNING_COFFEE_USER_ID + 1;
    public static final int NEW_YEAR_BRUNCH_USER_ID = 100014;
    public static final int SNACK_ADMIN_ID =100011;

    public static final Meal breakfastUser = new Meal(BREAKFAST_USER_ID, LocalDateTime.of(2020, 2, 4, 7, 0, 0), "breakfast", 2000);
    public static final Meal lunchUser = new Meal(LUNCH_USER_ID, LocalDateTime.of(2020, 2, 4, 12, 0, 0), "lunch", 1000);
    public static final Meal fiveoclockUser = new Meal(FIVEOCLOCK_USER_ID, LocalDateTime.of(2020, 2, 4, 17, 0, 0), "five-oclock", 300);
    public static final Meal dinnerUser = new Meal(DINNER_USER_ID, LocalDateTime.of(2020, 2, 4, 20, 0, 0), "dinner", 1200);
    public static final Meal morningCoffeeUser = new Meal(MORNING_COFFEE_USER_ID, LocalDateTime.of(2020, 2, 3, 6, 0, 0), "morning coffee", 500);
    public static final Meal lightLunchUser = new Meal(LIGHT_LUNCH_USER_ID, LocalDateTime.of(2020, 2, 3, 12, 0, 0), "light lunch", 800);
    public static final Meal newYearBrunchUser = new Meal(NEW_YEAR_BRUNCH_USER_ID, LocalDateTime.of(2012, 1, 1, 13, 0, 0), "new year brunch", 300);
    public static final Meal snackAdmin = new Meal(SNACK_ADMIN_ID, LocalDateTime.of(2020, 2, 2, 15, 0, 0), "snack", 200);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2010, 7, 7, 12, 0, 0), "newMeal", 2000);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static Meal getUpdated() {
        Meal result = new Meal(newYearBrunchUser);
        result.setCalories(123);
        result.setDateTime(LocalDateTime.of(2030, 9, 12, 12, 0, 0));
        result.setDescription("holiday lunchUser");
        return result;
    }

}
