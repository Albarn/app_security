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
    private int xGcd(){
        int i=r.size();
        int a=r.get(i-2);
        int b=r.get(i-1);
        if(b==0){
            return a;
        }else{
            int q=a/b;
            r.add(a-q*b);
            x.add(x.get(i-2)-q*x.get(i-1));
            y.add(y.get(i-2)-q*y.get(i-1));
            return xGcd();
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
            if(xGcd()==1){
                break;
            }
        }

        d=x.get(x.size()-2)>0?
                x.get(x.size()-2):
                y.get(y.size()-2);

        System.out.println("p:"+p);
        System.out.println("q:"+q);
        System.out.println("n:"+n);
        System.out.println("e:"+e);
        System.out.println("fn:"+fn);
        System.out.println("d:"+d);
        System.out.println("x:"+x.get(x.size()-2));
        System.out.println("y:"+y.get(y.size()-2));
    }
}
