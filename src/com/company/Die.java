package com.company;

import java.util.Random;

public class Die {

    private final Random random;

    public Die(){
        random = new Random();
    }


    /**
     * Method: rollD6
     * Use: Rolls a D6 and returns the value.
     * @return random value for die role
     */
    public int rollD6(){
        return random.nextInt(6)+1;
    }
}