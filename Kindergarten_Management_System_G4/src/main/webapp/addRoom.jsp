<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Add Room</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <style>
        body {
            background-color: #f8f9fa;
        }

        .container {
            margin-top: 50px;
            max-width: 600px;
            padding: 20px;
            border-radius: 10px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            margin-bottom: 20px;
            text-align: center;
        }

        .form-label {
            font-weight: bold;
        }

        button {
            width: 100%;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Add Room</h2>

    <!-- Hiển thị thông báo nếu có -->
    <%
        String successMessage = (String) session.getAttribute("successMessage");
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (successMessage != null) {
    %>
    <div class="alert alert-success">
        <%= successMessage %>
    </div>
    <%
            session.removeAttribute("successMessage");
        }
        if (errorMessage != null) {
    %>
    <div class="alert alert-danger">
        <%= errorMessage %>
    </div>
    <%
            session.removeAttribute("errorMessage");
        }
    %>

    <form action="${pageContext.request.contextPath}/addRoom" method="post">
        <div class="mb-3">
            <label for="roomNumber" class="form-label">Room Number:</label>
            <input type="text" id="roomNumber" name="roomNumber" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="capacity" class="form-label">Capacity:</label>
            <input type="number" id="capacity" name="capacity" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="status" class="form-label">Status:</label>
            <select id="status" name="status" class="form-select" required>
                <option value="1">Active</option>
                <option value="0">Inactive</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Add Room</button>
        <a href="${pageContext.request.contextPath}/Views/Manager/listRoom" class="btn btn-secondary mt-3">Back to Room
            List</a>
    </form>
</div>
</body>
</html>
