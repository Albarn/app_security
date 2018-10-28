package com.company;

class Kasiski implements Encoder {
    private static final double MAX_ERROR = 1e-2;

    @Override
    public byte[] encode(byte[] key, byte[] message) {
        return new byte[0];
    }

    @Override
    public
        //find key to decode cipher with Viginere algorithm
    byte[] decode(byte[] sim, byte[] cipher){

        //find coincidence index in
        //similar text
        //sim must be written on the same language
        //that encoded message did
        double ci = test(sim,1);

        //find length of potential key,
        //ci(cii) in part of cipher must be
        //very close to ci in sim
        int t=1;
        double e=1;
        for(int ti=1;ti<=Math.sqrt(cipher.length);ti++){
            double cii=test(cipher,ti);
            double ei=Math.abs(cii-ci);

            //check if this length gives us
            //better error value
            if(e>ei){
                t=ti;
                e=ei;
            }

            //if we got allowed error value,
            //we satisfied with this length
            //there is no need to repeat key
            if(e<MAX_ERROR){
                break;
            }
        }

        //find key by mode(sim) xor mode(part of cipher)
        byte[] key=new byte[t];
        byte simMode=findMode(sim,1,0);
        for(int i=0;i<t;i++){
            byte mode=findMode(cipher,t,i);
            key[i]=(byte)(mode^simMode);
        }

        return key;
    }

    //find the most popular letter in text
    private byte findMode(byte[] text, int t, int offset) {
        byte mode=Byte.MIN_VALUE;

        //get total number of letters
        int[] cnt=getCnt(text,t,offset);

        //then find max of them
        for(int i=0;i<cnt.length;i++){
            if(cnt[mode-Byte.MIN_VALUE]<cnt[i]){
                mode=(byte)(i-Byte.MIN_VALUE);
            }
        }
        return mode;
    }

    //Kasiski test
    private double test(byte[] text, int t) {
        //length of key must be positive
        if(t<1)return -1;

        //calculate squares of cnt letters
        //then aggregate them into sum
        int[] cnt = getCnt(text, t,0);
        double ci=0;
        for(int i=0;i<cnt.length;i++){
            ci+=Math.pow(cnt[i],2);
        }

        //divide it by length of text part
        ci/=Math.pow(text.length/t,2);

        //return coincidence index
        return ci;
    }

    //get number of letters in text
    private int[] getCnt(byte[] text, int t, int offset) {
        //initialize number of each byte with zero
        int[] cnt=new int[Byte.MAX_VALUE-Byte.MIN_VALUE+1];
        for(int i=0;i<cnt.length;i++){
            cnt[i]=0;
        }

        //count number
        for (int i=offset;i<text.length;i+=t) {
            cnt[text[i]-Byte.MIN_VALUE]++;
        }
        return cnt;
    }

}
