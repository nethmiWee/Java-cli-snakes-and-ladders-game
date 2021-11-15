package com.company;

import java.util.ArrayList;
import java.util.Map;

public class SnakeSquares extends NormalSquares{

    public int getRoll(int position, int[][] snakes, Map<Player, Integer> playerPositions, Player player, ArrayList<Board.stormedOrStickied> arr) {

        int NUM_SNAKES = 6;
        for (int i = 0; i < NUM_SNAKES; i++) {
            for (Board.stormedOrStickied isSticky : arr) {
                if (!isSticky.stickyUsed && isSticky.player == player) {
                    if (snakes[i][0] == position) {
                        //If starting point for a snake
                        //Move the player to the end position of snake
                        position = snakes[i][1];
                        playerPositions.put(player, position);

                        System.out.println("Uh oh. " + player + " takes snake from " + snakes[i][0] + " to " + snakes[i][1] + "\n");

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
