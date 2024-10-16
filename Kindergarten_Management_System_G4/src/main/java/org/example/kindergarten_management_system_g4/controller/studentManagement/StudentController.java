/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/2/2024       1.1              Đào Xuân Bình - HE163115        View List Student - View Student Detail
 */

package org.example.kindergarten_management_system_g4.controller.studentManagement;

import org.example.kindergarten_management_system_g4.dao.StudentDAO;
import org.example.kindergarten_management_system_g4.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// Định nghĩa servlet với tên "StudentController" và ánh xạ đến URL "/viewStudentList"
@WebServlet(name = "StudentController", value = "/viewStudentList")
public class StudentController extends HttpServlet {
    // Khai báo đối tượng StudentDAO để tương tác với cơ sở dữ liệu
    private StudentDAO studentDAO;

    // Phương thức khởi tạo servlet, được gọi khi servlet được tạo ra
    @Override
    public void init() throws ServletException {
        // Khởi tạo đối tượng StudentDAO
        studentDAO = new StudentDAO();
    }

    // Xử lý các yêu cầu GET từ phía client
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Lấy danh sách tất cả sinh viên
            List<Student> students = studentDAO.getAllStudents();
            // Thiết lập phân trang
            int studentsPerPage = 5; // Số sinh viên trên mỗi trang
            int totalStudents = students.size(); // Tổng số sinh viên
            int totalPages = (int) Math.ceil((double) totalStudents / studentsPerPage); // Tính tổng số trang
            int currentPage = req.getParameter("pageNumber") != null ? Integer.parseInt(req.getParameter("pageNumber")) : 1; // Lấy số trang hiện tại, mặc định là 1 nếu không có
            int start = (currentPage - 1) * studentsPerPage; // Tính chỉ số bắt đầu cho trang hiện tại
            int end = Math.min(start + studentsPerPage, totalStudents); // Tính chỉ số kết thúc cho trang hiện tại
            List<Student> studentsForPage = students.subList(start, end); // Lấy danh sách sinh viên cho trang hiện tại

            // Truyền dữ liệu đến JSP
            req.setAttribute("students", studentsForPage); // Thiết lập danh sách sinh viên cho trang hiện tại
            req.setAttribute("totalPages", totalPages); // Thiết lập tổng số trang
            req.setAttribute("currentPage", currentPage); // Thiết lập số trang hiện tại

            // Chuyển tiếp yêu cầu đến trang JSP để hiển thị
            req.getRequestDispatcher("/Views/Admin/viewStudentList.jsp").forward(req, resp);
        } catch (ClassNotFoundException e) {
            // Xử lý ngoại lệ và gửi phản hồi lỗi nội bộ máy chủ nếu có vấn đề về cơ sở dữ liệu
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi cơ sở dữ liệu");
        }
    }
}

