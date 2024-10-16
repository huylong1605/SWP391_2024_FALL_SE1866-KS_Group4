package org.example.kindergarten_management_system_g4.dao;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Student;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Phương thức kiểm tra sinh viên đã tồn tại chưa
    public boolean isStudentExist(String name, LocalDate dob, int userId) throws ClassNotFoundException {
        String CHECK_STUDENT_EXIST = "SELECT COUNT(*) FROM student WHERE Student_name = ? AND Date_of_birth = ? AND User_id = ?";

        // Sử dụng try-with-resources để quản lý kết nối cơ sở dữ liệu và câu lệnh
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

    // Phương thức để lấy tất cả sinh viên kèm thông tin người dùng
    public List<Student> getAllStudents() throws ClassNotFoundException {
        List<Student> students = new ArrayList<>();

        // Câu truy vấn SQL để chọn dữ liệu sinh viên kèm địa chỉ và số điện thoại người dùng
        String SELECT_ALL_STUDENTS_WITH_USER =
                "SELECT s.Student_ID, s.Date_of_birth, s.gender, s.Student_name, " +
                        "u.address, u.phoneNumber " +
                        "FROM student s " +
                        "JOIN user u ON s.User_id = u.User_id";

        // Sử dụng try-with-resources để quản lý kết nối cơ sở dữ liệu và câu lệnh
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS_WITH_USER)) {

            // Thực thi truy vấn và xử lý kết quả
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

    // Phương thức in chi tiết lỗi SQL
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

    // Phương thức thêm sinh viên mới vào cơ sở dữ liệu
    public void addStudent(Student student) throws ClassNotFoundException {
        String INSERT_STUDENT = "INSERT INTO student (Student_name, Date_of_birth, gender, User_id) VALUES (?,?,?,?)";

        // Sử dụng try-with-resources để quản lý kết nối cơ sở dữ liệu và câu lệnh
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
}






