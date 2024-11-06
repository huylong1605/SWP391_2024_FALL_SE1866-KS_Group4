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

@WebServlet(name = "ParentSubjectController", value = "/parent-subject")
public class ParentSubjectController extends HttpServlet {
    private SubjectDAO subjectDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        subjectDAO = new SubjectDAO();
    }

    /**
     * Handles GET requests to fetch the list of subjects for the parent role and display them on the ParentSubjectList JSP page.
     *
     * @param request HttpServletRequest object containing the client's request data
     * @param response HttpServletResponse object used to send responses back to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException if an input or output error occurs while handling the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Subject> filteredsubjectList = subjectDAO.parentGetAllSubjects();
        request.setAttribute("subjectList", filteredsubjectList);
        request.getRequestDispatcher("ParentSubjectList.jsp").forward(request, response);
    }
}
