<%--
  Created by IntelliJ IDEA.
  User: 88698
  Date: 2022/6/25
  Time: 上午 07:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="counter" class="obj.Counter" scope="application"></jsp:useBean>
<%--<jsp:useBean id="counter" class="obj.Counter" scope="session"></jsp:useBean>--%>
<%--<jsp:useBean id="counter" class="obj.Counter" scope="page"></jsp:useBean>--%>
<%--<jsp:useBean id="counter" class="obj.Counter" scope="request"></jsp:useBean>--%>
<head>
    <title>Title</title>
</head>
<body>
    <%
        counter.increaseCount();
    %>
    <p> You are visitor number <%= counter.getCount() %></p>
</body>
</html>
