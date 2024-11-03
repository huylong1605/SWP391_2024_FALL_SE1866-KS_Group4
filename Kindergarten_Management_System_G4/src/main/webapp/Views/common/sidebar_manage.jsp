<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/subject" class="sidebar-link">
                <i class="lni lni-graduation"></i>
                <span>Manage Subject</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/viewStudentList" class="sidebar-link">
                <i class="lni lni-graduation"></i>
                <span>View List Student</span>
            </a>
        </li>
        <li class="sidebar-item">
            <a href="${pageContext.request.contextPath}/listClass" class="sidebar-link">
                <i class="lni lni-graduation"></i>
                <span>View list class</span>
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
    </ul>
</aside>
