package org.example.kindergarten_management_system_g4.dao.AttendanceDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttendanceDAO extends DBConnection implements IAttendanceDAO {
    private static final Logger LOGGER = Logger.getLogger(AttendanceDAO.class.getName());
    private static final String GET_STUDENT_ATTENDANCE =
            "SELECT s.student_id, s.student_name, s.date_of_birth, a.attend_status " +
                    "FROM Student s " +
                    "INNER JOIN Class c ON s.class_id = c.class_id " +
                    "INNER JOIN Schedule sch ON c.class_id = sch.class_id " +
                    "LEFT JOIN Attendance a ON s.student_id = a.student_id AND a.date = ? AND a.slotId = ? " +
                    "WHERE c.class_id = ? AND sch.date = ? AND sch.slotId = ?;";

    private static final String SAVE_STUDENT_ATTENDANCE = "INSERT INTO Attendance (student_id, attend_status, date, slotId)\n" +
            "VALUES (?, ?, ?, ?) AS new_values\n" +
            "ON DUPLICATE KEY UPDATE attend_status = new_values.attend_status";

    private static final String GET_ATTENDANCE_SUMMARY ="SELECT\n" +
            "    s.student_ID,\n" +
            "    s.student_name,\n" +
            "    COUNT(a.attendence_id) AS total_attendance,\n" +
            "    SUM(CASE WHEN a.attend_status = 1 THEN 1 ELSE 0 END) AS total_present,\n" +
            "    SUM(CASE WHEN a.attend_status = 0 THEN 1 ELSE 0 END) AS total_absent\n" +
            "FROM\n" +
            "    student s\n" +
            "LEFT JOIN\n" +
            "    attendance a ON s.student_ID = a.student_ID\n" +
            "WHERE\n" +
            "    s.class_id = ?\n" +
            "GROUP BY\n" +
            "    s.student_ID, s.student_name\n" +
            "LIMIT 0, 1000;";

    private static final String GET_ATTENDANCE_DETAILS ="SELECT\n" +
            "    s.student_ID,\n" +
            "    s.student_name,\n" +
            "    a.date,\n" +
            "    sl.slot_name,\n" +
            "    CASE \n" +
            "        WHEN a.attend_status = 1 THEN 'Present'\n" +
            "        ELSE 'Absent'\n" +
            "    END AS attendence_status\n" +
            "FROM\n" +
            "    student s\n" +
            "JOIN\n" +
            "    attendance a ON s.student_ID = a.student_ID\n" +
            "JOIN\n" +
            "    slot sl ON a.slotId = sl.slot_id\n" +
            "WHERE\n" +
            "    s.class_id = ?\n" +
            "    AND s.student_ID = ?\n" +
            "ORDER BY\n" +
            "    a.date DESC;";

    private static final String GET_TOTAL_ATTENDANCE =
            "SELECT\n" +
                    "    s.student_ID,\n" +
                    "    s.student_name,\n" +
                    "    COUNT(a.attendence_id) AS total_attendance, \n" +
                    "    SUM(CASE WHEN a.attend_status = 1 THEN 1 ELSE 0 END) AS total_present, \n" +
                    "    SUM(CASE WHEN a.attend_status = 0 THEN 1 ELSE 0 END) AS total_absent \n" +
                    "FROM\n" +
                    "    student s\n" +
                    "LEFT JOIN\n" +
                    "    attendance a ON s.student_ID = a.student_ID\n" +
                    "WHERE\n" +
                    "    s.class_id = ? \n" +
                    "    AND s.student_ID = ?\n" +
                    "GROUP BY\n" +
                    "    s.student_ID, s.student_name\n" +
                    "LIMIT 0, 1000;";
    @Override
    public List<StudentAttendance> getStudentAttendance(int classId, String date, int slotId) {
        List<StudentAttendance> attendanceList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_STUDENT_ATTENDANCE);
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, slotId);
            preparedStatement.setInt(3, classId);
            preparedStatement.setString(4, date);
            preparedStatement.setInt(5, slotId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String studentName = resultSet.getString("student_name");
                Date dateOfBirth = resultSet.getDate("date_of_birth");
                boolean attendStatus = resultSet.getBoolean("attend_status");

                attendanceList.add(new StudentAttendance(studentId, studentName, dateOfBirth, attendStatus));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching student attendance", e);
            printSQLException(e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return attendanceList;
    }

    @Override
    public void saveAttendance(int classId, String date, int slotId, List<StudentAttendance> attendanceList) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(SAVE_STUDENT_ATTENDANCE);

            for (StudentAttendance attendance : attendanceList) {
                preparedStatement.setInt(1, attendance.getStudentId());
                preparedStatement.setBoolean(2, attendance.getAttendStatus());
                preparedStatement.setString(3, date);
                preparedStatement.setInt(4, slotId);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving attendance", e);
            printSQLException(e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }

    @Override
    public List<AttendanceRecord> getAttendanceSummary(int classId) {
        List<AttendanceRecord> summaryList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_ATTENDANCE_SUMMARY);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String studentName = resultSet.getString("student_name");
                int totalAttendance = resultSet.getInt("total_attendance");
                int presentCount = resultSet.getInt("total_present");
                int absentCount = resultSet.getInt("total_absent");

                summaryList.add(new AttendanceRecord(studentId, studentName, totalAttendance, presentCount, absentCount));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching attendance summary", e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return summaryList;
    }

    @Override
    public List<AttendanceRecord> getAttendanceDetails(int classId, int studentId) {
        List<AttendanceRecord> detailList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_ATTENDANCE_DETAILS);
            preparedStatement.setInt(1, classId);  // Thay thế với classId
            preparedStatement.setInt(2, studentId); // Thêm studentId

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
                String slotName = resultSet.getString("slot_name"); // Lấy tên slot từ truy vấn
                String attendStatus = resultSet.getString("attendence_status"); // Thay đổi để lấy attendence_status

                detailList.add(new AttendanceRecord(date, slotName, attendStatus)); // Chỉnh sửa constructor cho AttendanceRecord
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching attendance details", e);
            printSQLException(e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return detailList;
    }

    @Override
    public AttendanceRecord getTotalAttendance(int classId, int studentId) {
        AttendanceRecord attendanceSummary = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_TOTAL_ATTENDANCE);
            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(2, studentId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int totalAttendance = resultSet.getInt("total_attendance");
                int totalPresent = resultSet.getInt("total_present");
                int totalAbsent = resultSet.getInt("total_absent");
                String studentName = resultSet.getString("student_name");
                int studentID = resultSet.getInt("student_id");

                attendanceSummary = new AttendanceRecord(studentID, studentName, totalAttendance, totalPresent, totalAbsent);
                attendanceSummary.setTotalAbsent(totalAbsent); // Nếu AttendanceSummary có phương thức setTotalAbsent
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching total attendance", e);
            printSQLException(e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return attendanceSummary;
    }


    public static void main(String[] args) {
        AttendanceDAO attendanceService = new AttendanceDAO(); // Giả sử bạn có lớp này

        // Thay đổi classId và studentId theo nhu cầu kiểm tra của bạn
        int classId = 1;
        int studentId = 1;

        // Kiểm tra phương thức getAttendanceSummary
        System.out.println("Attendance Summary:");
        List<AttendanceRecord> summary = attendanceService.getAttendanceSummary(classId);
        for (AttendanceRecord record : summary) {
            System.out.println(record);
        }

        // Kiểm tra phương thức getAttendanceDetails
        System.out.println("\nAttendance Details:");
        List<AttendanceRecord> details = attendanceService.getAttendanceDetails(classId, studentId);
        for (AttendanceRecord record : details) {
            System.out.println(record);
        }

        // Kiểm tra phương thức getTotalAttendance
        System.out.println("\nTotal Attendance:");
        AttendanceRecord totalAttendance = attendanceService.getTotalAttendance(classId, studentId);
        if (totalAttendance != null) {
            System.out.println(totalAttendance);
        } else {
            System.out.println("No attendance record found for the student.");
        }
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
