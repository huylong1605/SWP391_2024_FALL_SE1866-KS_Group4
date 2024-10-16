<%@ page import="org.example.kindergarten_management_system_g4.model.User" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <style>
        body {
            margin: 0;
            background-color: #f0f2f5;
            font-family: Arial, sans-serif;
        }
        .content {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: calc(100vh - 160px); /* Adjust for header/footer height */
            text-align: center;
        }
        .error-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        h1 {
            font-size: 36px;
            color: #ff4d4f;
        }
        p {
            font-size: 18px;
            color: #555;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;

            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="content">
    <div class="error-container">
        <h1>An Error Occurred!</h1>
        <p>Sorry, something went wrong while processing your request.</p>
        <p>Please contact the system administrator.</p>
       <%-- <%
            User user = (User) session.getAttribute("user");
            int roleId = user.getRoleId();
            String homeUrl = "index.jsp"; // Default URL if roleid is not set

                if (roleId == 3) {
                    homeUrl = "/Views/HomePage/HomePage.jsp";
                } else if (roleId == 1) {
                    homeUrl = "/Views/HomePage/HomePageForAdmin.jsp"; // URL for parent home
                }else if (roleId == 4) {
                        homeUrl = "listClass";
            }else if (roleId == 2) {
                    homeUrl = "/Views/HomePage/HomePageForTeacher.jsp";
                }
        %>
        <a href="<%= homeUrl %>">Back to Home</a>--%>
    </div>
</div>
<%@ include file="/Views/common/footer.jsp" %>
</body>
</html>
