package com.carlgo11.simpleautomessage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class moveMessages {

    public static void moveOldMessages(Main Main)
    {
        File file = Main.getMessageFile();
        if (!file.exists()) {
            ArrayList<String> mm = new ArrayList<String>();
            int a = 1;
            while (true) {
                if (Main.getConfig().contains("msg" + a)) {
                    mm.add(Main.getConfig().getString("msg" + a));

                    a++;
                } else {
                    break;
                }
            }
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                for (int i = 0; i < mm.size(); i++) {
                    bw.write(mm.get(i));
                    bw.newLine();
                }
                bw.close();
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
