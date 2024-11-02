/*
 * Copyright(C) 2005, SWP_G4.
 * KMS: Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115        Register Student By Parent
 */

package org.example.kindergarten_management_system_g4.controller.studentManagement;

import org.example.kindergarten_management_system_g4.dao.StudentDAO;
import org.example.kindergarten_management_system_g4.dao.UserProfileDAO;
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
import java.util.List;

/**
 * Lớp ParentController xử lý các yêu cầu HTTP GET để hiển thị danh sách người dùng
 * cho việc đăng ký sinh viên mới. Danh sách người dùng được lấy từ cơ sở dữ liệu
 * và hiển thị dưới dạng dropdown trong giao diện người dùng.
 * <p>Lỗi: Chưa phát hiện lỗi.
 *
 * @author Đào Xuân Bình
 */

@WebServlet(name = "registerStudentByEnroll", value = "/registerStudentByEnroll")
public class ParentController extends HttpServlet {

    private UserProfileDAO userProfileDAO;

    /**
     * Phương thức khởi tạo servlet, được gọi khi servlet được tạo ra.
     * Khởi tạo đối tượng UserProfileDAO.
     */
    @Override
    public void init() {
        userProfileDAO = new UserProfileDAO();
    }

    /**
     * Xử lý các yêu cầu GET từ phía client để lấy danh sách người dùng.
     * Danh sách người dùng được truyền vào request để hiển thị trong giao diện JSP.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu GET
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy danh sách tất cả người dùng từ DAO
        List<User> userList =  userProfileDAO.getAllUserParent();
        System.out.println(userList);

        // Thiết lập danh sách người dùng làm thuộc tính của request để hiển thị trong giao diện
        req.setAttribute("userList", userList);

        // Chuyển hướng tới trang JSP
        req.getRequestDispatcher("/registerStudentByEnrollment.jsp").forward(req, resp);
    }
}
