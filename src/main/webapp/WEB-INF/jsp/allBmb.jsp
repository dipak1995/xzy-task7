<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生信息列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

<img src="${pageContext.request.contextPath}/image/aa.jpg" width="100px">

<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>学生信息列表 —— 显示所有学生信息</small>
                </h1>
                <h3>
                    <a href="${pageContext.request.contextPath}/logout">注销</a>
                </h3>

                <br>
                <form action="/upimage" method="post" enctype="multipart/form-data">
                    图片上传:<br>
                    <input type="file" name="file" id="image" placeholder="请选择图片" >
                    <br>
                    <input type="submit" value="上传">
                </form>

            </div>
        </div>
    </div>


    <div class="row">
        <div class="col-md-4 column">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/toAddBmb">新增</a>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>姓名</th>
                    <th>QQ</th>
                    <th>修真类型</th>
                    <th>加入时间</th>
                    <th>学校</th>
                    <th>学号</th>
                    <th>日报链接</th>
                    <th>口号</th>
                    <th>创建时间</th>
                    <th>更新时间</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="bmb" items="${list}">
                    <tr>
                        <td>${bmb.id}</td>
                        <td>${bmb.name}</td>
                        <td>${bmb.qq}</td>
                        <td>${bmb.type}</td>
                        <td>${bmb.jointime}</td>
                        <td>${bmb.school}</td>
                        <td>${bmb.studyid}</td>
                        <td>${bmb.dailylink}</td>
                        <td>${bmb.hope}</td>
                        <td>${bmb.create_at}</td>
                        <td>${bmb.update_at}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/toUpdateBmb?id=${bmb.getId()}">更改</a> |
                            <a href="${pageContext.request.contextPath}/del/${bmb.getId()}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>