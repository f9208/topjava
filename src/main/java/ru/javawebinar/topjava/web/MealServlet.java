package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.CountGenerator;
import ru.javawebinar.topjava.util.MealStorageService;
import ru.javawebinar.topjava.util.MealsStorageServiceHardImp;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorageService mealStorageService = new MealsStorageServiceHardImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        log.debug(req.getParameter("delete"));
        List<MealTo> listMealTo = MealsUtil.filteredByStreams(mealStorageService.getAll(), LocalTime.MIN, LocalTime.MAX, mealStorageService.getCaloriesLimit());
        req.setAttribute("list", listMealTo);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("handled POST");
        log.debug(req.getParameter("date"));
        mealStorageService.add(
                new Meal(CountGenerator.incrementCounter(),
                        timeHandler(req.getParameter("date")),
                        req.getParameter("description"),
                        Integer.valueOf(req.getParameter("calories"))));
        resp.sendRedirect("meals");
    }

    private LocalDateTime timeHandler(String inputString) {
        System.out.println(inputString);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-dd'T'HH:mm");
        return LocalDateTime.parse(inputString, dateTimeFormatter);
    }
}
