package org.example.kindergarten_management_system_g4.controller.teacherScheduleManagement;

import org.example.kindergarten_management_system_g4.dao.TeacherScheduleDAO.TeachingScheduleDAO;
import org.example.kindergarten_management_system_g4.model.TeacherSchedule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(value = "/Views/Teacher/teacherSchedule")
public class TeacherScheduleController extends HttpServlet {

    private TeachingScheduleDAO teachingScheduleDAO;

    public TeacherScheduleController() {
        super();
        this.teachingScheduleDAO = new TeachingScheduleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy teacherId từ tham số yêu cầu
        int teacherId = Integer.parseInt(req.getParameter("teacherId"));

        // Lấy danh sách lịch dạy của giáo viên
        List<TeacherSchedule> teachingSchedules = teachingScheduleDAO.getTeachingSchedules(teacherId);

        // Đặt danh sách lịch dạy vào thuộc tính request để hiển thị trên JSP
        req.setAttribute("teachingSchedules", teachingSchedules);

        // Chuyển hướng đến trang JSP để hiển thị lịch dạy
        req.getRequestDispatcher("/Views/Teacher/teacherSchedule.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
