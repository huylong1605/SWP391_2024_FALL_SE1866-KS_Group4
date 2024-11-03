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

@WebServlet("/teacher/evaluations/*")
public class TeacherStudentEvaluation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentEvaluationDAO studentEvaluationDAO = new StudentEvaluationDAO();
    private ApplicationDAO applicationDAO = new ApplicationDAO();
    private TermDAO termDAO = new TermDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        } else if (user.getRoleId() != 2) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String action = request.getPathInfo();

        if ("/list".equals(action)) {
            List<Classes> classes = applicationDAO.getClassesByTeacherId(user.getUserID());
            List<Term> terms = termDAO.getAllTerms();
            if (request.getParameter("classId") != null || request.getParameter("termId") != null) {
                int classId = Integer.parseInt(request.getParameter("classId"));
                int termId = Integer.parseInt(request.getParameter("termId"));
                List<StudentEvaluation> evaluations = studentEvaluationDAO.getAllEvaluationsByClassAndTerm(classId, termId);
                ArrayList<Student> students = studentEvaluationDAO.getListStudentByClassId(classId);
                System.out.println("student "+students.size());
                System.out.println("evaluation "+evaluations.size());
                if (evaluations.size() != students.size()) {
                    System.out.println("Student evaluation true");
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

                // Generate Excel file using a library like Apache POI or similar
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=Evaluations.xlsx");

                // Create the workbook and sheet
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Evaluations");

                // Create header row
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

                // Fill the sheet with evaluation data
                int rowCount = 1;
                for (StudentEvaluation evaluation : evaluations) {
                    Row row = sheet.createRow(rowCount++);
                    row.createCell(0).setCellValue(rowCount - 1); // Số thứ tự
                    row.createCell(1).setCellValue(evaluation.getStudentName());
                    row.createCell(2).setCellValue(evaluation.getDateOfBirth().toString());
                    row.createCell(3).setCellValue(evaluation.getGender());
                    row.createCell(4).setCellValue(evaluation.getRanking());
                    row.createCell(5).setCellValue(evaluation.getDescription());
                    // Assuming 'evaluation' is your StudentEvaluation object
                    LocalDate evaluationDate = evaluation.getEvaluationDate();
                    if (evaluationDate != null) {
                        row.createCell(6).setCellValue(evaluationDate.toString()); // Convert to String only if not null
                    } else {
                        row.createCell(6).setCellValue(""); // Set to an empty string or some default value if evaluationDate is null
                    }
                    row.createCell(7).setCellValue(evaluation.getTermName()); // Thêm tên kỳ
                    row.createCell(8).setCellValue(evaluation.getTeacherId()); // Thêm ID giáo viên
                }


                // Write the workbook to the response output stream
                workbook.write(response.getOutputStream());
                workbook.close();
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    // You can implement doPost if you need to handle any form submissions related to evaluations
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if ("/update-evaluation".equals(action)) {
            int evaluationId = Integer.parseInt(request.getParameter("id"));
            String ranking = request.getParameter("ranking");
            String description = request.getParameter("description");

            // Lấy đối tượng StudentEvaluation
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

            // Lấy lại dữ liệu sau khi cập nhật
            StudentEvaluation evaluationUpdated = studentEvaluationDAO.getEvaluationById(evaluationId);
            request.setAttribute("evaluation", evaluationUpdated);

            // Chuyển hướng tới trang chi tiết hoặc form chỉnh sửa
            request.getRequestDispatcher("/Views/StudentEvaluation/TeacherStudentEvaluationDetail.jsp").forward(request, response);
        }
    }
}
