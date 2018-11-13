package com.company.rsa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

//keys generator for rsa method
class RsaGenerator {

    RsaGenerator(){
        //initialize primes
        setPrimes();
        n=e=d=-1;
    }

    private boolean[] isPrime=new boolean[1<<16];
    private void setPrimes(){

        //set all values as true, than mark all
        //not prime numbers
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

        //get random prime according to isPrime array
        Random f=new Random(LocalDateTime.now().getNano());
        while (true){
            int ans=f.nextInt(isPrime.length-1);
            if(isPrime[ans]){
                return ans;
            }
        }
    }

    //size of alphabet, public key, private key
    private int n,e,d;

    int getD() {
        return d;
    }

    int getN() {
        return n;
    }

    int getE() {
        return e;
    }

    //stacks for extended gcd method
    private ArrayList<Integer> r,x,y;
    private int xGcd(){

        //number of iteration
        int i=r.size();

        //numbers to divide
        int a=r.get(i-2);
        int b=r.get(i-1);
        if(b==0){

            //gcd is the rest before zero
            return a;
        }else{

            //calculate next number, and output params
            int q=a/b;
            r.add(a-q*b);
            x.add(x.get(i-2)-q*x.get(i-1));
            y.add(y.get(i-2)-q*y.get(i-1));
            return xGcd();
        }
    }

    void setKeys(){

        //find two big prime numbers
        int p=getRandomPrime(),q=getRandomPrime();

        //then, multiply it and find euler function of it
        n=p*q;
        int fn=(p-1)*(q-1);

        //find solution keys via extended gcd
        d=fn/2;
        for(;d<fn;d++){
            r=new ArrayList<>();
            x=new ArrayList<>();
            y=new ArrayList<>();
            r.add(fn);
            r.add(d);
            x.add(1);
            x.add(0);
            y.add(0);
            y.add(1);
            if(xGcd()==1&&y.get(y.size()-2)>0){
                break;
            }
        }
        e=y.get(y.size()-2);

        //print report
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
