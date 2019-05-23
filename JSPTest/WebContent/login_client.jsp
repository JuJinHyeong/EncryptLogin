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



     var encValue = rsa.encrypt(value);     // �����ID�� ��й�ȣ�� RSA�� ��ȣȭ�Ѵ�.

     return encValue;

}
</script>
</head>



<body>
		�̸� <input type="text" name="idText" />
		��й�ȣ <input type="text" name="passText" id="userPw"/>
		<button onclick="encryp()" value="�α���" />
		<input type="hidden" id="rsaPublicKeyModulus" value="${publicKeyModulus}">

		<input type="hidden" id="rsaPublicKeyExponent" value="${publicKeyExponent}">
		<input type="hidden" name="encPw" value="">

</body>

</html>