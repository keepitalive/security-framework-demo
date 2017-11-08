<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page language="java" import="tdh.tnt.security.demo.framework.Constants" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>


<div style="text-align: center; margin-top: 100px;">


    <div style="color: red">
    <%
        String error = (String) request.getAttribute("LOGIN_ERROR");

        if (null != error) {
            out.print(error);
        }

    %>
    </div>

<form action="/login.do" method="post">

    用户名:<input type="text" name="userId" /> <br/>
    密码:<input type="password" name="password" /><br/>

    <button>登录</button>
</form>

</div>
</body>
</html>