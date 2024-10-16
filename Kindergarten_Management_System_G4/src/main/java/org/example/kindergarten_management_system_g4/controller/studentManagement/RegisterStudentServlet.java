/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/2/2024       1.1              Đào Xuân Bình - HE163115        Register Student By Parent
 */
package org.example.kindergarten_management_system_g4.controller.studentManagement;

import org.example.kindergarten_management_system_g4.dao.StudentDAO;
import org.example.kindergarten_management_system_g4.model.Student;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

// Định nghĩa servlet với tên "registerStudent" và ánh xạ đến URL "/registerStudent"
@WebServlet(name = "registerStudent", value = "/registerStudent")
public class RegisterStudentServlet extends HttpServlet {

    // Khai báo đối tượng StudentDAO để tương tác với cơ sở dữ liệu
    private StudentDAO studentDAO;

    // Phương thức khởi tạo servlet, được gọi khi servlet được tạo ra
    @Override
    public void init() {
        // Khởi tạo đối tượng StudentDAO
        studentDAO = new StudentDAO();
    }

    // Xử lý các yêu cầu POST từ phía client
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy phiên làm việc (session) của người dùng
        HttpSession session = req.getSession();
        // Lấy thông tin người dùng từ session
        User user = (User) session.getAttribute("user");
        // Lấy thông tin sinh viên từ yêu cầu
        String name = req.getParameter("name");  // Tên sinh viên
        LocalDate dob = LocalDate.parse(req.getParameter("dob"));  // Ngày sinh của sinh viên
        boolean gender = Boolean.parseBoolean(req.getParameter("gender"));  // Giới tính của sinh viên

        try {
            // Kiểm tra sinh viên đã tồn tại dựa trên tên, ngày sinh, và ID người dùng
            if (studentDAO.isStudentExist(name, dob, user.getUserID())) {
                // Nếu sinh viên đã tồn tại, trả về lỗi xung đột (409)
                resp.sendError(HttpServletResponse.SC_CONFLICT, "Sinh viên đã được đăng ký");
            } else {
                // Nếu sinh viên chưa tồn tại, tạo đối tượng sinh viên mới
                Student newStudent = new Student(0, dob, gender, name, user.getUserID()); // studentId sẽ được tự động tạo
                // Thêm sinh viên mới vào cơ sở dữ liệu
                studentDAO.addStudent(newStudent);
                // Chuyển tiếp yêu cầu đến trang danh sách sinh viên
                req.getRequestDispatcher("/students").forward(req, resp);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ và gửi lỗi máy chủ nội bộ (500)
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đăng ký thất bại");
        }
    }
}



