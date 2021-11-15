package com.company;

import java.util.ArrayList;

public class StickySquare extends NormalSquares{
    public void getRoll(Player player, ArrayList<Board.stormedOrStickied> arr){

        //If stickyUsed is false, it is turned true
        for(Board.stormedOrStickied element : arr ){
            if(!element.stickyUsed && element.player == player){
                System.out.println(player + " encountered sticky");
                element.stickyUsed = true;
                element.nextTurnSticky = 0;
            }
        }

    }

}
