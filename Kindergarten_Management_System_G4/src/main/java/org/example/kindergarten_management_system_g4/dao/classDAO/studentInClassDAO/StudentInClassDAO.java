package org.example.kindergarten_management_system_g4.dao.classDAO.studentInClassDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentInClassDAO extends DBConnection implements IStudentInClassDAO {
    public static final String GET_LIST_STUDENT_BY_CLASS_ID = "SELECT * FROM student WHERE class_id = ?";
    public static final String GET_ALL_STUDENT = "SELECT * FROM student";
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
            "JOIN \n" +
            "    class c ON s.class_id = c.class_id  \n" +
            "WHERE \n" +
            "    s.Student_ID = ?;\n";
    private static final Logger LOGGER = Logger.getLogger(StudentInClassDAO.class.getName());

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                LOGGER.log(Level.SEVERE, "SQL Exception: {0}", e.getMessage());
                LOGGER.log(Level.SEVERE, "SQLState: {0}", ((SQLException) e).getSQLState());
                LOGGER.log(Level.SEVERE, "Error Code: {0}", ((SQLException) e).getErrorCode());
                Throwable t = ex.getCause();
                while (t != null) {
                    LOGGER.log(Level.SEVERE, "Cause: {0}", t);
                    t = t.getCause();
                }
            }
        }
    }

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


    public List<Student> getStudentsByClassId(int classId) throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_LIST_STUDENT_BY_CLASS_ID);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("Student_ID"));
                student.setName(resultSet.getString("Student_name"));
                student.setDob(resultSet.getDate("Date_of_birth").toLocalDate());
                student.setGender(resultSet.getBoolean("gender"));
                student.setClassId(resultSet.getInt("class_id"));
                student.setUserId(resultSet.getInt("User_id"));
                students.add(student);
            }
        } catch (SQLException e) {
            // Ném lại SQLException để xử lý bên ngoài phương thức
            throw new SQLException("Lỗi khi lấy danh sách học sinh theo classId: " + classId, e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return students;
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_STUDENT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("Student_ID"));
                student.setName(resultSet.getString("Student_name"));
                student.setDob(resultSet.getDate("Date_of_birth").toLocalDate());
                student.setGender(resultSet.getBoolean("gender"));
                student.setClassId(resultSet.getInt("class_id"));
                student.setUserId(resultSet.getInt("User_id"));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi lấy danh sách học sinh", e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return students;
    }

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
            isAdded = rowsAffected > 0; // Nếu có ít nhất một dòng bị ảnh hưởng, học sinh đã được thêm vào lớp.
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi thêm học sinh vào lớp: " + classId, e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }
        return isAdded;
    }

    public boolean removeStudentFromClass(int studentId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isRemoved = false;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(REMOVE_STUDENT_FROM_CLASS);
            preparedStatement.setInt(1, studentId);
            int rowsAffected = preparedStatement.executeUpdate();
            isRemoved = rowsAffected > 0; // Nếu có ít nhất một dòng bị ảnh hưởng, học sinh đã được xóa khỏi lớp.
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi xóa học sinh khỏi lớp: " + studentId, e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }
        return isRemoved;
    }


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

    public static void main(String[] args) {
        // Khởi tạo DAO
        StudentInClassDAO studentInClassDAO = new StudentInClassDAO();

        try {
            // Lấy tất cả danh sách học sinh
            List<Student> students = studentInClassDAO.getAllStudents();

            // Kiểm tra và in danh sách học sinh
            if (!students.isEmpty()) {
                for (Student student : students) {
                    System.out.println("Student ID: " + student.getStudentId());
                    System.out.println("Name: " + student.getName());
                    System.out.println("Date of Birth: " + student.getDob());
                    System.out.println("Gender: " + (student.getGender() ? "Male" : "Female"));
                    System.out.println("Class ID: " + student.getClassId());
                    System.out.println("User ID: " + student.getUserId());
                    System.out.println("-------------------------");
                }
            } else {
                System.out.println("No students found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
    }



}
