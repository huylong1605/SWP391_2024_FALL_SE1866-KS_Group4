<%@ page import="org.example.kindergarten_management_system_g4.model.User" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register Student</title>
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
            min-height: calc(100vh - 160px);
            text-align: center;
        }
        .registration-form {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 40%;
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
        input, select {
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
        /* CSS để hiển thị thông báo lỗi ngay dưới trường nhập liệu */
        .form-group {
            position: relative;
            margin-bottom: 1.5rem;
        }

        .form-group small {
            position: absolute;
            bottom: -20px; /* Đẩy thông báo lỗi lên gần trường nhập */
            left: 0;
            color: red;
            font-size: 0.9em;
        }

        /* CSS để đảm bảo không có sự thay đổi kích thước của form khi hiện lỗi */
        input, select {
            display: block;
            width: 100%;
            padding: 0.375rem 0.75rem;
            font-size: 1rem;
            line-height: 1.5;
            border: 1px solid #ced4da;
            border-radius: 0.25rem;
        }

        /* Đảm bảo form không bị thay đổi kích thước khi có thông báo lỗi */
        .button-container {
            margin-top: 20px;
        }

    </style>
</head>
<body>
<%@ include file="/Views/common/header.jsp" %>
<div class="content">
    <div class="registration-form">
        <h2>Register Student</h2>
        <form id="registerEnrollmentForm" action="registerStudentByEnrollment" method="post">
            <div class="form-group">
                <label for="name">Name:<span style="color:red;">*</span></label>
                <input type="text" id="name" name="name" minlength="5" maxlength="50" required>
                <small id="nameError" style="display:none;">Name must be between 5 and 50 characters.</small>
            </div>
            <div class="form-group">
                <label for="dob">DOB:<span style="color:red;">*</span></label>
                <input type="date" id="dob" name="dob" required>
                <small id="dobError" style="display:none;">DOB cannot be in the future.</small>
            </div>
            <div class="form-group">
                <label for="gender">Gender:<span style="color:red;">*</span></label>
                <select id="gender" name="gender" required>
                    <option value="">Select</option>
                    <option value="true">Male</option>
                    <option value="false">Female</option>
                </select>
                <small id="genderError" style="display:none;">Please select a gender.</small>
            </div>
            <div class="form-group">
                <label for="phone">Phone:<span style="color:red;">*</span></label>
                <input type="text" id="phone" onkeyup="filterUsersByPhone()" placeholder="Enter parent's phone number" required>
                <small id="phoneError" style="display:none;">Please enter a valid phone number.</small>
            </div>
            <div class="form-group">
                <label for="userSelect">Parent Name</label>
                <select id="userSelect" name="userId" required>
                    <option value="" disabled selected>Select a user</option>
                </select>
                <small id="userSelectError" style="display:none;">Please select a parent.</small>
            </div>
            <div class="button-container">
                <button type="submit">Register</button>
            </div>
        </form>

    </div>
</div>
<%@ include file="/Views/common/footer.jsp" %>

<script>
    document.getElementById("registerEnrollmentForm").addEventListener("submit", function(event) {
        let formIsValid = true;

        // Validate Name
        const name = document.getElementById("name").value;
        const nameError = document.getElementById("nameError");
        if (name.length < 5 || name.length > 50) {
            nameError.style.display = "inline";
            formIsValid = false;
        } else {
            nameError.style.display = "none";
        }

        // Validate DOB
        const dob = document.getElementById("dob").value;
        const dobError = document.getElementById("dobError");
        const selectedDate = new Date(dob);
        const currentDate = new Date();
        if (selectedDate > currentDate) {
            dobError.style.display = "inline";
            formIsValid = false;
        } else {
            dobError.style.display = "none";
        }

        // Validate Gender
        const gender = document.getElementById("gender").value;
        const genderError = document.getElementById("genderError");
        if (gender === "") {
            genderError.style.display = "inline";
            formIsValid = false;
        } else {
            genderError.style.display = "none";
        }

        // Validate Phone (Assuming 10-digit format, adjust as needed)
        const phone = document.getElementById("phone").value;
        const phoneError = document.getElementById("phoneError");
        const phonePattern = /^[0-9]{10}$/; // Modify the pattern if phone format varies
        if (!phonePattern.test(phone)) {
            phoneError.style.display = "inline";
            formIsValid = false;
        } else {
            phoneError.style.display = "none";
        }

        // Validate Parent Name selection
        const userSelect = document.getElementById("userSelect").value;
        const userSelectError = document.getElementById("userSelectError");
        if (userSelect === "") {
            userSelectError.style.display = "inline";
            formIsValid = false;
        } else {
            userSelectError.style.display = "none";
        }

        // Prevent form submission if any validation fails
        if (!formIsValid) {
            event.preventDefault();
        }
    });
</script>

<!-- JavaScript for Live Filtering of Users by Phone -->
<script type="text/javascript">
    // Array of users initialized from server-side JSP userList
    const userList = [
        <c:forEach var="user" items="${userList}">
            { id: '${user.userID}', name: '${user.fullname}', phone: '${user.phoneNumber}' },
        </c:forEach>
    ];

    // Filter function to update options based on phone input
    function filterUsersByPhone() {
        const phoneInput = document.getElementById('phone').value;
        const userSelect = document.getElementById('userSelect');
        userSelect.innerHTML = '<option value="" disabled selected>Select a user</option>';

        // Filter users and update dropdown
        const filteredUsers = userList.filter(user => user.phone.includes(phoneInput));
        filteredUsers.forEach(user => {
            const option = document.createElement('option');
            option.value = user.id;
            option.textContent = user.name;
            userSelect.appendChild(option);
        });
    }
</script>
</body>
</html>
