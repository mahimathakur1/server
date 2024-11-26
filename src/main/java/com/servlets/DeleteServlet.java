package com.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.database.DBConnection;
import java.util.logging.Logger;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {

    // Logger for debugging
    private static final Logger logger = Logger.getLogger(DeleteServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Return the form for deleting a user
        response.setContentType("text/html");
        response.getWriter().write("<form action='DeleteServlet' method='POST'>" +
                                   "User ID: <input type='text' name='userId'>" +
                                   "<button type='submit'>Delete User</button>" +
                                   "</form>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        
        // Log the received userId
        logger.info("Received userId: " + userId);

        // Validate userId
        if (userId == null || userId.trim().isEmpty()) {
            response.getWriter().write("Error: User ID is required.");
            return;
        }

        int parsedUserId = -1;
        try {
            parsedUserId = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            response.getWriter().write("Error: Invalid User ID format.");
            return;
        }

        // Database connection and delete logic
        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, parsedUserId);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    response.getWriter().write("User Deleted Successfully");
                } else {
                    response.getWriter().write("Failed to Delete User: No user found with the provided ID.");
                }
            }
        } catch (Exception e) {
            logger.severe("Database error: " + e.getMessage());
            response.getWriter().write("Database Error: " + e.getMessage());
        }
    }
}





