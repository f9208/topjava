package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealDAO;
import ru.javawebinar.topjava.util.MealDAOHandImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class AddMealServlet extends HttpServlet {
    private static final Logger log = getLogger(AddMealServlet.class);
    private MealDAO mealDAO = new MealDAOHandImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("handle GET request inside AddMealServlet");
        log.debug("forward to /addMeal.jsp");
        req.getRequestDispatcher("/addMeal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("handle POST request inside AddMealServlet");
        LocalDateTime localDateTime = mealDAO.getById(Long.valueOf(req.getParameter("update_meal_id"))).getDateTime();
        Integer caloriesValue = mealDAO.getById(Long.valueOf(req.getParameter("update_meal_id"))).getCalories();
        String eating = mealDAO.getById(
                Long.valueOf(req.getParameter("update_meal_id"))).getDescription();
        req.setAttribute("dataTime", localDateTime);
        req.setAttribute("calories", caloriesValue);
        req.setAttribute("eating", eating);
        log.debug("forward to addMeal.jsp");
        req.getRequestDispatcher("/addMeal.jsp").forward(req, resp);
    }
}