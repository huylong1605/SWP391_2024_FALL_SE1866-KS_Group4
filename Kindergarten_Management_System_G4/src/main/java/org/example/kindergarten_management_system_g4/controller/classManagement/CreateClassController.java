/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/11/2024       1.1              Nguyễn Huy Long - He160140               Create create class
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
 * CreateClassController là lớp điều khiển cho việc tạo lớp học mới.
 * Lớp này sẽ xử lý các yêu cầu GET và POST cho trang tạo lớp học.
 * @author Nguyễn Huy Long
 */
@WebServlet(name = "createClass", value = "/createClass")
public class CreateClassController extends HttpServlet {

    // Logger để ghi lại các thông báo lỗi hoặc thông tin
    private static final Logger LOGGER = Logger.getLogger(CreateClassController.class.getName());
    private static final int MAX_LENGTH = 10; // Giới hạn độ dài tối đa cho tên lớp
    private IClassDAO iClassDAO; // Interface cho các phương thức quản lý lớp học

    /**
     * Phương thức init được gọi khi servlet được khởi tạo.
     * Sử dụng để khởi tạo các đối tượng cần thiết, chẳng hạn như iClassDAO.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        iClassDAO = new ClassDAOImpl(); // Khởi tạo đối tượng DAO cho lớp học
    }

    /**
     * Phương thức xử lý yêu cầu GET.
     * Nhận thông tin cần thiết để tạo lớp học và chuyển tiếp đến trang createClass.jsp.
     *
     * @param req  HttpServletRequest chứa thông tin yêu cầu từ client
     * @param resp HttpServletResponse chứa thông tin phản hồi cho client
     * @throws ServletException nếu có lỗi khi xử lý yêu cầu
     * @throws IOException      nếu có lỗi khi gửi phản hồi
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Lấy danh sách phòng, giáo viên và cấp lớp từ DAO
            List<Room> listRoom = iClassDAO.getListRoom();
            List<User> listTeacher = iClassDAO.getListTeacher();
            List<ClassLevel> listClassLevel = iClassDAO.getListClassLevel();
            // Đặt thuộc tính vào yêu cầu để sử dụng trong JSP
            req.setAttribute("listRoom", listRoom);
            req.setAttribute("listTeacher", listTeacher);
            req.setAttribute("listClassLevel", listClassLevel);
            // Chuyển tiếp đến trang createClass.jsp
            req.getRequestDispatcher("createClass.jsp").forward(req, resp);
        } catch (SQLException e) {
            // Ghi lại thông báo lỗi nếu có SQLException
            LOGGER.info("SQLException: " + e.getMessage());
        }
    }

    /**
     * Phương thức xử lý yêu cầu POST.
     * Nhận thông tin từ yêu cầu, kiểm tra tính hợp lệ và tạo lớp học mới nếu hợp lệ.
     *
     * @param req  HttpServletRequest chứa thông tin yêu cầu từ client
     * @param resp HttpServletResponse chứa thông tin phản hồi cho client
     * @throws ServletException nếu có lỗi khi xử lý yêu cầu
     * @throws IOException      nếu có lỗi khi gửi phản hồi
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy thông tin từ yêu cầu và loại bỏ khoảng trắng thừa
        String className = req.getParameter("className").trim().replaceAll("\\s+", " ");
        int classLevel = Integer.parseInt(req.getParameter("classLevelId"));
        int teacher = Integer.parseInt(req.getParameter("userId"));
        int room = Integer.parseInt(req.getParameter("roomId"));

        try {
            // Kiểm tra xem tên lớp đã tồn tại chưa
            String classNameExist = iClassDAO.getClassName(className);

            // Nếu tên lớp đã tồn tại, gửi thông báo lỗi và quay lại trang tạo lớp
            if (className.equals(classNameExist)) {
                List<Room> listRoom = iClassDAO.getListRoom();
                List<User> listTeacher = iClassDAO.getListTeacher();
                List<ClassLevel> listClassLevel = iClassDAO.getListClassLevel();
                req.setAttribute("classNameExist", "Class name already exists");
                req.setAttribute("listRoom", listRoom);
                req.setAttribute("listTeacher", listTeacher);
                req.setAttribute("listClassLevel", listClassLevel);
                req.getRequestDispatcher("createClass.jsp").forward(req, resp);
                LOGGER.info("classNameExist:  " + className + "=" + classNameExist);
                return;
            }

            // Kiểm tra độ dài tên lớp
            if (className.length() > MAX_LENGTH) {
                List<Room> listRoom = iClassDAO.getListRoom();
                List<User> listTeacher = iClassDAO.getListTeacher();
                List<ClassLevel> listClassLevel = iClassDAO.getListClassLevel();
                req.setAttribute("maxLength", "Class name can not > 10 character");
                req.setAttribute("listRoom", listRoom);
                req.setAttribute("listTeacher", listTeacher);
                req.setAttribute("listClassLevel", listClassLevel);
                req.getRequestDispatcher("createClass.jsp").forward(req, resp);
                LOGGER.info("classNameLength: " + className + " exceeds max length");
                return;
            }

            // Tạo đối tượng Classes và lưu vào cơ sở dữ liệu
            Classes classes = new Classes(className, classLevel, teacher, room);
            iClassDAO.createClass(classes);
            HttpSession session = req.getSession();
            // Đặt thông báo thành công vào session
            session.setAttribute("CreateSuccessful", "Create class " + className + " successful");
            // Chuyển hướng đến danh sách lớp
            resp.sendRedirect("listClass");
        } catch (SQLException e) {
            // Ghi lại thông báo lỗi nếu có SQLException
            LOGGER.info("SQLException: " + e.getMessage());
        }
    }
}
