package com.company.filecomparison;

import java.io.File;
import java.io.FileInputStream;

public class Main {
    private static final String FAIL="hash files are not equal";
    private static final String SUCCESS="hash files are equal";

    public static void main(String[] args){
        File hashFile1,hashFile2;
        try{
            hashFile1=new File(args[0]);
            hashFile2=new File(args[1]);
        }catch (Exception e){
            System.out.println("usage:<hash file1> <hash file2>");
            return;
        }

        try{
            if(hashFile1.length()!=hashFile2.length()){
                System.out.println(FAIL);
                return;
            }
            byte[] hash1=new byte[(int) hashFile1.length()];
            byte[] hash2=new byte[(int) hashFile2.length()];

            //read message
            FileInputStream fin=new FileInputStream(hashFile1);
            fin.read(hash1);
            fin.close();
            fin=new FileInputStream(hashFile2);
            fin.read(hash2);
            fin.close();

            for(int i=0;i<hash1.length;i++){
                if(hash1[i]!=hash2[i]){
                    System.out.println(FAIL);
                    return;
                }
            }
            System.out.println(SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}