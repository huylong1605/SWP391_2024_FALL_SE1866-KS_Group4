/*
 * Copyright(C) 2005, SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/5/2024       1.1              Nguyễn Huy Long - He160140              Tạo lớp danh sách
 */

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

/**
 * Triển khai Servlet để quản lý danh sách lớp học.
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "listClass", value = "/listClass")
public class ListClassController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ListClassController.class.getName());
    private IClassDAO iClassDAO;

    /**
     * Khởi tạo Servlet và thiết lập đối tượng truy cập dữ liệu.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        iClassDAO = new ClassDAOImpl(); // Khởi tạo triển khai DAO cho lớp học
    }

    /**
     * Xử lý các yêu cầu GET để lấy danh sách lớp học.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException nếu có lỗi xảy ra trong servlet
     * @throws IOException      nếu có lỗi I/O xảy ra
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int classLevelId = 0;
        String filterLevelParam = req.getParameter("filterLevel");
        String searching = req.getParameter("search");
        HttpSession session = req.getSession();

        // Lấy thông báo từ phiên nếu có
        String deleteFalse = (String) session.getAttribute("DeleteFalse");
        String deleteSuccessful = (String) session.getAttribute("DeleteSuccessful");
        String createSuccessful = (String) session.getAttribute("CreateSuccessful");
        String updateSuccessful = (String) session.getAttribute("UpdateSuccessful");

        // Xác định cấp lớp từ tham số yêu cầu
        if (filterLevelParam != null && !filterLevelParam.isEmpty()) {
            classLevelId = Integer.parseInt(filterLevelParam);
        }

        List<ClassDAL> listClass;
        try {
            boolean isSearchingEmpty = (searching == null || searching.isEmpty());
            // Lấy danh sách lớp học dựa trên các tiêu chí tìm kiếm
            if (classLevelId == 0 && isSearchingEmpty) {
                listClass = iClassDAO.getListClass(); // Lấy toàn bộ lớp
            } else if (isSearchingEmpty) {
                listClass = iClassDAO.getListClassFilter(classLevelId); // Lọc theo cấp lớp
            } else if (classLevelId == 0) {
                listClass = iClassDAO.getListClassSearch(searching); // Tìm kiếm theo từ khóa
            } else {
                listClass = iClassDAO.getListClassSearchFilter(classLevelId, searching); // Tìm kiếm và lọc
            }

            // Thiết lập thông báo cho từng trường hợp
            if (deleteSuccessful != null) {
                req.setAttribute("deleteSuccessful", deleteSuccessful);
                session.removeAttribute("DeleteSuccessful");
            }
            if (deleteFalse != null) {
                req.setAttribute("deleteFalse", deleteFalse);
                session.removeAttribute("DeleteFalse");
            }
            if (createSuccessful != null) {
                req.setAttribute("createSuccessful", createSuccessful);
                session.removeAttribute("CreateSuccessful");
            }
            if (updateSuccessful != null) {
                req.setAttribute("updateSuccessful", updateSuccessful);
                session.removeAttribute("UpdateSuccessful");
            }

            // Lấy danh sách cấp lớp để hiển thị trong JSP
            List<ClassLevel> listClassLevel = iClassDAO.getListClassLevel();
            req.setAttribute("listClass", listClass);
            req.setAttribute("listClassLevel", listClassLevel);

            // Chuyển hướng tới JSP để hiển thị danh sách lớp
            req.getRequestDispatcher("listClass.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            LOGGER.severe("SQLException: " + e.getMessage()); // Ghi lại lỗi SQL
        }
    }

    /**
     * Phương thức xử lý yêu cầu POST.
     * Hiện tại không có logic nào được xử lý trong phương thức này.
     * Có thể được sử dụng trong tương lai cho các yêu cầu gửi dữ liệu.
     *
     * @param req  HttpServletRequest chứa thông tin yêu cầu từ client
     * @param resp HttpServletResponse chứa thông tin phản hồi cho client
     * @throws ServletException nếu có lỗi khi xử lý yêu cầu
     * @throws IOException      nếu có lỗi khi gửi phản hồi
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Phương thức POST không được sử dụng ở đây
    }
}
