package org.example.kindergarten_management_system_g4.controller.classManagement;

/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115        Delete Extracurricular Activity
 */
import org.example.kindergarten_management_system_g4.dao.ActivityDAO.ExtracurricularActivitiesDAO;
import org.example.kindergarten_management_system_g4.dao.ActivityDAO.implement.ExtracurricularActivitiesDAOImpl;
import org.example.kindergarten_management_system_g4.model.ExtracurricularActivities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * ExtracurricularActivityDeleteController handles HTTP POST requests to delete an activity.
 * The activity ID to delete is taken from the form and removed from the database if it is not in use.
 */
@WebServlet(name = "deleteActivity", value = "/deleteActivity")
public class ExtracurricularActivityDeleteController extends HttpServlet {

    private ExtracurricularActivitiesDAO extracurricularActivitiesDAO;

    /**
     * Initialize the controller by setting up the Activity DAO.
     * This method is called once when the servlet is first created.
     *
     * @throws ServletException if there’s an error during initialization.
     */
    @Override
    public void init() throws ServletException {
        extracurricularActivitiesDAO = new ExtracurricularActivitiesDAOImpl(); // Initialize DAO for Extracurricular Activities
    }

    /**
     * Handle POST requests to delete an activity.
     * The activity ID to delete is obtained from the form, and then the delete method is called in the DAO.
     * If the activity is in use, it redirects with an error message.
     *
     * @param req  HttpServletRequest containing the client request
     * @param resp HttpServletResponse containing the servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error is detected while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("alo alo");
        int activityId = Integer.parseInt(req.getParameter("classLevelId")); // Get the activity ID from the form
        System.out.println("hahaha"+activityId);
        try {
            // Directly delete the activity
            extracurricularActivitiesDAO.deleteActivity(activityId);
            // Redirect to success message after deletion
            resp.sendRedirect(req.getContextPath() + "/view-extracurricular-activities?success=true");
        } catch (SQLException e) {
            e.printStackTrace(); // Print the SQLException for debugging
            // Redirect to error page in case of an SQL exception
            resp.sendRedirect(req.getContextPath() + "/view-extracurricular-activities?success=false");
        }
    }
}
