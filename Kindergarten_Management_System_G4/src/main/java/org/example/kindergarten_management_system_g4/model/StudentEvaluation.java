package org.example.kindergarten_management_system_g4.model;

import java.time.LocalDate;

/**
 * Lớp StudentEvaluation đại diện cho đánh giá của học sinh trong hệ thống quản lý nhà trẻ.
 * Chứa thông tin về ID đánh giá, ID học sinh, tên học sinh, xếp hạng, mô tả, ngày đánh giá,
 * ID giáo viên, ID kỳ học, ngày sinh, giới tính và tên lớp.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 */
public class StudentEvaluation {
    private int evaluationId;        // ID của đánh giá
    private int studentId;           // ID của học sinh
    private String studentName;      // Tên học sinh
    private String termName;         // Tên kỳ học
    private String ranking;          // Xếp hạng (dùng String để lưu trữ giá trị ENUM)
    private String description;      // Mô tả chi tiết về đánh giá
    private LocalDate evaluationDate;// Ngày đánh giá
    private int teacherId;           // ID của giáo viên thực hiện đánh giá
    private int termId;              // ID của kỳ học
    private LocalDate dateOfBirth;   // Ngày sinh của học sinh
    private String gender;           // Giới tính của học sinh
    private String className;        // Tên lớp của học sinh

    // Constructor mặc định
    public StudentEvaluation() {
    }

    /**
     * Constructor với tất cả các thuộc tính chính.
     *
     * @param evaluationId ID của đánh giá
     * @param studentId ID của học sinh
     * @param ranking Xếp hạng của học sinh
     * @param description Mô tả chi tiết về đánh giá
     * @param evaluationDate Ngày đánh giá
     * @param teacherId ID của giáo viên thực hiện đánh giá
     */
    public StudentEvaluation(int evaluationId, int studentId, String ranking, String description, LocalDate evaluationDate, int teacherId) {
        this.evaluationId = evaluationId;
        this.studentId = studentId;
        this.ranking = ranking;
        this.description = description;
        this.evaluationDate = evaluationDate;
        this.teacherId = teacherId;
    }

    // Getters và Setters cho các thuộc tính

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "StudentEvaluation{" +
                "evaluationId=" + evaluationId +
                ", studentId=" + studentId +
                ", ranking='" + ranking + '\'' +
                ", description='" + description + '\'' +
                ", evaluationDate=" + evaluationDate +
                ", teacherId=" + teacherId +
                '}';
    }
}
