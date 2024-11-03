package org.example.kindergarten_management_system_g4.dao.ActivityDAO.implement;

/*
 * Copyright(C) 2005, SWP_G4.
 * KMS:
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1               Đào Xuân Bình - HE163115          Implement Extracurricular Activities DAO
 */

import org.example.kindergarten_management_system_g4.dao.ActivityDAO.ExtracurricularActivitiesDAO;
import org.example.kindergarten_management_system_g4.model.ExtracurricularActivities;
import org.example.kindergarten_management_system_g4.connection.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ExtracurricularActivitiesDAOImpl cung cấp các phương thức để thao tác với cơ sở dữ liệu
 * cho các hoạt động ngoại khóa, bao gồm thêm mới, cập nhật, xóa, và lấy tất cả các hoạt động.
 * <p>Bugs: Không có lỗi nào được phát hiện.
 *
 * @author Đào Xuân Bình
 */
public class ExtracurricularActivitiesDAOImpl extends DBConnection implements ExtracurricularActivitiesDAO {

    private static final String GET_ALL_ACTIVITIES_QUERY = "SELECT * FROM extracurricular_activities";
    private static final String ADD_ACTIVITY_QUERY = "INSERT INTO extracurricular_activities(activity_name, description, date, start_time, end_time, location, user_id, materials_needed, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ACTIVITY_QUERY = "UPDATE extracurricular_activities SET activity_name = ?, description = ?, start_time = ?, end_time = ?, location = ?, user_id = ?, materials_needed = ?, status = ? WHERE activity_id = ?";
    private static final String DELETE_ACTIVITY_QUERY = "DELETE FROM extracurricular_activities WHERE activity_id = ?";
    private static final Logger LOGGER = Logger.getLogger(ExtracurricularActivitiesDAOImpl.class.getName());

    /**
     * Truy xuất tất cả các hoạt động ngoại khóa từ cơ sở dữ liệu.
     *
     * @return danh sách các đối tượng ExtracurricularActivities.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy xuất.
     */
    @Override
    public List<ExtracurricularActivities> getAllActivities() throws SQLException {
        List<ExtracurricularActivities> activities = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_ALL_ACTIVITIES_QUERY);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Tạo đối tượng hoạt động và thiết lập các giá trị từ kết quả truy vấn
                ExtracurricularActivities activity = new ExtracurricularActivities();
                activity.setActivity_id(rs.getInt("activity_id"));
                activity.setActivity_name(rs.getString("activity_name"));
                activity.setDescription(rs.getString("description"));
                activity.setDate(rs.getTimestamp("date").toLocalDateTime().toLocalDate());
                activity.setStart_time(rs.getTime("start_time").toLocalTime());
                activity.setEnd_time(rs.getTimestamp("end_time").toLocalDateTime().toLocalTime());
                activity.setLocation(rs.getString("location"));
                activity.setUser_id(rs.getInt("user_id"));
                activity.setMaterials_needed(rs.getString("materials_needed"));
                activity.setStatus(rs.getString("status"));
                activities.add(activity); // Thêm hoạt động vào danh sách
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving activities", e);
            throw e; // Ném ngoại lệ để xử lý bên ngoài
        }
        return activities;
    }

    /**
     * Thêm một hoạt động ngoại khóa vào cơ sở dữ liệu.
     *
     * @param activity đối tượng ExtracurricularActivities chứa thông tin hoạt động cần thêm.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình thêm.
     */
    @Override
    public void addActivity(ExtracurricularActivities activity) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(ADD_ACTIVITY_QUERY)) {

            ps.setString(1, activity.getActivity_name());
            ps.setString(2, activity.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(activity.getDate()));
            ps.setTime(4, Time.valueOf(activity.getStart_time()));
            ps.setTime(5, Time.valueOf(activity.getEnd_time()));
            ps.setString(6, activity.getLocation());
            ps.setInt(7, activity.getUser_id());
            ps.setString(8, activity.getMaterials_needed());
            ps.setString(9, "Planned");

            ps.executeUpdate(); // Thực hiện thêm hoạt động
            LOGGER.log(Level.INFO, "Activity added successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding activity", e);
            throw e;
        }
    }

    /**
     * Cập nhật thông tin của một hoạt động ngoại khóa trong cơ sở dữ liệu.
     *
     * @param activity đối tượng ExtracurricularActivities chứa thông tin hoạt động cần cập nhật.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình cập nhật.
     */
    @Override
    public void updateActivity(ExtracurricularActivities activity) throws SQLException {
        String sql = "UPDATE extracurricular_activities SET activity_name = ?, description = ?, start_time = ?, end_time = ?, location = ?, materials_needed = ?, status = ? WHERE activity_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, activity.getActivity_name());
            statement.setString(2, activity.getDescription());
            statement.setObject(3, activity.getStart_time()); // Sử dụng setObject cho LocalTime
            statement.setObject(4, activity.getEnd_time());   // Sử dụng setObject cho LocalTime
            statement.setString(5, activity.getLocation());
            statement.setString(6, activity.getMaterials_needed());
            statement.setString(7, activity.getStatus());
            statement.setInt(8, activity.getActivity_id());

            statement.executeUpdate(); // Thực hiện cập nhật hoạt động
        }
    }

    /**
     * Xóa một hoạt động ngoại khóa khỏi cơ sở dữ liệu.
     *
     * @param activityId ID của hoạt động cần xóa.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình xóa.
     */
    @Override
    public void deleteActivity(int activityId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_ACTIVITY_QUERY)) {

            ps.setInt(1, activityId);
            ps.executeUpdate(); // Thực hiện xóa hoạt động
            LOGGER.log(Level.INFO, "Activity deleted successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting activity", e);
            throw e;
        }
    }
}
