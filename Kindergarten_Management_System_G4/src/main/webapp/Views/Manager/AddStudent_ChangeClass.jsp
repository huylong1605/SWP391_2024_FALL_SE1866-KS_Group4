<%--
  Created by IntelliJ IDEA.
  User: chuc2
  Date: 10/12/2024
  Time: 9:23 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>
        Admin Manage
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <!--     Fonts and icons     -->
    <link rel="stylesheet" type="text/css"
          href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700"/>
    <!-- Nucleo Icons -->
    <link href="${pageContext.request.contextPath}/css/nucleo-icons.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/nucleo-svg.css" rel="stylesheet"/>
    <!-- Font Awesome Icons -->
    <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
    <!-- Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">
    <!-- CSS Files -->
    <link id="pagestyle" href="${pageContext.request.contextPath}/css/material-dashboard.css?v=3.1.0" rel="stylesheet"/>
    <style>
        a.sidebar-link:hover {
            background-color: #0303aa;
            border-left: 3px solid white;
            color: white;
        }

        /*.table-responsive {*/
        /*    max-height: 425px; !*
        /*    overflow-y: auto; !*
        /*}*/
        /*table {*/
        /*    width: 100%;*/
        /*    table-layout: fixed;*/
        /*}*/
        .wrapper {
            height: 860px;
        }
    </style>
</head>
<body class="g-sidenav-show bg-gray-200">
<%@include file="/Views/common/header.jsp" %>
<div class="containerAll">
    <div class="wrapper">
        <%@include file= "/Views/common/sidebar_manage.jsp"%>
        <div class="container">
            <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-12">
                <!-- Navbar -->
                <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur"
                     data-scroll="true">
                    <div class="d-flex py-1 px-3 justify-content-between align-items-center" style="width: 100%;">
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                                <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark"
                                                                       href="javascript:;">Pages</a></li>
                                <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Student</li>
                            </ol>
                            <h4 class="font-weight-bolder mb-0">Manage Student</h4>
                        </nav>

                        <div class="action-bar">

                        </div>
                    </div>

                </nav>
                <!-- End Navbar -->
                <div class="container-fluid py-4">
                    <div class="row">
                        <div class="col-12">
                            <div class="card my-4">
                                <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                    <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                                        <h6 class="text-white text-capitalize ps-3">Student</h6>
                                    </div>
                                </div>
                                <div class="container table-container">
                                    <h2 class="text-center">Student</h2>
                                    <div class="d-flex justify-content-center">
                                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/Views/Manager/StudentDontHaveClass" style="background-color: #ff2f2f;width: 300px;padding: 40px 0; margin: 10px">List Student Not Have Class</a>
                                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/Views/Manager/StudentHaveClass" style="background-color: #1b9f1b;width: 300px;padding: 40px 0; margin: 10px">List Student Had Class</a>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>
            </main>
        </div>
    </div>
</div>

<%@include file="/Views/common/footer.jsp" %>
</body>
</html>

