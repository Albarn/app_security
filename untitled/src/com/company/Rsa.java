package com.company;

public class Rsa implements Encoder {

    //quick power calculation with mod
    private long power(long i,long p,long n){
        long ans;

        if(p==0){
            //in case of zero, we return 1
            ans=1;
        }else if(p%2==0){
            //in case of even, we calculate square
            //of half-parts
            long a=power(i,p/2,n);
            ans=(a*a)%n;
        }else{
            //in other case we multiply number
            //by this number in power minus 1
            ans=(i*power(i,p-1,n))%n;
        }
        return ans;
    }

    @Override
    public byte[] encode(byte[] key, byte[] message) {
        //key must contain modulo and power
        if(key.length<8){
            return new byte[0];
        }else{

            //get params
            int n=BIConverter.toInt(new byte[]{key[0],key[1],key[2],key[3]});
            int p=BIConverter.toInt(new byte[]{key[4],key[5],key[6],key[7]});

            //encoded message is bigger 4 times,
            //each byte multiplies to int,
            //then, converts to byte array and appends to answer
            byte[] ans=new byte[message.length*4];
            for(int i=0;i<ans.length;i+=4){
                byte[] mi=BIConverter.toBytes((int)power(message[i/4],p,n));
                System.arraycopy(mi, 0, ans, i, mi.length);
            }
            return ans;
        }
    }

    @Override
    public byte[] decode(byte[] key, byte[] message) {
        //key should contain modulo and power
        if(key.length<8){
            return new byte[0];
        }else{
            int n=BIConverter.toInt(new byte[]{key[0],key[1],key[2],key[3]});
            int p=BIConverter.toInt(new byte[]{key[4],key[5],key[6],key[7]});

            //answer is 4 times smaller
            byte[] ans=new byte[message.length/4];
            for(int i=0;i<message.length;i+=4){
                //get integer from each four byte blocks
                //then, get power of it
                int mi=BIConverter.toInt(new byte[]{message[i],message[i+1],message[i+2],message[i+3]});
                ans[i/4]=(byte)power(mi,p,n);
            }
            return ans;
        }
    }
}
