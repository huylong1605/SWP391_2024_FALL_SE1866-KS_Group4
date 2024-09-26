<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 9/27/2024
  Time: 12:55 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student List - PreSkool</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <script src="script.js" defer></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
        }

        .container {
            display: flex;
        }

        /* Sidebar styling */
        .sidebar {
            width: 250px;
            background-color: #34495e; /* Màu tối hơn để dễ nhìn hơn */
            height: 100vh;
            color: #ecf0f1;
            padding-top: 20px;
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
            align-items: center;
        }

        .sidebar nav ul li a {
            color: #ecf0f1;
            text-decoration: none;
            font-size: 16px;
            display: flex;
            align-items: center;
            width: 100%;
        }

        .sidebar nav ul li a i {
            margin-right: 15px; /* Khoảng cách giữa icon và text */
            font-size: 18px;
        }

        .sidebar nav ul li:hover {
            background-color: #2c3e50;
            border-left: 4px solid #3498db; /* Hiệu ứng nhấn mạnh phần được chọn */
        }

        .sidebar nav ul li a:hover {
            color: #3498db;
        }

        .sidebar nav ul li ul {
            padding-left: 15px;
        }

        .sidebar nav ul li ul li {
            padding: 10px 0;
        }

        .sidebar nav ul li ul li a {
            font-size: 14px;
        }

        .sidebar nav ul li ul li a:hover {
            color: #2980b9;
        }
        main {
            flex-grow: 1;
            padding: 20px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 20px;
        }

        .header input {
            padding: 5px;
            margin-right: 10px;
        }

        .header button {
            padding: 5px 10px;
            background-color: #3498DB;
            color: white;
            border: none;
            cursor: pointer;
        }

        .header .user {
            display: flex;
            align-items: center;
        }

        .header .user img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-left: 10px;
        }

        .student-list {
            background-color: #ecf0f1;
            padding: 20px;
            border-radius: 10px;
        }

        .table-actions {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid #bdc3c7;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        thead {
            background-color: #2980B9;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <aside class="sidebar">
        <div class="logo">
            <h2>PreSkool</h2>
        </div>
        <nav>
            <ul>
                <li>
                    <a href="#">
                        <i class="fas fa-tachometer-alt"></i> <!-- Thêm icon -->
                        Dashboard
                    </a>
                </li>
                <li>
                    <a href="#">Students</a>
                    <ul>
                        <li><a href="#">Student List</a></li>
                        <li><a href="#">Student View</a></li>
                        <li><a href="#">Student Add</a></li>
                        <li><a href="#">Student Edit</a></li>
                    </ul>
                </li>
                <li><a href="#">Teachers</a></li>
                <li><a href="#">Departments</a></li>
                <li><a href="#">Subjects</a></li>
                <li><a href="#">Invoices</a></li>
                <li><a href="#">Accounts</a></li>
                <li><a href="#">Holiday</a></li>
                <li><a href="#">Fee</a></li>
            </ul>
        </nav>
    </aside>
    <main>
        <header class="header">
            <input type="text" placeholder="Search by ID..." />
            <input type="text" placeholder="Search by Name..." />
            <input type="text" placeholder="Search by Phone..." />
            <button>Search</button>
            <div class="user">
                <span>Ryan Taylor</span>
                <img src="avatar.png" alt="User Avatar">
            </div>
        </header>

        <section class="student-list">
            <div class="table-actions">
                <select>
                    <option>Show 10 entries</option>
                    <option>Show 20 entries</option>
                    <option>Show 50 entries</option>
                </select>
                <button>Download</button>
            </div>

            <table>
                <thead>
                <tr>
                    <th><input type="checkbox"></th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Class</th>
                    <th>DOB</th>
                    <th>Parent Name</th>
                    <th>Mobile Number</th>
                    <th>Address</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><input type="checkbox"></td>
                    <td>PRE2209</td>
                    <td>Aaliyah</td>
                    <td>10 A</td><td>2 Feb 2002</td>
                    <td>Jeffrey Wong</td>
                    <td>097 3584 5870</td>
                    <td>911 Deer Ridge Drive, USA</td>
                </tr>
                <!-- Repeat similar rows for other students -->
                </tbody>
            </table>
        </section>
    </main>
</div>
</body>
</html>
