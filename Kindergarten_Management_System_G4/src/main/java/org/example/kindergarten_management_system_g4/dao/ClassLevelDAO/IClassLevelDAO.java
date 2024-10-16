package org.example.kindergarten_management_system_g4.dao.ClassLevelDAO;


import org.example.kindergarten_management_system_g4.model.ClassLevel;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface cho các phương thức thao tác với ClassLevel.
 */
public interface IClassLevelDAO {

    /**
     * Lấy tất cả các class levels từ cơ sở dữ liệu.
     *
     * @return Danh sách các đối tượng ClassLevel.
     * @throws SQLException nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    List<ClassLevel> getAllClassLevels() throws SQLException;

    /**
     * Thêm một class level mới vào cơ sở dữ liệu.
     *
     * @param classLevel Đối tượng ClassLevel chứa thông tin của class level mới.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
     */
    void addClassLevel(ClassLevel classLevel) throws SQLException;

    /**
     * Cập nhật thông tin của một class level trong cơ sở dữ liệu.
     *
     * @param classLevel Đối tượng ClassLevel chứa thông tin cần cập nhật.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
     */
    void updateClassLevel(ClassLevel classLevel) throws SQLException;

    /**
     * Xóa một class level khỏi cơ sở dữ liệu.
     *
     * @param classLevelId ID của class level cần xóa.
     * @throws SQLException nếu có lỗi khi truy vấn cơ sở dữ liệu.
     */
    void deleteClassLevel(int classLevelId) throws SQLException;
}
