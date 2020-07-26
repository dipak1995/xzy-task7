<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>新增学员</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>新增学员</small>
                </h1>
            </div>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/addBmb" method="post">
        编号：<input type="text" name="id"><br><br><br>
        姓名：<input type="text" name="name"><br><br><br>
        QQ：<input type="text" name="qq"><br><br><br>
        修正类型：<input type="text" name="type"><br><br><br>
        加入时间：<input type="text" name="jointime"><br><br><br>
        学校：<input type="text" name="school"><br><br><br>
        学号：<input type="text" name="studyid"><br><br><br>
        日报链接：<input type="text" name="dailylink"><br><br><br>
        口号：<input type="text" name="hope"><br><br><br>
        <input type="submit" value="添加">
    </form>

</div>
