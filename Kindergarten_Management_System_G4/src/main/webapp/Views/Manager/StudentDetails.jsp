<%--
  Created by IntelliJ IDEA.
  User: chuc2
  Date: 9/29/2024
  Time: 8:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>
        Admin Manage
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <!--     Fonts and icons     -->
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
    <!-- Nucleo Icons -->
    <link href="${pageContext.request.contextPath}/css/nucleo-icons.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/nucleo-svg.css" rel="stylesheet" />
    <!-- Font Awesome Icons -->
    <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
    <!-- Material Icons -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">
    <!-- CSS Files -->
    <link id="pagestyle" href="${pageContext.request.contextPath}/css/material-dashboard.css?v=3.1.0" rel="stylesheet" />

    <style>
        a.sidebar-link:hover {
            background-color: #0303aa;
            border-left: 3px solid white;
            color: white;
        }

    </style>
</head>
<body class="g-sidenav-show bg-gray-200">
<%@include file="../common/header.jsp"%>
<div class="containerAll">
    <div class="wrapper">
        <aside id="sidebar" class="expand">
            <div class="d-flex">
                <button class="toggle-btn" type="button">
                    <i class="lni lni-grid-alt"></i>
                </button>
                <div class="sidebar-logo">
                    <a href="#">Manager Manage</a>
                </div>
            </div>
            <ul class="sidebar-nav">
                <li class="sidebar-item">
                    <a href="${pageContext.request.contextPath}/listClass" class="sidebar-link">
                        <i class="lni lni-user"></i>
                        <span>Manage Class</span>
                    </a>
                </li>
            </ul>
        </aside>
        <div class="main-content position-relative max-height-vh-100 h-100">
            <!-- Navbar -->
            <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur" data-scroll="true">
                <div class="container-fluid py-1 px-3">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark" href="javascript:;">Pages</a></li>
                            <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Student Detail</li>
                        </ol>
                        <h6 class="font-weight-bolder mb-0">Student Detail</h6>
                    </nav>
                </div>
            </nav>
            <!-- End Navbar -->
            <div class="container-fluid px-2 px-md-4" style="width: 1260px;">
                <div class="page-header min-height-300 border-radius-xl mt-4" style="background-image: url('https://images.unsplash.com/photo-1531512073830-ba890ca4eba2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1920&q=80');">
                    <span class="mask  bg-gradient-primary  opacity-2"></span>
                </div>
                <div class="card card-body mx-3 mx-md-4 mt-n6" style="z-index: 1">
                    <div class="row gx-4 mb-2">
                        <div class="col-auto">
                            <div class="avatar avatar-xl position-relative">
                                <img src="${pageContext.request.contextPath}/img/Chup-anh-chan-dung-cho-be-3.jpg" alt="profile_image" class="w-100 border-radius-lg shadow-sm">
                            </div>
                        </div>
                        <div class="col-auto my-auto">
                            <div class="h-100">
                                <h5 class="mb-1">${detaiStudent.name}</h5>
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="row">
                            <div class="col-12 col-xl-6">
                                <div class="card card-plain h-100">
                                    <div class="card-header pb-0 p-3">
                                        <h6 class="mb-0">Information</h6>
                                    </div>
                                    <div class="card-body p-3">
                                        <ul class="list-group">
                                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Full Name:</strong> &nbsp; ${detaiStudent.name}</li>
                                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Date Of Birth:</strong> &nbsp; ${detaiStudent.dob}</li>
                                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Gender:</strong> &nbsp; ${detaiStudent.gender?"Male":"Female"}</li>
                                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Age:</strong> &nbsp; ${detaiStudent.age}</li>
                                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Class:</strong> &nbsp; ${detaiStudent.className}</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-xl-6">
                                <div class="card card-plain h-100">
                                    <div class="card-header pb-0 p-3">
                                        <h6 class="mb-0">Contact</h6>
                                    </div>
                                    <div class="card-body p-3">
                                        <ul class="list-group">
                                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Parent Name:</strong> &nbsp; ${detaiStudent.parentName}</li>
                                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Parent Email:</strong> &nbsp; ${detaiStudent.parentEmail}</li>
                                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Address:</strong> &nbsp; ${detaiStudent.parentAddress}</li>
                                            <li class="list-group-item border-0 ps-0 text-sm"><strong class="text-dark">Parent Phone:</strong> &nbsp; ${detaiStudent.parentPhone}</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="pt-3">
                            <button class="btn btn-primary">
                                <a class="text-light" href="${pageContext.request.contextPath}/Views/Manager/listStudentInClass?classId=${detaiStudent.classId}">Back To List Student</a>
                            </button>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<%@include file="../common/footer.jsp"%>
</body>
</html>
