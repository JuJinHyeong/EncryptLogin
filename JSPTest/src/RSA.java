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
	// ���ǿ� ����Ű�� ���ڿ��� Ű���Ͽ� ����Ű�� �����Ѵ�.
	session.setAttribute("__rsaPrivateKey__", privateKey);

	// ����Ű�� ���ڿ��� ��ȯ�Ͽ� JavaScript RSA ���̺귯�� �Ѱ��ش�.
	RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);

	String publicKeyModulus = publicSpec.getModulus().toString(16);
	String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

	request.setAttribute("publicKeyModulus", publicKeyModulus);
	request.setAttribute("publicKeyExponent", publicKeyExponent);

	request.getRequestDispatcher("/WEB-INF/loginForm.jsp").forward(request, response);
}
