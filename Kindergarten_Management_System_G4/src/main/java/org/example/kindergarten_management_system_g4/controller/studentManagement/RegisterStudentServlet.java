/*
 * Copyright(C) 2005, SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version              AUTHOR                        DESCRIPTION
 * 12/10/2024     1.1                  Đào Xuân Bình - HE163115      Register Student By Parent
 */

package org.example.kindergarten_management_system_g4.controller.studentManagement;

import org.example.kindergarten_management_system_g4.dao.StudentDAO.StudentDAO;
import org.example.kindergarten_management_system_g4.model.Student;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
     * @param req  đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException      nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu POST
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy phiên làm việc (session) của người dùng
        HttpSession session = req.getSession();
        // Lấy thông tin người dùng từ session
        User user = (User) session.getAttribute("user");

        // Lấy thông tin sinh viên từ yêu cầu
        String name = req.getParameter("name"); // Tên sinh viên
        LocalDate dob = LocalDate.parse(req.getParameter("dob")); // Ngày sinh của sinh viên
        boolean gender = Boolean.parseBoolean(req.getParameter("gender")); // Giới tính của sinh viên

        // Định dạng ngày sinh
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date selectedDate = null;

        try {
            // Chuyển đổi dob từ LocalDate sang Date để so sánh với ngày hiện tại
            selectedDate = dateFormat.parse(dob.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e); // Ném ngoại lệ nếu có lỗi khi phân tích cú pháp
        }

        Date currentDate = new Date();
        // Kiểm tra nếu ngày sinh được chọn vượt quá ngày hiện tại
        if (selectedDate.after(currentDate)) {
            req.setAttribute("dateFalse", "Ngày sinh không được vượt quá ngày hiện tại"); // Thông báo lỗi
            req.getRequestDispatcher("registerStudent.jsp").forward(req, resp);
            return;
        }

        try {
            // Debug: In ra số lượng sinh viên hiện có của người dùng
            System.out.println("Test: " + studentDAO.getStudentsByUserId(user.getUserID()).size());

            // Kiểm tra sinh viên đã tồn tại dựa trên tên, ngày sinh và ID người dùng
            if (studentDAO.isStudentExist(name, dob, user.getUserID()) || studentDAO.getStudentsByUserId(user.getUserID()).size() > 0) {
                // Nếu sinh viên đã tồn tại, trả về lỗi xung đột (409)
                req.setAttribute("registrationSuccess", "Register Erroll !!!!! ");

                // Chuyển tiếp yêu cầu đến trang chủ của phụ huynh với danh sách sinh viên
                List<Student> listChild = studentDAO.getStudentsByUserId(user.getUserID());
                req.setAttribute("listChild", listChild);
                req.getRequestDispatcher("Views/HomePage/HomePage.jsp").forward(req, resp);
            } else {
                // Nếu sinh viên chưa tồn tại, tạo đối tượng sinh viên mới
                Student newStudent = new Student(0, dob, gender, name, user.getUserID()); // studentId sẽ được tự động tạo

                // Thêm sinh viên mới vào cơ sở dữ liệu
                studentDAO.addStudent(newStudent);

                // Thiết lập thông báo thành công và chuyển tiếp đến trang chủ của phụ huynh
                req.setAttribute("registrationSuccess", "Register Successful !!!!! ");

                // Lấy danh sách con của người dùng và chuyển tiếp yêu cầu về trang chủ
                List<Student> listChild = studentDAO.getStudentsByUserId(user.getUserID());
                req.setAttribute("listChild", listChild);
                req.getRequestDispatcher("Views/HomePage/HomePage.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ và gửi lỗi máy chủ nội bộ (500)
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Registration failed");
        }
    }
}
