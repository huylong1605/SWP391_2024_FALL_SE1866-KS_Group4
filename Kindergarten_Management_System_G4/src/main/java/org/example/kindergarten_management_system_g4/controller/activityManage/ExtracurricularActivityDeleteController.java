package org.example.kindergarten_management_system_g4.controller.activityManage;

/*
 * Copyright(C) 2005, SWP_G4.
 * KMS:
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1               Đào Xuân Bình - HE163115        Delete Extracurricular Activity
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
import java.util.List;

/**
 * ExtracurricularActivityDeleteController xử lý các yêu cầu POST để xóa một hoạt động ngoại khóa.
 * Lấy ID của hoạt động từ biểu mẫu và xóa hoạt động đó khỏi cơ sở dữ liệu nếu không còn sử dụng.
 * <p>Bugs: Không có lỗi nào được phát hiện.
 *
 * @author Đào Xuân Bình
 */
@WebServlet(name = "deleteActivity", value = "/deleteActivity")
public class ExtracurricularActivityDeleteController extends HttpServlet {

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
     * Xử lý các yêu cầu POST của HTTP để xóa một hoạt động.
     * Lấy ID của hoạt động từ biểu mẫu và gọi phương thức xóa trong DAO.
     * Nếu hoạt động đang được sử dụng, sẽ chuyển hướng với thông báo lỗi.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client.
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet.
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra.
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu POST.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("alo alo"); // In ra để kiểm tra điểm đầu vào của phương thức
        int activityId = Integer.parseInt(req.getParameter("classLevelId")); // Lấy ID hoạt động từ biểu mẫu
        System.out.println("hahaha" + activityId); // In ID hoạt động để kiểm tra

        try {
            // Thực hiện xóa trực tiếp hoạt động ngoại khóa
            extracurricularActivitiesDAO.deleteActivity(activityId);
            // Chuyển hướng đến trang thông báo thành công sau khi xóa
            resp.sendRedirect(req.getContextPath() + "/view-extracurricular-activities?success=true");
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi SQLException ra console để kiểm tra
            // Chuyển hướng đến trang lỗi nếu có lỗi SQL xảy ra
            resp.sendRedirect(req.getContextPath() + "/view-extracurricular-activities?success=false");
        }
    }
}
