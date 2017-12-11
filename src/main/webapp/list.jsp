<%--
  Created by IntelliJ IDEA.
  User: liuqianshun
  Date: 2017/12/10
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h4>登录成功页面</h4>
    Welcome:<shiro:principal/><br/>
    <shiro:hasRole name="admin">
        <a href="admin.jsp">Admin Page</a><br/>
    </shiro:hasRole>
    <shiro:hasRole name="user">
        <a href="user.jsp">User Page</a><br/>
    </shiro:hasRole>
    <a href="/shiro/logout">登出</a>
</body>
</html>
