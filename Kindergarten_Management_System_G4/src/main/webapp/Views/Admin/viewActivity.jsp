<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin Manage - Class Levels</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/material-dashboard.css?v=3.1.0" rel="stylesheet" />
    <style>
        .table-container { min-height: 350px; max-height: 600px; overflow: auto; }
        .pagination-container { margin-top: 20px; display: flex; justify-content: center; }
    </style>
</head>
<body class="g-sidenav-show bg-gray-200">
    <%@include file="../common/header.jsp"%>
    <div class="containerAll">
        <div class="wrapper">
              <%@ include file="../common/sidebar_manage.jsp" %>.

            <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg col-md-10">
                <div class="container-fluid py-4">
                    <div class="row">
                        <div class="col-12">
                            <div class="card my-4">
                                <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                    <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3"><h6 class="text-white text-capitalize ps-3">Manage Extracurricular Activities</h6></div>
                                </div>
                                <div class="card-body px-0 pb-2">
                                    <div class="table-container">
                                        <table class="table align-items-center mb-0">
                                            <thead>
                                                <tr>
                                                    <th class="text-center">Activity ID</th>
                                                    <th class="text-center">Activity Name</th>
                                                    <th class="text-center">Date</th>
                                                    <th class="text-center">Start Time</th>
                                                    <th class="text-center">End Time</th>
                                                    <th class="text-center">Location</th>
                                                    <th class="text-center">Materials Needed</th>
                                                    <th class="text-center">Status</th>
                                                    <th class="text-center">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody id="activityTableBody">
                                                <c:forEach var="activity" items="${activityList}">
                                                    <tr>
                                                        <td class="text-center">${activity.activity_id}</td>
                                                        <td class="text-center">${activity.activity_name}</td>
                                                        <td class="text-center">${activity.date}</td>
                                                        <td class="text-center">${activity.start_time}</td>
                                                        <td class="text-center">${activity.end_time}</td>
                                                        <td class="text-center">${activity.location}</td>
                                                        <td class="text-center">${activity.materials_needed}</td>
                                                        <td class="text-center">${activity.status}</td>
                                                        <td class="text-center">
                                                            <button type="button" class="btn btn-warning btn-sm" onclick="openEditModal('${activity.activity_id}', '${activity.activity_name}', '${activity.description}', '${activity.date}', '${activity.start_time}', '${activity.end_time}', '${activity.location}', '${activity.materials_needed}', '${activity.status}')">Update</button>
                                                            <button type="button" class="btn btn-danger btn-sm" onclick="confirmDelete('${activity.activity_id}')">Delete</button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <button type="button" class="btn btn-success" onclick="openAddModal()">Add New Activity</button>
                            <div class="pagination-container">
                                <nav><ul class="pagination" id="pagination"></ul></nav>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="addActivity" method="POST" onsubmit="return confirmAdd()">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addModalLabel">Add New Activity</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Activity Name -->
                        <div class="mb-3">
                            <label for="addActivityName" class="form-label">Activity Name<span style="color:red;">*</span></label>
                            <input type="text" class="form-control" id="addActivityName" name="activityName" minlength="5" maxlength="50" required>
                            <small id="activityNameError" style="color:red; display:none;">Activity name must be between 5 and 50 characters.</small>
                        </div>

                        <!-- Description -->
                        <div class="mb-3">
                            <label for="addDescription" class="form-label">Description<span style="color:red;">*</span></label>
                            <input type="text" class="form-control" id="addDescription" name="description" minlength="5" maxlength="250" required>
                            <small id="descriptionError" style="color:red; display:none;">Description must be between 5 and 250 characters.</small>
                        </div>

                        <!-- Date -->
                        <div class="mb-3">
                            <label for="addDate" class="form-label">Date<span style="color:red;">*</span></label>
                            <input type="date" class="form-control" id="addDate" name="date" required>
                            <small id="dateError" style="color:red; display:none;">Date cannot be in the past.</small>
                        </div>

                        <!-- Start Time -->
                        <div class="mb-3">
                            <label for="addStartTime" class="form-label">Start Time<span style="color:red;">*</span></label>
                            <input type="time" class="form-control" id="addStartTime" name="start_time" required>
                        </div>

                        <!-- End Time -->
                        <div class="mb-3">
                            <label for="addEndTime" class="form-label">End Time<span style="color:red;">*</span></label>
                            <input type="time" class="form-control" id="addEndTime" name="end_time" required>
                            <small id="timeError" style="color:red; display:none;">End time must be after start time.</small>
                        </div>

                        <!-- Location -->
                        <div class="mb-3">
                            <label for="addLocation" class="form-label">Location<span style="color:red;">*</span></label>
                            <input type="text" class="form-control" id="addLocation" name="location" minlength="5" maxlength="50" required>
                            <small id="locationError" style="color:red; display:none;">Location must be between 5 and 50 characters.</small>
                        </div>

                        <!-- Materials -->
                        <div class="mb-3">
                            <label for="addMaterial" class="form-label">Materials<span style="color:red;">*</span></label>
                            <input type="text" class="form-control" id="addMaterial" name="material" minlength="5" maxlength="50" required>
                            <small id="materialError" style="color:red; display:none;">Material must be between 5 and 50 characters.</small>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="updateActivity" method="POST" id="updateActivityForm">
                    <input type="hidden" id="editActivityId" name="activityId">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editModalLabel">Edit Activity</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="editActivityName" class="form-label">Activity Name<span style="color:red;">*</span></label>
                            <input type="text" class="form-control" id="editActivityName" name="activityName" minlength="5" maxlength="50" required>
                            <small id="editActivityNameError" style="color:red; display:none;">Activity name must be between 5 and 50 characters.</small>
                        </div>
                        <div class="mb-3">
                            <label for="editDescription" class="form-label">Description<span style="color:red;">*</span></label>
                            <input type="text" class="form-control" id="editDescription" name="description" minlength="5" maxlength="250" required>
                            <small id="editDescriptionError" style="color:red; display:none;">Description must be between 5 and 250 characters.</small>
                        </div>
                        <div class="mb-3">
                            <label for="editDate" class="form-label">Date<span style="color:red;">*</span></label>
                            <input type="date" class="form-control" id="editDate" name="date" required>
                            <small id="editDateError" style="color:red; display:none;">Date cannot be in the past.</small>
                        </div>
                        <div class="mb-3">
                            <label for="editStartTime" class="form-label">Start Time<span style="color:red;">*</span></label>
                            <input type="time" class="form-control" id="editStartTime" name="startTime" required>
                        </div>
                        <div class="mb-3">
                            <label for="editEndTime" class="form-label">End Time<span style="color:red;">*</span></label>
                            <input type="time" class="form-control" id="editEndTime" name="endTime" required>
                            <small id="editTimeError" style="color:red; display:none;">End time must be after start time.</small>
                        </div>
                        <div class="mb-3">
                            <label for="editLocation" class="form-label">Location<span style="color:red;">*</span></label>
                            <input type="text" class="form-control" id="editLocation" name="location" minlength="5" maxlength="50" required>
                            <small id="editLocationError" style="color:red; display:none;">Location must be between 5 and 50 characters.</small>
                        </div>
                        <div class="mb-3">
                            <label for="editMaterialsNeeded" class="form-label">Materials Needed<span style="color:red;">*</span></label>
                            <input type="text" class="form-control" id="editMaterialsNeeded" name="materialsNeeded" minlength="5" maxlength="50" required>
                            <small id="editMaterialsNeededError" style="color:red; display:none;">Materials needed must be between 5 and 50 characters.</small>
                        </div>
                        <div class="mb-3">
                            <label for="editStatus" class="form-label">Status<span style="color:red;">*</span></label>
                            <select class="form-control" id="editStatus" name="status" required>
                                <option value="Planned">Planned</option>
                                <option value="Completed">Completed</option>
                                <option value="Cancelled">Cancelled</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-success">Save changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/deleteActivity" method="POST">
                    <input type="hidden" id="deleteClassLevelId" name="classLevelId">
                    <div class="modal-header"><h5 class="modal-title" id="confirmDeleteModalLabel">Confirm Delete</h5><button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div>
                    <div class="modal-body"><p>Are you sure you want to delete this class level?</p></div>
                    <div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button><button type="submit" class="btn btn-danger">Delete</button></div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header"><h5 class="modal-title" id="successModalLabel">Success</h5><button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div>
                <div class="modal-body"><p>Action completed successfully!</p></div>
                <div class="modal-footer"><button type="button" class="btn btn-success" data-bs-dismiss="modal">OK</button></div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="falseDeleteModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header"><h5 class="modal-title" id="successModalLabel">Success</h5><button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div>
                <div class="modal-body"><p>The Class Level is in use and cannot be deleted.</p></div>
                <div class="modal-footer"><button type="button" class="btn btn-success" data-bs-dismiss="modal">OK</button></div>
            </div>
        </div>
    </div>

    <%@include file="../common/footer.jsp"%>
    <script>
        let currentPage = 1; const rowsPerPage = 5;
        function paginateTable(page) {
            const rows = document.querySelectorAll('#activityTableBody tr');
            const totalRows = rows.length;
            const totalPages = Math.ceil(totalRows / rowsPerPage);
            currentPage = page;
            rows.forEach(row => row.style.display = 'none');
            const start = (currentPage - 1) * rowsPerPage;
            const end = start + rowsPerPage;
            rows.forEach((row, index) => { if (index >= start && index < end) { row.style.display = ''; } });
            updatePagination(totalPages);
        }
        function updatePagination(totalPages) {
            const pagination = document.getElementById('pagination');
            pagination.innerHTML = '';
            for (let i = 1; i <= totalPages; i++) {
                const li = document.createElement('li');
                li.classList.add('page-item');
                if (i === currentPage) li.classList.add('active');
                const a = document.createElement('a');
                a.classList.add('page-link');
                a.href = '#';
                a.textContent = i;
                a.onclick = () => paginateTable(i);
                li.appendChild(a);
                pagination.appendChild(li);
            }
        }
        document.addEventListener('DOMContentLoaded', () => { paginateTable(1); });
        function openAddModal() { var addModal = new bootstrap.Modal(document.getElementById('addModal'), { keyboard: false }); addModal.show(); }
        function openEditModal(activityId, activityName, description, date, startTime, endTime, location, materialsNeeded, status) {
            document.getElementById("editActivityId").value = activityId;
            document.getElementById("editActivityName").value = activityName;
            document.getElementById("editDescription").value = description;
            document.getElementById("editDate").value = date;
            document.getElementById("editStartTime").value = startTime;
            document.getElementById("editEndTime").value = endTime;
            document.getElementById("editLocation").value = location;
            document.getElementById("editMaterialsNeeded").value = materialsNeeded;
            document.getElementById("editStatus").value = status;
            var editModal = new bootstrap.Modal(document.getElementById('editModal'), { keyboard: false });
            editModal.show();
        }
        function confirmDelete(id) { document.getElementById("deleteClassLevelId").value = id; var deleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'), { keyboard: false }); deleteModal.show(); }
        function confirmAdd() { return confirm("Are you sure you want to add this activity level?"); }
        function showSuccessModal() { var successModal = new bootstrap.Modal(document.getElementById('successModal'), { keyboard: false }); successModal.show(); }
        function showFalseDeleteModal() { var falseDeleteModal = new bootstrap.Modal(document.getElementById('falseDeleteModal'), { keyboard: false }); falseDeleteModal.show(); }
        document.addEventListener('DOMContentLoaded', function () {
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.has('success') && urlParams.get('success') === 'true') { showSuccessModal(); }
            else if (urlParams.has('success') && urlParams.get('success') === 'false') { showFalseDeleteModal(); }
        });
    </script>
    <script>
        function confirmAdd() {
            let isValid = true;

            // Activity Name validation
            const activityName = document.getElementById("addActivityName");
            const activityNameError = document.getElementById("activityNameError");
            if (activityName.value.length < 5 || activityName.value.length > 50) {
                activityNameError.style.display = "block";
                isValid = false;
            } else {
                activityNameError.style.display = "none";
            }

            // Description validation
            const description = document.getElementById("addDescription");
            const descriptionError = document.getElementById("descriptionError");
            if (description.value.length < 5 || description.value.length > 250) {
                descriptionError.style.display = "block";
                isValid = false;
            } else {
                descriptionError.style.display = "none";
            }

            // Date validation
            const date = document.getElementById("addDate");
            const dateError = document.getElementById("dateError");
            const today = new Date().toISOString().split("T")[0];
            if (date.value < today) {
                dateError.style.display = "block";
                isValid = false;
            } else {
                dateError.style.display = "none";
            }

            // Time validation
            const startTime = document.getElementById("addStartTime").value;
            const endTime = document.getElementById("addEndTime").value;
            const timeError = document.getElementById("timeError");
            if (startTime && endTime && startTime >= endTime) {
                timeError.style.display = "block";
                isValid = false;
            } else {
                timeError.style.display = "none";
            }

            // Location validation
            const location = document.getElementById("addLocation");
            const locationError = document.getElementById("locationError");
            if (location.value.length < 5 || location.value.length > 50) {
                locationError.style.display = "block";
                isValid = false;
            } else {
                locationError.style.display = "none";
            }

            // Material validation
            const material = document.getElementById("addMaterial");
            const materialError = document.getElementById("materialError");
            if (material.value.length < 5 || material.value.length > 50) {
                materialError.style.display = "block";
                isValid = false;
            } else {
                materialError.style.display = "none";
            }

            return isValid;
        }
    </script>
    <script>
        document.getElementById('updateActivityForm').addEventListener('submit', function(event) {
            let isValid = true;

            // Activity Name validation
            const editActivityName = document.getElementById("editActivityName");
            const editActivityNameError = document.getElementById("editActivityNameError");
            if (editActivityName.value.length < 5 || editActivityName.value.length > 50) {
                editActivityNameError.style.display = "block";
                isValid = false;
            } else {
                editActivityNameError.style.display = "none";
            }

            // Description validation
            const editDescription = document.getElementById("editDescription");
            const editDescriptionError = document.getElementById("editDescriptionError");
            if (editDescription.value.length < 5 || editDescription.value.length > 250) {
                editDescriptionError.style.display = "block";
                isValid = false;
            } else {
                editDescriptionError.style.display = "none";
            }

            // Date validation
            const editDate = document.getElementById("editDate");
            const editDateError = document.getElementById("editDateError");
            const today = new Date().toISOString().split("T")[0];
            if (editDate.value < today) {
                editDateError.style.display = "block";
                isValid = false;
            } else {
                editDateError.style.display = "none";
            }

            // Time validation
            const editStartTime = document.getElementById("editStartTime").value;
            const editEndTime = document.getElementById("editEndTime").value;
            const editTimeError = document.getElementById("editTimeError");
            if (editStartTime && editEndTime && editStartTime >= editEndTime) {
                editTimeError.style.display = "block";
                isValid = false;
            } else {
                editTimeError.style.display = "none";
            }

            // Location validation
            const editLocation = document.getElementById("editLocation");
            const editLocationError = document.getElementById("editLocationError");
            if (editLocation.value.length < 5 || editLocation.value.length > 50) {
                editLocationError.style.display = "block";
                isValid = false;
            } else {
                editLocationError.style.display = "none";
            }

            // Materials Needed validation
            const editMaterialsNeeded = document.getElementById("editMaterialsNeeded");
            const editMaterialsNeededError = document.getElementById("editMaterialsNeededError");
            if (editMaterialsNeeded.value.length < 5 || editMaterialsNeeded.value.length > 50) {
                editMaterialsNeededError.style.display = "block";
                isValid = false;
            } else {
                editMaterialsNeededError.style.display = "none";
            }

            if (!isValid) {
                event.preventDefault();
            }
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
