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


@WebServlet(name = "forgetPassword", value = "/forgetPassword")
public class ForgetPasswordController extends HttpServlet {

    private ForgetPasswordDAO forgetPasswordDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        forgetPasswordDAO = new ForgetPasswordDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("Email");
        EmailService emailService = new EmailService();

        String code = getRanDom();
        try {
            String Mail = forgetPasswordDAO.findMail(email);
            if (Mail.isEmpty() || Mail == null) {

                req.setAttribute("emailNull", "email is not exist");
                req.getRequestDispatcher("forgotPassword.jsp").forward(req, resp);


            } else {

                emailService.send(email, "Code", code);

                System.out.println("Email before redirect: " + email); // In ra để kiểm tra
                resp.sendRedirect("verificationCode.jsp?email=" + email);
                forgetPasswordDAO.insertCode(code, email);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRanDom() {
        Random rd = new Random();
        int num = rd.nextInt(999999);
        return String.format("%06d", num);
    }
}



