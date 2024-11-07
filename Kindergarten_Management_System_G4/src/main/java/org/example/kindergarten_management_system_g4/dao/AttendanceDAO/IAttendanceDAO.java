
/*
 * Copyright(C) 2005,  <SWP_G4>.
 * <KMS> :
 *  <Kindergarten Management System>
 *
 * Record of change:
 * DATE                       Version             AUTHOR                       DESCRIPTION
 * <11/3/2024>                 <1.1>           <Vu Viet Chuc>            <Update markAttendance method>
 */

package org.example.kindergarten_management_system_g4.dao.AttendanceDAO;
import org.example.kindergarten_management_system_g4.model.AttendanceRecord;
import org.example.kindergarten_management_system_g4.model.StudentAttendance;
import java.util.List;

/**
 * The IAttendanceDAO interface defines the contract for data access operations related to
 * attendance management in the kindergarten management system. It provides methods for
 * retrieving, saving, and summarizing attendance records, as well as sending notifications
 * and checking attendance statuses.
 */
public interface IAttendanceDAO {

     /**
      * Retrieves the attendance records for students in a specific class on a given date and slot.
      *
      * @param classId The ID of the class.
      * @param date The date of the attendance.
      * @param slotId The ID of the time slot.
      * @return A list of StudentAttendance objects representing student attendance data.
      */
     List<StudentAttendance> getStudentAttendance(int classId, String date, int slotId);

     /**
      * Saves attendance records for a specific class, date, and slot. If records already exist,
      * they will be updated.
      *
      * @param classId The ID of the class.
      * @param date The date of the attendance.
      * @param slotId The ID of the time slot.
      * @param attendanceList A list of StudentAttendance objects to be saved.
      */
     void saveAttendance(int classId, String date, int slotId, List<StudentAttendance> attendanceList);

     /**
      * Retrieves a summary of attendance for all students in a specific class, showing counts
      * of total attendance, total presence, and total absence.
      *
      * @param classId The ID of the class.
      * @return A list of AttendanceRecord objects containing attendance summary data.
      */
     List<AttendanceRecord> getAttendanceSummary(int classId);

     /**
      * Retrieves detailed attendance records for a specific student in a class, including dates and statuses.
      *
      * @param classId The ID of the class.
      * @param studentId The ID of the student.
      * @return A list of AttendanceRecord objects detailing the student's attendance.
      */
     List<AttendanceRecord> getAttendanceDetails(int classId, int studentId);

     /**
      * Retrieves the total attendance record for a specific student, including counts of attended
      * and missed sessions.
      *
      * @param classId The ID of the class.
      * @param studentId The ID of the student.
      * @return An AttendanceRecord object summarizing the student's total attendance.
      */
     AttendanceRecord getTotalAttendance(int classId, int studentId);

     /**
      * Sends absence notifications for students in a specific class and time slot on a given date.
      *
      * @param classId The ID of the class.
      * @param date The date of the attendance.
      * @param slotId The ID of the time slot.
      */
     void sendAbsenceNotifications(int classId, String date, int slotId);

     /**
      * Checks if attendance has already been saved for a specific class, date, and time slot.
      *
      * @param classId The ID of the class.
      * @param date The date to check.
      * @param slotId The ID of the time slot.
      * @return true if attendance has been saved; false otherwise.
      */
     boolean isAttendanceSaved(int classId, String date, int slotId);

     /**
      * Marks attendance for a specific class, date, and time slot, potentially initializing
      * attendance data if none exists.
      *
      * @param classId The ID of the class.
      * @param date The date for which attendance is to be marked.
      * @param slotId The ID of the time slot.
      */
     void markAttendance(int classId, String date, int slotId);

     /**
      * Retrieves detailed attendance records for a child, identified by the user ID.
      *
      * @param userId The ID of the user (child).
      * @return A list of AttendanceRecord objects containing detailed attendance information.
      */
     List<AttendanceRecord> getChildDetailAttendance(int userId);

     /**
      * Retrieves the total attendance record for a child, including overall attendance statistics.
      *
      * @param userId The ID of the user (child).
      * @return An AttendanceRecord object summarizing the child's total attendance.
      */
     AttendanceRecord getChildTotalAttendance(int userId);
}


