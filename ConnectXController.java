package cpsc2150.extendedConnectX;

/**
 * The controller class will handle communication between our View and our Model (IGameBoard)
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your IGameBoard interface
 * and both of the IGameBoard implementations from Project 4
 * If your code was correct you will not need to make any changes to your IGameBoard implementation class
 */
public class ConnectXController {

    //our current game that is being played
    private IGameBoard curGame;

    //The screen that provides our view
    private ConnectXView screen;

    //our play tokens are hard coded. We could make a screen to get those from the user, but
    private final char[] tokens = {'X', 'O', 'A', 'M', 'E', 'Z', 'I', 'P', 'S', 'V'};

    //static integer to keep track of whose turn it is
    private static int turn = 0;

    //newGame determines if we need to call new Game
    private static boolean newGame = false;

    private int numPlayers;
    public static final int MAX_PLAYERS = 10;

    /**
     * @param model the board implementation
     * @param view  the screen that is shown
     * @post the controller will respond to actions on the view using the model.
     */
    ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        numPlayers = np;
    }

    /**
     * @param col the column of the activated button
     * @post will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button
     */
    public void processButtonClick(int col)
    {
        //if the last game has resulted in a tie, this if statement will start a new game upon a button click
        if(newGame){
            turn = 0;
            newGame = false;
            newGame();
            return;
        }

        //determine whose turn it is and display that
        char player = tokens[turn];
        int temp = turn+1;
        if(temp == numPlayers) temp = 0;
        screen.setMessage("It is " + tokens[temp] + "'s turn. ");

        //if col is free, place the token and update the screen
        if (curGame.checkIfFree(col)) {
            curGame.placeToken(player, col);

            //for loop to determine what row the placement is in
            int i;
            for (i = curGame.getNumRows()-1; i >= 0; i--) {
                BoardPosition pos = new BoardPosition(i, col);
                if (curGame.whatsAtPos(pos) == ' ') {
                    i++;
                    break;
                } else if (i == 0) {
                    break;
                }
            }

            //set correct row number and place the marker
            i = curGame.getNumRows() - (i + 1);
            screen.setMarker(i, col, player);
        }
        //else, the column is full
        else {
            screen.setMessage("Column is full. ");
            return;
        }

        //check if the placement results in a win
        if(curGame.checkForWin(col)){
            screen.setMessage("Player " + player + " Won! Click any button to start a new game. ");
            newGame = true;
        }

        //check if the placement results in a tie
        if(curGame.checkTie()){
            screen.setMessage("It's a tie! Click any button to start a new game. ");
            newGame = true;
        }

        //increment turn to determine whose turn it is next
        turn++;
        if(turn == numPlayers) turn = 0;
    }

    /**
     * This method will start a new game by returning to the setup screen and controller
     */
    private void newGame() {
        //close the current screen
        screen.dispose();
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}