<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Attendance Summary for Class</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<div class="container mt-4">
  <h2>Attendance Summary for Class ID: ${classId}</h2>

  <table class="table table-striped">
    <thead>
    <tr>
      <th>Student ID</th>
      <th>Student Name</th>
      <th>Total Attendance</th>
      <th>Present</th>
      <th>Absent</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="attendance" items="${summaryList}">
      <tr>
        <td>${attendance.studentId}</td>
        <td>${attendance.studentName}</td>
        <td>${attendance.totalAttendance}</td>
        <td>${attendance.totalPresent}</td>
        <td>${attendance.totalAbsent}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <div class="mt-3">
    <a href="${pageContext.request.contextPath}/Views/Teacher/teacherSchedule?teacherId=${sessionScope.user.userID}" class="btn btn-primary">Back to Attendance</a>
  </div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
