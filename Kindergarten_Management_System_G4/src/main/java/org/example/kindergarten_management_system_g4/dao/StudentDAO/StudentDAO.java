/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/5/2024       1.1              Đào Xuân Bình - HE163115              Create StudentDAO class
 */

package org.example.kindergarten_management_system_g4.dao.StudentDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp StudentDAO chịu trách nhiệm tương tác với cơ sở dữ liệu để thực hiện các hoạt động liên quan đến bảng student.
 * Lớp này bao gồm các phương thức kiểm tra sinh viên tồn tại, thêm sinh viên mới, và lấy danh sách sinh viên từ cơ sở dữ liệu.
 * <p>Lỗi: Chưa phát hiện lỗi.
 */
public class StudentDAO {

    /**
     * Phương thức kiểm tra sinh viên đã tồn tại chưa trong cơ sở dữ liệu.
     *
     * @param name   tên sinh viên.
     * @param dob    ngày sinh của sinh viên.
     * @param userId ID người dùng (phụ huynh).
     * @return true nếu sinh viên đã tồn tại, false nếu chưa.
     * @throws ClassNotFoundException nếu không tìm thấy driver kết nối cơ sở dữ liệu.
     */
    public boolean isStudentExist(String name, LocalDate dob, int userId) throws ClassNotFoundException {
        String CHECK_STUDENT_EXIST = "SELECT COUNT(*) FROM student WHERE Student_name = ? AND Date_of_birth = ? AND User_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_STUDENT_EXIST)) {

            // Thiết lập các tham số cho truy vấn
            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, java.sql.Date.valueOf(dob));
            preparedStatement.setInt(3, userId);

            // Thực thi truy vấn và kiểm tra nếu có bản ghi khớp
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Trả về true nếu sinh viên đã tồn tại
                }
            }
        } catch (SQLException e) {
            // In chi tiết lỗi SQL nếu xảy ra lỗi
            printSQLException(e);
        }
        return false; // Trả về false nếu sinh viên chưa tồn tại
    }

    /**
     * Phương thức để lấy tất cả sinh viên kèm thông tin người dùng từ cơ sở dữ liệu.
     *
     * @return danh sách các đối tượng Student.
     * @throws ClassNotFoundException nếu không tìm thấy driver kết nối cơ sở dữ liệu.
     */
    public List<Student> getAllStudents() throws ClassNotFoundException {
        List<Student> students = new ArrayList<>();

        String SELECT_ALL_STUDENTS_WITH_USER =
                "SELECT s.Student_ID, s.Date_of_birth, s.gender, s.Student_name, " +
                        "u.address, u.phoneNumber " +
                        "FROM student s " +
                        "JOIN user u ON s.User_id = u.User_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS_WITH_USER)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Lấy dữ liệu từ kết quả
                int studentId = resultSet.getInt("Student_ID");
                LocalDate dob = resultSet.getDate("Date_of_birth").toLocalDate();
                boolean gender = resultSet.getBoolean("gender");
                String studentName = resultSet.getString("Student_name");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phoneNumber");

                // Tạo đối tượng Student và thêm vào danh sách
                Student student = new Student(studentId, dob, gender, studentName, address, phoneNumber);
                students.add(student);
            }

        } catch (SQLException e) {
            // In chi tiết lỗi SQL nếu xảy ra lỗi
            printSQLException(e);
        }

        System.out.println(students); // In danh sách sinh viên (để debug)
        return students; // Trả về danh sách sinh viên
    }

    /**
     * Phương thức in chi tiết lỗi SQL.
     *
     * @param ex đối tượng SQLException chứa thông tin lỗi.
     */
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Mã lỗi: " + ((SQLException) e).getErrorCode());
                System.err.println("Thông báo: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Nguyên nhân: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    /**
     * Phương thức thêm sinh viên mới vào cơ sở dữ liệu.
     *
     * @param student đối tượng Student chứa thông tin sinh viên mới.
     * @throws ClassNotFoundException nếu không tìm thấy driver kết nối cơ sở dữ liệu.
     */
    public void addStudent(Student student) throws ClassNotFoundException {
        String INSERT_STUDENT = "INSERT INTO student (Student_name, Date_of_birth, gender, User_id) VALUES (?,?,?,?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)) {

            // Thiết lập các tham số cho câu lệnh insert
            preparedStatement.setString(1, student.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(student.getDob()));
            preparedStatement.setBoolean(3, student.isGender());
            preparedStatement.setInt(4, student.getUserId());

            // Thực thi câu lệnh cập nhật (thêm mới)
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // In chi tiết lỗi SQL nếu xảy ra lỗi
            printSQLException(e);
        }
    }
    public boolean updateStudent(Student student) throws ClassNotFoundException {
        String UPDATE_STUDENT_SQL =
                "UPDATE student SET Student_name = ?, Date_of_birth = ?, gender = ? WHERE student_Id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT_SQL)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(student.getDob()));
            preparedStatement.setBoolean(3, student.isGender());
            preparedStatement.setInt(4, student.getStudentId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public List<Student> getStudentsByUserId(int userId) throws ClassNotFoundException {
        List<Student> students = new ArrayList<>();

        String SELECT_STUDENTS_BY_USER_ID =
                "SELECT s.Student_ID, s.Date_of_birth, s.gender, s.Student_name, " +
                        "u.address, u.phoneNumber " +
                        "FROM student s " +
                        "JOIN user u ON s.User_id = u.User_id " +
                        "WHERE u.User_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENTS_BY_USER_ID)) {

            // Set the userId parameter in the query
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve data from the result set
                int studentId = resultSet.getInt("Student_ID");
                LocalDate dob = resultSet.getDate("Date_of_birth").toLocalDate();
                boolean gender = resultSet.getBoolean("gender");
                String studentName = resultSet.getString("Student_name");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phoneNumber");

                // Create a Student object and add it to the list
                Student student = new Student(studentId, dob, gender, studentName, address, phoneNumber);
                students.add(student);
            }

        } catch (SQLException e) {
            // Print detailed SQL error if an error occurs
            printSQLException(e);
        }

        System.out.println(students); // Print the list of students for debugging
        return students; // Return the list of students
    }

}
