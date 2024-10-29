/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115        Register Student By Parent
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

/**
 * Lớp RegisterStudentServlet chịu trách nhiệm xử lý các yêu cầu HTTP POST để đăng ký sinh viên mới từ phía phụ huynh.
 * Dữ liệu sinh viên được lấy từ form và lưu vào cơ sở dữ liệu.
 * <p>Lỗi: Chưa phát hiện lỗi.
 *
 * @author Đào Xuân Bình
 */

// Định nghĩa servlet với tên "registerStudent" và ánh xạ đến URL "/registerStudent"
@WebServlet(name = "registerStudent", value = "/registerStudent")
public class RegisterStudentServlet extends HttpServlet {

    // Khai báo đối tượng StudentDAO để tương tác với cơ sở dữ liệu
    private StudentDAO studentDAO;

    /**
     * Phương thức khởi tạo servlet, được gọi khi servlet được tạo ra.
     * Khởi tạo đối tượng StudentDAO.
     */
    @Override
    public void init() {
        studentDAO = new StudentDAO(); // Khởi tạo đối tượng StudentDAO
    }

    /**
     * Xử lý các yêu cầu POST từ phía client để đăng ký sinh viên mới.
     * Dữ liệu sinh viên được lấy từ form, kiểm tra sự tồn tại, và thêm vào cơ sở dữ liệu nếu chưa tồn tại.
     * Nếu sinh viên đã tồn tại, trả về mã lỗi xung đột (409).
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu POST
     */
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
                resp.sendError(HttpServletResponse.SC_CONFLICT, "Student is already registered");
            } else {
                // Nếu sinh viên chưa tồn tại, tạo đối tượng sinh viên mới
                Student newStudent = new Student(0, dob, gender, name, user.getUserID()); // studentId sẽ được tự động tạo
                // Thêm sinh viên mới vào cơ sở dữ liệu
                studentDAO.addStudent(newStudent);
                // Chuyển tiếp yêu cầu đến trang danh sách sinh viên
                req.getRequestDispatcher("/viewStudentList").forward(req, resp);
            }
            // Chuyển tiếp yêu cầu đến trang JSP để hiển thị
            req.getRequestDispatcher("/registerStudent.jsp").forward(req, resp);

        } catch (Exception e) {
            // Xử lý ngoại lệ và gửi lỗi máy chủ nội bộ (500)
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Registration failed");
        }
    }
}
