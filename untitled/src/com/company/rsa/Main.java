package com.company.rsa;

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

            FileWriter writer=new FileWriter(openKey);
            writer.write(generator.getN()+"\r\n");
            writer.write(Integer.toString(generator.getD()));
            writer.close();

            writer=new FileWriter(privateKey);
            writer.write(generator.getN()+"\r\n");
            writer.write(Integer.toString(generator.getE()));
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
