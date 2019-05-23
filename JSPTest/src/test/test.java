package test;

import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class test {
    public static void main(String[] args){
    	RSA rsa = RSA.getEncKey();
    	String module=rsa.getPublicKeyModulus();
    	String expon=rsa.getPublicKeyExponent();
    	PrivateKey priv=rsa.getPrivateKey();
    	System.out.println(module);
    	System.out.println(expon);
    	System.out.println(priv);

    }
}
