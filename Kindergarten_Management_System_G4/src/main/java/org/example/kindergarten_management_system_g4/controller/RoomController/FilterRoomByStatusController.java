/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/18/2024       1.1                    vu gia huy                   FilterRoomByStatusController
 */

/**
 * Lớp FilterRoomByStatusController xử lý việc lọc các phòng dựa trên trạng thái của chúng.
 * Lớp này lấy danh sách các phòng theo trạng thái và hỗ trợ phân trang để quản lý hiển thị dữ liệu phòng.
 * Kết quả lọc và thông tin phân trang sẽ được gửi tới giao diện Room.jsp để hiển thị.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * @author Vũ Gia Huy - HE163358
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

@WebServlet(name = "filterRoomByStatus", value = "/filterRoomByStatus")
public class FilterRoomByStatusController extends HttpServlet {
    private IRoomDAO roomDAO;

    /**
     * Phương thức khởi tạo servlet và khởi tạo đối tượng roomDAO để tương tác với lớp dữ liệu phòng.
     *
     * @throws ServletException nếu xảy ra lỗi khi khởi tạo
     */
    @Override
    public void init() throws ServletException {
        roomDAO = new RoomDAO();  // Khởi tạo đối tượng roomDAO để tương tác với dữ liệu phòng
    }

    /**
     * Phương thức xử lý yêu cầu GET để lọc các phòng dựa trên trạng thái hoặc lấy danh sách tất cả phòng có phân trang.
     * Nếu có tham số trạng thái được truyền vào, phương thức sẽ lọc các phòng theo trạng thái đó.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ phía client
     * @param resp Đối tượng HttpServletResponse được sử dụng để gửi phản hồi về cho client
     * @throws ServletException nếu có lỗi xảy ra liên quan đến servlet
     * @throws IOException      nếu có lỗi nhập/xuất trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy tham số trạng thái từ yêu cầu của client
        int status = Integer.parseInt(req.getParameter("status"));
        int page = Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "1");
        int size = 4; // Số lượng phòng hiển thị trên mỗi trang

        // Gọi phương thức filterRoomsByStatus để lấy danh sách phòng theo trạng thái và phân trang
        List<Room> rooms = roomDAO.filterRoomsByStatus(status, page, size);

        // Đếm tổng số phòng theo trạng thái đã chọn
        int totalRooms = roomDAO.countRoomsByStatus(status);
        int totalPages = (int) Math.ceil((double) totalRooms / size); // Tính tổng số trang

        // Đặt các thuộc tính để truyền dữ liệu tới giao diện (JSP)
        req.setAttribute("rooms", rooms); // Danh sách phòng
        req.setAttribute("currentPage", page); // Trang hiện tại
        req.setAttribute("totalPages", totalPages); // Tổng số trang

        // Chuyển tiếp yêu cầu tới Room.jsp để hiển thị danh sách phòng
        req.getRequestDispatcher("/Views/Manager/Room.jsp").forward(req, resp);
    }
}
