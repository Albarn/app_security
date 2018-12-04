package com.company.hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args){
        File messageFile,hashFile;
        try{
            messageFile=new File(args[0]);
            hashFile=new File(args[1]);
            hashFile.createNewFile();
        }catch (Exception e){
            System.out.println("usage:<message file> <hash file>");
            return;
        }

        try{
            byte[] message=new byte[(int) messageFile.length()];

            //read message
            FileInputStream fin=new FileInputStream(messageFile);
            fin.read(message);
            fin.close();

            byte[] hash= Sha1.encode(message);

            FileOutputStream writer=new FileOutputStream(hashFile);

            writer.write(hash);
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}