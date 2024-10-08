<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin Manage - Student List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/material-dashboard.css?v=3.1.0" rel="stylesheet" />
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
                    <a href="#">Admin Manage</a>
                </div>
            </div>
            <ul class="sidebar-nav">
                <li class="sidebar-item">
                    <a href="${pageContext.request.contextPath}/viewStudentList" class="sidebar-link">
                        <i class="lni lni-graduation"></i>
                        <span>View List Student</span>
                    </a>
                </li>
            </ul>
        </aside>
        <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
            <div class="container-fluid py-4">
                <div class="row">
                    <div class="col-12">
                        <div class="card my-4">
                            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                                    <h6 class="text-white text-capitalize ps-3">Student List</h6>
                                </div>
                            </div>
                            <div class="card-body px-0 pb-2">
                                <div class="table-responsive p-0">
                                    <table class="table align-items-center mb-0">
                                        <thead>
                                        <tr>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">#</th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Name</th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Date of Birth</th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Gender</th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Address</th>
                                            <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Phone Number</th>
                                            <th class="text-secondary opacity-7"></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:set var="counter" value="${(currentPage - 1) * 5 + 1}" />
                                        <c:forEach var="student" items="${students}">
                                            <tr>
                                                <td class="text-center">${counter}</td>
                                                <td class="text-center">${student.name}</td>
                                                <td class="text-center">${student.dob}</td>
                                                <td class="text-center">${student.gender ? 'Male' : 'Female'}</td>
                                                <td class="text-center">${student.address}</td>
                                                <td class="text-center">${student.phoneNumber}</td>
                                                <td class="text-center">
                                                    <p style="cursor: pointer;" class="text-secondary font-weight-bold text-xs"
                                                       onclick="showModal('${student.studentId}', '${student.name}', '${student.dob}', '${student.gender ? 'Male' : 'Female'}', '${student.address}', '${student.phoneNumber}')">View</p>
                                                </td>
                                            </tr>
                                            <c:set var="counter" value="${counter + 1}" />
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <nav class="col-md-7" aria-label="Page navigation">
                                <ul class="pagination">
                                    <c:if test="${currentPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="${pageContext.request.contextPath}/viewStudentList?pageNumber=${currentPage - 1}" aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </a>
                                        </li>
                                    </c:if>

                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/viewStudentList?pageNumber=${i}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <c:if test="${currentPage < totalPages}">
                                        <li class="page-item">
                                            <a class="page-link" href="${pageContext.request.contextPath}/viewStudentList?pageNumber=${currentPage + 1}" aria-label="Next">
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

<!-- Modal HTML -->
<div class="modal fade" id="studentModal" tabindex="-1" aria-labelledby="studentModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="studentModalLabel">Student Information</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="studentName" class="form-label">Name:</label>
                    <input type="text" class="form-control" id="studentName" readonly>
                </div>
                <div class="mb-3">
                    <label for="studentDob" class="form-label">Date of Birth:</label>
                    <input type="text" class="form-control" id="studentDob" readonly>
                </div>
                <div class="mb-3">
                    <label for="studentGender" class="form-label">Gender:</label>
                    <input type="text" class="form-control" id="studentGender" readonly>
                </div>
                <div class="mb-3">
                    <label for="studentAddress" class="form-label">Address:</label>
                    <input type="text" class="form-control" id="studentAddress" readonly>
                </div>
                <div class="mb-3">
                    <label for="studentPhoneNumber" class="form-label">Phone Number:</label>
                    <input type="text" class="form-control" id="studentPhoneNumber" readonly>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script>
    function showModal(studentId, name, dob, gender, address, phoneNumber) {
        // Gán các giá trị vào modal
        document.getElementById("studentName").value = name;
        document.getElementById("studentDob").value = dob;
        document.getElementById("studentGender").value = gender;
        document.getElementById("studentAddress").value = address;
        document.getElementById("studentPhoneNumber").value = phoneNumber;

        // Hiển thị modal
        var modal = new bootstrap.Modal(document.getElementById('studentModal'), {
            keyboard: false
        });
        modal.show();
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<%@include file="../common/footer.jsp"%>
</body>
</html>
