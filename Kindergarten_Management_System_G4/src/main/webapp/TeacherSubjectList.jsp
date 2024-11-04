<%--
  Created by IntelliJ IDEA.
  User: anhla
  Date: 11/2/2024
  Time: 9:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Parent Subject List</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- DataTable CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
    <!-- Font Awesome CSS for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<%@ include file="Views/common/header.jsp" %>

<div class="mt-5 main-content container">
    <h2>List of Subject</h2>
    <a href="Views/HomePage/HomePageForTeacher.jsp" type="button" class="btn btn-primary mb-3">
        Back
    </a>
    <table id="subjectTable" class="table table-striped">
        <thead>
        <tr>
            <th>Subject Code</th>
            <th>Subject Name</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="subject" items="${subjectList}" varStatus="index">
            <tr>
                <td>${subject.subjectCode}</td>
                <td>${subject.subjectName}</td>
                <td>${subject.description}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<c:forEach var="subject" items="${subjectList}">
    <!-- subject Info Modal -->
    <div class="modal fade" id="subjectInfoModal_${subject.subjectId}" tabindex="-1" role="dialog"
         aria-labelledby="subjectInfoModalLabel_${subject.subjectId}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="subjectInfoModalLabel_${subject.subjectId}">Subject Details</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p><strong>Subject Code:</strong> ${subject.subjectCode}</p>
                    <p><strong>Subject Name:</strong> ${subject.subjectName}</p>
                    <p><strong>Description:</strong> ${subject.description}</p>
                </div>
            </div>
        </div>
    </div>
</c:forEach>
<%@include file="Views/common/footer.jsp" %>
</body>
</html>
