package org.example.kindergarten_management_system_g4.controller;

import org.example.kindergarten_management_system_g4.dao.NotificationDAO;
import org.example.kindergarten_management_system_g4.dao.StudentDAO;
import org.example.kindergarten_management_system_g4.model.Notification;
import org.example.kindergarten_management_system_g4.model.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "NotificationController", value = {"/Views/Admin/notifications", "/viewNotification", "/addNotification", "/editNotification","/deleteNotification"})
public class NotificationController extends HttpServlet {
    private NotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        notificationDAO = new NotificationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            if (action.equals("/viewNotification")) {
                viewNotificationDetail(request, response);
            } else if (action.equals("/editNotification")) {
                showEditNotificationForm(request, response);
            } else if (action.equals("/deleteNotification")) {
                deleteNotification(request, response); // Thêm dòng này
            } else {
                listNotifications(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
        }else if (action.equals("/editNotification")) {
            try {
                editNotification(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void addNotification(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        java.util.Date date = new java.util.Date(); // Lấy ngày hiện tại
        Notification newNotification = new Notification(0, title, content, date);

        try {
            // Kiểm tra xem tiêu đề đã tồn tại chưa
            if (notificationDAO.titleExists(title)) {
                request.setAttribute("errorMessage", "Tiêu đề đã tồn tại."); // Thiết lập thông báo lỗi
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addNotification.jsp");
                dispatcher.forward(request, response);
                return;
            }
            notificationDAO.addNotification(newNotification);
            response.sendRedirect(request.getContextPath() + "/Views/Admin/notifications");
        } catch (SQLException e) {
            // Xử lý ngoại lệ và gửi thông báo lỗi về phía người dùng
            request.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/addNotification.jsp");
            dispatcher.forward(request, response);
        }
    }




    private void editNotification(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        java.util.Date date = java.sql.Date.valueOf(request.getParameter("date"));
        Notification notification = new Notification(id, title, content, date);
        notificationDAO.updateNotification(notification);
        response.sendRedirect(request.getContextPath() + "/Views/Admin/notifications");
    }
    private void showEditNotificationForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int notificationId = Integer.parseInt(request.getParameter("id"));
        Notification notification = notificationDAO.getNotificationById(notificationId);

        request.setAttribute("notification", notification);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/editNotification.jsp");
        dispatcher.forward(request, response);
    }
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


    private void viewNotificationDetail(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int notificationId = Integer.parseInt(request.getParameter("id"));
        Notification notification = notificationDAO.getNotificationById(notificationId);

        request.setAttribute("notification", notification);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Views/Admin/viewNotification.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteNotification(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int notificationId = Integer.parseInt(request.getParameter("id"));
        notificationDAO.deleteNotification(notificationId);
        response.sendRedirect(request.getContextPath() + "/Views/Admin/notifications");
    }
}
