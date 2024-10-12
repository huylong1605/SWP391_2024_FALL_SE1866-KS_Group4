package org.example.kindergarten_management_system_g4.controller.classManagement;

import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.impliment.ClassDAOImpl;
import org.example.kindergarten_management_system_g4.model.ClassDAL;
import org.example.kindergarten_management_system_g4.model.ClassLevel;

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
        int classLevelId = 0;
        String filterLevelParam = req.getParameter("filterLevel");
        String searching = req.getParameter("search");
        HttpSession session = req.getSession();
        String deleteFalse = (String) session.getAttribute("DeleteFalse");
        String deleteSuccessful = (String) session.getAttribute("DeleteSuccessful");
        String createSuccessful = (String) session.getAttribute("CreateSuccessful");
        String updateSuccessful = (String) session.getAttribute("UpdateSuccessful");

        if (filterLevelParam != null && !filterLevelParam.isEmpty()) {
            classLevelId = Integer.parseInt(filterLevelParam);
        }
        List<ClassDAL> listClass;
        try {
            boolean isSearchingEmpty = (searching == null || searching.isEmpty());
            if (classLevelId == 0 && isSearchingEmpty) {
                listClass = iClassDAO.listClass();
            } else if (isSearchingEmpty) {
                listClass = iClassDAO.listClassFilter(classLevelId);
            } else if (classLevelId == 0) {
                listClass = iClassDAO.listClassSearch(searching);
            } else {
                listClass = iClassDAO.listClassSearchFilter(classLevelId, searching);
            }
            if (deleteSuccessful != null) {
                // Đặt thông báo vào request
                req.setAttribute("deleteSuccessful", deleteSuccessful);
                // Xóa thông báo khỏi session để tránh hiển thị lại
                session.removeAttribute("DeleteSuccessful");
            }
            if (deleteFalse != null) {
                req.setAttribute("deleteFalse", deleteFalse);
                // Xóa thông báo khỏi session để tránh hiển thị lại
                session.removeAttribute("DeleteFalse");
            }
            if (createSuccessful != null) {
                req.setAttribute("createSuccessful", createSuccessful);
                // Xóa thông báo khỏi session để tránh hiển thị lại
                session.removeAttribute("CreateSuccessful");
            }

            if (updateSuccessful != null) {
                // Gán thông báo vào request để hiển thị trong JSP
                req.setAttribute("updateSuccessful", updateSuccessful);

                // Xóa thuộc tính khỏi session để tránh hiển thị lại trong các yêu cầu khác
                session.removeAttribute("UpdateSuccessful");
            }

            List<ClassLevel> listClassLevel = iClassDAO.listClassLevel();
            req.setAttribute("listClass", listClass);
            req.setAttribute("listClassLevel", listClassLevel);
            req.getRequestDispatcher("listClass.jsp").forward(req, resp);
        } catch (SQLException e) {
            LOGGER.info("SQLException: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
