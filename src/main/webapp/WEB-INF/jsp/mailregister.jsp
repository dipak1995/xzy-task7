<%--
  Created by IntelliJ IDEA.
  User: djy
  Date: 2020/6/30
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>邮箱注册页面</title>
</head>

<h1>邮箱注册页面</h1>
<hr>
<body>
<script type="text/javascript">
    function x() {
        
    }
</script>

<form action="${pageContext.request.contextPath}/mailregister" method="post">

    请输入用户名:<br>
    <input type="text" name="name" id="username" placeholder="请输入小于16位的用户名" maxlength="16">
    ${NameError}
    <br>
    请输入密码:<br>
    <input type="text" name="password" id="pass" placeholder="请输入小于16位的密码"  maxlength="16">
    ${PassError}
    <br>

    邮箱:<br>
    <input type="text" name="address" id="email2" placeholder="请输入您的邮箱">

    <br>
    请输入收到的验证码:<br>
    <input type="text" name="sms" id="yzm" placeholder="请输入4位验证码" maxlength="4">
    <input type="button" name="" id="verCodeBtn" value="获取验证码" onclick="settime(this);"/>
    ${YzmError}
    <br>
    <br>
    <input type="submit" value="注册">


</form>

${error}${msg}

</div>
<br>

<script src="http://www.lanrenzhijia.com/ajaxjs/jquery.min.js"></script>
<script>
    //验证码
    var counts = 10;
    function settime(val) {
        if(counts == 0) {
            val.removeAttribute("disabled");
            val.value = "获取验证码";
            counts = 10;
            return false;
        } else {
            val.setAttribute("disabled", true);
            val.value = "重新发送（" + counts + "）";
            counts--;
        }

        setTimeout(function() {
            settime(val);
        }, 1000);
    }


    //获取验证码
    $(function(){
        $("#verCodeBtn").click(function() {

            $.ajax({
                url: "${pageContext.request.contextPath}/send2",
                data: {"address":$("#email2").val()},
                type: "post",
                success: function(data) {
                    if(JSON.parse(data).state === 404 || JSON.parse(data).state === 202 || userinfo.UserPhoneNum === '86//') {
                        alert("验证码发送失败")
                    } else {
                        alert("验证码发送成功，请耐心等待")
                    }
                },

//error: function() {
//alert("发送失败");
//}

            });
        });
    })
</script>

</hr>
<a href="${pageContext.request.contextPath}/toregister">如果你想使用手机注册，请点击此处</a>
<br>
<a href="${pageContext.request.contextPath}/tologin">已有账号？点此直接登录</a>
</body>
</html>
