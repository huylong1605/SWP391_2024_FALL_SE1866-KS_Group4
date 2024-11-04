package org.example.kindergarten_management_system_g4.dao.ApplicationDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Application;
import org.example.kindergarten_management_system_g4.model.Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO extends DBConnection {

    public boolean createApplication(Application application) throws SQLException {
        String sql = "INSERT INTO Application (application_content, user_id, date_create, application_response, date_response) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, application.getApplicationContent().trim());
            stmt.setInt(2, application.getUserId());
            stmt.setDate(3, new Date(application.getDateCreate().getTime()));
            stmt.setString(4, application.getApplicationResponse());
            stmt.setDate(5, application.getDateResponse() != null ? new Date(application.getDateResponse().getTime()) : null);

            return stmt.executeUpdate() > 0; // returns true if row is inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức để lấy danh sách đơn ứng tuyển
    public List<Application> getAllApplications() {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT application_content, date_create, status, application_response, date_response FROM Application";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Application application = new Application();
                application.setApplicationContent(rs.getString("application_content"));
                application.setDateCreate(rs.getDate("date_create"));
                application.setStatus(rs.getString("status"));
                application.setApplicationResponse(rs.getString("application_response"));
                application.setDateResponse(rs.getDate("date_response"));

                applications.add(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    public List<Application> getApplicationsByUserId(int userId) {
        List<Application> applications = new ArrayList<>();
        // Update the SQL query to filter by user_id
        String sql = "SELECT application_content, date_create, status, application_response, date_response " +
                "FROM Application WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the userId parameter in the PreparedStatement
            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Application application = new Application();
                    application.setApplicationContent(rs.getString("application_content"));
                    application.setDateCreate(rs.getDate("date_create"));
                    application.setStatus(rs.getString("status")); // Ensure 'status' column exists
                    application.setApplicationResponse(rs.getString("application_response"));
                    application.setDateResponse(rs.getDate("date_response"));

                    applications.add(application);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    public List<Application> getApplicationsByClass(int classId) {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM application WHERE user_id IN (SELECT user_id FROM student WHERE class_id = ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Application application = new Application();
                application.setApplicationId(rs.getInt("application_ID"));
                application.setApplicationContent(rs.getString("application_content"));
                application.setDateCreate(rs.getDate("date_create"));
                application.setStatus(rs.getString("status")); // Ensure 'status' column exists
                application.setApplicationResponse(rs.getString("application_response"));
                application.setDateResponse(rs.getDate("date_response"));

                applications.add(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    public Application getApplicationById(int id) {
        String sql = "SELECT * FROM application WHERE application_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Application application = new Application();
                application.setApplicationId(rs.getInt("application_ID"));
                application.setApplicationContent(rs.getString("application_content"));
                application.setDateCreate(rs.getDate("date_create"));
                application.setStatus(rs.getString("status")); // Ensure 'status' column exists
                application.setApplicationResponse(rs.getString("application_response"));
                application.setDateResponse(rs.getDate("date_response"));

                return application;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateApplication(Application application) {
        String sql = "UPDATE application SET application_content = ?, status = ?, application_response = ?, date_response = ? WHERE application_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, application.getApplicationContent());
            stmt.setString(2, application.getStatus());
            stmt.setString(3, application.getApplicationResponse().trim());
            stmt.setDate(4, new Date(System.currentTimeMillis()));
            stmt.setInt(5, application.getApplicationId());

            stmt.executeUpdate();
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Classes> getClassesByTeacherId(int teacherId) {
        List<Classes> classes = new ArrayList<>();
        String sql = "SELECT C.* FROM user U JOIN Class C ON U.user_id = C.user_id WHERE U.role_id = 2 AND U.user_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Classes clazz = new Classes();
                clazz.setClassId(rs.getInt("class_id")); // Adjust this based on your Class table structure
                clazz.setClassName(rs.getString("class_name")); // Adjust based on your Class table columns
                // Set other properties of Class if needed
                classes.add(clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

}
