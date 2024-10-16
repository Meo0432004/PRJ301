<%-- 
    Document   : userHistoryOrder
    Created on : Jul 7, 2024, 10:33:04 AM
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
        <c:if test="${empty requestScope.listO}">
            <h3>You have not placed an order yet</h3>
        </c:if>
        <c:if test="${not empty requestScope.listO}">
            <h2>History Order</h2>
            <table border="1px">
                <tr>
                    <th>Username</th>
                    <th>Total Price</th>
                    <th>Order Status</th>
                    <th>Date</th>
                    <th>Address</th>
                </tr>
                <c:forEach var="O" items="${requestScope.listO}">
                    <tr>
                        <th>${O.username}</th>
                        <th>${O.totalPrice}</th>
                        <th>${O.orderStatus}</th>
                        <th>${O.date}</th>
                        <th></th>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </body>
</html>
