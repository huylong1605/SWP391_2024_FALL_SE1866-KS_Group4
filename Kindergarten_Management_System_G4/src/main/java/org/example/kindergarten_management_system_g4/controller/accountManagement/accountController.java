package org.example.kindergarten_management_system_g4.controller.accountManagement;

import org.example.kindergarten_management_system_g4.dao.AccountDAO.AccountDAO;
import org.example.kindergarten_management_system_g4.model.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class accountController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccountDAO accountDAO = new AccountDAO();
        try{
            List<User> accounts = accountDAO.getAllAccounts();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("/Admin-Dashbosh.jsp").forward(req, resp);
        }catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cant take list account");
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
