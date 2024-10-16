package org.example.kindergarten_management_system_g4.dao.ClassLevelDAO.implement;

import org.example.kindergarten_management_system_g4.dao.ClassLevelDAO.IClassLevelDAO;
import org.example.kindergarten_management_system_g4.model.ClassLevel;
import org.example.kindergarten_management_system_g4.connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lớp triển khai các phương thức từ interface IClassLevelDAO để thao tác với cơ sở dữ liệu.
 */
public class ClassLevelDAOImpl extends DBConnection implements IClassLevelDAO {

    private static final String GET_ALL_CLASS_LEVELS_QUERY = "SELECT * FROM class_level";
    private static final String ADD_CLASS_LEVEL_QUERY = "INSERT INTO class_level(class_level_name, description, AgeRange) VALUES (?, ?, ?)";
    private static final String UPDATE_CLASS_LEVEL_QUERY = "UPDATE class_level SET class_level_name = ?, description = ?, AgeRange = ? WHERE class_level_id = ?";
    private static final String DELETE_CLASS_LEVEL_QUERY = "DELETE FROM class_level WHERE class_level_id = ?";
    private static final Logger LOGGER = Logger.getLogger(ClassLevelDAOImpl.class.getName());

    /**
     * Lấy danh sách tất cả các class levels từ cơ sở dữ liệu.
     *
     * @return Danh sách các đối tượng ClassLevel.
     * @throws SQLException nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    @Override
    public List<ClassLevel> getAllClassLevels() throws SQLException {
        List<ClassLevel> classLevelList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_ALL_CLASS_LEVELS_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ClassLevel classLevel = new ClassLevel();
                classLevel.setClassLevelId(resultSet.getInt("class_level_id"));
                classLevel.setClassLevelName(resultSet.getString("class_level_name"));
                classLevel.setDescription(resultSet.getString("description"));
                classLevel.setAgeRange(resultSet.getInt("AgeRange"));

                classLevelList.add(classLevel);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving class levels: " + e.getMessage(), e);
            throw e;
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classLevelList;
    }

    /**
     * Thêm một class level mới vào cơ sở dữ liệu.
     *
     * @param classLevel Đối tượng ClassLevel chứa thông tin của class level mới.
     * @throws SQLException nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    @Override
    public void addClassLevel(ClassLevel classLevel) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_CLASS_LEVEL_QUERY)) {

            preparedStatement.setString(1, classLevel.getClassLevelName());
            preparedStatement.setString(2, classLevel.getDescription());
            preparedStatement.setInt(3, classLevel.getAgeRange());

            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, "Class level added successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding class level: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Cập nhật thông tin của một class level trong cơ sở dữ liệu.
     *
     * @param classLevel Đối tượng ClassLevel chứa thông tin cần cập nhật.
     * @throws SQLException nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    @Override
    public void updateClassLevel(ClassLevel classLevel) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLASS_LEVEL_QUERY)) {

            preparedStatement.setString(1, classLevel.getClassLevelName());
            preparedStatement.setString(2, classLevel.getDescription());
            preparedStatement.setInt(3, classLevel.getAgeRange());
            preparedStatement.setInt(4, classLevel.getClassLevelId());

            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, "Class level updated successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating class level: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Xóa một class level khỏi cơ sở dữ liệu.
     *
     * @param classLevelId ID của class level cần xóa.
     * @throws SQLException nếu có lỗi xảy ra khi truy vấn cơ sở dữ liệu.
     */
    @Override
    public void deleteClassLevel(int classLevelId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLASS_LEVEL_QUERY)) {

            preparedStatement.setInt(1, classLevelId);
            preparedStatement.executeUpdate();
            LOGGER.log(Level.INFO, "Class level deleted successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting class level: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Phương thức để đóng tài nguyên (ResultSet, PreparedStatement, Connection)
     */
    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
            LOGGER.log(Level.INFO, "Resources closed successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing resources", e);
        }
    }
}



