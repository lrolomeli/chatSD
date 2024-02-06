import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;

public class AESCS{
    
    private SecretKey key;
    private final int KEY_SIZE = 128;
    private final int T_LEN = 128;
    private byte[] IV;

    public void init() throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
    }

    public void initFromStrings(String secretKey, String IV){
        key = new SecretKeySpec(decode(secretKey), "AES");
        this.IV = decode(IV);
    }

    public String encrypt(String message) throws Exception{
        byte[] messageInBytes = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        IV = encryptionCipher.getIV();
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception{
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, IV);
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    private String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
    private byte[] decode(String data){
        return Base64.getDecoder().decode(data);
    }

    public static void main(String[] args){
        try{
            AESCS aes = new AESCS();
            aes.initFromStrings("CHu01Fjd8YgJqTyapibFBQ==", "e3IYYJC2hxe24/E0");
            String encryptedMessage = aes.encrypt("Life is a journey, not a destination. Cherish every moment, for it will pass, as all things do. Enjoy the ride, and keep moving forward.");
            String decryptedMessage = aes.decrypt(encryptedMessage);

            System.err.println("Encrypted Message = "+encryptedMessage);
            System.err.println("Decrypted Message = "+decryptedMessage);
        }catch(Exception ignored){

        }
    }

}