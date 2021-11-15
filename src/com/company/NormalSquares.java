package com.company;

import java.util.ArrayList;
import java.util.Map;

public class NormalSquares implements RollVariances{

    static Die die = new Die();
    int roll = 0;

    //Get the roll
    public int getRoll(int roll){

        roll = die.rollD6();
        return roll;
    }


    public int getRoll(int position, int[][] ladderOrSnakes, Map<Player, Integer> playerPositions, Player player, ArrayList<Board.stormedOrStickied> arr) {
        System.out.println("Error in ladder/snake implementation.");
        return 1;
    }

    public void getRoll(Player player, ArrayList<Board.stormedOrStickied> arr){
        System.out.println("Error in sticky implementation.");
    }

}
