package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args){
        //check correct usage
        String helpMessage =
                "usage: <key filepath> " +
                        "<message filepath> " +
                        "<output filepath> " +
                        "(-l1|(-l2 (-encode|-findKey))|-l3|-l4)";

        //3 params required
        if(args.length<4){
            System.out.print(helpMessage);
            return;
        }

        //files for key, message and encoded message
        File keyFile=new File(args[0]);
        File messageFile= new File(args[1]);
        File outFile=new File(args[2]);

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
            switch(args[3]){
                case "-l1":encoded=Vigenere.encode(key,message);break;
                case "-l2":{
                    if(args.length<5){
                        System.out.print(helpMessage);
                        return;
                    }
                    switch (args[4]){
                        case "-encode":encoded=Histogram.encode(key,message);break;
                        case "-findKey":encoded=Histogram.decode(key,message);break;
                        default: {
                            System.out.print(helpMessage);
                            return;
                        }
                    }
                }break;
                case "-l3":
                {
                    //System.out.println(key.length);
                    encoded=Kasiski.findKey(message,key);
                }break;
                case "-l4":
                {
                    encoded=Gamma.encode(key,message);
                }break;
                default:{
                    System.out.print(helpMessage);
                    return;
                }
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

