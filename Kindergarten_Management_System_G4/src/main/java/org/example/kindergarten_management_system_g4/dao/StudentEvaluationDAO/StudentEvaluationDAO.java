/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/1/2024       1.1                     Vũ Gia Huy                      StudentEvaluationDAO
 */

package org.example.kindergarten_management_system_g4.dao.StudentEvaluationDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Student;
import org.example.kindergarten_management_system_g4.model.StudentEvaluation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Lớp StudentEvaluationDAO chứa các phương thức truy cập dữ liệu cho bảng Student_Evaluation.
 * Các phương thức bao gồm lấy, cập nhật, và tạo danh sách đánh giá học sinh theo lớp và kỳ học.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * @see StudentEvaluation
 * @see Student
 */
public class StudentEvaluationDAO extends DBConnection {

    /**
     * Cập nhật thông tin đánh giá của học sinh.
     *
     * @param evaluation đối tượng StudentEvaluation cần cập nhật
     * @return true nếu cập nhật thành công, ngược lại false
     */
    public boolean updateEvaluation(StudentEvaluation evaluation) {
        String sql = "UPDATE Student_Evaluation SET ranking = ?, description = ?, evaluation_date = ? WHERE evaluation_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evaluation.getRanking());
            stmt.setString(2, evaluation.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(evaluation.getEvaluationDate()));
            stmt.setInt(4, evaluation.getEvaluationId());

            return stmt.executeUpdate() > 0; // Trả về true nếu có bản ghi được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    /**
     * Lấy tất cả đánh giá của học sinh theo lớp và kỳ học.
     *
     * @param classId ID của lớp
     * @param termId ID của kỳ học
     * @return danh sách đánh giá của học sinh trong lớp và kỳ học đã chỉ định
     */
    public List<StudentEvaluation> getAllEvaluationsByClassAndTerm(int classId, int termId) {
        List<StudentEvaluation> evaluations = new ArrayList<>();
        String sql = "SELECT s.*, se.*, t.term_name, c.class_name FROM student s "
                + "RIGHT JOIN student_evaluation se ON s.student_ID = se.student_ID "
                + "JOIN term t ON se.term_id = t.term_ID "
                + "JOIN class c ON c.class_ID = s.class_id WHERE s.class_id = ? AND se.term_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            stmt.setInt(2, termId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                StudentEvaluation studentEvaluation = new StudentEvaluation();
                studentEvaluation.setEvaluationId(rs.getInt("evaluation_id"));
                studentEvaluation.setStudentId(rs.getInt("student_id"));
                studentEvaluation.setStudentName(rs.getString("student_name"));
                studentEvaluation.setTermId(rs.getInt("term_id"));
                studentEvaluation.setTermName(rs.getString("term_name"));
                studentEvaluation.setTeacherId(rs.getInt("teacher_id"));
                studentEvaluation.setClassName(rs.getString("class_name"));
                studentEvaluation.setRanking(rs.getString("ranking") != null ? rs.getString("ranking") : "");
                studentEvaluation.setDescription(rs.getString("description") != null ? rs.getString("description") : "");
                studentEvaluation.setEvaluationDate(rs.getDate("evaluation_date") != null ? rs.getDate("evaluation_date").toLocalDate() : null);
                studentEvaluation.setDateOfBirth(rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null);
                studentEvaluation.setGender(Objects.equals(rs.getString("gender"), "1") ? "male" : "female");

                evaluations.add(studentEvaluation); // Thêm đánh giá vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluations;
    }

    /**
     * Lấy tất cả đánh giá của học sinh theo ID người dùng và kỳ học.
     *
     * @param userId ID của người dùng
     * @param termId ID của kỳ học
     * @return danh sách đánh giá của học sinh dựa trên ID người dùng và kỳ học đã chỉ định
     */
    public List<StudentEvaluation> getAllEvaluationsByUserIdAndTerm(int userId, int termId) {
        List<StudentEvaluation> evaluations = new ArrayList<>();
        String sql = "SELECT s.*, se.*, t.term_name, c.class_name FROM student s "
                + "RIGHT JOIN student_evaluation se ON s.student_ID = se.student_ID "
                + "JOIN term t ON se.term_id = t.term_ID "
                + "JOIN class c ON c.class_ID = s.class_id WHERE s.user_id = ? AND se.term_id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, termId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                StudentEvaluation studentEvaluation = new StudentEvaluation();
                studentEvaluation.setEvaluationId(rs.getInt("evaluation_id"));
                studentEvaluation.setStudentId(rs.getInt("student_id"));
                studentEvaluation.setStudentName(rs.getString("student_name"));
                studentEvaluation.setTermId(rs.getInt("term_id"));
                studentEvaluation.setTermName(rs.getString("term_name"));
                studentEvaluation.setTeacherId(rs.getInt("teacher_id"));
                studentEvaluation.setClassName(rs.getString("class_name"));
                studentEvaluation.setRanking(rs.getString("ranking") != null ? rs.getString("ranking") : "");
                studentEvaluation.setDescription(rs.getString("description") != null ? rs.getString("description") : "");
                studentEvaluation.setEvaluationDate(rs.getDate("evaluation_date") != null ? rs.getDate("evaluation_date").toLocalDate() : null);
                studentEvaluation.setDateOfBirth(rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null);
                studentEvaluation.setGender(Objects.equals(rs.getString("gender"), "1") ? "male" : "female");

                evaluations.add(studentEvaluation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluations;
    }

    /**
     * Lấy đánh giá của học sinh dựa trên ID đánh giá.
     *
     * @param id ID của đánh giá
     * @return đối tượng StudentEvaluation nếu tìm thấy, ngược lại là null
     */
    public StudentEvaluation getEvaluationById(int id) {
        String sql = "SELECT s.*, se.*, t.term_name, c.class_name FROM student s "
                + "RIGHT JOIN student_evaluation se ON s.student_ID = se.student_ID "
                + "JOIN term t ON se.term_id = t.term_ID "
                + "JOIN class c ON c.class_ID = s.class_id WHERE se.evaluation_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                StudentEvaluation studentEvaluation = new StudentEvaluation();
                studentEvaluation.setEvaluationId(rs.getInt("evaluation_id"));
                studentEvaluation.setStudentId(rs.getInt("student_id"));
                studentEvaluation.setStudentName(rs.getString("student_name"));
                studentEvaluation.setTermId(rs.getInt("term_id"));
                studentEvaluation.setTermName(rs.getString("term_name"));
                studentEvaluation.setTeacherId(rs.getInt("teacher_id"));
                studentEvaluation.setRanking(rs.getString("ranking") != null ? rs.getString("ranking") : "");
                studentEvaluation.setDescription(rs.getString("description") != null ? rs.getString("description") : "");
                studentEvaluation.setEvaluationDate(rs.getDate("evaluation_date") != null ? rs.getDate("evaluation_date").toLocalDate() : null);
                studentEvaluation.setDateOfBirth(rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null);
                studentEvaluation.setGender(Objects.equals(rs.getString("gender"), "1") ? "male" : "female");
                studentEvaluation.setClassName(rs.getString("class_name"));

                return studentEvaluation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy danh sách sinh viên theo ID lớp.
     *
     * @param classId ID của lớp
     * @return danh sách sinh viên thuộc lớp đã chỉ định
     */
    public ArrayList<Student> getListStudentByClassId(int classId) {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student s JOIN class c ON s.class_id = c.class_ID WHERE c.class_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_ID"));
                student.setName(rs.getString("student_name"));
                student.setClassId(rs.getInt("class_id"));
                student.setClassName(rs.getString("class_name"));

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Tạo danh sách đánh giá cho từng sinh viên trong lớp cho kỳ học đã chỉ định.
     *
     * @param students danh sách sinh viên cần tạo đánh giá
     * @param teacherId ID của giáo viên
     * @param termId ID của kỳ học
     * @return true nếu tất cả đánh giá được tạo thành công, ngược lại false
     */
    public boolean createListStudentEvaluation(ArrayList<Student> students, int teacherId, int termId) {
        String sql = "INSERT INTO Student_Evaluation (student_ID, teacher_id, term_id) VALUES (?, ?, ?)";
        boolean success = true;

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false); // Tắt chế độ tự commit để thực hiện batch

            for (Student student : students) {
                stmt.setInt(1, student.getStudentId());
                stmt.setInt(2, teacherId);
                stmt.setInt(3, termId);
                stmt.addBatch();
            }

            int[] results = stmt.executeBatch();

            for (int result : results) {
                if (result == Statement.EXECUTE_FAILED) {
                    success = false;
                    break;
                }
            }

            if (success) {
                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }
}
