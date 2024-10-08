/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/5/2024       1.1              Nguyễn Huy Long - He160140            Create class Changepassword
 */
package org.example.kindergarten_management_system_g4.controller.profileManagement;




import org.example.kindergarten_management_system_g4.dao.profileDAO.Implement.ChangePasswordDAOImpl;
import org.example.kindergarten_management_system_g4.dao.profileDAO.IChangePassword;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;


/**
 * Sử lý logic cho việc người dùng muốn thay đổi mật khẩu
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "changePassword", value = "/changePassword")
public class ChangePasswordController extends HttpServlet {
    private IChangePassword changePasswordService;
    private static final int MAX_LENGTH_PASSWORD = 10;    //Giới hạn độ dài cho mật khẩu mới
    private static final Logger LOGGER = Logger.getLogger(ChangePasswordController.class.getName());

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public void init() throws ServletException {
        super.init();
        changePasswordService = new ChangePasswordDAOImpl();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }



    /**
     * Xử lý yêu cầu HTTP POST cho việc thay đổi mật khẩu của người dùng.
     * Phương thức này xác thực email và mật khẩu cũ của người dùng, kiểm tra
     * độ dài của mật khẩu mới và sự khớp với mật khẩu xác nhận, và cập nhật
     * mật khẩu nếu tất cả các điều kiện được thỏa mãn.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu mà client gửi đến servlet
     * @param resp đối tượng HttpServletResponse chứa phản hồi mà servlet trả về cho client
     * @throws ServletException nếu không thể xử lý yêu cầu POST
     * @throws IOException nếu phát hiện lỗi đầu vào hoặc đầu ra khi servlet xử lý yêu cầu POST
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Lấy các tham số từ yêu cầu HTTP và loại bỏ khoảng trắng thừa
        String email = req.getParameter("email").trim();
        String oldPassword = req.getParameter("oldPassword").trim();
        String newPassword = req.getParameter("newPassword").trim();
        String confirmPassword = req.getParameter("confirmNewPassword").trim();

        try {
            // Tìm kiếm email và mật khẩu từ dịch vụ thay đổi mật khẩu
            String getEmail = changePasswordService.findEmail(email);
            String getPassword = changePasswordService.findPassword(email);

            // Kiểm tra xem email có tồn tại trong hệ thống không
            if (!email.equals(getEmail)){
                req.setAttribute("emailFalse", "Your email is not exist");
                req.getRequestDispatcher("changePassword.jsp").forward(req, resp);
                LOGGER.info("Email is not true: " + email);
                return; // Dừng thực hiện nếu email không tồn tại
            }

            // Kiểm tra mật khẩu cũ có đúng không
            if (!passwordEncoder.matches(oldPassword, getPassword)){
                req.setAttribute("passwordFalse", "Your password is wrong");
                req.getRequestDispatcher("changePassword.jsp").forward(req, resp);
                LOGGER.info("Password is not true: " + email);
                return; // Dừng thực hiện nếu mật khẩu cũ sai
            }

            // Kiểm tra độ dài của mật khẩu mới
            if (newPassword.length() > MAX_LENGTH_PASSWORD) {
                req.setAttribute("lengthFalse", "Your password is too long");
                req.getRequestDispatcher("changePassword.jsp").forward(req, resp);
                LOGGER.info("Password is too long: " + email);
                return; // Dừng thực hiện nếu mật khẩu mới quá dài
            }

            // Kiểm tra mật khẩu mới có khớp với mật khẩu xác nhận không
            if (!newPassword.equals(confirmPassword)){
                req.setAttribute("notMatch", "password not match");
                req.getRequestDispatcher("changePassword.jsp").forward(req, resp);
                return; // Dừng thực hiện nếu mật khẩu mới và xác nhận không khớp
            }

            // Kiểm tra mật khẩu mới có giống mật khẩu cũ không
            if (oldPassword.equals(newPassword)){
                req.setAttribute("oldNewPassFalse", "New Password with old Password can not same");
                req.getRequestDispatcher("changePassword.jsp").forward(req, resp);
                LOGGER.info("pass same: " + email);
                return; // Dừng thực hiện nếu mật khẩu mới giống mật khẩu cũ
            }

            // Mã hóa mật khẩu mới và cập nhật vào hệ thống
            String hashedPassword = passwordEncoder.encode(newPassword);
            changePasswordService.updatePassword(email, hashedPassword);

            // Gửi thông báo thành công và chuyển tiếp đến trang thay đổi mật khẩu
            req.setAttribute("updateSuccessful", "Your password has been changed");
            req.getRequestDispatcher("changePassword.jsp").forward(req, resp);

        } catch (SQLException /*| ClassNotFoundException*/ e) {
            LOGGER.info("SQLException: " +e.getMessage());
            req.setAttribute("errorMessage", "An error occurred while changing password: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            // Xử lý ngoại lệ nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu


        }
    }




}
