<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%@ page import="test.DB"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>Insert title here</title>

</head>



<%
	request.setCharacterEncoding("euc-kr");

	String idStr = request.getParameter("idText");
	String pwStr = request.getParameter("passText");
%>



<body>

	<%
		DB.connect(idStr, pwStr);
		
	%>




</body>

</html>