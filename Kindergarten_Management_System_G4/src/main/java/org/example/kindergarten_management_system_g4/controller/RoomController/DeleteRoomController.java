/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/18/2024       1.1                    vu gia huy                   DeleteRoomController
 */

/**
 * Lớp DeleteRoomController xử lý việc xóa thông tin của phòng trong hệ thống.
 * Lớp này nhận yêu cầu xóa phòng từ client và thực hiện xóa thông tin phòng
 * khỏi cơ sở dữ liệu, đồng thời quản lý thông báo thành công hoặc thất bại cho người dùng.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * @author Vũ Gia Huy - HE163358
 */

package org.example.kindergarten_management_system_g4.controller.RoomController;

import org.example.kindergarten_management_system_g4.dao.RoomDAO.IRoomDAO;
import org.example.kindergarten_management_system_g4.dao.RoomDAO.RoomDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteRoom", value = "/deleteRoom")
public class DeleteRoomController extends HttpServlet {
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
     * Phương thức xử lý yêu cầu POST để xóa thông tin phòng.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ phía client
     * @param resp Đối tượng HttpServletResponse được sử dụng để gửi phản hồi về cho client
     * @throws ServletException nếu có lỗi xảy ra liên quan đến servlet
     * @throws IOException nếu có lỗi nhập/xuất trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action"); // Lấy tham số action từ yêu cầu
        if ("delete".equals(action)) { // Kiểm tra nếu action là "delete"
            int roomId = Integer.parseInt(req.getParameter("id")); // Lấy ID phòng từ tham số yêu cầu
            boolean isDeleted = roomDAO.deleteRoom(roomId); // Gọi phương thức xóa phòng

            // Kiểm tra xem phòng đã được xóa thành công hay chưa
            if (isDeleted) {
                req.getSession().setAttribute("successMessage", "Room deleted successfully!"); // Thiết lập thông báo thành công
            } else {
                req.getSession().setAttribute("errorMessage", "Cannot delete room because it is referenced in another table (class)."); // Thiết lập thông báo lỗi
            }

            resp.sendRedirect(req.getContextPath() + "/Views/Admin/listRoom"); // Chuyển hướng về trang danh sách phòng
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action"); // Gửi lỗi nếu action không hợp lệ
        }
    }

    /**
     * Phương thức xử lý yêu cầu GET cho hành động này không được hỗ trợ.
     *
     * @param req  Đối tượng HttpServletRequest chứa thông tin yêu cầu từ phía client
     * @param resp Đối tượng HttpServletResponse được sử dụng để gửi phản hồi về cho client
     * @throws ServletException nếu có lỗi xảy ra liên quan đến servlet
     * @throws IOException nếu có lỗi nhập/xuất trong quá trình xử lý yêu cầu
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method not supported for this action"); // Gửi lỗi cho yêu cầu GET
    }
}
