package Servlet;

import Service.AddServiceToUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addService")
public class AddServiceToUser extends HttpServlet {

    AddServiceToUserService addServiceToUserService = new AddServiceToUserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("users_id"));
        int serviceId = Integer.parseInt(req.getParameter("services_id"));
        boolean result = addServiceToUserService.add(userId, serviceId);
        if (result) {
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/successfully.jsp").forward(req,resp);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/unsuccessfully.jsp").forward(req,resp);
    }
}