package org.example.kindergarten_management_system_g4.controller.Term;

import org.example.kindergarten_management_system_g4.dao.TermDAO.TermDAO;
import org.example.kindergarten_management_system_g4.model.Term;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "TermController", value = "/term")
public class termController extends HttpServlet {
    private TermDAO termDAO;

    @Override
    public void init() throws ServletException {
        termDAO = new TermDAO();
    }

    /**
     * Handles GET requests to perform operations such as deleting a term or displaying the list of terms.
     *
     * @param request HttpServletRequest object containing the client's request data
     * @param response HttpServletResponse object used to send responses back to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException if an input or output error occurs while handling the request
     */
    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//        if (action != null) {
//            switch (action) {
//                case "delete":
//                    int termId = Integer.parseInt(request.getParameter("termId"));
//                    termDAO.deleteTerm(termId);
//                    response.sendRedirect("term?success");
//                    break;
//            }
//        } else {
//            List<Term> termList = termDAO.getAllTerms();
//            request.setAttribute("termList", termList);
//            request.getRequestDispatcher("term-manage.jsp").forward(request, response);
//        }
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the list of terms
        List<Term> termList = termDAO.getAllTerms();

        // Set the term list as an attribute to be accessed in the JSP
        request.setAttribute("termList", termList);

        // Forward the request and response to the JSP page
        request.getRequestDispatcher("term-manage.jsp").forward(request, response);
    }




    /**
     * Handles POST requests to perform operations such as adding, updating, or filtering terms.
     *
     * @param request HttpServletRequest object containing the client's request data
     * @param response HttpServletResponse object used to send responses back to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException if an input or output error occurs while handling the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addTerm(request, response);
                    break;
                case "update":
                    updateTerm(request, response);
                    break;
                case "filter":
                    filterTerm(request, response);
                    break;
                case "delete":
                    deleteTerm(request, response);
                    break;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    /**
     * Adds a new term using the data provided in the request and redirects to the success page if successful.
     *
     * @param request HttpServletRequest object containing the client's request data
     * @param response HttpServletResponse object used to send responses back to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException if an input or output error occurs while handling the request
     */
    private void addTerm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String termName = request.getParameter("termName");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        int year = Integer.parseInt(request.getParameter("year"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;

        try {
            startDate = dateFormat.parse(startDateStr);
            endDate = dateFormat.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format.");
            return;
        }

        Term newTerm = new Term(0, termName, startDate, endDate, year);
        boolean result = termDAO.createTerm(newTerm);
        if(result) {
            response.sendRedirect("term?add_success=Add successfully");
        } else {
            response.sendRedirect("term?add_error=Add failed");
        }

    }


    /**
     * Updates an existing term using the data provided in the request and redirects to the success page if successful.
     *
     * @param request HttpServletRequest object containing the client's request data
     * @param response HttpServletResponse object used to send responses back to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException if an input or output error occurs while handling the request
     */
    protected void updateTerm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // In ra dữ liệu để kiểm tra
            System.out.println("termId: " + request.getParameter("termId"));
            System.out.println("termName: " + request.getParameter("termName"));
            System.out.println("startDate: " + request.getParameter("startDate"));
            System.out.println("endDate: " + request.getParameter("endDate"));
            System.out.println("year: " + request.getParameter("year"));

            int termId = Integer.parseInt(request.getParameter("termId"));
            String termName = request.getParameter("termName");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            int year = Integer.parseInt(request.getParameter("year"));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate;
            Date endDate;

            // Kiểm tra và xử lý các trường hợp lỗi ngày tháng
            try {
                startDate = dateFormat.parse(startDateStr);
                endDate = dateFormat.parse(endDateStr);
            } catch (ParseException e) {
                System.out.println("Error parsing" + e);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("error: invalid date format");
                return;
            }

            Term term = new Term(termId, termName, startDate, endDate, year);
            boolean result = termDAO.updateTerm(term);

            response.setCharacterEncoding("UTF-8");
            if (result) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("error");
            }
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("error: " + e.getMessage());
        }
    }


    /**
     * Filters terms based on the provided criteria and forwards the filtered list to the term management page.
     *
     * @param request HttpServletRequest object containing the client's request data
     * @param response HttpServletResponse object used to send responses back to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException if an input or output error occurs while handling the request
     */
    private void filterTerm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String termName = request.getParameter("termName");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String year = request.getParameter("year");

        List<Term> filteredTerms = termDAO.filterTerms(termName, startDate, endDate, year);
        request.setAttribute("termList", filteredTerms);
        request.getRequestDispatcher("term-manage.jsp").forward(request, response);
    }

    /**
     * Deletes a term using the term ID provided in the request.
     *
     * @param request HttpServletRequest object containing the client's request data
     * @param response HttpServletResponse object used to send responses back to the client
     * @throws ServletException if an error occurs during request processing
     * @throws IOException if an input or output error occurs while handling the request
     */
    private void deleteTerm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int termId = Integer.parseInt(request.getParameter("termId"));
        boolean isDeleted = termDAO.deleteTerm(termId);

        if (isDeleted) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("error");
        }
    }

}
