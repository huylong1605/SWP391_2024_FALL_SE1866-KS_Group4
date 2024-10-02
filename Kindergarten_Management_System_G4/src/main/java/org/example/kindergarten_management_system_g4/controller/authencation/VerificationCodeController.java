/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/2/2024       1.1              Nguyễn Huy Long - He160140          Update VerificationCode
 */

package org.example.kindergarten_management_system_g4.controller.authencation;

import org.example.kindergarten_management_system_g4.dao.AuthenDAO.ForgetPasswordDAO;
import org.example.kindergarten_management_system_g4.javaMail.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sử lý logic cho việc xác thực mã Code và gửi mật khẩu mới qua email
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "verificationCode", value = "/verificationCode")
public class VerificationCodeController extends HttpServlet {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Chuỗi ký tự để tạo mã ngẫu nhiên
    private static final Logger LOGGER = Logger.getLogger(VerificationCodeController.class.getName());

    private ForgetPasswordDAO forgetPasswordDAO;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    @Override
    public void init() throws ServletException {
        super.init();
        forgetPasswordDAO = new ForgetPasswordDAO();
        emailService = new EmailService();
        passwordEncoder = new BCryptPasswordEncoder();
        LOGGER.info("VerificationCodeController initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    /**
     * Xử lý yêu cầu POST để xác thực mã và cập nhật mật khẩu mới.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ người dùng.
     * @param resp Đối tượng HttpServletResponse để gửi phản hồi cho người dùng.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi đầu vào/đầu ra.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*EmailService emailService = new EmailService();*/
        String email = req.getParameter("email");

        String code = req.getParameter("Code");

        try {
            String codeConfirm = forgetPasswordDAO.findCode(email); //Lấy code từ cơ sở dữ liệu
            if (isCodeValid(codeConfirm, code)) {
                String newPass = generateRandomString();
                CompletableFuture.runAsync(() -> sendNewPassword(email, newPass));
                updatePassword(email, newPass);
                notifyPasswordUpdate(req, resp);
            } else {
                handleInvalidCode(req, resp);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error processing password reset", e);
            throw new ServletException("Database error", e);
        }
    }

    /**
     * Kiểm tra tính hợp lệ của mã xác thực.
     *
     * @param codeConfirm Mã xác thực lưu trong cơ sở dữ liệu.
     * @param code        Mã xác thực người dùng nhập vào.
     * @return true nếu mã hợp lệ, false nếu không hợp lệ.
     */
    private boolean isCodeValid(String codeConfirm, String code) {
        return codeConfirm.equals(code);
    }


    /**
     * Gửi mật khẩu mới đến địa chỉ email của người dùng.
     *
     * @param email   Địa chỉ email của người dùng.
     * @param newPass Mật khẩu mới.
     */
    private void sendNewPassword(String email, String newPass) {
        emailService.send(email, "New Password", newPass);
        LOGGER.info("New password sent to email: " + email);
    }


    /**
     * Cập nhật mật khẩu mới trong cơ sở dữ liệu.
     *
     * @param email    Địa chỉ email của người dùng.
     * @param newPass  Mật khẩu mới.
     * @throws ClassNotFoundException Nếu không tìm thấy lớp liên quan đến cơ sở dữ liệu.
     */
    private void updatePassword(String email, String newPass) throws ClassNotFoundException {
        String hashedPassword = passwordEncoder.encode(newPass);
        forgetPasswordDAO.newPass(email, hashedPassword);
        LOGGER.info("Password updated for email: " + email);
    }

    /**
     * Thông báo cho người dùng rằng mật khẩu đã được cập nhật.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ người dùng.
     * @param resp Đối tượng HttpServletResponse để gửi phản hồi cho người dùng.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi đầu vào/đầu ra.
     */
    private void notifyPasswordUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("PasswordUpdate", "Check your email, password has changed");
        req.getRequestDispatcher("Login.jsp").forward(req, resp);
        LOGGER.info("User notified about password update.");
    }

    /**
     * Xử lý trường hợp mã xác thực không hợp lệ.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ người dùng.
     * @param resp Đối tượng HttpServletResponse để gửi phản hồi cho người dùng.
     * @throws ServletException Nếu có lỗi trong quá trình xử lý Servlet.
     * @throws IOException      Nếu có lỗi đầu vào/đầu ra.
     */
    private void handleInvalidCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("CodeNotTrue", "Code is not valid, try again");
        req.getRequestDispatcher("verificationCode.jsp").forward(req, resp);
        LOGGER.warning("Invalid code attempted for email: " + req.getParameter("email"));
    }

    /**
     * Tạo chuỗi ngẫu nhiên gồm 6 ký tự để làm mật khẩu mới.
     *
     * @return Chuỗi mật khẩu ngẫu nhiên.
     */
    public static String generateRandomString() {
        Random rd = new Random(); // Khởi tạo đối tượng Random
        StringBuilder sb = new StringBuilder(6); // Tạo StringBuilder với kích thước 6 ký tự

        for (int i = 0; i < 6; i++) {
            int index = rd.nextInt(CHARACTERS.length()); // Lấy chỉ số ngẫu nhiên từ CHARACTERS
            sb.append(CHARACTERS.charAt(index)); // Thêm ký tự vào chuỗi
        }

        return sb.toString(); // Trả về chuỗi ngẫu nhiên
    }
}
