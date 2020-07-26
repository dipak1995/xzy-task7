<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>

<h1>登录页面</h1>
<hr>

<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    用户名：<input type="text" name="name"> <br>
    密码：<input type="password" name="password"> <br>
    <input type="submit" value="登录">

</form>

<a href="${pageContext.request.contextPath}/toregister">电话注册</a>
<a href="${pageContext.request.contextPath}/tomailregister">邮箱注册</a>
${error}${msg}
</body>
</html>
