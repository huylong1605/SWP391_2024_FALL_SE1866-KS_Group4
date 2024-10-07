<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>List of Classes</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding: 20px;
        }
        .table-container {
            max-width: 900px;
            margin: auto;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container table-container">
    <h2 class="text-center">List of Classes</h2>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>Class Name</th>
            <th>Class Level</th>
            <th>User</th>
            <th>Room</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Duyệt qua danh sách lớp học -->
        <c:forEach var="classes" items="${classes}">
            <tr>
                <td>${classes.name}</td>
                <td>${classes.classLevel.name}</td>
                <td>${classes.user.name}</td>
                <td>${classes.room.name}</td>
                <td>
                    <a href="updateClass.jsp?id=${classes.id}" class="btn btn-warning btn-sm">Edit</a>
                    <a href="deleteClass?id=${classes.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this class?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="createClass.jsp" class="btn btn-primary">Create New Class</a>
</div>
<%@ include file="/Views/common/footer.jsp" %>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>