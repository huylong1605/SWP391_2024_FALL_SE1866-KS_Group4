/*
 * Copyright(C) 2005, SWP_G4.
 * KMS:
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.0               Đào Xuân Bình - HE163115          Add Activity Controller
 */

package org.example.kindergarten_management_system_g4.controller.activityManage;

import org.example.kindergarten_management_system_g4.dao.ActivityDAO.ExtracurricularActivitiesDAO;
import org.example.kindergarten_management_system_g4.dao.ActivityDAO.implement.ExtracurricularActivitiesDAOImpl;
import org.example.kindergarten_management_system_g4.model.ExtracurricularActivities;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * AddActivityController xử lý các yêu cầu POST để thêm hoạt động ngoại khóa vào hệ thống.
 * Lấy thông tin hoạt động từ yêu cầu HTTP, bao gồm tên hoạt động, mô tả, thời gian và địa điểm,
 * sau đó lưu hoạt động vào cơ sở dữ liệu thông qua lớp DAO.
 * <p>Bug: Không có lỗi nào được phát hiện.
 *
 * @author Đào Xuân Bình
 */
@WebServlet(name = "addActivity", value = "/addActivity")
public class AddActivityCotroller extends HttpServlet {
    private ExtracurricularActivitiesDAO activityDAO;

    /**
     * Khởi tạo controller bằng cách thiết lập đối tượng ExtracurricularActivitiesDAO.
     * Phương thức này được gọi một lần khi servlet được tạo lần đầu tiên.
     *
     * @throws ServletException nếu có lỗi xảy ra trong quá trình khởi tạo.
     */
    @Override
    public void init() throws ServletException {
        activityDAO = new ExtracurricularActivitiesDAOImpl();
    }

    /**
     * Xử lý các yêu cầu POST của HTTP để thêm hoạt động ngoại khóa.
     * Lấy thông tin từ biểu mẫu của người dùng, bao gồm tên hoạt động, mô tả, ngày, thời gian bắt đầu và kết thúc,
     * địa điểm, và vật liệu cần thiết, sau đó lưu dữ liệu vào cơ sở dữ liệu.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet.
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra.
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu POST.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy phiên làm việc (session) của người dùng
        HttpSession session = req.getSession();
        // Lấy thông tin người dùng từ session
        User user = (User) session.getAttribute("user");

        // Nhận dữ liệu từ các trường biểu mẫu
        String activityName = req.getParameter("activityName"); // Tên hoạt động
        String description = req.getParameter("description"); // Mô tả hoạt động
        String dateString = req.getParameter("date"); // Ngày hoạt động (chuỗi)
        LocalDate date = LocalDate.parse(dateString); // Chuyển đổi chuỗi thành LocalDate

        String startTimeString = req.getParameter("start_time"); // Thời gian bắt đầu (chuỗi)
        LocalTime startTime = LocalTime.parse(startTimeString); // Chuyển đổi thành LocalTime

        String endTimeString = req.getParameter("end_time"); // Thời gian kết thúc (chuỗi)
        LocalTime endTime = LocalTime.parse(endTimeString); // Chuyển đổi thành LocalTime

        String location = req.getParameter("location"); // Địa điểm hoạt động
        String material = req.getParameter("material"); // Vật liệu cần thiết

        try {
            // Tạo đối tượng hoạt động ngoại khóa mới với các thông tin đã nhận
            ExtracurricularActivities newActivity = new ExtracurricularActivities(
                    0, activityName, description, date, startTime, endTime, location, user.getUserID(), material
            );

            System.out.println(newActivity); // In thông tin hoạt động mới (chỉ để kiểm tra)

            // Gọi DAO để thêm hoạt động vào cơ sở dữ liệu
            activityDAO.addActivity(newActivity);

            // Chuyển hướng tới trang xem hoạt động ngoại khóa sau khi thêm thành công
            resp.sendRedirect(req.getContextPath() + "/view-extracurricular-activities");
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console nếu có lỗi SQL
            resp.sendRedirect("error.jsp"); // Chuyển hướng tới trang lỗi nếu có lỗi xảy ra
        }
    }
}
