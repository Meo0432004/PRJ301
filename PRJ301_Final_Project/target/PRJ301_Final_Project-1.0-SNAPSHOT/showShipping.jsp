<%-- 
    Document   : showShipping
    Created on : Jul 6, 2024, 9:41:42 PM
    Author     : NHAT
--%>

<%@page import="Models.Shipping"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Shipping</h2>
        <c:if test="${requestScope.listSh==null}">
            <h3>There is no shipping</h3>
        </c:if>
        <c:if test="${requestScope.listSh!=null}">
            <table border="1px">
                <tr>
                    <th>Shipping ID</th>
                    <th>Order ID</th>
                    <th>Username</th>
                    <th>Province/City</th>
                    <th>District</th>
                </tr>


                <c:forEach var="sh" items="${requestScope.listSh}">
                    <tr>
                        <th>${sh.shippingID}</th>
                        <th>${sh.orderID}</th>
                        <th>${sh.username}</th>
                        <th>${sh.shippingProvince}</th>
                        <th>${sh.shippingDistrict}</th>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
