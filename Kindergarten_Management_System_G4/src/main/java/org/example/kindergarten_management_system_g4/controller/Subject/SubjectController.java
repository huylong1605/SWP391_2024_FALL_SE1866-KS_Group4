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

        String codePattern = "^[A-Za-z]{2}[A-Za-z0-9]{3,4}$";
        if (!subjectCode.matches(codePattern)) {
            session.setAttribute("errorMessage", "Error: Subject code must start with at least 2 letters and max 5-6 characters long.");
            response.sendRedirect("subject");
            return;
        }

        String namePattern = "^[A-Za-z\\s]+$";
        if (!subjectName.matches(namePattern)) {
            session.setAttribute("errorMessage", "Error: Subject name should not contain numbers.");
            response.sendRedirect("subject");
            return;
        }

        if (Integer.parseInt(userId) <= 0) {
            session.setAttribute("errorMessage", "Error: ID user must be a interger number.");
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
        String codePattern = "^[A-Za-z]{2}[A-Za-z0-9]{3,4}$";
        if (!subjectCode.matches(codePattern)) {
            session.setAttribute("errorMessage", "Error: Subject code must start with at least 2 letters and max 5-6 characters long !");
            response.sendRedirect("subject");
            return;
        }

        String namePattern = "^[A-Za-z\\s]+$";
        if (!subjectName.matches(namePattern)) {
            session.setAttribute("errorMessage", "Subject name should not contain numbers.");
            response.sendRedirect("subject");
            return;
        }

        if (userId  <= 0) {
            session.setAttribute("errorMessage", "Error: ID user must be a interger number.");
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
