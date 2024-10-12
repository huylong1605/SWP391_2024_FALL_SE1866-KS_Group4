/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/11/2024       1.1              Nguyễn Huy Long - He160140              Create class update
 */
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

/**
 * UpdateClassController xử lý việc cập nhật thông tin lớp trong Hệ thống Quản lý Mầm non.
 * Nó xử lý cả yêu cầu GET và POST để cập nhật thông tin lớp.
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "updateClass", value = "/updateClass")
public class UpdateClassController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UpdateClassController.class.getName());
    private static final int MAX_LENGTH = 10; // Độ dài tối đa cho tên lớp
    private IClassDAO iClassDAO;

    /**
     * Khởi tạo UpdateClassController và khởi tạo lớp DAO để xử lý lớp.
     */
    public void init() throws ServletException {
        super.init();
        iClassDAO = new ClassDAOImpl(); // Tạo thể hiện của ClassDAOImpl
    }

    /**
     * Xử lý yêu cầu GET để hiển thị biểu mẫu cập nhật lớp với thông tin lớp hiện có.
     *
     * @param req đối tượng HttpServletRequest
     * @param resp đối tượng HttpServletResponse
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý servlet
     * @throws IOException nếu có lỗi nhập/xuất xảy ra
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classId = req.getParameter("classId"); // Lấy ID lớp từ yêu cầu

        try {
            // Lấy thông tin lớp dựa trên ID lớp
            Classes classes = iClassDAO.getClassById(Integer.parseInt(classId));
            // Lấy danh sách giáo viên và phòng liên quan đến lớp
            List<User> teacher = iClassDAO.getListTeacher(classes.getUserId());
            List<Room> room = iClassDAO.getListRoom(classes.getRoomId());
            List<ClassLevel> listClassLevel = iClassDAO.getListClassLevel();

            // Đặt thuộc tính cho JSP
            req.setAttribute("classObject", classes);
            req.setAttribute("listRoom", room);
            req.setAttribute("listTeacher", teacher);
            req.setAttribute("listClassLevel", listClassLevel);
            req.getRequestDispatcher("updateClass.jsp").forward(req, resp); // Chuyển tiếp đến updateClass.jsp
        } catch (SQLException e) {
            LOGGER.info("SQLException: " + e.getMessage());
        }
    }

    /**
     * Xử lý yêu cầu POST để cập nhật thông tin lớp.
     *
     * @param req đối tượng HttpServletRequest
     * @param resp đối tượng HttpServletResponse
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý servlet
     * @throws IOException nếu có lỗi nhập/xuất xảy ra
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int classId = Integer.parseInt(req.getParameter("classId")); // Lấy ID lớp từ yêu cầu
        String className = req.getParameter("className").trim().replaceAll("\\s+", " "); // Chuẩn hóa tên lớp
        int classLevel = Integer.parseInt(req.getParameter("classLevelId")); // Lấy cấp độ lớp
        int teacher = Integer.parseInt(req.getParameter("userId")); // Lấy ID giáo viên
        int room = Integer.parseInt(req.getParameter("roomId")); // Lấy ID phòng

        try {
            // Kiểm tra xem tên lớp có vượt quá độ dài tối đa không
            if (className.length() > MAX_LENGTH) {
                // Lấy thông tin lớp hiện có để hiển thị thông báo lỗi
                Classes classese = iClassDAO.getClassById(classId);
                List<User> teacherr = iClassDAO.getListTeacher(classese.getUserId());
                List<Room> roomm = iClassDAO.getListRoom(classese.getRoomId());
                List<ClassLevel> listClassLevel = iClassDAO.getListClassLevel();

                // Đặt thông báo lỗi cho JSP
                req.setAttribute("maxLength", "Tên lớp không được > 10 ký tự");
                req.setAttribute("classObject", classese);
                req.setAttribute("listRoom", roomm);
                req.setAttribute("listTeacher", teacherr);
                req.setAttribute("listClassLevel", listClassLevel);
                req.getRequestDispatcher("updateClass.jsp").forward(req, resp);
                return; // Kết thúc hàm nếu có lỗi
            }

            // Kiểm tra xem tên lớp đã tồn tại hay chưa
            String classNames = iClassDAO.getClassNameUpdate(className, classId);
            if (className.equals(classNames)) {
                // Lấy thông tin lớp hiện có để hiển thị thông báo lỗi
                Classes classese = iClassDAO.getClassById(classId);
                List<User> teacherr = iClassDAO.getListTeacher(classese.getUserId());
                List<Room> roomm = iClassDAO.getListRoom(classese.getRoomId());
                List<ClassLevel> listClassLevel = iClassDAO.getListClassLevel();

                // Đặt thông báo lỗi cho JSP
                req.setAttribute("classNameExist", "Tên lớp đã tồn tại");
                req.setAttribute("classObject", classese);
                req.setAttribute("listRoom", roomm);
                req.setAttribute("listTeacher", teacherr);
                req.setAttribute("listClassLevel", listClassLevel);
                req.getRequestDispatcher("updateClass.jsp").forward(req, resp);
                return; // Kết thúc hàm nếu có lỗi
            }

            // Tạo đối tượng lớp mới với thông tin đã cập nhật
            Classes classes = new Classes(classId, className, classLevel, teacher, room);
            iClassDAO.updateClass(classes); // Cập nhật lớp trong cơ sở dữ liệu
            HttpSession session = req.getSession();
            // Thiết lập thông báo thành công trong phiên
            session.setAttribute("UpdateSuccessful", "Cập nhật lớp " + className + " thành công");
            resp.sendRedirect("listClass"); // Chuyển hướng đến danh sách lớp
        } catch (SQLException e) {
            LOGGER.info("SQLException: " + e.getMessage());
        }
    }
}
