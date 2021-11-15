package com.company;

import java.util.Scanner;

public class Player {

    private final String name;
    private boolean hasBooster;
    private boolean hasStorm;
    int rollBeforeStorm;
    int roll;

    //Initialize the fields
    public Player(String name){
        this.name = name;
    }

    /**
     * Method: earnBooster
     * Use: Sets hasBooster to true
     */
    public void earnBooster(){
        this.hasBooster = true;
        System.out.println(name + " earned powerup");
    }


    /**
     * Method: earnStorm
     * Use: Sets storm to true
     */
    public void earnStorm(){
        this.hasStorm = true;
        System.out.println(name + " encountered storm");
        rollBeforeStorm = ((int) Math.floor(roll/2));
    }

    /**
     * Method: takeTurn
     * Use: This method plays out the player's turn.
     * @return Returns the number of spaces to move on the board.
     *
     * Used in SnakesLadders class
     */
    public int takeTurn(){

        //Initialize scanner
        Scanner scan = new Scanner(System.in);
        //Prompt user for roll
        System.out.print(name+"'s turn to roll dice: <Press enter to roll dice> \n");

        //Dummy input taker for interactivity
        //Remove line for the game to play with entering to roll
        String input = scan.nextLine();

        if(input.equalsIgnoreCase("E")){
            System.out.println("Game exited. Thank you for playing!");
            System. exit(0);
        }

        //Get the roll
        if (hasBooster){

            BoosterSquare boosterSquare = new BoosterSquare();
            roll = boosterSquare.getRoll(0);


        } else if (hasStorm){
            //If the player has storm, then halve dice value

            StormSquare stormSquare = new StormSquare();
            roll = stormSquare.getRoll(rollBeforeStorm);
            this.hasStorm = false;

        }else {

            //Otherwise, roll normally
          NormalSquares normalSquares = new NormalSquares();
          roll = normalSquares.getRoll(0);

        }


        hasBooster = false;
        System.out.println(name + " rolled " + roll + ".");
        return roll;
    }

   //Gives name of player for board
    public String toString(){
        return name;
    }

}

