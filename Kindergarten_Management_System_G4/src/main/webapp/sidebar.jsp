<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 9/27/2024
  Time: 1:13 AM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sidebar Fragment</title>
    <link rel="stylesheet" href="path/to/your/styles.css"> <!-- Thêm đường dẫn tới file CSS của bạn -->
</head>
<body>
<div id="sidebar">
    <div class="fixed left-0 top-0 w-64 h-full bg-gray-900 p-4 z-50 sidebar-menu transition-transform">
        <a href="<c:url value='/homepage' />" class="flex items-center pb-4 border-b border-b-gray-800">
            <img src="/img/chococ.png" alt="" class="w-8 h-8 rounded object-cover">
            <span class="text-lg font-bold text-white ml-3" style="color: #81c408;">Chợ Cóc</span>
        </a>
        <ul class="mt-4">
            <li class="mb-1 group active">
                <a href="<c:url value='/homepage' />"
                   class="flex items-center py-2 px-4 text-gray-300 hover:bg-gray-950 hover:text-gray-100 rounded-md">
                    <i class="ri-home-2-line mr-3 text-lg"></i>
                    <span class="text-sm">Trang Chủ</span>
                </a>
            </li>
            <li class="mb-1 group active">
                <a href="<c:url value='/manage-user' />"
                   class="flex items-center py-2 px-4 text-gray-300 hover:bg-gray-950 hover:text-gray-100 rounded-md">
                    <i class="ri-group-2-line mr-3 text-lg"></i>
                    <span class="text-sm">Quản Lý Người Dùng</span>
                </a>
            </li>
            <li class="mb-1 group active">
                <a href="<c:url value='/manage-canteen' />"
                   class="flex items-center py-2 px-4 text-gray-300 hover:bg-gray-950 hover:text-gray-100 rounded-md">
                    <i class="ri-archive-2-line mr-3 text-lg"></i>
                    <span class="text-sm">Quản Lý Căn Tin</span>
                </a>
            </li>
            <li class="mb-1 group active">
                <a href="<c:url value='/dashboard-admin' />"
                   class="flex items-center py-2 px-4 text-gray-300 hover:bg-gray-950 hover:text-gray-100 rounded-md">
                    <i class="ri-bar-chart-2-line mr-3 text-lg"></i>
                    <span class="text-sm">Tổng Quan</span>
                </a>
            </li>
            <li class="mb-1 group active">
                <a href="<c:url value='/logout' />"
                   class="flex items-center py-2 px-4 text-gray-300 hover:bg-gray-950 hover:text-gray-100 rounded-md">
                    <i class="ri-logout-box-line mr-3 text-lg"></i>
                    <span class="text-sm">Đăng Xuất</span>
                </a>
            </li>
        </ul>
    </div>
    <div class="fixed top-0 left-0 w-full h-full bg-black/50 z-40 md:hidden sidebar-overlay"></div>
</div>
</body>
</html>