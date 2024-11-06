/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/1/2024       1.1                     Vũ Gia Huy                      Initial creation of TeacherStudentEvaluation
 */

package org.example.kindergarten_management_system_g4.controller.StudentEvaluation;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.kindergarten_management_system_g4.dao.ApplicationDAO.ApplicationDAO;
import org.example.kindergarten_management_system_g4.dao.StudentEvaluationDAO.StudentEvaluationDAO;
import org.example.kindergarten_management_system_g4.dao.TermDAO.TermDAO;
import org.example.kindergarten_management_system_g4.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp TeacherStudentEvaluation xử lý các yêu cầu liên quan đến đánh giá học sinh dành cho giáo viên.
 * Lớp này cho phép giáo viên xem danh sách đánh giá, xem chi tiết và xuất báo cáo đánh giá dưới dạng file Excel.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * @see StudentEvaluationDAO
 * @see TermDAO
 * @see ApplicationDAO
 */
@WebServlet("/teacher/evaluations/*")
public class TeacherStudentEvaluation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentEvaluationDAO studentEvaluationDAO = new StudentEvaluationDAO(); // Đối tượng DAO để thao tác với đánh giá học sinh
    private ApplicationDAO applicationDAO = new ApplicationDAO(); // Đối tượng DAO để thao tác với lớp học
    private TermDAO termDAO = new TermDAO(); // Đối tượng DAO để thao tác với kỳ học

    /**
     * Xử lý các yêu cầu GET từ giáo viên, cho phép xem danh sách đánh giá, chi tiết đánh giá,
     * và xuất báo cáo đánh giá dưới dạng file Excel.
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws ServletException nếu có lỗi servlet xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Kiểm tra xem người dùng đã đăng nhập và có quyền giáo viên hay không
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        } else if (user.getRoleId() != 2) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String action = request.getPathInfo();

        if ("/list".equals(action)) {
            // Hiển thị danh sách đánh giá cho giáo viên
            List<Classes> classes = applicationDAO.getClassesByTeacherId(user.getUserID());
            List<Term> terms = termDAO.getAllTerms();
            if (request.getParameter("classId") != null || request.getParameter("termId") != null) {
                int classId = Integer.parseInt(request.getParameter("classId"));
                int termId = Integer.parseInt(request.getParameter("termId"));
                List<StudentEvaluation> evaluations = studentEvaluationDAO.getAllEvaluationsByClassAndTerm(classId, termId);
                ArrayList<Student> students = studentEvaluationDAO.getListStudentByClassId(classId);

                // Tạo danh sách đánh giá nếu chưa có
                if (evaluations.size() != students.size()) {
                    studentEvaluationDAO.createListStudentEvaluation(students, user.getUserID(), termId);
                    evaluations = studentEvaluationDAO.getAllEvaluationsByClassAndTerm(classId, termId);
                }
                request.setAttribute("evaluations", evaluations);
                request.setAttribute("classIdSelected", classId);
                request.setAttribute("termIdSelected", termId);
            }
            request.setAttribute("terms", terms);
            request.setAttribute("classes", classes);
            request.getRequestDispatcher("/Views/StudentEvaluation/TeacherStudentEvaluationList.jsp").forward(request, response);
        } else if (action != null && action.startsWith("/detail")) {
            // Xem chi tiết một đánh giá
            int evaluationId = Integer.parseInt(action.split("/")[2]);
            StudentEvaluation evaluation = studentEvaluationDAO.getEvaluationById(evaluationId);
            request.setAttribute("evaluation", evaluation);
            request.getRequestDispatcher("/Views/StudentEvaluation/TeacherStudentEvaluationDetail.jsp").forward(request, response);
        }

        if ("/export".equals(action)) {
            try {
                int classId = Integer.parseInt(request.getParameter("classId"));
                int termId = Integer.parseInt(request.getParameter("termId"));
                List<StudentEvaluation> evaluations = studentEvaluationDAO.getAllEvaluationsByClassAndTerm(classId, termId);

                // Thiết lập loại nội dung và tiêu đề cho phản hồi HTTP để xuất file Excel
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=Evaluations.xlsx");

                // Tạo workbook và sheet
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Evaluations");

                // Tạo hàng tiêu đề
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("S/N"); // Cột số thứ tự
                headerRow.createCell(1).setCellValue("Student Name");
                headerRow.createCell(2).setCellValue("Date of Birth");
                headerRow.createCell(3).setCellValue("Gender");
                headerRow.createCell(4).setCellValue("Ranking");
                headerRow.createCell(5).setCellValue("Description");
                headerRow.createCell(6).setCellValue("Evaluation Date");
                headerRow.createCell(7).setCellValue("Term Name"); // Cột tên kỳ
                headerRow.createCell(8).setCellValue("Teacher ID"); // Cột ID giáo viên

                // Điền dữ liệu đánh giá vào sheet
                int rowCount = 1;
                for (StudentEvaluation evaluation : evaluations) {
                    Row row = sheet.createRow(rowCount++);
                    row.createCell(0).setCellValue(rowCount - 1); // Số thứ tự
                    row.createCell(1).setCellValue(evaluation.getStudentName());
                    row.createCell(2).setCellValue(evaluation.getDateOfBirth().toString());
                    row.createCell(3).setCellValue(evaluation.getGender());
                    row.createCell(4).setCellValue(evaluation.getRanking());
                    row.createCell(5).setCellValue(evaluation.getDescription());
                    LocalDate evaluationDate = evaluation.getEvaluationDate();
                    row.createCell(6).setCellValue(evaluationDate != null ? evaluationDate.toString() : ""); // Ngày đánh giá
                    row.createCell(7).setCellValue(evaluation.getTermName()); // Tên kỳ học
                    row.createCell(8).setCellValue(evaluation.getTeacherId()); // ID giáo viên
                }

                // Ghi workbook vào luồng đầu ra
                workbook.write(response.getOutputStream());
                workbook.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Xử lý các yêu cầu POST từ giáo viên, cho phép giáo viên cập nhật đánh giá của học sinh.
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws ServletException nếu có lỗi servlet xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if ("/update-evaluation".equals(action)) {
            // Cập nhật đánh giá của học sinh
            int evaluationId = Integer.parseInt(request.getParameter("id"));
            String ranking = request.getParameter("ranking");
            String description = request.getParameter("description");

            StudentEvaluation evaluation = new StudentEvaluation();
            evaluation.setEvaluationId(evaluationId);
            evaluation.setRanking(ranking);
            evaluation.setDescription(description);
            evaluation.setEvaluationDate(LocalDate.now());

            boolean isUpdated = studentEvaluationDAO.updateEvaluation(evaluation);
            if (isUpdated) {
                request.setAttribute("message", "Evaluation updated successfully.");
            } else {
                request.setAttribute("message", "Failed to update evaluation.");
            }

            // Lấy lại dữ liệu sau khi cập nhật và chuyển tiếp tới trang chi tiết
            StudentEvaluation evaluationUpdated = studentEvaluationDAO.getEvaluationById(evaluationId);
            request.setAttribute("evaluation", evaluationUpdated);
            request.getRequestDispatcher("/Views/StudentEvaluation/TeacherStudentEvaluationDetail.jsp").forward(request, response);
        }
    }
}
