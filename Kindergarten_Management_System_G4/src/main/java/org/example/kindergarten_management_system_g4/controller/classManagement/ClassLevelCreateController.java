package org.example.kindergarten_management_system_g4.controller.classManagement;

import org.example.kindergarten_management_system_g4.dao.ClassLevelDAO.IClassLevelDAO;
import org.example.kindergarten_management_system_g4.dao.ClassLevelDAO.implement.ClassLevelDAOImpl;
import org.example.kindergarten_management_system_g4.model.ClassLevel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "addClassLevel", value = "/addClassLevel")
public class ClassLevelCreateController extends HttpServlet {

        private IClassLevelDAO iClassLevelDAO;

    @Override
    public void init() throws ServletException {
            iClassLevelDAO = new ClassLevelDAOImpl(); // Khởi tạo DAO
        }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String name = req.getParameter("classLevelName");
            String description = req.getParameter("description");
            int ageRange = Integer.parseInt(req.getParameter("ageRange"));

            ClassLevel classLevel = new ClassLevel(0, name, description, ageRange); // 0 vì ID tự tăng
            try {
                iClassLevelDAO.addClassLevel(classLevel);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            resp.sendRedirect(req.getContextPath() + "/classLevel");
        }
    }
