/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115        Delete Class Level
 */

package org.example.kindergarten_management_system_g4.controller.classManagement;

import org.example.kindergarten_management_system_g4.dao.ClassLevelDAO.IClassLevelDAO;
import org.example.kindergarten_management_system_g4.dao.ClassLevelDAO.implement.ClassLevelDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Lớp ClassLevelDeleteController chịu trách nhiệm xử lý các yêu cầu HTTP POST để xóa một cấp lớp trong hệ thống.
 * ID của cấp lớp cần xóa được lấy từ form và sau đó được xóa khỏi cơ sở dữ liệu.
 * <p>Lỗi: Chưa phát hiện lỗi.
 *
 * @author Đào Xuân Bình
 */
@WebServlet(name = "deleteClassLevel", value = "/deleteClassLevel")
public class ClassLevelDeleteController extends HttpServlet {

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
     * Xử lý yêu cầu POST để xóa một cấp lớp.
     * ID của cấp lớp cần xóa được lấy từ form, sau đó gọi phương thức xóa trong DAO để thực hiện xóa khỏi cơ sở dữ liệu.
     * Sau khi xóa thành công, chuyển hướng tới trang danh sách cấp lớp với thông báo thành công.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu POST
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int classLevelId = Integer.parseInt(req.getParameter("classLevelId")); // Lấy ID của cấp lớp từ form
        try {
            iClassLevelDAO.deleteClassLevel(classLevelId); // Xóa cấp lớp khỏi cơ sở dữ liệu
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi nếu có SQLException
        }
        resp.sendRedirect(req.getContextPath() + "/classLevel?success=true"); // Chuyển hướng tới trang danh sách cấp lớp với thông báo thành công
    }
}
