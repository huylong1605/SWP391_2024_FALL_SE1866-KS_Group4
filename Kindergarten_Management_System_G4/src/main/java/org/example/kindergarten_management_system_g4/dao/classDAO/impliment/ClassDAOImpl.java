package org.example.kindergarten_management_system_g4.dao.classDAO.impliment;

import org.example.kindergarten_management_system_g4.connection.DBConnection;

import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassDAOImpl extends DBConnection implements IClassDAO {
    private static final Logger LOGGER = Logger.getLogger(ClassDAOImpl.class.getName());
    public static final String GET_LIST_ROOM = "SELECT r.* \n" +
            "FROM room r\n" +
            "LEFT JOIN class c \n" +
            "ON r.Room_ID = c.Room_ID\n" +
            "WHERE c.Room_ID IS NULL;";

    public static final String GET_LIST_TEACHER = "SELECT u.* \n" +
            "FROM user u\n" +
            "LEFT JOIN class c \n" +
            "ON u.User_id = c.User_id\n" +
            "WHERE c.User_id IS NULL;";

    public static final String GET_LIST_CLASS_LEVEL = "SELECT * from class_level";
    public static final String GET_CLASS_NAME = "SELECT Class_name from class where Class_name = ?";

    public static final String GET_LIST_CLASS = "SELECT \n" +
            "    c.Class_ID, \n" +
            "    c.Class_name, \n" +
            "    cl.Class_level_name, \n" +
            "    u.Fullname, \n" +
            "    r.Room_number\n" +
            "FROM \n" +
            "    class c\n" +
            "JOIN \n" +
            "    class_level cl ON c.Class_level_ID = cl.Class_level_ID\n" +
            "JOIN \n" +
            "    user u ON c.User_id = u.User_id\n" +
            "JOIN \n" +
            "    room r ON c.Room_ID = r.Room_ID\n" +
            "WHERE \n" +
            "    u.Role_id = 2;\n";

    public static final String GET_LIST_CLASS_FILTER = "SELECT \n" +
            "    c.Class_ID, \n" +
            "    c.Class_name, \n" +
            "    cl.Class_level_name, \n" +
            "    u.Fullname, \n" +
            "    r.Room_number\n" +
            "FROM \n" +
            "    class c\n" +
            "JOIN \n" +
            "    class_level cl ON c.Class_level_ID = cl.Class_level_ID\n" +
            "JOIN \n" +
            "    user u ON c.User_id = u.User_id\n" +
            "JOIN \n" +
            "    room r ON c.Room_ID = r.Room_ID\n" +
            "WHERE \n" +
            "    cl.Class_level_ID = ?;\n";

    public static final String GET_LIST_CLASS_SEARCH_FILTER = "SELECT \n" +
            "    c.Class_ID, \n" +
            "    c.Class_name, \n" +
            "    cl.Class_level_name, \n" +
            "    u.Fullname, \n" +
            "    r.Room_number\n" +
            "FROM \n" +
            "    class c\n" +
            "JOIN \n" +
            "    class_level cl ON c.Class_level_ID = cl.Class_level_ID\n" +
            "JOIN \n" +
            "    user u ON c.User_id = u.User_id\n" +
            "JOIN \n" +
            "    room r ON c.Room_ID = r.Room_ID\n" +
            "WHERE \n" +
            "   cl.Class_level_ID = ? AND u.Fullname like ?;\n";

    public static final String GET_LIST_CLASS_SEARCH = "SELECT \n" +
            "    c.Class_ID, \n" +
            "    c.Class_name, \n" +
            "    cl.Class_level_name, \n" +
            "    u.Fullname, \n" +
            "    r.Room_number\n" +
            "FROM \n" +
            "    class c\n" +
            "JOIN \n" +
            "    class_level cl ON c.Class_level_ID = cl.Class_level_ID\n" +
            "JOIN \n" +
            "    user u ON c.User_id = u.User_id\n" +
            "JOIN \n" +
            "    room r ON c.Room_ID = r.Room_ID\n" +
            "WHERE \n" +
            "  u.Fullname like ?";

    private static final String INSERT_CLASS = "INSERT INTO class(Class_name, Class_level_ID, User_id, Room_ID) " +
                                                "VALUES (?, ?, ?, ?)";
    @Override
    public List<ClassDAL> listClass() throws SQLException {
        List<ClassDAL> classList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_CLASS);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                ClassDAL classDAL = new ClassDAL();
                classDAL.setClassId(resultSet.getInt("Class_ID"));
                classDAL.setClassName(resultSet.getString("Class_name"));
                classDAL.setClassLevelName(resultSet.getString("Class_level_name"));
                classDAL.setFullname(resultSet.getString("Fullname"));
                classDAL.setRoomNumber(resultSet.getString("Room_number"));

                // Thêm đối tượng vào danh sách
                classList.add(classDAL);

            }

        }catch (SQLException e) {
            throw new SQLException("Error list class " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
   return classList;
    }

    @Override
    public List<ClassDAL> listClassSearchFilter(int classLevelId, String search) throws SQLException {
        List<ClassDAL> classSearchFilterList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_CLASS_SEARCH_FILTER);
            preparedStatement.setInt(1, classLevelId);
            preparedStatement.setString(2, "%" + search + "%");
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                ClassDAL classDAL = new ClassDAL();
                classDAL.setClassId(resultSet.getInt("Class_ID"));
                classDAL.setClassName(resultSet.getString("Class_name"));
                classDAL.setClassLevelName(resultSet.getString("Class_level_name"));
                classDAL.setFullname(resultSet.getString("Fullname"));
                classDAL.setRoomNumber(resultSet.getString("Room_number"));

                // Thêm đối tượng vào danh sách
                classSearchFilterList.add(classDAL);

            }

        }catch (SQLException e) {
            throw new SQLException("Error filter list class " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classSearchFilterList;
    }

    @Override
    public List<Room> listRoom() throws SQLException {
        List<Room> roomList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_ROOM);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                Room room = new Room();
                room.setRoomId(resultSet.getInt("Room_ID"));
                room.setRoomNumber(resultSet.getString("Room_number"));
                room.setStatus(resultSet.getInt("Status"));
                room.setCapacity(resultSet.getInt("capacity"));


                // Thêm đối tượng vào danh sách
                roomList.add(room);

            }

        }catch (SQLException e) {
            throw new SQLException("Error list room " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return roomList;
    }

    @Override
    public List<User> listTeacher() throws SQLException {
        List<User> teacherList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_TEACHER);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
               User teacher = new User();
                teacher.setUserID(resultSet.getInt("User_id"));
                teacher.setFullname(resultSet.getString("Fullname"));
                teacher.setEmail(resultSet.getString("Email"));
                teacher.setGender(resultSet.getInt("gender"));
                teacher.setPhoneNumber(resultSet.getString("phoneNumber"));


                // Thêm đối tượng vào danh sách
                teacherList.add(teacher);

            }

        }catch (SQLException e) {
            throw new SQLException("Error list teacher " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return teacherList;
    }

    @Override
    public List<ClassLevel> listClassLevel() throws SQLException {
        List<ClassLevel> classLevelList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_CLASS_LEVEL);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                ClassLevel classLevel = new ClassLevel();
                classLevel.setClassLevelId(resultSet.getInt("Class_level_ID"));
                classLevel.setClassLevelName(resultSet.getString("Class_level_name"));
                classLevel.setDescription(resultSet.getString("Description"));



                // Thêm đối tượng vào danh sách
                classLevelList.add(classLevel);

            }

        }catch (SQLException e) {
            throw new SQLException("Error list class level " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classLevelList;
    }

    @Override
    public List<ClassDAL> listClassFilter(int classLevelId) throws SQLException {
        List<ClassDAL> classFilterList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_CLASS_FILTER);
            preparedStatement.setInt(1, classLevelId);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                ClassDAL classDAL = new ClassDAL();
                classDAL.setClassId(resultSet.getInt("Class_ID"));
                classDAL.setClassName(resultSet.getString("Class_name"));
                classDAL.setClassLevelName(resultSet.getString("Class_level_name"));
                classDAL.setFullname(resultSet.getString("Fullname"));
                classDAL.setRoomNumber(resultSet.getString("Room_number"));

                // Thêm đối tượng vào danh sách
                classFilterList.add(classDAL);

            }

        }catch (SQLException e) {
            throw new SQLException("Error filter list class " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classFilterList;
    }

    @Override
    public List<ClassDAL> listClassSearch(String search) throws SQLException {
        List<ClassDAL> classSearchList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_CLASS_SEARCH);
            preparedStatement.setString(1, "%" + search + "%");
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                ClassDAL classDAL = new ClassDAL();
                classDAL.setClassId(resultSet.getInt("Class_ID"));
                classDAL.setClassName(resultSet.getString("Class_name"));
                classDAL.setClassLevelName(resultSet.getString("Class_level_name"));
                classDAL.setFullname(resultSet.getString("Fullname"));
                classDAL.setRoomNumber(resultSet.getString("Room_number"));

                // Thêm đối tượng vào danh sách
                classSearchList.add(classDAL);

            }

        }catch (SQLException e) {
            throw new SQLException("Error search list class " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classSearchList;
    }

    @Override
    public Boolean createClass(Classes classes) throws SQLException {
        boolean isInserted = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(INSERT_CLASS);

            preparedStatement.setString(1, classes.getClassName());
            preparedStatement.setInt(2, classes.getClassLevelId());
            preparedStatement.setInt(3, classes.getUserId());
            preparedStatement.setInt(4, classes.getRoomId());


            LOGGER.log(Level.INFO, "Executing insert class: {0}", preparedStatement);

            int result = preparedStatement.executeUpdate(); // Thực thi câu lệnh

            if (result > 0) {
                // Nếu số dòng bị ảnh hưởng > 0, thêm thành công
                isInserted = true;
                LOGGER.log(Level.INFO, "Class inserted successfully: {0}", classes.getClassName());
            }
        } catch (SQLException e) {
            throw new SQLException("Error insert class " + e.getMessage(), e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }

        return isInserted; // Trả về kết quả kiểm tra
    }

    @Override
    public String getClassName(String className) throws SQLException {

        String ClassNamee = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");

            preparedStatement = connection.prepareStatement(GET_CLASS_NAME);
            preparedStatement.setString(1, className);
            LOGGER.log(Level.INFO, "Executing query: {0}", preparedStatement);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ClassNamee = resultSet.getString(1);  // Lưu Password ở đây
                LOGGER.log(Level.INFO, "Get class name: {0}", className);  // Ghi log khi lấy được Password
            } else {
                LOGGER.log(Level.WARNING, "No password found for email: {0}", className); // Ghi log không tìm thấy Password
            }

        } catch (SQLException e) {
            throw new SQLException("Error get class name " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return ClassNamee;
    }

    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
            // Đóng ResultSet
            try {
                if (resultSet != null) {
                    resultSet.close();
                    LOGGER.log(Level.INFO, "ResultSet closed");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing ResultSet", e);
                printSQLException(e);
            }

            // Đóng PreparedStatement
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    LOGGER.log(Level.INFO, "PreparedStatement closed");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing PreparedStatement", e);
                printSQLException(e);
            }

            // Đóng Connection
            try {
                if (connection != null) {
                    connection.close();
                    LOGGER.log(Level.INFO, "Connection closed");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing Connection", e);
                printSQLException(e);
            }
        }

        /**
         * Ghi lại thông tin lỗi SQL
         *
         * @param ex Đối tượng SQLException
         */
        private void printSQLException(SQLException ex) {
            for (Throwable e : ex) {
                if (e instanceof SQLException) {
                    LOGGER.log(Level.SEVERE, "SQL Exception: {0}", e.getMessage());
                    LOGGER.log(Level.SEVERE, "SQLState: {0}", ((SQLException) e).getSQLState());
                    LOGGER.log(Level.SEVERE, "Error Code: {0}", ((SQLException) e).getErrorCode());
                    Throwable t = ex.getCause();
                    while (t != null) {
                        LOGGER.log(Level.SEVERE, "Cause: {0}", t);
                        t = t.getCause();
                    }
                }
            }
        }

}
