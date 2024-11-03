<%--
  Created by IntelliJ IDEA.
  User: chuc2
  Date: 10/2/2024
  Time: 10:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Basic -->
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <!-- Site Metas -->
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Brighton</title>
    <!-- slider stylesheet -->
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.1.3/assets/owl.carousel.min.css" />
    <!-- bootstrap core css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <!-- fonts style -->
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700|Poppins:400,700|Roboto:400,700&display=swap" rel="stylesheet" />
    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/css/Home-Manager.css" rel="stylesheet" />
    <!-- responsive style -->
    <link href="${pageContext.request.contextPath}/css/responsive.css" rel="stylesheet" />

</head>
<body>
<%@ include file="/Views/common/header.jsp" %>

<div class="container-fluid home-manager">
    <div class="item-nav-manager d-flex justify-content-center">
        <a href="${pageContext.request.contextPath}/registerStudentByEnrollment" class="items-nav-manager">
            Register New Information Student
        </a>
       
    </div>
    <div class=" aa d-flex justify-content-center align-items-center">
        <div class="admin-page">
            <div class="waviy s1">
                <span style="--i: 1">W</span>
                <span style="--i: 2">E</span>
                <span style="--i: 3">L</span>
                <span style="--i: 4">C</span>
                <span style="--i: 5">O</span>
                <span style="--i: 6">M</span>
                <span style="--i: 7">E</span>
            </div>

            <div class="waviy s2">
                <span style="--i: 1">E</span>
                <span style="--i: 2">N</span>
                <span style="--i: 3">R</span>
                <span style="--i: 4">O</span>
                <span style="--i: 4">L</span>
                <span style="--i: 1">L</span>
                <span style="--i: 2">M</span>
                <span style="--i: 1">E</span>
                <span style="--i: 2">N</span>
                <span style="--i: 2">T</span>
            </div>
        </div>
    </div>
</div>


<%@ include file="/Views/common/footer.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
</body>
</html>
