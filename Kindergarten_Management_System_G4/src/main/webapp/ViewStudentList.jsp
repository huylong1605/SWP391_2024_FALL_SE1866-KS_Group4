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

        /* Modal styles */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
            padding-top: 60px;
        }

        /* Modal content */
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto; /* 15% from the top and centered */
            padding: 20px;
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        .action-btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
        }
    </style>
</head>
<body>

<div class="sidebar">
    <div class="logo">
        <h2>Kider</h2>
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
                <th>Address</th>
                <th>Phone Number</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
       <c:set var="counter" value="1" />
       <c:forEach var="student" items="${students}">
           <tr>
               <td>${counter}</td>
               <td>${student.name}</td>
               <td>${student.dob}</td>
               <td>${student.gender ? 'Male' : 'Female'}</td>
               <td>${student.address}</td>
               <td>${student.phoneNumber}</td>
               <td><button class="action-btn" onclick="showModal('${student.studentId}', '${student.name}', '${student.dob}', '${student.gender ? 'Male' : 'Female'}', '${student.address}', '${student.phoneNumber}')">View</button></td>
           </tr>
           <c:set var="counter" value="${counter + 1}" />
       </c:forEach>

        </tbody>
    </table>

    <c:if test="${empty students}">
        <p class="no-students">No students found.</p>
    </c:if>
</div>

<!-- Modal -->
<div id="studentModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2>Student Information</h2>
        <p><strong>ID:</strong> <span id="modalStudentId"></span></p>
        <p><strong>Name:</strong> <span id="modalStudentName"></span></p>
        <p><strong>DOB:</strong> <span id="modalStudentDob"></span></p>
        <p><strong>Gender:</strong> <span id="modalStudentGender"></span></p>
        <p><strong>Address:</strong> <span id="modalAddress"></span></p>
        <p><strong>Phone Number:</strong> <span id="modalPhoneNumber"></span></p>
    </div>
</div>

<script>
    function showModal(studentId, name, dob, gender, address, phoneNumber) {
        document.getElementById('modalStudentId').innerText = studentId;
        document.getElementById('modalStudentName').innerText = name;
        document.getElementById('modalStudentDob').innerText = dob;
        document.getElementById('modalStudentGender').innerText = gender;
        document.getElementById('modalAddress').innerText = address;
        document.getElementById('modalPhoneNumber').innerText = phoneNumber;
        document.getElementById('studentModal').style.display = "block";
    }

    function closeModal() {
        document.getElementById('studentModal').style.display = "none";
    }

    // Close modal when clicking outside of it
    window.onclick = function(event) {
        const modal = document.getElementById('studentModal');
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
</script>

</body>
</html>
