package org.example.kindergarten_management_system_g4.controller;

import org.example.kindergarten_management_system_g4.dao.StudentDAO;
import org.example.kindergarten_management_system_g4.model.Student;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
//        HttpSession session = req.getSession();

        // Sửa lỗi truy xuất thuộc tính user từ session
//        User currentUser = (User) session.getAttribute("user");
//        System.out.println("fix0-"+ currentUser);

        try {
            List<Student> students = studentDAO.getAllStudents();
            System.out.println(students);
            req.setAttribute("students", students);
            req.getRequestDispatcher("/ViewStudentList.jsp").forward(req, resp);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
