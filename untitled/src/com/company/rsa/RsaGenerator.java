package com.company.rsa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class RsaGenerator {

    public RsaGenerator(){
        setPrimes();
        n=e=d=-1;
    }

    private boolean[] isPrime=new boolean[1<<16];
    private void setPrimes(){

        for(int i=0;i<isPrime.length;i++){
            isPrime[i]=true;
        }
        for(int i=2;i*i<isPrime.length;i++){
            if(isPrime[i]){
                for(int j=i*i;j<isPrime.length;j+=i){
                    isPrime[j]=false;
                }
            }
        }
    }

    private int getRandomPrime(){
        Random f=new Random(LocalDateTime.now().getNano());
        while (true){
            int ans=f.nextInt(isPrime.length-1);
            if(isPrime[ans]){
                return ans;
            }
        }
    }

    private int n,e,d;

    public int getD() {
        return d;
    }

    public int getN() {
        return n;
    }

    public int getE() {
        return e;
    }

    private ArrayList<Integer> r,x,y;
    private int gcd(int a,int b){


        if(a==0||b==0){
            return a+b;
        }else{
            int max=a>b?a:b;
            int min=a<b?a:b;
            int q=max/min;
            int i=r.size();
            r.add(r.get(i-2)-q*r.get(i-1));
            x.add(x.get(i-2)-q*x.get(i-1));
            y.add(y.get(i-2)-q*y.get(i-1));
            return gcd(max%min,min);
        }
    }

    void setKeys(){
        int p=getRandomPrime(),q=getRandomPrime();
        n=p*q;

        int fn=(p-1)*(q-1);

        e=fn/2;
        for(;e<fn;e++){
            r=new ArrayList<>();
            x=new ArrayList<>();
            y=new ArrayList<>();
            r.add(e);
            r.add(fn);
            x.add(1);
            x.add(0);
            y.add(0);
            y.add(1);
            if(gcd(e,fn)==1&&y!=null&&y.size()>0){
                break;
            }
        }

        d=x.get(x.size()-1)>0?
                x.get(x.size()-1):
                y.get(y.size()-1);
    }
}
