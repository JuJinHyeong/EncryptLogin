<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="CN">
<head>
    <script src="https://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <script type="text/javascript" src="js/jsencrypt.min.js"></script>
</head>
<body>
<h2>로그인</h2>
<%
  String pubKey = (String) request.getServletContext().getAttribute("pubKey");
%>
<form id="loginForm" action="<%= request.getContextPath() %>/user/login" method="post" onsubmit="return false;">
    <input id="id" type="text" placeholder="아이디를 입력하세요" name="id">
    <input id="password" type="password" placeholder="비밀번호를 입력하세요" name="password">
    <input id="pubKey" type="hidden" name="pubKey" value="${pubKey}">
    <button id="loginSubmitBtn" type="submit">로그인</button>
</form>
<script>
//비밀번호 암호화시키는 부분
    $(function () {
        $("#loginSubmitBtn").on("click",function () {
            $(this).attr("disabled","disabled");
            $(this).attr("value","加密中");
            // Encrypt with the public key...
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey($('#pubKey').val());
            var encrypted = encrypt.encrypt($('#password').val());
            $('#password').val(encrypted);
            $("#loginForm").attr("onsubmit","");
            $("#loginForm").submit();
            //$(this).removeAttr("disabled");
        });
    });
</script>
</body>
</html>
