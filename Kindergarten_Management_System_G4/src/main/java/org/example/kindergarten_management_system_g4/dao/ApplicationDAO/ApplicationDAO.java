package org.example.kindergarten_management_system_g4.dao.ApplicationDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Application;
import org.example.kindergarten_management_system_g4.model.Classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO extends DBConnection {

    /**
     * Creates a new application in the database.
     *
     * @param application The application object containing the details to be inserted
     * @return boolean indicating if the application was successfully created
     * @throws SQLException if a database error occurs during the insertion
     */
    public boolean createApplication(Application application) throws SQLException {
        String sql = "INSERT INTO Application (application_content, user_id, date_create, application_response, date_response, title) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, application.getApplicationContent().trim());
            stmt.setInt(2, application.getUserId());
            stmt.setDate(3, new Date(application.getDateCreate().getTime()));
            stmt.setString(4, application.getApplicationResponse());
            stmt.setDate(5, application.getDateResponse() != null ? new Date(application.getDateResponse().getTime()) : null);
            stmt.setString(6, application.getTitle().trim());

            return stmt.executeUpdate() > 0; // returns true if row is inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all applications from the database, ordered by creation date.
     *
     * @return List<Application> containing all applications in the system
     */    public List<Application> getAllApplications() {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM Application join User on Application.user_id = User.user_id order by Application.date_create Desc";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Application application = new Application();
                application.setApplicationContent(rs.getString("application_content"));
                application.setDateCreate(rs.getDate("date_create"));
                application.setStatus(rs.getString("status"));
                application.setApplicationResponse(rs.getString("application_response"));
                application.setDateResponse(rs.getTimestamp("date_response"));
                application.setTitle(rs.getString("title"));
                application.setParentName(rs.getString("fullname"));

                applications.add(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    /**
     * Retrieves applications by a specific user ID.
     *
     * @param userId The ID of the user whose applications are to be retrieved
     * @return List<Application> containing applications for the specified user
     */
    public List<Application> getApplicationsByUserId(int userId) {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM Application join User on Application.user_id = User.user_id WHERE Application.user_id = ? order by Application.date_create Desc";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Application application = new Application();
                    application.setApplicationContent(rs.getString("application_content"));
                    application.setDateCreate(rs.getDate("date_create"));
                    application.setStatus(rs.getString("status"));
                    application.setApplicationResponse(rs.getString("application_response"));

                    // Lấy dateResponse và định dạng lại trước khi thêm vào danh sách
                    Timestamp dateResponse = rs.getTimestamp("date_response");
                    if (dateResponse != null) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String formattedDateResponse = sdf.format(dateResponse);
                            application.setDateResponse(Timestamp.valueOf(formattedDateResponse));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    application.setTitle(rs.getString("title"));
                    application.setParentName(rs.getString("fullname"));

                    applications.add(application);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }


    /**
     * Retrieves applications for a specific class based on the class ID.
     *
     * @param classId The ID of the class whose applications are to be retrieved
     * @return List<Application> containing applications for the specified class
     */
    public List<Application> getApplicationsByClass(int classId) {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM application join User on Application.user_id = User.user_id WHERE Application.user_id IN (SELECT user_id FROM student WHERE class_id = ?) order by Application.date_create Desc";

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
                application.setDateResponse(rs.getTimestamp("date_response"));
                application.setTitle(rs.getString("title"));
                application.setParentName(rs.getString("fullname"));

                applications.add(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }


    /**
     * Retrieves an application by its unique ID.
     *
     * @param id The ID of the application to be retrieved
     * @return Application object representing the application with the specified ID
     */
    public Application getApplicationById(int id) {
        String sql = "SELECT * FROM application join User on Application.user_id = User.user_id WHERE application_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Application application = new Application();
                application.setApplicationId(rs.getInt("application_ID"));
                application.setApplicationContent(rs.getString("application_content"));
                application.setDateCreate(rs.getDate("date_create"));
                application.setStatus(rs.getString("status"));
                application.setApplicationResponse(rs.getString("application_response"));

                // Định dạng dateResponse để bỏ phần mili giây
                Timestamp dateResponse = rs.getTimestamp("date_response");
                if (dateResponse != null) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDateResponse = sdf.format(dateResponse);
                        application.setDateResponse(Timestamp.valueOf(formattedDateResponse));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                application.setTitle(rs.getString("title"));
                application.setParentName(rs.getString("fullname"));

                return application;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Updates an existing application in the database.
     *
     * @param application The application object containing updated details
     * @return boolean indicating if the update was successful
     */
    public boolean updateApplication(Application application) {
        String sql = "UPDATE application SET application_content = ?, status = ?, application_response = ?, date_response = ? WHERE application_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, application.getApplicationContent());
            stmt.setString(2, application.getStatus());
            stmt.setString(3, application.getApplicationResponse().trim());

            // Lấy thời gian hiện tại không có mili giây
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            currentTimestamp.setNanos(0); // Loại bỏ phần mili giây
            stmt.setTimestamp(4, currentTimestamp);

            stmt.setInt(5, application.getApplicationId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a list of classes assigned to a specific teacher.
     *
     * @param teacherId The ID of the teacher whose classes are to be retrieved
     * @return List<Classes> containing all the classes assigned to the specified teacher
     */
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
