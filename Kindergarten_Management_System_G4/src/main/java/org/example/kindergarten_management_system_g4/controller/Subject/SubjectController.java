package org.example.kindergarten_management_system_g4.controller.Subject;

import org.example.kindergarten_management_system_g4.dao.SubjectDAO.SubjectDAO;
import org.example.kindergarten_management_system_g4.model.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Determine action (add or update)
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addsubject(request, response);
                    break;
                case "update":
                    updatesubject(request, response);
                    break;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void addsubject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String subjectCode = request.getParameter("subjectCode");
        String subjectName = request.getParameter("subjectName");
        String description = request.getParameter("description");
        String userId = request.getParameter("userId");
        String status = request.getParameter("status");

        // Check kí tự đúng form
        // yc at least 1 char
        String codePattern = "^[A-Z]{2}[A-Za-z0-9]{3,4}$";
        String namePattern = "^[A-Za-z\\s]+$";
        String descriptionPattern = "^[^!@#$%^&*]+$";

        // Ktra kí tự khi nhập
        if (!subjectCode.matches(codePattern)) {
            session.setAttribute("errorMessage", "Alert: Subject code must start with at least 2 uppercase letters, max 6 characters and should not contain space or special characters!");
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

        // Clean khoảng trắng cho subject name
        String cleanedSubjectName = subjectName.trim().replaceAll("\\s+", " ");
        subjectName = cleanedSubjectName;


        // Kiểm tra trùng lặp subjectCode và subjectName

        boolean isDuplicateCode = subjectDAO.checkDuplicateSubjectCode(subjectCode);
        boolean isDuplicateName = subjectDAO.checkDuplicateSubjectName(subjectName);

        if (isDuplicateCode) {
            session.setAttribute("errorMessage", "Alert: Subject code already exists!");
            response.sendRedirect("subject");
            return;
        }

        if (isDuplicateName) {
            session.setAttribute("errorMessage", "Alert: Subject name already exists!");
            response.sendRedirect("subject");
            return;
        }

        // Ktra description
        if (!description.matches(descriptionPattern)) {
            session.setAttribute("errorMessage", "Alert: Description contains special characters!");
            response.sendRedirect("subject");
            return;
        }

        // Clean khoảng trắng cho description
        description = description.trim().replaceAll("\\s+", " ");

        // Check UserID > 0
        if (Integer.parseInt(userId) < 0 ) {
            session.setAttribute("errorMessage", "Alert: ID user must be a integer number !");
            response.sendRedirect("subject");
            return;
        }



        Subject newSubject = new Subject();
        newSubject.setSubjectCode(subjectCode);
        newSubject.setSubjectName(subjectName);
        newSubject.setDescription(description);
        newSubject.setUserId(Integer.parseInt(userId));
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

//    -----------------------------------------------------------------------------------------------------

    private void updatesubject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        String subjectCode = request.getParameter("subjectCode");
        String subjectName = request.getParameter("subjectName");
        String description = request.getParameter("description");
        int userId = Integer.parseInt(request.getParameter("userId"));
        String status = request.getParameter("status");
        Subject subject = new Subject(subjectId, subjectCode, subjectName, description, userId);
        subject.setStatus(status);


        String codePattern = "^[A-Z]{2}[A-Za-z0-9]{3,4}$";
        String namePattern = "^[A-Za-z\\s]+$";
        String descriptionPattern = "^[^!@#$%^&*]+$";



        // Ktra kí tự khi nhập
        if (!subjectCode.matches(codePattern)) {
            session.setAttribute("errorMessage", "Alert: Subject code must start with at least 2 uppercase letters, max 6 characters and should not contain space or special characters !");
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

        // Clean khoảng trắng cho subject name
        String cleanedSubjectName = subjectName.trim().replaceAll("\\s+", " ");
        subjectName = cleanedSubjectName;


        // Kiểm tra trùng lặp subjectCode và subjectName
        boolean isDuplicateCode = subjectDAO.checkDuplicateSubjectCode(subjectCode);
        boolean isDuplicateName = subjectDAO.checkDuplicateSubjectName(subjectName);

        if (isDuplicateCode) {
            session.setAttribute("errorMessage", "Alert: Subject code already exists!");
            response.sendRedirect("subject");
            return;
        }

        if (isDuplicateName) {
            session.setAttribute("errorMessage", "Alert: Subject name already exists!");
            response.sendRedirect("subject");
            return;
        }

//         Ktra description
        if (!description.matches(descriptionPattern)) {
            session.setAttribute("errorMessage", "Alert: Description contains special characters!");
            response.sendRedirect("subject");
            return;
        }

        // Clean khoảng trắng cho description
        description = description.trim().replaceAll("\\s+", " ");

        // Check UserID > 0
        if (userId < 0 ) {
            session.setAttribute("errorMessage", "Alert: ID user must be a integer number !");
            response.sendRedirect("subject");
            return;
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
}
