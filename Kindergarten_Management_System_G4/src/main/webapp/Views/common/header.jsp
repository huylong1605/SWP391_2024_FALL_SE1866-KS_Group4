<%@ page import="org.example.kindergarten_management_system_g4.model.User" %><%--
  Created by IntelliJ IDEA.
  User: chuc2
  Date: 9/25/2024
  Time: 11:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Kider - Preschool Website Template</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">


    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600&family=Inter:wght@600&family=Lobster+Two:wght@700&display=swap"
          rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="${pageContext.request.contextPath}/lib/animate/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-white navbar-light sticky-top px-4 px-lg-5 py-lg-0">
    <a href="${pageContext.request.contextPath}/Views/LandingPage/LandingPage.jsp" class="navbar-brand">
        <h1 class="m-0 text-primary"><i class="fa fa-book-reader me-3"></i>KMS</h1>
    </a>
    <button type="button" class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse" style="margin-left: 200px; font-size: 20px">
        <div class="navbar-nav mx-auto">

                <%
                    User user = (User) session.getAttribute("user");
                    if (user != null) {  // Kiểm tra xem user có null không
                        String role = String.valueOf(user.getRoleId());

                        if ("1".equals(role)) {
                %>
                <a href="${pageContext.request.contextPath}/Views/HomePage/HomePageForAdmin.jsp" class="nav-item nav-link active">Home</a>
                <%
                } else if ("2".equals(role)) {
                %>
                <a href="${pageContext.request.contextPath}/Views/HomePage/HomePageForTeacher.jsp" class="nav-item nav-link active">Home</a>
                <%
                } else if ("3".equals(role)) {
                %>
                <a href="${pageContext.request.contextPath}/Views/HomePage/HomePage.jsp" class="nav-item nav-link active">Home</a>
                <%
                } else if ("4".equals(role)) {
                %>
                <a href="${pageContext.request.contextPath}/Views/HomePage/HomePageForManager" class="nav-item nav-link active">Home</a>
                <%
                } else if ("5".equals(role)) {
                %>
                <a href="${pageContext.request.contextPath}/Views/HomePage/HomePageForEnrollment.jsp" class="nav-item nav-link active">Home</a>
                <%
                    }
                } else {
                %>
                <a href="${pageContext.request.contextPath}/Views/LandingPage/LandingPage.jsp" class="nav-item nav-link active">Home</a>
                <%
                    }
                %>

                <a href="#" class="nav-item nav-link">About Us</a>
            <a href="#" class="nav-item nav-link">Contact Us</a>

        </div>

        <ul class="navbar-nav ml-auto" style="font-size: 20px">
            <c:if test="${sessionScope.user != null}">
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/viewprofile">Hello, ${sessionScope.user.fullname}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                </li>
            </c:if>
        </ul>
    </div>
    <div class="collapse navbar-collapse" style="margin-left: 150px; font-size: 20px">
        <ul class="navbar-nav ml-auto">
            <c:if test="${sessionScope.user == null}">
                <a href="${pageContext.request.contextPath}/Login.jsp"
                   class="btn btn-primary rounded-pill px-3 d-none d-lg-block">Sign in</a>
                <a href="${pageContext.request.contextPath}/register.jsp"
                   class="btn btn-primary rounded-pill px-3 d-none d-lg-block">Sign up</a>
            </c:if>
        </ul>
    </div>
</nav>
</body>
</html>
