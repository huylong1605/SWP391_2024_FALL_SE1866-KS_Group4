
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

    <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
      <!-- Navbar -->
      <c:if test="${not empty sessionScope.successMessage}">
        <div id="success-alert-create" style="width: 93%; background-color: #06bf06" class="alert alert-success text-light text-center mx-auto" role="alert">
            ${sessionScope.successMessage}
          <c:remove var="successMessage" scope="session" />
        </div>
      </c:if>
      <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur" data-scroll="true">
        <div class="d-flex py-1 px-3 justify-content-between align-items-center" style="width: 100%;">
          <nav aria-label="breadcrumb">
            <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
              <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark" href="javascript:;">Pages</a></li>
              <li class="breadcrumb-item text-sm text-dark active" aria-current="page">List Student</li>
            </ol>
            <h4 class="font-weight-bolder mb-0">Manage Class</h4>
          </nav>

          <div class="btn-search" style="margin-top: 20px;">
            <form action="${pageContext.request.contextPath}/Views/Manager/AddStudentToClass" method="get">
              <input type="text" name="searchTerm" placeholder="Search by name" value="${param.searchTerm}">
              <input type="hidden" name="classId" value="${classId}" />
              <input type="hidden" name="action" value="search">
              <button type="submit">Search</button>
            </form>
          </div>

            <div style="margin-top: 20px;">
                <form action="${pageContext.request.contextPath}/Views/Manager/AddStudentToClass" method="get">
                    <label for="classFilter">Filter by class:</label>
                    <select name="classFilter" id="classFilter" onchange="this.form.submit()">
                        <option value="">All</option> <!-- Option to display all students -->
                        <option value="withClass">Class IS Available</option>
                        <option value="noClass">Class NOT Available</option>
                    </select>
                    <input type="hidden" name="classId" value="${classId}" />
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
                  <h5 class="text-white text-capitalize ps-3">Add Student To <span style="font-size: 40px">${className}</span></h5>
                </div>
              </div>
              <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                  <c:choose>
                    <c:when test="${empty AllStudent}">
                      <div class="text-center text-danger">
                        <p>Dont have any student in this class !!</p>
                      </div>
                    </c:when>
                    <c:otherwise>
                      <table class="table align-items-center mb-0 table-responsive">
                        <thead>
                        <tr>
                          <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Number</th>
                          <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Name</th>
                          <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Gender</th>
                          <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Age</th>
                          <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Classes available</th>
                          <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Class Name</th>
                          <th class="text-secondary opacity-7"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="student" items="${AllStudent}" varStatus="status">
                          <tr style="font-weight: bold">
                            <td class="text-center">${(currentPage - 1) * pageSize + status.index + 1}</td>
                            <td class="text-center">${student.name}</td>
                            <td class="text-center">${student.gender? "<span style='color:Magenta'>Male</span>" : "<span style='color:blue'>Female</span>"}</td>
                            <td class="text-center">${student.age}</td>
                            <c:if test="${student.classId == 0}">
                              <td class="text-center" style="color: red"><i class="fa-solid fa-circle-xmark" style="font-size: 30px"></i></td>
                            </c:if>
                            <c:if test="${student.classId != 0}">
                              <td class="text-center" style="color: #039f03"><i class="fa-solid fa-circle-check" style="font-size: 30px"></i></td>
                            </c:if>
                            <c:if test="${student.classId == 0}">
                              <td class="text-center" style="color: red">###</td>
                            </c:if>
                            <c:if test="${student.classId != 0}">
                              <td class="text-center" style="color: #039f03">${student.className}</td>
                            </c:if>
                            <td class="align-middle">
                              <form action="${pageContext.request.contextPath}/Views/Manager/AddStudentToClass" method="post" style="display:inline;">
                                <input type="hidden" name="classId" value="${classId}" />
                                <input type="hidden" name="studentId" value="${student.studentId}" />
                                <button style="margin-top: 15px" type="button" class="text-light btn btn-primary text-secondary font-weight-bold text-xs"
                                        onclick="handleAddToClass(${student.classId}, ${student.studentId}, ${classId})">
                                  ADD
                                </button>
                              </form>
                              <a href="${pageContext.request.contextPath}/Views/Manager/StudentDetails?action=details&studentId=${student.studentId}" style="margin-top: 14px" class="text-light btn btn-primary text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                                Details
                              </a>
                            </td>
                          </tr>
                        </c:forEach>
                        </tbody>
                      </table>
                    </c:otherwise>
                  </c:choose>

                  <c:set var="actionValue" value="${isFiltered ? 'filter' : 'add'}" />
                  <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-4">
                      <c:if test="${currentPage > 1}">
                        <li class="page-item">
                          <a class="page-link" href="${pageContext.request.contextPath}/Views/Manager/AddStudentToClass?classId=${classId}&page=${currentPage - 1}&size=${pageSize}&action=${actionValue}&classFilter=${filterType}&searchTerm=${searchTerm}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                          </a>
                        </li>
                      </c:if>
                      <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                          <a class="page-link" href="${pageContext.request.contextPath}/Views/Manager/AddStudentToClass?classId=${classId}&page=${i}&size=${pageSize}&action=${actionValue}&classFilter=${filterType}&searchTerm=${searchTerm}">${i}</a>
                        </li>
                      </c:forEach>
                      <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                          <a class="page-link" href="${pageContext.request.contextPath}/Views/Manager/AddStudentToClass?classId=${classId}&page=${currentPage + 1}&size=${pageSize}&action=${actionValue}&classFilter=${filterType}&searchTerm=${searchTerm}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                          </a>
                        </li>
                      </c:if>
                    </ul>
                  </nav>


                  <!-- Modal xác nhận chuyển lớp -->
                  <div class="modal fade" id="confirmChangeClassModal" tabindex="-1" aria-labelledby="confirmChangeClassModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title" id="confirmChangeClassModalLabel">Confirm Change Class</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                          The student already has a class. Do you want to change classes?
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                          <button type="button" class="btn btn-primary" id="confirmChangeClassBtn">Confirm</button>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Modal thông báo thêm thành công -->
                  <div class="modal fade" id="addSuccessModal" tabindex="-1" aria-labelledby="addSuccessModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title" id="addSuccessModalLabel">Add Success</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body" id="successMessage">
                          <!-- Thông điệp sẽ được thay đổi ở đây -->
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-primary" data-bs-dismiss="modal">OK</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <button class="btn btn-primary text-light">
              <a class="text-light" href="${pageContext.request.contextPath}/Views/Manager/listStudentInClass?classId=${classId}">Back To List Student</a>
            </button>
          </div>
        </div>
      </div>
    </main>
  </div>
