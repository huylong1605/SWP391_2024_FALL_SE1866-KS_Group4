/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 10/1/2024       1.1                     Vũ Gia Huy                      Initial creation of ParentStudentEvaluation
 */

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

/**
 * Lớp ParentStudentEvaluation xử lý các yêu cầu liên quan đến đánh giá học sinh dành cho phụ huynh.
 * Lớp này cho phép phụ huynh xem danh sách đánh giá và xuất báo cáo học bạ dưới dạng file Word.
 *
 * <p>Lỗi: Không có lỗi nào được biết đến</p>
 *
 * @see StudentEvaluationDAO
 * @see TermDAO
 */
@WebServlet("/student/evaluations/*")
public class ParentStudentEvaluation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentEvaluationDAO studentEvaluationDAO = new StudentEvaluationDAO(); // Đối tượng DAO để thao tác với đánh giá học sinh
    private TermDAO termDAO = new TermDAO(); // Đối tượng DAO để thao tác với kỳ học

    /**
     * Xử lý các yêu cầu GET từ phụ huynh, cho phép phụ huynh xem danh sách đánh giá
     * của học sinh hoặc xuất báo cáo học bạ dưới dạng file Word.
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @throws ServletException nếu có lỗi servlet xảy ra
     * @throws IOException nếu có lỗi vào/ra xảy ra
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Kiểm tra xem người dùng đã đăng nhập hay chưa và có quyền phụ huynh hay không
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        } else if (user.getRoleId() != 3) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String action = request.getPathInfo();

        if ("/list".equals(action)) {
            List<Term> terms = termDAO.getAllTerms(); // Lấy danh sách các kỳ học
            if (request.getParameter("termId") != null) {
                int termId = Integer.parseInt(request.getParameter("termId"));
                List<StudentEvaluation> evaluations = studentEvaluationDAO.getAllEvaluationsByUserIdAndTerm(user.getUserID(), termId);
                request.setAttribute("evaluations", evaluations); // Đặt danh sách đánh giá vào thuộc tính request
                request.setAttribute("termIdSelected", termId); // Đặt kỳ học đã chọn vào thuộc tính request
            }
            request.setAttribute("terms", terms); // Đặt danh sách kỳ học vào thuộc tính request
            request.getRequestDispatcher("/Views/StudentEvaluation/StudentEvaluation.jsp").forward(request, response); // Chuyển tiếp đến trang đánh giá học sinh
        }

        if ("/export".equals(action)) {
            int termId = Integer.parseInt(request.getParameter("termId")); // Lấy ID kỳ học từ yêu cầu
            exportToWord(request, response, user.getUserID(), termId); // Xuất báo cáo học bạ dưới dạng file Word
        }
    }

    /**
     * Xuất báo cáo học bạ của học sinh dưới dạng file Word
     *
     * @param request yêu cầu HTTP từ người dùng
     * @param response phản hồi HTTP để gửi lại người dùng
     * @param userId ID của người dùng (phụ huynh)
     * @param termId ID của kỳ học
     * @throws IOException nếu có lỗi vào/ra xảy ra trong quá trình xuất file
     */
    private void exportToWord(HttpServletRequest request, HttpServletResponse response, int userId, int termId) throws IOException {
        List<StudentEvaluation> evaluations = studentEvaluationDAO.getAllEvaluationsByUserIdAndTerm(userId, termId);
        StudentEvaluation evaluation = evaluations.get(0); // Lấy đánh giá đầu tiên (giả sử chỉ có một đánh giá)

        // Tạo tài liệu Word mới
        XWPFDocument document = new XWPFDocument();

        // Tiêu đề tài liệu
        XWPFParagraph titleParagraph = document.createParagraph();
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText("HỌC BẠ KỲ HỌC " + evaluation.getTermName());
        titleRun.setBold(true);
        titleRun.setFontSize(20);
        titleRun.setFontFamily("Times New Roman");
        titleRun.addBreak();

        // Phần chào
        XWPFParagraph greetingParagraph = document.createParagraph();
        greetingParagraph.createRun().setText("Kính gửi Quý Phụ Huynh,");
        greetingParagraph.createRun().addBreak();

        // Giới thiệu nội dung
        XWPFParagraph introParagraph = document.createParagraph();
        introParagraph.createRun().setText("Chúng tôi xin trân trọng gửi đến Quý Phụ Huynh báo cáo nội dung học tập của học sinh trong kỳ học này. Dưới đây là thông tin chi tiết về kết quả đánh giá của học sinh:");
        introParagraph.createRun().addBreak();

        // Thông tin chi tiết về đánh giá
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

        // Phần kết luận
        XWPFParagraph conclusionParagraph = document.createParagraph();
        conclusionParagraph.createRun().setText("Chúng tôi rất mong nhận được sự hỗ trợ và hợp tác của Quý Phụ Huynh trong việc phát triển và nâng cao chất lượng giáo dục cho học sinh. Xin chân thành cảm ơn Quý Phụ Huynh đã dành thời gian quan tâm đến sự tiến bộ của học sinh.");

        // Phần kết thúc
        XWPFParagraph closingParagraph = document.createParagraph();
        closingParagraph.createRun().setText("Trân trọng,");
        closingParagraph.createRun().addBreak();
        closingParagraph.createRun().setText("Ban Giám Hiệu Trường Mầm Non XYZ");

        // Thiết lập loại nội dung và tiêu đề cho phản hồi HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=\"bao_cao_hoc_tap.docx\"");

        // Ghi tài liệu vào luồng đầu ra
        try (OutputStream out = response.getOutputStream()) {
            document.write(out);
        }
        document.close();
    }
}
