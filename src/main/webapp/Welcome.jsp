<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 900px;
            margin: auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        h1 {
            color: #4CAF50;
            text-align: center;
        }

        .welcome-message {
            text-align: center;
            font-size: 1.2em;
            margin-bottom: 30px;
        }

        .menu {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 30px;
        }

        .menu a {
            text-decoration: none;
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border-radius: 4px;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        .menu a:hover {
            background-color: #45a049;
        }

        .logout-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            font-size: 16px;
        }

        .logout-link a {
            text-decoration: none;
            color: #4CAF50;
            font-weight: bold;
        }

        .logout-link a:hover {
            color: #007bff;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to the Application!</h1>
        <div class="welcome-message">
            <p>Hello, <%= request.getSession().getAttribute("user") %>! You have successfully logged in.</p>
            <P>You can perform CRUD Operation</p>
        </div>
        <div class="menu">
            <a href="Register.html">Register</a>
            <a href="Update.html">Update</a>
            <a href="Delete.html">Delete</a>
            <a href="DisplayServlet">Display</a>
        </div>
        <div class="logout-link">
            <a href="LogoutServlet">Logout</a>
        </div>
    </div>
</body>
</html>

