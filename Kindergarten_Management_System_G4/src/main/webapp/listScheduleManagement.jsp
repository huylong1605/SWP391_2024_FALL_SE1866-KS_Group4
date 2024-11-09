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
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
    <!-- Font Awesome CSS for icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <!-- CSS Files -->
    <link id="pagestyle" href="${pageContext.request.contextPath}/css/material-dashboard.css?v=3.1.0" rel="stylesheet"/>
    <style>
        .header-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
        }

        .form-group {
            margin: 0 10px;
            width: auto;
        }

        .combo-boxes {
            display: flex;
            align-items: center;
        }

        .schedule-list {
            margin-top: 40px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
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
            height: 1060px;
        }
    </style>
</head>
<body class="g-sidenav-show bg-gray-200">
<%@include file="/Views/common/header.jsp" %>
<div class="containerAll">
    <div class="wrapper">
        <%@include file= "/Views/common/sidebar_manage.jsp"%>
        <div class="container">
            <div class="header-section">
                <div class="combo-boxes">
                    <!-- Combo box for selecting class -->
                    <form action="listSchedule" method="get" class="d-flex align-items-center">
                        <div class="form-group mr-2">
                            <label for="classSelect" class="sr-only">Select Class</label>
                            <select id="classSelect" name="classSelect" class="form-control form-control-sm"
                                    onchange="this.form.submit()">
                                <option value="" disabled selected>Select Class</option>
                                <c:forEach var="listclasses" items="${Classes}">
                                    <option value="${listclasses.classId}"
                                            <c:if test="${listclasses.classId == param.classSelect}">selected</c:if>>
                                            ${listclasses.className}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <!-- Combo box for selecting date -->
                        <div class="form-group">
                            <label for="startDate" class="sr-only">Start Date</label>
                            <select id="startDate" name="startDate" class="form-control form-control-sm" onchange="this.form.submit()">
                                <!-- Tùy chọn mặc định cho Start Date -->
                                <option value="" <c:if test="${param.startDate == null || param.startDate == ''}">selected</c:if>>Start Date</option>
                                <c:forEach var="date" items="${dates}">
                                    <option value="${date}" <c:if test="${date == param.startDate}">selected</c:if>>${date}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <p>To</p>

                        <div class="form-group">
                            <label for="endDate" class="sr-only">End Date</label>
                            <select id="endDate" name="endDate" class="form-control form-control-sm" onchange="this.form.submit()">
                                <!-- Tùy chọn mặc định cho End Date -->
                                <option value="" <c:if test="${param.endDate == null || param.endDate == ''}">selected</c:if>>End Date</option>
                                <c:forEach var="date" items="${dates}">
                                    <option value="${date}" <c:if test="${date == param.endDate}">selected</c:if>>${date}</option>
                                </c:forEach>
                            </select>
                        </div>

                    </form>
                </div>
                <!-- Create new schedule button -->
                <a href="createSchedule" class="btn btn-primary btn-sm">Add class to Schedule</a>
            </div>
            <% if (request.getAttribute("EditScheduleSuccessful") != null) { %>
            <div class="alert alert-success" id="successMessage">
                <%= request.getAttribute("EditScheduleSuccessful") %>
            </div>
            <% } %>
            <!-- Display schedule list -->
            <div id="scheduleList" class="schedule-list">
                <div class="table-container">
                    <div>teacher: <span>${schedules[0].teacher}</span></div>
                    <table class="table table-bordered table-hover">
                        <thead class="thead-dark">
                        <tr>
                            <th>Day of the Week</th>
                            <th>Subject</th>
                            <th>Slot Time</th>
                            <th>Date</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="schedule" items="${schedules}">
                            <tr>
                                <td>${schedule.dayOfWeek}</td>
                                <td>${schedule.subject_name}</td>
                                <td class="slot-time">
                                        ${schedule.slotName}
                                    <div class="time-range">(${schedule.startTime} - ${schedule.endTime})</div>
                                </td>
                                <td>${schedule.dateOfDay}</td>
                                <td><a href="editSchedule?schedulesId=${schedule.scheduleId}"
                                       class="btn btn-warning btn-sm">Edit</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div>
</div>

<script>
    // Hàm để tạo danh sách tùy chọn ngày
    function populateDateOptions() {
        const endDate = document.getElementById('endDate');
        const today = new Date();
        const Datee = new Date('2026-12-31'); // Giới hạn ngày đến cuối năm 2026

        // Tạo tùy chọn từ hôm nay đến ngày 2026-12-31
        while (today <= Datee) {
            const option = document.createElement('option');
            option.value = today.toISOString().split('T')[0]; // Định dạng: YYYY-MM-DD
            option.textContent = option.value; // Hiển thị ngày cũng theo định dạng YYYY-MM-DD

            endDate.appendChild(option);

            // Tăng ngày lên 1
            today.setDate(today.getDate() + 1);
        }
    }

    function populateDateOptions() {
        const startDate = document.getElementById('startDate');
        const today = new Date();
        const Datee = new Date('2026-12-31'); // Giới hạn ngày đến cuối năm 2026

        // Tạo tùy chọn từ hôm nay đến ngày 2026-12-31
        while (today <= Datee) {
            const option = document.createElement('option');
            option.value = today.toISOString().split('T')[0]; // Định dạng: YYYY-MM-DD
            option.textContent = option.value; // Hiển thị ngày cũng theo định dạng YYYY-MM-DD

            startDate.appendChild(option);

            // Tăng ngày lên 1
            today.setDate(today.getDate() + 1);
        }
    }

    // Gọi hàm để tạo tùy chọn khi tải trang
    document.addEventListener('DOMContentLoaded', populateDateOptions);

    const successMessage = document.getElementById('successMessage');
    if (successMessage) {
        // Đặt timeout để ẩn thông báo sau 5 giây
        setTimeout(() => {
            successMessage.style.display = 'none';
        }, 5000); // 5000 milliseconds = 5 giây
    }
</script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<%@include file="/Views/common/footer.jsp" %>
</body>
</html>

