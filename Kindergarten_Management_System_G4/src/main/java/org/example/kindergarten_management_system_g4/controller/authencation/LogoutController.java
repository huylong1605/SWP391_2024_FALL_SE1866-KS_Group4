/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/2/2024       1.1              Nguyễn Huy Long - He160140            Update Logout
 */

package org.example.kindergarten_management_system_g4.controller.authencation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Sử lý logic cho việc đăng xuất, xóa session của người dùng khi đăng xuất
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "logout", value = "/logout")
public class LogoutController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LogoutController.class.getName());


    /**
     * Phương thức doGet xử lý yêu cầu GET khi người dùng yêu cầu đăng xuất.
     * Nó xóa thuộc tính người dùng khỏi phiên và chuyển hướng đến trang đăng nhập.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ người dùng.
     * @param resp Đối tượng HttpServletResponse để gửi phản hồi cho người dùng.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi đầu vào/đầu ra.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Lấy phiên mà không tạo mới nếu không tồn tại
        if (session != null) {
            session.removeAttribute("user"); // Xóa thuộc tính user khỏi phiên
            logger.info("User logged out successfully."); // Ghi log thông báo đăng xuất thành công
        } else {
            logger.warning("Session was null during logout attempt."); // Ghi log cảnh báo nếu phiên không tồn tại
        }
        req.getRequestDispatcher("Login.jsp").forward(req, resp); // Chuyển hướng đến trang đăng nhập
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        super.doPost(req, resp);
    }
}
