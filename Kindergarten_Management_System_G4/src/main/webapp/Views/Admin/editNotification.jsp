<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8" />
    <title>Edit Notification</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<%@include file="../common/header.jsp"%>

<div class="container">
    <h2 style="font-family: Roboto", Helvetica, Arial, sans-serif">Edit Notification</h2>
    <form action="${pageContext.request.contextPath}/editNotification" method="post">
        <input type="hidden" name="id" value="${notification.notificationId}" />
        <div class="mb-3">
            <label for="title" class="form-label">Title</label>
            <input type="text" class="form-control" id="title" name="title" value="${notification.title}" required />
        </div>
        <div class="mb-3">
            <label for="content" class="form-label">Content</label>
            <textarea class="form-control" id="content" name="content" required>${notification.content}</textarea>
        </div>
        <div class="mb-3">
            <label for="date" class="form-label">Date</label>
            <input type="date" class="form-control" id="date" name="date" value="${notification.date}" required />
        </div>
        <button type="submit" class="btn btn-primary">Update Notification</button>
        <a href="${pageContext.request.contextPath}/Views/Admin/notifications" class="btn btn-secondary">Cancel</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<%@include file="../common/footer.jsp"%>
</body>
</html>
