
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

<body class="g-sidenav-show  bg-gray-200">
<%@include file="../common/header.jsp"%>
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
                        <i class="lni lni-agenda"></i>
                        <span>Manage Notification</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="${pageContext.request.contextPath}/subject" class="sidebar-link">
                        <i class="lni lni-graduation"></i>
                        <span>Manage Subject</span>
                    </a>
                </li>
            </ul>

        </aside>
        <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
            <!-- Navbar -->
            <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur" data-scroll="true">
                <div class="d-flex py-1 px-3 justify-content-between align-items-center" style="width: 100%;">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark" href="javascript:;">Pages</a></li>
                            <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Account</li>
                        </ol>
                        <h4 class="font-weight-bolder mb-0">Manage Account</h4>
                    </nav>

                    <div class="btn-search" style="margin-top: 20px;">
                        <form action="${pageContext.request.contextPath}/Views/Admin/accountManage" method="post">
                            <input type="text" name="searchName" placeholder="Search by name">
                            <input type="hidden" name="action" value="search">
                            <button type="submit">Search</button>
                        </form>
                    </div>

                    <div style="margin-top: 20px;">
                        <label for="">Fillter:</label>
                        <select>
                            <option id="" value="">Teacher</option>
                            <option value="">Parent</option>
                            <option value="">Enrollment</option>
                        </select>
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
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">User Id</th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Role</th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Full Name</th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Email</th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Status</th>
                                            <th class="text-secondary opacity-7"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="user" items="${accounts}">
                                            <tr>
                                                <td class="text-center">${user.userID}</td>
                                                <c:choose>
                                                    <c:when test="${user.roleId == 1}">
                                                        <td class="text-center">Admin</td>
                                                    </c:when>
                                                    <c:when test="${user.roleId == 2}">
                                                        <td class="text-center">Teacher</td>
                                                    </c:when>
                                                    <c:when test="${user.roleId == 3}">
                                                        <td class="text-center">Principal</td>
                                                    </c:when>
                                                    <c:when test="${user.roleId == 4}">
                                                        <td class="text-center">Parent</td>
                                                    </c:when>
                                                    <c:when test="${user.roleId == 5}">
                                                        <td class="text-center">Enrollment</td>
                                                    </c:when>
                                                </c:choose>

                                                <td class="text-center">${user.fullname}</td>
                                                <td class="text-center">${user.email}</td>
                                                <c:choose>
                                                <c:when test="${user.status == 1}">
                                                <td class="align-middle text-center" style="color: lawngreen; font-weight: bold">
                                                    Active
                                                </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td class="align-middle text-center" style="color: red; font-weight: bold">
                                                        Deactive
                                                    </td>

                                                </c:otherwise>
                                                </c:choose>
                                                <td class="align-middle">
                                                    <a href="javascript:void(0);"
                                                       onclick="toggleStatus(${user.userID}, this)"
                                                       class="text-secondary font-weight-bold text-xs"
                                                       data-toggle="tooltip"
                                                       data-original-title="Toggle status">
                                                        <i class="fa-solid fa-toggle-on" style="color: ${user.status == 1 ? '#029f02' : 'red'}; font-size: 30px; margin: 5px"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/Views/Admin/accountManage/Detail?userId=${user.userID}" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                                                        <i class="fa-solid fa-circle-info" style="color: blue; font-size: 30px; margin: 5px"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>

                                </div>

                            </div>

                        </div>
                    </div>
                    <div>
                        <button class="btn btn-primary">
                            <a href="${pageContext.request.contextPath}/Views/Admin/CreateAccount.jsp" class="text-light">Create Account</a>
                        </button>
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

        xhr.onload = function() {
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

<%--<script>--%>
<%--    function toggleStatus(userId, element) {--%>
<%--        const xhr = new XMLHttpRequest();--%>
<%--        xhr.open("POST", "${pageContext.request.contextPath}/Views/Admin/accountManage", true);--%>
<%--        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");--%>

<%--        xhr.onload = function() {--%>
<%--            if (xhr.status === 200) {--%>
<%--                const currentColor = element.querySelector('i').style.color;--%>
<%--                element.querySelector('i').style.color = currentColor === 'rgb(2, 159, 2)' ? 'red' : '#029f02';--%>
<%--                const statusText = element.closest('tr').querySelector('td:nth-child(5)');--%>
<%--                statusText.textContent = statusText.textContent === 'Active' ? 'Deactive' : 'Active';--%>
<%--                statusText.style.color = statusText.style.color === 'lawngreen' ? 'red' : 'lawngreen';--%>
<%--            } else {--%>
<%--                console.error('Error toggling status: ' + xhr.statusText);--%>
<%--            }--%>
<%--        };--%>
<%--        xhr.send("userId=" + userId);--%>
<%--    }--%>
<%--</script>--%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<%@include file="../common/footer.jsp"%>
</body>
</html>
