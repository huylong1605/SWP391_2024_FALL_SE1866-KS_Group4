/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115        Update Class Level
 */

package org.example.kindergarten_management_system_g4.controller.classManagement;

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
 * Lớp ClassLevelUpdateController chịu trách nhiệm xử lý các yêu cầu HTTP POST để cập nhật thông tin cấp lớp trong hệ thống.
 * Dữ liệu cấp lớp được lấy từ form, tạo một đối tượng ClassLevel và cập nhật thông tin trong cơ sở dữ liệu.
 * <p>Lỗi: Chưa phát hiện lỗi.
 *
 * @tác_giả Đào Xuân Bình
 */
@WebServlet(name = "updateClassLevel", value = "/updateClassLevel")
public class ClassLevelUpdateController extends HttpServlet {

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
     * Xử lý yêu cầu POST để cập nhật thông tin của một cấp lớp.
     * Dữ liệu cấp lớp được lấy từ form, tạo một đối tượng ClassLevel, và gọi phương thức cập nhật thông tin trong DAO.
     * Sau khi cập nhật thành công, chuyển hướng tới trang danh sách cấp lớp với thông báo thành công.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu POST
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int classLevelId = Integer.parseInt(req.getParameter("classLevelId")); // Lấy ID của cấp lớp từ form
        String name = req.getParameter("classLevelName"); // Lấy tên của cấp lớp từ form
        String description = req.getParameter("description"); // Lấy mô tả của cấp lớp từ form
        int ageRange = Integer.parseInt(req.getParameter("ageRange")); // Lấy độ tuổi từ form và chuyển sang số nguyên

        ClassLevel classLevel = new ClassLevel(classLevelId, name, description, ageRange); // Tạo đối tượng ClassLevel với thông tin từ form
        try {
            iClassLevelDAO.updateClassLevel(classLevel); // Cập nhật thông tin cấp lớp trong cơ sở dữ liệu
        } catch (SQLException e) {
            throw new RuntimeException(e); // Ném lỗi nếu có SQLException
        }

        resp.sendRedirect(req.getContextPath() + "/classLevel?success=true"); // Chuyển hướng tới trang danh sách cấp lớp với thông báo thành công
    }
}
