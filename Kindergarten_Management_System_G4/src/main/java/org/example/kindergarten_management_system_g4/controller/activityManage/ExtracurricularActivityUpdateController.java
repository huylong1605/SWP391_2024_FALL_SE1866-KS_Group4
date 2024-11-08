package org.example.kindergarten_management_system_g4.controller.activityManage;

/*
 * Copyright(C) 2005, SWP_G4.
 * KMS:
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1               Đào Xuân Bình - HE163115          Update Extracurricular Activity
 */

import org.example.kindergarten_management_system_g4.dao.ActivityDAO.ExtracurricularActivitiesDAO;
import org.example.kindergarten_management_system_g4.dao.ActivityDAO.implement.ExtracurricularActivitiesDAOImpl;
import org.example.kindergarten_management_system_g4.model.ExtracurricularActivities;

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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * ExtracurricularActivityUpdateController xử lý các yêu cầu POST để cập nhật một hoạt động ngoại khóa.
 * Lấy thông tin hoạt động từ yêu cầu, thực hiện xác thực và cập nhật thông tin hoạt động trong cơ sở dữ liệu.
 * <p>Bugs: Không có lỗi nào được phát hiện.
 *
 * @author Đào Xuân Bình
 */
@WebServlet(name = "updateActivity", value = "/updateActivity")
public class ExtracurricularActivityUpdateController extends HttpServlet {

    private ExtracurricularActivitiesDAO extracurricularActivitiesDAO;

    /**
     * Khởi tạo controller bằng cách thiết lập đối tượng ExtracurricularActivitiesDAO.
     * Phương thức này được gọi một lần khi servlet được tạo lần đầu tiên.
     *
     * @throws ServletException nếu có lỗi xảy ra trong quá trình khởi tạo.
     */
    @Override
    public void init() throws ServletException {
        extracurricularActivitiesDAO = new ExtracurricularActivitiesDAOImpl(); // Khởi tạo DAO cho hoạt động ngoại khóa
    }

    /**
     * Xử lý các yêu cầu POST của HTTP để cập nhật một hoạt động.
     * Lấy thông tin hoạt động từ biểu mẫu và cập nhật hoạt động tương ứng trong cơ sở dữ liệu.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet.
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra.
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu POST.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Retrieve parameters with null checks
            int activityId = Integer.parseInt(req.getParameter("activityId"));
            String activityName = req.getParameter("activityName");
            String description = req.getParameter("description");
            String location = req.getParameter("location");
            String materialsNeeded = req.getParameter("materialsNeeded");
            String status = req.getParameter("status");
            String dateString = req.getParameter("date");

            if (activityName == null || description == null || location == null || materialsNeeded == null || status == null || dateString == null) {
                throw new IllegalArgumentException("All fields are required.");
            }

            // Convert date and time fields with appropriate formats
            LocalDate date = LocalDate.parse(dateString);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(req.getParameter("startTime"), timeFormat);
            LocalTime endTime = LocalTime.parse(req.getParameter("endTime"), timeFormat);

            // Populate activity object
            ExtracurricularActivities activity = new ExtracurricularActivities();
            activity.setActivity_id(activityId);
            activity.setActivity_name(activityName);
            activity.setDescription(description);
            activity.setDate(date);
            activity.setLocation(location);
            activity.setMaterials_needed(materialsNeeded);
            activity.setStatus(status);
            activity.setStart_time(startTime);
            activity.setEnd_time(endTime);

            // Update activity in the database
            extracurricularActivitiesDAO.updateActivity(activity);

            // Redirect with success message
            resp.sendRedirect(req.getContextPath() + "/view-extracurricular-activities?success=true");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?error=Invalid ID format");
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?error=Invalid date or time format");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?error=Database error");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?error=" + e.getMessage());
        }
    }

}
