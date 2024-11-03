package org.example.kindergarten_management_system_g4.controller.classManagement;

/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                          DESCRIPTION
 * 12/10/2024       1.1              Đào Xuân Bình - HE163115        Update Extracurricular Activity
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ExtracurricularActivityUpdateController handles HTTP POST requests to update an activity.
 * It retrieves activity details from the request, performs validation, and updates the activity in the database.
 */
@WebServlet(name = "updateActivity", value = "/updateActivity")
public class ExtracurricularActivityUpdateController extends HttpServlet {

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
     * Handle POST requests to update an activity.
     * Retrieves activity details from the form and updates the corresponding activity in the database.
     *
     * @param req  HttpServletRequest containing the client request
     * @param resp HttpServletResponse containing the servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error is detected while processing the request
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int activityId = Integer.parseInt(req.getParameter("activityId"));
        String activityName = req.getParameter("activityName");
        String description = req.getParameter("description");
        String location = req.getParameter("location");
        String materialsNeeded = req.getParameter("materialsNeeded");
        String status = req.getParameter("status");

        // Parse start and end times
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(req.getParameter("startTime"), timeFormat);
        LocalTime endTime = LocalTime.parse(req.getParameter("endTime"), timeFormat);

        try {
            // Set all fields in the activity object
            ExtracurricularActivities activity = new ExtracurricularActivities();
            activity.setActivity_id(activityId);
            activity.setActivity_name(activityName);
            activity.setDescription(description);
            activity.setLocation(location);
            activity.setMaterials_needed(materialsNeeded);
            activity.setStatus(status);
            activity.setStart_time(startTime);
            activity.setEnd_time(endTime);

            // Update the activity in the database
            extracurricularActivitiesDAO.updateActivity(activity);

            // Redirect with success message
            resp.sendRedirect(req.getContextPath() + "/view-extracurricular-activities?success=true");
        } catch (SQLException e) {
            e.printStackTrace(); // Print the SQLException
            resp.sendRedirect("error.jsp"); // Redirect to error page in case of an SQL exception
        }
    }
}
