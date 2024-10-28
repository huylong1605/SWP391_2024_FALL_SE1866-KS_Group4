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

import org.example.kindergarten_management_system_g4.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface IScheduleDAO {

       List<Schedule> scheduleOfStudent(int parentId) throws SQLException;


}
