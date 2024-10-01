package org.example.kindergarten_management_system_g4.controller.authencation;

import org.example.kindergarten_management_system_g4.dao.AuthenDAO.ForgetPasswordDAO;
import org.example.kindergarten_management_system_g4.javaMail.EmailService;

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

@WebServlet(name = "forgetPassword", value = "/forgetPassword")
public class ForgetPasswordController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ForgetPasswordController.class.getName());
    private ForgetPasswordDAO forgetPasswordDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        forgetPasswordDAO = new ForgetPasswordDAO();
        logger.log(Level.INFO, "ForgetPasswordController initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Có thể thêm xử lý cho yêu cầu GET nếu cần
        logger.log(Level.INFO, "GET request received for forgetPassword");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("Email");
        String code = generateRandomCode();
        logger.log(Level.INFO, "Processing password reset for email: {0}", email);

        try {
            if (isEmailValid(email)) {
                sendVerificationCode(email, code);
                logger.log(Level.INFO, "Verification code sent to email: {0}", email);
                resp.sendRedirect("verificationCode.jsp?email=" + email);
                forgetPasswordDAO.insertCode(code, email);
            } else {
                logger.log(Level.WARNING, "Invalid email address provided: {0}", email);
                handleInvalidEmail(req, resp);
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "ClassNotFoundException occurred", e);
            throw new RuntimeException(e);
        }
    }

    private boolean isEmailValid(String email) throws ClassNotFoundException {
        String mail = forgetPasswordDAO.findMail(email);
        boolean isValid = mail != null && !mail.isEmpty();
        logger.log(Level.INFO, "Email validation result for {0}: {1}", new Object[]{email, isValid});
        return isValid;
    }

    private void sendVerificationCode(String email, String code) {
        CompletableFuture.runAsync(() -> {
            EmailService emailService = new EmailService();
            emailService.send(email, "Verification Code", code);
            logger.log(Level.INFO, "Sent verification code: {0} to email: {1}", new Object[]{code, email});
        });
    }

    private void handleInvalidEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("emailNull", "Email does not exist");
        logger.log(Level.WARNING, "Attempt to reset password for non-existing email");
        req.getRequestDispatcher("forgotPassword.jsp").forward(req, resp);
    }

    public String generateRandomCode() {
        Random rd = new Random();
        int num = rd.nextInt(999999);
        String code = String.format("%06d", num);
        logger.log(Level.INFO, "Generated random verification code: {0}", code);
        return code;
    }
}
