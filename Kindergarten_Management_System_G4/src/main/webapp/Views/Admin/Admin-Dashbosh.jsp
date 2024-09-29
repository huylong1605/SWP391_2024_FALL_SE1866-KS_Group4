
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
</head>

<body class="g-sidenav-show  bg-gray-200">
<%@include file="../common/header.jsp"%>
<div class="">
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
          <a href="#" class="sidebar-link">
            <i class="lni lni-user"></i>
            <span>Manage Account</span>
          </a>
        </li>
        <li class="sidebar-item">
          <a href="notifications" class="sidebar-link">
            <i class="lni lni-agenda"></i>
            <span>Manage Notification</span>
          </a>
        </li>
      </ul>

    </aside>
    <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
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

          <div  class="btn-search" style="margin-top: 20px;">
            <input type="text" name="" id="">
            <button>Search</button>
          </div>

          <div style="margin-top: 20px;">
            <label for="">Fillter:</label>
            <select>
              <option value="">Teacher</option>
              <option value="">Parent</option>
              <option value="">Enrollment</option>
            </select>
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
                  <h6 class="text-white text-capitalize ps-3">Account Table</h6>
                </div>
              </div>
              <div class="card-body px-0 pb-2">
                <div class="table-responsive p-0">
                  <table class="table align-items-center mb-0">
                    <thead>
                    <tr>
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">User Id</th>
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Role</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Full Name</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Email</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Status</th>
                      <th class="text-secondary opacity-7"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${accounts}">
                      <tr>
                        <td>${user.userID}</td> <!-- Display user ID -->
                        <td>${user.roleId}</td> <!-- Display user role -->
                        <td class="text-center">${user.fullname}</td> <!-- Display user fullname -->
                        <td class="text-center">${user.email}</td> <!-- Display user email -->
                        <td class="align-middle text-center">
                    <span class="badge badge-sm bg-gradient-${user.status == 1 ? 'Active' : 'Deactive'}">
                        ${user.status == 1 ? 'Active' : 'Deactive'}
                    </span>
                        </td>
                        <td class="align-middle">
                          <a href="" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                            Edit
                          </a>
                        </td>
                      </tr>
                    </c:forEach>
                    </tbody>
<%--                    <tbody>--%>
<%--                    <c:forEach var="user" items="${accounts}" >--%>
<%--                    <tr>--%>
<%--                      <td>--%>
<%--                        <div class="d-flex px-2 py-1">--%>
<%--                          <div>--%>
<%--                            <p class="text-xs font-weight-bold mb-0">${user.}</p>--%>
<%--                          </div>--%>
<%--                          <div class="d-flex flex-column justify-content-center">--%>
<%--                            <h6 class="mb-0 text-sm">John Michael</h6>--%>
<%--                            <p class="text-xs text-secondary mb-0">john@creative-tim.com</p>--%>
<%--                          </div>--%>
<%--                        </div>--%>
<%--                      </td>--%>
<%--                      <td>--%>
<%--                        <p class="text-xs font-weight-bold mb-0">Manager</p>--%>
<%--                        <p class="text-xs text-secondary mb-0">Organization</p>--%>
<%--                      </td>--%>
<%--                      <td class="align-middle text-center text-sm">--%>
<%--                        <span class="badge badge-sm bg-gradient-success">Online</span>--%>
<%--                      </td>--%>
<%--                      <td class="align-middle text-center">--%>
<%--                        <span class="text-secondary text-xs font-weight-bold">23/04/18</span>--%>
<%--                      </td>--%>
<%--                      <td class="align-middle">--%>
<%--                        <a href="javascript:;" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">--%>
<%--                          Edit--%>
<%--                        </a>--%>
<%--                      </td>--%>
<%--                    </tr>--%>
<%--                    </c:forEach>--%>
<%--                    </tbody>--%>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<%@include file="../common/footer.jsp"%>
</body>
</html>
