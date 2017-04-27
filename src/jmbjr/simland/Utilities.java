package jmbjr.simland;

import java.util.Random;

/**
 * @author John Boyle, boylejm@gmail.com, https://github.com/jmbjr
 * SimFarm entry point
 */
public class Utilities {

    /**
     * @param args
     * standard main method
     */
    public static void main(final String[] args) {

    }
    
    public static boolean coinFlip() {
    	Random rand = new Random();
    	int randInt = rand.nextInt(2);
    	
    	boolean flip = ( randInt == 1 ? true:false);
    	return flip;
    }
}
