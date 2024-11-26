package com.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.database.DBConnection;
import org.mindrot.jbcrypt.BCrypt;  // Import the BCrypt library

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Retrieve the stored bcrypt hash from the database
                    String storedHash = rs.getString("password");

                    // Compare the password entered by the user with the stored hash using bcrypt
                    if (BCrypt.checkpw(password, storedHash)) {
                        // Password matches, proceed to login
                        HttpSession session = request.getSession();
                        session.setAttribute("user", rs.getString("name"));
                        response.sendRedirect("Welcome.jsp");  // Redirect to the welcome page
                    } else {
                        response.getWriter().write("Invalid email or password.");
                    }
                } else {
                    response.getWriter().write("Invalid email or password.");
                }
            }
        } catch (Exception e) {
            response.getWriter().write("Database Error: " + e.getMessage());
        }
    }
}

