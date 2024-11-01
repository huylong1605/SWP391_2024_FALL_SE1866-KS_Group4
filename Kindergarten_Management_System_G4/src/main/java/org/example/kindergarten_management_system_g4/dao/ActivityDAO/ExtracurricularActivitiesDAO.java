package org.example.kindergarten_management_system_g4.dao.ActivityDAO;

import org.example.kindergarten_management_system_g4.model.ExtracurricularActivities;
import java.sql.SQLException;
import java.util.List;

public interface ExtracurricularActivitiesDAO {
    List<ExtracurricularActivities> getAllActivities() throws SQLException;
    void addActivity(ExtracurricularActivities activity) throws SQLException;
    void updateActivity(ExtracurricularActivities activity) throws SQLException;
    void deleteActivity(int activityId) throws SQLException;
}
