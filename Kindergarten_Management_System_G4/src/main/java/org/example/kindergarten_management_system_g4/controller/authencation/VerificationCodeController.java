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

@WebServlet(name = "verificationCode", value = "/verificationCode")
public class VerificationCodeController extends HttpServlet {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String code = req.getParameter("Code");

        try {
            String codeConfirm = forgetPasswordDAO.findCode(email);
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

    private boolean isCodeValid(String codeConfirm, String code) {
        return codeConfirm.equals(code);
    }

    private void sendNewPassword(String email, String newPass) {
        emailService.send(email, "New Password", newPass);
        LOGGER.info("New password sent to email: " + email);
    }

    private void updatePassword(String email, String newPass) throws ClassNotFoundException {
        String hashedPassword = passwordEncoder.encode(newPass);
        forgetPasswordDAO.newPass(email, hashedPassword);
        LOGGER.info("Password updated for email: " + email);
    }

    private void notifyPasswordUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("PasswordUpdate", "Check your email, password has changed");
        req.getRequestDispatcher("Login.jsp").forward(req, resp);
        LOGGER.info("User notified about password update.");
    }

    private void handleInvalidCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("CodeNotTrue", "Code is not valid, try again");
        req.getRequestDispatcher("verificationCode.jsp").forward(req, resp);
        LOGGER.warning("Invalid code attempted for email: " + req.getParameter("email"));
    }

    public static String generateRandomString() {
        Random rd = new Random();
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int index = rd.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
}
