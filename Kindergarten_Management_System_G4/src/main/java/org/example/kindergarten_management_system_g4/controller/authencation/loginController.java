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

@WebServlet(name = "login", value = "/login")
public class loginController extends HttpServlet {
    private LoginDAO loginDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        loginDAO = new LoginDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmailService emailService = new EmailService();
        String email = req.getParameter("Email");
        String password = req.getParameter("password");
        String remember = req.getParameter("rememberMe");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            String hashedPassword = loginDAO.Password(email);
            if (passwordEncoder.matches(password, hashedPassword)) {
                User user = loginDAO.getUser(email);
                if (user != null) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user); // Lưu user vào session
                    //session.setMaxInactiveInterval(100);

                    Cookie cookieEmail;
                    Cookie cookiePass;
                    Cookie cookieRemember;
                    if("1".equals(remember)) {
                        cookieEmail = new Cookie("cookieEmail", email);
                        cookiePass = new Cookie("cookiePass", password);
                        cookieRemember = new Cookie("cookieRemember", "1");

                        cookieEmail.setMaxAge(60); // 1 phút
                        cookiePass.setMaxAge(60); // 1 phút
                        cookieRemember.setMaxAge(60);

                    }else{
                        cookieEmail = new Cookie("cookieEmail", "");
                        cookiePass = new Cookie("cookiePass", "");
                        cookieRemember = new Cookie("cookieRemember", "");

                         cookieEmail.setMaxAge(0); // Xóa cookie
                         cookiePass.setMaxAge(0); // Xóa cookie
                         cookieRemember.setMaxAge(0);

                    }
                    resp.addCookie(cookieEmail);
                    resp.addCookie(cookiePass);
                    resp.addCookie(cookieRemember);

                    if (user.getRoleId() == 4) {
                        req.getRequestDispatcher("/Views/HomePage/HomePage.jsp").forward(req, resp);

                        /*  resp.sendRedirect("index.jsp");*/
                    } else if (user.getRoleId() == 1) {
                        req.getRequestDispatcher("/Views/Admin/accountManage.jsp").forward(req, resp);

                    }

                }
                emailService.send(email, "hello " + user.getFullname(), "You have logged in to the Kindergarten Management System ");
                }else {
                req.setAttribute("Email", email);
                req.setAttribute("password", password);
                req.setAttribute("message1", "Username or password incorrect");
                req.getRequestDispatcher("Login.jsp").forward(req, resp);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
        @Override
        protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doGet(req, resp);
        }
    }

