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
<%@include file="../common/header.jsp" %>
<div class="containerAll">
    <div class="wrapper">
        <aside id="sidebar" class="expand">
            <div class="d-flex">
                <button class="toggle-btn" type="button">
                    <i class="lni lni-grid-alt"></i>
                </button>
                <div class="sidebar-logo">
                    <a href="#">Admin Manage</a>
                </div>
            </div>
            <ul class="sidebar-nav">
                <li class="sidebar-item">
                    <a href="${pageContext.request.contextPath}/Views/Admin/accountManage" class="sidebar-link">
                        <i class="lni lni-user"></i>
                        <span>Manage Account</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="${pageContext.request.contextPath}/Views/Admin/notifications" class="sidebar-link">
                        <i class="fa-solid fa-bell"></i>
                        <span>Manage Notification</span>
                    </a>
                </li>

            </ul>
        </aside>

        <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
            <!-- Navbar -->
            <c:if test="${not empty sessionScope.successMessage}">
                <div id="success-alert-create" style="width: 93%; background-color: #06bf06"
                     class="alert alert-success text-light text-center mx-auto" role="alert">
                        ${sessionScope.successMessage}
                    <c:remove var="successMessage" scope="session"/>
                </div>
            </c:if>
            <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur"
                 data-scroll="true">
                <div class="d-flex py-1 px-3 justify-content-between align-items-center" style="width: 100%;">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark"
                                                                   href="javascript:;">Pages</a></li>
                            <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Account</li>
                        </ol>
                        <h4 class="font-weight-bolder mb-0">Manage Account</h4>
                    </nav>

                    <div class="btn-search" style="margin-top: 20px;">
                        <form action="${pageContext.request.contextPath}/Views/Admin/accountManage" method="get">
                            <input type="text" name="searchName" placeholder="Search by name"
                                   value="${param.searchName}">
                            <input type="hidden" name="action" value="search">
                            <button type="submit">Search</button>
                        </form>
                    </div>

                    <div style="margin-top: 20px;">
                        <form action="${pageContext.request.contextPath}/Views/Admin/accountManage" method="get">
                            <label for="roleFilter">Filter by role:</label>
                            <select name="roleFilter" id="roleFilter" onchange="this.form.submit()">
                                <option value="">All</option>
                                <option value="1" ${param.roleFilter == '1' ? 'selected' : ''}>Admin</option>
                                <option value="2" ${param.roleFilter == '2' ? 'selected' : ''}>Teacher</option>
                                <option value="3" ${param.roleFilter == '3' ? 'selected' : ''}>Principal</option>
                                <option value="4" ${param.roleFilter == '4' ? 'selected' : ''}>Parent</option>
                                <option value="5" ${param.roleFilter == '5' ? 'selected' : ''}>Enrollment</option>
                            </select>
                            <input type="hidden" name="action" value="filter">
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
                                    <h6 class="text-white text-capitalize ps-3">Account Table</h6>
                                </div>
                            </div>
                            <div class="card-body px-0 pb-2">
                                <div class="table-responsive p-0">
                                    <table class="table align-items-center mb-0">
                                        <thead>
                                        <tr>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                User Id
                                            </th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">
                                                Role
                                            </th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                Full Name
                                            </th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                Email
                                            </th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                Status
                                            </th>
                                            <th class="text-secondary opacity-7"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="user" items="${accounts}">
                                            <tr>
                                                <td class="text-center">${user.userID}</td>
                                                <td class="text-center">
                                                    <c:choose>
                                                        <c:when test="${user.roleId == 1}">Admin</c:when>
                                                        <c:when test="${user.roleId == 2}">Teacher</c:when>
                                                        <c:when test="${user.roleId == 3}">Principal</c:when>
                                                        <c:when test="${user.roleId == 4}">Parent</c:when>
                                                        <c:when test="${user.roleId == 5}">Enrollment</c:when>
                                                        <c:otherwise>Unknown</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="text-center">${user.fullname}</td>
                                                <td class="text-center">${user.email}</td>
                                                <td class="align-middle text-center">
                                                    <c:choose>
                                                        <c:when test="${user.status == 1}">
                                                            <span style="color: lawngreen; font-weight: bold">Active</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span style="color: red; font-weight: bold">Deactive</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="align-middle">
                                                    <a href="javascript:void(0);"
                                                       onclick="toggleStatus(${user.userID}, this)"
                                                       class="text-secondary font-weight-bold text-xs"
                                                       data-toggle="tooltip" data-original-title="Toggle status">
                                                        <i class="fa-solid fa-toggle-on"
                                                           style="color: ${user.status == 1 ? '#029f02' : 'red'}; font-size: 30px; margin: 5px"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/Views/Admin/accountManage/Detail?userId=${user.userID}"
                                                       class="text-secondary font-weight-bold text-xs"
                                                       data-toggle="tooltip" data-original-title="Edit user">
                                                        <i class="fa-solid fa-circle-info"
                                                           style="color: blue; font-size: 30px; margin: 5px"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-5">
                                <button class="btn btn-primary">
                                    <a href="${pageContext.request.contextPath}/Views/Admin/CreateAccount.jsp"
                                       class="text-light">Create Account</a>
                                </button>
                            </div>
                            <nav class="col-md-7" aria-label="Page navigation">
                                <ul class="pagination">
                                    <c:if test="${currentPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/Views/Admin/accountManage?pageNumber=${currentPage - 1}&searchName=${param.searchName}&roleFilter=${param.roleFilter}"
                                               aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </a>
                                        </li>
                                    </c:if>

                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/Views/Admin/accountManage?pageNumber=${i}&searchName=${param.searchName}&roleFilter=${param.roleFilter}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <c:if test="${currentPage < totalPages}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/Views/Admin/accountManage?pageNumber=${currentPage + 1}&searchName=${param.searchName}&roleFilter=${param.roleFilter}"
                                               aria-label="Next">
                                                <span aria-hidden="true">&raquo;</span>
                                            </a>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>

            </div>
        </main>

    </div>
</div>
<script>
    function toggleStatus(userId, element) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "${pageContext.request.contextPath}/Views/Admin/accountManage", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onload = function () {
            if (xhr.status === 200) {
                // Lấy icon và statusText từ DOM
                const icon = element.querySelector('i');
                const statusText = element.closest('tr').querySelector('td:nth-child(5)');

                // Thay đổi màu sắc của icon
                if (icon.style.color === 'rgb(2, 159, 2)') {
                    icon.style.color = 'red';
                    statusText.textContent = 'Deactive';
                    statusText.style.color = 'red';
                } else {
                    icon.style.color = '#029f02';
                    statusText.textContent = 'Active';
                    statusText.style.color = 'lawngreen';
                }
            } else {
                console.error('Error toggling status: ' + xhr.statusText);
            }
        };
        xhr.send("userId=" + userId);
    }
</script>

<script>
    window.onload = function () {
        var alert = document.getElementById("success-alert-create");
        if (alert) {
            setTimeout(function () {
                alert.style.display = 'none';
            }, 3000);
        }
    };
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous">
</script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<%@include file="../common/footer.jsp" %>
</body>
</html>
