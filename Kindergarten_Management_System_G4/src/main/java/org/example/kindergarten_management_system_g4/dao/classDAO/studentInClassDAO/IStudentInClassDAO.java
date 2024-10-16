package org.example.kindergarten_management_system_g4.dao.classDAO.studentInClassDAO;

import org.example.kindergarten_management_system_g4.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface IStudentInClassDAO {
    List<Student> getStudentsByClassId(int classId) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
    boolean addStudentToClass(int studentId, int classId) throws SQLException;
    boolean removeStudentFromClass(int studentId) throws SQLException;
    Student getStudentById(int studentId) throws SQLException;
}
