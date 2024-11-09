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

    /**
     * Creates or inserts a new subject into the database.
     * @param subject The subject to be added to the database
     * @return true if the subject was successfully created, false otherwise
     * @throws SQLException if an error occurs during the database operation
     */    public boolean createSubject(Subject subject)  {
        String sql = "INSERT INTO Subject (subject_Code, subject_name, description, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getSubjectCode().trim());
            statement.setString(2, subject.getSubjectName().trim());
            statement.setString(3, subject.getDescription().trim());
            statement.setString(4, subject.getStatus());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    /**
     * Checks if the subject code already exists in the database.
     * @param subjectCode The subject code to check for duplication
     * @return true if the subject code is already used, false otherwise
     */    public boolean checkDuplicateSubjectCode(String subjectCode) {
        String query = "SELECT COUNT(*) FROM subject WHERE subject_Code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, subjectCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Checks if the subject name already exists in the database.
     * @param subjectName The subject name to check for duplication
     * @return true if the subject name is already used, false otherwise
     */
    public boolean checkDuplicateSubjectName(String subjectName) {
        String query = "SELECT COUNT(*) FROM subject WHERE subject_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, subjectName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




    /**
     * Updates an existing subject in the database.
     * @param subject The subject with updated values
     * @return true if the subject was successfully updated, false otherwise
     * @throws SQLException if an error occurs during the database operation
     */    public boolean updateSubject(Subject subject)  {
        String sql = "UPDATE Subject SET subject_Code = ?, subject_name = ?, Description = ?, status=? WHERE Subject_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getSubjectCode().trim());
            statement.setString(2, subject.getSubjectName().trim());
            statement.setString(3, subject.getDescription().trim());
            statement.setString(4, subject.getStatus());
            statement.setInt(5, subject.getSubjectId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Deletes a subject from the database by its ID.
     * @param subjectId The ID of the subject to delete
     * @return true if the subject was successfully deleted, false otherwise
     * @throws SQLException if an error occurs during the database operation
     */    public boolean deleteSubject(int subjectId)  {
        String sql = "DELETE FROM Subject WHERE Subject_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves a subject by its ID from the database.
     * @param subjectId The ID of the subject to retrieve
     * @return the subject with the given ID, or null if not found
     * @throws SQLException if an error occurs during the database operation
     */
    public Subject getSubjectById(int subjectId) {
        String sql = "SELECT * FROM Subject WHERE Subject_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Subject s = new Subject(
                        resultSet.getInt("Subject_ID"),
                        resultSet.getString("subject_Code"),
                        resultSet.getString("subject_name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Status")
                );
                return s;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a subject by its ID and code from the database, ensuring the code is unique.
     * @param subjectId The ID of the subject to exclude from the check
     * @param code The subject code to check
     * @return the subject with the given ID and code, or null if not found
     * @throws SQLException if an error occurs during the database operation
     */
    public Subject getSubjectByIdCode(int subjectId, String code) {
        String sql = "SELECT * FROM Subject WHERE subject_ID != ? and subject_Code = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);
            statement.setString(2, code);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Subject(
                        resultSet.getInt("Subject_ID"),
                        resultSet.getString("subject_Code"),
                        resultSet.getString("subject_name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Status")
                );

            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves all subjects from the database.
     * @return a list of all subjects
     * @throws SQLException if an error occurs during the database operation
     */
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM Subject ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Subject s = new Subject(
                        resultSet.getInt("Subject_ID"),
                        resultSet.getString("subject_Code"),
                        resultSet.getString("subject_name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Status")
                );
                subjects.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return subjects;
    }

    /**
     * Retrieves all subjects for the parent role from the database.
     * @return a list of subjects with selected fields for the parent role
     * @throws SQLException if an error occurs during the database operation
     */
    public List<Subject> parentGetAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM Subject where Status = 'active'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Subject s = new Subject();
                s.setSubjectCode(resultSet.getString("subject_Code"));
                s.setSubjectName(resultSet.getString("subject_name"));
                s.setDescription(resultSet.getString("Description"));
                subjects.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return subjects;
    }
}
