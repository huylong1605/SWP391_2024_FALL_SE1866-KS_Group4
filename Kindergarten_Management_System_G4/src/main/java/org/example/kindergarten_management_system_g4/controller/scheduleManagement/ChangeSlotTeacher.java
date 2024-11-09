/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 6/11/2024       1.1              Nguyễn Huy Long - He160140             Change slot teacher
 */

package org.example.kindergarten_management_system_g4.controller.scheduleManagement;
import org.example.kindergarten_management_system_g4.dao.scheduledao.IScheduleDAO;
import org.example.kindergarten_management_system_g4.dao.scheduledao.implimentation.ScheduleDAOImpl;
import org.example.kindergarten_management_system_g4.model.Schedule;
import org.example.kindergarten_management_system_g4.model.Slot;
import org.example.kindergarten_management_system_g4.model.Subject;
import org.example.kindergarten_management_system_g4.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;


@WebServlet(name = "changeSlotTeacher", value = "/changeSlotTeacher")
public class ChangeSlotTeacher extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ChangeSlotTeacher.class.getName());
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

    /**
     * Xử lý yêu cầu GET để lấy thông tin lịch học và slot cho ID lịch cụ thể.
     * Thiết lập các thuộc tính liên quan cho JSP để hiển thị.
     *
     * @param req Đối tượng HttpServletRequest
     * @param resp Đối tượng HttpServletResponse
     * @throws ServletException nếu xảy ra lỗi trong xử lý yêu cầu
     * @throws IOException nếu phát hiện lỗi nhập/xuất
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int schedulesId = Integer.parseInt(req.getParameter("schedulesId"));

        try {
            Schedule schedule = iScheduleDAO.getScheduleById(schedulesId);
            Slot slot = iScheduleDAO.getSlotByScheduleId(schedulesId);

            Subject subject = iScheduleDAO.getSubjectByScheduleId(schedulesId);
            /*List<Term> listTerm = iScheduleDAO.getListTerm();
            List<Subject> listSubject = iScheduleDAO.getListSubject();
            List<Classes> listClass = iScheduleDAO.getListClass();*/
            List<Slot> listSlot = iScheduleDAO.getListSlot();
            /*req.setAttribute("listTerm", listTerm);*/
            req.setAttribute("subjectById", subject);
            /*req.setAttribute("dayOfWeek", schedule.getDayOfWeek());*/
            /*req.setAttribute("listSubject", listSubject);*/
            /*req.setAttribute("listClass", listClass);*/
            req.setAttribute("listSlot", listSlot);
            req.setAttribute("slot", slot);
            req.setAttribute("schedules", schedule);
            req.getRequestDispatcher("changeSlot.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            throw new RuntimeException(e);
        }
    }

    /**
     * Xử lý yêu cầu POST để thay đổi slot của lịch học cho ID lịch cụ thể.
     * Xác minh ngày nhập vào, kiểm tra xung đột lịch, và cập nhật slot nếu hợp lệ.
     *
     * @param req Đối tượng HttpServletRequest
     * @param resp Đối tượng HttpServletResponse
     * @throws ServletException nếu xảy ra lỗi trong xử lý yêu cầu
     * @throws IOException nếu phát hiện lỗi nhập/xuất
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int schedulesId = Integer.parseInt(req.getParameter("scheduleIdd"));
        String dateOfDay = req.getParameter("dateChange");
        int slotId = Integer.parseInt(req.getParameter("slotChange"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate selectedDate = LocalDate.parse(dateOfDay, dateFormatter);
            LocalDate currentDate = LocalDate.now();
            if (selectedDate.isBefore(currentDate)) {
                Schedule schedule = iScheduleDAO.getScheduleById(schedulesId);
                Slot slot = iScheduleDAO.getSlotByScheduleId(schedulesId);

                Subject subject = iScheduleDAO.getSubjectByScheduleId(schedulesId);

                List<Slot> listSlot = iScheduleDAO.getListSlot();

                req.setAttribute("subjectById", subject);

                req.setAttribute("listSlot", listSlot);
                req.setAttribute("slot", slot);
                req.setAttribute("schedules", schedule);
                req.setAttribute("dateFalseUpdate", "Do not leave the date in the past");
                req.getRequestDispatcher("changeSlot.jsp").forward(req, resp);
                return;
            }

            Schedule scheduleCheck = new Schedule(schedulesId, dateOfDay, slotId);
            Boolean isCheckExistSchedule = iScheduleDAO.getSchedule2(scheduleCheck);
            if (isCheckExistSchedule) {
                // Đảm bảo `data` không cam kết phản hồi
                Schedule schedule = iScheduleDAO.getScheduleById(schedulesId);
                Slot slot = iScheduleDAO.getSlotByScheduleId(schedulesId);

                Subject subject = iScheduleDAO.getSubjectByScheduleId(schedulesId);

                List<Slot> listSlot = iScheduleDAO.getListSlot();

                req.setAttribute("subjectById", subject);

                req.setAttribute("listSlot", listSlot);
                req.setAttribute("slot", slot);
                req.setAttribute("schedules", schedule);
                req.setAttribute("ExistSchedule", "Due to scheduling conflicts, please check the date and class slot again");
                req.getRequestDispatcher("changeSlot.jsp").forward(req, resp);
                return;
            }

            DayOfWeek dayOfWeekEnum = selectedDate.getDayOfWeek();
            String dayOfWeek = dayOfWeekEnum.toString().toLowerCase();
            if (dayOfWeek.equalsIgnoreCase("SUNDAY")) {
                Schedule schedule = iScheduleDAO.getScheduleById(schedulesId);
                Slot slot = iScheduleDAO.getSlotByScheduleId(schedulesId);

                Subject subject = iScheduleDAO.getSubjectByScheduleId(schedulesId);

                List<Slot> listSlot = iScheduleDAO.getListSlot();

                req.setAttribute("subjectById", subject);

                req.setAttribute("listSlot", listSlot);
                req.setAttribute("slot", slot);
                req.setAttribute("schedules", schedule);
                req.setAttribute("sunday", "can not choose on " + dayOfWeek);
                req.getRequestDispatcher("changeSlot.jsp").forward(req, resp);
                return;
            }
            HttpSession session = req.getSession();

            // Lấy thông báo từ phiên nếu có

            Schedule schedule = new Schedule(schedulesId, dayOfWeek, dateOfDay, slotId);
            iScheduleDAO.changeSlot(schedule);
            /*HttpSession session = req.getSession();*/
            User user = (User) session.getAttribute("user");
            // Đặt thông báo thành công vào session
            session.setAttribute("changeSlotSuccessful", "Change slot successfully");
            // Chuyển hướng đến danh sách lớp
            resp.sendRedirect("Views/Teacher/teacherSchedule?teacherId=" + user.getUserID());
        } catch (SQLException e) {
            LOGGER.info("Exeption change slot: " + e.getMessage());
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            throw new RuntimeException(e);
        }
    }
}
