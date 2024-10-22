/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/18/2024       1.1                    vu gia huy                   RoomListController
 */
package org.example.kindergarten_management_system_g4.controller.RoomController;

import org.example.kindergarten_management_system_g4.dao.RoomDAO.IRoomDAO;
import org.example.kindergarten_management_system_g4.dao.RoomDAO.RoomDAO;
import org.example.kindergarten_management_system_g4.model.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Lớp RoomListingController chịu trách nhiệm hiển thị danh sách các phòng với phân trang.
 * Nó truy vấn các phòng từ cơ sở dữ liệu, tính toán phân trang và chuyển dữ liệu sang giao diện Room.jsp.
 *
 * <p>Lỗi: Chưa phát hiện lỗi</p>
 *
 * @author Vũ Gia Huy - HE163358
 */
@WebServlet(name = "listRoom", value = "/Views/Admin/listRoom")
public class RoomListingController extends HttpServlet {
    private IRoomDAO roomDAO;

    /**
     * Phương thức khởi tạo servlet và thiết lập đối tượng roomDAO để tương tác với tầng dữ liệu của phòng.
     *
     * @throws ServletException nếu xảy ra lỗi khởi tạo
     */
    @Override
    public void init() throws ServletException {
        super.init();
        roomDAO = new RoomDAO();
    }

    /**
     * Xử lý yêu cầu HTTP GET để lấy danh sách các phòng với phân trang.
     * Phương thức này sẽ lấy các phòng cho trang được chỉ định và thiết lập các thuộc tính liên quan đến phân trang.
     *
     * @param req  đối tượng HttpServletRequest chứa dữ liệu yêu cầu
     * @param resp đối tượng HttpServletResponse dùng để gửi phản hồi lại cho client
     * @throws ServletException nếu có lỗi xảy ra liên quan đến servlet
     * @throws IOException      nếu có lỗi I/O trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "1");
        int size = 4; // Số lượng phòng mỗi trang

        List<Room> rooms = roomDAO.getAllRooms(page, size); // Lấy danh sách các phòng với phân trang
        int totalRooms = roomDAO.countRooms(); // Lấy tổng số phòng

        int totalPages = (int) Math.ceil((double) totalRooms / size); // Tính toán tổng số trang

        // Thiết lập các thuộc tính để truyền cho view
        req.setAttribute("rooms", rooms);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        // Chuyển tiếp yêu cầu đến Room.jsp để hiển thị giao diện
        req.getRequestDispatcher("/Views/Admin/Room.jsp").forward(req, resp);
    }

    /**
     * Xử lý yêu cầu HTTP POST. Hiện tại phương thức này chưa được sử dụng nhưng được ghi đè để hỗ trợ các chức năng trong tương lai.
     *
     * @param req  đối tượng HttpServletRequest chứa dữ liệu yêu cầu
     * @param resp đối tượng HttpServletResponse dùng để gửi phản hồi lại cho client
     * @throws ServletException nếu có lỗi xảy ra liên quan đến servlet
     * @throws IOException      nếu có lỗi I/O trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
