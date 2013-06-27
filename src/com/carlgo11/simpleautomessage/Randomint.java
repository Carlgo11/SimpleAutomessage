package com.carlgo11.simpleautomessage;
import java.util.Random;

public class Randomint {
	 Main plugin;
     public Randomint(Main plug) {
             super();
             this.plugin = plug;
     }
     
     public static void onRandom(String[] args){
    	 Random d = new Random();
 		int a;
 		for(int counter=1; counter<=10;counter++){
 			a = 1+d.nextInt(10);
 			Main.random = a;
     }
     }

}
