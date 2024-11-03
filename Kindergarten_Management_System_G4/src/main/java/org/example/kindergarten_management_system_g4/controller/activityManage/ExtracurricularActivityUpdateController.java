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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        int activityId = Integer.parseInt(req.getParameter("activityId")); // Lấy ID hoạt động từ biểu mẫu
        String activityName = req.getParameter("activityName"); // Tên hoạt động
        String description = req.getParameter("description"); // Mô tả hoạt động
        String location = req.getParameter("location"); // Địa điểm hoạt động
        String materialsNeeded = req.getParameter("materialsNeeded"); // Vật liệu cần thiết
        String status = req.getParameter("status"); // Trạng thái hoạt động

        // Định dạng và chuyển đổi thời gian bắt đầu và kết thúc
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(req.getParameter("startTime"), timeFormat); // Thời gian bắt đầu
        LocalTime endTime = LocalTime.parse(req.getParameter("endTime"), timeFormat); // Thời gian kết thúc

        try {
            // Thiết lập tất cả các trường trong đối tượng hoạt động
            ExtracurricularActivities activity = new ExtracurricularActivities();
            activity.setActivity_id(activityId);
            activity.setActivity_name(activityName);
            activity.setDescription(description);
            activity.setLocation(location);
            activity.setMaterials_needed(materialsNeeded);
            activity.setStatus(status);
            activity.setStart_time(startTime);
            activity.setEnd_time(endTime);

            // Cập nhật hoạt động trong cơ sở dữ liệu
            extracurricularActivitiesDAO.updateActivity(activity);

            // Chuyển hướng với thông báo thành công
            resp.sendRedirect(req.getContextPath() + "/view-extracurricular-activities?success=true");
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi SQLException ra console để kiểm tra
            resp.sendRedirect("error.jsp"); // Chuyển hướng tới trang lỗi nếu có lỗi SQL xảy ra
        }
    }
}
