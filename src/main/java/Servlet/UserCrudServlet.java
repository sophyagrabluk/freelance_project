package Servlet;

import Service.UserCrudService;
import org.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserCrudServlet extends HttpServlet {
    UserCrudService userCrudService = new UserCrudService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int requestUserId = Integer.parseInt(req.getParameter("id"));
        User user = userCrudService.getUserById(requestUserId);
        req.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/singleUser.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        boolean result = userCrudService.createUser(firstName, lastName, country, city, login, password);
        if (result) {
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/successfully.jsp").forward(req,resp);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/unsuccessfully.jsp").forward(req,resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        boolean result = userCrudService.createUser(firstName, lastName, country, city, login, password);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean result = userCrudService.deleteUser(id);
        if (result) {
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/successfully.jsp").forward(req,resp);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/unsuccessfully.jsp").forward(req,resp);

    }
}