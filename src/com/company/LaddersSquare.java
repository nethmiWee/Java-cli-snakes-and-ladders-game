package com.company;

import java.util.ArrayList;
import java.util.Map;

public class LaddersSquare extends NormalSquares{

    public int getRoll(int position, int[][] ladders, Map<Player, Integer> playerPositions, Player player, ArrayList<Board.stormedOrStickied> arr) {
        int NUM_LADDERS = 9;
        for (int i = 0; i < NUM_LADDERS; i++) {
        for (Board.stormedOrStickied isSticky : arr) {
            if (!isSticky.stickyUsed && isSticky.player == player) {
                if (ladders[i][0] == position) {
                    //If the new position is the starting point for a ladder
                    //Move the player to the end position for the ladder
                    position = ladders[i][1];
                    playerPositions.put(player, position);

                    System.out.println("Yay! " + player + " takes ladder from " + ladders[i][0] + " to " + ladders[i][1] + "\n");

                    for (Board.stormedOrStickied element : arr) {
                        element.nextTurnSticky++;
                        element.nextTurnStormy++;
                    }

                    return 0;
                }
            }
        }
    }

        return 1;
    }
}