package com.carlgo11.simpleautomessage;

import java.util.Random;

public class RandomishInt {

    static int a = 0;

    public static void onInt(int asd)
    {
        Random n = new Random();
        int num;
        for (int count = 1; count <= 2; count++) {
            num = 1 + n.nextInt(asd);
            a = num;
        }
    }

}
