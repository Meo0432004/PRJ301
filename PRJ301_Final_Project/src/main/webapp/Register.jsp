<%@ page import="org.json.simple.parser.JSONParser" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script>
            function A(provinceID) {
                console.log(provinceID); // Kiểm tra giá trị provinceID trong console log

                // Gửi yêu cầu AJAX để lấy dữ liệu từ API
                $.ajax({
                    type: "GET",
                    url: "https://esgoo.net/api-tinhthanh/2/" + provinceID + ".htm",
                    dataType: "json",
                    success: function (response) {
                        console.log(response); // Kiểm tra dữ liệu nhận được từ API

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
                    var provinceName = $("#province option:selected").text(); // Lấy tên của tỉnh thành được chọn
                    $('#provinceName').val(provinceName); // Cập nhật input ẩn với tên của tỉnh thành

                    A(provinceId); // Gọi hàm A khi có sự thay đổi trong dropdown
                });
            });

        </script>
    </head>
    <body>
        <div class="container mt-5 pt-5">
            <form action="Account" method="post">
                <div class="card">
                    <div class="card-body">
                        <h3>${requestScope.error}</h3>
                        <h1 class="header">Register</h1>
                        Username: <input type="text" name="username" class="form-control"/><br/>
                        Password: <input type="password" name="password" class="form-control"/><br/>
                        Email: <input type="email" name="email" class="form-control"/><br/>
                        Phone: <input type="text" name="phone" class="form-control"/><br/>
                        Province/City:
                        <select name="province" id="province" class="form-control">
                            <option value="">Select Province/City</option>
                            <%
                                JSONArray provinces = (JSONArray) request.getAttribute("provinces");
                                String provinceName = "";
                                if (provinces != null) {
                                    for (Object obj : provinces) {
                                        JSONObject province = (JSONObject) obj;
                            %>
                            <option value="<%= province.get("id")%>"><%= province.get("full_name_en")%></option>
                            <%

                                    }
                                }
                            %>
                        </select>
                        <input type="hidden" id="provinceName" name="provinceName"/>
                        District:
                        <select name="district" id="district" class="form-control">
                            <option value="">Select District</option>
                        </select>
                        <div class="button-submit container-fluid row">
                            <button name="btnRegister" id="register" class="btn btn-primary my-3 py-2 col-md-5">Register</button>
                            <span class="col-md-2"></span>
                            <a name="Login" id="Login" class="btn btn-primary col-md-5 my-3 py-2" href="Account/Login" role="button">Login</a>
                        </div>
                    </div>
                </div>
            </form>
        </div>

    </body>
</html>
