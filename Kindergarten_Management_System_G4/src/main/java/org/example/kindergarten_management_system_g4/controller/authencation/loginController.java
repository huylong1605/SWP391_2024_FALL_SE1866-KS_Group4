package org.example.kindergarten_management_system_g4.controller.authencation;

import org.example.kindergarten_management_system_g4.dao.AuthenDAO.LoginDAO;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        String email = req.getParameter("Email");
        String password = req.getParameter("password");

        try {
            User user = loginDAO.getUser(email, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user); // Lưu user vào session
                //session.setMaxInactiveInterval(100);

                if (user.getRoleId() == 4) {
                     req.getRequestDispatcher("index.jsp").forward(req, resp);
                  /*  resp.sendRedirect("index.jsp");*/
                } else if (user.getRoleId() == 1) {
                    resp.sendRedirect("ListProduct");

                }

            } else {
                req.setAttribute("Email", email);
                req.setAttribute("password", password);
                req.setAttribute("message1", "Username or password incorrect");
                req.getRequestDispatcher("Login.jsp").forward(req, resp);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
        }
}
        @Override
        protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doGet(req, resp);
        }
    }

