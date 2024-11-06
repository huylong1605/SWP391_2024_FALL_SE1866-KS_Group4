package org.example.kindergarten_management_system_g4.dao.AttendanceDAO;

import org.example.kindergarten_management_system_g4.model.AttendanceRecord;
import org.example.kindergarten_management_system_g4.model.StudentAttendance;

import java.sql.Date;
import java.util.List;

public interface IAttendanceDAO {

     List<StudentAttendance> getStudentAttendance(int classId, String date, int slotId);

     void saveAttendance(int classId, String date, int slotId, List<StudentAttendance> attendanceList);

     List<AttendanceRecord> getAttendanceSummary(int classId);

     List<AttendanceRecord> getAttendanceDetails(int classId, int studentId);

     AttendanceRecord getTotalAttendance(int classId, int studentId);

     void sendAbsenceNotifications(int classId, String date, int slotId);

     boolean isAttendanceSaved(int classId, String date, int slotId);

     void markAttendance(int classId, String date, int slotId);

     List<AttendanceRecord> getChildDetailAttendance(int userId);

     AttendanceRecord getChildTotalAttendance(int userId);
}


