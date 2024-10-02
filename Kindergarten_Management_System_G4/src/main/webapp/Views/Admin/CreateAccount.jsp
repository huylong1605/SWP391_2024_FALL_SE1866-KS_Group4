<%--
  Created by IntelliJ IDEA.
  User: chuc2
  Date: 9/30/2024
  Time: 2:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
      </ul>

    </aside>
    <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
      <c:if test="${not empty errorMessage}">
        <div id="failed-alert-create" style="width: 93%; background-color: #bf0606" class="alert alert-danger text-light text-center mx-auto" role="alert">
            ${errorMessage}
        </div>
      </c:if>
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
        </div>
      </nav>
      <!-- End Navbar -->
      <div class="container-fluid py-4">
        <div class="row">
          <div class="col-12">
            <div class="card my-4">
              <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                  <h6 class="text-white text-capitalize ps-3">Create Account</h6>
                </div>
              </div>
              <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                  <form action="${pageContext.request.contextPath}/Views/Admin/accountManage/Create" method="post">
                    <input type="hidden" name="action" value="create"/>

                    <div class="form-group" style="width: 95%; padding-left: 5%">
                      <label for="fullname">Full Name</label>
                      <input type="text" class="form-control" id="fullname" name="fullname" required />
                    </div>

                    <div class="form-group" style="width: 95%; padding-left: 5%">
                      <label for="email">Email</label>
                      <input type="email" class="form-control" id="email" name="email" required />
                    </div>

                    <div class="form-group" style="width: 95%; padding-left: 5%">
                      <label for="role">Role</label>
                      <select class="form-control" id="role" name="role" required>
                        <option value="2">Teacher</option>
                        <option value="3">Principal</option>
                        <option value="4">Parent</option>
                        <option value="5">Enrollment</option>
                      </select>
                    </div>

                    <button class="btn btn-primary mt-3" type="submit" style="margin-left: 5%">Create Account</button>
                  </form>
                </div>
              </div>


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

    xhr.onload = function() {
      if (xhr.status === 200) {
        const currentColor = element.querySelector('i').style.color;
        element.querySelector('i').style.color = currentColor === 'rgb(2, 159, 2)' ? 'red' : '#029f02';
        const statusText = element.closest('tr').querySelector('td:nth-child(5)');
        statusText.textContent = statusText.textContent === 'Active' ? 'Deactive' : 'Active';
        statusText.style.color = statusText.style.color === 'lawngreen' ? 'red' : 'lawngreen';
      } else {
        console.error('Error toggling status: ' + xhr.statusText);
      }
    };
    xhr.send("userId=" + userId);
  }
</script>

<script>
  window.onload = function() {
    var alert = document.getElementById("failed-alert-create");
    if (alert) {
      setTimeout(function() {
        alert.style.display = 'none';
      }, 3000);
    }
  };
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<%@include file="../common/footer.jsp"%>
</body>
</html>
