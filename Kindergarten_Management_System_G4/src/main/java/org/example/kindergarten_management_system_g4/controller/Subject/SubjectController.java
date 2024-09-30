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


        // Perform filtering based on the provided parameters
        List<Subject> filteredsubjectList = subjectDAO.getAllSubjects();

        // Forward the filtered subject list and pagination parameters to the JSP
        request.setAttribute("subjectList", filteredsubjectList);
        request.getRequestDispatcher("subject-manage.jsp").forward(request, response);
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
            // Handle missing action parameter
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void addsubject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the form values
        String subjectCode = request.getParameter("subjectCode");
        String subjectName = request.getParameter("subjectName");
        String description = request.getParameter("description");

        Subject newSubject = new Subject();
        newSubject.setSubjectCode(subjectCode);
        newSubject.setSubjectName(subjectName);
        newSubject.setDescription(description);


        boolean success = subjectDAO.createSubject(newSubject);

        if (success) {
            // Redirect to subject list page after successful addition
            response.sendRedirect("subject?success");
        } else {
            response.sendRedirect("subject?fail");
        }
    }

    private void updatesubject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        String subjectCode = request.getParameter("subjectCode");
        String subjectName = request.getParameter("subjectName");
        String description = request.getParameter("description");
        int userId = Integer.parseInt(request.getParameter("userId"));
        Subject subject = new Subject(subjectId, subjectCode, subjectName, description, userId);

        // Update the subject
        boolean success = subjectDAO.updateSubject(subject);
        if (success) {
            // Redirect to subject list page after successful update
            response.sendRedirect("subject?success");
        } else {
            // Handle update failure
            response.sendRedirect("subject?fail");
        }
    }
}