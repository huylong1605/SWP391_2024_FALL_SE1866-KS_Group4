/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/18/2024       1.1                    vu gia huy                        IRoomDAO
 */

/**
 * Interface IRoomDAO định nghĩa các phương thức để truy cập và quản lý dữ liệu phòng.
 * Nó cung cấp các phương thức để thêm, cập nhật, xóa, truy vấn thông tin phòng,
 * và các chức năng tìm kiếm và lọc dựa trên trạng thái.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * @author Vũ Gia Huy - HE163358
 */

package org.example.kindergarten_management_system_g4.dao.RoomDAO;

import org.example.kindergarten_management_system_g4.model.Room;

import java.util.List;

public interface IRoomDAO {
    /**
     * Lấy danh sách tất cả các phòng với phân trang.
     *
     * @param page số trang muốn lấy
     * @param size số lượng phòng mỗi trang
     * @return danh sách các phòng
     */
    List<Room> getAllRooms(int page, int size);

    /**
     * Thêm một phòng mới vào cơ sở dữ liệu.
     *
     * @param room đối tượng Room chứa thông tin phòng cần thêm
     * @return true nếu thêm thành công, false nếu thất bại
     */
    boolean addRoom(Room room);

    /**
     * Cập nhật thông tin phòng trong cơ sở dữ liệu.
     *
     * @param room đối tượng Room chứa thông tin phòng cần cập nhật
     * @return true nếu cập nhật thành công, false nếu thất bại
     */
    boolean updateRoom(Room room);

    /**
     * Xóa một phòng khỏi cơ sở dữ liệu theo ID.
     *
     * @param roomId ID của phòng cần xóa
     * @return true nếu xóa thành công, false nếu thất bại
     */
    boolean deleteRoom(int roomId);

    /**
     * Lấy thông tin của một phòng theo ID.
     *
     * @param roomId ID của phòng cần lấy thông tin
     * @return đối tượng Room chứa thông tin phòng, hoặc null nếu không tìm thấy
     */
    Room getRoomById(int roomId);

    /**
     * Đếm tổng số phòng trong cơ sở dữ liệu.
     *
     * @return tổng số phòng
     */
    int countRooms();

    /**
     * Tìm kiếm danh sách phòng theo tên phòng.
     *
     * @param roomNumber chuỗi tên phòng cần tìm
     * @return danh sách các phòng tìm thấy
     */
    List<Room> searchRoomsByName(String roomNumber);

    /**
     * Kiểm tra dữ liệu của phòng trước khi thực hiện thêm hoặc cập nhật.
     *
     * @param room đối tượng Room cần kiểm tra
     * @return true nếu dữ liệu hợp lệ, false nếu không hợp lệ
     */
    boolean validateRoom(Room room);

    /**
     * Tìm kiếm danh sách phòng theo tên với phân trang.
     *
     * @param name chuỗi tên phòng cần tìm
     * @param page số trang muốn lấy
     * @param size số lượng phòng mỗi trang
     * @return danh sách các phòng tìm thấy
     */
    List<Room> searchRoomsByName(String name, int page, int size);

    /**
     * Lọc danh sách phòng theo trạng thái với phân trang.
     *
     * @param status trạng thái của phòng (0 hoặc 1)
     * @param page số trang muốn lấy
     * @param size số lượng phòng mỗi trang
     * @return danh sách các phòng phù hợp
     */
    List<Room> filterRoomsByStatus(int status, int page, int size);

    /**
     * Đếm số phòng theo trạng thái.
     *
     * @param status trạng thái của phòng (0 hoặc 1)
     * @return số lượng phòng theo trạng thái
     */
    int countRoomsByStatus(int status);
}
