<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/31
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <label>RSA 암호화 결과：${encryptPassword}</label>
</div>
<div>
    <label>복호화 결과：${password}</label>
    
    <script>
    if(${confirm}==1){
    	alert("로그인이 성공하였습니다.");
    }else{
    	alert("로그인이 실패하였습니다.");
    }
    </script>
</div>
</body>
</html>
