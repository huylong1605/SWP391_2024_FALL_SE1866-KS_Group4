package org.example.kindergarten_management_system_g4.dao.StudentClassManageDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentClassManageDAO extends DBConnection implements IStudentClassManageDAO{

    public static final String ADD_STUDENT_TO_CLASS = "UPDATE student SET class_id = ? WHERE student_ID = ?";

    public static final String GET_CLASS_BY_LEVEL = "SELECT class_ID, class_name, room_ID, user_ID FROM class WHERE class_level_ID = ?";

    public static final String GET_ALL_CLASS_LEVEL = "SELECT class_level_ID, class_level_name, description, ageRange FROM class_level";

    // Truy vấn lấy học sinh chưa có lớp với phân trang
    public static final String GET_ALL_UNASSIGNED_STUDENTS_PAGED = "SELECT s.student_ID, s.student_name, s.gender, s.date_of_birth \n" +
            "            FROM student s \n" +
            "            WHERE s.class_id IS NULL order by date_of_birth desc\n" +
            "            LIMIT ? OFFSET ?;";

    // Truy vấn đếm tổng số học sinh chưa có lớp
    public static final String COUNT_ALL_UNASSIGNED_STUDENTS = "SELECT COUNT(*) FROM student s WHERE s.class_id IS NULL";


    public static final String GET_UNASSIGNED_STUDENTS_PAGED = "SELECT s.student_ID, s.student_name, s.gender, s.date_of_birth " +
            "FROM student s " +
            "JOIN class_level cl ON TIMESTAMPDIFF(YEAR, s.date_of_birth, CURDATE()) = cl.ageRange " +
            "WHERE s.class_id IS NULL AND cl.class_level_ID = ? " +
            "LIMIT ? OFFSET ?";

    public static final String COUNT_UNASSIGNED_STUDENTS = "SELECT COUNT(*) FROM student s " +
            "JOIN class_level cl ON TIMESTAMPDIFF(YEAR, s.date_of_birth, CURDATE()) = cl.ageRange " +
            "WHERE s.class_id IS NULL AND cl.class_level_ID = ?";

    // Truy vấn tìm kiếm học sinh theo tên với phân trang
    public static final String SEARCH_STUDENTS_BY_NAME_PAGED = "SELECT s.student_ID, s.student_name, s.gender, s.date_of_birth " +
            "FROM student s WHERE s.student_name LIKE ? " +
            "ORDER BY s.student_name LIMIT ? OFFSET ?";


    public List<Student> searchStudentsByNameWithPaging(String name, int limit, int offset) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_STUDENTS_BY_NAME_PAGED)) {

            // Sử dụng dấu '%' để tìm kiếm học sinh có tên chứa từ khóa
            statement.setString(1, "%" + name + "%");
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_ID"));
                student.setName(resultSet.getString("student_name"));
                student.setGender(resultSet.getBoolean("gender"));
                student.setDob(resultSet.getDate("date_of_birth").toLocalDate()); // Nếu cần
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    public int countStudentsByName(String name) {
        int count = 0;
        String query = "SELECT COUNT(*) FROM student WHERE student_name LIKE ?;";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }


        // Gán nhiều học sinh vào lớp
        public void assignStudentsToClass(List<Integer> studentIds, int classId) {

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(ADD_STUDENT_TO_CLASS)) {
                connection.setAutoCommit(false); // Tắt tự động commit để thực hiện giao dịch

                for (int studentId : studentIds) {
                    statement.setInt(1, classId);
                    statement.setInt(2, studentId);
                    statement.addBatch(); // Thêm vào batch
                }

                statement.executeBatch(); // Thực thi batch
                connection.commit(); // Commit khi tất cả thành công

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    // Lấy danh sách các lớp theo cấp độ
    public List<Classes> getClassesByClassLevel(int classLevelId) {
        List<Classes> classes = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CLASS_BY_LEVEL)) {

            statement.setInt(1, classLevelId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Classes classObj = new Classes();
                classObj.setClassId(resultSet.getInt("class_ID"));
                classObj.setClassName(resultSet.getString("class_name"));
                classObj.setRoomId(resultSet.getInt("room_ID"));
                classObj.setUserId(resultSet.getInt("user_ID"));
                classes.add(classObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }

    // Lấy danh sách tất cả cấp độ lớp
    public List<ClassLevel> getAllClassLevels() {
        List<ClassLevel> classLevels = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_CLASS_LEVEL)) {
            while (resultSet.next()) {
                ClassLevel classLevel = new ClassLevel();
                classLevel.setClassLevelId(resultSet.getInt("class_level_ID"));
                classLevel.setClassLevelName(resultSet.getString("class_level_name"));
                classLevel.setDescription(resultSet.getString("description"));
                classLevel.setAgeRange(resultSet.getInt("ageRange"));
                classLevels.add(classLevel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classLevels;
    }

    public List<Student> getAllUnassignedStudentsWithPaging(int limit, int offset) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_UNASSIGNED_STUDENTS_PAGED)) {

            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_ID"));
                student.setName(resultSet.getString("student_name"));
                student.setGender(resultSet.getBoolean("gender"));
                student.setDob(resultSet.getDate("date_of_birth").toLocalDate());
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public int countAllUnassignedStudents() {
        int count = 0;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_ALL_UNASSIGNED_STUDENTS)) {

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Lấy danh sách học sinh chưa có lớp theo cấp độ
    public List<Student> getUnassignedStudentsByClassLevelWithPaging(int classLevelId, int limit, int offset) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_UNASSIGNED_STUDENTS_PAGED)) {

            statement.setInt(1, classLevelId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_ID"));
                student.setName(resultSet.getString("student_name"));
                student.setGender(resultSet.getBoolean("gender"));
                student.setDob(resultSet.getDate("date_of_birth").toLocalDate()); // nếu cần
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public int countUnassignedStudentsByClassLevel(int classLevelId) {
        int count = 0;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_UNASSIGNED_STUDENTS)) {

            statement.setInt(1, classLevelId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


}



