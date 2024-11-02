/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/5/2024       1.1              Nguyễn Huy Long - He160140              Create interface IClassDAO
 */
package org.example.kindergarten_management_system_g4.dao.classDAO;

import org.example.kindergarten_management_system_g4.model.*;

import java.sql.SQLException;
import java.util.List;

/**
 * IClassDAO định nghĩa các phương thức cần thiết để tương tác với dữ liệu liên quan đến lớp học
 * trong Hệ thống Quản lý Mầm non.
 * @author Nguyễn Huy Long
 */
public interface IClassDAO {

    /**
     * Lấy danh sách tất cả các lớp học.
     *
     * @return danh sách lớp học
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    List<ClassDAL> getListClass() throws SQLException;

    /**
     * Lấy danh sách lớp học theo bộ lọc cấp độ lớp và từ khóa tìm kiếm.
     *
     * @param classLevelId ID của cấp độ lớp
     * @param search từ khóa tìm kiếm
     * @return danh sách lớp học thỏa mãn điều kiện
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    List<ClassDAL> getListClassSearchFilter(int classLevelId, String search) throws SQLException;

    /**
     * Lấy danh sách tất cả các phòng học.
     *
     * @return danh sách phòng học
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    List<Room> getListRoom() throws SQLException;

    /**
     * Lấy danh sách phòng học theo ID phòng.
     *
     * @param roomId ID của phòng học
     * @return danh sách phòng học
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    List<Room> getListRoom(int roomId) throws SQLException;

    /**
     * Lấy danh sách tất cả giáo viên.
     *
     * @return danh sách giáo viên
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    List<User> getListTeacher() throws SQLException;

    /**
     * Lấy danh sách giáo viên theo ID giáo viên.
     *
     * @param userId ID của giáo viên
     * @return danh sách giáo viên
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    List<User> getListTeacher(int userId) throws SQLException;

    /**
     * Lấy danh sách tất cả các cấp độ lớp học.
     *
     * @return danh sách cấp độ lớp
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    List<ClassLevel> getListClassLevel() throws SQLException;

    /**
     * Lấy danh sách lớp học theo bộ lọc cấp độ lớp.
     *
     * @param classLevelId ID của cấp độ lớp
     * @return danh sách lớp học thỏa mãn điều kiện
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    List<ClassDAL> getListClassFilter(int classLevelId) throws SQLException;

    /**
     * Lấy danh sách lớp học theo từ khóa tìm kiếm.
     *
     * @param search từ khóa tìm kiếm
     * @return danh sách lớp học thỏa mãn điều kiện
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    List<ClassDAL> getListClassSearch(String search) throws SQLException;

    /**
     * Lấy thông tin lớp học dựa trên ID lớp.
     *
     * @param classId ID của lớp học
     * @return đối tượng Classes tương ứng với ID lớp
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    Classes getClassById(int classId) throws SQLException;

    /**
     * Lấy thông tin chi tiết lớp học dựa trên ID lớp.
     *
     * @param classId ID của lớp học
     * @return đối tượng ClassDAL chứa thông tin chi tiết lớp
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    ClassDAL getClassByIdDetail(int classId) throws SQLException;

    /**
     * Tạo một lớp học mới.
     *
     * @param classes đối tượng Classes chứa thông tin lớp học
     * @return true nếu tạo thành công, false nếu không
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    Boolean createClass(Classes classes) throws SQLException;

    /**
     * Kiểm tra xem tên lớp đã tồn tại hay chưa.
     *
     * @param className tên lớp cần kiểm tra
     * @return tên lớp nếu tồn tại, null nếu không
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    String getClassName(String className) throws SQLException;

    /**
     * Kiểm tra xem tên lớp đã tồn tại hay chưa khi cập nhật.
     *
     * @param className tên lớp cần kiểm tra
     * @param classId ID của lớp học
     * @return tên lớp nếu tồn tại, null nếu không
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    String getClassNameUpdate(String className, int classId) throws SQLException;

    /**
     * Cập nhật thông tin lớp học.
     *
     * @param classes đối tượng Classes chứa thông tin lớp học cần cập nhật
     * @return true nếu cập nhật thành công, false nếu không
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    Boolean updateClass(Classes classes) throws SQLException;

    /**
     * Xóa lớp học dựa trên ID lớp.
     *
     * @param classId ID của lớp học
     * @return true nếu xóa thành công, false nếu không
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu
     */
    Boolean deleteClass(int classId) throws SQLException;


}
