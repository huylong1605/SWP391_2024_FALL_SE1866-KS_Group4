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


    /**
     * Lấy danh sách học sinh có classId với phân trang.
     *
     * @param pageNumber Số trang muốn lấy.
     * @param pageSize   Số lượng học sinh trong mỗi trang.
     * @return Danh sách học sinh có classId.
     * @throws SQLException Nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    List<Student> getStudentsWithClass(int pageNumber, int pageSize) throws SQLException;

    /**
     * Lấy danh sách học sinh không có classId với phân trang.
     *
     * @param pageNumber Số trang muốn lấy.
     * @param pageSize   Số lượng học sinh trong mỗi trang.
     * @return Danh sách học sinh không có classId.
     * @throws SQLException Nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    List<Student> getStudentsWithoutClass(int pageNumber, int pageSize) throws SQLException;

    /**
     * Tìm kiếm học sinh theo tên.
     *
     * @param name Tên của học sinh cần tìm kiếm.
     * @return Danh sách học sinh phù hợp với tên tìm kiếm.
     * @throws SQLException Nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    List<Student> searchStudentsByName(String name) throws SQLException;

    /**
     * Lấy tổng số học sinh, có thể áp dụng bộ lọc.
     *
     * @param filterType Kiểu lọc để xác định học sinh cần đếm (có hoặc không có classId).
     * @return Tổng số học sinh phù hợp với bộ lọc.
     * @throws SQLException Nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    int getTotalStudentsCount(String filterType) throws SQLException;


    /**
     * Lấy tên lớp theo classId.
     *
     * @param classId ID của lớp cần lấy tên.
     * @return Tên của lớp tương ứng với classId.
     * @throws SQLException Nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    String getClassNameById(int classId) throws SQLException;
}






