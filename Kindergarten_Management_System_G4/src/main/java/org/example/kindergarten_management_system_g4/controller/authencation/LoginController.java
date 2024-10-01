package org.example.kindergarten_management_system_g4.controller.authencation;

import org.example.kindergarten_management_system_g4.dao.AuthenDAO.LoginDAO;
import org.example.kindergarten_management_system_g4.javaMail.EmailService;
import org.example.kindergarten_management_system_g4.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "login", value = "/login")
public class LoginController extends HttpServlet {
    private LoginDAO loginDAO;
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        loginDAO = new LoginDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("Email");
        String password = req.getParameter("password");
        String remember = req.getParameter("rememberMe");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        try {
            String hashedPassword = loginDAO.getPassword(email);
            if (passwordEncoder.matches(password, hashedPassword)) {
                User user = loginDAO.getUser(email);
                if (user != null) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);
                    logger.info("User " + email + " logged in successfully."); // Ghi log đăng nhập thành công

                    handleCookies(email, password, remember, resp);
                    redirectUserBasedOnRole(user, req, resp);

                    // Gửi email thông báo đăng nhập (bất đồng bộ)
                    CompletableFuture.runAsync(() -> sendLoginNotification(email, user));
                }
            } else {
                logger.warning("Login attempt failed for user: " + email); // Ghi log khi đăng nhập thất bại
                handleLoginFailure(req, resp, email, password);
            }
        } catch (ClassNotFoundException e) {
            logger.severe("Error during login process: " + e.getMessage()); // Ghi log lỗi nghiêm trọng
            throw new RuntimeException(e);
        }
    }

    // Tách logic xử lý cookie thành hàm riêng
    private void handleCookies(String email, String password, String remember, HttpServletResponse resp) {
        Cookie cookieEmail;
        Cookie cookiePass;
        Cookie cookieRemember;

        if ("1".equals(remember)) {
            cookieEmail = new Cookie("cookieEmail", email);
            cookiePass = new Cookie("cookiePass", password);
            cookieRemember = new Cookie("cookieRemember", "1");

            cookieEmail.setMaxAge(60); // 1 phút
            cookiePass.setMaxAge(60); // 1 phút
            cookieRemember.setMaxAge(60);
            logger.info("Cookies set for user: " + email); // Ghi log khi thiết lập cookie
        } else {
            cookieEmail = new Cookie("cookieEmail", "");
            cookiePass = new Cookie("cookiePass", "");
            cookieRemember = new Cookie("cookieRemember", "");

            cookieEmail.setMaxAge(0); // Xóa cookie
            cookiePass.setMaxAge(0); // Xóa cookie
            cookieRemember.setMaxAge(0);
            logger.info("Cookies cleared for user: " + email); // Ghi log khi xóa cookie
        }

        resp.addCookie(cookieEmail);
        resp.addCookie(cookiePass);
        resp.addCookie(cookieRemember);
    }

    // Tách logic điều hướng người dùng theo vai trò
    private void redirectUserBasedOnRole(User user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (user.getRoleId() == 4) {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else if (user.getRoleId() == 1) {
            req.getRequestDispatcher("/Views/Admin/accountManage.jsp").forward(req, resp);
        }
    }

    // Xử lý đăng nhập thất bại
    private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, String email, String password) throws ServletException, IOException {
        req.setAttribute("Email", email);
        req.setAttribute("password", password);
        req.setAttribute("message1", "Username or password incorrect");
        req.getRequestDispatcher("Login.jsp").forward(req, resp);
    }

    // Gửi email thông báo đăng nhập (chạy bất đồng bộ)
    private void sendLoginNotification(String email, User user) {
        EmailService emailService = new EmailService();
        emailService.send(email, "hello " + user.getFullname(), "You have logged in to the Kindergarten Management System ");
        logger.info("Login notification sent to user: " + email); // Ghi log thông báo email đã được gửi
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
