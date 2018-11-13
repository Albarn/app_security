package com.company;

//byte<->integer converter
public class BIConverter {

    //mask for getting byte block
    private static int mask=0b1111_1111;

    public static byte[] toBytes(int i){
        byte[] ans=new byte[4];
        ans[3]=(byte)(i&mask); i>>=8;
        ans[2]=(byte)(i&mask); i>>=8;
        ans[1]=(byte)(i&mask); i>>=8;
        ans[0]=(byte)(i&mask); //i>>=8;
        return ans;
    }

    public static int toInt(byte[] bytes){
        int ans=0;
        ans|=(bytes[0]&mask);ans<<=8;
        ans|=(bytes[1]&mask);ans<<=8;
        ans|=(bytes[2]&mask);ans<<=8;
        ans|=(bytes[3]&mask);//ans<<=8;
        return ans;
    }
}
