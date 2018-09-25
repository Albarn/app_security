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
                        "(-l1|(-l2 [-encode|-decode]))";

        //3 params required
        if(args.length<3){
            System.out.print(helpMessage);
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
            if(args.length<=3 || args[3].equals("-l1")){

                //lab1 encoding/decoding
                encoded= Vigenere.Encode(key,message);
            }else if(args[3].equals("-l2")){

                //lab2
                if(args.length<=4 || args[4].equals("-encode")){

                    //encoding
                    encoded=Histogram.Encode(key,message);
                }else if(args[4].equals("-decode")){

                    //decoding
                    encoded=Histogram.Decode(key,message);
                }else{
                    System.out.print(helpMessage);
                    return;
                }
            }else{
                System.out.print(helpMessage);
                return;
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

