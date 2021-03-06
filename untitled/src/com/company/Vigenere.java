package com.company;

class Vigenere implements Encoder {

    @Override
    public byte[] encode(byte[] key, byte[] message){
        int size=message.length;
        byte[] encoded=new byte[size];

        for(int i=0,j=0;i<size;i++,j++,j%=key.length){
            encoded[i]= (byte) (message[i]^key[j]);
        }

        return encoded;
    }

    @Override
    public byte[] decode(byte[] key, byte[] message) {
        return encode(key,message);
    }
}
