package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private static ProfileRestController profileRestController = new ProfileRestController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        String userId = request.getParameter("userId");
        if (!userId.isEmpty()) {
            request.setAttribute("userId", userId);
            request.getRequestDispatcher("meals").forward(request, response);
        }
        request.getRequestDispatcher("index.html").forward(request, response);
    }
}
