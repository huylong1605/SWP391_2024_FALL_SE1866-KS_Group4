<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Parent Application List</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- DataTable CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
    <!-- Font Awesome CSS for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
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

        .btn-custom {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class="container mt-5">
    <h2>Application List</h2>

    <a href="${pageContext.request.contextPath}/Views/HomePage/HomePage.jsp" type="button" class="btn btn-primary mb-3">
        Back to Home
    </a>
    <a href="${pageContext.request.contextPath}/send-application" class="btn btn-primary btn-custom">Send New
        Application</a>

    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>Type</th>
            <th>Application Content</th>
            <th>Date Created</th>
            <th>Status</th>
            <th>Process Note</th>
            <th>Date Response</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="application" items="${applications}">
            <tr>
                <td>${application.title}</td>
                <td>${application.applicationContent}</td>
                <td>${application.dateCreate}</td>
                <td>${application.status}</td>
                <td>${application.applicationResponse}</td>
                <td>${application.dateResponse}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@include file="../common/footer.jsp" %>

<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<!-- DataTable JS -->
<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>

</body>
</html>
