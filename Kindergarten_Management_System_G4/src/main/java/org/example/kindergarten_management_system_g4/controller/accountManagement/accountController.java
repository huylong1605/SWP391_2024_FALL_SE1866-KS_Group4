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
import java.util.Random;


@WebServlet(value = {"/Views/Admin/accountManage", "/Views/Admin/accountManage/Detail", "/Views/Admin/accountManage/Create" })
public class accountController extends HttpServlet {
    private AccountDAO accountDAO;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public void init() throws ServletException {
        accountDAO = new AccountDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/Views/Admin/accountManage/Detail")) {
            showAccountDetails(req, resp);
        } else {
            listAccounts(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("userId");
        String action = req.getParameter("action");

        // Nếu userIdParam không null, xử lý trạng thái tài khoản
        if (userIdParam != null) {
            int userId = Integer.parseInt(userIdParam);
            try {
                accountDAO.toggleAccountStatus(userId);
                resp.sendRedirect(req.getContextPath() + "/Views/Admin/accountManage");
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to toggle account status");
                return;
            }
        }

        // Nếu action là createAccount, xử lý tạo tài khoản
        if ("create".equals(action)) {
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            String roleIdParam = req.getParameter("role"); // Lấy vai trò từ form

            if (fullname == null || email == null || roleIdParam == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
                return;
            }

            int roleId;
            try {
                roleId = Integer.parseInt(roleIdParam); // Chuyển đổi vai trò thành số nguyên
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid role ID");
                return;
            }

            try {
                if (accountDAO.isEmailExists(email)) {
                    req.setAttribute("errorMessage", "Email has exist, please use another email");
                    req.getRequestDispatcher("/Views/Admin/CreateAccount.jsp").forward(req, resp); // Chuyển hướng lại trang với thông báo
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to check email existence");
                return;
            }

            try {
                accountDAO.createAccount(fullname, email, roleId);
                req.getSession().setAttribute("successMessage", "Create account successfully"); // Lưu thông báo vào session
                resp.sendRedirect(req.getContextPath() + "/Views/Admin/accountManage"); // Chuyển hướng đến accountManage
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to create account");
                return;
            }

        }

        if ("search".equals(action)) {
            String searchName = req.getParameter("searchName");
            req.setAttribute("searchName", searchName); // Lưu lại giá trị tìm kiếm
            // Gọi phương thức để lấy danh sách tài khoản theo tên
            listAccounts(req, resp);
            return;
        }

        // Xử lý lọc theo vai trò
        if ("filter".equals(action)) {
            String roleFilter = req.getParameter("roleFilter");
            req.setAttribute("roleFilter", roleFilter); // Lưu lại giá trị lọc
            // Gọi phương thức để lấy danh sách tài khoản theo vai trò
            listAccounts(req, resp);
            return;
        }


        super.doPost(req, resp);
    }

    private void listAccounts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageSize = 7; // Số lượng tài khoản trên mỗi trang
        int currentPage = 1; // Trang hiện tại

        String pageParam = req.getParameter("pageNumber");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid page number");
                return;
            }
        }

        // Thêm tham số tìm kiếm và lọc
        String searchName = req.getParameter("searchName");
        String roleIdParam = req.getParameter("roleFilter");
        Integer roleId = null;

        if (roleIdParam != null && !roleIdParam.isEmpty()) {
            try {
                roleId = Integer.parseInt(roleIdParam); // Chuyển đổi vai trò thành số nguyên
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid role ID");
                return;
            }
        }

        try {
            int totalAccounts = accountDAO.getAccountCount(searchName, roleId); // Lấy tổng số tài khoản từ DAO với điều kiện tìm kiếm và lọc
            int totalPages = (int) Math.ceil((double) totalAccounts / pageSize); // Tính số trang
            List<User> accounts = accountDAO.getAccounts(currentPage, pageSize, searchName, roleId); // Lấy danh sách tài khoản cho trang hiện tại

            req.setAttribute("accounts", accounts);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("searchName", searchName);
            req.setAttribute("roleFilter", roleIdParam);
            req.getRequestDispatcher("/Views/Admin/accountManage.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot fetch account list");
        }
    }


    private void showAccountDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        try {
            User account = accountDAO.getAccountById(userId);
            req.setAttribute("account", account);
            req.getRequestDispatcher("/Views/Admin/accountDetail.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch account details");
        }
    }

}




