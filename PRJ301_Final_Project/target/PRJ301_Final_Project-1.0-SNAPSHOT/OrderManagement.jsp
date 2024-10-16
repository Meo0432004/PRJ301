<%-- 
    Document   : OrderManagement
    Created on : Jul 5, 2024, 4:09:57 PM
    Author     : NHAT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Order Management</h2>
        <c:if test="${requestScope.listO==null}">
            <h3>There is no Order</h3>
        </c:if>
        <c:if test="${requestScope.listO != null}">
            <table border="1px">
                <tr>
                    <th>Order ID</th>
                    <th>User ID</th>
                    <th>Username</th>
                    <th>User Type</th>
                    <th>Total Price</th>
                    <th>Order Status</th>
                    <th>Date</th>
                    <th>is Shipping</th>
                    <th>View</th>
                </tr>

                <c:forEach var="O" items="${requestScope.listO}">
                    <tr>
                        <td>${O.orderID}</td>
                        <td>${O.userID}</td>
                        <td>${O.username}</td>
                        <td>${O.userType}</td>
                        <td>${O.totalPrice}</td>
                        <td>${O.orderStatus}</td>
                        <td>${O.date}</td>
                        <td>${O.isShipping}</td>
                        <td><a href="#" id="showOrderLink" data-orderID="${O.orderID}">Show Order Items</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
