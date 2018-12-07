import java.io.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.math.*;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
public class DHAES {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
    
    public static void main(String args[]) throws IOException {
    	
    	BigInteger p = new BigInteger("521419622856657689423872613771");
    	BigInteger q = new BigInteger("153312796669816512924567214991");
    	BigInteger A = new BigInteger("334069331527797282526896253437");
    	
    	int b = 17207;
    	
    	BigInteger B = q.pow(b).mod(p);
    	
    	System.out.println("B: " + B);

    	BigInteger K = A.pow(b).mod(p);
    	
    	System.out.println("K: " + K);
    	
    	File file = new File("data.des");
    	FileInputStream in = new FileInputStream(file);
    	FileOutputStream out = new FileOutputStream("des.jpeg");
    	
    	byte[] test = new byte[(int) file.length()];
    	
    	in.read(test);
    	
    	byte[] decryption = decrypt(test, K.toString());
    	
    	out.write(decryption);
    }
 
    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 8);
            secretKey = new SecretKeySpec(key, "DES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(byte[] strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(strToEncrypt);
        }
        catch (Exception e) {
            System.out.println("Error while encrypting bytes: " + e.toString());
        }
        return null;
    }

 
    public static byte[] decrypt(byte[] strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(strToDecrypt);
        }
        catch (Exception e) {
            System.out.println("Error while decrypting bytes: " + e.toString());
        }
        return null;
    }
 
}