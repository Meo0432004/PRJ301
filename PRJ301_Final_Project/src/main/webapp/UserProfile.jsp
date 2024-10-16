<%@page import="Models.User"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Profile</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function A(provinceID) {
            console.log(provinceID);

            $.ajax({
                type: "GET",
                url: "https://esgoo.net/api-tinhthanh/2/" + provinceID + ".htm",
                dataType: "json",
                success: function (response) {
                    console.log(response);

                    if (response.error === 0) {
                        var data = response.data;
                        var options = '';
                        for (var i = 0; i < data.length; i++) {
                            options += '<option value="' + data[i].full_name_en + '">' + data[i].full_name_en + '</option>';
                        }
                        $('#district').html('<option value="">Select District</option>' + options);
                    } else {
                        console.error("Error from API: " + response.error_text);
                    }
                },
                error: function (xhr, status, error) {
                    console.error("Error fetching data from API: " + error);
                }
            });
        }

        $(document).ready(function () {
            $('#province').change(function () {
                var provinceId = $(this).val();
                var provinceName = $("#province option:selected").text();
                $('#provinceName').val(provinceName);

                A(provinceId);
            });

            $('#togglePassword').click(function (e) {
                e.preventDefault(); // Prevent form submission

                var passwordField = $('#password');
                var type = passwordField.attr('type') === 'password' ? 'text' : 'password';
                passwordField.attr('type', type);
                $(this).text(type === 'password' ? 'Show' : 'Hide');
            });
        });
    </script>
</head>
<body>
    <c:set var="user" value="${requestScope.user}"/>
    <% User user = (User) request.getAttribute("user"); %>
    <h1>Hello ${user.username}</h1>
    <form action="User" method="post">
        Username:<input type="text" value="${user.username}" name="username" required/><br>
        Password:<input type="password" value="${user.password}" name="password" id="password" required/><br>
        <button type="button" id="togglePassword">Show</button><br>
        Email:<input type="text" value="${user.email}" name="email" required/><br><!-- comment -->
        Phone:<input type="text" value="${user.phone}" name="phone" required/><br>
        Province/City:
        <select name="province" id="province" class="form-control">
            <option value="">Select Province/City</option>
            <%
                JSONArray provinces = (JSONArray) request.getAttribute("provinces");
                if (provinces != null) {
                    for (Object obj : provinces) {
                        JSONObject province = (JSONObject) obj;
            %>
            <option value="<%= province.get("id")%>" <%= user != null && user.getUserProvinceCity().equals(province.get("full_name_en")) ? "selected" : ""%>><%= province.get("full_name_en")%></option>
            <%
                    }
                }
            %>
        </select><br>
        <input type="hidden" id="provinceName" name="provinceName"/>

        District:
        <select name="district" id="district" class="form-control">
            <option value="">Select District</option>
            <c:if test="${not empty user.userDistrict}">
                <option value="${user.userDistrict}" selected>${user.userDistrict}</option>
            </c:if>
        </select><br>
        <input type="hidden" name="action" value="EditProfile"/>
        <button type="submit">Save</button>
    </form>
</body>
</html>
