
/*
 * Copyright(C) 2005,  <SWP_G4>.
 * <KMS> :
 *  <Kindergarten Management System>
 *
 * Record of change:
 * DATE                       Version             AUTHOR                       DESCRIPTION
 * <10/2/2024>                 <1.1>           <Vu Viet Chuc>            <Update List Account method>
 */

package org.example.kindergarten_management_system_g4.controller.accountManagement;

import org.example.kindergarten_management_system_g4.dao.accountDAO.AccountDAO;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Lớp `accountController` xử lý các yêu cầu liên quan đến quản lý tài khoản
 * trong hệ thống quản lý trường mẫu giáo (Kindergarten Management System).
 * Lớp này quản lý các hành động như liệt kê tài khoản, hiển thị chi tiết tài khoản,
 * tạo tài khoản mới, và thay đổi trạng thái tài khoản. Nó cũng hỗ trợ tìm kiếm và
 * lọc tài khoản theo role
 *
 * <p>Lỗi: Hiện tại chưa có lỗi nào được ghi nhận.
 *
 * @author Vu Viet Chuc
 */

@WebServlet(value = {"/Views/Admin/accountManage", "/Views/Admin/accountManage/Detail", "/Views/Admin/accountManage/Create"})
public class AccountController extends HttpServlet {
    private AccountDAO accountDAO;

    @Override
    public void init() throws ServletException {
        accountDAO = new AccountDAO();
    }

    /**
     * Xử lý yêu cầu GET từ client.
     * Nếu đường dẫn yêu cầu là "/Views/Admin/accountManage/Detail",
     * phương thức sẽ hiển thị chi tiết tài khoản.
     * Nếu không, nó sẽ hiển thị danh sách tài khoản.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ client.
     * @param resp Đối tượng HttpServletResponse dùng để phản hồi lại client.
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý yêu cầu.
     * @throws IOException      nếu có lỗi xảy ra trong quá trình nhập/xuất dữ liệu.
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/Views/Admin/accou" +
                "ntManage/Detail")) {
            // Hien thi chi tiet tai khoan
            showAccountDetails(req, resp);
        } else {
            // Hien thi danh sach tai khoan
            listAccounts(req, resp);
        }
    }

    /**
     * Xử lý yêu cầu POST từ client.
     * Method này có thể thực hiện nhiều hành động như thay đổi trạng thái tài khoản,
     * tạo tài khoản mới, tìm kiếm tài khoản, và lọc tài khoản theo vai trò.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ client.
     * @param resp Đối tượng HttpServletResponse dùng để phản hồi lại client.
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý yêu cầu.
     * @throws IOException      nếu có lỗi xảy ra trong quá trình nhập/xuất dữ liệu.
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("userId"); // Lay id cua account
        String action = req.getParameter("action"); // lay hanh dong tu yeu cau

        if (userIdParam != null) {
            int userId = Integer.parseInt(userIdParam);
            try {
                // Thay doi trang thai cua tai khoan
                accountDAO.toggleAccountStatus(userId);
                resp.sendRedirect(req.getContextPath() + "/Views/Admin/accountManage");
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to toggle account status");
                return;
            }
        }

        // Neu co yeu cau tao tai khoan moi
        if ("create".equals(action)) {
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");
            String roleIdParam = req.getParameter("role");

            // Kiem tra cac tham so bat buoc
            if (fullname == null || email == null || roleIdParam == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters");
                return;
            }

            int roleId;
            try {
                // Chuyen doi role thanh so nguyen
                roleId = Integer.parseInt(roleIdParam);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid role ID");
                return;
            }

            try {
                // Kiem tra xem email co ton tai hay khong
                if (accountDAO.isEmailExists(email)) {
                    req.setAttribute("errorMessage", "Email has exist, please use another email");
                    req.getRequestDispatcher("/Views/Admin/CreateAccount.jsp").forward(req, resp);
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to check email existence");
                return;
            }

            try {
                // Tao tai khoan moi
                accountDAO.createAccount(fullname, email, roleId);
                req.getSession().setAttribute("successMessage", "Create account successfully");
                resp.sendRedirect(req.getContextPath() + "/Views/Admin/accountManage");
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to create account");
                return;
            }
        }

        // Tim kiem account by name
        if ("search".equals(action)) {
            String searchName = req.getParameter("searchName");
            req.setAttribute("searchName", searchName);
            listAccounts(req, resp);
            return;
        }

        // Fillter theo vai tro
        if ("filter".equals(action)) {
            String roleFilter = req.getParameter("roleFilter");
            req.setAttribute("roleFilter", roleFilter);
            listAccounts(req, resp);
            return;
        }
        // Neu khong co hanh dong thi goi doPost cua lop cha
        super.doPost(req, resp);
    }

    /**
     * Liệt kê danh sách tài khoản với phân trang.
     * Phương thức này sẽ lấy danh sách tài khoản dựa trên các tham số tìm kiếm
     * và lọc từ yêu cầu và chuyển tiếp đến trang JSP hiển thị danh sách tài khoản.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ client.
     * @param resp Đối tượng HttpServletResponse dùng để phản hồi lại client.
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý yêu cầu.
     * @throws IOException      nếu có lỗi xảy ra trong quá trình nhập/xuất dữ liệu.
     */

    // Liet ke danh sach tai khoan voi phan trang
    private void listAccounts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageSize = 7; // So tai khoan tren moi trang
        int currentPage = 1; // Trang hien tai

        // Lay so trang tu yeu cau
        String pageParam = req.getParameter("pageNumber");
        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid page number");
                return;
            }
        }

        String searchName = req.getParameter("searchName");
        String roleIdParam = req.getParameter("roleFilter");
        Integer roleId = null;

        // Chuyen doi role tu chuoi thanh so nguyen neu co
        if (roleIdParam != null && !roleIdParam.isEmpty()) {
            try {
                roleId = Integer.parseInt(roleIdParam);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid role ID");
                return;
            }
        }

        try {
            // Lay tong so account va tinh tong so trang
            int totalAccounts = accountDAO.getAccountCount(searchName, roleId);
            int totalPages = (int) Math.ceil((double) totalAccounts / pageSize);
            List<User> accounts = accountDAO.getAccounts(currentPage, pageSize, searchName, roleId);

            // Gui thong tin list account, trang hien tai va tong so trang toi trang JSP
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

    /**
     * Hiển thị chi tiết tài khoản dựa trên ID tài khoản.
     * Phương thức này sẽ lấy thông tin chi tiết tài khoản từ cơ sở dữ liệu
     * và chuyển tiếp đến trang JSP hiển thị thông tin tài khoản.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ client.
     * @param resp Đối tượng HttpServletResponse dùng để phản hồi lại client.
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý yêu cầu.
     * @throws IOException      nếu có lỗi xảy ra trong quá trình nhập/xuất dữ liệu.
     */
    //Hien thi chi tiet tai khoan
    private void showAccountDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        try {
            // Lay details account theo ID va chuyen tiep toi trang details account
            User account = accountDAO.getAccountById(userId);
            req.setAttribute("account", account);
            req.getRequestDispatcher("/Views/Admin/accountDetail.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch account details");
        }
    }
}
