<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Class Detail</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container mt-5">
    <h2>Class Detail</h2>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <c:if test="${not empty classesDAL}">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">${classesDAL.className}</h5>
                <p class="card-text"><strong>Level:</strong> ${classesDAL.classLevelName}</p>
                <p class="card-text"><strong>Teacher:</strong> ${classesDAL.fullname}</p>
                <p class="card-text"><strong>Room:</strong> ${classesDAL.roomNumber}</p>
                <p class="card-text"><strong>description:</strong> ${classesDAL.description}</p>
                <p class="card-text"><strong>capacity:</strong> ${classesDAL.capacity}</p>
                <p class="card-text"><strong>Email:</strong> ${classesDAL.email}</p>
                <a href="editClass?classId=${classesDAL.classId}" class="btn btn-primary">Edit</a>

                <a href="listClass" class="btn btn-secondary">Back to List</a>
            </div>
        </div>
    </c:if>
</div>
<%@ include file="/Views/common/footer.jsp" %>
</body>
</html>
