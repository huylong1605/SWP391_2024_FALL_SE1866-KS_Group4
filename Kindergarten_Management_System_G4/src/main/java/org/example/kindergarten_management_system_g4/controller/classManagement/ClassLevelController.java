/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/12/2024       1.1              Nguyễn Huy Long - He160140            Create class ClassLevelController
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
import java.util.List;
import java.util.logging.Logger;

/**
 * ClassLevelController là lớp điều khiển cho việc quản lý cấp độ lớp học.
 * Lớp này sẽ xử lý các yêu cầu GET để lấy danh sách các cấp độ lớp học.
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "classLevel", value = "/classLevel")
public class ClassLevelController extends HttpServlet {

    // Logger để ghi lại các thông báo lỗi hoặc thông tin
    private static final Logger LOGGER = Logger.getLogger(ClassLevelController.class.getName());
    private IClassLevelDAO iClassLevelDAO; // Interface cho các phương thức quản lý cấp độ lớp học

    /**
     * Phương thức init được gọi khi servlet được khởi tạo.
     * Sử dụng để khởi tạo các đối tượng cần thiết, chẳng hạn như iClassLevelDAO.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        iClassLevelDAO = new ClassLevelDAOImpl(); // Khởi tạo đối tượng DAO cho cấp độ lớp học
    }

    /**
     * Phương thức xử lý yêu cầu GET.
     * Lấy danh sách các cấp độ lớp học từ cơ sở dữ liệu,
     * và chuyển tiếp đến trang viewClassLevels.jsp để hiển thị thông tin.
     *
     * @param req  HttpServletRequest chứa thông tin yêu cầu từ client
     * @param resp HttpServletResponse chứa thông tin phản hồi cho client
     * @throws ServletException nếu có lỗi khi xử lý yêu cầu
     * @throws IOException      nếu có lỗi khi gửi phản hồi
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Gọi phương thức DAO để lấy danh sách các cấp độ lớp học
            List<ClassLevel> classLevelList = iClassLevelDAO.getAllClassLevels();

            // Đặt danh sách classLevelList vào request để sử dụng trong JSP
            req.setAttribute("classLevelList", classLevelList);

            // Kiểm tra xem tham số success có được truyền trong request hay không

            String success = req.getParameter("success");
            if (success != null && success.equals("true")) {
                req.setAttribute("success", "Action completed successfully!");
            }
            // Chuyển tiếp đến trang viewClassLevels.jsp để hiển thị
            req.getRequestDispatcher("/Views/Admin/viewClassLevels.jsp").forward(req, resp);
        } catch (SQLException e) {
            // Ghi lại thông báo lỗi nếu có SQLException và chuyển tiếp đến trang lỗi
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            LOGGER.info("SQLException: " + e.getMessage());
        }
    }

}