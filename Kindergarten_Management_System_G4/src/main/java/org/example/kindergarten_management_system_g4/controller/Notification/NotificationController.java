/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/1/2024       1.1                    vu gia huy              Update validate NotificationController
 */

/**
 * Lớp NotificationController quản lý các yêu cầu liên quan đến thông báo trong hệ thống.
 * Nó cung cấp các phương thức để xem, thêm, cập nhật, và xóa thông báo.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * @author Vũ Gia Huy
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
    // Đối tượng DAO để thực hiện các thao tác cơ sở dữ liệu liên quan đến thông báo
    private NotificationDAO notificationDAO;

    /**
     * Phương thức init để khởi tạo servlet, tạo instance của NotificationDAO
     *
     * @throws ServletException nếu có lỗi trong quá trình khởi tạo
     */
    @Override
    public void init() throws ServletException {
        super.init();
        notificationDAO = new NotificationDAO();
    }

    /**
     * Phương thức doGet để xử lý các yêu cầu GET, điều hướng đến phương thức tương ứng dựa trên URL action
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws ServletException nếu có lỗi servlet xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            // Điều hướng đến các hành động khác nhau dựa trên URL
            if (action.equals("/viewNotification")) {
                viewNotificationDetail(request, response);
            } else if (action.equals("/editNotification")) {
                showEditNotificationForm(request, response);
            } else if (action.equals("/deleteNotification")) {
                deleteNotification(request, response);
            } else {
                listNotifications(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Phương thức doPost để xử lý các yêu cầu POST, thực hiện thêm hoặc chỉnh sửa thông báo
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws ServletException nếu có lỗi servlet xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/addNotification")) {
            try {
                addNotification(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (action.equals("/editNotification")) {
            try {
                editNotification(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Thêm thông báo mới sau khi kiểm tra dữ liệu
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws SQLException nếu có lỗi cơ sở dữ liệu xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     * @throws ServletException nếu có lỗi servlet xảy ra
     */
    private void addNotification(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        String title = request.getParameter("title").trim().replaceAll("\\s+", " ");
        String content = request.getParameter("content").trim().replaceAll("\\s+", " ");
        java.util.Date date = new java.util.Date();

        // Kiểm tra dữ liệu đầu vào: tiêu đề và nội dung không được để trống
        if (title.isEmpty() || content.isEmpty()) {
            request.setAttribute("errorMessage", "Tiêu đề và nội dung không được để trống.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/addNotification.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (title.length() > 100) {
            request.setAttribute("errorMessage", "Tiêu đề không được vượt quá 100 ký tự.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/addNotification.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (content.length() > 500) {
            request.setAttribute("errorMessage", "Nội dung không được vượt quá 500 ký tự.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/addNotification.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Notification newNotification = new Notification(0, title, content, date);

        try {
            // Kiểm tra xem tiêu đề đã tồn tại hay chưa trong cơ sở dữ liệu
            if (notificationDAO.titleExists(title)) {
                request.setAttribute("errorMessage", "Tiêu đề đã tồn tại.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addNotification.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Thêm thông báo mới vào cơ sở dữ liệu và chuyển hướng về trang danh sách thông báo
            notificationDAO.addNotification(newNotification);
            response.sendRedirect(request.getContextPath() + "/Views/Admin/notifications");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/addNotification.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Cập nhật thông báo đã tồn tại sau khi kiểm tra dữ liệu
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws SQLException nếu có lỗi cơ sở dữ liệu xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     * @throws ServletException nếu có lỗi servlet xảy ra
     */
    private void editNotification(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID không hợp lệ.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/editNotification.jsp");
            dispatcher.forward(request, response);
            return;
        }

        String title = request.getParameter("title").trim().replaceAll("\\s+", " ");
        String content = request.getParameter("content").trim().replaceAll("\\s+", " ");

        // Kiểm tra dữ liệu đầu vào
        if (title.isEmpty() || content.isEmpty()) {
            request.setAttribute("errorMessage", "Tiêu đề và nội dung không được để trống.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/editNotification.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (title.length() > 100) {
            request.setAttribute("errorMessage", "Tiêu đề không được vượt quá 100 ký tự.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/editNotification.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (content.length() > 500) {
            request.setAttribute("errorMessage", "Nội dung không được vượt quá 500 ký tự.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/editNotification.jsp");
            dispatcher.forward(request, response);
            return;
        }

        java.sql.Date date;
        try {
            date = java.sql.Date.valueOf(request.getParameter("date"));
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Ngày không hợp lệ.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/editNotification.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Notification notification = new Notification(id, title, content, date);

        try {
            // Cập nhật thông báo trong cơ sở dữ liệu và chuyển hướng về trang danh sách thông báo
            notificationDAO.updateNotification(notification);
            response.sendRedirect(request.getContextPath() + "/Views/Admin/notifications");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/editNotification.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Hiển thị form chỉnh sửa thông báo dựa trên ID của thông báo
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws SQLException nếu có lỗi cơ sở dữ liệu xảy ra
     * @throws ServletException nếu có lỗi servlet xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    private void showEditNotificationForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int notificationId = Integer.parseInt(request.getParameter("id"));
        Notification notification = notificationDAO.getNotificationById(notificationId);

        request.setAttribute("notification", notification);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/editNotification.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Hiển thị danh sách các thông báo với phân trang
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws SQLException nếu có lỗi cơ sở dữ liệu xảy ra
     * @throws ServletException nếu có lỗi servlet xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    private void listNotifications(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * recordsPerPage;
        List<Notification> notifications = notificationDAO.getNotifications(offset, recordsPerPage);
        int totalRecords = notificationDAO.getTotalNotifications();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        request.setAttribute("notifications", notifications);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/notifications.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Hiển thị chi tiết thông báo dựa trên ID của thông báo
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws SQLException nếu có lỗi cơ sở dữ liệu xảy ra
     * @throws ServletException nếu có lỗi servlet xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    private void viewNotificationDetail(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int notificationId = Integer.parseInt(request.getParameter("id"));
        Notification notification = notificationDAO.getNotificationById(notificationId);

        request.setAttribute("notification", notification);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/viewNotification.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Xóa thông báo khỏi cơ sở dữ liệu dựa trên ID của thông báo
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws SQLException nếu có lỗi cơ sở dữ liệu xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    private void deleteNotification(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int notificationId = Integer.parseInt(request.getParameter("id"));
        notificationDAO.deleteNotification(notificationId);
        response.sendRedirect(request.getContextPath() + "/Views/Admin/notifications");
    }
}
