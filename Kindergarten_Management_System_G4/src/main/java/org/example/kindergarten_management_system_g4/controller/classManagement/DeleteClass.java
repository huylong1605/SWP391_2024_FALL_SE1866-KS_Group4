package org.example.kindergarten_management_system_g4.controller.classManagement;

import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.impliment.ClassDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(name = "deleteClass", value = "/deleteClass")
public class DeleteClass extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CreateClass.class.getName());

    private IClassDAO iClassDAO;

    public void init() throws ServletException {
        super.init();
        iClassDAO = new ClassDAOImpl();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int classId = Integer.parseInt(req.getParameter("classId"));

        try {
            Boolean isDelete = iClassDAO.deleteClass(classId);

            if(isDelete){

                req.setAttribute("deleteTrue", "delete successful");
                req.getRequestDispatcher("listClass.jsp").forward(req, resp);
            }else{
                req.setAttribute("deleteFalse", "delete false");
                req.getRequestDispatcher("listClass.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            LOGGER.info("SQLException: " +e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
