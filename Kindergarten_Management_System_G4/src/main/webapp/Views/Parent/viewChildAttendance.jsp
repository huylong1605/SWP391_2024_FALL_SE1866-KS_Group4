<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attendance Details for Student ID: ${totalAttendance.studentId}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container mt-4">
    <h2>Student Name: ${totalAttendance.studentName}</h2>
    <h2>Date Of Birth: ${totalAttendance.dateOfBirth}</h2>

    <h3>Total Attendance Summary</h3>
    <c:if test="${not empty totalAttendance}">
        <ul class="list-group mb-4">
            <li class="list-group-item">Total Attendance: ${totalAttendance.totalAttendance}</li>
            <li class="list-group-item">Total Present: ${totalAttendance.totalPresent}</li>
            <li class="list-group-item">Total Absent: ${totalAttendance.totalAbsent}</li>
        </ul>
    </c:if>

    <h3>Attendance Records</h3>
    <c:if test="${not empty attendanceDetails}">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Date</th>
                <th>Slot Name</th>
                <th>Attendance Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="record" items="${attendanceDetails}">
                <tr>
                    <td>${record.date}</td>
                    <td>${record.slotName}</td>
                    <td>
                        <c:choose>
                            <c:when test="${record.attendStatus == 'Present'}">
                                <span style="color: green;">${record.attendStatus}</span>
                            </c:when>
                            <c:when test="${record.attendStatus == 'Absent'}">
                                <span style="color: red;">${record.attendStatus}</span>
                            </c:when>
                            <c:otherwise>
                                ${record.attendStatus} <!-- Nếu không phải Present hay Absent -->
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <div class="mt-3">
        <a href="${pageContext.request.contextPath}/Views/HomePage/HomePage.jsp" class="btn btn-primary">Back to home</a>
    </div>
</div>
<%@ include file="/Views/common/footer.jsp" %>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
