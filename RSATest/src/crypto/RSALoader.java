package crypto;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class RSALoader implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Map<String,Object> mapKey = RSACoder.initKey();
            byte[] publicKey = RSACoder.getPublicKey(mapKey);
            byte[] privateKey = RSACoder.getPrivateKey(mapKey);

            System.out.println("aa"+ Base64Utils.encodeToString(publicKey));

            System.out.println("bb"+publicKey.length);
            System.out.println("cc"+ Base64Utils.encodeToString(privateKey));
            System.out.println("dd"+privateKey.length);
            servletContextEvent.getServletContext().setAttribute("pubKey",Base64Utils.encodeToString(publicKey));
            servletContextEvent.getServletContext().setAttribute("priKey",Base64Utils.encodeToString(privateKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
