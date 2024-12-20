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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "createSchedule", value = "/createSchedule")
public class CreateSchedule extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateSchedule.class.getName());
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
     * Phương thức doGet xử lý yêu cầu GET để hiển thị danh sách các học kỳ, môn học, lớp và slot.
     * Thiết lập các thuộc tính này để JSP có thể hiển thị chúng.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Term> listTerm = iScheduleDAO.getListTerm();
            List<Subject> listSubject = iScheduleDAO.getListSubject();
            List<Classes> listClass = iScheduleDAO.getListClass();
            List<Slot> listSlot = iScheduleDAO.getListSlot();
            LOGGER.info(listSlot.toString());
            req.setAttribute("listTerm", listTerm);
            req.setAttribute("listSubject", listSubject);
            req.setAttribute("listClass", listClass);
            req.setAttribute("listSlot", listSlot);
            req.getRequestDispatcher("createSchedule.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            LOGGER.info("SQLException: " + e.getMessage());
        }
    }


    /**
     * Phương thức doPost xử lý yêu cầu POST để tạo lịch học mới.
     * Kiểm tra ngày hợp lệ, kiểm tra trùng lịch, và tạo lịch học nếu các điều kiện hợp lệ.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*String dayOfWeek = req.getParameter("dayOfWeek");*/
        String dateOfDay = req.getParameter("date");
        int termId = Integer.parseInt(req.getParameter("term_id"));
        int subjectId = Integer.parseInt(req.getParameter("subject_id"));
        int classId = Integer.parseInt(req.getParameter("class_id"));
        int slotId = Integer.parseInt(req.getParameter("slotId"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date selectedDate = dateFormat.parse(dateOfDay);
            Date currentDate = new Date();
            if (selectedDate.before(currentDate)) {
                data(req, resp);
                req.setAttribute("dateFalse", "Do not leave the date in the past");
                req.getRequestDispatcher("createSchedule.jsp").forward(req, resp);
                return;
            }


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);

            int dayOfWeekInt = calendar.get(Calendar.DAY_OF_WEEK);  // Lấy số đại diện cho ngày trong tuần
            String dayOfWeek = getDayOfWeekString(dayOfWeekInt);

            if (dayOfWeek.equalsIgnoreCase("SUNDAY")) {
                data(req, resp);
                req.setAttribute("sunday", "can not add class in sunday");
                req.getRequestDispatcher("createSchedule.jsp").forward(req, resp);
            }
           /* if (!selectedDayOfWeek.equalsIgnoreCase(dayOfWeek)) {
                data(req, resp);
                req.setAttribute("DayNotMatch", "ngày không khớp");
                req.getRequestDispatcher("createSchedule.jsp").forward(req, resp);
                return;
            }*/
            Schedule scheduleCheck = new Schedule(dateOfDay, classId, slotId);
            Boolean isCheckExistSchedule = iScheduleDAO.getSchedule(scheduleCheck);
            if (isCheckExistSchedule == true) {
                data(req, resp);
                req.setAttribute("ExistSchedule", "Due to scheduling conflicts, please check the date and class slot again");
                req.getRequestDispatcher("createSchedule.jsp").forward(req, resp);
                return;
            }

            Term term = iScheduleDAO.getTermById(termId);
            String startTimeString = String.valueOf(term.getStartDate());
            String endTimeString = String.valueOf(term.getEndDate());
            LocalDate dateSelect = LocalDate.parse(dateOfDay);
            LocalDate startTime = LocalDate.parse((startTimeString));
            LocalDate endTime = LocalDate.parse(endTimeString);
            if (dateSelect.isBefore(startTime) || dateSelect.isAfter(endTime)) {
                data(req, resp);
                req.setAttribute("outOfDateTerm", "Because the semester you choose is " + term.getTermName()
                        + " - " + term.getYear() + " so the date must be in the range from "
                        + term.getStartDate() + " to " + term.getEndDate() + ".");
                req.getRequestDispatcher("createSchedule.jsp").forward(req, resp);
                return;
            }


            Schedule schedule = new Schedule(dayOfWeek, dateOfDay, termId, classId, slotId);
            iScheduleDAO.addSchedule(schedule, subjectId);
            data(req, resp);
            LOGGER.info("list schedule: " + schedule);
            req.setAttribute("AddSuccessfully", "Registered for the class on the class schedule successfully");
            req.getRequestDispatcher("createSchedule.jsp").forward(req, resp);
            /*HttpSession session = req.getSession();
            // Đặt thông báo thành công vào session
            session.setAttribute("AddScheduleSuccessful", "Add class to schedule successful");*/
            // Chuyển hướng đến danh sách lớp
            /*resp.sendRedirect("listSchedule");*/
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            throw new RuntimeException(e);
        }


    }

    private void data(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        List<Term> listTerm = iScheduleDAO.getListTerm();
        List<Subject> listSubject = iScheduleDAO.getListSubject();
        List<Classes> listClass = iScheduleDAO.getListClass();
        List<Slot> listSlot = iScheduleDAO.getListSlot();
        req.setAttribute("listTerm", listTerm);
        req.setAttribute("listSubject", listSubject);
        req.setAttribute("listClass", listClass);
        req.setAttribute("listSlot", listSlot);
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
