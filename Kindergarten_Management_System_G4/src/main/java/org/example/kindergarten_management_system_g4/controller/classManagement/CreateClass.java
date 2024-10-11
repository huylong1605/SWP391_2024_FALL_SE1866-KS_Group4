package org.example.kindergarten_management_system_g4.controller.classManagement;

import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.impliment.ClassDAOImpl;
import org.example.kindergarten_management_system_g4.model.ClassLevel;
import org.example.kindergarten_management_system_g4.model.Classes;
import org.example.kindergarten_management_system_g4.model.Room;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "createClass", value = "/createClass")
public class CreateClass extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CreateClass.class.getName());
     private IClassDAO iClassDAO;

    public void init() throws ServletException {
        super.init();
        iClassDAO = new ClassDAOImpl();

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Room> listRoom = iClassDAO.listRoom();
            List<User> listTeacher = iClassDAO.listTeacher();
            List<ClassLevel> listClassLevel = iClassDAO.listClassLevel();

            req.setAttribute("listRoom", listRoom);
            req.setAttribute("listTeacher", listTeacher);
            req.setAttribute("listClassLevel", listClassLevel);
            req.getRequestDispatcher("createClass.jsp").forward(req, resp);
        } catch (SQLException e) {
            LOGGER.info("SQLException: " +e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String className = req.getParameter("className").trim().replaceAll("\\s+", " ");
        int classLevel = Integer.parseInt(req.getParameter("classLevelId"));
        int teacher = Integer.parseInt(req.getParameter("userId"));
        int room = Integer.parseInt(req.getParameter("roomId"));

        try {
            String classNameExist = iClassDAO.getClassName(className);

            if(className.equals(classNameExist)){

                List<Room> listRoom = iClassDAO.listRoom();
                List<User> listTeacher = iClassDAO.listTeacher();
                List<ClassLevel> listClassLevel = iClassDAO.listClassLevel();
                req.setAttribute("classNameExist", "Class name already exists");
                req.setAttribute("listRoom", listRoom);
                req.setAttribute("listTeacher", listTeacher);
                req.setAttribute("listClassLevel", listClassLevel);
                req.getRequestDispatcher("createClass.jsp").forward(req, resp);
                LOGGER.info("classNameExist:  " + className +"=" +classNameExist );
                 return;
            }

            Classes classes = new Classes(className, classLevel, teacher, room );
            iClassDAO.createClass(classes);
            resp.sendRedirect("listClass");
//            req.setAttribute("createSuccessful", "Create class " + className +" Successful");
//            req.getRequestDispatcher("createClass.jsp").forward(req, resp);
        } catch (SQLException e) {
            LOGGER.info("SQLException: " +e.getMessage());
        }




    }
}
