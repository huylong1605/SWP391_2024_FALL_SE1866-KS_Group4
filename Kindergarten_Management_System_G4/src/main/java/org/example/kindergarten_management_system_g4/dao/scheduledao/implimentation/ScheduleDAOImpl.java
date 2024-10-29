package org.example.kindergarten_management_system_g4.dao.scheduledao.implimentation;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.dao.classDAO.impliment.ClassDAOImpl;
import org.example.kindergarten_management_system_g4.dao.scheduledao.IScheduleDAO;
import org.example.kindergarten_management_system_g4.model.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduleDAOImpl extends DBConnection implements IScheduleDAO {
    private static final Logger LOGGER = Logger.getLogger(ScheduleDAOImpl.class.getName());
    public static final String GET_SCHEDULE_FOR_STUDENT = "SELECT \n" +
            "    ss.Schedule_ID,\n" +
            "    s.Subject_name ,\n" +
            "    cl.Class_name,\n" +
            "    r.Room_number,\n" +
            "    t.Term_name ,\n" +
            "    sl.Slot_name ,\n" +
            "    sl.Start_time ,\n" +
            "    \n" +
            "    sl.end_time ,\n" +
            "    sch.date ,\n" +
            "    \n" +
            "    sch.day_of_week \n" +
            "FROM \n" +
            "    Student st\n" +
            "JOIN \n" +
            "    User u ON st.User_id = u.User_id\n" +
            "JOIN \n" +
            "    Schedule sch ON st.class_id = sch.class_id\n" +
            "JOIN \n" +
            "    subject_Schedule ss ON sch.Schedule_ID = ss.Schedule_ID\n" +
            "JOIN \n" +
            "    Subject s ON ss.Subject_ID = s.Subject_ID\n" +
            "JOIN \n" +
            "    Term t ON sch.Term_ID = t.Term_ID\n" +
            "JOIN \n" +
            "    Slot sl ON sch.SlotId = sl.slot_id\n" +
            "JOIN \n" +
            "     Class cl on  sch.class_id = cl.Class_ID \n" +
            "JOIN \n" +
            "     room r on  cl.Room_ID = r.Room_ID\n" +
            "WHERE \n" +
            "    u.User_id = ?\n" +
            "  ORDER BY \n" +
            "    sch.date ASC;";





    @Override
    public List<Schedule> scheduleOfStudent(int parentId) throws SQLException {
        List<Schedule> listSchedule = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_SCHEDULE_FOR_STUDENT);
            preparedStatement.setInt(1, parentId);
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleId(resultSet.getInt("Schedule_ID"));
                schedule.setSubject_name(resultSet.getString("Subject_name"));
                schedule.setTermName(resultSet.getString("Term_name"));
                schedule.setSlotName(resultSet.getString("Slot_name"));
                schedule.setStartTime(resultSet.getString("Start_time"));
                schedule.setEndTime(resultSet.getString("end_time"));
                schedule.setDayOfWeek(resultSet.getString("day_of_week"));
                schedule.setDateOfDay(resultSet.getString("date"));
                schedule.setRoom(resultSet.getString("Room_number"));
                schedule.setClassName(resultSet.getString("class_name"));

                // Thêm đối tượng vào danh sách
               listSchedule.add(schedule);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list schedule for student " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return listSchedule;
    }


    /**
     * Đóng các tài nguyên cơ sở dữ liệu (ResultSet, PreparedStatement, Connection)
     *
     * @param resultSet         Đối tượng ResultSet cần đóng
     * @param preparedStatement Đối tượng PreparedStatement cần đóng
     * @param connection        Đối tượng Connection cần đóng
     */
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


