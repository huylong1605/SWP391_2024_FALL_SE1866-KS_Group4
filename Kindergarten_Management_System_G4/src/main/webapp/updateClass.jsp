<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Class</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding: 20px;
            background-color: #f8f9fa;
        }
        .form-container {
            max-width: 600px;
            margin: 50px auto;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        select.form-control {
            appearance: none;
            -moz-appearance: none;
            -webkit-appearance: none;
            padding-right: 30px;
            background-image: url('data:image/svg+xml;charset=US-ASCII,%3Csvg xmlns%3D%22http%3A//www.w3.org/2000/svg%22 width%3D%2224%22 height%3D%2224%22 viewBox%3D%220 0 24 24%22 fill%3D%22none%22 stroke%3D%22%23000%22 stroke-width%3D%222%22 stroke-linecap%3D%22round%22 stroke-linejoin%3D%22round%22 class%3D%22feather feather-chevron-down%22%3E%3Cpolyline points%3D%226 9 12 15 18 9%22%3E%3C/polyline%3E%3C/svg%3E');
            background-position: right 10px center;
            background-repeat: no-repeat;
            background-size: 16px 16px;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container form-container">
    <h2 class="text-center mb-4">Update Class</h2>
    <form action="UpdateClassServlet" method="post">
        <!-- Hidden field for class ID -->
        <input type="hidden" name="classId" value="${classes.id}">

        <!-- Class Name -->
        <div class="form-group">
            <label for="className">Class Name</label>
            <input type="text" class="form-control" id="className" name="className" value="${classes.name}" required>
        </div>

        <!-- Class Level ID (Combobox) -->
        <div class="form-group">
            <label for="classLevelId">Class Level</label>
            <select class="form-control" id="classLevelId" name="classLevelId" required>
                <!-- Duyệt qua danh sách class levels -->
                <c:forEach var="classLevel" items="${classLevels}">
                    <option value="${classLevel.id}" ${classLevel.id == classes.classLevel.id ? 'selected' : ''}>
                            ${classLevel.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- User ID (Combobox) -->
        <div class="form-group">
            <label for="userId">User</label>
            <select class="form-control" id="userId" name="userId" required>
                <!-- Duyệt qua danh sách users -->
                <c:forEach var="user" items="${users}">
                    <option value="${user.id}" ${user.id == classes.user.id ? 'selected' : ''}>
                            ${user.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Room ID (Combobox) -->
        <div class="form-group">
            <label for="roomId">Room</label>
            <select class="form-control" id="roomId" name="roomId" required>
                <!-- Duyệt qua danh sách rooms -->
                <c:forEach var="room" items="${rooms}">
                    <option value="${room.id}" ${room.id == classes.room.id ? 'selected' : ''}>
                            ${room.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Submit Button -->
        <div class="form-group text-center">
            <button type="submit" class="btn btn-primary btn-block">Update Class</button>
        </div>
    </form>
</div>
<%@ include file="/Views/common/footer.jsp" %>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
