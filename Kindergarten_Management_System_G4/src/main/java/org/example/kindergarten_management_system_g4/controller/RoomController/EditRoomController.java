/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/18/2024       1.1                    vu gia huy                   EditRoomController
 */
/**
 * Lớp EditRoomController xử lý việc chỉnh sửa thông tin của phòng trong hệ thống.
 * Lớp này nhận thông tin phòng từ client, cập nhật vào cơ sở dữ liệu,
 * và quản lý thông báo thành công hoặc thất bại cho người dùng.
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

@WebServlet(name = "editRoom", value = "/editRoom")
public class EditRoomController extends HttpServlet {
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
     * Phương thức xử lý yêu cầu GET để lấy thông tin phòng theo ID và chuyển tiếp tới giao diện chỉnh sửa.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ phía client
     * @param resp Đối tượng HttpServletResponse được sử dụng để gửi phản hồi về cho client
     * @throws ServletException nếu có lỗi xảy ra liên quan đến servlet
     * @throws IOException nếu có lỗi nhập/xuất trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roomId = Integer.parseInt(req.getParameter("id")); // Lấy ID phòng từ tham số yêu cầu
        Room room = roomDAO.getRoomById(roomId); // Lấy thông tin phòng từ cơ sở dữ liệu
        req.setAttribute("room", room); // Đặt thông tin phòng vào thuộc tính để gửi tới giao diện
        req.getRequestDispatcher("/Views/Admin/editRoom.jsp").forward(req, resp); // Chuyển tiếp tới trang chỉnh sửa
    }

    /**
     * Phương thức xử lý yêu cầu POST để cập nhật thông tin phòng.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ phía client
     * @param resp Đối tượng HttpServletResponse được sử dụng để gửi phản hồi về cho client
     * @throws ServletException nếu có lỗi xảy ra liên quan đến servlet
     * @throws IOException nếu có lỗi nhập/xuất trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roomId = Integer.parseInt(req.getParameter("roomId")); // Lấy ID phòng từ tham số yêu cầu
        String roomNumber = req.getParameter("roomNumber"); // Lấy số phòng từ tham số yêu cầu
        int capacity = Integer.parseInt(req.getParameter("capacity")); // Lấy sức chứa phòng từ tham số yêu cầu
        int status = Integer.parseInt(req.getParameter("status")); // Lấy trạng thái phòng từ tham số yêu cầu

        Room room = new Room(); // Tạo một đối tượng Room mới
        room.setRoomId(roomId); // Đặt ID phòng
        room.setRoomNumber(roomNumber); // Đặt số phòng
        room.setCapacity(capacity); // Đặt sức chứa phòng
        room.setStatus(status); // Đặt trạng thái phòng

        // Cập nhật thông tin phòng vào cơ sở dữ liệu
        boolean isUpdated = roomDAO.updateRoom(room);
        if (isUpdated) {
            req.getSession().setAttribute("successMessage", "Room updated successfully!"); // Thiết lập thông báo thành công
            resp.sendRedirect(req.getContextPath() + "/Views/Admin/listRoom"); // Chuyển hướng tới danh sách phòng
        } else {
            req.getSession().setAttribute("errorMessage", "Error edit room. Please check your input."); // Thiết lập thông báo lỗi
            req.getRequestDispatcher("/Views/Admin/editRoom.jsp").forward(req, resp); // Chuyển tiếp trở lại trang chỉnh sửa
        }
    }
}
