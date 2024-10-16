package org.example.kindergarten_management_system_g4.controller.classManagement;

import org.example.kindergarten_management_system_g4.dao.ClassLevelDAO.IClassLevelDAO;
import org.example.kindergarten_management_system_g4.dao.ClassLevelDAO.implement.ClassLevelDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "deleteClassLevel", value = "/deleteClassLevel")
public class ClassLevelDeleteController  extends HttpServlet {



        private IClassLevelDAO iClassLevelDAO;


    @Override
    public void init() throws ServletException {
            iClassLevelDAO = new ClassLevelDAOImpl(); // Khởi tạo DAO
        }

    @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            int classLevelId = Integer.parseInt(req.getParameter("classLevelId"));
            try {
                iClassLevelDAO.deleteClassLevel(classLevelId);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            resp.sendRedirect(req.getContextPath() + "/classLevel");
        }
    }

