package org.example.kindergarten_management_system_g4.controller.classManagement;

import org.example.kindergarten_management_system_g4.controller.profileManagement.ChangePasswordController;
import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.impliment.ClassDAOImpl;
import org.example.kindergarten_management_system_g4.dao.profileDAO.Implement.ChangePasswordDAOImpl;
import org.example.kindergarten_management_system_g4.model.ClassDAL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;


@WebServlet(name = "listClass", value = "/listClass")
public class ListClassController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ListClassController.class.getName());
    private IClassDAO iClassDAO;

    public void init() throws ServletException {
        super.init();
        iClassDAO = new ClassDAOImpl();

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            List<ClassDAL> listClass = iClassDAO.listClass();
            req.setAttribute("listClass", listClass);
            req.getRequestDispatcher("listClass.jsp").forward(req, resp);
        } catch (SQLException e) {
            LOGGER.info("SQLException: " +e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
