<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin Manage - Class Levels</title>
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
    <!-- Include the common header JSP file -->
    <%@include file="../common/header.jsp"%>

    <!-- Main container for the entire page layout -->
    <div class="containerAll">
        <div class="wrapper">
            <!-- Sidebar section -->
            <aside id="sidebar" class="expand">
                <ul class="sidebar-nav">
                    <li class="sidebar-item">
                        <a href="${pageContext.request.contextPath}/classLevel" class="sidebar-link">
                            <i class="lni lni-graduation"></i>
                            <span>Manage Class Levels</span>
                        </a>
                    </li>
                </ul>
            </aside>

            <!-- Main content section -->
            <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
                <div class="container-fluid py-4">
                    <div class="row">
                        <div class="col-12">
                            <div class="card my-4">
                                <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                    <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                                        <h6 class="text-white text-capitalize ps-3">Manage Class Levels</h6>
                                    </div>
                                </div>
                                <div class="card-body px-0 pb-2">
                                    <div class="table-responsive p-0">
                                        <table class="table align-items-center mb-0">
                                            <thead>
                                                <tr>
                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">#</th>
                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Name</th>
                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Description</th>
                                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Age Range</th>
                                                    <th class="text-secondary opacity-7">Actions</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Iterate over the list of class levels -->
                                                <c:forEach var="class_level" items="${classLevelList}">
                                                    <tr>
                                                        <td class="text-center">${class_level.classLevelId}</td>
                                                        <td class="text-center">${class_level.classLevelName}</td>
                                                        <td class="text-center">${class_level.description}</td>
                                                        <td class="text-center">${class_level.ageRange}</td>
                                                        <td class="text-center">
                                                            <!-- Button to open the modal for editing class level -->
                                                            <button type="button" class="btn btn-warning btn-sm"
                                                                    onclick="openEditModal('${class_level.classLevelId}', '${class_level.classLevelName}', '${class_level.description}', '${class_level.ageRange}')">
                                                                Update
                                                            </button>

                                                            <!-- Button to delete class level -->
                                                            <form action="${pageContext.request.contextPath}/deleteClassLevel" method="POST" style="display:inline-block;">
                                                                <input type="hidden" name="classLevelId" value="${class_level.classLevelId}">
                                                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <!-- Button to open the modal for adding a new class level -->
                            <button type="button" class="btn btn-success" onclick="openAddModal()">Add New Class Level</button>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <!-- Modal for adding a new class level -->
    <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/addClassLevel" method="POST">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addModalLabel">Add New Class Level</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="addClassName" class="form-label">Class Level Name</label>
                            <input type="text" class="form-control" id="addClassName" name="classLevelName" required>
                        </div>
                        <div class="mb-3">
                            <label for="addDescription" class="form-label">Description</label>
                            <input type="text" class="form-control" id="addDescription" name="description" required>
                        </div>
                        <div class="mb-3">
                            <label for="addAgeRange" class="form-label">Age Range</label>
                            <input type="number" class="form-control" id="addAgeRange" name="ageRange" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal for editing an existing class level -->
    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/updateClassLevel" method="POST">
                    <input type="hidden" id="editClassLevelId" name="classLevelId">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editModalLabel">Edit Class Level</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="editClassName" class="form-label">Class Level Name</label>
                            <input type="text" class="form-control" id="editClassName" name="classLevelName" required>
                        </div>
                        <div class="mb-3">
                            <label for="editDescription" class="form-label">Description</label>
                            <input type="text" class="form-control" id="editDescription" name="description" required>
                        </div>
                        <div class="mb-3">
                            <label for="editAgeRange" class="form-label">Age Range</label>
                            <input type="number" class="form-control" id="editAgeRange" name="ageRange" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        // Function to open the add modal
        function openAddModal() {
            var addModal = new bootstrap.Modal(document.getElementById('addModal'), {
                keyboard: false
            });
            addModal.show();
        }

        // Function to open the edit modal and populate the fields with class level data
        function openEditModal(id, name, description, ageRange) {
            document.getElementById("editClassLevelId").value = id;
            document.getElementById("editClassName").value = name;
            document.getElementById("editDescription").value = description;
            document.getElementById("editAgeRange").value = ageRange;

            var editModal = new bootstrap.Modal(document.getElementById('editModal'), {
                keyboard: false
            });
            editModal.show();
        }
    </script>

    <!-- Include Bootstrap JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
