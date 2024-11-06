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
<%@include file="../common/header.jsp" %>

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

        </ul>
    </aside>

    <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
        <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur"
             data-scroll="true">
            <div class="d-flex py-1 px-3 justify-content-between align-items-center" style="width: 100%;">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                        <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark" href="javascript:;">Pages</a>
                        </li>
                        <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Notifications</li>
                    </ol>
                    <h4 class="font-weight-bolder mb-0">Manage Notifications</h4>
                </nav>
                <div class="btn-search" style="margin-top: 20px;">
                    <form action="${pageContext.request.contextPath}/searchNotification" method="get">
                        <input type="text" name="searchTitle" placeholder="Search by title"
                               value="${param.searchTitle}">
                        <input type="hidden" name="action" value="search">
                        <button type="submit">Search</button>
                    </form>
                </div>

                <div class="btn-search" style="margin-top: 20px;">

                    <div class="text-end">
                        <a href="${pageContext.request.contextPath}/addNotification.jsp" class="btn btn-primary">Add
                            Notification</a>

                    </div>

                </div>


            </div>
        </nav>

        <div class="container-fluid py-4">
            <div class="row">
                <div class="col-12">
                    <div class="card my-4">
                        <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                            <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                                <h6 class="text-white text-capitalize ps-3">Notifications</h6>
                            </div>
                        </div>
                        <div class="card-body px-0 pb-2">
                            <div class="table-responsive p-0">
                                <table class="table align-items-center mb-0">
                                    <thead>
                                    <tr>
                                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                            No
                                        </th>
                                        <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                            Title
                                        </th>
                                        <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                            Date
                                        </th>
                                        <th class="text-secondary opacity-7"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${not empty notifications}">
                                        <c:forEach var="notification" items="${notifications}" varStatus="index">
                                            <tr>
                                                <td>
                                                    <p class="text-xs font-weight-bold mb-0">${index.index + 1}</p>
                                                </td>

                                                <td>
                                                    <div class="d-flex px-2 py-1">
                                                        <div class="d-flex flex-column justify-content-center">
                                                            <h6 class="mb-0 text-sm">${notification.title}</h6>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td class="align-middle text-center">
                                                    <span class="text-secondary text-xs font-weight-bold">${notification.date}</span>
                                                </td>
                                                <td class="align-middle">
                                                    <a href="${pageContext.request.contextPath}/viewNotification?id=${notification.notificationId}"
                                                       class="text-secondary font-weight-bold text-xs"
                                                       data-toggle="tooltip" data-original-title="View notification">
                                                        View
                                                    </a>
                                                    |
                                                    <a href="${pageContext.request.contextPath}/editNotification?id=${notification.notificationId}"
                                                       class="text-secondary font-weight-bold text-xs"
                                                       data-toggle="tooltip" data-original-title="Edit notification">
                                                        Edit
                                                    </a>
                                                    |
                                                        <%--                                                    <a href="#"--%>
                                                        <%--                                                       onclick="confirmDelete(${notification.notificationId}); return false;"--%>
                                                        <%--                                                       class="text-secondary font-weight-bold text-xs"--%>
                                                        <%--                                                       data-toggle="tooltip" data-original-title="Delete notification">--%>
                                                        <%--                                                        Delete--%>
                                                        <%--                                                    </a>--%>
                                                    <!-- Button trigger modal -->
                                                    <a type="button" class="text-secondary font-weight-bold text-xs"
                                                       data-toggle="tooltip" data-original-title="Delete notification"
                                                       onclick="showDeleteModal(${notification.notificationId})">
                                                        Delete
                                                    </a>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>


                                    <%--                                    <script>--%>
                                    <%--                                        function confirmDelete(notificationId) {--%>
                                    <%--                                            if (confirm("Are you sure you want to delete this notification?")) {--%>
                                    <%--                                                // Redirect to the delete endpoint--%>
                                    <%--                                                window.location.href = "${pageContext.request.contextPath}/deleteNotification?id=" + notificationId;--%>
                                    <%--                                            }--%>
                                    <%--                                        }--%>
                                    <%--                                    </script>--%>

                                    </tbody>
                                </table>
                                <div class="d-flex justify-content-center mt-4">
                                    <c:if test="${totalPages > 1}">
                                        <ul class="pagination">
                                            <c:if test="${currentPage > 1}">
                                                <li class="page-item">
                                                    <a class="page-link" href="?page=${currentPage - 1}"
                                                       aria-label="Previous">
                                                        <span aria-hidden="true">&laquo;</span>
                                                    </a>
                                                </li>
                                            </c:if>

                                            <c:forEach var="i" begin="1" end="${totalPages}">
                                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                    <a class="page-link" href="?page=${i}">${i}</a>
                                                </li>
                                            </c:forEach>

                                            <c:if test="${currentPage < totalPages}">
                                                <li class="page-item">
                                                    <a class="page-link" href="?page=${currentPage + 1}"
                                                       aria-label="Next">
                                                        <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                </li>
                                            </c:if>
                                        </ul>
                                    </c:if>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Delete Confirmation Modal -->
        <div class="modal fade" id="deleteConfirmationModal" tabindex="-1" aria-labelledby="deleteConfirmationModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="deleteConfirmationModalLabel">Delete Confirmation</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Are you sure you want to delete this notification?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-danger" onclick="confirmDelete()">Delete</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
                crossorigin="anonymous">
        </script>
        <script src="${pageContext.request.contextPath}/js/script.js"></script>
        <script>
            let notificationIdToDelete = null;

            function showDeleteModal(notificationId) {
                notificationIdToDelete = notificationId; // Lưu notificationId để sử dụng khi xác nhận xóa
                const deleteModal = new bootstrap.Modal(document.getElementById('deleteConfirmationModal'));
                deleteModal.show();
            }

            function confirmDelete() {
                if (notificationIdToDelete) {
                    // Redirect to the delete endpoint
                    window.location.href = "${pageContext.request.contextPath}/deleteNotification?id=" + notificationIdToDelete;
                }
            }
        </script>
        <%@include file="../common/footer.jsp" %>
</body>
</html>
