package org.example.kindergarten_management_system_g4.controller.userManagement;

import org.example.kindergarten_management_system_g4.dao.UserProfileDAO;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ViewUserProfileController", value = "/viewprofile")
public class ViewUserProfileController extends HttpServlet {
    private UserProfileDAO userProfileDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userProfileDAO = new UserProfileDAO(); // Bạn cần đảm bảo rằng DAO này có kết nối CSDL
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        System.out.println(sessionUser.getUserID());
        if (sessionUser != null) {
            // Lấy thông tin user từ DAO
            User user = userProfileDAO.getUserById(sessionUser.getUserID());
            System.out.println(sessionUser.getUserID());
            if (user != null) {

                req.setAttribute("user", user);
                req.getRequestDispatcher("/viewProfile.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("/Login.jsp");
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } else {
            resp.sendRedirect("login");
        }
    }
}
