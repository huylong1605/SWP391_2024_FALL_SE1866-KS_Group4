package org.example.kindergarten_management_system_g4.dao.StudentEvaluationDAO;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.model.Application;
import org.example.kindergarten_management_system_g4.model.Student;
import org.example.kindergarten_management_system_g4.model.StudentEvaluation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentEvaluationDAO extends DBConnection {
    public boolean updateEvaluation(StudentEvaluation evaluation) {
        String sql = "UPDATE Student_Evaluation SET ranking = ?, description = ?, evaluation_date = ? WHERE evaluation_ID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evaluation.getRanking());
            stmt.setString(2, evaluation.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(evaluation.getEvaluationDate()));
            stmt.setInt(4, evaluation.getEvaluationId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<StudentEvaluation> getAllEvaluationsByClassAndTerm(int classId, int termId) {
        List<StudentEvaluation> evaluations = new ArrayList<>();
        String sql = "select\n" + "\ts.*, se.*, t.term_name, c.class_name\n" + "from\n" + "\tstudent s\n" + "right join student_evaluation se \n" + "on s.student_ID = se.student_ID\n" + "join term t \n" + "on se.term_id = t.term_ID join class c on c.class_ID = s.class_id  \n" + "where\n" + "\ts.class_id = ? and se.term_id = ?";

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
                // Check ranking
                String ranking = rs.getString("ranking");
                studentEvaluation.setRanking(ranking != null ? ranking : ""); // Return empty string if ranking is NULL

// Check description
                String description = rs.getString("description");
                studentEvaluation.setDescription(description != null ? description : ""); // Return empty string if description is NULL

// Check evaluation_date
                Date evaluationDate = rs.getDate("evaluation_date");
                studentEvaluation.setEvaluationDate(evaluationDate != null ? evaluationDate.toLocalDate() : null); // Set to null if evaluation_date is NULL

// Retrieve and set date of birth
                Date dateOfBirth = rs.getDate("date_of_birth");
                studentEvaluation.setDateOfBirth(dateOfBirth != null ? dateOfBirth.toLocalDate() : null); // Set to null if date_of_birth is NULL

                studentEvaluation.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                studentEvaluation.setGender(Objects.equals(rs.getString("gender"), "1") ? "male" : "female");

                evaluations.add(studentEvaluation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluations;
    }

    public List<StudentEvaluation> getAllEvaluationsByUserIdAndTerm(int userId, int termId) {
        List<StudentEvaluation> evaluations = new ArrayList<>();
        String sql = "select\n" + "\ts.*, se.*, t.term_name, c.class_name\n" + "from\n" + "\tstudent s\n" + "right join student_evaluation se \n" + "on s.student_ID = se.student_ID\n" + "join term t \n" + "on se.term_id = t.term_ID  join class c on c.class_ID = s.class_id \n" + "where s.user_id = ? and se.term_id = ?";

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
                // Check ranking
                String ranking = rs.getString("ranking");
                studentEvaluation.setRanking(ranking != null ? ranking : ""); // Return empty string if ranking is NULL

// Check description
                String description = rs.getString("description");
                studentEvaluation.setDescription(description != null ? description : ""); // Return empty string if description is NULL

// Check evaluation_date
                Date evaluationDate = rs.getDate("evaluation_date");
                studentEvaluation.setEvaluationDate(evaluationDate != null ? evaluationDate.toLocalDate() : null); // Set to null if evaluation_date is NULL

// Retrieve and set date of birth
                Date dateOfBirth = rs.getDate("date_of_birth");
                studentEvaluation.setDateOfBirth(dateOfBirth != null ? dateOfBirth.toLocalDate() : null); // Set to null if date_of_birth is NULL

                studentEvaluation.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                studentEvaluation.setGender(Objects.equals(rs.getString("gender"), "1") ? "male" : "female");

                evaluations.add(studentEvaluation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluations;
    }

    public StudentEvaluation getEvaluationById(int id) {
        String sql = "select\n" + "\ts.*, se.*, t.term_name, c.class_name\n" + "from\n" + "\tstudent s\n" + "right join student_evaluation se \n" + "on s.student_ID = se.student_ID\n" + "join term t \n" + "on se.term_id = t.term_ID join class c on c.class_ID = s.class_id \n" + "where se.evaluation_ID = ?";
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
                // Check ranking
                String ranking = rs.getString("ranking");
                studentEvaluation.setRanking(ranking != null ? ranking : ""); // Return empty string if ranking is NULL

// Check description
                String description = rs.getString("description");
                studentEvaluation.setDescription(description != null ? description : ""); // Return empty string if description is NULL

// Check evaluation_date
                Date evaluationDate = rs.getDate("evaluation_date");
                studentEvaluation.setEvaluationDate(evaluationDate != null ? evaluationDate.toLocalDate() : null); // Set to null if evaluation_date is NULL

// Retrieve and set date of birth
                Date dateOfBirth = rs.getDate("date_of_birth");
                studentEvaluation.setDateOfBirth(dateOfBirth != null ? dateOfBirth.toLocalDate() : null); // Set to null if date_of_birth is NULL

                studentEvaluation.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                studentEvaluation.setGender(Objects.equals(rs.getString("gender"), "1") ? "male" : "female");
                studentEvaluation.setClassName(rs.getString("class_name"));

                return studentEvaluation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Student> getListStudentByClassId(int classId) {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "select * from student s join class c on s.class_id = c.class_ID where c.class_ID = ?";
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

    public boolean createListStudentEvaluation(ArrayList<Student> students, int teacherId, int termId) {
        String sql = "INSERT INTO Student_Evaluation (student_ID, teacher_id, term_id) VALUES (?, ?, ?)";
        boolean success = true; // Flag to track success of all insertions

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Disable auto-commit for batch processing
            conn.setAutoCommit(false);

            for (Student student : students) {
                stmt.setInt(1, student.getStudentId()); // Set student ID
                stmt.setInt(2, teacherId);               // Set teacher ID
                stmt.setInt(3, termId);                  // Set term ID

                stmt.addBatch(); // Add the current statement to the batch
            }

            // Execute the batch
            int[] results = stmt.executeBatch();

            // Check if all inserts were successful
            for (int result : results) {
                if (result == Statement.EXECUTE_FAILED) {
                    success = false; // If any insertion fails, set success to false
                    break;
                }
            }

            // Commit the transaction if all inserts were successful
            if (success) {
                conn.commit();
            } else {
                conn.rollback(); // Rollback if there was any failure
            }
        } catch (SQLException e) {
            e.printStackTrace();
            success = false; // If an exception occurs, set success to false
        }

        return success; // Return true if all inserts were successful, false otherwise
    }


    // Additional methods for creating, updating, or deleting evaluations can be added here
}
