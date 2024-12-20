

/*
 * Copyright(C) 2005,  <SWP_G4>.
 * <KMS> :
 *  <Kindergarten Management System>
 *
 * Record of change:
 * DATE                       Version             AUTHOR                       DESCRIPTION
 * <10/15/2024>                 <1.1>           <Vu Viet Chuc>            <Update searchStudentsByName method>
 */



package org.example.kindergarten_management_system_g4.dao.classDAO.studentInClassDAO;
import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Lớp StudentInClassDAO quản lý các thao tác cơ sở dữ liệu liên quan đến học sinh trong lớp học.
 * Lớp này bao gồm các phương thức để thêm, xóa, lấy thông tin học sinh, và các truy vấn liên quan
 * đến việc lọc hoặc phân trang danh sách học sinh theo lớp.
 */

public class StudentInClassDAO extends DBConnection implements IStudentInClassDAO {
    // Truy vấn SQL cho các hoạt động khác nhau liên quan đến sinh viên và lớp học
    public static final String GET_LIST_STUDENT_BY_CLASS_ID = "SELECT s.*, c.Class_name FROM student s \n" +
            "    JOIN class c ON s.class_id = c.Class_ID\n" +
            "    WHERE s.class_id = ?";
    public static final String GET_ALL_STUDENT = "SELECT s.*, c.Class_name FROM student s LEFT JOIN class c ON s.class_id = c.class_id";
    public static final String ADD_STUDENT_TO_CLASS = "UPDATE student SET class_id = ? WHERE Student_ID = ?";
    public static final String REMOVE_STUDENT_FROM_CLASS = "UPDATE student SET class_id = NULL WHERE Student_ID = ?";
    public static final String GET_STUDENT_BY_ID = "SELECT \n" +
            "    s.Student_ID, \n" +
            "    s.Student_name, \n" +
            "    s.Date_of_birth, \n" +
            "    s.gender, \n" +
            "    s.class_id, \n" +
            "    s.User_id, \n" +
            "    c.Class_name, \n" +
            "    u.Fullname AS parent_name, \n" +
            "    u.email AS parent_email, \n" +
            "    u.address AS parent_address, \n" +
            "    u.phoneNumber AS parent_phone \n" +
            "FROM \n" +
            "    student s \n" +
            "JOIN \n" +
            "    user u ON s.User_id = u.User_id \n" +
            "LEFT JOIN \n" +
            "    class c ON s.class_id = c.class_id  \n" +
            "WHERE \n" +
            "    s.Student_ID = ?;\n";

    public static final String SEARCH_STUDENT_BY_NAME = "SELECT s.*, c.Class_name " +
            "FROM student s " +
            "LEFT JOIN class c ON s.class_id = c.class_id " +
            "WHERE s.Student_name LIKE ?";

    public static final String GET_CLASS_NAME_BY_ID = "SELECT Class_name FROM class WHERE Class_ID = ?";

    // Logger để ghi lại thông điệp và lỗi
    private static final Logger LOGGER = Logger.getLogger(StudentInClassDAO.class.getName());


