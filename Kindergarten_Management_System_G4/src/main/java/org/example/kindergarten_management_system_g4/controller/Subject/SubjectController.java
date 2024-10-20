package org.example.kindergarten_management_system_g4.controller.Subject;

import org.example.kindergarten_management_system_g4.dao.SubjectDAO.SubjectDAO;
import org.example.kindergarten_management_system_g4.model.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SubjectController", value = "/subject")
public class SubjectController extends HttpServlet {

    private SubjectDAO subjectDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        subjectDAO = new SubjectDAO();
    }

    /**
     * Xử lý các yêu cầu GET để thực hiện các thao tác như xóa môn học hoặc hiển thị danh sách môn học.
     *
     * @param request đối tượng HttpServletRequest chứa dữ liệu yêu cầu
     * @param response đối tượng HttpServletResponse để gửi phản hồi tới khách hàng
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý yêu cầu
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra xảy ra trong khi xử lý yêu cầu
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "delete":
                    String id = request.getParameter("subjectId");
                    subjectDAO.deleteSubject(Integer.parseInt(id));
                    response.sendRedirect("subject?success");
                    break;
            }
        } else {
            List<Subject> filteredsubjectList = subjectDAO.getAllSubjects();
            request.setAttribute("subjectList", filteredsubjectList);
            request.getRequestDispatcher("subject-manage.jsp").forward(request, response);
        }
    }

    /**
     * Xử lý các yêu cầu POST để thêm hoặc cập nhật môn học dựa trên tham số "action" được cung cấp.
     *
     * @param request đối tượng HttpServletRequest chứa dữ liệu yêu cầu
     * @param response đối tượng HttpServletResponse để gửi phản hồi tới khách hàng
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý yêu cầu
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra xảy ra trong khi xử lý yêu cầu
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Determine action (add or update)
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addSubject(request, response);
                    break;
                case "update":
                    updateSubject(request, response);
                    break;
                case "filter":
                    filterSubject(request, response);
                    break;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Thêm một môn học mới vào cơ sở dữ liệu sau khi kiểm tra các điều kiện hợp lệ.
     *
     * @param request đối tượng HttpServletRequest chứa dữ liệu yêu cầu
     * @param response đối tượng HttpServletResponse để gửi phản hồi tới khách hàng
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý yêu cầu
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra xảy ra trong khi xử lý yêu cầu
     */
    private void addSubject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String subjectCode = request.getParameter("subjectCode");
        String subjectName = request.getParameter("subjectName");
        String description = request.getParameter("description");
//        String userId = request.getParameter("userId");
        String status = request.getParameter("status");

        // Check kí tự đúng form
        String codePattern = "^[A-Z]{3}[0-9]{3}$";
        String namePattern = "^[A-Za-z\\s]+$";  // yc at least 1 char
        String descriptionPattern = "^[^!@#$%^&*]+$";


        // Ktra kí tự khi nhập
        if (!subjectCode.matches(codePattern)) {
            session.setAttribute("errorMessage", "Alert: Subject code is invalid \n" +
                    "and Should not contain space or special characters!");
            response.sendRedirect("subject");
            return;
        }

        // Check subjectName k chứa số hoặc kí tự db
        if (!subjectName.matches(namePattern)) {
            session.setAttribute("errorMessage", "Alert: Subject name contain numbers or special characters!!");
            response.sendRedirect("subject");
            return;
        }


        // Check độ dài của subjectName
        if (subjectName.length() > 50) {
            session.setAttribute("errorMessage", "Alert: Subject name should not exceed 50 characters!");
            response.sendRedirect("subject");
            return;
        }



        // Kiểm tra subjectName contains too many spaces
        if (subjectName.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Alert: Subject name contains too many spaces!");
            response.sendRedirect("subject");
            return;
        }


        // Ktra description
        if (!description.matches(descriptionPattern)) {
            session.setAttribute("errorMessage", "Alert: Description contains special characters!");
            response.sendRedirect("subject");
            return;
        }


        // Kiểm tra description contains too many spaces
        if (description.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Alert: Description contains too many spaces!");
            response.sendRedirect("subject");
            return;
        }

        // Check UserID > 0
//        if (Integer.parseInt(userId) < 0 ) {
//            session.setAttribute("errorMessage", "Alert: ID user must be a integer number !");
//            response.sendRedirect("subject");
//            return;
//        }

        // Cắt khoảng trắng
        request.getParameter("subjectName");
        request.getParameter("description");

        if (subjectName != null && description != null) {
            // Cắt bỏ khoảng trắng thừa
            subjectName = subjectName.trim().replaceAll("\\s+", " ");
            description = description.trim().replaceAll("\\s+", " ");

            // Tạo đối tượng Subject và lưu vào csdl
            Subject subject = new Subject();
            subject.setSubjectName(subjectName);
            subject.setDescription(description);

            // Lưu đối tượng subject vào database
            SubjectDAO subjectDAO = new SubjectDAO();
            subjectDAO.save(subject);
        }


        Subject newSubject = new Subject();
        newSubject.setSubjectCode(subjectCode);
        newSubject.setSubjectName(subjectName);
        newSubject.setDescription(description);
