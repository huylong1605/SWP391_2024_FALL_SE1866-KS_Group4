package org.example.kindergarten_management_system_g4.controller.scheduleManagement;

import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.impliment.ClassDAOImpl;
import org.example.kindergarten_management_system_g4.dao.scheduledao.IScheduleDAO;
import org.example.kindergarten_management_system_g4.dao.scheduledao.implimentation.ScheduleDAOImpl;
import org.example.kindergarten_management_system_g4.model.Classes;
import org.example.kindergarten_management_system_g4.model.ScheduleDAL;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@WebServlet(name = "listSchedule", value = "/listSchedule")
public class ListSchedule extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ListSchedule.class.getName());
    private IScheduleDAO iScheduleDAO; // Interface cho các phương thức quản lý lớp học
    private IClassDAO iClassDAO;
    /**
     * Phương thức init được gọi khi servlet được khởi tạo.
     * Sử dụng để khởi tạo các đối tượng cần thiết, chẳng hạn như iClassDAO.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        iScheduleDAO = new ScheduleDAOImpl();
        iClassDAO = new ClassDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<LocalDate> dates = new ArrayList<>();
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = LocalDate.of(2026, 12, 31);

            while (!startDate.isAfter(endDate)) {
                dates.add(startDate);
                startDate = startDate.plusDays(1); // Tăng thêm một ngày
        }


        String classId = req.getParameter("classSelect");
        String startDateGet = req.getParameter("startDate");
        String endDateGet = req.getParameter("endDate");

        try {
            List<Classes> classDALList = iScheduleDAO.getListClass();
            List<ScheduleDAL> scheduleDALS = new ArrayList<>();

            if (classId != null && !classId.isEmpty()) {
                scheduleDALS = iScheduleDAO.getListScheduleByClass(Integer.parseInt(classId), startDateGet, endDateGet);
                LOGGER.info("list schedule: "+ scheduleDALS );
            }
            HttpSession session = req.getSession();
           String EditScheduleSuccessful = (String) session.getAttribute("EditScheduleSuccessful");

            req.setAttribute("dates", dates);
            req.setAttribute("Classes", classDALList);
            req.setAttribute("schedules", scheduleDALS);
            req.setAttribute("EditScheduleSuccessful", EditScheduleSuccessful);
            req.getRequestDispatcher("listScheduleManagement.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
