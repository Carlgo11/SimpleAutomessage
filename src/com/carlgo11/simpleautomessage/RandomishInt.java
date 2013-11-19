package com.carlgo11.simpleautomessage;

import java.util.Random;

public class RandomishInt {

    static int a = 0;
    static int qwe = 0;
    static boolean use = true;

    public static void onInt(int asd)
    {
        use = false;
        qwe = a;
        if (asd < 3 || qwe == 0) {
            Random n = new Random();
            int num = 0;
            for (int count = 1; count <= 2; count++) {
                num = 1 + n.nextInt(asd);
            }
            sendtoA(num);
        } else {
            Random n = new Random();
            int num = 0;
            for (int count = 1; count <= 2; count++) {
                num = 1 + n.nextInt(asd);
            }
            sendtoA(num);
            if (qwe == a) {
                RandomishInt.onInt(asd);
            }
        }
        use = true;
    }

    public static void sendtoA(int b)
    { // For future debug purposes. 
        a = b;
    }

}
