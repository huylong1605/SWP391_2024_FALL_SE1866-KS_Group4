<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teacher Application List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Additional styling for the table */
        table {
            margin-top: 20px;
        }

        th, td {
            padding: 15px;
            text-align: center;
        }

        th {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="container mt-5">
    <h2>Application List</h2>

    <a href="${pageContext.request.contextPath}/Views/HomePage/HomePageForTeacher.jsp" type="button" class="btn btn-primary mb-3">
        Back to Home
    </a>

    <!-- Dropdown to select class -->
    <form action="${pageContext.request.contextPath}/teacher/applications" method="get">
        <select name="classId" class="form-control" onchange="this.form.submit()">
            <option value="">Select Class</option>
            <c:forEach var="classItem" items="${classes}">
                <option value="${classItem.classId}" ${classItem.classId == classIdSelected ? 'selected' : ''}>${classItem.className}</option>
            </c:forEach>
        </select>
    </form>

    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>Parent Name</th>
            <th>Title</th>
            <th>Application Content</th>
            <th>Date Created</th>
            <th>Status</th>
            <th>Process Note</th>
            <th>Date Response</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty applications}">
                <c:forEach var="application" items="${applications}">
                    <tr>
                        <td>${application.parentName}</td>
                        <td>${application.title}</td>
                        <td>${application.applicationContent}</td>
                        <td>${application.dateCreate}</td>
                        <td>${application.status}</td>
                        <td>${application.applicationResponse}</td>
                        <td>${application.dateResponse}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/teacher/edit-application/${application.applicationId}"
                               class="btn btn-warning">View</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="6" class="text-center">No data found.</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>
<%@include file="../common/footer.jsp" %>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
