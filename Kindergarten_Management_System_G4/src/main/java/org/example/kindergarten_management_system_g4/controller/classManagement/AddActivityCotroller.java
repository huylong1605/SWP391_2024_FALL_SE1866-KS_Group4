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

@WebServlet(name = "addActivity", value = "/addActivity")
public class AddActivityCotroller extends HttpServlet {
    private ExtracurricularActivitiesDAO activityDAO;

    @Override
    public void init() throws ServletException {
        activityDAO = new ExtracurricularActivitiesDAOImpl();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy phiên làm việc (session) của người dùng
        HttpSession session = req.getSession();
        // Lấy thông tin người dùng từ session
        User user = (User) session.getAttribute("user");
        String activityName = req.getParameter("activityName");
        String description = req.getParameter("description");
        String dateString = req.getParameter("date"); // Nhận dữ liệu từ input date
        LocalDate date = LocalDate.parse(dateString); // Chuyển đổi chuỗi thành LocalDate
        String startTimeString = req.getParameter("start_time");
        LocalTime startTime = LocalTime.parse(startTimeString);
        String endTimeString = req.getParameter("end_time");
        LocalTime endTime = LocalTime.parse(endTimeString);
        String location = req.getParameter("location");
        String material =req.getParameter("material");
        try {
            ExtracurricularActivities newActivity = new ExtracurricularActivities(0,activityName,description,date,startTime,endTime,location,user.getUserID(),material);
            System.out.println(newActivity);
            activityDAO.addActivity(newActivity);
            resp.sendRedirect(req.getContextPath() + "/view-extracurricular-activities");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }

}
