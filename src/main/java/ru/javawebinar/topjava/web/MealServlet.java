package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.storage.InMemoryMealDao;
import ru.javawebinar.topjava.storage.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
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
    private MealDao mealDao = new InMemoryMealDao();
    private static final int CALORIES_LIMIT = 2000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("handle GET request");
        String action = req.getParameter("action");
        if (action != null && action.equalsIgnoreCase("delete")) {
            int deleteId = Integer.parseInt(req.getParameter("mealId"));
            mealDao.deleteById(deleteId);
        }

        if (action != null && action.equalsIgnoreCase("addMeal")) {
            req.setAttribute("action", "Add");
            log.debug("forward to addMeal.jsp for add");
            req.getRequestDispatcher("/addMeal.jsp").forward(req, resp);
        }

        if (action != null && action.equalsIgnoreCase("edit")) {
            int edit = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = mealDao.getById(edit);
            req.setAttribute("action", "Edit");
            req.setAttribute("meal", meal);
            log.debug("forward to addMeal.jsp for update");
            req.getRequestDispatcher("/addMeal.jsp").forward(req, resp);
        }
        List<MealTo> listMealTo = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_LIMIT);
        req.setAttribute("list", listMealTo);
        log.debug("forward to meals.jsp");
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("handle Post");
        req.setCharacterEncoding("UTF-8");
        LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("date"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        String id = req.getParameter("mealId");
        if (id == null || id.isEmpty()) {
            mealDao.add(localDateTime,
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories")));
            resp.sendRedirect("meals");
        } else {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = new Meal(mealId,
                    localDateTime,
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("calories")));
            mealDao.update(mealId, meal);
            resp.sendRedirect("meals");
        }
    }
}