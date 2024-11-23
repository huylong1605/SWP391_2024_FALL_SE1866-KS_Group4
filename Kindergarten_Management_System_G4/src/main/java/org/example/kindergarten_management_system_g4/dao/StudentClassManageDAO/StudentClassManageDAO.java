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


    // Truy vấn đếm tổng số học sinh da có lớp
    public static final String COUNT_ALL_ASSIGNED_STUDENTS = "SELECT COUNT(*) FROM student s WHERE s.class_id IS NOT NULL";


    public static final String GET_UNASSIGNED_STUDENTS_PAGED = "SELECT s.student_ID, s.student_name, s.gender, s.date_of_birth " +
            "FROM student s " +
            "JOIN class_level cl ON TIMESTAMPDIFF(YEAR, s.date_of_birth, CURDATE()) = cl.ageRange " +
            "WHERE s.class_id IS NULL AND cl.class_level_ID = ? " +
            "LIMIT ? OFFSET ?";



    public static final String GET_ASSIGNED_STUDENTS_PAGED = "SELECT \n" +
            "    s.student_ID, \n" +
            "    s.student_name, \n" +
            "    s.gender, \n" +
            "    s.date_of_birth,\n" +
            "    c.class_id,         -- class_id từ bảng class\n" +
            "    c.class_name,       -- class_name từ bảng class\n" +
            "    cl.class_level_ID,  -- class_level_ID từ bảng class_level\n" +
            "    cl.class_level_name -- class_level_name từ bảng class_level\n" +
            "FROM \n" +
            "    student s\n" +
            "JOIN \n" +
            "    class_level cl \n" +
            "    ON TIMESTAMPDIFF(YEAR, s.date_of_birth, CURDATE()) = cl.ageRange\n" +
            "JOIN \n" +
            "    class c \n" +
            "    ON s.class_id = c.class_id    -- Kết nối với bảng class để lấy class_id và class_name\n" +
            "WHERE \n" +
            "    s.class_id IS NOT NULL \n" +
            "    AND cl.class_level_ID = ?     -- Điều kiện lọc theo class_level_ID\n" +
            "LIMIT ? OFFSET ?;\n";



    public static final String COUNT_UNASSIGNED_STUDENTS = "SELECT COUNT(*) FROM student s " +
            "JOIN class_level cl ON TIMESTAMPDIFF(YEAR, s.date_of_birth, CURDATE()) = cl.ageRange " +
            "WHERE s.class_id IS NULL AND cl.class_level_ID = ?";


    public static final String COUNT_ASSIGNED_STUDENTS = "SELECT COUNT(*) FROM student s " +
            "JOIN class_level cl ON TIMESTAMPDIFF(YEAR, s.date_of_birth, CURDATE()) = cl.ageRange " +
            "WHERE s.class_id IS NOT NULL AND cl.class_level_ID = ?";

    // Truy vấn tìm kiếm học sinh theo tên với phân trang
    public static final String SEARCH_STUDENTS_BY_NAME_PAGED = "SELECT s.student_ID, s.student_name, s.gender, s.date_of_birth, \n" +
            "       c.class_name, c.class_level_ID, cl.class_level_name\n" +
            "FROM student s\n" +
            "JOIN class c ON s.class_id = c.class_ID\n" +
            "JOIN class_level cl ON c.class_level_ID = cl.class_level_ID\n" +
            "WHERE s.student_name LIKE ? \n" +
            "ORDER BY s.student_name \n" +
            "LIMIT ? OFFSET ?\n";


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
                student.setClassName(resultSet.getString("class_name"));
                student.setClassLevelName(resultSet.getString("class_level_name"));
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

    public int countStudentsInClass(int classId) {
        String sql = "SELECT COUNT(*) FROM student WHERE class_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu có lỗi
    }


    public String getClassNameById(int classId) {
        String className = "";
        String sql = "SELECT class_name FROM class WHERE class_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    className = rs.getString("class_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return className;
    }



    //========================= DA CO LOP=========================


    public List<Student> getAssignedStudentsWithPaging(int pageSize, int offset) {
        List<Student> students = new ArrayList<>();
        String query = " SELECT \n" +
                "    s.student_id, \n" +
                "    s.student_name, \n" +
                "    s.date_of_birth, \n" +
                "    s.gender, \n" +
                "    c.class_name,\n" +
                "    cl.class_level_ID,    -- class_level_ID từ bảng class_level\n" +
                "    cl.class_level_name   -- class_level_name từ bảng class_level\n" +
                "FROM \n" +
                "    student s\n" +
                "JOIN \n" +
                "    class c ON s.class_id = c.class_id\n" +
                "JOIN \n" +
                "    class_level cl ON c.class_level_ID = cl.class_level_ID  -- Kết nối với bảng class_level để lấy class_level_ID và class_level_name\n" +
                "LIMIT ? OFFSET ?;\n";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pageSize);  // Set limit for pagination
            stmt.setInt(2, offset);    // Set offset for pagination

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student();

                // Lấy thông tin từ ResultSet và gán vào đối tượng Student
                student.setStudentId(rs.getInt("student_id"));
                student.setName(rs.getString("student_name"));
                student.setGender(rs.getBoolean("gender"));
                student.setDob(rs.getDate("date_of_birth").toLocalDate()); // nếu cần
                student.setClassName(rs.getString("class_name"));  // Thêm tên lớp
                student.setClassLevelId(rs.getInt("class_level_ID"));
                student.setClassLevelName(rs.getString("class_level_name"));

                // Thêm học sinh vào danh sách
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    public List<Student> getAssignedStudentsByClassLevelWithPaging(int classLevelId, int limit, int offset) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ASSIGNED_STUDENTS_PAGED)) {

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
                student.setClassId(resultSet.getInt("class_id"));
                student.setClassName(resultSet.getString("class_name"));
                student.setClassLevelId(resultSet.getInt("class_level_ID"));
                student.setClassLevelName(resultSet.getString("class_level_name"));
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    public int countAllAssignedStudents() {
        int count = 0;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_ALL_ASSIGNED_STUDENTS)) {

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    public int countAssignedStudentsByClassLevel(int classLevelId) {
        int count = 0;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_ASSIGNED_STUDENTS)) {

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



    public void removeStudentsFromClass(List<Integer> studentIds) throws SQLException {  // Thêm throws SQLException
        String query = "UPDATE student SET class_id = NULL WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int studentId : studentIds) {
                stmt.setInt(1, studentId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            // Đảm bảo SQLException được ném lại nếu có lỗi
            throw new SQLException("Lỗi khi xóa học sinh khỏi lớp.", e);
        }
    }



    public void updateStudentClass(List<Integer> studentIds, int newClassId) throws SQLException {  // Thêm throws SQLException
        String query = "UPDATE student SET class_id = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int studentId : studentIds) {
                stmt.setInt(1, newClassId);
                stmt.setInt(2, studentId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            // Đảm bảo SQLException được ném lại nếu có lỗi
            throw new SQLException("Lỗi khi cập nhật lớp học cho học sinh.", e);
        }
    }





}



