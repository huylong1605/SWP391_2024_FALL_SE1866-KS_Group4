
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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
    <link id="pagestyle" href="${pageContext.request.contextPath}/css/material-dashboard.css?v=3.1.0" rel="stylesheet" />
    <style>
        a.sidebar-link:hover {
            background-color: #0303aa;
            border-left: 3px solid white;
            color: white;
        }
        .table-responsive {
            max-height: 445px;
            overflow-y: auto;
        }
        table {
            width: 100%;
            table-layout: fixed;
        }

        .table tbody tr td {
            padding: 0.3rem; /* Padding nhỏ hơn */
        }
        .table tbody tr {
            min-height: 30px; /* Chiều cao tối thiểu */
        }
        .table {
            font-size: 0.85rem; /* Kích thước chữ nhỏ hơn */
        }


        .wrapper{
            height: 860px;
        }

        .toast-container {
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 9999;
            padding-top: 10px;
        }

        .toast {
            min-width: 250px;
            opacity: 0.9;
            transition: opacity 0.3s ease-in-out;
        }

        .toast-header {
            border-radius: 5px 5px 0 0;
        }
        .toast-body {
            border-radius: 0 0 5px 5px;
        }
        /* Thêm hiệu ứng để toast mượt mà hơn */
        .toast.show {
            opacity: 1;
        }

    </style>
</head>
<body class="g-sidenav-show bg-gray-200">
<%@include file="../common/header.jsp"%>
<div class="containerAll">
    <div class="wrapper">
        <%@include file= "/Views/common/sidebar_manage.jsp"%>
        <div class="container">
            <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-12">
                <!-- Navbar -->
                <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur" data-scroll="true">
                    <div class="d-flex py-1 px-3 justify-content-between align-items-center" style="width: 100%;">
                        <nav aria-label="breadcrumb">
                            <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
                                <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark" href="javascript:;">Pages</a></li>
                                <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Teacher</li>
                            </ol>
                            <h4 class="font-weight-bolder mb-0">Teacher Attendance</h4>
                        </nav>

                    </div>
                </nav>
                <!-- End Navbar -->
                <div class="container-fluid py-4">
                    <div class="row">
                        <form action="${pageContext.request.contextPath}/Views/Teacher/attendStudent" method="post">
                            <input type="hidden" name="classId" value="${classId}">
                            <input type="hidden" name="date" value="${date}">
                            <input type="hidden" name="slotId" value="${slotId}">
                            <input type="hidden" name="className" value="${className}"> <!-- Thêm dòng này -->
                            <input type="hidden" name="slotName" value="${slotName}">
                            <div class="col-12">
                                <div class="card my-4">
                                    <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                        <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3 d-flex">
                                            <div>
                                                <h5 class="text-white text-capitalize ps-3">Class: <span style="font-size: 20px">${className}</span></h5>
                                                <h5 class="text-white text-capitalize ps-3">Date: <span style="font-size: 20px">${date}</span></h5>
                                                <h5 class="text-white text-capitalize ps-3">Slot: <span style="font-size: 20px">${slotName}</span></h5>
                                            </div>
                                            <div style="padding-left: 20%">
                                                <h5 class="text-white text-capitalize ps-3">Present: <span style="font-size: 20px">${presentCount}</span></h5>
                                                <h5 class="text-white text-capitalize ps-3">Absent: <span style="font-size: 20px">${absentCount}</span></h5>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="card-body px-0 pb-2">
                                        <div class="table-responsive p-0">

                                            <c:choose>
                                                <c:when test="${empty attendanceList}">
                                                    <div class="text-center text-danger">
                                                        <p>Dont have any schedule !!!</p>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>

                                                    <table class="table align-items-center mb-0 table-responsive">
                                                        <thead>
                                                        <tr>
                                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">STT</th>
                                                                <%--                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Student ID</th>--%>
                                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Student Name</th>
                                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Date of Birth</th>
                                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Attendance Status</th>
                                                            <th class="text-secondary opacity-7"></th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach var="student" items="${attendanceList}" varStatus="status">
                                                            <tr style="font-weight: bold">
                                                                <td class="text-center">${status.index + 1}</td>
                                                                    <%--                                                        <td class="text-center">${student.studentId}</td>--%>
                                                                <td class="text-center">${student.studentName}</td>
                                                                <td class="text-center">${student.dateOfBirth}</td>
                                                                <td class="text-center">
                                                            <span id="statusText_${student.studentId}" style="color: ${student.attendStatus ? 'green' : 'red'};">
                                                                    ${student.attendStatus ? 'Present' : 'Absent'}
                                                            </span>
                                                                    <input type="hidden" id="attendStatus_${student.studentId}" name="attendStatus" value="${student.attendStatus}">
                                                                    <input type="hidden" name="studentId" value="${student.studentId}">
                                                                </td>
                                                                <td class="text-center">
                                                                    <button style="margin-top: 15px; font-size: 10px" type="button" class="btn btn-warning" onclick="toggleAttendance(${student.studentId})">
                                                                        Take Attendance
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                        </tbody>
                                                    </table>

                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="text-center d-flex align-content-around">
                                <a href="${pageContext.request.contextPath}/Views/Teacher/teacherSchedule?teacherId=${sessionScope.user.userID}" class="btn btn-primary">Back To Schedule</a>
                                <button type="submit" class="btn btn-primary" style="background-color: #4b4bff; margin-left: 15px">Save Attendance</button>
                                <a href="${pageContext.request.contextPath}/Views/Teacher/sendAbsenceNotifications?classId=${classId}&date=${date}&slotId=${slotId}&className=${className}&slotName=${slotName}" class="btn btn-primary" style="background-color: red; margin-left: 15px">Notification Parent</a>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div>

    </div>
