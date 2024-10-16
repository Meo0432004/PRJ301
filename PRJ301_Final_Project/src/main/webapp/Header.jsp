<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Models.User" %>
<%@ page import="Models.Admin" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Link Bootstrap CSS -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<!-- Link Bootstrap Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css">

<%
    String Username = "";
    String Adminname = "";
    Cookie cookie[] = request.getCookies();
    if (cookie != null) {
        for (Cookie c : cookie) {
            if (c.getName().equals("user")) {
                Username = c.getValue();
            } else if (c.getName().equals("admin")) {
                Adminname = c.getValue();
            }
        }
    }

    request.setAttribute("Username", Username);
    request.setAttribute("Adminname", Adminname);

    if (!Username.equals("") && session.getAttribute("user") == null) {
        session.setAttribute("user", Username);
    }
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light header">
    <div class="container-fluid">
        <div class="row w-100">
            <div class="col-2 d-flex align-items-center">
                <img src="<%=request.getContextPath()%>/images/logo_group1.jpg" alt="Logo" width="30%">
            </div>
            <div class="col-2 d-flex align-items-center">
                <a class="navbar-brand" href="#">Group 1</a><br><!-- comment -->
                <p>${requestScope.currentAdmin}</p>
            </div>
            <div class="col-4 d-flex align-items-center justify-content-center">
                <a class="navbar-brand" href="#">Shop name</a>
            </div>
            <div class="col-2 d-flex align-items-center">
                <form class="form-inline my-2 my-lg-0 search-bar w-100" action="<%=request.getContextPath()%>/Product/Search" method="get">
                    <div class="input-group w-100">
                        <input class="form-control mr-sm-2" value="${searchText}" type="search" placeholder="Search" aria-label="Search" name="pSearch">
                        <div class="input-group-append">
                            <button class="btn btn-outline-secondary" type="submit">
                                <i class="bi bi-search"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-2 d-flex align-items-center justify-content-end">
                <a class="btn btn-outline-primary my-2 my-sm-0 mr-2 position-relative" href="<%=request.getContextPath()%>/Cart">
                    <i class="bi bi-cart-fill"></i> Cart
                    <c:set var="cartSize" value="0"/>
                    <c:forEach var="c" items="${pageContext.request.cookies}">
                        <c:if test="${c.name.contains('cart_')}">
                            <c:set var="cartSize" value="${cartSize+1}"/>
                        </c:if>
                    </c:forEach>
                    <c:if test="${cartSize > 0}">
                        <span class="cart-badge">${cartSize}</span>
                    </c:if>
                </a>
                <c:if test="${not empty Username or not empty Adminname}">                   
                    <a class="btn btn-outline-danger my-2 my-sm-0" href="<%=request.getContextPath()%>/Account/LogOut">Logout</a>
                </c:if>
                <c:if test="${empty Username and empty Adminname}">
                    <a class="btn btn-outline-success my-2 my-sm-0" href="<%=request.getContextPath()%>/Account/Login">Login</a>
                </c:if>
            </div>
            <div class="col-12 mt-3">
                <c:choose>
                    <c:when test="${not empty Username}">
                        <p class="text-center">Hello, ${Username}!</p>
                        <div class="row">
                            <div class="col-md-6"></div>
                            <div class="col-md-3">
                                <a href="<%=request.getContextPath()%>/Home" class="btn btn-outline-primary">Home</a>
                            </div>
                            <div class="col-md-3">
                                <a href="<%=request.getContextPath()%>/UserManagement?action=Management">User</a>
                            </div>
                            
                        </div>

                    </c:when>
                    <c:when test="${not empty Adminname}">
                        <div class="row">
                            <div class="col-4">
                                <p class="text-center">Hello, ${Adminname}!</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <a href="<%=request.getContextPath()%>/Admin?action=ManageTransaction" class="btn btn-outline-primary">Manage Transaction</a>
                            </div>
                            <div class="col-md-4">
                                <a href="<%=request.getContextPath()%>/Home" class="btn btn-outline-primary">Home</a>
                            </div>
                            <div class="col-md-4">
                                <a href="<%=request.getContextPath()%>/Admin?action=Management" class="btn btn-outline-primary">Admin Management</a>
                            </div>

                        </div>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>
</nav>
