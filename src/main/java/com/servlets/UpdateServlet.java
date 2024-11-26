package com.servlets;


import jakarta.servlet.*;
import jakarta.servlet.http.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.database.DBConnection;


public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String newName = request.getParameter("newName");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE users SET name = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, newName);
                pstmt.setInt(2, Integer.parseInt(userId));
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    response.getWriter().write("User Updated Successfully");
                } else {
                    response.getWriter().write("Failed to Update User");
                }
            }
        } catch (Exception e) {
            response.getWriter().write("Database Error: " + e.getMessage());
        }
    }
}

