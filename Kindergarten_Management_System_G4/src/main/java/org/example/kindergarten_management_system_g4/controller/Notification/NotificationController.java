/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/1/2024       1.1                    vu gia huy              Update validate NotificationController
 */
package org.example.kindergarten_management_system_g4.controller.Notification;

import org.example.kindergarten_management_system_g4.dao.NotificationDAO.NotificationDAO;
import org.example.kindergarten_management_system_g4.model.Notification;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "NotificationController", value = {"/Views/Admin/notifications", "/viewNotification", "/addNotification", "/editNotification", "/deleteNotification"})
public class NotificationController extends HttpServlet {
    // Khai báo đối tượng NotificationDAO để thao tác với dữ liệu liên quan đến thông báo
    private NotificationDAO notificationDAO;

    // Phương thức init để khởi tạo servlet, tạo instance của NotificationDAO
    @Override
    public void init() throws ServletException {
        super.init();
        notificationDAO = new NotificationDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy URL action từ yêu cầu của người dùng
        String action = request.getServletPath();

        try {
            // Xử lý các hành động khác nhau dựa trên URL
            if (action.equals("/viewNotification")) {
                viewNotificationDetail(request, response); // Hiển thị chi tiết thông báo
            } else if (action.equals("/editNotification")) {
                showEditNotificationForm(request, response); // Hiển thị form chỉnh sửa thông báo
            } else if (action.equals("/deleteNotification")) {
                deleteNotification(request, response); // Xóa thông báo
            } else {
                listNotifications(request, response); // Hiển thị danh sách thông báo
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Xử lý các yêu cầu POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        // Nếu action là thêm thông báo
        if (action.equals("/addNotification")) {
            try {
                addNotification(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (action.equals("/editNotification")) {
            // Nếu action là chỉnh sửa thông báo
            try {
                editNotification(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Phương thức thêm thông báo
    private void addNotification(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        // Lấy dữ liệu từ request
        String title = request.getParameter("title").trim().replaceAll("\\s+", " ");
        String content = request.getParameter("content").trim().replaceAll("\\s+", " ");
        java.util.Date date = new java.util.Date(); // Ngày hiện tại
        Notification newNotification = new Notification(0, title, content, date); // Tạo đối tượng thông báo mới

        try {
            // Kiểm tra xem tiêu đề đã tồn tại chưa
            if (notificationDAO.titleExists(title)) {
                request.setAttribute("errorMessage", "Tiêu đề đã tồn tại."); // Nếu tiêu đề tồn tại, thông báo lỗi
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addNotification.jsp");
                dispatcher.forward(request, response);
                return;
            }
            // Thêm thông báo mới vào cơ sở dữ liệu
            notificationDAO.addNotification(newNotification);
            // Chuyển hướng về danh sách thông báo
            response.sendRedirect(request.getContextPath() + "/Views/Admin/notifications");
        } catch (SQLException e) {
            // Xử lý lỗi và gửi thông báo lỗi về phía người dùng
            request.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/addNotification.jsp");
            dispatcher.forward(request, response);
        }
    }

    // Phương thức chỉnh sửa thông báo
    private void editNotification(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id")); // Lấy ID của thông báo
        String title = request.getParameter("title").trim().replaceAll("\\s+", " ");
        String content = request.getParameter("content").trim().replaceAll("\\s+", " ");
        java.util.Date date = java.sql.Date.valueOf(request.getParameter("date")); // Lấy ngày từ request
        Notification notification = new Notification(id, title, content, date); // Tạo đối tượng thông báo với dữ liệu mới
        notificationDAO.updateNotification(notification); // Cập nhật thông báo
        response.sendRedirect(request.getContextPath() + "/Views/Admin/notifications"); // Chuyển hướng về danh sách thông báo
    }

    // Phương thức hiển thị form chỉnh sửa thông báo
    private void showEditNotificationForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int notificationId = Integer.parseInt(request.getParameter("id")); // Lấy ID thông báo từ request
        Notification notification = notificationDAO.getNotificationById(notificationId); // Lấy thông báo từ DB theo ID

        request.setAttribute("notification", notification); // Đặt thông báo vào request để hiển thị trong form
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/editNotification.jsp");
        dispatcher.forward(request, response); // Chuyển tiếp tới trang chỉnh sửa thông báo
    }

    // Phương thức hiển thị danh sách thông báo
    private void listNotifications(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int page = 1; // Số trang mặc định là 1
        int recordsPerPage = 5; // Mỗi trang hiển thị 5 thông báo
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page")); // Nếu có tham số trang, lấy trang tương ứng
        }
        int offset = (page - 1) * recordsPerPage; // Tính toán vị trí bắt đầu của bản ghi
        List<Notification> notifications = notificationDAO.getNotifications(offset, recordsPerPage); // Lấy danh sách thông báo
        int totalRecords = notificationDAO.getTotalNotifications(); // Tổng số bản ghi
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage); // Tính tổng số trang
        request.setAttribute("notifications", notifications); // Đặt thông báo vào request để hiển thị
        request.setAttribute("currentPage", page); // Trang hiện tại
        request.setAttribute("totalPages", totalPages); // Tổng số trang
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/notifications.jsp");
        dispatcher.forward(request, response); // Chuyển tiếp tới trang danh sách thông báo
    }

    // Phương thức hiển thị chi tiết thông báo
    private void viewNotificationDetail(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int notificationId = Integer.parseInt(request.getParameter("id")); // Lấy ID thông báo từ request
        Notification notification = notificationDAO.getNotificationById(notificationId); // Lấy thông báo từ DB theo ID

        request.setAttribute("notification", notification); // Đặt thông báo vào request để hiển thị chi tiết
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/viewNotification.jsp");
        dispatcher.forward(request, response); // Chuyển tiếp tới trang chi tiết thông báo
    }

    // Phương thức xóa thông báo
    private void deleteNotification(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int notificationId = Integer.parseInt(request.getParameter("id")); // Lấy ID thông báo từ request
        notificationDAO.deleteNotification(notificationId); // Xóa thông báo trong cơ sở dữ liệu
        response.sendRedirect(request.getContextPath() + "/Views/Admin/notifications"); // Chuyển hướng về danh sách thông báo
    }
}
