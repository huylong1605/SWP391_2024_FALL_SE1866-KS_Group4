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
        .action-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        .filter-form {
            display: flex;
            gap: 10px;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container table-container">
    <h2 class="text-center">List of Classes</h2>

    <!-- Thanh tìm kiếm và lọc -->
    <div class="action-bar">
        <!-- Nút tạo lớp -->
        <a href="createClass" class="btn btn-primary">Create New Class</a>

        <!-- Form tìm kiếm và lọc -->
        <form class="filter-form" method="get" action="listClass">
            <input type="text" id="search" name="search" class="form-control"  placeholder="Search by class name..." value="${param.search}" >

            <select name="filterLevel" class="form-select" >
                <option value="0">All Levels</option>
                <c:forEach var="classLevel" items="${listClassLevel}">
                    <option value="${classLevel.classLevelId}"
                            <c:if test="${classLevel.classLevelId == param.filterLevel}">selected</c:if>>
                            ${classLevel.classLevelName}
                    </option>
                </c:forEach>
            </select>

            <button type="submit" class="btn btn-secondary">Search</button>
        </form>
    </div>

    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>Class Name</th>
            <th>Class Level</th>
            <th>Teacher</th>
            <th>Room</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <!-- Duyệt qua danh sách lớp học -->
        <c:forEach var="classes" items="${listClass}">
            <tr>
                <td>${classes.className}</td>
                <td>${classes.classLevelName}</td>
                <td>${classes.fullname}</td>
                <td>${classes.roomNumber}</td>
                <td>
                    <a href="updateClass.jsp?id=${classes.classId}" class="btn btn-warning btn-sm">Edit</a>
                    <a href="deleteClass?id=${classes.classId}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this class?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/Views/common/footer.jsp" %>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
