/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/18/2024       1.1                    vu gia huy                        RoomDAO
 */

/**
 * Lớp RoomDAO thực hiện các thao tác truy cập dữ liệu cho bảng room.
 * Nó cung cấp các phương thức để thêm, cập nhật, xóa và truy vấn thông tin phòng.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * @author Vũ Gia Huy - HE163358
 */

package org.example.kindergarten_management_system_g4.dao.RoomDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomDAO implements IRoomDAO {
    private Connection connection;
    private static final Logger logger = Logger.getLogger(RoomDAO.class.getName());

    public RoomDAO() {
        try {
            this.connection = DBConnection.getConnection(); // Thiết lập kết nối đến cơ sở dữ liệu
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error establishing database connection", e); // Ghi log nếu kết nối thất bại
        }
    }

    @Override
    public List<Room> getAllRooms(int page, int size) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room LIMIT ? OFFSET ?"; // Câu truy vấn lấy danh sách phòng với phân trang

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, size); // Kích thước trang
            statement.setInt(2, (page - 1) * size); // Tính toán vị trí bắt đầu
            ResultSet resultSet = statement.executeQuery(); // Thực thi câu truy vấn
            while (resultSet.next()) {
                Room s = new Room(
                        resultSet.getInt("Room_ID"),
                        resultSet.getString("Room_number"),
                        resultSet.getInt("Status"),
                        resultSet.getInt("capacity")
                );
                rooms.add(s); // Thêm phòng vào danh sách
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching all rooms", e); // Ghi log nếu xảy ra lỗi
        }
        return rooms; // Trả về danh sách phòng
    }

    public boolean addRoom(Room room) {
        // Gọi validateRoom để kiểm tra dữ liệu hợp lệ
        if (!validateRoom(room)) {
            return false; // Trả về false nếu không hợp lệ
        }

        // Loại bỏ khoảng trắng lần nữa trước khi lưu vào database
        String trimmedRoomNumber = room.getRoomNumber().trim().replaceAll("\\s+", " "); // Xóa khoảng trắng thừa

        String sql = "INSERT INTO room (Room_number, Status, Capacity) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Gán giá trị đã loại bỏ khoảng trắng vào PreparedStatement
            statement.setString(1, trimmedRoomNumber);
            statement.setInt(2, room.getStatus());
            statement.setInt(3, room.getCapacity());

            // Thực thi câu truy vấn
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu có dòng được chèn vào
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding room", e); // Ghi log nếu xảy ra lỗi
            return false; // Trả về false nếu có lỗi
        }
    }

    public boolean updateRoom(Room room) {
        if (!validateRoom(room)) {
            return false; // Trả về false nếu không hợp lệ
        }
        String sql = "UPDATE room SET Room_number = ?, Status = ?, capacity = ? WHERE Room_ID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, room.getRoomNumber());
            statement.setInt(2, room.getStatus());
            statement.setInt(3, room.getCapacity());
            statement.setInt(4, room.getRoomId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có dòng được cập nhật
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating room", e); // Ghi log nếu xảy ra lỗi
            return false; // Trả về false nếu có lỗi
        }
    }

    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM room WHERE Room_ID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roomId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0; // Trả về true nếu có dòng bị xóa
        } catch (SQLException e) {
            // Kiểm tra nếu lỗi là do khóa ngoại
            if (e.getErrorCode() == 1451) {
                // Xử lý khi không thể xóa do ràng buộc khóa ngoại
                logger.log(Level.WARNING, "Cannot delete room due to foreign key constraint", e);
                return false; // Trả về false để chỉ ra rằng không thể xóa
            } else {
                logger.log(Level.SEVERE, "Error deleting room", e); // Ghi log nếu xảy ra lỗi
                return false;
            }
        }
    }

    public Room getRoomById(int roomId) {
        String sql = "SELECT * FROM room WHERE Room_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Room(
                        resultSet.getInt("Room_ID"),
                        resultSet.getString("Room_number"),
                        resultSet.getInt("Status"),
                        resultSet.getInt("capacity")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching room by ID", e); // Ghi log nếu xảy ra lỗi
        }
        return null; // Trả về null nếu không tìm thấy phòng
    }

    public int countRooms() {
        String sql = "SELECT COUNT(*) FROM room";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1); // Trả về số lượng phòng
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error counting rooms", e); // Ghi log nếu xảy ra lỗi
        }
        return 0; // Trả về 0 nếu có lỗi
    }

    @Override
    public List<Room> searchRoomsByName(String roomNumber) {
        List<Room> rooms = new ArrayList<>();
        if (roomNumber != null) {
            roomNumber = roomNumber.trim();
        }

        // Kiểm tra chuỗi tìm kiếm không được rỗng hoặc chỉ chứa khoảng trắng
        if (roomNumber == null || roomNumber.isEmpty()) {
            logger.warning("Room number cannot be empty or just spaces.");
            return rooms; // Trả về danh sách rỗng nếu chuỗi không hợp lệ
        }
        String query = "SELECT * FROM room WHERE Room_number LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + roomNumber + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("Room_ID"));
                room.setRoomNumber(rs.getString("Room_number"));
                room.setCapacity(rs.getInt("capacity"));
                room.setStatus(rs.getInt("status"));
                rooms.add(room); // Thêm phòng vào danh sách
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error searching rooms by name", e); // Ghi log nếu xảy ra lỗi
        }

        return rooms; // Trả về danh sách phòng tìm thấy
    }

    @Override
    public boolean validateRoom(Room room) {
        // Kiểm tra trường RoomNumber không được null hoặc chỉ chứa khoảng trắng
        if (room.getRoomNumber() == null || room.getRoomNumber().trim().isEmpty()) {
            logger.warning("Room number cannot be empty or just spaces.");
            return false; // Trả về false nếu không hợp lệ
        }
        // Loại bỏ khoảng trắng ở đầu và cuối của RoomNumber
        room.setRoomNumber(room.getRoomNumber().trim());
        // Giới hạn độ dài của RoomNumber (giả sử max là 50 ký tự)
        if (room.getRoomNumber().length() > 5) {
            logger.warning("Room number exceeds the maximum length of 50 characters.");
            return false; // Trả về false nếu vượt quá chiều dài
        }
        // Kiểm tra capacity (giả sử sức chứa không âm và không quá lớn)
        if (room.getCapacity() <= 0 || room.getCapacity() > 30) {
            logger.warning("Capacity must be between 1 and 30.");
            return false; // Trả về false nếu không hợp lệ
        }
        // Kiểm tra status (chỉ nhận 0 hoặc 1)
        if (room.getStatus() != 0 && room.getStatus() != 1) {
            logger.warning("Status must be either 0 (inactive) or 1 (active).");
            return false; // Trả về false nếu không hợp lệ
        }
        return true; // Nếu tất cả các kiểm tra đều hợp lệ
    }

    @Override
    public List<Room> searchRoomsByName(String name, int page, int size) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room WHERE Room_number LIKE ? LIMIT ? OFFSET ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + name + "%");
            statement.setInt(2, size);
            statement.setInt(3, (page - 1) * size);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Room room = new Room(
                        resultSet.getInt("Room_ID"),
                        resultSet.getString("Room_number"),
                        resultSet.getInt("Status"),
                        resultSet.getInt("Capacity")
                );
                rooms.add(room); // Thêm phòng vào danh sách
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching rooms by name", e); // Ghi log nếu xảy ra lỗi
        }
        return rooms; // Trả về danh sách phòng tìm thấy
    }

    @Override
    public List<Room> filterRoomsByStatus(int status, int page, int size) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM room WHERE Status = ? LIMIT ? OFFSET ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, status); // Trạng thái của phòng
            statement.setInt(2, size);   // Số lượng phòng mỗi trang
            statement.setInt(3, (page - 1) * size); // Tính toán phân trang
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Room room = new Room(
                        resultSet.getInt("Room_ID"),
                        resultSet.getString("Room_number"),
                        resultSet.getInt("Status"),
                        resultSet.getInt("Capacity")
                );
                rooms.add(room); // Thêm phòng vào danh sách
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error filtering rooms by status", e); // Ghi log nếu xảy ra lỗi
        }
        return rooms; // Trả về danh sách phòng tìm thấy
    }

    @Override
    public int countRoomsByStatus(int status) {
        String sql = "SELECT COUNT(*) FROM room WHERE Status = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, status); // Gán trạng thái vào câu truy vấn
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1); // Trả về số lượng phòng theo trạng thái
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error counting rooms by status", e); // Ghi log nếu xảy ra lỗi
        }
        return 0; // Trả về 0 nếu có lỗi
    }
}
