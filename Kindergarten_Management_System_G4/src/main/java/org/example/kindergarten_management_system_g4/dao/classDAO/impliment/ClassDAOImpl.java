package org.example.kindergarten_management_system_g4.dao.classDAO.impliment;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.dao.AuthenDAO.ForgetPasswordDAO;
import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.model.ClassDAL;

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
            "    c.User_id = 2;\n";
    @Override
    public List<ClassDAL> listClass() throws SQLException {
        List<ClassDAL> classList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
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
