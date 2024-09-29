package org.example.kindergarten_management_system_g4.controller.accountManagement;

import org.example.kindergarten_management_system_g4.dao.AccountDAO.AccountDAO;
import org.example.kindergarten_management_system_g4.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(value = {"/Views/Admin/accountManage", "/Views/Admin/accountManage/Detail", "/Views/Admin/accountManage/Create" })
public class accountController extends HttpServlet {
    private AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> accounts = accountDAO.getAllAccounts();
            req.setAttribute("accounts", accounts);
            req.getRequestDispatcher("/Views/Admin/accountManage.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cant not take list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("userId");
        if (userIdParam != null) {
            int userId = Integer.parseInt(userIdParam);

            try {
                accountDAO.toggleAccountStatus(userId);
                resp.sendRedirect(req.getContextPath() + "/Views/Admin/accountManage");
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to toggle account status");
            }
        } else {
            super.doPost(req, resp);
        }
    }
}




