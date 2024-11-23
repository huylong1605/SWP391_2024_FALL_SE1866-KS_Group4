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
        <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
            <!-- Navbar -->
            <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur"
                 data-scroll="true">
                <div class="d-flex py-1 px-3 justify-content-between align-items-center" style="width: 100%;">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark"
                                                                   href="javascript:;">Pages</a></li>
                            <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Account</li>
                        </ol>
                        <h4 class="font-weight-bolder mb-0">Manage Class</h4>
                    </nav>

                    <div class="action-bar">

                        <!-- Form tìm kiếm và lọc -->
                        <form class="filter-form d-flex" method="get" action="listClass">
                            <input type="text" id="search" name="search" class="form-control"
                                   placeholder="Search by class name..."
                                   value="${param.search}" onchange="this.form.submit()">

                            <select name="filterLevel" class="form-select" onchange="this.form.submit()">
                                <option value="0">All Levels</option>
                                <c:forEach var="classLevel" items="${listClassLevel}">
                                    <option value="${classLevel.classLevelId}"
                                            <c:if test="${classLevel.classLevelId == param.filterLevel}">selected</c:if>>
                                            ${classLevel.classLevelName}
                                    </option>
                                </c:forEach>
                            </select>

                            <%-- <button type="submit" class="btn btn-secondary">Search</button>--%>
                        </form>
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
                                    <h6 class="text-white text-capitalize ps-3">Class List</h6>
                                </div>
                            </div>
                            <div class="container table-container">
                                <h2 class="text-center">List of Classes</h2>

                                <% if (request.getAttribute("deleteSuccessful") != null) { %>
                                <div class="alert alert-success">
                                    <%= request.getAttribute("deleteSuccessful") %>
                                </div>

                                </p>
                                <% } %>
                                <% if (request.getAttribute("updateSuccessful") != null) { %>
                                <div class="alert alert-success"><%= request.getAttribute("updateSuccessful") %>
                                </div>
                                <% } %>
                                <% if (request.getAttribute("createSuccessful") != null) { %>
                                <div class="alert alert-success"><%= request.getAttribute("createSuccessful") %>
                                </div>
                                <% } %>

                                <% if (request.getAttribute("deleteFalse") != null) { %>
                                <div class="alert alert-danger"><%= request.getAttribute("deleteFalse") %>
                                </div>
                                <% } %>

                                <!-- Thanh tìm kiếm và lọc -->
                                <div class="action-bar">
                                    <a href="createClass" class="btn btn-primary">Create New Class</a>
                                    <!-- Form tìm kiếm và lọc -->
                                </div>

                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>Class Name</th>
                                        <th>Class Level</th>
                                        <th>Age</th>
                                        <th>Teacher</th>
                                        <th>Room</th>
                                        <th>Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!-- Duyệt qua danh sách lớp học -->
                                    <c:forEach var="classes" items="${listClass}">
                                        <tr>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/Views/Manager/listStudentInClass?classId=${classes.classId}">${classes.className}</a>
                                            </td>
                                            <td>${classes.classLevelName} </td>
                                            <td>${classes.description} </td>
                                            <td>${classes.fullname}</td>
                                            <td>${classes.roomNumber}</td>
                                            <td>
                                                <a href="updateClass?classId=${classes.classId}"
                                                   class="btn btn-warning btn-sm">Edit</a>
                                                <a href="classDetail?classId=${classes.classId}"
                                                   class="btn btn-warning btn-sm">Detail</a>
                                                <a href="#" class="btn btn-danger btn-sm"
                                                   onclick="deleteClass(${classes.classId})">Delete</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </main>

    </div>
</div>

<script>
    function deleteClass(classId) {
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = 'deleteClass?classId=' + classId;
            }
        });
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<%@include file="/Views/common/footer.jsp" %>
</body>
</html>

