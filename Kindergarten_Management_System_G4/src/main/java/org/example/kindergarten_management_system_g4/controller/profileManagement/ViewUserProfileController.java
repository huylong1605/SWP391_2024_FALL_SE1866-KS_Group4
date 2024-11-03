/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115        View User Profile
 */

package org.example.kindergarten_management_system_g4.controller.profileManagement;

import org.example.kindergarten_management_system_g4.dao.profileDAO.UserProfileDAO;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Lớp ViewUserProfileController chịu trách nhiệm xử lý các yêu cầu HTTP GET để hiển thị thông tin hồ sơ người dùng.
 * Thông tin người dùng được lấy từ cơ sở dữ liệu và hiển thị trên giao diện.
 * <p>Lỗi: Chưa phát hiện lỗi.
 *
 * @author Đào Xuân Bình
 */

// Định nghĩa servlet với tên "ViewUserProfileController" và ánh xạ đến URL "/viewprofile"
@WebServlet(name = "ViewUserProfileController", value = "/viewprofile")
public class ViewUserProfileController extends HttpServlet {

    // Khai báo đối tượng UserProfileDAO để tương tác với cơ sở dữ liệu
    private UserProfileDAO userProfileDAO;

    /**
     * Phương thức khởi tạo servlet, được gọi khi servlet được tạo ra.
     * Khởi tạo đối tượng UserProfileDAO để đảm bảo kết nối với cơ sở dữ liệu.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        userProfileDAO = new UserProfileDAO(); // Khởi tạo đối tượng UserProfileDAO
    }

    /**
     * Xử lý các yêu cầu GET từ phía client để hiển thị hồ sơ người dùng.
     * Thông tin người dùng được lấy từ session, kiểm tra sự tồn tại và hiển thị trên trang xem hồ sơ.
     * Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu GET
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(); // Lấy phiên làm việc (session) của người dùng
        User sessionUser = (User) session.getAttribute("user"); // Lấy thông tin người dùng từ session

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
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } else {
            // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
            resp.sendRedirect("/Login.jsp");
        }
    }

}
