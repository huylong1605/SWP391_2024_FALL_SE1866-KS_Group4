package org.example.kindergarten_management_system_g4.controller.scheduleManagement;

import org.example.kindergarten_management_system_g4.dao.scheduledao.IScheduleDAO;
import org.example.kindergarten_management_system_g4.dao.scheduledao.implimentation.ScheduleDAOImpl;
import org.example.kindergarten_management_system_g4.model.*;

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
import java.util.Calendar;
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
            Subject subject = iScheduleDAO.getSubjectByScheduleId(schedulesId);
            List<Term> listTerm = iScheduleDAO.getListTerm();
            List<Subject> listSubject = iScheduleDAO.getListSubject();
            List<Classes> listClass = iScheduleDAO.getListClass();
            List<Slot> listSlot = iScheduleDAO.getListSlot();
            req.setAttribute("listTerm", listTerm);
            req.setAttribute("subjectById", subject);
            req.setAttribute("dayOfWeek", schedule.getDayOfWeek());
            req.setAttribute("listSubject", listSubject);
            req.setAttribute("listClass", listClass);
            req.setAttribute("listSlot", listSlot);
            req.setAttribute("schedules", schedule);
            req.getRequestDispatcher("editSchedule.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int schedulesId = Integer.parseInt(req.getParameter("scheduleIdd"));
        String dateOfDay = req.getParameter("date");
        int termId = Integer.parseInt(req.getParameter("term_id"));
        int subjectId = Integer.parseInt(req.getParameter("subject_id"));
        int classId = Integer.parseInt(req.getParameter("class_id"));
        int slotId = Integer.parseInt(req.getParameter("slotId"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate selectedDate = LocalDate.parse(dateOfDay, dateFormatter);
            LocalDate currentDate = LocalDate.now();
            if (selectedDate.isBefore(currentDate)) {
                Schedule schedule = iScheduleDAO.getScheduleById(schedulesId);
                Subject subject = iScheduleDAO.getSubjectByScheduleId(schedulesId);
                List<Term> listTerm = iScheduleDAO.getListTerm();
                List<Subject> listSubject = iScheduleDAO.getListSubject();
                List<Classes> listClass = iScheduleDAO.getListClass();
                List<Slot> listSlot = iScheduleDAO.getListSlot();
                req.setAttribute("listTerm", listTerm);
                req.setAttribute("subjectById", subject);//
                req.setAttribute("dayOfWeek", schedule.getDayOfWeek());
                req.setAttribute("listSubject", listSubject);
                req.setAttribute("listClass", listClass);
                req.setAttribute("listSlot", listSlot);
                req.setAttribute("schedule", schedule); //
                req.setAttribute("dateFalseUpdate", "Không được để ngày trong quá khứ");
                req.getRequestDispatcher("editSchedule.jsp").forward(req, resp);
                return;
            }


            DayOfWeek dayOfWeekEnum = selectedDate.getDayOfWeek();
            String dayOfWeek = dayOfWeekEnum.toString().toLowerCase();

            if (dayOfWeek.equalsIgnoreCase("SUNDAY")) {
                Schedule schedule = iScheduleDAO.getScheduleById(schedulesId);
                Subject subject = iScheduleDAO.getSubjectByScheduleId(schedulesId);
                List<Term> listTerm = iScheduleDAO.getListTerm();
                List<Subject> listSubject = iScheduleDAO.getListSubject();
                List<Classes> listClass = iScheduleDAO.getListClass();
                List<Slot> listSlot = iScheduleDAO.getListSlot();
                req.setAttribute("listTerm", listTerm);
                req.setAttribute("subjectById", subject);//
                req.setAttribute("dayOfWeek", schedule.getDayOfWeek());
                req.setAttribute("listSubject", listSubject);
                req.setAttribute("listClass", listClass);
                req.setAttribute("listSlot", listSlot);
                req.setAttribute("schedule", schedule); //
                req.setAttribute("sunday", "không đăng ký lớp vào " + dayOfWeek);
                req.getRequestDispatcher("editSchedule.jsp").forward(req, resp);
                return;
            }
           /* if (!selectedDayOfWeek.equalsIgnoreCase(dayOfWeek)) {
                data(req, resp);
                req.setAttribute("DayNotMatch", "ngày không khớp");
                req.getRequestDispatcher("createSchedule.jsp").forward(req, resp);
                return;
            }*/
            Schedule scheduleCheck = new Schedule(schedulesId, dateOfDay, classId, slotId);
            Boolean isCheckExistSchedule = iScheduleDAO.getSchedule(scheduleCheck);
            if (isCheckExistSchedule) {
                // Đảm bảo `data` không cam kết phản hồi
                Schedule schedule = iScheduleDAO.getScheduleById(schedulesId);
                Subject subject = iScheduleDAO.getSubjectByScheduleId(schedulesId);
                List<Term> listTerm = iScheduleDAO.getListTerm();
                List<Subject> listSubject = iScheduleDAO.getListSubject();
                List<Classes> listClass = iScheduleDAO.getListClass();
                List<Slot> listSlot = iScheduleDAO.getListSlot();
                req.setAttribute("listTerm", listTerm);
                req.setAttribute("subjectById", subject);//
                req.setAttribute("dayOfWeek", schedule.getDayOfWeek());
                req.setAttribute("listSubject", listSubject);
                req.setAttribute("listClass", listClass);
                req.setAttribute("listSlot", listSlot);
                req.setAttribute("schedule", schedule);
                req.setAttribute("ExistSchedule", "Trùng lịch, hãy kiểm tra lại ngày, lop, slot học");
                req.getRequestDispatcher("editSchedule.jsp").forward(req, resp);
                return;
            }


            Term term = iScheduleDAO.getTermById(termId);
            String startTimeString = String.valueOf(term.getStartDate());
            String endTimeString = String.valueOf(term.getEndDate());
            LocalDate dateSelect = LocalDate.parse(dateOfDay);
            LocalDate startTime = LocalDate.parse((startTimeString));
            LocalDate endTime = LocalDate.parse(endTimeString);
            if (dateSelect.isBefore(startTime) || dateSelect.isAfter(endTime)) {
                Schedule schedule = iScheduleDAO.getScheduleById(schedulesId);
                Subject subject = iScheduleDAO.getSubjectByScheduleId(schedulesId);
                List<Term> listTerm = iScheduleDAO.getListTerm();
                List<Subject> listSubject = iScheduleDAO.getListSubject();
                List<Classes> listClass = iScheduleDAO.getListClass();
                List<Slot> listSlot = iScheduleDAO.getListSlot();
                req.setAttribute("listTerm", listTerm);
                req.setAttribute("subjectById", subject);//
                req.setAttribute("dayOfWeek", schedule.getDayOfWeek());
                req.setAttribute("listSubject", listSubject);
                req.setAttribute("listClass", listClass);
                req.setAttribute("listSlot", listSlot);
                req.setAttribute("schedule", schedule);
                req.setAttribute("outOfDateTerm", "Vì kỳ học bạn chọn là " + term.getTermName()
                        + " - " + term.getYear() + " nên ngày phải nằm trong khoảng từ "
                        + term.getStartDate() + " đến " + term.getEndDate() + ".");
                req.getRequestDispatcher("editSchedule.jsp").forward(req, resp);
                return;
            }


            Schedule schedule = new Schedule(schedulesId, dayOfWeek, dateOfDay, termId, classId, slotId);
            iScheduleDAO.editSchedule(schedule, subjectId);
            HttpSession session = req.getSession();
            // Đặt thông báo thành công vào session
            session.setAttribute("EditScheduleSuccessful", "Edit class to schedule successful");
            // Chuyển hướng đến danh sách lớp
            resp.sendRedirect("listSchedule?classSelect=" + classId + "&startDate=&endDate=");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private String getDayOfWeekString(int dayOfWeekInt) {
        switch (dayOfWeekInt) {
            case Calendar.MONDAY:
                return "monday";
            case Calendar.TUESDAY:
                return "tuesday";
            case Calendar.WEDNESDAY:
                return "wednesday";
            case Calendar.THURSDAY:
                return "thursday";
            case Calendar.FRIDAY:
                return "friday";
            case Calendar.SATURDAY:
                return "saturday";
            case Calendar.SUNDAY:
                return "sunday";
            default:
                return "";
        }
    }
}
