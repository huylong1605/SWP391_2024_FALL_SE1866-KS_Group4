/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/18/2024       1.1                    vu gia huy                    SearchRoomController
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
 * SearchRoomController xử lý logic tìm kiếm và hiển thị các phòng với tính năng phân trang.
 * Nó hỗ trợ tìm kiếm phòng theo số phòng hoặc lấy tất cả các phòng nếu không có tiêu chí tìm kiếm nào được cung cấp.
 * Kết quả tìm kiếm và dữ liệu phân trang được chuyển đến view Room.jsp để hiển thị.
 *
 * <p>Lỗi: Chưa phát hiện lỗi</p>
 *
 * @author Vũ Gia Huy - HE163358
 */
@WebServlet(name = "searchRoom", value = "/searchRoom")
public class SearchRoomController extends HttpServlet {
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
     * Xử lý yêu cầu HTTP GET để tìm kiếm phòng hoặc lấy danh sách phòng có phân trang.
     * Nếu số phòng được cung cấp, phương thức sẽ tìm kiếm các phòng theo số phòng với tính năng phân trang.
     * Ngược lại, nó sẽ lấy tất cả các phòng với phân trang.
     *
     * @param req  đối tượng HttpServletRequest chứa dữ liệu yêu cầu
     * @param resp đối tượng HttpServletResponse dùng để gửi phản hồi lại cho client
     * @throws ServletException nếu có lỗi xảy ra liên quan đến servlet
     * @throws IOException      nếu có lỗi I/O trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchNumber = req.getParameter("searchNumber");
        int page = Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "1");
        int size = 4; // Số lượng phòng mỗi trang
        List<Room> rooms;

        // Nếu có searchNumber, tìm kiếm phòng theo tên, nếu không thì lấy tất cả phòng với phân trang
        if (searchNumber != null && !searchNumber.isEmpty()) {
            rooms = roomDAO.searchRoomsByName(searchNumber, page, size); // Tìm kiếm với phân trang
        } else {
            rooms = roomDAO.getAllRooms(page, size); // Lấy tất cả phòng với phân trang
        }

        int totalRooms = roomDAO.countRooms(); // Tổng số phòng
        int totalPages = (int) Math.ceil((double) totalRooms / size); // Tổng số trang

        // Thiết lập các thuộc tính cho đối tượng request để chuyển tới view
        req.setAttribute("rooms", rooms);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        // Chuyển tiếp yêu cầu tới Room.jsp để hiển thị giao diện
        req.getRequestDispatcher("/Views/Manager/Room.jsp").forward(req, resp);
    }
}
