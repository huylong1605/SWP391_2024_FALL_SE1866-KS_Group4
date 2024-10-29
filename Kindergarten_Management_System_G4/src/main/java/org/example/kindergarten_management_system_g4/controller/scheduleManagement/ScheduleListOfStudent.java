package org.example.kindergarten_management_system_g4.controller.scheduleManagement;

import org.example.kindergarten_management_system_g4.controller.classManagement.ClassDetailController;
import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.impliment.ClassDAOImpl;
import org.example.kindergarten_management_system_g4.dao.scheduledao.IScheduleDAO;
import org.example.kindergarten_management_system_g4.dao.scheduledao.implimentation.ScheduleDAOImpl;
import org.example.kindergarten_management_system_g4.model.Schedule;
import org.example.kindergarten_management_system_g4.model.User;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "scheduleStudent", value = "/scheduleStudent")
public class ScheduleListOfStudent extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ScheduleListOfStudent.class.getName());
    private IScheduleDAO iScheduleDAO; // Interface cho các phương thức quản lý lớp học

    /**
     * Phương thức init được gọi khi servlet được khởi tạo.
     * Sử dụng để khởi tạo các đối tượng cần thiết, chẳng hạn như iClassDAO.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        iScheduleDAO = new ScheduleDAOImpl(); // Khởi tạo đối tượng DAO cho lớp học
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User userGet = (User) session.getAttribute("user");
        LOGGER.info("User " + userGet);
        try {

            List<Schedule> listScheduleStudent =  iScheduleDAO.scheduleOfStudent(7);


            req.setAttribute("listScheduleStudent", listScheduleStudent);
            req.getRequestDispatcher("scheduleStudent.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