</div>

<script>
  function handleAddToClass(currentClassId, studentId, targetClassId) {
    if (currentClassId !== 0) {
      // Hiển thị modal xác nhận chuyển lớp
      var confirmModal = new bootstrap.Modal(document.getElementById('confirmChangeClassModal'));
      confirmModal.show();

      // Xử lý khi người dùng xác nhận chuyển lớp
      document.getElementById('confirmChangeClassBtn').onclick = function () {
        // Gửi yêu cầu để chuyển lớp cho học sinh
        addStudentToClass(studentId, true, targetClassId); // Truyền targetClassId vào hàm
        // Đóng modal sau khi xác nhận
        confirmModal.hide();
      };
    } else {
      // Thêm học sinh vào lớp và hiển thị modal thành công
      addStudentToClass(studentId, false, targetClassId); // Truyền targetClassId vào hàm
    }
  }
  function addStudentToClass(studentId, isChange, classId) { // Nhận thêm classId
    // Gửi yêu cầu đến server bằng AJAX để thêm hoặc chuyển lớp
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '${pageContext.request.contextPath}/Views/Manager/AddStudentToClass', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function () {
      if (xhr.status === 200) {
        // Kiểm tra phản hồi từ server
        var response = JSON.parse(xhr.responseText);
        if (response.success) {
          // Hiển thị modal thêm thành công
          var successModal = new bootstrap.Modal(document.getElementById('addSuccessModal'));

          // Thiết lập thông điệp thành công dựa trên loại hành động
          if (isChange) {
            document.getElementById('successMessage').innerText = 'The student has successfully transferred classes!';
          } else {
            document.getElementById('successMessage').innerText = 'Student has been successfully added to the class!';
          }
          successModal.show();

          // Chuyển hướng về trang danh sách học sinh trong lớp học sau khi đóng modal
          successModal._element.addEventListener('hidden.bs.modal', function () {
            window.location.href = '${pageContext.request.contextPath}/Views/Manager/listStudentInClass?classId=' + classId; // Đường dẫn tới trang danh sách
          });
        } else {
          alert(response.message || 'Không thể thực hiện hành động này.');
        }
      } else {
        alert('Có lỗi xảy ra khi gửi yêu cầu. Vui lòng thử lại.');
      }
    };

    xhr.onerror = function () {
      alert('Lỗi kết nối. Vui lòng kiểm tra lại.');
    };

    xhr.send('studentId=' + studentId + '&isChange=' + isChange + '&classId=' + classId); // Gửi classId
  }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<%@include file="../common/footer.jsp"%>
</body>
</html>
