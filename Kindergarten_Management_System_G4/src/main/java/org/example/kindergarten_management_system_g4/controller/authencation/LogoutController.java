package org.example.kindergarten_management_system_g4.controller.authencation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "logout", value = "/logout")
public class LogoutController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LogoutController.class.getName());

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
        logger.warning("Post request on logout endpoint is not supported."); // Ghi log cảnh báo nếu có yêu cầu POST
        super.doPost(req, resp);
    }
}
