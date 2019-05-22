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
		Integer result=DB.connect(idStr, pwStr);
		if(result==1){
			out.println("<script>alert('로그인이 완료되었습니다');</script>");
		}
	%>

</body>

</html>