package org.example.kindergarten_management_system_g4.controller.AttendanceController;

import org.example.kindergarten_management_system_g4.dao.AttendanceDAO.AttendanceDAO;
import org.example.kindergarten_management_system_g4.dao.AttendanceDAO.IAttendanceDAO;
import org.example.kindergarten_management_system_g4.model.AttendanceRecord;
import org.example.kindergarten_management_system_g4.model.StudentAttendance;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet( value = {"/Views/Teacher/attendStudent", "/Views/Teacher/listAttendanceClass" })
public class AttendanceController extends HttpServlet {
    private IAttendanceDAO attendanceDAO;

    @Override
    public void init() {
        attendanceDAO = new AttendanceDAO(); // Khởi tạo AttendanceDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/Views/Teacher/attendStudent")) {
            handleAttendStudent(request, response);
        } else if (path.equals("/Views/Teacher/listAttendanceClass")) {
            handleListAttendanceClass(request, response);
        }
    }

    private void handleAttendStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ request
        int classId = Integer.parseInt(request.getParameter("classId"));
        String date = request.getParameter("date");
        int slotId = Integer.parseInt(request.getParameter("slotId"));
        String className = request.getParameter("className");
        String slotName = request.getParameter("slotName");

        // Lấy danh sách điểm danh học sinh
        List<StudentAttendance> attendanceList = attendanceDAO.getStudentAttendance(classId, date, slotId);

        // Đặt danh sách vào request
        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("classId", classId);
        request.setAttribute("date", date);
        request.setAttribute("slotId", slotId);
        request.setAttribute("className", className);
        request.setAttribute("slotName", slotName);

        // Chuyển hướng tới trang JSP để hiển thị
        request.getRequestDispatcher("/Views/Teacher/attendStudent.jsp").forward(request, response);
    }

    private void handleListAttendanceClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ request
        int classId = Integer.parseInt(request.getParameter("classId"));

        // Lấy danh sách điểm danh tổng hợp cho lớp
        List<AttendanceRecord> summaryList = attendanceDAO.getAttendanceSummary(classId);

        // Đặt danh sách vào request
        request.setAttribute("summaryList", summaryList);
        request.setAttribute("classId", classId);

        // Chuyển hướng tới trang ListAttendanceOfClass.jsp để hiển thị
        request.getRequestDispatcher("/Views/Teacher/listAttendanceClass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int classId = Integer.parseInt(request.getParameter("classId"));
        String date = request.getParameter("date");
        int slotId = Integer.parseInt(request.getParameter("slotId"));
        String className = request.getParameter("className");
        String slotName = request.getParameter("slotName");

        // Nhận thông tin studentIds và attendStatuses
        String[] studentIds = request.getParameterValues("studentId");
        String[] attendStatuses = request.getParameterValues("attendStatus");

        // Kiểm tra xem studentIds có phải là null không
        if (studentIds == null || attendStatuses == null) {
            // Xử lý lỗi ở đây (ví dụ: ghi log, thông báo cho người dùng)
            response.sendRedirect("errorPage.jsp"); // Chuyển hướng tới trang lỗi hoặc thực hiện thao tác khác
            return;
        }
        List<StudentAttendance> attendanceList = new ArrayList<>();
        for (int i = 0; i < studentIds.length; i++) {
            int studentId = Integer.parseInt(studentIds[i]);
            boolean attendStatus = Boolean.parseBoolean(attendStatuses[i]);
            attendanceList.add(new StudentAttendance(studentId, null, null, attendStatus));
        }
        // Lưu điểm danh
        attendanceDAO.saveAttendance(classId, date, slotId, attendanceList);
        // Chuyển hướng về trang hiển thị danh sách điểm danh
        request.getSession().setAttribute("successMessage", "Attendance saved successfully!");
        response.sendRedirect(request.getContextPath() + "/Views/Teacher/attendStudent?classId=" + classId + "&date=" + date + "&slotId=" + slotId + "&className=" + className + "&slotName=" + slotName);
    }

}
