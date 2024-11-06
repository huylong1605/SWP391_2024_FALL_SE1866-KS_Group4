/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/1/2024       1.1                    Vũ Gia Huy                      Initial creation of SearchNotificationController
 */

package org.example.kindergarten_management_system_g4.controller.Notification;

import org.example.kindergarten_management_system_g4.dao.NotificationDAO.NotificationDAO;
import org.example.kindergarten_management_system_g4.dao.RoomDAO.IRoomDAO;
import org.example.kindergarten_management_system_g4.dao.RoomDAO.RoomDAO;
import org.example.kindergarten_management_system_g4.model.Notification;
import org.example.kindergarten_management_system_g4.model.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Lớp SearchNotificationController thực hiện tìm kiếm thông báo dựa trên tiêu đề.
 * Nếu không có tiêu đề tìm kiếm nào được cung cấp, lớp sẽ trả về tất cả thông báo.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * @see NotificationDAO
 */
@WebServlet(name = "searchNotification", value = "/searchNotification")
public class SearchNotificationController extends HttpServlet {
    private NotificationDAO notificationDAO; // Đối tượng DAO để thao tác với thông báo

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
     * Phương thức doGet để xử lý các yêu cầu GET, tìm kiếm thông báo theo tiêu đề hoặc trả về tất cả thông báo.
     *
     * @param req yêu cầu HTTP từ người dùng
     * @param resp phản hồi HTTP để gửi lại người dùng
     * @throws ServletException nếu có lỗi servlet xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchTitle = req.getParameter("searchTitle"); // Lấy tiêu đề tìm kiếm từ yêu cầu
        List<Notification> notifications; // Danh sách thông báo để trả về

        if (searchTitle != null && !searchTitle.isEmpty()) {
            try {
                // Tìm kiếm thông báo theo tiêu đề nếu tiêu đề tìm kiếm không rỗng
                notifications = notificationDAO.searchNotificationsByTitle(searchTitle);
            } catch (SQLException e) {
                throw new RuntimeException(e); // Xử lý lỗi SQL nếu có
            }
        } else {
            try {
                // Nếu không có tiêu đề tìm kiếm, trả về tất cả thông báo
                notifications = notificationDAO.getAllNotifications();
            } catch (SQLException e) {
                throw new RuntimeException(e); // Xử lý lỗi SQL nếu có
            }
        }

        // Đặt danh sách thông báo vào thuộc tính để hiển thị trong JSP
        req.setAttribute("notifications", notifications);
        req.getRequestDispatcher("/Views/Admin/notifications.jsp").forward(req, resp); // Chuyển tiếp tới trang danh sách thông báo
    }
}
