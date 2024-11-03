<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>User Profile Form Requirement</title>
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">


</head>

<body>
<%@ include file="Views/common/header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="col-md-10">
                <h4>
                    <a href="viewprofile">User Profile</a>
                    <form class="form-horizontal" action="updateprofile" method="post" enctype="multipart/form-data">
                        <fieldset>
                            <legend>
                                <h1>Update User Profile</h1>
                            </legend>

                            <div class="form-group">
                                <label class="col-md-4 control-label" for="fullname">Full name<span style="color:red;">*</span></label>
                               <div class="col-md-4">
                                   <input value="${user.fullname}" id="fullname" name="fullname" type="text" class="form-control" minlength="5" maxlength="50" required>
                               </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label" for="image">Avatar</label>
                                <div class="col-md-4">
                                    <c:choose>
                                        <c:when test="${not empty user.image}">
                                            <img src="${user.image}" width="100" height="100" alt="Avatar">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="https://cellphones.com.vn/sforum/wp-content/uploads/2023/10/avatar-trang-4.jpg" width="100" height="100" alt="Avatar">
                                        </c:otherwise>
                                    </c:choose>
                                    <input id="image" name="image" class="input-file" type="file">
                                </div>
                                <input type="hidden" name="currentImage" value="${user.image != null ? user.image : 'https://via.placeholder.com/100'}">
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label" for="gender">Gender<span style="color:red;">*</span></label>
                                <div class="col-md-4">
                                    <label class="radio-inline">
                                        <input type="radio" name="gender" id="male" value="1" <c:if test="${user.gender == 1}">checked</c:if>> Male
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="gender" id="female" value="0" <c:if test="${user.gender != 1}">checked</c:if>> Female
                                    </label>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label" for="phoneNumber">Phone Number<span style="color:red;">*</span></label>
                                <div class="col-md-4">
                                    <input value="${user.phoneNumber}" id="phoneNumber" name="phoneNumber" type="tel" pattern="0\d{9}" max-value="10" placeholder="Phone Number (format 10 digit)" class="form-control" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label" for="email">Email<span style="color:red;">*</span></label>
                                <div class="col-md-4">
                                    <input value="${user.email}" id="email" name="email" type="email" placeholder="Email Address" class="form-control" required readonly>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label" for="address">Address<span style="color:red;">*</span></label>
                                <div class="col-md-4">
                                    <input value="${user.address}" id="address" name="address" type="text" placeholder="Address" class="form-control"  minlength="5" maxlength="50" required>
                                </div>
                            </div>

                            <div class="form-group text-center">
                                <button type="submit" class="btn btn-primary btn-warning">Update</button>
                            </div>
                        </fieldset>
                    </form>
                </h4>
            </div>
        </div>
    </div>
        <%@ include file="Views/common/footer.jsp" %>

</body>
</html>
