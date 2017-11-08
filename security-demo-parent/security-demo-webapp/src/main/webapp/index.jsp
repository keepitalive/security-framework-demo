<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page language="java" import="tdh.tnt.security.demo.framework.Constants" %>
<%@ page import="tdh.tnt.security.demo.framework.model.Subject" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

欢迎你:

<%

    Subject subject = (Subject) session.getAttribute(Constants.SESSION_KEY_SUBJECT);

    out.print(subject.getUserId());

%>


<ul>
    <li>
        <a href="role1.jsp">功能1(需要角色:role1)</a>
    </li>
    <li>
        <a href="role2.jsp">功能2(需要角色:role2)</a>
    </li>
</ul>

</body>
</html>