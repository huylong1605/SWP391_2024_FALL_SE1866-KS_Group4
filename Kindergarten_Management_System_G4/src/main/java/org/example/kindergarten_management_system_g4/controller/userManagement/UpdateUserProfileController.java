package org.example.kindergarten_management_system_g4.controller.userManagement;

import org.example.kindergarten_management_system_g4.dao.UserProfileDAO;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "UpdateUserProfileController", value = "/updateprofile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class UpdateUserProfileController extends HttpServlet {
    private UserProfileDAO userProfileDAO;

    @Override
    public void init() throws ServletException {
        userProfileDAO = new UserProfileDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser != null) {
            User user = userProfileDAO.getUserById(sessionUser.getUserID());
            if (user != null) {
                req.setAttribute("user", user);
                req.getRequestDispatcher("/updateProfile.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } else {
            resp.sendRedirect("Login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            resp.sendRedirect("Login.jsp");
            return;
        }

        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        int gender = Integer.parseInt(req.getParameter("gender"));
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");
        Part image = req.getPart("image");

        User user = new User();
        user.setUserID(sessionUser.getUserID());
        user.setFullname(fullname);
        user.setEmail(email);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setImage(String.valueOf(image));
        System.out.println(user);
        boolean updateSuccess = userProfileDAO.updateUserProfile(user);

        if (updateSuccess) {
            resp.sendRedirect("viewprofile");
        } else {
            req.setAttribute("errorMessage", "Cập nhật thông tin thất bại.");
            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
        }
    }
}
