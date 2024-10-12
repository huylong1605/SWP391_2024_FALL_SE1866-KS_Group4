<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Class</title>
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

        label.required:after {
            content: "*";
            color: red;
            margin-left: 5px;
        }

        select.form-control {
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="10" height="10" viewBox="0 0 10 10"><path fill="black" d="M0 0l5 5 5-5z"/></svg>');
            background-repeat: no-repeat;
            background-position: right 10px top 50%;
            background-size: 10px 10px;
        }
    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container form-container">
    <h2 class="text-center mb-4">Update Class</h2>

    <!-- Thông báo lỗi nếu có -->
    <% if (request.getAttribute("maxLength") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("maxLength") %></div>
    <% } %>
    <% if (request.getAttribute("classNameExist") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("classNameExist") %></div>
    <% } %>

    <form action="updateClass" method="post">
        <input type="hidden" name="classId" value="${classObject.classId}">

        <!-- Class Name -->
        <div class="form-group mb-3">
            <label for="className" class="required">Class Name</label>
            <input type="text" class="form-control" id="className" name="className"
                   value="${param.className != null ? param.className : classObject.className}" required>
        </div>

        <!-- Class Level -->
        <div class="form-group mb-3">
            <label for="classLevelId" class="required">Class Level</label>
            <select class="form-control" id="classLevelId" name="classLevelId" required>
                <c:forEach var="classLevel" items="${listClassLevel}">
                    <option value="${classLevel.classLevelId}"
                            <c:if test="${classLevel.classLevelId == (param.classLevelId != null ? param.classLevelId : classObject.classLevelId)}">selected</c:if>>
                            ${classLevel.classLevelName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Teacher -->
        <div class="form-group mb-3">
            <label for="userId" class="required">Teacher</label>
            <select class="form-control" id="userId" name="userId" required>
                <c:forEach var="teachers" items="${listTeacher}">
                    <option value="${teachers.userID}"
                            <c:if test="${teachers.userID == (param.userId != null ? param.userId : classObject.userId)}">selected</c:if>>
                            ${teachers.fullname}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Room -->
        <div class="form-group mb-3">
            <label for="roomId" class="required">Room</label>
            <select class="form-control" id="roomId" name="roomId" required>
                <c:forEach var="rooms" items="${listRoom}">
                    <option value="${rooms.roomId}"
                            <c:if test="${rooms.roomId == (param.roomId != null ? param.roomId : classObject.roomId)}">selected</c:if>>
                            ${rooms.roomNumber}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- Nút Update và Back to Home -->
        <div class="form-group text-end">
            <a href="listClass" class="btn btn-secondary me-2">Back to List</a>
            <button type="submit" class="btn btn-primary">Update Class</button>
        </div>
    </form>
</div>
<%@ include file="/Views/common/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
