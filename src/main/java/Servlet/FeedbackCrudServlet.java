package Servlet;

import Service.FeedbackCrudService;
import org.Feedback;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/feedback")
public class FeedbackCrudServlet extends HttpServlet {
    FeedbackCrudService feedbackCrudService = new FeedbackCrudService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int requestFeedbackId = Integer.parseInt(req.getParameter("id"));
        Feedback feedback = feedbackCrudService.getFeedbackById(requestFeedbackId);
        req.setAttribute("feedback", feedback);
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/singleFeedback.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double rating = Double.parseDouble(req.getParameter("rating"));
        String comment = req.getParameter("comment");
        int toWhichUserId = Integer.parseInt(req.getParameter("toWhichUserId"));
        int fromWhichUserId = Integer.parseInt(req.getParameter("fromWhichUserId"));
        boolean result = feedbackCrudService.createFeedback(rating, comment, toWhichUserId, fromWhichUserId);
        if (result) {
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/successfully.jsp").forward(req, resp);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/unsuccessfully.jsp").forward(req, resp);
    }
}
