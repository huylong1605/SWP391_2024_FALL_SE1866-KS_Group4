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

@WebServlet(name = "verificationCode", value = "/verificationCode")
public class VerificationCodeController extends HttpServlet {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private ForgetPasswordDAO forgetPasswordDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        forgetPasswordDAO = new ForgetPasswordDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EmailService emailService = new EmailService();
        String email = req.getParameter("email");

        String code = req.getParameter("Code");
        String newPass = "";

        String codeConfirm = "";
        try {
            codeConfirm = forgetPasswordDAO.FindCode(email);

        if (codeConfirm.equals(code)){
            newPass = generateRandomString();
            emailService.send(email, "New Password", newPass);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(newPass);
            forgetPasswordDAO.newPass(email, hashedPassword);
            req.setAttribute("PasswordUpdate", "Check your email, password has change");
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
        }else{
            req.setAttribute("CodeNotTrue", "Code is not true, try again");
            req.getRequestDispatcher("verificationCode.jsp").forward(req, resp);
        }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
