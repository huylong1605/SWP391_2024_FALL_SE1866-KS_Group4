<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Class</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding: 20px;
            background-color: #f7f7f7;
        }

        .form-container {
            max-width: 600px;
            margin: auto;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group select {
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            background: url('data:image/svg+xml;charset=US-ASCII,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 4 5"><path fill="none" stroke="black" stroke-width="1.5" d="M0 0l2 2 2-2"/></svg>') no-repeat right 12px center;
            background-size: 10px 10px;
        }

        .form-group input, .form-group select {
            padding-right: 30px;
        }

        .text-center {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<%@ include file="/Views/common/header.jsp" %>

<div class="container form-container" style="margin-top: 70px">
    <h2 class="text-center">Create Class</h2>

    <% if (request.getAttribute("classNameExist") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("classNameExist") %>
    </div>
    <% } %>

    <% if (request.getAttribute("maxLength") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("maxLength") %>
    </div>
    <% } %>

    <form action="createClass" method="post">
        <!-- Class Name -->
        <div class="mb-3">
            <label for="className" class="form-label">
                Class Name <span class="text-danger">*</span>
            </label>
            <input type="text" class="form-control" id="className" name="className"
                   value="<c:out value='${param.className}'/>" required >
        </div>

        <!-- Class Level ID (Combobox) -->
        <div class="mb-3">
            <label for="classLevelId" class="form-label">
                Class Level <span class="text-danger">*</span>
            </label>
            <select class="form-control" id="classLevelId" name="classLevelId" required>
                <option value="" disabled selected>Select Class Level</option>
                <c:forEach var="classLevel" items="${listClassLevel}">
                    <option value="${classLevel.classLevelId}"
                            <c:if test="${classLevel.classLevelId == param.classLevelId}">selected</c:if>>
                            ${classLevel.classLevelName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- User ID (Combobox) -->
        <div class="mb-3">
            <label for="userId" class="form-label">
                Teacher <span class="text-danger">*</span>
            </label>
            <select class="form-control" id="userId" name="userId" required>
                <option value="" disabled selected>Select Teacher</option>
                <c:forEach var="teacher" items="${listTeacher}">
                    <option value="${teacher.userID}"
                            <c:if test="${teacher.userID == param.userId}">selected</c:if>>
                            ${teacher.fullname}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Room ID (Combobox) -->
        <div class="mb-3">
            <label for="roomId" class="form-label">
                Room <span class="text-danger">*</span>
            </label>
            <select class="form-control" id="roomId" name="roomId" required>
                <option value="" disabled selected>Select Room</option>
                <c:forEach var="room" items="${listRoom}">
                    <option value="${room.roomId}"
                            <c:if test="${room.roomId == param.roomId}">selected</c:if>>
                            ${room.roomNumber}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Submit Button -->
        <div class="form-group text-end">
            <a href="listClass" class="btn btn-secondary me-2">Back to List</a>
            <button type="submit" class="btn btn-primary">Create Class</button>
        </div>
    </form>
</div>


<%@ include file="/Views/common/footer.jsp" %>

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
