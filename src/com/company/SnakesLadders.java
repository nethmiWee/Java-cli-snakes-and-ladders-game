package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The snakes and ladders game implements the classic workings of the board game
 * on a digital screen with special squares and ladders and snakes
 *
 * @author  Nethmi Weeraman
 * @version 2.0
 * @since   2021-06-07
 */

public class SnakesLadders {

    public static void main(String[] args){
        //Welcome message
        System.out.println("Welcome to Snakes & Ladders");
        System.out.println("Enter E to exit at any turn" );
        //Make scanner.
        Scanner scan = new Scanner (System.in);

        //Number of players prompt
        int numPlayers = 0;
        String input = "";
        while (numPlayers <= 0 || numPlayers > 6 ){
            System.out.print("Please enter the number of player (1-6): " );
            input = scan.nextLine();
            if(!input.equalsIgnoreCase("E")){
                numPlayers = Integer.parseInt(input);

            }else{
                System.out.println("Game exited. Thank you for playing!");
                System. exit(0);
            }
        }

        //Player initialization
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++){
            Player player = new Player("P" + i); //Name input to player class
            players.add(player);
        }

        //Board initialization
        Board board = new Board(players);

        //Loop until a player reaches the final spot.
        //Players take turns to roll the die and move on the board
        boolean gameEnd = false;
        int playerIndex = 0;
        while (!gameEnd){
            //Player takes turn
            Player currentPlayer = players.get(playerIndex);
            int roll = currentPlayer.takeTurn();

            //Update the board
            gameEnd = board.movePlayer(currentPlayer, roll);


            //Print the board
            System.out.println(board);
            System.out.println("---------------------------------------\n");

            //If game is won, print player won message.
            if (gameEnd){
                System.out.println(currentPlayer + " wins");
            }

            //Set up for next player
            playerIndex++;
            if (playerIndex == numPlayers){
                playerIndex = 0;
            }

        }
    }
}