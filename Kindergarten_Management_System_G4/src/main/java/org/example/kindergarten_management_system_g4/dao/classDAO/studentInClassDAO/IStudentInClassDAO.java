/*
 * Copyright(C) 2005,  <SWP_G4>.
 * <KMS> :
 *  <Kindergarten Management System>
 *
 * Record of change:
 * DATE                       Version             AUTHOR                       DESCRIPTION
 * <10/15/2024>                 <1.1>           <Vu Viet Chuc>            <Add getStudentById method>
 */


package org.example.kindergarten_management_system_g4.dao.classDAO.studentInClassDAO;

import org.example.kindergarten_management_system_g4.model.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface IStudentInClassDAO định nghĩa các phương thức để tương tác với
 * dữ liệu học sinh trong ngữ cảnh quản lý lớp học. Nó cung cấp các chức năng
 * để lấy thông tin, thêm và xóa học sinh khỏi các lớp học.
 */
public interface IStudentInClassDAO {
    /**
     * Lấy danh sách học sinh dựa trên ID của lớp học đã cho.
     *
     * @param classId ID của lớp học
     * @return danh sách các học sinh trong lớp học được chỉ định
     * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu
     */
    List<Student> getStudentsByClassId(int classId) throws SQLException;

    /**
     * Lấy danh sách tất cả học sinh, không phân biệt lớp học.
     *
     * @return danh sách tất cả học sinh
     * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu
     */
    List<Student> getAllStudents(int pageNumber, int pageSize) throws SQLException;

    /**
     * Thêm học sinh vào một lớp học cụ thể.
     *
     * @param studentId ID của học sinh cần thêm
     * @param classId ID của lớp học cần thêm học sinh vào
     * @return true nếu thêm học sinh vào lớp thành công, false nếu không thành công
     * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu
     */
    boolean addStudentToClass(int studentId, int classId) throws SQLException;

    /**
     * Xóa học sinh khỏi lớp hiện tại của họ.
     *
     * @param studentId ID của học sinh cần xóa
     * @return true nếu xóa học sinh khỏi lớp thành công, false nếu không thành công
     * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu
     */
    boolean removeStudentFromClass(int studentId) throws SQLException;

    /**
     * Lấy thông tin chi tiết về học sinh dựa trên ID của họ.
     *
     * @param studentId ID của học sinh
     * @return một đối tượng Student chứa thông tin của học sinh, hoặc null nếu không tìm thấy
     * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu
     */
    Student getStudentById(int studentId) throws SQLException;

    List<Student> getStudentsWithClass(int pageNumber, int pageSize) throws SQLException;

    List<Student> getStudentsWithoutClass(int pageNumber, int pageSize) throws SQLException;

    List<Student> searchStudentsByName(String name) throws SQLException;

    int getTotalStudentsCount(String filterType) throws SQLException;

    String getClassNameById(int classId) throws SQLException;
}






