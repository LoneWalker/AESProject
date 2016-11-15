package aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;

/**
 * Created by azhar on 11/14/16.
 */
public class LibraryAES {

    public static boolean encrypt(FileInputStream in, long input_size_in_bytes, FileOutputStream out, byte[] key_bytes) throws Exception {
/*
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        //Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        */

        int pad_lenght=0;
        byte[] padding;
        int i;
        int j;

        long noOfBlocks = (long) Math.ceil(input_size_in_bytes / (double)Constants.blockSizeinBytes);
        boolean isRoundSize=true;


        // logics to add padding

        pad_lenght = Constants.blockSizeinBytes - (int)input_size_in_bytes % Constants.blockSizeinBytes;	// because blocks are 16 bytes or 128 bits long
        padding = new byte[pad_lenght];
        padding[0] = Constants.padkMarker;
        //boolean isPaddingRequired=false;
        if (pad_lenght!=Constants.blockSizeinBytes){
            isRoundSize = false;

            if (ControlUI.isPadding){
                for (i = 1,j=0; i < pad_lenght; i++,j++)
                    padding[i] = ControlUI.pad[j];
            }else {
                System.out.println("File size is not multiple of 16 bytes / 128 bits and padding is disabled. So, padding with all 0's");

                for (i = 1,j=0; i < pad_lenght; i++,j++)
                    padding[i] = 0;
            }
        }else {	// if file size is multiple of blockSizeinBytes, then do padding only if its enabled
            if (ControlUI.isPadding){
                for (i = 1,j=0; i < pad_lenght; i++,j++)
                    padding[i] = ControlUI.pad[j];
            }
        }

        // padding logic ends

        byte[] block = new byte[Constants.blockSizeinBytes];

        // library aes configuration

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKeySpec key = new SecretKeySpec(key_bytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        int count = 0;

        for(i=0; i < noOfBlocks; i++) {

            count=in.read(block,0,Constants.blockSizeinBytes);

            if (count<0){// could not read the file. Need to terminate encryption
                System.out.println("Could not read anymore. count:"+count);
                JOptionPane.showMessageDialog(null,"Could not read the whole file.\n No of bytes read = "+(Constants.blockSizeinBytes*i)+"\n Count value="+count);
                return false;
            }

            if (i==noOfBlocks-1){// last block of the file
                if (!isRoundSize){// need to copy padd to the remaining bytes;
                    for (int k=count, l=0; k<Constants.blockSizeinBytes;k++,l++){
                        block[k]=padding[l];
                    }

                }

            }

            block=cipher.doFinal(block);
            out.write(block,0,Constants.blockSizeinBytes);


        }
        // now only need to check if padding required even if file size is multiple of blockSizeinBytes
        if (isRoundSize && ControlUI.isPadding){

            /*
            for (int k=0; k<Constants.blockSizeinBytes;k++){
                block[k]=padding[k];

                block=cipher.doFinal(block);
                out.write(block,0,Constants.blockSizeinBytes);
            }
            */

            block=cipher.doFinal(padding);
            out.write(block,0,Constants.blockSizeinBytes);

        }

        return true;
    }

    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(cipherText),"UTF-8");
    }

    public static boolean decrypt(FileInputStream in, long input_size_in_bytes, FileOutputStream out, byte[] key_bytes) throws Exception {
        int i=0, count=0;
        byte[] block = new byte[Constants.blockSizeinBytes];
        long noOfBlocks = (long) Math.ceil(input_size_in_bytes / (double)Constants.blockSizeinBytes);

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        SecretKeySpec key = new SecretKeySpec(key_bytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        for(i=0; i < noOfBlocks; i++) {
            count=in.read(block,0,Constants.blockSizeinBytes);

            if (count<0){// could not read the file. Need to terminate encryption
                System.out.println("Could not read anymore. count:"+count);
                JOptionPane.showMessageDialog(null,"Could not read the whole file.\n No of bytes read = "+(Constants.blockSizeinBytes*i)+"\n Count value="+count);
                return false;
            }

            block=cipher.doFinal(block);

            if (i==noOfBlocks-1) {// last block of the file
                int marker_index=AES.findPadMarkerIndex(block);
                if (marker_index==-1){
                    System.out.println("No padding was done while encrypting this file");
                    out.write(block,0,count);
                }else if (marker_index==0){ // entire last byte is pad. So skip.
                    System.out.println("Entire Last word is a pad");
                }else {
                    out.write(block,0,marker_index);

                }

            }else {
                out.write(block,0,count);
            }

        }

        return true;
    }
}

