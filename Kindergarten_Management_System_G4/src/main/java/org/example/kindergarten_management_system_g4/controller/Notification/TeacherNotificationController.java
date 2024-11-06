/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/1/2024       1.1                    Vũ Gia Huy                      Initial creation of TeacherNotificationController
 */

package org.example.kindergarten_management_system_g4.controller.Notification;

import org.example.kindergarten_management_system_g4.dao.NotificationDAO.NotificationDAO;
import org.example.kindergarten_management_system_g4.model.Notification;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Lớp TeacherNotificationController phục vụ các yêu cầu liên quan đến việc hiển thị danh sách
 * thông báo dành cho giáo viên trong hệ thống.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * <p>URL: /teacher-notification</p>
 *
 * @see NotificationDAO
 * @see Notification
 */
@WebServlet(name = "TeacherNotificationController", value = "/teacher-notification")
public class TeacherNotificationController extends HttpServlet {
    private NotificationDAO notificationDAO; // Đối tượng DAO để thao tác với thông báo

    /**
     * Phương thức init để khởi tạo servlet, tạo instance của NotificationDAO
     *
     * @throws ServletException nếu có lỗi trong quá trình khởi tạo
     */
    @Override
    public void init() throws ServletException {
        notificationDAO = new NotificationDAO();
    }

    /**
     * Phương thức doGet để xử lý các yêu cầu GET, lấy danh sách thông báo và hiển thị trên trang dành cho giáo viên.
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws ServletException nếu có lỗi trong quá trình xử lý servlet
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy danh sách thông báo từ cơ sở dữ liệu
            List<Notification> notificationList = notificationDAO.getAllNotifications();

            // Thiết lập danh sách thông báo như một thuộc tính để truy cập trong JSP
            request.setAttribute("notificationList", notificationList);

            // Chuyển tiếp yêu cầu và phản hồi tới trang JSP để hiển thị danh sách thông báo cho giáo viên
            request.getRequestDispatcher("TeacherNewsList.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // Chuyển hướng tới trang lỗi nếu có lỗi trong quá trình lấy thông báo
            throw new ServletException("Error retrieving notifications", e);
        }
    }
}
