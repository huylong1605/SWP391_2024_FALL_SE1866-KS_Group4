package org.example.kindergarten_management_system_g4.controller;

import org.example.kindergarten_management_system_g4.dao.DAO;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "list", value = "/list")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();

        List<User> userList = dao.getAllUsers();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
