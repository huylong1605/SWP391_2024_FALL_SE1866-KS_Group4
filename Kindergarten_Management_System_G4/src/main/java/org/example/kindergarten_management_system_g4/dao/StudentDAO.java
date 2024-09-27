package org.example.kindergarten_management_system_g4.dao;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM student";

    public List<Student> getAllStudents() throws ClassNotFoundException {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("Student_ID");
                LocalDate dob = resultSet.getDate("Date_of_birth").toLocalDate();
                boolean gender = resultSet.getBoolean("gender");
                String name = resultSet.getString("Student_name");
                int classId = resultSet.getInt("class_id");
                int userId = resultSet.getInt("User_id");

                Student student = new Student(studentId, dob, gender, name, classId, userId);
                students.add(student);
            }
            System.out.println("Executing query: " + preparedStatement);
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
        String INSERT_STUDENT = "INSERT INTO student (Student_name, Date_of_birth, gender, class_id, User_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(student.getDob()));
            preparedStatement.setBoolean(3, student.isGender());
            preparedStatement.setInt(4, student.getClassId());
            preparedStatement.setInt(5, student.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

}
