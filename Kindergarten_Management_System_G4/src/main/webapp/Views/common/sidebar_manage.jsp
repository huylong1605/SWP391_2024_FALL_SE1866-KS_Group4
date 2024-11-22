<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside id="sidebar" class="expand" style="width: 24%">
    <div class="d-flex">
        <button class="toggle-btn" type="button">
            <i class="lni lni-grid-alt"></i>
        </button>
        <div class="sidebar-logo">
            <a href="#">Manager Management</a>
        </div>
    </div>
    <ul class="sidebar-nav">
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/Views/Manager/listRoom" class="sidebar-link">
                <i class="lni lni-user"></i>
                <span>Manage Room</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/listSchedule" class="sidebar-link">
                <i class="lni lni-agenda"></i>
                <span>Manage Schedule</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/subject" class="sidebar-link">
                <i class="lni lni-graduation"></i>
                <span>Manage Subject</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/viewStudentList" class="sidebar-link">
                <i class="lni lni-graduation"></i>
                <span>Manage Student</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/term" class="sidebar-link">
                <i class="lni lni-user"></i>
                <span>Manage Term</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/listClass" class="sidebar-link">
                <i class="lni lni-graduation"></i>
                <span>Manage Class</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/classLevel" class="sidebar-link">
                <i class="lni lni-graduation"></i>
                <span>Manage Class Level</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/view-extracurricular-activities" class="sidebar-link">
                <i class="lni lni-graduation"></i>
                <span>Manage Activity</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/Views/Manager/AddStudent_ChangeClass.jsp" class="sidebar-link" >
                <i class="lni lni-graduation"></i>
                <span >Add/Change Class For Student</span>
            </a>
        </li>
    </ul>
</aside>
