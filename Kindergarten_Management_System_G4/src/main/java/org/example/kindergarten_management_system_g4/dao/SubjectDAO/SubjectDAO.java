package org.example.kindergarten_management_system_g4.dao.SubjectDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private Connection connection;

    public SubjectDAO()  {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Create or Insert a new Subject
    public boolean createSubject(Subject subject)  {
        String sql = "INSERT INTO Subject (subject_Code, subject_name, Description, User_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getSubjectCode());
            statement.setString(2, subject.getSubjectName());
            statement.setString(3, subject.getDescription());
            statement.setInt(4, subject.getUserId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Update an existing Subject
    public boolean updateSubject(Subject subject)  {
        String sql = "UPDATE Subject SET subject_Code = ?, subject_name = ?, Description = ?, User_id = ? WHERE Subject_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getSubjectCode());
            statement.setString(2, subject.getSubjectName());
            statement.setString(3, subject.getDescription());
            statement.setInt(4, subject.getUserId());
            statement.setInt(5, subject.getSubjectId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Delete a Subject
    public boolean deleteSubject(int subjectId)  {
        String sql = "DELETE FROM Subject WHERE Subject_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Retrieve a Subject by ID
    public Subject getSubjectById(int subjectId) {
        String sql = "SELECT * FROM Subject WHERE Subject_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Subject(
                        resultSet.getInt("Subject_ID"),
                        resultSet.getString("subject_Code"),
                        resultSet.getString("subject_name"),
                        resultSet.getString("Description"),
                        resultSet.getInt("User_id")
                );
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM Subject";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                subjects.add(new Subject(
                        resultSet.getInt("Subject_ID"),
                        resultSet.getString("subject_Code"),
                        resultSet.getString("subject_name"),
                        resultSet.getString("Description"),
                        resultSet.getInt("User_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return subjects;
    }
}
