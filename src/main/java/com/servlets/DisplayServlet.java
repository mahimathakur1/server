package com.servlets;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.database.DBConnection;

@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM users";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>User List</title>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
                out.println("h1 { text-align: center; color: #333; margin-top: 50px; }");
                out.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; }");
                out.println("th, td { padding: 12px; text-align: left; border: 1px solid #ddd; }");
                out.println("th { background-color: #4CAF50; color: white; }");
                out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
                out.println("tr:hover { background-color: #ddd; }");
                out.println("footer { text-align: center; padding: 20px; background-color: #333; color: white; margin-top: 50px; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>User List</h1>");
                out.println("<table>");
                out.println("<tr><th>ID</th><th>Name</th><th>Email</th></tr>");
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getString("email") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("<footer>");
                out.println("<p>&copy; 2024 User Management System</p>");
                out.println("</footer>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (Exception e) {
            out.println("<html><body><h2>Database Error: " + e.getMessage() + "</h2></body></html>");
        }
    }
}


