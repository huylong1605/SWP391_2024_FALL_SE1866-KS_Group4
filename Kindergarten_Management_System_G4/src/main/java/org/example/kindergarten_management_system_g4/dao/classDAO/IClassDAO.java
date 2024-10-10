package org.example.kindergarten_management_system_g4.dao.classDAO;

import org.example.kindergarten_management_system_g4.model.ClassDAL;

import java.sql.SQLException;
import java.util.List;

public interface IClassDAO {

     List<ClassDAL> listClass() throws SQLException;
}
