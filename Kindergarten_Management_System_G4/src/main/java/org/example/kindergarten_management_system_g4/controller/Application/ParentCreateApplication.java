package org.example.kindergarten_management_system_g4.controller.Application;

import org.example.kindergarten_management_system_g4.dao.ApplicationDAO.ApplicationDAO;
import org.example.kindergarten_management_system_g4.model.Application;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(name = "send-application", value = "/send-application")
public class ParentCreateApplication extends HttpServlet {

    private ApplicationDAO applicationDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        applicationDAO = new ApplicationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }else if (user.getRoleId() != 3) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.getRequestDispatcher("/Views/Application/ParentApplication.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String applicationContent = req.getParameter("applicationContent");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        int userId = (user.getUserID()); // Assuming userId is passed from the request
        Date dateCreate = new Date(); // Set current date as creation date

        Application application = new Application();
        application.setApplicationContent(applicationContent);
        application.setUserId(userId);
        application.setDateCreate(dateCreate);

        boolean isCreated = false;
        try {
            isCreated = applicationDAO.createApplication(application);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (isCreated) {
            req.setAttribute("message", "Application submitted successfully.");
        } else {
            req.setAttribute("message", "Failed to submit application.");
        }

        req.getRequestDispatcher("/Views/Application/ParentApplication.jsp").forward(req, resp);
    }
}
