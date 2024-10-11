package org.example.kindergarten_management_system_g4.dao.classDAO;

import org.example.kindergarten_management_system_g4.model.*;

import java.sql.SQLException;
import java.util.List;

public interface IClassDAO {

     List<ClassDAL> listClass() throws SQLException;
     List<ClassDAL> listClassSearchFilter(int classLevelId,String search ) throws SQLException;
     List<Room> listRoom() throws SQLException;

     List<User>  listTeacher() throws SQLException;

     List<ClassLevel> listClassLevel() throws SQLException;
     List<ClassDAL> listClassFilter(int classLevelId) throws SQLException;
     List<ClassDAL> listClassSearch(String search) throws SQLException;


     Boolean createClass(Classes classes) throws SQLException;

     String getClassName(String className) throws SQLException;
}
