package com.company.rsa;

import com.company.BIConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class Main {

    public static void main(String[] args){
        File openKey,privateKey;
        try{
            openKey=new File(args[0]);
            openKey.createNewFile();
            privateKey=new File(args[1]);
            privateKey.createNewFile();
        }catch (Exception e){
            System.out.println("usage:<open key file> <private key file>");
            return;
        }

        try{
            RsaGenerator generator=new RsaGenerator();
            generator.setKeys();

            FileOutputStream writer=new FileOutputStream(openKey);
            writer.write(BIConverter.toBytes(generator.getN()));
            writer.write(BIConverter.toBytes(generator.getE()));
            writer.close();

            writer=new FileOutputStream(privateKey);
            writer.write(BIConverter.toBytes(generator.getN()));
            writer.write(BIConverter.toBytes(generator.getD()));
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
