package org.example.kindergarten_management_system_g4.controller.StudentEvaluation;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.example.kindergarten_management_system_g4.dao.StudentEvaluationDAO.StudentEvaluationDAO;
import org.example.kindergarten_management_system_g4.dao.TermDAO.TermDAO;
import org.example.kindergarten_management_system_g4.model.StudentEvaluation;
import org.example.kindergarten_management_system_g4.model.Term;
import org.example.kindergarten_management_system_g4.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/student/evaluations/*")
public class ParentStudentEvaluation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentEvaluationDAO studentEvaluationDAO = new StudentEvaluationDAO();
    private TermDAO termDAO = new TermDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        } else if (user.getRoleId() != 3) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String action = request.getPathInfo();

        if ("/list".equals(action)) {
            List<Term> terms = termDAO.getAllTerms();
            if (request.getParameter("termId") != null) {
                int termId = Integer.parseInt(request.getParameter("termId"));
                List<StudentEvaluation> evaluations = studentEvaluationDAO.getAllEvaluationsByUserIdAndTerm(user.getUserID(), termId);
                request.setAttribute("evaluations", evaluations);
                request.setAttribute("termIdSelected", termId);
            }
            request.setAttribute("terms", terms);
            request.getRequestDispatcher("/Views/StudentEvaluation/StudentEvaluation.jsp").forward(request, response);
        }
        if ("/export".equals(action)) {
            int termId = Integer.parseInt(request.getParameter("termId"));
            exportToWord(request, response, user.getUserID(), termId);
        }
    }

    private void exportToWord(HttpServletRequest request, HttpServletResponse response, int userId, int termId) throws IOException {
        List<StudentEvaluation> evaluations = studentEvaluationDAO.getAllEvaluationsByUserIdAndTerm(userId, termId);
        StudentEvaluation evaluation = evaluations.get(0);

        // Create a new Word document
        XWPFDocument document = new XWPFDocument();

        // Title
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText("HỌC BẠ KỲ HỌC " + evaluation.getTermName());
        titleRun.setBold(true);
        titleRun.setFontSize(20);
        titleRun.setFontFamily("Times New Roman");
        titleRun.addBreak();

        // Greeting
        XWPFParagraph greetingParagraph = document.createParagraph();
        greetingParagraph.createRun().setText("Kính gửi Quý Phụ Huynh,");
        greetingParagraph.createRun().addBreak(); // Cannot resolve method 'addBreak' in 'XWPFParagraph

        // Introduction
        XWPFParagraph introParagraph = document.createParagraph();
        introParagraph.createRun().setText("Chúng tôi xin trân trọng gửi đến Quý Phụ Huynh báo cáo nội dung học tập của học sinh trong kỳ học này. Dưới đây là thông tin chi tiết về kết quả đánh giá của học sinh:");
        introParagraph.createRun().addBreak(); // Cannot resolve method 'addBreak' in 'XWPFParagraph'

        // Detailed evaluation information
        XWPFParagraph evalParagraph = document.createParagraph();
        evalParagraph.createRun().setText("Tên học sinh:  " + evaluation.getStudentName());
        evalParagraph.createRun().addBreak();
        evalParagraph.createRun().setText("Ngày sinh:  " + evaluation.getDateOfBirth());
        evalParagraph.createRun().addBreak();
        evalParagraph.createRun().setText("Giới tính:  " + evaluation.getGender());
        evalParagraph.createRun().addBreak();
        evalParagraph.createRun().setText("Xếp hạng:  " + evaluation.getRanking());
        evalParagraph.createRun().addBreak();
        evalParagraph.createRun().setText("Mô tả:  " + evaluation.getDescription());
        evalParagraph.createRun().addBreak();
        evalParagraph.createRun().setText("Ngày đánh giá:  " + evaluation.getEvaluationDate());
        evalParagraph.createRun().addBreak();
        evalParagraph.createRun().addBreak();


        // Conclusion
        XWPFParagraph conclusionParagraph = document.createParagraph();
        conclusionParagraph.createRun().setText("Chúng tôi rất mong nhận được sự hỗ trợ và hợp tác của Quý Phụ Huynh trong việc phát triển và nâng cao chất lượng giáo dục cho học sinh. Xin chân thành cảm ơn Quý Phụ Huynh đã dành thời gian quan tâm đến sự tiến bộ của học sinh.");

        // Closing
        XWPFParagraph closingParagraph = document.createParagraph();
        closingParagraph.createRun().setText("Trân trọng,");
        closingParagraph.createRun().addBreak();
        closingParagraph.createRun().setText("Ban Giám Hiệu Trường Mầm Non XYZ");

        // Set the response content type and headers
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=\"bao_cao_hoc_tap.docx\"");

        // Write the document to the output stream
        try (OutputStream out = response.getOutputStream()) {
            document.write(out);
        }
        document.close();
    }

}