package org.example.kindergarten_management_system_g4.controller.homePageController;

import org.example.kindergarten_management_system_g4.dao.StudentClassManageDAO.StudentClassManageDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Views/HomePage/HomePageForManager")
public class homePageManager extends HttpServlet {

    private StudentClassManageDAO studentClassManageDAO;
    @Override
    public void init() throws ServletException {
        studentClassManageDAO = new StudentClassManageDAO(); // Khởi tạo DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentCountWithoutClass = studentClassManageDAO.countAllUnassignedStudents(); // Lấy số học sinh chưa có lớp
        request.setAttribute("studentCountWithoutClass", studentCountWithoutClass); // Đưa số liệu vào request
        request.getRequestDispatcher("/Views/HomePage/HomePageForManager.jsp").forward(request, response); // Chuyển tiếp đến JSP
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
