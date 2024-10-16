package org.example.kindergarten_management_system_g4.controller.classManagement;

import org.example.kindergarten_management_system_g4.dao.classDAO.studentInClassDAO.IStudentInClassDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.studentInClassDAO.StudentInClassDAO;
import org.example.kindergarten_management_system_g4.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(value = {"/Views/Manager/listStudentInClass", "/Views/Manager/AddStudentToClass", "/Views/Manager/StudentDetails"})
public class StudentClassController extends HttpServlet {
    private IStudentInClassDAO studentDAO;

    public void init() throws ServletException {
        studentDAO = new StudentInClassDAO(); // Khởi tạo đối tượng StudentDAO
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classIdParam = req.getParameter("classId");
        String studentIdParam = req.getParameter("studentId");
        String action = req.getParameter("action");

        try {
            if ("details".equals(action) && studentIdParam != null) {
                // Hiển thị chi tiết học sinh
                int studentId = Integer.parseInt(studentIdParam);
                Student detaiStudent = studentDAO.getStudentById(studentId);
                if (detaiStudent != null) {
                    req.setAttribute("detaiStudent", detaiStudent);
                    req.getRequestDispatcher("/Views/Manager/StudentDetails.jsp").forward(req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Student not found");
                }
            } else if (classIdParam != null && !classIdParam.isEmpty()) {
                int classId = Integer.parseInt(classIdParam);
                if ("add".equals(action)) {
                    // Lấy danh sách tất cả học sinh để thêm
                    List<Student> AllStudent = studentDAO.getAllStudents();
                    req.setAttribute("AllStudent", AllStudent);
                    req.setAttribute("classId", classId);
                    req.getRequestDispatcher("/Views/Manager/AddStudentToClass.jsp").forward(req, resp);
                } else {
                    // Lấy danh sách học sinh theo classId
                    List<Student> students = studentDAO.getStudentsByClassId(classId);
                    req.setAttribute("students", students);
                    req.setAttribute("classId", classId);
                    req.getRequestDispatcher("/Views/Manager/listStudentInClass.jsp").forward(req, resp);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Class ID or Student ID is required");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Class ID or Student ID");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to process request");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classIdParam = req.getParameter("classId");
        String studentIdParam = req.getParameter("studentId");
        String action = req.getParameter("action");

        if (classIdParam != null && studentIdParam != null) {
            int classId;
            int studentId;
            try {
                classId = Integer.parseInt(classIdParam);
                studentId = Integer.parseInt(studentIdParam);
                boolean isProcessed;

                if ("remove".equals(action)) {
                    isProcessed = studentDAO.removeStudentFromClass(studentId);
                    if (isProcessed) {
                        resp.setContentType("application/json");
                        resp.getWriter().write("{\"success\": true, \"message\": \"Học sinh đã được xóa khỏi lớp thành công.\"}");
                    } else {
                        resp.setContentType("application/json");
                        resp.getWriter().write("{\"success\": false, \"message\": \"Không thể xóa học sinh khỏi lớp.\"}");
                    }
                } else {
                    boolean isAdded = studentDAO.addStudentToClass(studentId, classId);
                    if (isAdded) {
                        resp.setContentType("application/json");
                        resp.getWriter().write("{\"success\": true, \"message\": \"Học sinh đã được thêm vào lớp thành công.\"}");
                    } else {
                        resp.setContentType("application/json");
                        resp.getWriter().write("{\"success\": false, \"message\": \"Không thể thêm học sinh vào lớp.\"}");
                    }
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Class ID or Student ID");
            } catch (SQLException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to add or update student in class");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Class ID and Student ID are required");
        }
    }


}