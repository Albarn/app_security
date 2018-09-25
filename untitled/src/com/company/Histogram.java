package com.company;

//histogram cipher method
public class Histogram {

    public static byte[] Encode(byte[] key, byte[] message){
        int size=message.length;
        byte[] encoded=new byte[size];

        //get histogram pattern from key and message size
        byte[][] h= GetHistogram(key, size);

        //fill histogram with message bytes
        //*following the rows
        for(int i=0,k=0;i<h.length&&k<size;i++){
            for(int j=0;j<h[i].length&&k<size;j++,k++){
                h[i][j]=message[k];
            }
        }

        //find max row size
        //its highest(longest) point in histogram
        int columnNum=0;
        for(int i=0;i<h.length;i++){
            columnNum = columnNum > h[i].length ?
                    columnNum :
                    h[i].length;
        }

        //fill result following the columns
        int k=0;
        for(int j=0;j<columnNum;j++){
            for(int i=0;i<h.length;i++){
                if (h[i].length > j) {
                    encoded[k] = h[i][j];
                    k++;
                }
            }
        }
        return encoded;
    }

    public static byte[] Decode(byte[] key, byte[] message){
        int size=message.length;
        byte[] decoded=new byte[size];

        //get pattern
        byte[][] h= GetHistogram(key, size);

        //histogram width
        int columnNum=0;
        for(int i=0;i<h.length;i++){
            columnNum = columnNum > h[i].length ?
                    columnNum :
                    h[i].length;
        }

        //fill histogram following the columns
        for(int j=0,k=0;j<columnNum;j++){
            for(int i=0;i<h.length;i++){
                if (h[i].length > j) {
                    h[i][j]=message[k];
                    k++;
                }
            }
        }

        //fill message following the rows
        for(int i=0,k=0;i<h.length&&k<size;i++){
            for(int j=0;j<h[i].length&&k<size;j++,k++){
                decoded[k] = h[i][j];
            }
        }

        return decoded;
    }

    //get histogram pattern with given key and size
    private static byte[][] GetHistogram(byte[] key, int size) {

        //convert key bytes to string format
        String[] params=new String(key).split("\r\n");

        //total size of histogram
        int totalSize=0;

        //sizes of rows in histogram
        int[] rows=new int[params.length];
        for(int i=0;i<rows.length;i++){

            //try to parse string number to int
            try{
                rows[i]=Integer.parseInt(params[i]);
            }catch (Exception e){
                rows[i]=0;
            }

            //add row size to total number
            totalSize+=rows[i];

            //if total number is higher than size of message
            //then we subtract it from row size & total number
            if(totalSize>size){
                rows[i]-=totalSize-size;
                totalSize=size;
            }
        }

        //if volume of histogram is not enough
        //we increment rows until we get equal value
        if(totalSize<size){
            for(int i = 0; totalSize<size; i++,i%=rows.length){
                rows[i]++;
                totalSize++;
            }
        }

        //finally, construct histogram with given row sizes
        byte[][] histogram=new byte[rows.length][];
        for(int i=0;i<rows.length;i++){
            histogram[i]=new byte[rows[i]];
        }

        return histogram;
    }
}
