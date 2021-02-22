package ru.javawebinar.topjava.testUtils;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public final static int BREAKFAST_ID = START_SEQ + 2;
    public final static int LUNCH_ID = BREAKFAST_ID + 1;
    public final static int FIVEOCLOCK_ID = LUNCH_ID + 1;
    public final static int DINNER_ID = FIVEOCLOCK_ID + 1;
    public final static int MORNING_COFFEE_ID = DINNER_ID + 1;
    public final static int LIGHT_LUNCH_ID = MORNING_COFFEE_ID + 1;
    public final static int NEW_YEAR_BRUNCH_ID = 100014;

    public final static Meal BREAKFAST =
            new Meal(BREAKFAST_ID, LocalDateTime.of(2020, 02, 04, 07, 00, 00),
                    "breakfast", 2000);
    public final static Meal LUNCH =
            new Meal(LUNCH_ID, LocalDateTime.of(2020, 02, 04, 12, 00, 00),
                    "lunch", 1000);
    public final static Meal FIVEOCLOCK =
            new Meal(FIVEOCLOCK_ID, LocalDateTime.of(2020, 02, 04, 17, 00, 00),
                    "five-oclock", 300);
    public final static Meal DINNER =
            new Meal(DINNER_ID, LocalDateTime.of(2020, 02, 04, 20, 00, 00),
                    "dinner", 1200);
    public final static Meal MORNING_COFFEE = new Meal(MORNING_COFFEE_ID, LocalDateTime.of(2020, 02, 03, 06, 00, 00),
            "morning coffee", 500);
    public final static Meal LIGHT_LUNCH = new Meal(LIGHT_LUNCH_ID, LocalDateTime.of(2020, 02, 03, 12, 00, 00),
            "light lunch", 800);
    public final static Meal NEW_YEAR_BRUNCH = new Meal(NEW_YEAR_BRUNCH_ID, LocalDateTime.of(2012, 01, 01, 13, 00, 00),
            "new year brunch", 300);

    public final static int USER_ID = START_SEQ;
    public final static int ADMIN_ID = START_SEQ + 1;


    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2010,7,7,12,0,0), "newMeal", 2000);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
    public static void assertMatchIgnoreId(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static Meal getUpdated() {
        Meal result = new Meal(NEW_YEAR_BRUNCH);
        result.setCalories(123);
        result.setDateTime(LocalDateTime.of(2030, 9, 12, 12, 00, 00));
        result.setDescription("holiday lunch");
        return result;
    }

}
