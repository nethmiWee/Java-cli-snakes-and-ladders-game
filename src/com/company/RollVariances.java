package com.company;

import java.util.ArrayList;
import java.util.Map;

public interface RollVariances{
    int getRoll(int position, int[][] ladderOrSnakes, Map<Player, Integer> playerPositions, Player player, ArrayList<Board.stormedOrStickied> arr);


    void getRoll(Player player, ArrayList<Board.stormedOrStickied> arr);

    int getRoll(int roll);

}

