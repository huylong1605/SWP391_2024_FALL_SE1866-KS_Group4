<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Notification Detail</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
</head>
<body>
<%@include file="../common/header.jsp"%>

<div class="container my-4">
    <h2 style="font-family: 'Roboto', Helvetica, Arial, sans-serif">Notification Detail</h2>

    <div class="card">
        <div class="card-header">
            <h4 style="font-family: 'Roboto', Helvetica, Arial, sans-serif">${notification.title} </h4>
        </div>
        <div class="card-body">
            <p><strong>Date:</strong> ${notification.date}</p>
            <p><strong>Content:</strong></p>
            <p>${notification.content}</p>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/Views/Admin/notifications" class="btn btn-primary mt-3">Back to Notifications</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
</body>
</html>
