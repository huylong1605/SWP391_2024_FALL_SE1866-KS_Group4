<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Manage Notifications</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
  <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
  <link href="${pageContext.request.contextPath}/css/nucleo-icons.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/nucleo-svg.css" rel="stylesheet" />
  <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet">
  <link id="pagestyle" href="${pageContext.request.contextPath}/css/material-dashboard.css?v=3.1.0" rel="stylesheet" />
</head>

<body class="g-sidenav-show  bg-gray-200">
<%@include file="../common/header.jsp"%>
<div class="wrapper">
  <aside id="sidebar">
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
        <a href="${pageContext.request.contextPath}/manageAccount.jsp" class="sidebar-link">
          <i class="lni lni-user"></i>
          <span>Manage Account</span>
        </a>
      </li>
      <li class="sidebar-item">
        <a href="notifications" class="sidebar-link">
          <i class="lni lni-agenda"></i>
          <span>Manage Students</span>
        </a>
      </li>
    </ul>
  </aside>

  <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
    <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur" data-scroll="true">
      <div class="d-flex py-1 px-3 justify-content-between align-items-center" style="width: 100%;">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-dark" href="javascript:;">Pages</a></li>
            <li class="breadcrumb-item text-sm text-dark active" aria-current="page">Students</li>
          </ol>
          <h4 class="font-weight-bolder mb-0">Manage Students</h4>
        </nav>





      </div>
    </nav>

    <div class="container-fluid py-4">
      <div class="row">
        <div class="col-12">
          <div class="card my-4">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
              <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                <h6 class="text-white text-capitalize ps-3">Students</h6>
              </div>
            </div>
            <div class="card-body px-0 pb-2">
              <div class="table-responsive p-0">
                <table class="table align-items-center mb-0">
                  <thead>
                  <tr>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">ID</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Name</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">DOB</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Gender</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Address</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Phone Number</th>
                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Actions</th>
                  </tr>
                  </thead>
                  <tbody>
<c:set var="counter" value="1" />
                   <c:forEach var="student" items="${students}">
                      <tr>
                     <td><p class="text-xs font-weight-bold mb-0">${counter}</p></td>
                      <td> <p class="text-xs font-weight-bold mb-0">${student.name}</p> </td>
                      <td><p class="text-xs font-weight-bold mb-0">${student.dob}</p></td>
                       <td><p class="text-xs font-weight-bold mb-0">${student.gender ? 'Male' : 'Female'}</p></td>
                       <td><p class="text-xs font-weight-bold mb-0">${student.address}</td>
                       <td><p class="text-xs font-weight-bold mb-0">${student.phoneNumber}</td>
                       <td><p style:" cursor: pointer;" class="text-secondary font-weight-bold text-xs" onclick="showModal('${student.studentId}', '${student.name}', '${student.dob}', '${student.gender ? 'Male' : 'Female'}', '${student.address}', '${student.phoneNumber}')">View</p></td>
                      </tr>
                       <c:set var="counter" value="${counter + 1}" />
                    </c:forEach>
                  </tbody>
                </table>
                <div class="d-flex justify-content-center mt-4">
                  <c:if test="${totalPages > 1}">
                    <ul class="pagination">
                      <c:if test="${currentPage > 1}">
                        <li class="page-item">
                          <a class="page-link" href="?page=${currentPage - 1}" aria-label="Previous">
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
                          <a class="page-link" href="?page=${currentPage + 1}" aria-label="Next">
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
<!-- Modal -->
<div id="studentModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2>Student Information</h2>
        <p><strong>ID:</strong> <span id="modalStudentId"></span></p>
        <p><strong>Name:</strong> <span id="modalStudentName"></span></p>
        <p><strong>DOB:</strong> <span id="modalStudentDob"></span></p>
        <p><strong>Gender:</strong> <span id="modalStudentGender"></span></p>
        <p><strong>Address:</strong> <span id="modalAddress"></span></p>
        <p><strong>Phone Number:</strong> <span id="modalPhoneNumber"></span></p>
    </div>
</div>

<script>
    function showModal(studentId, name, dob, gender, address, phoneNumber) {
        document.getElementById('modalStudentId').innerText = studentId;
        document.getElementById('modalStudentName').innerText = name;
        document.getElementById('modalStudentDob').innerText = dob;
        document.getElementById('modalStudentGender').innerText = gender;
        document.getElementById('modalAddress').innerText = address;
        document.getElementById('modalPhoneNumber').innerText = phoneNumber;
        const modal = document.getElementById('studentModal');
           modal.style.display = "block";
           modal.style.width = "50%";
           modal.style.left = "50%";
           modal.style.top = "70%";
           modal.style.transform = "translate(-50%, -50%)";
    }

    function closeModal() {
        document.getElementById('studentModal').style.display = "none";
    }

    // Close modal when clicking outside of it
    window.onclick = function(event) {
        const modal = document.getElementById('studentModal');
        if (event.target === modal) {
            modal.style.display = "none";
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
