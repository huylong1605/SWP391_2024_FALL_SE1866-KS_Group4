package org.example.kindergarten_management_system_g4.controller.classManagement;


import org.example.kindergarten_management_system_g4.dao.ActivityDAO.ExtracurricularActivitiesDAO;
import org.example.kindergarten_management_system_g4.dao.ActivityDAO.implement.ExtracurricularActivitiesDAOImpl;
import org.example.kindergarten_management_system_g4.model.ExtracurricularActivities;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet(name = "extracurricularActivities", value = "/view-extracurricular-activities")
public class ExtracurricularActivitiesController extends HttpServlet {
    private ExtracurricularActivitiesDAO activityDAO;

    @Override
    public void init() throws ServletException {
        activityDAO = new ExtracurricularActivitiesDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<ExtracurricularActivities> activities = activityDAO.getAllActivities();
            req.setAttribute("activityList", activities);
            req.getRequestDispatcher("/Views/Admin/viewActivity.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }



}
