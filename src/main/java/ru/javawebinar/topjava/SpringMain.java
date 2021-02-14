package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    private static final String SEPARATOR = "\n-----------------------------------";

    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mrc = appCtx.getBean(MealRestController.class);
            System.out.println(mrc.getAllBetweenTime(null, null, null, null) + SEPARATOR);
            System.out.println(mrc.getAllBetweenTime(LocalDate.of(2020, Month.JANUARY, 31), null, null, null) + SEPARATOR);
            System.out.println(mrc.getAllBetweenTime(LocalDate.of(2020, Month.JANUARY, 30), LocalDate.of(2020, Month.JANUARY, 31), null, null) + SEPARATOR);
            System.out.println(mrc.getAllBetweenTime(null, null, LocalTime.of(10, 00), null) + SEPARATOR);
            System.out.println(mrc.getAllBetweenTime(null, null, LocalTime.of(10, 00), LocalTime.of(21, 00)) + SEPARATOR);
            System.out.println(mrc.getAllBetweenTime(null, LocalDate.of(2012, Month.FEBRUARY, 20), null, null) + SEPARATOR);

            mrc.save(new Meal(2, LocalDateTime.of(2030, Month.APRIL, 2, 10, 00), "branch", 234));
            System.out.println(mrc.getAll() + SEPARATOR);
            mrc.save(new Meal(LocalDateTime.of(2030, Month.APRIL, 2, 10, 00), "branch", 3000));
            System.out.println(mrc.getAllBetweenTime(LocalDate.of(2030, Month.APRIL, 2), null, null, null) + SEPARATOR);
            mrc.update(new Meal(8, LocalDateTime.of(2130, Month.APRIL, 2, 10, 00), "light_branch", 500), 8);
            System.out.println(mrc.getAllBetweenTime(LocalDate.of(2030, Month.APRIL, 2), null, null, null) + SEPARATOR);
            System.out.println(mrc.getAll() + SEPARATOR);

            //invalid input values,
            //mrc.get(123);
            //mrc.delete(334);
            //try to update with wrong request id
            //mrc.update(new Meal(8, LocalDateTime.of(2030, Month.APRIL, 2, 10, 00), "light_branch", 500), 3);

            System.out.println(SEPARATOR);

            ProfileRestController prc = appCtx.getBean(ProfileRestController.class);
            prc.create(new User(null, "vasya", "vasiaPupkin@gmail.com", "password", Role.USER));
            prc.create(new User(null, "basia", "1asiaPupkin@gmail.com", "password", Role.USER));
            prc.create(new User(null, "basia", "3aPupkin@gmail.com", "password", Role.USER));
            prc.create(new User(null, "basia", "2aPupkin@gmail.com", "password", Role.USER));
            prc.create(new User(null, "aasia", "aPupkin@gmail.com", "password", Role.USER));
            System.out.println(prc.getAll());
        }
    }
}
