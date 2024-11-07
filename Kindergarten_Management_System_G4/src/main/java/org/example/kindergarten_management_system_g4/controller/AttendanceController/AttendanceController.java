

/*
 * Copyright(C) 2005,  <SWP_G4>.
 * <KMS> :
 *  <Kindergarten Management System>
 *
 * Record of change:
 * DATE                       Version             AUTHOR                       DESCRIPTION
 * <11/3/2024>                 <1.1>           <Vu Viet Chuc>            <Update viewDetailAttendOfChild method>
 */


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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * The AttendanceController class handles HTTP requests related to attendance operations
 * for teachers and parents in the kindergarten management system. It extends HttpServlet
 * and processes requests for viewing, exporting, and sending notifications about student
 * attendance. The servlet is mapped to multiple URL patterns to handle various functions.
 */
@WebServlet( value = {"/Views/Teacher/attendStudent",
        "/Views/Teacher/listAttendanceClass",
        "/Views/Teacher/detailAttendance",
        "/Views/Teacher/exportAttendance",
        "/Views/Teacher/exportDetailAttendance",
        "/Views/Teacher/sendAbsenceNotifications",
        "/Views/Parent/viewChildAttendance"
})
public class AttendanceController extends HttpServlet {
    private IAttendanceDAO attendanceDAO;


    @Override
    public void init() {
        attendanceDAO = new AttendanceDAO(); // Khởi tạo AttendanceDAO
    }

    /**
     * Handles GET requests for various attendance-related operations based on the request path.
     *
     * @param request  The HttpServletRequest object that contains the request the client made.
     * @param response The HttpServletResponse object that contains the response the servlet returns.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/Views/Teacher/attendStudent":
                handleAttendStudent(request, response);
                break;
            case "/Views/Teacher/listAttendanceClass":
                handleListAttendanceClass(request, response);
                break;
            case "/Views/Teacher/detailAttendance":
                handleDetailAttendance(request, response);
                break;
            case "/Views/Teacher/exportAttendance":
                exportAttendance(request, response);
                break;
            case "/Views/Teacher/exportDetailAttendance":
                exportDetailAttendance(request, response);
                break;
            case "/Views/Teacher/sendAbsenceNotifications":
                handleSendAbsenceNotifications(request, response); // Thêm xử lý cho chức năng gửi thông báo
                break;
            case "/Views/Parent/viewChildAttendance":
                viewDetailAttendOfChild(request, response); // Thêm xử lý cho chức năng gửi thông báo
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // Không tìm thấy
                break;
        }
    }

    /**
     * Handles viewing detailed attendance for a child by parents.
     *
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    private void viewDetailAttendOfChild(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));

            // Lấy thông tin chi tiết điểm danh cho học sinh
            List<AttendanceRecord> attendanceDetailsOfChild = attendanceDAO.getChildDetailAttendance(userId);
            AttendanceRecord totalAttendanceOfChild = attendanceDAO.getChildTotalAttendance(userId);

            // Đặt thông tin vào request
            request.setAttribute("attendanceDetails", attendanceDetailsOfChild);
            request.setAttribute("totalAttendance", totalAttendanceOfChild);
            request.setAttribute("userId", userId);
            // Chuyển hướng tới trang detailAttendance.jsp để hiển thị
            request.getRequestDispatcher("/Views/Parent/viewChildAttendance.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid input data.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    /**
     * Handles sending absence notifications to parents for a specific class and time slot.
     *
     * @param request  The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    private void handleSendAbsenceNotifications(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int classId = Integer.parseInt(request.getParameter("classId"));
            String date = request.getParameter("date");
            int slotId = Integer.parseInt(request.getParameter("slotId"));
            String className = request.getParameter("className");
            String slotName = request.getParameter("slotName");

            // Kiểm tra xem điểm danh đã được lưu chưa
            if (!attendanceDAO.isAttendanceSaved(classId, date, slotId)) {
                // Nếu chưa lưu, đặt thông báo lỗi
                request.getSession().setAttribute("errorMessage", "Please save attendance before sending notification.");
                response.sendRedirect(request.getContextPath() + "/Views/Teacher/attendStudent?classId=" + classId
                        + "&date=" + date + "&slotId=" + slotId + "&className=" + className + "&slotName=" + slotName);
                return;
            }
            // Nếu điểm danh đã lưu, tiếp tục gửi thông báo
            attendanceDAO.sendAbsenceNotifications(classId, date, slotId);
            request.getSession().setAttribute("successMessage", "Send notification successfully!");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Error!!.");
        }

        response.sendRedirect(request.getContextPath() + "/Views/Teacher/attendStudent?classId=" + request.getParameter("classId")
                + "&date=" + request.getParameter("date") + "&slotId=" + request.getParameter("slotId")+ "&className=" + request.getParameter("className")+ "&slotName=" + request.getParameter("slotName"));
    }

    /**
     * Handles the process of displaying the attendance list for a specific class, date, and time slot.
     * Retrieves attendance data, checks if the attendance is marked, and forwards the data to a JSP for rendering.
     *
     * @param request  The HttpServletRequest object containing client request parameters.
     * @param response The HttpServletResponse object for sending the response to the client.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    private void handleAttendStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ request
        int classId = Integer.parseInt(request.getParameter("classId"));
        String date = request.getParameter("date");
        int slotId = Integer.parseInt(request.getParameter("slotId"));
        String className = request.getParameter("className");
        String slotName = request.getParameter("slotName");

        // Lấy danh sách điểm danh học sinh
        List<StudentAttendance> attendanceList = attendanceDAO.getStudentAttendance(classId, date, slotId);
        if (attendanceDAO.isAttendanceSaved(classId, date, slotId)) {
            attendanceDAO.markAttendance(classId, date, slotId);
        }
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


    /**
     * Handles the retrieval and display of an attendance summary for a specified class.
     * Fetches attendance summary data and forwards it to a JSP for display.
     *
     * @param request  The HttpServletRequest object containing client request parameters.
     * @param response The HttpServletResponse object for sending the response to the client.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    private void handleListAttendanceClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ request
        int classId = Integer.parseInt(request.getParameter("classId"));
        String className = request.getParameter("className");

        // Lấy danh sách điểm danh tổng hợp cho lớp
        List<AttendanceRecord> summaryList = attendanceDAO.getAttendanceSummary(classId);

        // Đặt danh sách vào request
        request.setAttribute("summaryList", summaryList);
        request.setAttribute("classId", classId);
        request.setAttribute("className", className);

        // Chuyển hướng tới trang ListAttendanceOfClass.jsp để hiển thị
        request.getRequestDispatcher("/Views/Teacher/listAttendanceClass.jsp").forward(request, response);
    }

    /**
     * Handles the process of retrieving and displaying detailed attendance records for a specific student within a class.
     * Fetches the attendance details and total attendance data, then forwards this information to a JSP for rendering.
     *
     * @param request  The HttpServletRequest object containing client request parameters.
     * @param response The HttpServletResponse object for sending the response to the client.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    private void handleDetailAttendance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int classId = Integer.parseInt(request.getParameter("classId"));
            int studentId = Integer.parseInt(request.getParameter("studentId")); // Lấy studentId từ request

            // Lấy thông tin chi tiết điểm danh cho học sinh
            List<AttendanceRecord> attendanceDetails = attendanceDAO.getAttendanceDetails(classId, studentId);
            AttendanceRecord totalAttendance = attendanceDAO.getTotalAttendance(classId, studentId);

            // Đặt thông tin vào request
            request.setAttribute("attendanceDetails", attendanceDetails);
            request.setAttribute("totalAttendance", totalAttendance);
            request.setAttribute("classId", classId);
            // Chuyển hướng tới trang detailAttendance.jsp để hiển thị
            request.getRequestDispatcher("/Views/Teacher/detailAttendance.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid input data.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

    /**
     * Exports the attendance data to a downloadable format (e.g., CSV or PDF) for external use.
     *
     * @param request  The HttpServletRequest object containing client request parameters.
     * @param response The HttpServletResponse object for sending the response to the client.
     * @throws IOException if an I/O error occurs.
     */
    private void exportAttendance(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int classId = Integer.parseInt(request.getParameter("classId"));
        String className = request.getParameter("className");

        // Lấy danh sách điểm danh tổng hợp
        List<AttendanceRecord> summaryList = attendanceDAO.getAttendanceSummary(classId);

        // Thiết lập định dạng file CSV
        response.setContentType("text/csv; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"attendance_summary_" + className + ".csv\"");

        PrintWriter writer = response.getWriter();
        writer.println("Student ID,Student Name,Total Attendance,Present,Absent"); // Tiêu đề

        for (AttendanceRecord record : summaryList) {
            writer.println(record.getStudentId() + "," + record.getStudentName() + "," +
                    record.getTotalAttendance() + "," + record.getTotalPresent() + "," +
                    record.getTotalAbsent());
        }

        writer.flush();
        writer.close();
    }

