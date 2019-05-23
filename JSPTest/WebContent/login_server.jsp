<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="test.DB" %>
<%@ page import="test.RSA" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("euc-kr");
		String idStr = request.getParameter("idText");
		String pwStr = request.getParameter("encPw");
		out.println("<script>alert(pwStr);</script>");
		String decPw = RSA.decryptRsa((PrivateKey) request.getSession().getAttribute("__rsaPrivateKey__"), pwStr);

		Integer result=DB.connect(idStr, decPw);
		if(result==1){
			out.println("<script>alert('로그인이 완료되었습니다');</script>");
		}
	%>
</body>
</html>