    // Phương thức in chi tiết SQLException
    /**
     * Phương thức này ghi lại thông tin chi tiết của một SQLException, bao gồm thông báo lỗi,
     * mã trạng thái SQL và mã lỗi, cùng với các nguyên nhân dẫn đến lỗi.
     *
     * @param ex Ngoại lệ SQLException cần ghi lại.
     */

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                // Ghi lại thông báo lỗi, mã SQLState, và mã lỗi của SQL
                LOGGER.log(Level.SEVERE, "SQL Exception: {0}", e.getMessage());
                LOGGER.log(Level.SEVERE, "SQLState: {0}", ((SQLException) e).getSQLState());
                LOGGER.log(Level.SEVERE, "Error Code: {0}", ((SQLException) e).getErrorCode());
                // Ghi lại các nguyên nhân dẫn đến lỗi nếu có
                Throwable t = ex.getCause();
                while (t != null) {
                    LOGGER.log(Level.SEVERE, "Cause: {0}", t);
                    t = t.getCause();
                }
            }
        }
    }


    // Phương thức đóng các tài nguyên ResultSet, PreparedStatement, Connection
    /**
     * Đóng các tài nguyên bao gồm ResultSet, PreparedStatement và Connection.
     * Phương thức này giúp giải phóng tài nguyên để tránh tình trạng rò rỉ bộ nhớ.
     *
     * @param resultSet Đối tượng ResultSet cần đóng.
     * @param preparedStatement Đối tượng PreparedStatement cần đóng.
     * @param connection Đối tượng Connection cần đóng.
     */


    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        // Đóng ResultSet
        try {
            if (resultSet != null) {
                resultSet.close();
                LOGGER.log(Level.INFO, "ResultSet closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing ResultSet", e);
            printSQLException(e);
        }
        // Đóng PreparedStatement
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
                LOGGER.log(Level.INFO, "PreparedStatement closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing PreparedStatement", e);
            printSQLException(e);
        }
        // Đóng Connection
        try {
            if (connection != null) {
                connection.close();
                LOGGER.log(Level.INFO, "Connection closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing Connection", e);
            printSQLException(e);
        }
    }


    // Phương thức lấy danh sách học sinh theo mã lớp
    /**
     * Lấy danh sách học sinh thuộc một lớp học cụ thể thông qua mã lớp (classId).
     *
     * @param classId Mã lớp học cần lấy danh sách học sinh.
     * @return Danh sách học sinh thuộc lớp học đã chỉ định.
     * @throws SQLException Nếu có lỗi xảy ra khi thực hiện truy vấn.
     */


    public List<Student> getStudentsByClassId(int classId) throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Mở kết nối và chuẩn bị truy vấn
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_LIST_STUDENT_BY_CLASS_ID);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();
            // Duyệt qua kết quả và tạo đối tượng Student cho từng bản ghi
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("Student_ID"));
                student.setName(resultSet.getString("Student_name"));
                student.setDob(resultSet.getDate("Date_of_birth").toLocalDate());
                student.setGender(resultSet.getBoolean("gender"));
                student.setClassId(resultSet.getInt("class_id"));
                student.setClassName(resultSet.getString("Class_name"));
                student.setUserId(resultSet.getInt("User_id"));
                students.add(student);
            }
        } catch (SQLException e) {
            // Ném lại SQLException để xử lý bên ngoài phương thức
            throw new SQLException("Lỗi khi lấy danh sách học sinh theo classId: " + classId, e);
        } finally {
            // Đóng tài nguyên
            closeResources(resultSet, preparedStatement, connection);
        }

        return students;
    }


    // Phương thức lấy tất cả học sinh với phân trang
    /**
     * Lấy danh sách tất cả học sinh từ cơ sở dữ liệu, hỗ trợ phân trang.
     *
     * @param pageNumber Số thứ tự trang.
     * @param pageSize Số lượng bản ghi trên mỗi trang.
     * @return Danh sách học sinh trong trang yêu cầu.
     * @throws SQLException Nếu có lỗi xảy ra khi thực hiện truy vấn.
     */

    public List<Student> getAllStudents(int pageNumber, int pageSize) throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // Tính toán offset
        int offset = (pageNumber - 1) * pageSize;

        // Cập nhật câu truy vấn SQL với LIMIT và OFFSET
        String paginatedQuery = GET_ALL_STUDENT + " LIMIT ? OFFSET ?";

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(paginatedQuery);
            preparedStatement.setInt(1, pageSize); // Kích thước trang
            preparedStatement.setInt(2, offset); // Offset

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("Student_ID"));
                student.setName(resultSet.getString("Student_name"));
                student.setDob(resultSet.getDate("Date_of_birth").toLocalDate());
                student.setGender(resultSet.getBoolean("gender"));
                student.setClassId(resultSet.getInt("class_id"));
                student.setClassName(resultSet.getString("Class_name"));
                student.setUserId(resultSet.getInt("User_id"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy danh sách tất cả học sinh", e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return students;
    }


    // Phương thức lấy tổng số lượng học sinh
    /**
     * Lấy tổng số lượng học sinh trong cơ sở dữ liệu, có thể lọc theo các tiêu chí khác nhau.
     *
     * @param filterType Loại bộ lọc (có lớp, không có lớp, hoặc tất cả).
     * @return Tổng số học sinh thỏa mãn điều kiện lọc.
     * @throws SQLException Nếu có lỗi xảy ra khi thực hiện truy vấn.
     */


    public int getTotalStudentsCount(String filterType) throws SQLException {
        int count = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query;

        // Xác định truy vấn dựa trên loại bộ lọc
        if ("withClass".equals(filterType)) {
            query = "SELECT COUNT(*) AS total FROM student WHERE class_id IS NOT NULL";
        } else if ("noClass".equals(filterType)) {
            query = "SELECT COUNT(*) AS total FROM student WHERE class_id IS NULL";
        } else if ("".equals(filterType)) {
            query = "SELECT COUNT(*) AS total FROM student"; // Lấy tổng số học sinh
        } else {
            throw new SQLException("Lỗi: Bộ lọc không hợp lệ.");
        }

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy tổng số học sinh", e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return count;
    }


    // Phương thức thêm học sinh vào lớp
    /**
     * Thêm học sinh vào một lớp cụ thể thông qua mã lớp và mã học sinh.
     *
     * @param studentId Mã học sinh cần thêm vào lớp.
     * @param classId Mã lớp mà học sinh sẽ được thêm vào.
     * @return true nếu thêm thành công, false nếu thất bại.
     * @throws SQLException Nếu có lỗi xảy ra khi thực hiện truy vấn.
     */


    public boolean addStudentToClass(int studentId, int classId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isAdded = false;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(ADD_STUDENT_TO_CLASS);
            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(2, studentId);
            int rowsAffected = preparedStatement.executeUpdate();
            isAdded = rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi thêm học sinh vào lớp: " + classId, e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }
        return isAdded;
    }


    // Phương thức xóa học sinh khỏi lớp
    /**
     * Xóa học sinh khỏi lớp học (thiết lập class_id là NULL).
     *
     * @param studentId Mã học sinh cần xóa khỏi lớp.
     * @return true nếu xóa thành công, false nếu thất bại.
     * @throws SQLException Nếu có lỗi xảy ra khi thực hiện truy vấn.
     */


    public boolean removeStudentFromClass(int studentId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isRemoved = false;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(REMOVE_STUDENT_FROM_CLASS);
            preparedStatement.setInt(1, studentId);
            int rowsAffected = preparedStatement.executeUpdate();
            isRemoved = rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi xóa học sinh khỏi lớp: " + studentId, e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }
        return isRemoved;
    }


    // Phương thức lấy thông tin học sinh theo mã học sinh
    /**
     * Lấy thông tin chi tiết của một học sinh thông qua mã học sinh.
     *
     * @param studentId Mã học sinh cần lấy thông tin.
     * @return Đối tượng Student chứa thông tin chi tiết của học sinh.
     * @throws SQLException Nếu có lỗi xảy ra khi thực hiện truy vấn.
     */


    public Student getStudentById(int studentId) throws SQLException {
        Student student = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_STUDENT_BY_ID);
            preparedStatement.setInt(1, studentId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                student = new Student();
                student.setStudentId(resultSet.getInt("Student_ID"));
                student.setName(resultSet.getString("Student_name"));
                student.setDob(resultSet.getDate("Date_of_birth").toLocalDate());
                student.setGender(resultSet.getBoolean("gender"));
                student.setClassId(resultSet.getInt("class_id"));
                student.setClassName(resultSet.getString("Class_name"));
                student.setUserId(resultSet.getInt("User_id"));
                student.setParentName(resultSet.getString("parent_name"));
                student.setParentEmail(resultSet.getString("parent_email"));
                student.setParentAddress(resultSet.getString("parent_address"));
                student.setParentPhone(resultSet.getString("parent_phone"));
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy thông tin chi tiết học sinh: " + studentId, e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return student;
    }


    // Phương thức lọc học sinh theo lớp và phân trang
    /**
     * Lọc danh sách học sinh dựa trên việc học sinh có thuộc lớp hay không, kèm theo phân trang.
     *
     * @param hasClassId true nếu muốn lấy danh sách học sinh có lớp, false nếu không có lớp.
     * @param pageNumber Số thứ tự trang.
     * @param pageSize Số lượng bản ghi trên mỗi trang.
     * @return Danh sách học sinh thỏa mãn điều kiện lọc.
     * @throws SQLException Nếu có lỗi xảy ra khi thực hiện truy vấn.
     */


    public List<Student> filterStudentsByClassId(Boolean hasClassId, int pageNumber, int pageSize) throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // Khởi tạo truy vấn cơ bản
        String query = "SELECT s.*, c.Class_name FROM student s LEFT JOIN class c ON s.class_id = c.class_id WHERE ";

        // Thêm điều kiện vào truy vấn dựa trên giá trị hasClassId
        if (hasClassId) {
            query += "s.class_id IS NOT NULL";  // Nếu hasClassId là true, chọn học sinh có classId
        } else {
            query += "s.class_id IS NULL";      // Nếu hasClassId là false, chọn học sinh không có classId
        }

        // Thêm phân trang vào truy vấn
        query += " LIMIT ? OFFSET ?"; // Sử dụng LIMIT và OFFSET để phân trang

        try {
            // Mở kết nối và chuẩn bị truy vấn
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);

            // Thiết lập các tham số phân trang
            preparedStatement.setInt(1, pageSize); // Kích thước trang
            preparedStatement.setInt(2, (pageNumber - 1) * pageSize); // Vị trí bắt đầu

            // Thực thi truy vấn và nhận kết quả
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua kết quả và tạo đối tượng Student cho từng bản ghi
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("Student_ID"));
                student.setName(resultSet.getString("Student_name"));
                student.setDob(resultSet.getDate("Date_of_birth").toLocalDate());
                student.setGender(resultSet.getBoolean("gender"));
                student.setClassId(resultSet.getInt("class_id"));
                student.setClassName(resultSet.getString("Class_name"));
                student.setUserId(resultSet.getInt("User_id"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lọc danh sách học sinh", e);
        } finally {
            // Đóng tài nguyên
            closeResources(resultSet, preparedStatement, connection);
        }

        return students;
    }

    /**
     * Lấy danh sách học sinh có classId với phân trang.
     *
     * @param pageNumber Số trang muốn lấy.
     * @param pageSize   Số lượng học sinh trong mỗi trang.
     * @return Danh sách học sinh có classId.
     * @throws SQLException Nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */

    public List<Student> getStudentsWithClass(int pageNumber, int pageSize) throws SQLException {
        return filterStudentsByClassId(true, pageNumber, pageSize);  // Lấy học sinh có classId
    }


    /**
     * Lấy danh sách học sinh không có classId với phân trang.
     *
     * @param pageNumber Số trang muốn lấy.
     * @param pageSize   Số lượng học sinh trong mỗi trang.
     * @return Danh sách học sinh không có classId.
     * @throws SQLException Nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */

    public List<Student> getStudentsWithoutClass(int pageNumber, int pageSize) throws SQLException {
        return filterStudentsByClassId(false, pageNumber, pageSize); // Lấy học sinh không có classId
    }

    /**
     * Tìm kiếm học sinh theo tên.
     *
     * @param name Tên của học sinh cần tìm kiếm.
     * @return Danh sách học sinh phù hợp với tên tìm kiếm.
     * @throws SQLException Nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */

    public List<Student> searchStudentsByName(String name) throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SEARCH_STUDENT_BY_NAME);
            preparedStatement.setString(1, "%" + name + "%"); // Sử dụng % để tìm kiếm bất kỳ vị trí nào
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("Student_ID"));
                student.setName(resultSet.getString("Student_name"));
                student.setDob(resultSet.getDate("Date_of_birth").toLocalDate());
                student.setGender(resultSet.getBoolean("gender"));
                student.setClassId(resultSet.getInt("class_id"));
                student.setClassName(resultSet.getString("Class_name"));
                student.setUserId(resultSet.getInt("User_id"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi tìm kiếm học sinh theo tên: " + name, e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return students;
    }

    /**
     * Lấy tên lớp theo classId.
     *
     * @param classId ID của lớp cần lấy tên.
     * @return Tên của lớp tương ứng với classId.
     * @throws SQLException Nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */

    public String getClassNameById(int classId) throws SQLException {
        String className = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_CLASS_NAME_BY_ID);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                className = resultSet.getString("Class_name");
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy tên lớp theo classId: " + classId, e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return className;
    }

}