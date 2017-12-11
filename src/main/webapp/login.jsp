<%--
  Created by IntelliJ IDEA.
  User: liuqianshun
  Date: 2017/12/10
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h4>登录页面</h4>

    <form action="shiro/login" method="post">
        username:<input type="text" name="username">
        <br>
        password:<input type="password" name="password">
        <br>
        <input type="submit" value="提交">
    </form>
</body>
</html>
