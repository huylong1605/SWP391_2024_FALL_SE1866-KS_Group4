/*
 * Copyright(C) 2005, SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/11/2024       1.1              Nguyễn Huy Long - He160140              Tạo lớp xóa
 */

package org.example.kindergarten_management_system_g4.controller.classManagement;

import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.impliment.ClassDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Triển khai Servlet để xóa một lớp học.
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "deleteClass", value = "/deleteClass")
public class DeleteClassController extends HttpServlet {

    // Logger để ghi lại các sự kiện và lỗi
    private static final Logger LOGGER = Logger.getLogger(DeleteClassController.class.getName());

    // Giao diện cho việc truy cập dữ liệu lớp học
    private IClassDAO classDAO;

    /**
     * Khởi tạo Servlet và thiết lập đối tượng truy cập dữ liệu.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        classDAO = new ClassDAOImpl(); // Khởi tạo triển khai DAO cho lớp học
    }

    /**
     * Xử lý các yêu cầu GET để xóa một lớp học.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException nếu có lỗi xảy ra trong servlet
     * @throws IOException      nếu có lỗi I/O xảy ra
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy ID lớp từ tham số yêu cầu
        int classId = Integer.parseInt(req.getParameter("classId"));

        try {
            // Cố gắng xóa lớp học
            boolean isDeleted = classDAO.deleteClass(classId);
            HttpSession session = req.getSession(); // Lấy phiên hiện tại

            // Thiết lập thuộc tính phiên dựa trên kết quả xóa
            if (isDeleted) {
                session.setAttribute("DeleteSuccessful", "Delete class successfully");
            } else {
                session.setAttribute("DeleteFalse", "Delete failed because the class already has students or the class is already on the class schedule");
            }

            // Chuyển hướng về servlet danh sách lớp
            resp.sendRedirect("listClass");
        } catch (SQLException e) {
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            LOGGER.severe("SQLException: " + e.getMessage()); // Ghi lại lỗi SQL
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
        // Không sử dụng phương thức POST trong lớp này
    }
}
