package org.example.kindergarten_management_system_g4.controller.Term;

import org.example.kindergarten_management_system_g4.dao.TermDAO.TermDAO;
import org.example.kindergarten_management_system_g4.model.Term;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ParentTermController", value = "/parent-term")
public class ParentTermController extends HttpServlet {
    private TermDAO termDAO;

    @Override
    public void init() throws ServletException {
        termDAO = new TermDAO();
    }

    /**
     * Handles GET requests to fetch the list of terms and display them on the ParentTermList JSP page.
     *
     * @param request HttpServletRequest object containing the client's request data
     * @param response HttpServletResponse object used to send responses back to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException if an input or output error occurs while handling the request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the list of terms
        List<Term> termList = termDAO.getAllTerms();

        // Set the term list as an attribute to be accessed in the JSP
        request.setAttribute("termList", termList);

        // Forward the request and response to the JSP page
        request.getRequestDispatcher("ParentTermList.jsp").forward(request, response);
    }
}
