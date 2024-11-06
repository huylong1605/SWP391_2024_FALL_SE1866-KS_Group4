package org.example.kindergarten_management_system_g4.controller.teacherScheduleManagement;

import org.example.kindergarten_management_system_g4.dao.TeacherScheduleDAO.TeachingScheduleDAO;
import org.example.kindergarten_management_system_g4.model.TeacherSchedule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        int teacherId = Integer.parseInt(req.getParameter("teacherId"));
        HttpSession session = req.getSession();

        String changeSlotSuccessful = (String) session.getAttribute("changeSlotSuccessful");

        List<TeacherSchedule> teachingSchedules = teachingScheduleDAO.getTeachingSchedules(teacherId);

        // Lấy ngày hiện tại
        java.util.Date currentDate = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String currentDateString = sdf.format(currentDate);

        // Chuyển đổi danh sách lịch dạy để thêm thông tin "có thể điểm danh hay không"
        for (TeacherSchedule schedule : teachingSchedules) {
            boolean canAttend = schedule.getDate().compareTo(currentDateString) <= 0;
            schedule.setCanAttend(canAttend); // Thêm trường "canAttend" vào model nếu cần
        }
        req.setAttribute("currentDate", currentDateString);
        req.setAttribute("teachingSchedules", teachingSchedules);
        req.setAttribute("changeSlotSuccessful", changeSlotSuccessful);
        req.getRequestDispatcher("/Views/Teacher/teacherSchedule.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
