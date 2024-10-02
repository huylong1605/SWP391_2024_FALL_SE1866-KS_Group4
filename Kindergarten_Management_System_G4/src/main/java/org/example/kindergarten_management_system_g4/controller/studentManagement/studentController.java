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

@WebServlet(name = "StudentController", value = "/viewStudentList")
public class studentController extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Lấy danh sách tất cả sinh viên
            List<Student> students = studentDAO.getAllStudents();

            // Thiết lập phân trang
            int studentsPerPage = 5;
            int totalStudents = students.size();
            int totalPages = (int) Math.ceil((double) totalStudents / studentsPerPage);
            int currentPage = req.getParameter("pageNumber") != null ? Integer.parseInt(req.getParameter("pageNumber")) : 1;
            int start = (currentPage - 1) * studentsPerPage;
            int end = Math.min(start + studentsPerPage, totalStudents);
            List<Student> studentsForPage = students.subList(start, end);

            // Truyền dữ liệu sang JSP
            req.setAttribute("students", studentsForPage);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("currentPage", currentPage);

            // Chuyển hướng sang trang JSP
            req.getRequestDispatcher("/Views/Admin/viewStudentList.jsp").forward(req, resp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
