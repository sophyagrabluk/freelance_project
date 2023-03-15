package com.tms.servlet;

import com.tms.service.ServiceCrudService;
import com.tms.domain.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/service")
public class ServiceCrudServlet extends HttpServlet {
    ServiceCrudService serviceCrudService = new ServiceCrudService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int requestServiceId = Integer.parseInt(req.getParameter("id"));
        Service service = serviceCrudService.getServiceById(requestServiceId);
        req.setAttribute("service", service);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/singleService.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String section = req.getParameter("section");
        String description = req.getParameter("description");
        boolean result = serviceCrudService.createService(name, section, description);
        if (result) {
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/successfully.jsp").forward(req,resp);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/unsuccessfully.jsp").forward(req,resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String section = req.getParameter("section");
        String description = req.getParameter("description");
        boolean result = serviceCrudService.createService(name, section, description);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        serviceCrudService.deleteService(id);
    }
}
