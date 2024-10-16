<%-- 
    Document   : Buy
    Created on : Jun 23, 2024, 2:38:28 PM
    Author     : NGUYEN LE NHAT - CE181840
--%>

<%@page import="Models.User"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Models.CartItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Buy Product</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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

                $('input[name="purchaseType"]').change(function () {
                    if ($(this).val() == 'shipping') {
                        $('#shippingInfo').show();
                    } else {
                        $('#shippingInfo').hide();
                    }
                });
            });

        </script>

    </head>
    <body>
        <h1>Buy Product</h1>
        <a href="<%=request.getContextPath()%>/Home">Back to Home</a>
        <form action="Buy" method="post">
            <input type="hidden" value="Purchase" name="action"/>
            <c:forEach var="p" items="${listP}">
                <img src="<%=request.getContextPath()%>${p.productImage}" width="5%"/>
                <input type="hidden" value="${p.productID}" name="productID"/>
                Product Name:<input type="text" value="${p.productName}" name="productName" readonly/>
                Price:<input type="text" value="${p.productPrice}" name="productPrice" readonly/>VND
                Quantity:<input type="text" value="${p.productQuantity}" name="productQuantity" readonly/>
                Total:<input type="text" value="${p.productQuantity * p.productPrice}" name="totalPrice" readonly/>VND
                <hr/>
            </c:forEach>

            <c:set var="customer" value="${requestScope.customer}"/><br/>
            <%
                User customer = (User) request.getAttribute("customer");
            %>
            <h3>Your Information</h3>
            <h3>${requestScope.error}</h3>
            Your Name:<input type="text" value="${customer.username}" name="username"/><br/>
            Your Email:<input type="text" value="${customer.email}" name="email"/><br/>
            Your Phone:<input type="text" value="${customer.phone}" name="phone"/><br/>

            <h3>Purchase Type</h3>
            <input type="radio" name="purchaseType" value="shop" checked> Purchase at Shop<br>
            <input type="radio" name="purchaseType" value="shipping"> Shipping<br>

            <div id="shippingInfo" style="display: none;">
                <h3>Shipping Information</h3>
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

                    <option value="<%= province.get("id")%>" <%=customer != null && customer.getUserProvinceCity().equals(province.get("full_name_en")) ? "selected" : ""%>><%= province.get("full_name_en")%></option>
                    <%

                            }
                        }
                    %>
                </select>
                <input type="hidden" id="provinceName" name="provinceName" value="${customer.userProvinceCity}"/>
                District:
                <select name="district" id="district" class="form-control">
                    <option value="">Select District</option>
                    <c:if test="${not empty customer.userDistrict}">
                        <option value="${customer.userDistrict}" selected>${customer.userDistrict}</option>
                    </c:if>
                </select>
            </div>

            <input type="submit" value="Purchase" name="Purchase"/>
        </form>
    </body>
</html>
