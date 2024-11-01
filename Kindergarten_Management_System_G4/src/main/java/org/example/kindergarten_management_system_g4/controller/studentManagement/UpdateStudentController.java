package org.example.kindergarten_management_system_g4.controller.studentManagement;

import org.example.kindergarten_management_system_g4.dao.StudentDAO;
import org.example.kindergarten_management_system_g4.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
@WebServlet(name = "updateStudent", value = "/updateStudent")
public class UpdateStudentController extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String name = request.getParameter("name");
            LocalDate dob = LocalDate.parse(request.getParameter("dob"));
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));

            Student student = new Student(studentId, dob, gender, name);

            boolean isUpdated = studentDAO.updateStudent(student);

            if (isUpdated) {
                response.sendRedirect("viewStudentList"); // or return success message
            } else {
                response.sendRedirect("error.jsp"); // or display an error
            }
        } catch (Exception e) {
            throw new ServletException("Error updating student", e);
        }
    }
}
