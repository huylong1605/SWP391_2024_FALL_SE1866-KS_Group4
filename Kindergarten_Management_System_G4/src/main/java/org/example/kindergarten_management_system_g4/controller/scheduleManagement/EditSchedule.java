package org.example.kindergarten_management_system_g4.controller.scheduleManagement;

import org.example.kindergarten_management_system_g4.dao.scheduledao.IScheduleDAO;
import org.example.kindergarten_management_system_g4.dao.scheduledao.implimentation.ScheduleDAOImpl;
import org.example.kindergarten_management_system_g4.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "editSchedule", value = "/editSchedule")
public class EditSchedule extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EditSchedule.class.getName());
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
         int schedulesId = Integer.parseInt(req.getParameter("schedulesId"));

        try {
            Schedule schedule = iScheduleDAO.getScheduleById(schedulesId);
            List<Term> listTerm = iScheduleDAO.getListTerm();
            List<Subject> listSubject = iScheduleDAO.getListSubject();
            List<Classes> listClass = iScheduleDAO.getListClass();
            List<Slot> listSlot = iScheduleDAO.getListSlot();
            req.setAttribute("listTerm", listTerm);
            req.setAttribute("listSubject", listSubject);
            req.setAttribute("listClass", listClass);
            req.setAttribute("listSlot", listSlot);
            req.setAttribute("schedule", schedule);
            req.getRequestDispatcher("editSchedule.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
