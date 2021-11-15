package com.company;

public class StormSquare extends NormalSquares {

    public int getRoll(int previousRoll){
        //If the player has storm, then halve dice value
        roll = -(previousRoll);
        System.out.print("In storm. Previous roll halved and rolled backwards\n");
        return roll;
    }

}
