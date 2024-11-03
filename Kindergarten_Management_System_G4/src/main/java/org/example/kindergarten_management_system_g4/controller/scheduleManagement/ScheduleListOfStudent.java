package org.example.kindergarten_management_system_g4.controller.scheduleManagement;

import org.example.kindergarten_management_system_g4.dao.scheduledao.IScheduleDAO;
import org.example.kindergarten_management_system_g4.dao.scheduledao.implimentation.ScheduleDAOImpl;
import org.example.kindergarten_management_system_g4.model.ScheduleDAL;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "scheduleStudent", value = "/scheduleStudent")
public class ScheduleListOfStudent extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ScheduleListOfStudent.class.getName());
    private IScheduleDAO iScheduleDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        iScheduleDAO = new ScheduleDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parentIds = req.getParameter("parentId");

        // Lấy giá trị tuần vừa chọn từ combobox weekSelector
        String selectedWeek = req.getParameter("weekSelector");
        String startDate = null;
        String endDate = null;

        if (selectedWeek != null) {
            LOGGER.info("Tuần đã chọn: " + selectedWeek);

            // Tách selectedWeek thành startDate và endDate
            String[] dates = selectedWeek.split(" to ");
            if (dates.length == 2) {
                startDate = String.valueOf(LocalDate.parse(dates[0]));
                endDate = String.valueOf(LocalDate.parse(dates[1]));
                LOGGER.info("Ngày bắt đầu: " + startDate);
                LOGGER.info("Ngày kết thúc: " + endDate);
            }
        } else {
            LOGGER.info("Không có tuần nào được chọn.");
        }

        // Xác định ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Xác định ngày bắt đầu của tuần hiện tại là thứ Hai và ngày kết thúc là Chủ Nhật
        LocalDate startOfCurrentWeek = currentDate.with(DayOfWeek.MONDAY);
        LocalDate endOfCurrentWeek = startOfCurrentWeek.plusDays(6);
        String currentWeek = startOfCurrentWeek + " to " + endOfCurrentWeek;

        // Đặt tuần hiện tại làm tuần mặc định nếu không có tuần nào được chọn
        if (startDate == null || endDate == null) {
            startDate = startOfCurrentWeek.toString();
            endDate = endOfCurrentWeek.toString();
        }

        // Tạo danh sách các tuần từ ngày 2020-01-01 đến ngày 2026-12-31
        LocalDate overallStartDate = LocalDate.of(2020, 1, 1).with(DayOfWeek.MONDAY); // Bắt đầu từ thứ Hai
        LocalDate endDateOverall = LocalDate.of(2026, 12, 31);
        List<String> weeks = new ArrayList<>();

        while (!overallStartDate.isAfter(endDateOverall)) {
            LocalDate startOfWeek = overallStartDate;
            LocalDate endOfWeek = overallStartDate.plusDays(6);

            if (endOfWeek.isAfter(endDateOverall)) {
                endOfWeek = endDateOverall;
            }

            // Định dạng tuần dưới dạng chuỗi "YYYY-MM-DD to YYYY-MM-DD"
            String weekRange = startOfWeek + " to " + endOfWeek;
            weeks.add(weekRange);

            // Tăng ngày lên 7 để chuyển sang tuần tiếp theo (từ thứ Hai tiếp theo)
            overallStartDate = overallStartDate.plusDays(7);
        }

        // Đưa danh sách tuần và tuần hiện tại vào request attribute để sử dụng trong JSP
        req.setAttribute("weeks", weeks);
        req.setAttribute("currentWeek", currentWeek); // tuần hiện tại để chọn mặc định
        req.setAttribute("startDate", startDate); // truyền startDate vào JSP
        req.setAttribute("endDate", endDate); // truyền endDate vào JSP

        try {

            List<ScheduleDAL> listScheduleStudent = iScheduleDAO.getScheduleOfStudent(Integer.parseInt(parentIds), startDate, endDate);
            req.setAttribute("listScheduleStudent", listScheduleStudent);

            // Forward request và response tới trang JSP
            req.getRequestDispatcher("scheduleStudent.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Xử lý logic của phương thức POST nếu cần
    }
}
