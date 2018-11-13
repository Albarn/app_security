package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {
    private static final int KEY_PARAM=0;
    private static final int MESSAGE_PARAM=1;
    private static final int OUTPUT_PARAM=2;
    private static final int LAB_NUMBER=3;
    private static final int OPTION=4;


    public static void main(String[] args){
        //check correct usage
        String helpMessage =
                "usage: <key filepath> " +
                        "<message filepath> " +
                        "<output filepath> " +
                        "(-l1|-l2|-l3|-l4) [-encode|-decode]";

        //4 params required
        if((args.length-1)<LAB_NUMBER){
            System.out.print(helpMessage);
            return;
        }

        //files for key, message and encoded message
        File keyFile=new File(args[KEY_PARAM]);
        File messageFile= new File(args[MESSAGE_PARAM]);
        File outFile=new File(args[OUTPUT_PARAM]);

        //reader for key and message files
        FileInputStream fin;
        try {

            //create byte arrays according to file length
            byte[] key=new byte[(int) keyFile.length()];
            byte[] message=new byte[(int) messageFile.length()];

            //read key
            fin=new FileInputStream(keyFile);
            fin.read(key);
            fin.close();

            //read message
            fin=new FileInputStream(messageFile);
            fin.read(message);
            fin.close();

            //get result
            byte[] encoded;

            //choose crypt method according to
            //user data
            Encoder encoder=null;
            switch(args[LAB_NUMBER]){
                case "-l1":encoder=new Vigenere();break;
                case "-l2":encoder=new Histogram();break;
                case "-l3":encoder=new Gamma();break;
                case "-l4":encoder=new Kasiski();break;
                case "-l5":encoder=new Rsa();break;
                default:{
                    System.out.print(helpMessage);
                    return;
                }
            }
            if(args.length>OPTION && args[OPTION].equals("-decode")){
                    encoded=encoder.decode(key,message);
            }else{
                encoded=encoder.encode(key,message);
            }

            //write it to output file
            FileOutputStream fout=new FileOutputStream(outFile);
            outFile.createNewFile();
            for (byte anEncoded : encoded) {
                fout.write(anEncoded);
            }
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

