package org.example.kindergarten_management_system_g4.controller.authencation;

import org.example.kindergarten_management_system_g4.dao.AuthenDAO.StudentDAO;
import org.example.kindergarten_management_system_g4.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "StudentController", value = "/students")
public class studentController extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Student> students = studentDAO.getAllStudents();
            req.setAttribute("students", students);
            req.getRequestDispatcher("/ViewStudentList.jsp").forward(req, resp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
