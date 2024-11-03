/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115        Add Class Level
 */

package org.example.kindergarten_management_system_g4.controller.classLevelManagement;

import org.example.kindergarten_management_system_g4.dao.ClassLevelDAO.IClassLevelDAO;
import org.example.kindergarten_management_system_g4.dao.ClassLevelDAO.implement.ClassLevelDAOImpl;
import org.example.kindergarten_management_system_g4.model.ClassLevel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Lớp ClassLevelCreateController chịu trách nhiệm xử lý các yêu cầu HTTP POST để tạo cấp lớp mới trong hệ thống.
 * Dữ liệu cấp lớp được lấy từ form gửi lên và lưu vào cơ sở dữ liệu.
 * <p>Lỗi: Chưa phát hiện lỗi.
 *
 * @author Đào Xuân Bình
 */
@WebServlet(name = "addClassLevel", value = "/addClassLevel")
public class ClassLevelCreateController extends HttpServlet {

    private IClassLevelDAO iClassLevelDAO;

    /**
     * Khởi tạo controller bằng cách thiết lập đối tượng ClassLevelDAO.
     * Phương thức này được gọi một lần khi servlet được tạo lần đầu tiên.
     *
     * @throws ServletException nếu có lỗi xảy ra trong quá trình khởi tạo.
     */
    @Override
    public void init() throws ServletException {
        iClassLevelDAO = new ClassLevelDAOImpl(); // Khởi tạo DAO
    }

    /**
     * Xử lý yêu cầu POST để tạo một cấp lớp mới.
     * Dữ liệu cấp lớp được lấy từ form, tạo một đối tượng ClassLevel, và thêm vào cơ sở dữ liệu.
     * Sau khi thêm thành công, chuyển hướng tới trang danh sách cấp lớp với thông báo thành công.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu POST
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("classLevelName"); // Lấy tên cấp lớp từ form
        String description = req.getParameter("description"); // Lấy mô tả cấp lớp từ form
        int ageRange = Integer.parseInt(req.getParameter("ageRange")); // Lấy độ tuổi từ form và chuyển sang số nguyên

        ClassLevel classLevel = new ClassLevel(0, name, description, ageRange); // Tạo đối tượng ClassLevel mới, ID tự động tăng
        try {
            iClassLevelDAO.addClassLevel(classLevel); // Thêm cấp lớp vào cơ sở dữ liệu
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu xảy ra SQLException
        }
        resp.sendRedirect(req.getContextPath() + "/classLevel?success=true"); // Chuyển hướng tới trang danh sách cấp lớp với thông báo thành công
    }
}
