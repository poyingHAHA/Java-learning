<%--
  Created by IntelliJ IDEA.
  User: 88698
  Date: 2022/6/25
  Time: 上午 06:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="obj.Currency" %>
<%@ page errorPage="error.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
  int amount = Integer.parseInt(request.getParameter("amount"));
  Currency currency = new Currency(amount);
%>

<h2>美金: <%= currency.getUsd() %> </h2>
<h2>日幣: <%= currency.getJpn() %> </h2>
<h2>人民幣: <%= currency.getCyn() %> </h2>
</body>
</html>
