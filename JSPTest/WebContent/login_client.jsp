<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%@ page import="test.DB"%>
<%@ page import="test.RSA"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>Insert title here</title>
<%
RSA rsa = RSA.getEncKey();
request.setAttribute("publicKeyModulus", rsa.getPublicKeyModulus());
request.setAttribute("publicKeyExponent", rsa.getPublicKeyExponent());
request.getSession().setAttribute("__rsaPrivateKey__", rsa.getPrivateKey());
%>
<script>
function encryp(){
var rsaPublicKeyModulus = document.getElementById("rsaPublicKeyModulus").value;
var rsaPublicKeyExponent = document.getElementById("rsaPublicKeyExponent").value;
var encPassword = fnRsaEnc(document.getElementById("userPw").value, rsaPublicKeyModulus, rsaPublicKeyExponent);
document.frm.encPw.value = encPassword;
}
 

function fnRsaEnc(value, rsaPublicKeyModulus, rsaPpublicKeyExponent) {

    var rsa = new RSAKey();

    rsa.setPublic(rsaPublicKeyModulus, rsaPpublicKeyExponent);



     var encValue = rsa.encrypt(value);     // 사용자ID와 비밀번호를 RSA로 암호화한다.

     return encValue;

}
</script>
</head>



<body>
		이름 <input type="text" name="idText" />
		비밀번호 <input type="text" name="passText" id="userPw"/>
		<button onclick="encryp()" value="로그인" />
		<input type="hidden" id="rsaPublicKeyModulus" value="${publicKeyModulus}">

		<input type="hidden" id="rsaPublicKeyExponent" value="${publicKeyExponent}">
		<input type="hidden" name="encPw" value="">

</body>

</html>