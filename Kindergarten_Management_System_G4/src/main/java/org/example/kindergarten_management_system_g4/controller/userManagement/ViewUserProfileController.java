/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/2/2024       1.1              Đào Xuân Bình - HE163115        View Profile
 */
package org.example.kindergarten_management_system_g4.controller.userManagement;

import org.example.kindergarten_management_system_g4.dao.UserProfileDAO;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// Define servlet with the name "ViewUserProfileController" and map to the URL "/viewprofile"
@WebServlet(name = "ViewUserProfileController", value = "/viewprofile")
public class ViewUserProfileController extends HttpServlet {

    // Declare UserProfileDAO object to interact with the database
    private UserProfileDAO userProfileDAO;

    // Phương thức khởi tạo servlet, được gọi khi servlet được tạo ra
    @Override
    public void init() throws ServletException {
        super.init();
        // Khởi tạo đối tượng UserProfileDAO (đảm bảo kết nối với cơ sở dữ liệu)
        userProfileDAO = new UserProfileDAO();
    }

    // Xử lý các yêu cầu GET từ phía client
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy phiên làm việc (session) của người dùng
        HttpSession session = req.getSession();
        // Lấy thông tin người dùng từ session
        User sessionUser = (User) session.getAttribute("user");

        // Debug: in ra console userID của người dùng
        System.out.println(sessionUser.getUserID());

        // Kiểm tra nếu người dùng đã đăng nhập
        if (sessionUser != null) {
            // Lấy thông tin người dùng từ DAO dựa trên userID
            User user = userProfileDAO.getUserById(sessionUser.getUserID());

            // Debug: in lại userID của người dùng ra console
            System.out.println(sessionUser.getUserID());

            // Nếu người dùng tồn tại, đặt attribute "user" vào request và chuyển tiếp đến trang xem hồ sơ
            if (user != null) {
                req.setAttribute("user", user);
                req.getRequestDispatcher("/viewProfile.jsp").forward(req, resp);
            } else {
                // Nếu không tìm thấy người dùng, chuyển hướng đến trang đăng nhập và trả về lỗi 404
                resp.sendRedirect("/Login.jsp");
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy người dùng");
            }
        } else {
            // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
            resp.sendRedirect("/Login.jsp");
        }
    }

}