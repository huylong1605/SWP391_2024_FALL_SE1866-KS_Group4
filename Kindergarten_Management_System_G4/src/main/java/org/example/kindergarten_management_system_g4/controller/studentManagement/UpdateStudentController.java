package org.example.kindergarten_management_system_g4.controller.studentManagement;

/*
 * Copyright(C) 2005, SWP_G4.
 * KMS:
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1               Đào Xuân Bình - HE163115          Update Student Information
 */

import org.example.kindergarten_management_system_g4.dao.StudentDAO.StudentDAO;
import org.example.kindergarten_management_system_g4.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * UpdateStudentController xử lý các yêu cầu POST để cập nhật thông tin học sinh.
 * Lấy thông tin sinh viên từ yêu cầu, thực hiện xác thực và cập nhật dữ liệu trong cơ sở dữ liệu.
 * <p>Bugs: Không có lỗi nào được phát hiện.
 *
 * @author Đào Xuân Bình
 */
@WebServlet(name = "updateStudent", value = "/updateStudent")
public class UpdateStudentController extends HttpServlet {
    private StudentDAO studentDAO;

    /**
     * Khởi tạo controller bằng cách thiết lập đối tượng StudentDAO.
     * Phương thức này được gọi một lần khi servlet được tạo lần đầu tiên.
     */
    @Override
    public void init() {
        studentDAO = new StudentDAO(); // Khởi tạo DAO cho sinh viên
    }

    /**
     * Xử lý các yêu cầu POST của HTTP để cập nhật thông tin học sinh.
     * Lấy thông tin sinh viên từ biểu mẫu và cập nhật sinh viên tương ứng trong cơ sở dữ liệu.
     *
     * @param request  đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param response đối tượng HttpServletResponse chứa phản hồi của servlet.
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra.
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu POST.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy ID sinh viên từ biểu mẫu
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String name = request.getParameter("name"); // Tên sinh viên
            LocalDate dob = LocalDate.parse(request.getParameter("dob")); // Ngày sinh của sinh viên
            boolean gender = Boolean.parseBoolean(request.getParameter("gender")); // Giới tính của sinh viên

            // Tạo đối tượng sinh viên với các thông tin đã nhận
            Student student = new Student(studentId, dob, gender, name);

            // Gọi DAO để cập nhật sinh viên trong cơ sở dữ liệu
            boolean isUpdated = studentDAO.updateStudent(student);

            // Kiểm tra nếu cập nhật thành công, chuyển hướng đến trang danh sách sinh viên
            if (isUpdated) {
                response.sendRedirect("viewStudentList"); // hoặc trả về thông báo thành công
            } else {
                response.sendRedirect("error.jsp"); // hoặc hiển thị lỗi nếu cập nhật không thành công
            }
        } catch (Exception e) {
            throw new ServletException("Error updating student", e); // Ném lỗi nếu có vấn đề trong khi cập nhật
        }
    }
}
