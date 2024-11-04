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
import java.util.List;

@WebServlet("/view-applications")
public class ParentApplicationListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ApplicationDAO applicationDAO = new ApplicationDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        int userId = (user.getUserID());
        List<Application> applications = applicationDAO.getApplicationsByUserId(userId);

        request.setAttribute("applications", applications);
        request.getRequestDispatcher("/Views/Application/ParentApplicationList.jsp").forward(request, response);
    }
}