//        newSubject.setUserId(Integer.parseInt(userId));
        newSubject.setStatus(status);

        boolean success = false;
        if (subjectDAO.getSubjectByIdCode(0, subjectCode) == null) {
            success = subjectDAO.createSubject(newSubject);
        }

        if (success) {
            response.sendRedirect("subject?success");
        } else {
            response.sendRedirect("subject?fail");
        }
    }


    /**
     * Cập nhật thông tin của một môn học dựa trên dữ liệu nhận được từ yêu cầu.
     *
     * @param request đối tượng HttpServletRequest chứa dữ liệu yêu cầu
     * @param response đối tượng HttpServletResponse để gửi phản hồi tới khách hàng
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý yêu cầu
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra xảy ra trong khi xử lý yêu cầu
     */
    private void updateSubject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        String subjectCode = request.getParameter("subjectCode");
        String subjectName = request.getParameter("subjectName");
        String description = request.getParameter("description");
        int userId = Integer.parseInt(request.getParameter("userId"));
        String status = request.getParameter("status");
        Subject subject = new Subject(subjectId, subjectCode, subjectName, description,status);
        subject.setStatus(status);


        String codePattern = "^[A-Z]{3}[0-9]{3}$";
        String namePattern = "^[A-Za-z\\s]+$";
        String descriptionPattern = "^[^!@#$%^&*]+$";


        // Ktra kí tự khi nhập
        if (!subjectCode.matches(codePattern)) {
            session.setAttribute("errorMessage", " Alert: Subject code is invalid (Ex: KOR311) \n" +
                    "and Should not contain space or special characters!");
            response.sendRedirect("subject");
            return;
        }

        // Check subjectName k chứa số hoặc kí tự db
        if (!subjectName.matches(namePattern)) {
            session.setAttribute("errorMessage", "Alert: Subject name contain numbers or special characters!!");
            response.sendRedirect("subject");
            return;
        }


        // Check độ dài của subjectName
        if (subjectName.length() > 50) {
            session.setAttribute("errorMessage", "Alert: Subject name should not exceed 50 characters!");
            response.sendRedirect("subject");
            return;
        }

        // Kiểm tra subjectName contains too many spaces
        if (subjectName.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Alert: Subject name contains too many spaces!");
            response.sendRedirect("subject");
            return;
        }


//         Ktra description
        if (!description.matches(descriptionPattern)) {
            session.setAttribute("errorMessage", "Alert: Description contains special characters!");
            response.sendRedirect("subject");
            return;
        }

        // Kiểm tra description contains too many spaces
        if (description.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Alert: Description contains too many spaces!");
            response.sendRedirect("subject");
            return;
        }

//         Check UserID > 0
//        if (userId < 0 ) {
//            session.setAttribute("errorMessage", "Alert: ID user must be a integer number !");
//            response.sendRedirect("subject");
//            return;
//        }

        // Cắt khoảng trắng
        request.getParameter("subjectName");
        request.getParameter("description");

        if (subjectName != null && description != null) {
            // Cắt bỏ khoảng trắng thừa
            subjectName = subjectName.trim().replaceAll("\\s+", " ");
            description = description.trim().replaceAll("\\s+", " ");

            // Đặt lại các giá trị cho subject
            subject.setSubjectName(subjectName);
            subject.setDescription(description);

            // Lưu đối tượng subject vào csdl
            subjectDAO.updateSubject(subject);
        }



        // Update the subject
        boolean success = false;
        if (subjectDAO.getSubjectByIdCode(subjectId, subjectCode) == null) {
            success = subjectDAO.updateSubject(subject);
        }
        if (success) {
            // Redirect to subject list page after successful update
            response.sendRedirect("subject?success");
        } else {
            // Handle update failure
            response.sendRedirect("subject?fail");
        }
    }

    /**
     * Lọc danh sách môn học dựa trên các tiêu chí từ form.
     *
     * @param request đối tượng HttpServletRequest chứa dữ liệu yêu cầu
     * @param response đối tượng HttpServletResponse để gửi phản hồi tới khách hàng
     * @throws ServletException nếu có lỗi xảy ra trong quá trình xử lý yêu cầu
     * @throws IOException nếu có lỗi đầu vào hoặc đầu ra xảy ra trong khi xử lý yêu cầu
     */
    private void filterSubject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy các tham số từ form filter
        String subjectCode = request.getParameter("subjectCode");
        String subjectName = request.getParameter("subjectName");
        String status = request.getParameter("status");

        // Trim các giá trị để bỏ khoảng trắng đầu và cuối
        if (subjectCode != null) subjectCode = subjectCode.trim();
        if (subjectName != null) subjectName = subjectName.trim();
        if (status != null) status = status.trim();

        // Gọi phương thức DAO để lấy danh sách môn học đã lọc
        List<Subject> filteredSubjects = subjectDAO.filterSubjects(subjectCode, subjectName, status);

        // Đặt danh sách môn học đã lọc vào request để hiển thị trên giao diện
        request.setAttribute("subjectList", filteredSubjects);

        // Chuyển tiếp tới trang quản lý môn học (subject-manage.jsp)
        request.getRequestDispatcher("subject-manage.jsp").forward(request, response);
    }


}
