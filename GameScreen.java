//package cpsc2150.extendedConnectX;
import java.util.*;

public class GameScreen {

    //create scanner object to take input
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        //boolean used to know when to stop the game
        boolean playGame = true;
        //boolean to know if we need to create a new board
        boolean playAgain = true;

        //Initialize the game board
        IGameBoard board = new GameBoard(0, 0, 0);
        //array list to hold the players specific by the user
        ArrayList<Character> players = new ArrayList<Character>();

        //this loop will iterate until the user doesn't want to play anymore
        while(playGame){

            //if we need to create a new GameBoard
            if(playAgain){
                players = getPlayers();
                int row = howManyRows();
                int col = howManyCols();
                int win = howManyToWin(row, col);

                //determine which IGameBoard implementation
                boolean ask = true;
                while(ask){
                    System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
                    String s = scanner.nextLine();
                    s = s.toUpperCase();
                    if (s.equals("F")) {
                        board = new GameBoard(row, col, win);
                        ask = false;
                    }
                    else if(s.equals("M")) {
                        board = new GameBoardMem(row, col, win);
                        ask = false;
                    }
                    else System.out.println("Please enter F or M");
                }
            }//end newGame statement

            playAgain = false;

            //this loop is used to traverse through the array list
            //each player will place their token
            for(int i = 0; i < players.size(); i++) {

                char p = players.get(i);
                System.out.println(board.toString());
                int c = getPlayersColumn(board, p);
                board.placeToken(p, c);

                //check if this placement resulted in a win
                if (board.checkForWin(c)) {
                    System.out.println(board.toString());
                    System.out.println("Player " + p + " Won!");

                    //user wants to play again
                    if(askToPlayAgain()) {
                        playAgain = true;
                        break;
                    }
                    //user does not want to play again
                    else{
                        playGame = false;
                        break;
                    }
                }

                //check if this placement resulted in a tie
                if(board.checkTie()){
                    System.out.println(board.toString());
                    System.out.println("It's a tie!");

                    //user wants to play again
                    if(askToPlayAgain()){
                        playAgain = true;
                        break;
                    }
                    //user does not want to play again
                    else{
                        playGame = false;
                        break;
                    }
                }
            }//end for

        }//end playGame loop

        scanner.close();
    }//end main

    /**
     * @return an ArrayList of character containing all the players specified by the user
     * @post getPlayers = a list containing all of the players
     *       (there will be NO duplicates, all are uppercase)
     *       AND the size of the list, #SIZE will be 2<=SIZE<=10
     */
     private static ArrayList<Character> getPlayers()
     {
         //variables for min and max number of players
         int min = 2;
         int max = 10;

         //array list holds the players
         ArrayList<Character> list = new ArrayList<Character>();

         int num = 0;
         //while loop to get number of players
         while(num < min || num > max) {
             System.out.println("How many players?");
             String s = scanner.nextLine();
             num = Integer.parseInt(s);
             if(num < min)
                 System.out.println("Must be at least " + min + " players");
             else if(num > max)
                 System.out.println("Must be " + max + " players or fewer");
         }

         //loop to get each character
         for(int i = 1; i <= num; i++){
             System.out.println("Enter the character to represent player " + i);
             String s = scanner.nextLine();
             s = s.toUpperCase();
             char c = s.charAt(0);
             //if the character has not already been used:
             if(!list.contains(c)) {
                 list.add(c);
             }
             //ask for the character again by decrementing i
             else{
                 System.out.println(c + " is already taken as a player token!");
                 i--;
             }
         }

         return list;
     }//end getPlayers

    /**
     * @return how many rows the user specified
     * @post 3<=howManyRows<=100
     */
     private static int howManyRows()
     {
         //min and max row
         int min = 3;
         int max = 100;
         int row = 0;

         while(row < min || row > max){
             System.out.println("How many rows should be on the board?");
             String s = scanner.nextLine();
             row = Integer.parseInt(s);
             if(row < min)
                 System.out.println("Must be at least " + min + " row");
             else if(row > max)
                 System.out.println("Rows cannot be greater than " + max);
         }

         return row;
     }//end howManyRows

    /**
     * @return the number of columns the user specified
     * @post 3<=howManyCols<=100
     */
     private static int howManyCols()
     {
         //min and max column
         int min = 3;
         int max = 100;
         int col = 0;

         while(col < min || col > max){
             System.out.println("How many columns should be on the board?");
             String s = scanner.nextLine();
             col = Integer.parseInt(s);
             if(col < min)
                 System.out.println("Must be at least " + min + " column");
             else if(col > max)
                 System.out.println("Columns cannot be greater than " + max);
         }

         return col;
     }//end howManyCols

    /**
     * @param row is the number of rows
     * @param col is the number of columns
     * @return how many tokens in a row it takes to win
     * @pre 3<=row<=100 AND 3<=col<=100
     * @post 3<=howManytoWin<=25 AND howManytoWin<=row AND howManyToWin<=col
     */
    private static int howManyToWin(int row, int col)
    {
        //min and max num to win
        int min = 3;
        int max = 25;
        int win = 0;

        while(win < min || win > max || win > row || win > col){
            System.out.println("How many in a row to win?");
            String s = scanner.nextLine();
            win = Integer.parseInt(s);
            if(win < min)
                System.out.println("Must be at least " + min + " to win");
            else if(win > max)
                System.out.println("Number to win cannot be greater than " + max);
            else if(win > row)
                System.out.println("Number to win cannot be greater than the number of rows: " + row);
            else if(win > col)
                System.out.println("Number to win cannot be greater than the number of columns: " + col);
        }

        return win;
    }//end howManyToWin

    /**
     * @param board is the current state of the gameBoard passed from main
     * @param p is the character of the player
     * @return the integer value for column that the user enters
     * @pre the board has been initialized, the board is not full
     * @post getPlayersColumn = the col that the player chose, 0<=playerX<=getNumColumns()-1
     */
    private static int getPlayersColumn(IGameBoard board, char p)
    {
        boolean getCol = true;
        int col = 0;
        int maxCol = board.getNumColumns()-1;

        //ask player X for position and check if it is valid
        while(getCol){
            System.out.println("Player " + p + ", what column do you want to place your marker in?");
            String answer = scanner.nextLine();
            col = Integer.parseInt(answer);

            if(col < 0)
                System.out.println("Column cannot be less than 0");
            else if(col > maxCol)
                System.out.println("Column cannot be greater than " + maxCol);
            else if(col >= 0 && col <= maxCol) {
                if(board.checkIfFree(col)) {
                    getCol = false;
                    break;
                }
                else System.out.println("Column is full");

            }//end else if

        }//end while
        return col;
    }//end getColumn

    /**
     * @return true if the user wants to play another game, false otherwise
     * @post askToPlayAgain = true iff the user wants to play again
     */
    private static boolean askToPlayAgain()
    {
        boolean askAgain = true;

        while(askAgain) {
            System.out.println("Would you like to play again? Y/N");
            String answer = scanner.nextLine();
            answer = answer.toUpperCase();

            if(answer.equals("N")) return false;
            else if(answer.equals("Y")) return true;
            else System.out.println("Invalid character!");
        }

        return false;
    }//end askToPlayAgain

}//end GameScreen class
