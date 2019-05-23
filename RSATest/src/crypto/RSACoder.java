package crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public abstract class RSACoder {

    public  static  final  String KEY_ALGORITHM = "RSA";
  
    public  static  final  String PUBLIC_KEY = "RSAPublicKey";
   
    public  static  final  String PRIVATE_KEY = "RSAPrivateKey";
 
    public static  final int MAX_DECRYPT_BLOCK = 128;

    public static  final int KEY_SIZE = 1024;

    public static Map<String,Object> initKey() throws NoSuchAlgorithmException {
      
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        keyPairGenerator.initialize(KEY_SIZE);
      
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
     
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();

        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String,Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY,rsaPublicKey);
        keyMap.put(PRIVATE_KEY,rsaPrivateKey);
        return keyMap;
    }
    public static byte[] encryptByPublicKey(byte[] data,byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
     
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        return cipher.doFinal(data);
    }

    public static  byte[] decryptByPrivateKey(byte[] data,byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
      
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
      
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
       
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE,privateKey);

        int inputLen = data.length;

        int offSet = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (inputLen-offSet > 0) {
            if(inputLen - offSet > MAX_DECRYPT_BLOCK) {
                out.write(cipher.doFinal(data,offSet,MAX_DECRYPT_BLOCK));
            }else {
                out.write(cipher.doFinal(data,offSet,inputLen-offSet));
            }
            offSet=offSet+MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] getPrivateKey(Map<String,Object> keyMap){
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return  key.getEncoded();
    }
   
    public static byte[] getPublicKey(Map<String,Object> keyMap){
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return  key.getEncoded();
    }


}
