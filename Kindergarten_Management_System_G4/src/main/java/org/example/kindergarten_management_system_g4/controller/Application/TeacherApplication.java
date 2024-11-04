package org.example.kindergarten_management_system_g4.controller.Application;

import org.example.kindergarten_management_system_g4.dao.ApplicationDAO.ApplicationDAO;
import org.example.kindergarten_management_system_g4.model.Application;
import org.example.kindergarten_management_system_g4.model.Classes;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/*")
public class TeacherApplication extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ApplicationDAO applicationDAO = new ApplicationDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }else if (user.getRoleId() != 2) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String action = request.getPathInfo();

        if ("/applications".equals(action)) {
            List<Classes> classes = applicationDAO.getClassesByTeacherId(user.getUserID());
            if (request.getParameter("classId") != null) {
                int classId = Integer.parseInt(request.getParameter("classId"));
                List<Application> applications = applicationDAO.getApplicationsByClass(classId);
                request.setAttribute("applications", applications);
                request.setAttribute("classIdSelected", classId);
            }
            request.setAttribute("classes", classes);
            request.getRequestDispatcher("/Views/Application/TeacherViewApplicationList.jsp").forward(request, response);
        } else if (action != null && action.startsWith("/edit-application")) {
            int applicationId = Integer.parseInt(action.split("/")[2]);
            Application application = applicationDAO.getApplicationById(applicationId);
            request.setAttribute("application", application);
            request.getRequestDispatcher("/Views/Application/TeacherEditApplication.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if ("/update-application".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String applicationContent = request.getParameter("applicationContent");
            String status = request.getParameter("status");
            String applicationResponse = request.getParameter("applicationResponse");

            Application application = new Application();
            application.setApplicationId(id);
            application.setApplicationResponse(applicationResponse);
            application.setStatus(status);
            application.setApplicationContent(applicationContent);

            boolean isUpdated = applicationDAO.updateApplication(application);
            if (isUpdated) {
                request.setAttribute("message", "Application updated successfully.");
            } else {
                request.setAttribute("message", "Failed to update application.");
            }

            Application applicationUpdated = applicationDAO.getApplicationById(id);
            request.setAttribute("application", applicationUpdated);
            request.getRequestDispatcher("/Views/Application/TeacherEditApplication.jsp").forward(request, response);
        }
    }
}
