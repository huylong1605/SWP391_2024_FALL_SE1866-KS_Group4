
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
        /*.table-responsive {*/
        /*  height: 475px;*/
        /*}*/
        /*table {*/
        /*   width: 100%;*/
        /*   height: 800px;*/
        /*}*/
        .wrapper{
            height: 1200px;
        }
    </style>
</head>
<body class="g-sidenav-show bg-gray-200">
<%@include file="../common/header.jsp"%>
<div class="containerAll">
    <div class="wrapper">
        <%@include file= "/Views/common/sidebar_manage.jsp"%>
        <div class="container my-4">
            <h2 class="text-center">List Student Dont Have Class</h2>

            <!-- Hiển thị thông báo thành công hoặc lỗi -->
            <c:if test="${not empty sessionScope.message}">
                <div class="alert alert-success">${sessionScope.message}</div>
                <%
                    session.removeAttribute("message"); // Xóa thông báo sau khi hiển thị
                %>
            </c:if>
            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-danger">${sessionScope.error}</div>
                <%
                    session.removeAttribute("error"); // Xóa thông báo sau khi hiển thị
                %>
            </c:if>

            <div class="d-flex justify-content-around">
                <!-- Form chọn cấp lớp -->
                <form method="get" action="StudentDontHaveClass" class="mb-4">
                    <div class="row">
                        <div class="col-md-6">
                            <label for="classLevelId" class="form-label">Class Level:</label>
                            <select name="classLevelId" id="classLevelId" class="form-select" style="width: 100px">
                                <c:forEach items="${classLevels}" var="level">
                                    <option value="${level.classLevelId}" ${level.classLevelId == selectedClassLevel ? 'selected' : ''}>
                                            ${level.classLevelName}
                                        <c:if test="${level.ageRange != -1}">(${level.ageRange} years old)</c:if>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6 align-self-end" style="margin-top: 30px">
                            <button type="submit" class="btn btn-primary">Choose</button>
                        </div>
                    </div>
                </form>

                <!-- Form tìm kiếm -->
                <form method="get" action="StudentDontHaveClass" class="mb-4">
                    <div class="row">
                        <div class="col-md-6">
                            <label for="studentName" class="form-label">Search By Name:</label>
                            <input type="text" id="studentName" name="studentName" class="form-control" value="${studentName}">
                        </div>
                        <div class="col-md-6 align-self-end" style="margin-top: 30px">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                </form>
            </div>

            <!-- Danh sách học sinh -->
            <c:if test="${not empty studentsByLevel}">
                <form method="post" action="StudentDontHaveClass">
                    <input type="hidden" name="classLevelId" value="${selectedClassLevel}">
                    <table class="table table-bordered text-center" style="">
                        <thead class="table-dark text-light">
                        <tr>
                            <th>
                                <!-- Checkbox chọn tất cả học sinh -->
                                <input type="checkbox" id="selectAll" onclick="toggleSelectAll()" style="width: 25px; height: 25px">
                            </th>
                            <th>STT</th>
                            <th>Name Student</th>
                            <th>Gender</th>
                            <th>Age</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${studentsByLevel}" var="student" varStatus="status">
                            <tr>
                                <td>
                                    <input type="checkbox" name="studentIds" value="${student.studentId}" style="width: 20px; height: 20px">
                                </td>
                                <td class="text-center">${(currentPage - 1) * pageSize + status.index + 1}</td>
                                <td>${student.name}</td>
                                <td>${student.gender ? 'Male' : 'Female'}</td>
                                <td>${student.age}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <!-- Hiển thị tổng số học sinh -->
                    <p class="mt-3" style="font-weight: bold">Total student: <strong>${totalStudents}</strong></p>

                    <!-- Phân trang -->
                    <nav aria-label="Pagination">
                        <ul class="pagination justify-content-center">
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <!-- Thêm tham số tìm kiếm vào URL -->
                                    <a class="page-link" href="?classLevelId=${selectedClassLevel}&studentName=${studentName}&page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>


                    <!-- Chọn lớp học -->
                    <div class="mb-3">
                        <label for="classId" class="form-label">Choose Class:</label>
                        <select name="classId" id="classId" class="form-select">
                            <option value="" disabled selected>Choose Class</option>
                            <c:forEach items="${classesByLevel}" var="classObj">
                                <option value="${classObj.classId}">${classObj.className}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-success">Add Student To Class</button>
                </form>
            </c:if>

            <!-- Thông báo khi không có dữ liệu -->
            <c:if test="${empty studentsByLevel}">
                <p class="text-warning">Không có học sinh nào chưa có lớp ở cấp lớp đã chọn.</p>
            </c:if>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- JavaScript để chọn/deselect tất cả checkbox -->
<script>
    function toggleSelectAll() {
        var selectAllCheckbox = document.getElementById("selectAll");
        var checkboxes = document.getElementsByName("studentIds");

        // Duyệt qua tất cả các checkbox học sinh và thay đổi trạng thái của chúng
        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = selectAllCheckbox.checked;
        }
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<%@include file="../common/footer.jsp"%>
</body>
</html>
