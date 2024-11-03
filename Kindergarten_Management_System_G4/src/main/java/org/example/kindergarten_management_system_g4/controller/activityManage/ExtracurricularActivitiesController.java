/*
 * Copyright(C) 2005, SWP_G4.
 * KMS:
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.0               Đào Xuân Bình - HE163115          Extracurricular Activities Controller
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
 * ExtracurricularActivitiesController xử lý các yêu cầu GET để xem danh sách các hoạt động ngoại khóa.
 * Lấy dữ liệu hoạt động từ cơ sở dữ liệu và chuyển chúng tới view tương ứng để hiển thị.
 * <p>Bugs: Không có lỗi nào được phát hiện.
 *
 * @author Đào Xuân Bình
 */
@WebServlet(name = "extracurricularActivities", value = "/view-extracurricular-activities")
public class ExtracurricularActivitiesController extends HttpServlet {
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
     * Xử lý các yêu cầu GET của HTTP để lấy danh sách các hoạt động ngoại khóa.
     * Lấy dữ liệu từ cơ sở dữ liệu và thiết lập vào yêu cầu để hiển thị trên trang JSP.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet.
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra.
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu GET.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Lấy danh sách tất cả các hoạt động ngoại khóa từ cơ sở dữ liệu
            List<ExtracurricularActivities> activities = activityDAO.getAllActivities();
            req.setAttribute("activityList", activities); // Thiết lập danh sách hoạt động vào yêu cầu

            // Chuyển tiếp yêu cầu tới trang JSP để hiển thị danh sách hoạt động
            req.getRequestDispatcher("/Views/Admin/viewActivity.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console nếu có lỗi SQL
            resp.sendRedirect("error.jsp"); // Chuyển hướng tới trang lỗi nếu có lỗi xảy ra
        }
    }
}
