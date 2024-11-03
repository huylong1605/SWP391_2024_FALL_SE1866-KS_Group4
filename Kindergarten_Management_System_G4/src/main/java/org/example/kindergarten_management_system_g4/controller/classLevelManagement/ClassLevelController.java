/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115        View Class Level
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
import java.util.List;
import java.util.logging.Logger;

/**
 * Lớp ClassLevelController xử lý các yêu cầu HTTP để quản lý cấp lớp trong hệ thống.
 * Nó lấy tất cả các cấp lớp từ cơ sở dữ liệu và chuyển chúng tới view tương ứng để hiển thị.
 * <p>Lỗi: Chưa phát hiện lỗi.
 *
 * @author Đào Xuân Bình
 */
@WebServlet(name = "classLevel", value = "/classLevel")
public class ClassLevelController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ClassLevelController.class.getName()); // Logger để ghi lại các hoạt động trong lớp này
    private IClassLevelDAO iClassLevelDAO;

    /**
     * Khởi tạo controller bằng cách thiết lập đối tượng ClassLevelDAO.
     * Phương thức này được gọi một lần khi servlet được tạo lần đầu tiên.
     *
     * @throws ServletException nếu có lỗi xảy ra trong quá trình khởi tạo.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        iClassLevelDAO = new ClassLevelDAOImpl(); // Khởi tạo DAO để quản lý cấp lớp
    }

    /**
     * Xử lý các yêu cầu GET của HTTP để lấy và hiển thị dữ liệu cấp lớp.
     * Lấy tất cả các cấp lớp từ cơ sở dữ liệu và thiết lập chúng thành thuộc tính trong yêu cầu.
     * Chuyển tiếp yêu cầu đến trang 'viewClassLevels.jsp' để hiển thị.
     * Nếu có tham số 'success' trong yêu cầu, sẽ thiết lập thông báo thành công.
     *
     * @param req đối tượng HttpServletRequest chứa yêu cầu từ phía client
     * @param resp đối tượng HttpServletResponse chứa phản hồi của servlet
     * @throws ServletException nếu có lỗi đặc thù của servlet xảy ra
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra được phát hiện khi servlet xử lý yêu cầu GET
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<ClassLevel> classLevelList = iClassLevelDAO.getAllClassLevels(); // Lấy tất cả cấp lớp từ cơ sở dữ liệu
            req.setAttribute("classLevelList", classLevelList); // Thiết lập danh sách cấp lớp vào yêu cầu

            String success = req.getParameter("success");
            if (success != null && success.equals("true")) { // Kiểm tra nếu tham số success có tồn tại và bằng true
                req.setAttribute("success", "Action completed successfully!"); // Thiết lập thông báo thành công
            }
            req.getRequestDispatcher("/Views/Admin/viewClassLevels.jsp").forward(req, resp); // Chuyển tiếp yêu cầu tới trang JSP
        } catch (SQLException e) {
            req.getRequestDispatcher("error.jsp").forward(req, resp); // Chuyển tiếp tới trang lỗi nếu có SQLException xảy ra
            LOGGER.info("SQLException: " + e.getMessage()); // Ghi lại thông báo lỗi SQL
        }
    }

}
