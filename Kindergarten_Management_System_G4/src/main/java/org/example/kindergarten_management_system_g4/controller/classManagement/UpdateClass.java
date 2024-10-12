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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "updateClass", value = "/updateClass")
public class UpdateClass extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ListClassController.class.getName());
    private static final int MAX_LENGTH = 10;
    private IClassDAO iClassDAO;

    public void init() throws ServletException {
        super.init();
        iClassDAO = new ClassDAOImpl();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String classId = req.getParameter("classId");

        try {
            Classes classes = iClassDAO.getClassById(Integer.parseInt(classId));
            List<User> teacher = iClassDAO.listTeacher(classes.getUserId());
            List<Room> room = iClassDAO.listRoom(classes.getRoomId());
            List<ClassLevel> listClassLevel = iClassDAO.listClassLevel();

            req.setAttribute("classObject", classes);
            req.setAttribute("listRoom", room);
            req.setAttribute("listTeacher", teacher);
            req.setAttribute("listClassLevel", listClassLevel);
            req.getRequestDispatcher("updateClass.jsp").forward(req, resp);
        } catch (SQLException e) {
            LOGGER.info("SQLException: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int classId = Integer.parseInt(req.getParameter("classId"));
        String className = req.getParameter("className").trim().replaceAll("\\s+", " ");
        int classLevel = Integer.parseInt(req.getParameter("classLevelId"));
        int teacher = Integer.parseInt(req.getParameter("userId"));
        int room = Integer.parseInt(req.getParameter("roomId"));


        try {
            String classIds = req.getParameter("classId");
            Classes classesee = iClassDAO.getClassById(Integer.parseInt(classIds));
            String classNames = iClassDAO.getClassNameUpdate(className, classId);
            if (className.length() > MAX_LENGTH) {

                Classes classese = iClassDAO.getClassById(Integer.parseInt(classIds));
                List<User> teacherr = iClassDAO.listTeacher(classese.getUserId());
                List<Room> roomm = iClassDAO.listRoom(classese.getRoomId());
                List<ClassLevel> listClassLevel = iClassDAO.listClassLevel();
                req.setAttribute("maxLength", "Class name can not > 10 character");
                req.setAttribute("classObject", classesee);
                req.setAttribute("listRoom", roomm);
                req.setAttribute("listTeacher", teacherr);
                req.setAttribute("listClassLevel", listClassLevel);
                req.getRequestDispatcher("updateClass.jsp").forward(req, resp);
                return;
            }
            if (className.equals(classNames)) {

                Classes classese = iClassDAO.getClassById(Integer.parseInt(classIds));
                List<User> teacherr = iClassDAO.listTeacher(classese.getUserId());
                List<Room> roomm = iClassDAO.listRoom(classese.getRoomId());
                List<ClassLevel> listClassLevel = iClassDAO.listClassLevel();
                req.setAttribute("classNameExist", "Class name is exist");
                req.setAttribute("classObject", classesee);
                req.setAttribute("listRoom", roomm);
                req.setAttribute("listTeacher", teacherr);
                req.setAttribute("listClassLevel", listClassLevel);
                req.getRequestDispatcher("updateClass.jsp").forward(req, resp);
                return;
            }

            Classes classes = new Classes(classId, className, classLevel, teacher, room);
            iClassDAO.updateClass(classes);
            HttpSession session = req.getSession();
            session.setAttribute("UpdateSuccessful", "Update class " + className + " successful");
            resp.sendRedirect("listClass");
        } catch (SQLException e) {
            LOGGER.info("SQLException: " + e.getMessage());
        }

    }
}
