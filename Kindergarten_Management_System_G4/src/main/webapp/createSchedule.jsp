<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Schedule</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css">
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="container mt-5">
    <h2 class="mb-4">Create Schedule</h2>

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
    <%
        String dayOfWeek = request.getParameter("dayOfWeek");
        String date = request.getParameter("date");
    %>
    <form action="createSchedule" method="post">
        <div class="row">
            <!-- Column 1 -->
            <div class="col-md-4">
                <div class="mb-3">
                    <label for="dayOfWeek" class="form-label">Day of the Week</label>
                    <select class="form-select" id="dayOfWeek" name="dayOfWeek" required>
                        <option value="" disabled <%= (dayOfWeek == null || dayOfWeek.isEmpty()) ? "selected" : "" %>>Select Day</option>
                        <option value="monday" <%= "monday".equals(dayOfWeek) ? "selected" : "" %>>Monday</option>
                        <option value="tuesday" <%= "tuesday".equals(dayOfWeek) ? "selected" : "" %>>Tuesday</option>
                        <option value="wednesday" <%= "wednesday".equals(dayOfWeek) ? "selected" : "" %>>Wednesday</option>
                        <option value="thursday" <%= "thursday".equals(dayOfWeek) ? "selected" : "" %>>Thursday</option>
                        <option value="friday" <%= "friday".equals(dayOfWeek) ? "selected" : "" %>>Friday</option>
                        <option value="saturday" <%= "saturday".equals(dayOfWeek) ? "selected" : "" %>>Saturday</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="date" class="form-label">Date</label>
                    <input type="date" class="form-control" id="date" name="date" required value="<%= (date != null) ? date : "" %>">
                </div>
            </div>

            <!-- Column 2 -->
            <div class="col-md-4">
                <div class="mb-3">
                    <label for="term_id" class="form-label">Term</label>
                    <select class="form-select" id="term_id" name="term_id" required>
                        <option value="" disabled selected>Select term</option>
                        <c:forEach var="term" items="${listTerm}">
                            <option value="${term.termId}"
                                    <c:if test="${term.termId == param.term_id}">selected</c:if>>
                                    ${term.termName} - ${term.year}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="subject_id" class="form-label">Subject</label>
                    <select class="form-select" id="subject_id" name="subject_id" required>
                        <option value="" disabled selected>Select subject</option>
                        <c:forEach var="subject" items="${listSubject}">
                            <option value="${subject.subjectId}"
                                    <c:if test="${subject.subjectId == param.subject_id}">selected</c:if>>
                                    ${subject.subjectName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- Column 3 -->
            <div class="col-md-4">
                <div class="mb-3">
                    <label for="class_id" class="form-label">Class</label>
                    <select class="form-select" id="class_id" name="class_id" required>
                        <!-- Populate with class data -->
                        <option value="" disabled selected>Select Class</option>
                        <c:forEach var="classes" items="${listClass}">
                            <option value="${classes.classId}"
                                    <c:if test="${classes.classId == param.class_id}">selected</c:if>>
                                    ${classes.className}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="slotId" class="form-label">Time Slot</label>
                    <select class="form-select" id="slotId" name="slotId" required>
                        <!-- Populate with time slot data -->
                        <option value="" disabled selected>Select Slot</option>
                        <c:forEach var="slot" items="${listSlot}">
                            <option value="${slot.slotId}"
                                    <c:if test="${slot.slotId == param.slotId}">selected</c:if>>
                                         ${slot.slotName}
<%--                                    ${slot.statrTime} - ${slot.endTime}--%>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-primary mt-3">Create Schedule</button>
    </form>
</div>
<%@ include file="/Views/common/footer.jsp" %>
</body>
</html>
