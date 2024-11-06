package org.example.kindergarten_management_system_g4.dao.AttendanceDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
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
                    "    s.date_of_birth,\n" +
                    "    COUNT(a.attendence_id) AS total_attendance,\n" +
                    "    SUM(CASE WHEN a.attend_status = 1 THEN 1 ELSE 0 END) AS total_present,\n" +
                    "    SUM(CASE WHEN a.attend_status = 0 THEN 1 ELSE 0 END) AS total_absent\n" +
                    "FROM\n" +
                    "    student s\n" +
                    "LEFT JOIN\n" +
                    "    attendance a ON s.student_ID = a.student_ID\n" +
                    "WHERE\n" +
                    "    s.class_id = ?\n" +
                    "    AND s.student_ID = ?\n" +
                    "GROUP BY\n" +
                    "    s.student_ID, s.student_name, s.date_of_birth\n" +
                    "LIMIT 0, 1000;";
    private  static final String IS_SAVE_ATTENDANCE = "SELECT COUNT(*) AS count \n" +
            "FROM Attendance a\n" +
            "JOIN student s ON a.student_ID = s.student_ID\n" +
            "WHERE s.class_id = ? AND a.date = ? AND a.slotId = ?";

    private  static final String IS_MARK_ATTENDANCE = "UPDATE attendance a\n" +
            "JOIN schedule s ON a.slotId = s.slotId AND a.date = s.date\n" +
            "SET a.attendanceMarked = TRUE\n" +
            "WHERE s.class_id = ? AND s.date = ? AND s.slotId = ?";

    private static final String GET_STUDENT_AND_CLASS_BY_USER_ID_QUERY =
            "SELECT student_id, class_id FROM student WHERE user_id = ?";

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
                Date dateOfBirth = resultSet.getDate("date_of_birth"); // Lấy date_of_birth

                // Tạo AttendanceRecord với date_of_birth và các dữ liệu khác
                attendanceSummary = new AttendanceRecord(studentID, studentName, dateOfBirth, totalAttendance, totalPresent, totalAbsent);
                attendanceSummary.setTotalAbsent(totalAbsent); // Nếu AttendanceRecord có phương thức setTotalAbsent
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching total attendance", e);
            printSQLException(e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return attendanceSummary;
    }


    public List<AttendanceRecord> getChildDetailAttendance(int userId) {
        List<AttendanceRecord> attendanceList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Kết nối đến cơ sở dữ liệu
            connection = getConnection();

            // Lấy studentId và classId từ userId
            preparedStatement = connection.prepareStatement(GET_STUDENT_AND_CLASS_BY_USER_ID_QUERY);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                int classId = resultSet.getInt("class_id");

                // Sau khi có studentId và classId, lấy thông tin điểm danh
                preparedStatement = connection.prepareStatement(GET_ATTENDANCE_DETAILS);
                preparedStatement.setInt(1, classId);  // Thay thế với classId
                preparedStatement.setInt(2, studentId); // Thêm studentId
                resultSet = preparedStatement.executeQuery();

                // Duyệt qua kết quả và thêm vào danh sách
                while (resultSet.next()) {
                    Date date = resultSet.getDate("date");
                    String slotName = resultSet.getString("slot_name"); // Lấy tên slot từ truy vấn
                    String attendStatus = resultSet.getString("attendence_status"); // Thay đổi để lấy attendence_status

                    // Tạo đối tượng AttendanceRecord và thêm vào danh sách
                    attendanceList.add(new AttendanceRecord(date, slotName, attendStatus));
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching child attendance by userId", e);
            printSQLException(e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return attendanceList;
    }

    public AttendanceRecord getChildTotalAttendance(int userId) {
        AttendanceRecord attendanceSummary = null;
        List<AttendanceRecord> attendanceList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Kết nối đến cơ sở dữ liệu
            connection = getConnection();

            // Lấy studentId và classId từ userId
            preparedStatement = connection.prepareStatement(GET_STUDENT_AND_CLASS_BY_USER_ID_QUERY);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                int classId = resultSet.getInt("class_id");

                // Sau khi có studentId và classId, lấy thông tin điểm danh
                preparedStatement = connection.prepareStatement(GET_TOTAL_ATTENDANCE);
                preparedStatement.setInt(1, classId);
                preparedStatement.setInt(2, studentId);
                resultSet = preparedStatement.executeQuery();

                // Duyệt qua kết quả và thêm vào danh sách
                while (resultSet.next()) {
                    int totalAttendance = resultSet.getInt("total_attendance");
                    int totalPresent = resultSet.getInt("total_present");
                    int totalAbsent = resultSet.getInt("total_absent");
                    String studentName = resultSet.getString("student_name");
                    int studentID = resultSet.getInt("student_id");
                    Date dateOfBirth = resultSet.getDate("date_of_birth"); // Lấy date_of_birth

                    // Tạo AttendanceRecord với date_of_birth và các dữ liệu khác
                    attendanceSummary = new AttendanceRecord(studentID, studentName, dateOfBirth, totalAttendance, totalPresent, totalAbsent);
                    attendanceSummary.setTotalAbsent(totalAbsent); // Nếu AttendanceRecord có phương thức setTotalAbsent
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching child attendance by userId", e);
            printSQLException(e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return attendanceSummary;
    }

    public void sendAbsenceNotifications(int classId, String date, int slotId) {
        List<StudentAttendance> absentees = getStudentAttendance(classId, date, slotId);
        for (StudentAttendance student : absentees) {
            if (!student.getAttendStatus()) { // Kiểm tra nếu học sinh vắng mặt
                String parentEmail = getParentEmail(student.getStudentId());
                if (parentEmail != null && !parentEmail.isEmpty()) {
                    String subject = "Notification: Student Absence";
                    String message = "Dear Parent,\n\n" +
                            "Student " + student.getStudentName() + " was absent on " + date + " during slot " + slotId + ".\n" +
                            "Please contact the school for more details.\n\n" +
                            "Sincerely,\n" +
                            "Kindergarten School";
                    if (sendEmail(parentEmail, subject, message)) {
                        LOGGER.log(Level.INFO, "Email sent successfully to: " + parentEmail);
                    } else {
                        LOGGER.log(Level.WARNING, "Failed to send email to: " + parentEmail);
                    }
                } else {
                    LOGGER.log(Level.WARNING, "No parent email found for student ID: " + student.getStudentId());
                }
            }
        }
    }

    private boolean sendEmail(String to, String subject, String message) {
        // Cấu hình thông tin server gửi mail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tcnatsu150977@gmail.com", "yqxf ijdq ypze lhed"); // Từ tệp cấu hình hoặc biến môi trường
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("tcnatsu150977@gmail.com"));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage); // Gửi email
            return true;
        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "Error sending email to " + to, e);
            return false;
        }
    }

    private String getParentEmail(int studentId) {
        String email = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT u.email FROM user u INNER JOIN student s ON u.user_id = s.user_id WHERE s.student_ID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString("email");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching parent email for student ID: " + studentId, e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return email;
    }

    public boolean isAttendanceSaved(int classId, String date, int slotId) {
        boolean isSaved = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(IS_SAVE_ATTENDANCE);
            preparedStatement.setInt(1, classId);
            preparedStatement.setString(2, date);
            preparedStatement.setInt(3, slotId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                isSaved = count > 0; // Nếu count lớn hơn 0, tức là đã có bản ghi
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking attendance", e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return isSaved;
    }

    public void markAttendance(int classId, String date, int slotId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(IS_MARK_ATTENDANCE);
            stmt.setInt(1, classId);
            stmt.setString(2, date);
            stmt.setInt(3, slotId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking attendance", e);
        } finally {
        closeResources(resultSet, preparedStatement, connection);
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
