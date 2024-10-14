
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
    /*    max-height: 425px; !*
    /*    overflow-y: auto; !*
    /*}*/
    /*table {*/
    /*    width: 100%;*/
    /*    table-layout: fixed;*/
    /*}*/
    .wrapper{
      height: 860px;
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
          <a href="#" class="sidebar-link">
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
            <form action="${pageContext.request.contextPath}/Views/Admin/accountManage" method="get">
              <input type="text" name="searchName" placeholder="Search by name" value="${param.searchName}">
              <input type="hidden" name="action" value="search">
              <button type="submit">Search</button>
            </form>
          </div>

          <div style="margin-top: 20px;">
            <form action="${pageContext.request.contextPath}/Views/Admin/accountManage" method="get">
              <label for="roleFilter">Filter by class:</label>
              <select name="roleFilter" id="roleFilter" onchange="this.form.submit()">
                <option value="">All</option>
              </select>
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
                  <h6 class="text-white text-capitalize ps-3">List Student</h6>
                </div>
              </div>
              <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                  <table class="table align-items-center mb-0">
                    <thead>
                    <tr>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Name</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Gender</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Age</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Class</th>
                      <th class="text-secondary opacity-7"></th>
                    </tr>
                    </thead>
                    <tbody>

                      <tr>
                        <td class="text-center">Ngoc Hung</td>
                        <td class="text-center">Male</td>
                        <td class="text-center">6</td>
                        <td class="text-center">MA123</td>
                        <td class="align-middle">
                          <a href="#" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            <i class="fa-solid fa-trash" style="color: red; font-size: 30px; margin: 5px"></i>
                          </a>
                          <a href="#" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            <i class="fa-solid fa-circle-info" style="color: blue; font-size: 30px; margin: 5px"></i>
                          </a>
                        </td>
                      </tr>

                      <tr>
                        <td class="text-center">Manh Tien</td>
                        <td class="text-center">Male</td>
                        <td class="text-center">4</td>
                        <td class="text-center">MA331</td>
                        <td class="align-middle">
                          <a href="#" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            <i class="fa-solid fa-trash" style="color: red; font-size: 30px; margin: 5px"></i>
                          </a>
                          <a href="#" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            <i class="fa-solid fa-circle-info" style="color: blue; font-size: 30px; margin: 5px"></i>
                          </a>
                        </td>
                      </tr>


                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-md-5">
                <button class="btn btn-primary">
                  <a href="#" class="text-light">Add Student</a>
                </button>
              </div>
            </div>
          </div>
        </div>

      </div>
    </main>

  </div>
</div>

<script>
  window.onload = function() {
    var alert = document.getElementById("success-alert-create");
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
