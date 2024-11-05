package org.example.kindergarten_management_system_g4.dao.TeacherScheduleDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.TeacherSchedule;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeachingScheduleDAO extends DBConnection {
    private static final String GET_TEACHER_SCHEDULE = "SELECT s.schedule_ID, c.class_ID, c.class_name, sub.subject_name, sl.slot_id, sl.slot_name, s.date, \n" +
            "       r.room_number, t.term_name, s.day_of_week, sl.start_time, sl.end_time \n" +
            "FROM schedule s \n" +
            "JOIN class c ON s.class_id = c.class_ID \n" +
            "JOIN subject_schedule ss ON s.schedule_ID = ss.schedule_ID \n" +
            "JOIN subject sub ON ss.subject_ID = sub.subject_ID \n" +
            "JOIN slot sl ON s.slotId = sl.slot_id\n" +
            "JOIN term t ON s.term_ID = t.term_ID\n" +
            "JOIN room r ON c.room_ID = r.room_ID\n" +
            "WHERE c.user_id = ?\n" +
            "ORDER BY s.date;";

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
                teachingSchedules.add(new TeacherSchedule(scheduleID, classId, className, subjectName, slotId, slotName, date,
                        room, termName, dayOfWeek, startTime, endTime)); // Cập nhật constructor
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting teaching schedules", e);
        }
        return teachingSchedules;
    }


    public static void main(String[] args) {
        // Giả sử bạn có ID giáo viên cần kiểm tra
        int teacherId = 6; // Thay đổi ID theo yêu cầu của bạn

        TeachingScheduleDAO teachingScheduleDAO = new TeachingScheduleDAO();
        List<TeacherSchedule> schedules = teachingScheduleDAO.getTeachingSchedules(teacherId);

        // Kiểm tra và in ra danh sách lịch dạy
        if (schedules.isEmpty()) {
            System.out.println("Không có lịch dạy nào cho giáo viên với ID: " + teacherId);
        } else {
            System.out.println("Lịch dạy cho giáo viên với ID: " + teacherId);
            for (TeacherSchedule schedule : schedules) {
                System.out.println("Schedule ID: " + schedule.getScheduleID());
                System.out.println("Class ID: " + schedule.getClassId());
                System.out.println("Class Name: " + schedule.getClassName());
                System.out.println("Subject Name: " + schedule.getSubjectName());
                System.out.println("Slot ID: " + schedule.getSlotId());
                System.out.println("Slot Name: " + schedule.getSlotName());
                System.out.println("Date: " + schedule.getDate());
                System.out.println("Room: " + schedule.getRoom());
                System.out.println("Term Name: " + schedule.getTermName());
                System.out.println("Day of Week: " + schedule.getDayOfWeek());
                System.out.println("Start Time: " + schedule.getStartTime());
                System.out.println("End Time: " + schedule.getEndTime());
                System.out.println("----------------------------");
            }
        }
    }


}
