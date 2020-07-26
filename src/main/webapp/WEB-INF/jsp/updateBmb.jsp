<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>修改信息</title>
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
                    <small>修改信息</small>
                </h1>
            </div>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/updateBmb" method="post">
        <input type="hidden" name="id" value="${Qbmb.getId()}"/>
        姓名：<input type="text" name="name" value="${Qbmb.getName()}"/>
        QQ：<input type="text" name="qq" value="${Qbmb.getQq()}"/>
        修真类型：<input type="text" name="type" value="${Qbmb.getType()}"/>
        加入时间：<input type="text" name="jointime" value="${Qbmb.getJointime()}"/>
        学校：<input type="text" name="school" value="${Qbmb.getSchool()}"/>
        学号：<input type="text" name="studyid" value="${Qbmb.getStudyid()}"/>
        日报链接：<input type="text" name="dailylink" value="${Qbmb.getDailylink()}"/>
        口号：<input type="text" name="hope" value="${Qbmb.getHope()}"/>
        <input type="submit" value="提交">
    </form>

</div>

</body>
</html>
