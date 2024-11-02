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
        .registration-form {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width:40%;
        }


         h2 {
                     text-align: center;
                     margin-bottom: 20px;
                     font-size: 24px;
                     color: #333;
                 }

                 .form-group {
                     display: flex;
                     align-items: center;
                     margin-bottom: 20px;
                 }

                 label {
                     flex: 0.5;
                     font-size: 14px;
                     color: #333;
                 }

                 input,
                 select {
                     flex: 2;
                     padding: 10px;
                     border: 1px solid #bdc3c7;
                     border-radius: 5px;
                     font-size: 14px;
                     box-sizing: border-box;
                 }

                  .button-container {
                            display: flex;
                            justify-content: center;
                        }

                        button {
                            width: 40%;
                            padding: 12px;
                            background-color: #3498db;
                            color: white;
                            border: none;
                            border-radius: 5px;
                            font-size: 16px;
                            cursor: pointer;
                            transition: background-color 0.3s ease;
                        }
                 button:hover {
                     background-color: #2980b9;
                 }

    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="content">
    <div class="registration-form">
<h2>Register Student</h2>
    <form action="registerStudentByEnrollment" method="post">
        <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="dob">DOB:</label>
            <input type="date" id="dob" name="dob" required>
        </div>
        <div class="form-group">
            <label for="gender">Gender:</label>
            <select id="gender" name="gender" required>
                <option value="true">Male</option>
                <option value="false">Female</option>
            </select>
        </div>

        <div class="form-group">
            <label for="userSelect">Parent Name</label>
            <select id="userSelect" name="userId" required>
                <option value="" disabled selected>Select a user</option> <!-- Tùy chọn trống mặc định -->
                <c:forEach var="user" items="${userList}">
                    <option value="${user.userID}">${user.fullname}</option>
                </c:forEach>
            </select>
        </div>


        <div class="button-container">
            <button type="submit">Register</button>
        </div>
    </form>
    </div>
</div>
<%@ include file="/Views/common/footer.jsp" %>
</body>
</html>
