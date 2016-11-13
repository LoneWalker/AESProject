/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author azhar
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;

public class Main {

    /**
     * @param args the command line arguments
     */
    
    static String IV = "AAAAAAAAAAAAAAAA";
    //static String plaintext = "test text 123\0\0\0"; /*Note null padding*/
    static String plaintext = "BBBBBBBBBBBBBBBB"; /*Note null padding*/
    static String encryptionKey = "AAAAAAAAAAAAAAAA";// 16 bytes or 128 bits
    public static void main(String [] args) {
        try {

            System.out.println("==Java Library Encryption==");
            System.out.println("plain:   " + plaintext);

            byte[] cipher = encrypt(plaintext, encryptionKey);

            System.out.print("cipher:  ");
            printCipher(cipher);

            //String decrypted = decrypt(cipher, encryptionKey);

            //System.out.println("decrypt: " + decrypted);

            byte[] tempcipher=cipher;


            //manually implemented

            //plaintext = "Algoritmul de criptare AES";
            //AES
            //encryptionKey = "1a25s8fe5dsg65ad";

            System.out.println("==Manual implementation of AES==");
            System.out.println("Text clar: "+plaintext);
            cipher = aes.AES.encrypt(plaintext.getBytes(), encryptionKey.getBytes());
            System.out.print("Cipher : ");
            printCipher(cipher);

            /*
            byte [] dec = tsd.crypto.algoritm.AES.decrypt(cipher, encryptionKey.getBytes());
            System.out.println("Text decriptat AES: "+new String(dec));
            System.out.println("------------------");
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printCipher(byte[] cipher){
        for (int i=0; i<cipher.length; i++)
            System.out.print(new Integer(cipher[i])+" ");
        System.out.println("");
    }
    public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
/*
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        //Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        */

        //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(cipherText),"UTF-8");
    }

    private byte[] readByteBlock(InputStream in, int offset, int noBytes) throws IOException {
        byte[] result = new byte[noBytes];
        int bytesread=in.read(result, offset, noBytes);
        // check the number of bytes read
        return result;
    }


    public byte[][] toByteArray(File file, int byteBlockSize) throws IOException {

        InputStream in = new FileInputStream(file);
        long noOfBlocks = (long) Math.ceil((double)file.length() / (double)byteBlockSize);
        byte[][] result = new byte[(int)noOfBlocks][byteBlockSize];
        int offset = 0;
        for(int i = 0; i < result.length; i++) {
            result[i] = readByteBlock(in, offset, byteBlockSize);
        }
        return result;
    }
    
}
