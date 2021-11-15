package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    //Inner class for recording stickies and storm-------------------------------------------------------------------------
   //Keeps track of each player and their encounters with stickies/storms

    static class stormedOrStickied{
        Player player;
        int nextTurnSticky = 0;
        int nextTurnStormy = 0;
        boolean stickyUsed = false;
        boolean stormUsed = false;


        stormedOrStickied(Player thisPlayer){
            player = thisPlayer;
        }


    }

    ArrayList<stormedOrStickied> arr = new ArrayList<>();

//Specifying board size, and number of snakes, ladders, boosters, sticky disadvantages and storms--------------------------------

    private final int ROWS = 15;
    private final int COLS = 10;
    private final int NUM_SNAKES = 6;
    private final int NUM_LADDERS = 9;
    private final int NUM_BOOSTERS = 5;
    private final int NUM_STICKIES = 5;
    private final int NUM_STORM = 5;



    //Board variables--------------------------------------------------------------------------------------------------------------
    private final int[][] gameBoard;
    private int[][] snakes;
    private int[][] ladders;
    private int[] boosters;
    private int[] stickies;
    private int[] storms;

    //Variable to start game
    private boolean gameStarted = false;

//Map of player positions-------------------------------------------------------------------------------------------------------

    Map<Player, Integer> playerPositions;

    //In key-value pair, Key = current player, Value = player position
    //This is an interface for the hashmap below

    /**
     * Method: Board
     * Use: For setting players down, putting grid and setting special squares
     * @param players This parameter is an object of player class in a list
     *
     * This is instantiated in main class SnakesLadders to create board
     */

    public Board(List<Player> players){

        //Initialize the player positions with hashmap----------------------------------------------------------------------------
        this.playerPositions = new HashMap<>();


        for (Player player : players){
            this.playerPositions.put(player, 0);
            arr.add(new stormedOrStickied(player)); //Name input to player class
            //All players start at position 0
        }

        //Setting up the row x column board with a grid----------------------------------------------------------------------------

        int num = 1; //This num variable the board number starting point, it is incremented and shown in grid

        gameBoard = new int[ROWS][COLS];
        for (int row = 0; row < ROWS; row++){
            for (int col = 0; col < COLS; col++){
                gameBoard[row][col]= num;
                num++;
            }
        }

        //Setting up the snakes, ladders, boosters, stickies and storms--------------------------------------------------------------
        setSnakes();
        setLadders();
        setBoosters();
        setStickies();
        setStorms();
    }



    /**
     * Method: movePlayer
     * Use: Moves the given player based on the value provided.
     * @param player  The player to move.
     * @param value  The spaces to move.
     * @return Returns True if the player reaches the final (150) spot, else false.

     * This is used in the main class SnakesLadders to move players if players don't reach 150
     */


    public boolean movePlayer(Player player, int value) {

        //For calculating the new position----------------------------------------------------------------------------------------
        int position = playerPositions.get(player);
        position += value;

        //If player gets only 1 or 6 start game by making gameStarted equal true
        if (!gameStarted) {
            if (position == 1 || position == 6) {
                System.out.println("Game started! Roll on ~");
                gameStarted = true;
            } else {
                System.out.println(player + " must roll 1 or 6 to start game"); // Start
                System.out.println("Aw, Game didn't started. Try again till you get 1 or 6!\n");
            }
        }

        //Game started with 1 or 6 roll
        if (gameStarted) {
            if (position == 150) {
                //If the new position is 150, the player wins!
                playerPositions.put(player, 150);
                return true;
            } else if (position > 150) {
                //If the new position is over 150, the player is reset to 149 and must get 1
                playerPositions.put(player, 149);
                System.out.println("Almost there, just get a little less! " + player + " has gone over board end and is placed at 149th square\n");

                //Turns since a storm or sticky is incremented
                for(stormedOrStickied element : arr ){
                    element.nextTurnSticky++;
                    element.nextTurnStormy++;
                }

                return false;

            } else {

                //If the new position is less than 150


                //Seeing if the new position is start point for a snake-----------------------------------------------------------------
                SnakeSquares snakeSquares = new SnakeSquares();
               if(snakeSquares.getRoll(position, snakes, playerPositions, player, arr) == 0){
                   return false;
               }


                //Seeing if the new position is start point for a ladder-------------------------------------------------------------
                LaddersSquare laddersSquare = new LaddersSquare();
                if(laddersSquare.getRoll(position, ladders, playerPositions, player, arr) == 0){
                    return false;
                }


                //Check if the new position contains a booster or a sticky or storm and turn on the variable for player class
                //in required instances (Booster or Storm). Stickies are handled in board.
                //All special squares of boosters, stickies and storm numbers are 5

                for (int i = 0; i < NUM_BOOSTERS; i++) {
                    if (boosters[i] == position) {
                        //player is given booster
                        player.earnBooster();

                    } else if (stickies[i] == position) {

                        //player's variable isSticky is made true
                        StickySquare stickySquare = new StickySquare();
                        stickySquare.getRoll(player, arr);


                    } else if (storms[i] == position) {

                        //Iterate over and check if the storm is already in use and skips if it is
                        //This is for when a previous roll is 1 and the new roll back would be 0
                        //This helps the player not get stuck in storm forever in that case
                        for(stormedOrStickied element : arr ){
                            if(!element.stormUsed && element.player == player){
                                element.stormUsed = true;
                                element.nextTurnStormy = 0;

                                //player is given storm
                                player.earnStorm();
                            }
                        }
                    }
                }

                //Make stormUsed false
                for(stormedOrStickied element : arr ){
                    element.nextTurnSticky++;
                    element.nextTurnStormy++;

                    if(element.stormUsed && element.player == player && element.nextTurnStormy > playerPositions.size()+1){

                        element.stormUsed = false;
                    }

                }



                //If the player is not on a snake/ladder or on sticky, then ju st update
                //its position normally

                for(stormedOrStickied element : arr ) {
                    if (element.player == player) {
                        if (element.stickyUsed && element.nextTurnSticky == playerPositions.size() + 1) {
                            System.out.println(player + " skipped turn due to sticky\n");
                            element.stickyUsed = false;
                        } else {
                            playerPositions.put(player, position);
                            System.out.println(player + " landed on " + position + " square \n");

                        }
                    }
                }


               //Player is not at 150 so returns false

            }

        }
        return false;
    }



    /**
     * Method: setSnakes
     * Use: Sets the snakes for the board

     * This is used in the class Board to put snakes positions
     */
    private void setSnakes(){
        snakes = new int[NUM_SNAKES][2];

        snakes[0][0] = 37;
        snakes[0][1] = 25;

        snakes[1][0] = 74;
        snakes[1][1] = 31;

        snakes[2][0] = 77;
        snakes[2][1] = 60;

        snakes[3][0] = 108;
        snakes[3][1] = 95;

        snakes[4][0] = 123;
        snakes[4][1] = 118;

        snakes[5][0] = 146;
        snakes[5][1] = 124;

    }

    /**
     * Method: setLadders
     * Use: Sets the ladders for the board

     * This is used in the class Board to put ladders positions
     */
    private void setLadders(){
        ladders = new int[NUM_LADDERS][2];

        ladders[0][0] = 4;
        ladders[0][1] = 15;

        ladders[1][0] = 8;
        ladders[1][1] = 30;

        ladders[2][0] = 28;
        ladders[2][1] = 84;

        ladders[3][0] = 51;
        ladders[3][1] = 73;

        ladders[4][0] = 63;
        ladders[4][1] = 81;

        ladders[5][0] = 99;
        ladders[5][1] = 103;

        ladders[6][0] = 112;
        ladders[6][1] = 127;

        ladders[7][0] = 121;
        ladders[7][1] = 139;
    }

    /**
     * Method: setBoosters
     * Use: Sets the boosters for the board
     * This is used in the class Board to put boosters positions
     */
    private void setBoosters(){
        boosters = new int[NUM_BOOSTERS];

        boosters[0] = 9;
        boosters[1] = 15;
        boosters[2] = 47;
        boosters[3] = 87;
        boosters[4] = 101;
    }

    /**
     * Method: setStickies
     * Use: Sets the stickies for the board
     * This is used in the class Board to put stickies positions
     */
    private void setStickies(){
        stickies = new int[NUM_STICKIES];

        stickies[0] = 2;
        stickies[1] = 75;
        stickies[2] = 81;
        stickies[3] = 110;
        stickies[4] = 142;
    }

    /**
     * Method: setStorms
     * Use: Sets the storms for the board
     * This is used in the class Board to put storm positions
     */

    private void setStorms(){
        storms = new int[NUM_STORM];

        storms[0] = 44;
        storms[1] = 67;
        storms[2] = 89;
        storms[3] = 98;
        storms[4] = 133;
    }

    /**
     * Method: toString
     * Use: Outputs and prints the board into the terminal in an understandable form
     * @return  Readable graphical version of board is made
     */
    public String toString(){
        //Using StringBuilder for creating the string output of the board
        StringBuilder sb = new StringBuilder();
        boolean oddRow = true;
        boolean snakePosition = false;
        boolean ladderPosition = false;
        // The rows will be in reverse order: 1-10 at the bottom, and 141-150 at the top.

        //'Even' (1-10, 21-30, ..., 131-140)  are printed left to right.
        //'Odd' (11-20, 31-40, ..., 141-150)  are printed right to left.

        //If position is being used by one player, we print the player and not the number in position.

        for (int row = ROWS-1; row >= 0; row--){
            for (int col = 0; col < COLS; col++){

                if (oddRow){

                    //oddRow: row = 9, 7, 5, 3, 1
                    //Check if any of the players occupy the current location

                    String pl = "";
                    boolean occupied = false;
                    for (Player temp : playerPositions.keySet()){
                        if (playerPositions.get(temp) == gameBoard[row][COLS-1-col]){
                            //If a player occupies the current location, then set the flag
                            //and update player
                            occupied = true;
                            pl += temp + " ";
                        }
                    }


                    if (occupied){
                        //If at least one player occupies the location, then print those players
                        pl += "\t";
                        sb.append(pl);
                    } else {

                        //Else, print the position number or snake/ladder
                        for (int[] ladder : ladders) {
                            if (ladder[0] == gameBoard[row][COLS - 1 - col]) {
                                ladderPosition = true;
                                break;
                            }
                        }


                        for (int[] snake : snakes) {
                            if (snake[0] == gameBoard[row][COLS - 1 - col]) {
                                snakePosition = true;
                                break;
                            }
                        }

                        if(ladderPosition){
                            sb.append("[L] ");
                            ladderPosition = false;
                        }else if(snakePosition){
                            sb.append("[S] ");
                            snakePosition = false;
                        }else{
                            sb.append(gameBoard[row][COLS-1-col] + "\t");
                        }

                    }
                } else {
                    //evenRow: row = 8, 6, 4, 2, 0
                    //Check if any of the players occupy the current location
                    boolean occupied = false;
                    String pl = "";
                    for (Player temp : playerPositions.keySet()){
                        if (playerPositions.get(temp) == gameBoard[row][col]){
                            //If a player occupies the current location, then set the flag
                            //and update pl
                            occupied = true;
                            pl += (temp + " ");
                        }
                    }

                    if (occupied){
                        //If at least one player occupies the location, then print those players
                        pl += "\t";
                        sb.append(pl);
                    } else {

                        //Else, print the position number or snake/ladder

                        for (int[] ladder : ladders) {
                            if (ladder[0] == gameBoard[row][col]) {
                                ladderPosition = true;
                                break;
                            }
                        }


                        for (int[] snake : snakes) {
                            if (snake[0] == gameBoard[row][col]) {
                                snakePosition = true;
                                break;
                            }
                        }

                        if(ladderPosition){
                            sb.append("[L] ");
                            ladderPosition = false;
                        }else if(snakePosition){
                            sb.append("[S] ");
                            snakePosition = false;
                        }else{
                            sb.append(gameBoard[row][col] + "\t");
                        }

                    }
                }
            }

            //Switch oddRow flag and print new line
            oddRow = !oddRow;
            sb.append("\n");
        }
        sb.append("\n");

        return sb.toString();
    }

}