    /**
     * Exports detailed attendance records for a specific student in a class to a CSV file.
     * The method fetches detailed attendance data and a summary, formats it into CSV format,
     * and writes the result to the HTTP response for download.
     *
     * @param request  The HttpServletRequest object containing client request parameters, including class ID, student ID, and student name.
     * @param response The HttpServletResponse object for sending the CSV file to the client.
     * @throws IOException if an I/O error occurs during writing to the response.
     */
    private void exportDetailAttendance(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int classId = Integer.parseInt(request.getParameter("classId"));
        int studentId = Integer.parseInt(request.getParameter("studentId")); // Lấy studentId từ request
        String studentName = request.getParameter("studentName");
        // Lấy thông tin chi tiết điểm danh cho học sinh
        List<AttendanceRecord> attendanceDetails = attendanceDAO.getAttendanceDetails(classId, studentId);
        AttendanceRecord totalAttendance = attendanceDAO.getTotalAttendance(classId, studentId); // Lấy thông tin tổng hợp điểm danh

        // Thiết lập định dạng file CSV
        response.setContentType("text/csv; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"attendance_details_student_" + studentName + ".csv\"");

        PrintWriter writer = response.getWriter();
        writer.println("Date,Slot Name,Attendance Status"); // Tiêu đề

        // Xuất các bản ghi chi tiết điểm danh
        for (AttendanceRecord record : attendanceDetails) {
            writer.println(record.getDate() + "," + record.getSlotName() + "," + record.getAttendStatus());
        }

        // Xuất thông tin tổng hợp điểm danh
        writer.println(); // Thêm một dòng trống để phân cách
        writer.println("Student Name: ," + studentName);
        writer.println("Total Attendance," + totalAttendance.getTotalAttendance());
        writer.println("Total Present," + totalAttendance.getTotalPresent());
        writer.println("Total Absent," + totalAttendance.getTotalAbsent());

        writer.flush();
        writer.close();
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
