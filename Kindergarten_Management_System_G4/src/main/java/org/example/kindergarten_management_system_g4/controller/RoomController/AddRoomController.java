/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/18/2024       1.1                    vu gia huy                   AddRoomController
 */
/**
 * Lớp AddRoomController xử lý việc thêm thông tin phòng vào hệ thống.
 * Lớp này nhận yêu cầu từ client để thêm thông tin phòng mới và
 * thực hiện các thao tác lưu trữ vào cơ sở dữ liệu.
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

@WebServlet(name = "addRoom", value = "/addRoom")
public class AddRoomController extends HttpServlet {
    private IRoomDAO roomDAO;

    /**
     * Phương thức khởi tạo servlet và khởi tạo đối tượng roomDAO để tương tác với lớp dữ liệu phòng.
     *
     * @throws ServletException nếu xảy ra lỗi khi khởi tạo
     */
    @Override
    public void init() throws ServletException {
        super.init(); // Gọi phương thức khởi tạo của lớp cha
        roomDAO = new RoomDAO(); // Khởi tạo đối tượng roomDAO để tương tác với dữ liệu phòng
    }

    /**
     * Phương thức xử lý yêu cầu GET để hiển thị trang thêm phòng.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ phía client
     * @param resp Đối tượng HttpServletResponse được sử dụng để gửi phản hồi về cho client
     * @throws ServletException nếu có lỗi xảy ra liên quan đến servlet
     * @throws IOException      nếu có lỗi nhập/xuất trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/Views/Manager/addRoom.jsp").forward(req, resp); // Chuyển tiếp yêu cầu tới trang thêm phòng
    }

    /**
     * Phương thức xử lý yêu cầu POST để thêm thông tin phòng.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ phía client
     * @param resp Đối tượng HttpServletResponse được sử dụng để gửi phản hồi về cho client
     * @throws ServletException nếu có lỗi xảy ra liên quan đến servlet
     * @throws IOException      nếu có lỗi nhập/xuất trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy giá trị từ request và loại bỏ khoảng trắng ở đầu, cuối và giữa các chuỗi liên tiếp
        String roomNumber = req.getParameter("roomNumber")/*.replaceAll("\\s+", "")*/; // Xóa khoảng trắng
        int capacity = Integer.parseInt(req.getParameter("capacity")); // Lấy sức chứa
        int status = Integer.parseInt(req.getParameter("status")); // Lấy trạng thái

        // Khởi tạo đối tượng Room
        Room room = new Room();
        room.setRoomNumber(roomNumber); // Thiết lập số phòng
        room.setCapacity(capacity); // Thiết lập sức chứa
        room.setStatus(status); // Thiết lập trạng thái

        // Thêm phòng và kiểm tra kết quả
        boolean isAdded = roomDAO.addRoom(room); // Thực hiện thêm phòng vào cơ sở dữ liệu
        if (isAdded) {
            req.getSession().setAttribute("successMessage", "Room added successfully!"); // Thiết lập thông báo thành công
            resp.sendRedirect(req.getContextPath() + "/Views/Manager/listRoom"); // Chuyển hướng về trang danh sách phòng
        } else {
            req.getSession().setAttribute("errorMessage", "Error adding room. Please check your input."); // Thiết lập thông báo lỗi
            req.getRequestDispatcher("addRoom.jsp").forward(req, resp); // Chuyển tiếp tới trang thêm phòng
        }
    }
}
