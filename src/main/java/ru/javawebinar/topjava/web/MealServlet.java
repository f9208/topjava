package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.CountGenerator;
import ru.javawebinar.topjava.util.MealDAO;
import ru.javawebinar.topjava.util.MealDAOHandImp;
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
    private MealDAO mealDAO = new MealDAOHandImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("handle GET request inside MealServlet");
        if (req.getParameter("update_meal") != null) {
            log.debug("forward to addMeal");
            req.getRequestDispatcher("addMeal").forward(req, resp);
        }
        if (req.getParameter("id") != null && req.getParameter("update_meal") != null) {
            log.debug("forward to addMeal");
            req.getRequestDispatcher("addMeal").forward(req, resp);
        }
        List<MealTo> listMealTo = MealsUtil.filteredByStreams(mealDAO.getAll(), LocalTime.MIN, LocalTime.MAX, mealDAO.getCaloriesLimit());
        req.setAttribute("list", listMealTo);
        log.debug("forward to meals.jsp");
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("handle POST request inside MealServlet");
        if (req.getParameter("saveButton") != null) {
            log.debug("add new \"Meal\" to DB");
            mealDAO.add(new Meal(CountGenerator.incrementCounter(),
                    dateTimeHandler(req.getParameter("date")),
                    req.getParameter("description"),
                    Integer.valueOf(req.getParameter("calories"))));
            log.debug("redirect to GET");
            resp.sendRedirect("meals");
        }
        if (req.getParameter("delete") != null && req.getParameter("delete_meal_id") != null) {
            log.debug("delete entry from DB with id=" + req.getParameter("delete_meal_id"));
            mealDAO.deleteById(Long.valueOf(req.getParameter("delete_meal_id")));
            resp.sendRedirect("meals");
        }
        if (req.getParameter("update_meal") != null && req.getParameter("update_meal_id") != null) {
            log.debug("update meal: forward to addMeal");
            req.getRequestDispatcher("addMeal").forward(req, resp);
        }
        if (req.getParameter("id_value") != null && !req.getParameter("id_value").equals("")) {
            log.debug("delete entry_id after update");
            mealDAO.deleteById(Long.valueOf(req.getParameter("id_value")));
        }
    }

    private LocalDateTime dateTimeHandler(String inputString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-dd'T'HH:mm");
        return LocalDateTime.parse(inputString, dateTimeFormatter);
    }
}