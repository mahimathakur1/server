package com.servlets;
import org.mindrot.jbcrypt.BCrypt;



import jakarta.servlet.*;
import jakarta.servlet.http.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.database.DBConnection;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
    	
    	int id = Integer.parseInt(request.getParameter("id"));

    	String name = request.getParameter("name");
        String email = request.getParameter("email");
        
        String password = request.getParameter("password"); // The password entered by the user

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO users (id, name, email, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            	pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, email);
                pstmt.setString(4, hashedPassword); // Store the hashed password
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                	 response.sendRedirect("Login.html");
                } else {
                    response.getWriter().write("Failed to Register User");
                }
            }
        } catch (Exception e) {
            response.getWriter().write("Database Error: " + e.getMessage());
        }
    }
}

