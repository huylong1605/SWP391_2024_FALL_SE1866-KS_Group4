package org.example.kindergarten_management_system_g4.dao.teacherScheduleDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.TeacherSchedule;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeachingScheduleDAO extends DBConnection {
    private static final String GET_TEACHER_SCHEDULE = "SELECT s.schedule_ID, c.class_ID, c.class_name, sub.subject_name, sl.slot_id, sl.slot_name, s.date, \n" +
            "       r.room_number, t.term_name, s.day_of_week, sl.start_time, sl.end_time, \n" +
            "       MAX(a.attendanceMarked) AS attendanceMarked\n" +
            "FROM schedule s\n" +
            "JOIN class c ON s.class_id = c.class_ID\n" +
            "JOIN subject_schedule ss ON s.schedule_ID = ss.schedule_ID\n" +
            "JOIN subject sub ON ss.subject_ID = sub.subject_ID\n" +
            "JOIN slot sl ON s.slotId = sl.slot_id\n" +
            "JOIN term t ON s.term_ID = t.term_ID\n" +
            "JOIN room r ON c.room_ID = r.room_ID\n" +
            "LEFT JOIN attendance a ON a.slotId = s.slotId AND a.date = s.date\n" +
            "WHERE c.user_id = ?\n" +
            "GROUP BY s.schedule_ID, c.class_ID, c.class_name, sub.subject_name, sl.slot_id, sl.slot_name, s.date, \n" +
            "         r.room_number, t.term_name, s.day_of_week, sl.start_time, sl.end_time\n" +
            "ORDER BY s.date\n" +
            "LIMIT 0, 1000;\n";

    private static final Logger LOGGER = Logger.getLogger(TeachingScheduleDAO.class.getName());

    public List<TeacherSchedule> getTeachingSchedules(int teacherId) {
        List<TeacherSchedule> teachingSchedules = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TEACHER_SCHEDULE)) {
            preparedStatement.setInt(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int scheduleID = resultSet.getInt("schedule_ID");
                int classId = resultSet.getInt("class_ID"); // Lấy classId
                String className = resultSet.getString("class_name");
                String subjectName = resultSet.getString("subject_name");
                int slotId = resultSet.getInt("slot_id"); // Lấy slotId
                String slotName = resultSet.getString("slot_name");
                String date = resultSet.getString("date");
                String room = resultSet.getString("room_number");
                String termName = resultSet.getString("term_name");
                String dayOfWeek = resultSet.getString("day_of_week");
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");
                boolean attendanceMarked = resultSet.getBoolean("attendanceMarked");
                teachingSchedules.add(new TeacherSchedule(scheduleID, classId, className, subjectName, slotId, slotName, date,
                        room, termName, dayOfWeek, startTime, endTime, attendanceMarked)); // Cập nhật constructor
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting teaching schedules", e);
        }
        return teachingSchedules;
    }

}
