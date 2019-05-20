import java.security.*;
import javax.servlet.http.HttpSession;

public class RSA {
	KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
	generator.initialize(KEY_SIZE);

	KeyPair keyPair = generator.genKeyPair();
	KeyFactory keyFactory = KeyFactory.getInstance("RSA");

	PublicKey publicKey = keyPair.getPublic();
	PrivateKey privateKey = keyPair.getPrivate();
	
	HttpSession session = request.getSession();
	// 세션에 공개키의 문자열을 키로하여 개인키를 저장한다.
	session.setAttribute("__rsaPrivateKey__", privateKey);

	// 공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
	RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

	String publicKeyModulus = publicSpec.getModulus().toString(16);
	String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

	request.setAttribute("publicKeyModulus", publicKeyModulus);
	request.setAttribute("publicKeyExponent", publicKeyExponent);

	request.getRequestDispatcher("/WEB-INF/loginForm.jsp").forward(request, response);
}
