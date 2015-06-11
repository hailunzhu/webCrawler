package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Spider s = new Spider();

        try {
            s.start(args[0],args[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
