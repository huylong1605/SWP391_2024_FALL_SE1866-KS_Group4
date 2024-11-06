<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Manage Notifications</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css"
          href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700"/>
    <link href="${pageContext.request.contextPath}/css/nucleo-icons.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/nucleo-svg.css" rel="stylesheet"/>
    <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">
    <link id="pagestyle" href="${pageContext.request.contextPath}/css/material-dashboard.css?v=3.1.0" rel="stylesheet"/>
</head>
<body class="g-sidenav-show  bg-gray-200">
<%@include file="Views/common/header.jsp" %>

<main class="main-content container mt-5">
    <h4 class="text-3xl font-weight-bolder mb-4">Notifications</h4>
    <a href="Views/HomePage/HomePage.jsp" type="button" class="btn btn-primary mb-3">
        Back
    </a>
    <div class="table-responsive">
        <table class="table align-items-center mb-0">
            <thead>
            <tr>
                <th class="text-uppercase text-secondary text-lg font-weight-bolder ">No</th>
                <th class="text-uppercase text-secondary text-lg font-weight-bolder ">Title</th>
                <th class="text-center text-uppercase text-secondary text-lg font-weight-bolder ">Date</th>
                <th class="text-center text-uppercase text-secondary text-lg font-weight-bolder ">Information</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="notification" items="${notificationList}" varStatus="index">
                <tr>
                    <td>
                        <p class="text-xl font-weight-bold mb-0">${index.index + 1}</p>
                    </td>
                    <td>
                        <p class="text-xl font-weight-bold mb-0">${notification.title}</p>
                    </td>
                    <td class="align-middle text-center">
                        <span class="text-secondary text-xl font-weight-bold">${notification.date}</span>
                    </td>
                    <td class="align-middle text-center">
                        <a href="${pageContext.request.contextPath}/viewNotification?id=${notification.notificationId}"
                           class="btn btn-sm btn-primary">View</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous">
</script>

<%@include file="Views/common/footer.jsp" %>
</body>
</html>
