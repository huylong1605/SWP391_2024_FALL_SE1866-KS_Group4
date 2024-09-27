<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Student List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            display: flex;
        }

        /* Sidebar styles */
        .sidebar {
            width: 250px;
            background-color: #34495e;
            height: 100vh; /* Full height */
            color: #ecf0f1;
            padding-top: 20px;
            position: fixed; /* Fixed sidebar */
        }

        .sidebar .logo {
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            color: #ecf0f1;
            margin-bottom: 30px;
        }

        .sidebar nav ul {
            list-style-type: none;
            padding: 0;
        }

        .sidebar nav ul li {
            padding: 15px 20px;
            display: flex;
        }

        .sidebar nav ul li a {
            color: #ecf0f1;
            text-decoration: none;
            font-size: 16px;
            display: block;
            width: 100%;
        }

        .sidebar nav ul li a:hover {
            background-color: #2c3e50;
            color: #3498db;
        }

        /* Main content styles */
        .main {
            margin-left: 250px; /* Space for sidebar */
            padding: 20px;
            width: calc(100% - 250px); /* Adjust width */
        }

        /* Table styles */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #bdc3c7;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #2980b9;
            color: white;
        }

        /* No students message */
        .no-students {
            text-align: center;
            margin-top: 20px;
            font-size: 18px;
            color: #e74c3c;
        }
    </style>
</head>
<body>

<div class="sidebar">
    <div class="logo">
        <h2>PreSkool</h2>
    </div>
    <nav>
        <ul>
            <li><a href="#">Dashboard</a></li>
            <li><a href="#">Students</a></li>
            <li><a href="#">Teachers</a></li>
            <li><a href="#">Classes</a></li>
            <li><a href="#">Subjects</a></li>
            <li><a href="#">Invoices</a></li>
            <li><a href="#">Accounts</a></li>
            <li><a href="#">Logout</a></li>
        </ul>
    </nav>
</div>

<div class="main">
    <h1 style="text-align: center; margin-top: 20px;">List of Students</h1>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>DOB</th>
                <th>Gender</th>
                <th>Class ID</th>
                <th>User ID</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="student" items="${students}">
                <tr>
                    <td>${student.studentId}</td>
                    <td>${student.name}</td>
                    <td>${student.dob}</td>
                    <td>${student.gender ? 'Male' : 'Female'}</td>
                    <td>${student.classId}</td>
                    <td>${student.userId}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty students}">
        <p class="no-students">No students found.</p>
    </c:if>
</div>

</body>
</html>
