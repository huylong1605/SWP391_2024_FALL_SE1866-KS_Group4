package org.example.kindergarten_management_system_g4;

import org.example.kindergarten_management_system_g4.dao.AuthenDAO.StudentDAO;
import org.example.kindergarten_management_system_g4.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "registerStudent", value = "/registerStudent")
public class RegisterStudentServlet extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        LocalDate dob = LocalDate.parse(req.getParameter("dob"));
        boolean gender = Boolean.parseBoolean(req.getParameter("gender"));
        int classId = Integer.parseInt(req.getParameter("classId"));
        int userId = Integer.parseInt(req.getParameter("userId"));

        Student newStudent = new Student(0, dob, gender, name, classId, userId); // studentId will be auto-generated

        try {
            studentDAO.addStudent(newStudent); // Make sure to implement this method in StudentDAO
            resp.sendRedirect("studentList.jsp"); // Redirect to student list after successful registration
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Registration failed");
        }
    }
}
