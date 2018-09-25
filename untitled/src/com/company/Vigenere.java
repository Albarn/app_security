package com.company;

public class Vigenere {

    public static byte[] Encode(byte[] key, byte[] message){
        int size=message.length;
        byte[] encoded=new byte[size];

        for(int i=0,j=0;i<size;i++,j++,j%=key.length){
            encoded[i]= (byte) (message[i]^key[j]);
        }

        return encoded;
    }
}
