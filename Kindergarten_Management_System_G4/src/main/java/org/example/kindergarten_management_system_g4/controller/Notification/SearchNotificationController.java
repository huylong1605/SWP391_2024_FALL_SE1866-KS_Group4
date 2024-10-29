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

@WebServlet(name = "searchNotification", value = "/searchNotification")
public class SearchNotificationController extends HttpServlet {
    private NotificationDAO notificationDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        notificationDAO = new NotificationDAO();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchTitle = req.getParameter("searchTitle");
        List<Notification> notifications;
        if (searchTitle != null && !searchTitle.isEmpty()) {
            try {
                notifications = notificationDAO.searchNotificationsByTitle(searchTitle);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                notifications = notificationDAO.getAllNotifications();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        req.setAttribute("notifications", notifications);
        req.getRequestDispatcher("/Views/Admin/notifications.jsp").forward(req, resp);
    }
}
