package server;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Possible KEY_SIZE values are 128, 192 and 256
 * Possible T_LEN values are 128, 120, 112, 104 and 96
 */

public class Decrypter {
    private SecretKey key;
    private int T_LEN = 128;
    private byte[] IV;
    private String sk;
    
    public Decrypter() {
    	this.sk = "CHuO1Fjd8YgJqTyapibFBQ==";
    	this.key = new SecretKeySpec(decode(this.sk),"AES");
    	this.IV = decode("e3IYYJC2hxe24/EO");
    }

    public String decrypt(String encryptedMessage) {
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher;
		try {
			decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
	        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, this.IV);
	        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
	        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
	        return new String(decryptedBytes);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

   
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

}