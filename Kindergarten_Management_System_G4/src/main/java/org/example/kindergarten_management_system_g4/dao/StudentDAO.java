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
    public List<Student> getAllStudents() throws ClassNotFoundException {
        List<Student> students = new ArrayList<>();

        // Sử dụng JOIN để kết hợp bảng student và user
        String SELECT_ALL_STUDENTS_WITH_USER =
                "SELECT s.Student_ID, s.Date_of_birth, s.gender, s.Student_name, " +
                        "u.address, u.phoneNumber " +
                        "FROM student s " +
                        "JOIN user u ON s.User_id = u.User_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS_WITH_USER)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Lấy thông tin từ bảng student
                int studentId = resultSet.getInt("Student_ID");
                LocalDate dob = resultSet.getDate("Date_of_birth").toLocalDate();
                boolean gender = resultSet.getBoolean("gender");
                String studentName = resultSet.getString("Student_name");

                // Lấy thông tin từ bảng user
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phoneNumber");


                // Tạo đối tượng Student với các trường cần thiết
                Student student = new Student(studentId, dob, gender, studentName, address, phoneNumber);
                students.add(student);
            }


        } catch (SQLException e) {
            printSQLException(e);
        }

        System.out.println(students);
        return students;
    }




    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    public void addStudent(Student student) throws ClassNotFoundException {
        String INSERT_STUDENT = "INSERT INTO student (Student_name, Date_of_birth, gender,User_id) VALUES (?, ?,?,?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(student.getDob()));
            preparedStatement.setBoolean(3, student.isGender());
            preparedStatement.setInt(4, student.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

}
