/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/2/2024       1.1              Nguyễn Huy Long - He160140        Update validate Login
 */

package org.example.kindergarten_management_system_g4.controller.authencation;

import org.example.kindergarten_management_system_g4.dao.AuthenDAO.LoginDAO;
import org.example.kindergarten_management_system_g4.dao.StudentDAO.StudentDAO;
import org.example.kindergarten_management_system_g4.javaMail.EmailService;
import org.example.kindergarten_management_system_g4.model.Student;
import org.example.kindergarten_management_system_g4.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Sử lý logic cho việc xác thực đăng nhập và phân quyền người dùng
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "login", value = "/login")
public class LoginController extends HttpServlet { // lớp LoginController để kiểm tra đăng nhập của người dùng
    private LoginDAO loginDAO;
    private StudentDAO studentDAO;


    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        loginDAO = new LoginDAO();
        studentDAO = new StudentDAO(); // Khởi tạo đối tượng StudentDAO
    }
    /**
     * Phương thức doPost xử lý yêu cầu POST từ người dùng khi đăng nhập.
     * Nó kiểm tra email và mật khẩu, mã hóa mật khẩu, lưu trữ thông tin trong session và cookie,
     * điều hướng người dùng theo vai trò và gửi thông báo qua email.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ người dùng.
     * @param resp Đối tượng HttpServletResponse để gửi phản hồi cho người dùng.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi đầu vào/đầu ra.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("Email"); //Lấy địa chỉ email từ phía người dùng thông qua getParameter
        String password = req.getParameter("password"); // Lấy password từ phía người dùng thông qua getParameter
        String remember = req.getParameter("rememberMe"); // Lấy giá trị từ checkbox remember me
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Khởi tạo đối tượng PasswordEncoder
                                                                        // để thực hiện mã hóa mật khẩu


        try {

            String hashedPassword = loginDAO.getPassword(email);// Biến hashedPassword để lưu mật khẩu được lấy từ hàm getPassword của lớp loginDAO
            if (passwordEncoder.matches(password, hashedPassword)) { // Đối chiếu Password thông qua hàm matches của lớp  PasswordEncoder
                User user = loginDAO.getUser(email); // Sử dụng email để lấy đối tượng tương ưng với email đó
                if (user != null) {
                    HttpSession session = req.getSession(); //Sử dụng session để lưu đối tượng người dùng
                    session.setAttribute("user", user); // set session
                    LOGGER.info("User " + email + " logged in successfully."); // Ghi log đăng nhập thành công

                    handleCookies(email, password, remember, resp); // Lưu trữ đối tượng cookies cho
                    redirectUserBasedOnRole(user, req, resp); //Điều hướng người dùng khi đăng nhập thành công

                    // Sử dụng  runAsync giúp việc gửi thông tin đến người dùng qua email nhanh hơn
                    /*CompletableFuture.runAsync(() -> sendLoginNotification(email, user));*/
                }
            } else {
                LOGGER.warning("Login attempt failed for user: " + email); // Ghi log khi đăng nhập thất bại
                handleLoginFailure(req, resp, email, password); //Sử lý khi đăng nhập thất bại
            }
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Error during login process: " + e.getMessage()); // Ghi log lỗi nghiêm trọng
            throw new RuntimeException(e);
        }
    }

    /**
     * Xử lý việc lưu trữ hoặc xóa cookie dựa trên lựa chọn "Remember Me" của người dùng.
     * Nếu người dùng chọn nhớ thông tin đăng nhập (remember = 1), phương thức sẽ lưu email,
     * mật khẩu và thông tin ghi nhớ vào cookie trong vòng 1 phút. Nếu không, phương thức sẽ
     * xóa cookie.
     *
     * @param email     Email của người dùng.
     * @param password  Mật khẩu của người dùng.
     * @param remember  Thông tin ghi nhớ đăng nhập (giá trị "1" nếu người dùng chọn nhớ đăng nhập).
     * @param resp      Đối tượng HttpServletResponse để thêm cookie vào phản hồi.
     */
    private void handleCookies(String email, String password, String remember, HttpServletResponse resp) {
        Cookie cookieEmail;
        Cookie cookiePass;
        Cookie cookieRemember;

        if ("1".equals(remember)) { //Lưu trữ thông tin người dùng qua cookie cho lần đăng nhập tới khi remember = 1
            cookieEmail = new Cookie("cookieEmail", email);
            cookiePass = new Cookie("cookiePass", password);
            cookieRemember = new Cookie("cookieRemember", "1");

            cookieEmail.setMaxAge(60); // 1 phút
            cookiePass.setMaxAge(60);
            cookieRemember.setMaxAge(60);
            LOGGER.info("Cookies set for user: " + email); // Ghi log khi thiết lập cookie
        } else {
            cookieEmail = new Cookie("cookieEmail", "");
            cookiePass = new Cookie("cookiePass", "");
            cookieRemember = new Cookie("cookieRemember", "");

            cookieEmail.setMaxAge(0); // Xóa cookie
            cookiePass.setMaxAge(0);
            cookieRemember.setMaxAge(0);
            LOGGER.info("Cookies cleared for user: " + email); // Ghi log khi xóa cookie
        }

        resp.addCookie(cookieEmail);
        resp.addCookie(cookiePass);
        resp.addCookie(cookieRemember);
    }

    /**
     * Điều hướng người dùng đến trang thích hợp dựa trên vai trò của họ.
     * Nếu người dùng có roleId = 4 (Parent), họ sẽ được điều hướng đến trang chủ Parent.
     * Nếu người dùng có roleId = 1 (Admin), họ sẽ được điều hướng đến trang quản lý Admin.
     *
     * @param user  Đối tượng User chứa thông tin vai trò của người dùng.
     * @param req   Đối tượng HttpServletRequest để điều hướng người dùng.
     * @param resp  Đối tượng HttpServletResponse để điều hướng người dùng.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi đầu vào/đầu ra.
     */
    private void redirectUserBasedOnRole(User user, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ClassNotFoundException {
        if (user.getRoleId() == 3) {         //Id = 3 điều hướng người dùng đến trang  homepage của parent
            List<Student> listChild = studentDAO.getStudentsByUserId(user.getUserID());
            req.setAttribute("listChild",listChild);
            System.out.println("Test01"+listChild);
            req.getRequestDispatcher("/Views/HomePage/HomePage.jsp").forward(req, resp);
        } else if (user.getRoleId() == 1) {      //Id = 1 điều hướng người dùng đến trang  homepage của Admin
            req.getRequestDispatcher("/Views/HomePage/HomePageForAdmin.jsp").forward(req, resp);
        } else if (user.getRoleId() == 4) {      //Id = 1 điều hướng người dùng đến trang  homepage của Manager
            resp.sendRedirect(req.getContextPath() + "/Views/HomePage/HomePageForManager");
        } else if (user.getRoleId() == 2) {      //Id = 1 điều hướng người dùng đến trang  homepage của teacher
            req.getRequestDispatcher("/Views/HomePage/HomePageForTeacher.jsp").forward(req, resp);
        }else if (user.getRoleId() == 5) {      //Id = 1 điều hướng người dùng đến trang  homepage của teacher
            req.getRequestDispatcher("/Views/HomePage/HomePageForEnrollment.jsp").forward(req, resp);
        }
    }

    /**
     * Xử lý tình huống đăng nhập thất bại.
     * Phương thức này thiết lập các thuộc tính cần thiết trong request, bao gồm email,
     * mật khẩu, và thông báo lỗi, sau đó chuyển hướng người dùng về trang đăng nhập.
     *
     * @param req      Đối tượng HttpServletRequest chứa thông tin đăng nhập.
     * @param resp     Đối tượng HttpServletResponse để điều hướng người dùng.
     * @param email    Email của người dùng.
     * @param password Mật khẩu của người dùng.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi đầu vào/đầu ra.
     */
    private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, String email, String password) throws ServletException, IOException {
        req.setAttribute("Email", email);
        req.setAttribute("password", password);
        req.setAttribute("loginFalse", "Username or password incorrect");
        req.getRequestDispatcher("Login.jsp").forward(req, resp);
    }

    /**
     * Gửi email thông báo cho người dùng khi họ đăng nhập thành công.
     * Phương thức này sử dụng EmailService để gửi email thông báo với thông tin đăng nhập của người dùng.
     * Sau đó, ghi log rằng email đã được gửi đi.
     *
     * @param email Email của người dùng nhận thông báo.
     * @param user  Đối tượng User chứa thông tin người dùng.
     */
    private void sendLoginNotification(String email, User user) {
        EmailService emailService = new EmailService();     //Khởi tạo từ lớp EmailService
        emailService.send(email, "hello " + user.getFullname(), "You have logged in to the Kindergarten Management System ");
        LOGGER.info("Login notification sent to user: " + email); // Ghi log thông báo email đã được gửi
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}

