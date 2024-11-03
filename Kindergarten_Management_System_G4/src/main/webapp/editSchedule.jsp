<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Schedule</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css">
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container mt-5">
    <h2 class="mb-4">Update Schedule</h2>

    <!-- Display error messages if any -->
    <% if (request.getAttribute("dateFalse") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("dateFalse") %>
    </div>
    <% } %>

    <% if (request.getAttribute("DayNotMatch") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("DayNotMatch") %>
    </div>
    <% } %>

    <% if (request.getAttribute("ExistSchedule") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("ExistSchedule") %>
    </div>
    <% } %>
    <% if (request.getAttribute("outOfDateTerm") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("outOfDateTerm") %>
    </div>
    <% } %>

    <% if (request.getAttribute("sunday") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("sunday") %>
    </div>
    <% } %>
    <!-- Retrieve existing data for the form fields -->


    <form action="editSchedule" method="post">
        <input type="hidden" name="scheduleIdd" value="${param.scheduleIdd != null ? param.scheduleIdd : schedules.scheduleId}"/>

        <div class="row">
            <!-- Left Column - Form Data -->
            <div class="col-md-8">
                <div class="mb-3">
                    <label for="date" class="form-label">Date <span class="text-danger">*</span></label>
                    <input type="date" class="form-control" id="date" name="date" required
                           value="${ param.date != null ? param.date : schedules.dateOfDay }">
                </div>

                <div class="mb-3">
                    <label for="term_id" class="form-label">Term <span class="text-danger">*</span></label>
                    <select class="form-select" id="term_id" name="term_id" required>
                        <option value="" disabled>Select term</option>
                        <c:forEach var="term" items="${listTerm}">
                        <option value="${term.termId}"
                                <c:if test="${term.termId == (param.term_id != null ? param.term_id : schedules.termId)}">selected</c:if>>
                            ${term.termName} - ${term.year}
                        </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="subject_id" class="form-label">Subject <span class="text-danger">*</span></label>
                    <select class="form-select" id="subject_id" name="subject_id" required>
                        <option value="" disabled>Select subject</option>
                        <c:forEach var="subject" items="${listSubject}">
                        <option value="${subject.subjectId}"
                                <c:if test="${subject.subjectId == (param.subject_id != null ? param.subject_id : subjectById.subjectId)}">selected</c:if>>
                            ${subject.subjectName}
                        </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="class_id" class="form-label">Class <span class="text-danger">*</span></label>
                    <select class="form-select" id="class_id" name="class_id" required>
                        <option value="" disabled>Select Class</option>
                        <c:forEach var="classes" items="${listClass}">
                        <option value="${classes.classId}"
                                <c:if test="${classes.classId == (param.class_id != null ? param.class_id : schedules.classId)}">selected</c:if>>
                            ${classes.className}
                        </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="slotId" class="form-label">Time Slot <span class="text-danger">*</span></label>
                    <select class="form-select" id="slotId" name="slotId" required>
                        <option value="" disabled>Select Slot</option>
                        <c:forEach var="slot" items="${listSlot}">
                        <option value="${slot.slotId}"
                                <c:if test="${slot.slotId == (param.slotId != null ? param.slotId : schedules.slotId)}">selected</c:if>>
                            ${slot.slotName}
                        </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- Right Column - Action Buttons -->
            <div class="col-md-4 d-flex flex-column align-items-center justify-content-center">
                <button type="submit" class="btn btn-primary mb-2">Update Schedule</button>
                <a href="listSchedule" class="btn btn-secondary mb-2">Cancel</a>
                <a href="listSchedule" class="btn btn-link">Back to List</a>
            </div>
        </div>
    </form>
</div>
<%@ include file="/Views/common/footer.jsp" %>
</body>
</html>
