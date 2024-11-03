/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/2/2024       1.1              Đào Xuân Bình - HE163115        Update Profile
 */

package org.example.kindergarten_management_system_g4.controller.profileManagement;

import org.example.kindergarten_management_system_g4.dao.profileDAO.UserProfileDAO;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Lớp UpdateUserProfileController chịu trách nhiệm xử lý các yêu cầu HTTP GET và POST để cập nhật thông tin hồ sơ người dùng.
 * Yêu cầu có thể bao gồm việc cập nhật tên, email, giới tính, số điện thoại, địa chỉ, và ảnh đại diện của người dùng.
 * <p>Lỗi: Chưa phát hiện lỗi.
 *
 * @author Đào Xuân Bình
 */

// Định nghĩa servlet với tên "UpdateUserProfileController" và ánh xạ đến URL "/updateprofile"
@WebServlet(name = "UpdateUserProfileController", value = "/updateprofile")

// Cấu hình cho việc tải file lên với các giới hạn về kích thước
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // Ngưỡng kích thước 2MB, nếu vượt quá sẽ ghi tệp vào đĩa
        maxFileSize = 1024 * 1024 * 10,       // Kích thước tệp tối đa là 10MB
        maxRequestSize = 1024 * 1024 * 50     // Kích thước yêu cầu tối đa là 50MB
)
public class UpdateUserProfileController extends HttpServlet {

    // Khai báo đối tượng UserProfileDAO để tương tác với cơ sở dữ liệu
    private UserProfileDAO userProfileDAO;

    /**
     * Phương thức khởi tạo servlet, được gọi khi servlet được tạo ra.
     * Khởi tạo đối tượng UserProfileDAO.
     */
    @Override
    public void init() throws ServletException {
        userProfileDAO = new UserProfileDAO(); // Khởi tạo đối tượng UserProfileDAO
    }

    /**
     * Xử lý các yêu cầu GET từ phía client để hiển thị trang cập nhật hồ sơ người dùng.
     * Nếu người dùng đã đăng nhập, lấy thông tin chi tiết từ cơ sở dữ liệu và hiển thị trên giao diện cập nhật hồ sơ.
     * Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu GET
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(); // Lấy phiên làm việc (session) của người dùng
        User sessionUser = (User) session.getAttribute("user"); // Lấy thông tin người dùng từ session

        // Kiểm tra nếu người dùng đã đăng nhập
        if (sessionUser != null) {
            // Lấy thông tin chi tiết của người dùng từ cơ sở dữ liệu dựa trên userID
            User user = userProfileDAO.getUserById(sessionUser.getUserID());
            if (user != null) {
                // Đặt thông tin người dùng vào request attribute và chuyển tiếp đến trang cập nhật hồ sơ
                req.setAttribute("user", user);
                req.getRequestDispatcher("/updateProfile.jsp").forward(req, resp);
            } else {
                // Nếu không tìm thấy người dùng, trả về lỗi 404 (Không tìm thấy người dùng)
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } else {
            // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
            resp.sendRedirect("/Login.jsp");
        }
    }

    /**
     * Xử lý các yêu cầu POST từ phía client để cập nhật hồ sơ người dùng.
     * Nếu người dùng đã đăng nhập, lấy thông tin từ form cập nhật và lưu lại trong cơ sở dữ liệu.
     * Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu POST
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(); // Lấy phiên làm việc (session) của người dùng
        User sessionUser = (User) session.getAttribute("user"); // Lấy thông tin người dùng từ session

        // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
        if (sessionUser == null) {
            resp.sendRedirect("Login.jsp");
            return;
        }

        // Lấy thông tin từ biểu mẫu cập nhật hồ sơ
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        int gender = Integer.parseInt(req.getParameter("gender"));  // Giới tính: 0 - Nữ, 1 - Nam
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");
        Part image = req.getPart("image");  // Lấy tệp hình ảnh từ biểu mẫu

        // Tạo đối tượng người dùng và cập nhật thông tin người dùng
        User user = new User();
        user.setUserID(sessionUser.getUserID());  // Giữ nguyên userID từ session
        user.setFullname(fullname);  // Cập nhật họ tên
        user.setEmail(email);  // Cập nhật email
        user.setGender(gender);  // Cập nhật giới tính
        user.setPhoneNumber(phoneNumber);  // Cập nhật số điện thoại
        user.setAddress(address);  // Cập nhật địa chỉ
        user.setImage(String.valueOf(image));  // Cập nhật ảnh đại diện (chuyển sang định dạng chuỗi)
        System.out.println(user);  // In thông tin người dùng ra console để kiểm tra lỗi

        // Cập nhật hồ sơ người dùng trong cơ sở dữ liệu
        boolean updateSuccess = userProfileDAO.updateUserProfile(user);

        // Nếu cập nhật thành công, chuyển hướng đến trang xem hồ sơ
        if (updateSuccess) {
            resp.sendRedirect("viewprofile");
        } else {
            // Nếu cập nhật thất bại, hiển thị thông báo lỗi và quay lại trang cập nhật hồ sơ
            req.setAttribute("errorMessage", "Profile update failed.");
            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
        }
    }
}
