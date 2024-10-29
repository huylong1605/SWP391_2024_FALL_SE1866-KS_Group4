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
    <form action="ScheduleServlet" method="post">
        <div class="row">
            <!-- Column 1 -->
            <div class="col-md-4">
                <div class="mb-3">
                    <label for="dayOfWeek" class="form-label">Day of the Week</label>
                    <select class="form-select" id="dayOfWeek" name="dayOfWeek" required>
                        <option value="1">Monday</option>
                        <option value="2">Tuesday</option>
                        <option value="3">Wednesday</option>
                        <option value="4">Thursday</option>
                        <option value="5">Friday</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="date" class="form-label">Date</label>
                    <input type="date" class="form-control" id="date" name="date" required>
                </div>
            </div>

            <!-- Column 2 -->
            <div class="col-md-4">
                <div class="mb-3">
                    <label for="term_id" class="form-label">Term</label>
                    <select class="form-select" id="term_id" name="term_id" required>
                        <!-- Populate with term data -->
                        <option value="1">Term 1</option>
                        <option value="2">Term 2</option>
                        <option value="3">Term 3</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="subject_id" class="form-label">Subject</label>
                    <select class="form-select" id="subject_id" name="subject_id" required>
                        <!-- Populate with subject data -->
                        <option value="1">Math</option>
                        <option value="2">English</option>
                        <option value="3">Science</option>
                    </select>
                </div>
            </div>

            <!-- Column 3 -->
            <div class="col-md-4">
                <div class="mb-3">
                    <label for="class_id" class="form-label">Class</label>
                    <select class="form-select" id="class_id" name="class_id" required>
                        <!-- Populate with class data -->
                        <option value="1">Class A</option>
                        <option value="2">Class B</option>
                        <option value="3">Class C</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="slotId" class="form-label">Time Slot</label>
                    <select class="form-select" id="slotId" name="slotId" required>
                        <!-- Populate with time slot data -->
                        <option value="1">8:00 - 9:00</option>
                        <option value="2">9:00 - 10:00</option>
                        <option value="3">10:00 - 11:00</option>
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
