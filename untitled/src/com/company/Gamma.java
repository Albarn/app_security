package com.company;

class Gamma {

    //simple random generator
    static class TRandom{
        //current value, multiplier, offset
        byte t,a,c;

        TRandom(byte t,byte a,byte c){
            this.t=t;
            this.a=a;
            this.c=c;
        }

        //return next random value in sequence
        byte next(){
            //calculate next value by this formula
            t=(byte)((a*t+c)%256-(256-Byte.MAX_VALUE));
            return t;
        }
    }


    static byte[] encode(byte[] key, byte[] message){
        int size=message.length;
        byte[] encoded=new byte[size];

        //if bad key was given
        //(less than 3 params)
        //then we replace it with empty key
        if(key.length<3)
        {
            key=new byte[]{0,0,0};
        }

        //create random sequence
        TRandom f=new TRandom(key[0],key[1],key[3]);

        //encode message with given sequence
        for(int i=0;i<size;i++){
            encoded[i]= (byte) (message[i]^f.next());
        }

        return encoded;
    }
}
