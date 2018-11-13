package com.company;

import java.util.HashMap;

public class Rsa implements Encoder {

    private long power(long i,long p,long n){
        long ans;
        if(p==0){
            ans=1;
        }else if(p%2==0){
            long a=power(i,p/2,n);
            ans=(a*a)%n;
        }else{
            ans=(i*power(i,p-1,n))%n;
        }
        return ans;
    }

    @Override
    public byte[] encode(byte[] key, byte[] message) {
        if(key.length<8){
            return new byte[0];
        }else{
            int n=BIConverter.toInt(new byte[]{key[0],key[1],key[2],key[3]});
            int p=BIConverter.toInt(new byte[]{key[4],key[5],key[6],key[7]});
            HashMap<Byte,Integer> map=new HashMap<>();
            for(int i=-128;i<128;i++){
                map.put((byte)i,(int)power(i,p,n));
                System.out.println(i+" "+power(i,p,n));
            }

            byte[] ans=new byte[message.length*4];

            for(int i=0;i<ans.length;i+=4){
                byte[] mi=BIConverter.toBytes(map.get(message[i/4]));
                System.out.println(mi[0]+" "+mi[1]+" "+mi[2]+" "+mi[3]);
                System.arraycopy(mi, 0, ans, i, mi.length);
            }
            return ans;
        }
    }

    @Override
    public byte[] decode(byte[] key, byte[] message) {
        return encode(key,message);
    }
}
