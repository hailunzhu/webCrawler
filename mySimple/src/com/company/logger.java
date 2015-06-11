package com.company;

/**
 * Created by hailunzhu on 6/10/15.
 */
public class logger {

    public void step(String s){
        System.out.println("STEP:            "+ s );
        System.out.println("============================================================");
    }

    public void info(String s){
        System.out.println("INFO: "+ s );
     //   System.out.println("============================================================");
    }

    public void print(String s){
        System.out.println(s);
    }


    public void error(String s){
        System.out.println("ERROR:           "+ s );
        System.out.println("============================================================");
    }


}
