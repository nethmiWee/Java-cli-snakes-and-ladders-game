package com.company;

public class BoosterSquare extends NormalSquares {

    //Get the roll
    public int getRoll(int roll){
        //If the player has the powerup, increment by 1
        roll = die.rollD6() + 1;
        System.out.print("Using powerup; Rolled " + (roll -1) + " incremented by 1\n");
        return roll;
    }

}
