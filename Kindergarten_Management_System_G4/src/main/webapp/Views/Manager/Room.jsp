<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin Manage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link id="pagestyle" href="${pageContext.request.contextPath}/css/material-dashboard.css?v=3.1.0" rel="stylesheet"/>
    <style>
        a.sidebar-link:hover {
            background-color: #0303aa;
            border-left: 3px solid white;
            color: white;
        }

        .wrapper {
            height: 860px;
        }

        .pagination {
            display: flex;
            justify-content: center;
            padding: 1rem;
        }

        .pagination a {
            margin: 0 0.5rem;
            padding: 0.5rem 1rem;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .pagination a:hover {
            background-color: #0056b3;
        }

        .pagination span {
            margin: 0 0.5rem;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            background-color: #6c757d;
            color: white;
        }
    </style>
</head>
<%
    String successMessage = (String) session.getAttribute("successMessage");
    if (successMessage != null) {
%>
<div class="alert alert-success">
    <%= successMessage %>
</div>
<%
        session.removeAttribute("successMessage");
    }
%>


<body class="g-sidenav-show bg-gray-200">
<%@include file="../common/header.jsp" %>
<div class="containerAll">
    <div class="wrapper">
        <%@include file= "/Views/common/sidebar_manage.jsp"%>
        <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
            <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur"
                 data-scroll="true">
                <div class="d-flex py-1 px-3 justify-content-between align-items-center" style="width: 100%;">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark"
                                                                   href="javascript:;">Pages</a></li>
                            <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Room Management
                            </li>
                        </ol>
                        <h4 class="font-weight-bolder mb-0">Manage Rooms</h4>
                    </nav>
                    <div class="btn-search" style="margin-top: 20px;">
                        <form action="${pageContext.request.contextPath}/searchRoom" method="get">
                            <input type="text" name="searchNumber" placeholder="Search by Room"
                                   value="${param.searchNumber}">
                            <input type="hidden" name="action" value="search">
                            <button type="submit">Search</button>
                        </form>
                    </div>
                    <div class="btn-search" style="margin-top: 20px;">
                        <form action="${pageContext.request.contextPath}/filterRoomByStatus" method="get">
                            <label for="status">Filter by Status:</label>
                            <select name="status" id="status">
                                <option value="">Status</option> <!-- Hiển thị tất cả các phòng -->
                                <option value="1" ${param.status == '1' ? 'selected' : ''}>Active</option>
                                <option value="0" ${param.status == '0' ? 'selected' : ''}>Inactive</option>
                            </select>
                            <button type="submit">Filter</button>
                        </form>
                    </div>

                    <div class="text-end">
                        <a href="${pageContext.request.contextPath}/addRoom.jsp" class="btn btn-primary">Create Room</a>
                    </div>
                </div>
            </nav>

            <div class="container-fluid py-4">
                <div class="row">
                    <div class="col-12">
                        <div class="card my-4">
                            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                                    <h6 class="text-white text-capitalize ps-3">Rooms</h6>
                                </div>
                            </div>
                            <div class="card-body px-0 pb-2">
                                <div class="table-responsive p-0">
                                    <table class="table align-items-center mb-0">
                                        <thead>
                                        <tr>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                STT
                                            </th>
                                            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                Room Number
                                            </th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                Status
                                            </th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                Capacity
                                            </th>
                                            <th class="text-secondary opacity-7"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:if test="${not empty rooms}">
                                            <c:forEach var="room" items="${rooms}">
                                                <tr>
                                                    <td><p class="text-xs font-weight-bold mb-0">${room.roomId}</p></td>
                                                    <td>
                                                        <div class="d-flex px-2 py-1">
                                                            <div class="d-flex flex-column justify-content-center">
                                                                <h6 class="mb-0 text-sm">${room.roomNumber}</h6>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td class="align-middle text-center">
                                                            <span class="text-secondary text-xs font-weight-bold">
                                                                <c:choose>
                                                                    <c:when test="${room.status == 1}">Active</c:when>
                                                                    <c:otherwise>Inactive</c:otherwise>
                                                                </c:choose>
                                                            </span>
                                                    </td>
                                                    <td class="align-middle text-center">
                                                        <span class="text-secondary text-xs font-weight-bold">${room.capacity}</span>
                                                    </td>
                                                    <td class="align-middle">
                                                        <a href="${pageContext.request.contextPath}/editRoom?id=${room.roomId}"
                                                           class="text-secondary font-weight-bold text-xs"
                                                           data-toggle="tooltip"
                                                           data-original-title="Edit room">Edit</a>
                                                        <a href="#" class="text-danger" data-bs-toggle="modal"
                                                           data-bs-target="#deleteModal"
                                                           data-room-id="${room.roomId}"
                                                           data-room-number="${room.roomNumber}">Delete
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${empty rooms}">
                                            <tr>
                                                <td colspan="5" class="text-center text-danger">No rooms found</td>
                                            </tr>
                                        </c:if>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="pagination">
                                    <c:if test="${currentPage > 1}">
                                        <a href="?page=${currentPage - 1}&searchNumber=${param.searchNumber}">&laquo;
                                            Previous</a>
                                    </c:if>

                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                        <c:choose>
                                            <c:when test="${i == currentPage}">
                                                <span>${i}</span> <!-- Current page -->
                                            </c:when>
                                            <c:otherwise>
                                                <a href="?page=${i}&searchNumber=${param.searchNumber}">${i}</a> <!-- Other pages -->
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>

                                    <c:if test="${currentPage < totalPages}">
                                        <a href="?page=${currentPage + 1}&searchNumber=${param.searchNumber}">Next
                                            &raquo;</a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal for Confirm Delete -->
            <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="deleteModalLabel">Confirm Delete</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            Are you sure you want to delete room number <strong id="roomNumber"></strong>?
                        </div>
                        <div class="modal-footer">
                            <form id="deleteRoomForm"
                                  action="${pageContext.request.contextPath}/deleteRoom?action=delete" method="post">
                                <input type="hidden" name="id" id="roomId"/>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
        integrity="sha384-oBqDVmMz4fnFO9gybD85cYke1HymkGGG7ZwnL3d7KAs7+8jF5cmJ7IzY8U2V9wFq"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js"
        integrity="sha384-4A7W0C3yoYVRv0GnTAYe/ae5U3lFYJmj7Bb0rX5KHg/POc/XZwRLPdohjP5h0B8e"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Script to populate modal with room details
    const deleteModal = document.getElementById('deleteModal');
    deleteModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget; // Button that triggered the modal
        const roomId = button.getAttribute('data-room-id');
        const roomNumber = button.getAttribute('data-room-number');

        // Update the modal's content
        const roomIdInput = deleteModal.querySelector('#roomId');
        const roomNumberLabel = deleteModal.querySelector('#roomNumber');

        roomIdInput.value = roomId;
        roomNumberLabel.textContent = roomNumber;
    });
</script>
</body>
</html>
