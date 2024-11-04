/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/28/2024       1.1              Nguyễn Huy Long - HE160140         Create interface  IScheduleDAO
 */

package org.example.kindergarten_management_system_g4.dao.scheduledao;

import org.example.kindergarten_management_system_g4.model.*;

import java.sql.SQLException;
import java.util.List;

public interface IScheduleDAO {

    List<ScheduleDAL> getScheduleOfStudent(int parentId, String startDate, String endDate) throws SQLException;

    List<Term> getListTerm() throws SQLException;

    List<Subject> getListSubject() throws SQLException;

    List<Classes> getListClass() throws SQLException;

    List<Slot> getListSlot() throws SQLException;

    Boolean addSchedule(Schedule schedule, int subjectId) throws SQLException;

    Boolean getSchedule(Schedule schedule) throws SQLException;

    Boolean getSchedule2(Schedule schedule) throws SQLException;

    Term getTermById(int TermId) throws SQLException;

    List<ScheduleDAL> getListScheduleByClass(int classId, String startDate, String endDate) throws SQLException;

    Schedule getScheduleById(int scheduleId) throws SQLException;

    Subject getSubjectByScheduleId(int scheduleId) throws SQLException;

    Boolean editSchedule(Schedule schedule, int subjectId) throws SQLException;

    Slot getSlotByScheduleId(int slotId) throws SQLException;

    Boolean changeSlot(Schedule schedule) throws SQLException;
}
