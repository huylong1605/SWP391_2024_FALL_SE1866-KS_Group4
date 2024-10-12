/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/12/2024       1.1              Nguyễn Huy Long - He160140            Create class Detail
 */
package org.example.kindergarten_management_system_g4.controller.classManagement;

import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.impliment.ClassDAOImpl;
import org.example.kindergarten_management_system_g4.model.ClassDAL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * ClassDetailController là lớp điều khiển cho việc quản lý chi tiết lớp học.
 * Lớp này sẽ xử lý các yêu cầu GET và POST cho trang chi tiết lớp học.
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "classDetail", value = "/classDetail")
public class ClassDetailController extends HttpServlet {

    // Logger để ghi lại các thông báo lỗi hoặc thông tin
    private static final Logger LOGGER = Logger.getLogger(ClassDetailController.class.getName());
    private IClassDAO iClassDAO; // Interface cho các phương thức quản lý lớp học

    /**
     * Phương thức init được gọi khi servlet được khởi tạo.
     * Sử dụng để khởi tạo các đối tượng cần thiết, chẳng hạn như iClassDAO.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        iClassDAO = new ClassDAOImpl(); // Khởi tạo đối tượng DAO cho lớp học
    }

    /**
     * Phương thức xử lý yêu cầu GET.
     * Nhận tham số classId từ yêu cầu, lấy thông tin chi tiết lớp học từ cơ sở dữ liệu,
     * và chuyển tiếp đến trang viewClassDetail.jsp để hiển thị thông tin chi tiết.
     *
     * @param req  HttpServletRequest chứa thông tin yêu cầu từ client
     * @param resp HttpServletResponse chứa thông tin phản hồi cho client
     * @throws ServletException nếu có lỗi khi xử lý yêu cầu
     * @throws IOException      nếu có lỗi khi gửi phản hồi
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy classId từ tham số yêu cầu
        int classId = Integer.parseInt(req.getParameter("classId"));

        try {
            // Gọi phương thức để lấy thông tin chi tiết lớp học
            ClassDAL classDAL = iClassDAO.getClassByIdDetail(classId);

            // Đặt thuộc tính classDAL vào yêu cầu để sử dụng trong JSP
            req.setAttribute("classesDAL", classDAL);
            // Chuyển tiếp đến trang viewClassDetail.jsp
            req.getRequestDispatcher("viewClassDetail.jsp").forward(req, resp);
        } catch (SQLException e) {
            // Ghi lại thông báo lỗi nếu có SQLException
            LOGGER.info("SQLException: " + e.getMessage());
        }
    }

    /**
     * Phương thức xử lý yêu cầu POST.
     * Hiện tại không có logic nào được xử lý trong phương thức này.
     * Có thể được sử dụng trong tương lai cho các yêu cầu gửi dữ liệu.
     *
     * @param req  HttpServletRequest chứa thông tin yêu cầu từ client
     * @param resp HttpServletResponse chứa thông tin phản hồi cho client
     * @throws ServletException nếu có lỗi khi xử lý yêu cầu
     * @throws IOException      nếu có lỗi khi gửi phản hồi
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Gọi phương thức doPost của lớp cha
    }
}