</div>

<div class="toast-container text-center">
    <div id="successToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3000">
        <div class="toast-header">
            <strong style="font-size: 24px" class="me-auto text-success">Successfully</strong>
            <small class="text-muted">just now</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
            ${sessionScope.successMessage}
        </div>
    </div>
</div>

<div class="toast-container text-center">
    <div id="errorToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3000">
        <div class="toast-header">
            <strong style="font-size: 24px" class="me-auto text-danger">Error</strong>
            <small class="text-muted">just now</small>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
            ${sessionScope.errorMessage}
        </div>
    </div>
</div>

<script>
    function toggleAttendance(studentId) {
        // Lấy phần tử hiển thị trạng thái và giá trị trạng thái hiện tại
        var attendanceStatusElement = document.getElementById("attendStatus_" + studentId);
        var statusTextElement = document.getElementById("statusText_" + studentId);

        // Cập nhật trạng thái
        var currentStatus = attendanceStatusElement.value === "true";
        var newStatus = !currentStatus;
        attendanceStatusElement.value = newStatus;

        // Cập nhật văn bản và màu sắc dựa trên trạng thái mới
        statusTextElement.textContent = newStatus ? "Present" : "Absent";
        statusTextElement.style.color = newStatus ? "green" : "red";
    }


    // Hiển thị Toast nếu có thông báo thành công trong session
    $(document).ready(function() {
        <c:if test="${not empty sessionScope.errorMessage}">
        $('#errorToast').toast('show');
        <%-- Xóa thông báo sau khi hiển thị --%>
        <c:remove var="errorMessage" scope="session" />
        </c:if>
    });

    $(document).ready(function() {
        <c:if test="${not empty sessionScope.successMessage}">
        $('#successToast').toast('show');
        <%-- Xóa thông báo sau khi hiển thị --%>
        <c:remove var="successMessage" scope="session" />
        </c:if>
    });
</script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5/7mA3J+O2/7V/KPj8o3cp7V/e4URK5M8nqFf1x7" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<%@include file="../common/footer.jsp"%>
</body>
</html>
