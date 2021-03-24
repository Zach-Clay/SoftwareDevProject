//package cpsc2150.extendedConnectX;

/**
 * GameBoard is abstractly a 2D grid of characters
 *
 * Initialization Ensures:
 *      GameBoard contains only null characters i.e. is empty
 * Defines:
 *          max_num_rows: 100
 *          max_num_cols: 100
 *          max_num_toWin: 25
 * Constraints:
 *      Only Characters can fill the game board
 */
public interface IGameBoard {

    /**
     * @return the number of rows in self, self = #self
     */
    public int getNumRows();

    /**
     * @return the number of columns in self, self = #self
     */
    public int getNumColumns();

    /**
     * @return the number of tokens in a row needed to win the game, self = #self
     */
    public int getNumToWin();

    /**
     * @param c is the column to check if free
     * @return true if the column is able to accept another token, false otherwise
     * @pre 0<=c<=getNumColumns()
     * @post true iff column does not contain #getNumRows() characters, self = #self
     */
    public default boolean checkIfFree(int c)
    {
        for(int i = getNumRows()-1; i >= 0; i--) {
            BoardPosition pos = new BoardPosition(i, c);
            if(whatsAtPos(pos) == ' ') return true;
        }
        return false;
    }//end checkIfFree

    /**
     * @param c is the column of the last token placed
     * @return true if it results in a win, false otherwise
     * @pre 0<=c<=getNumColumns()
     * @post true iff #getNumToWin() equivalent tokens are placed horizontally, vertically, or diagonally, self = #self
     */
    public default boolean checkForWin(int c)
    {
        for(int i = getNumRows()-1; i >= 0; i--){
            BoardPosition temp = new BoardPosition(i, c);

            if(whatsAtPos(temp) == ' '){
                BoardPosition pos = new BoardPosition(i+1, c);
                char p = whatsAtPos(pos);
                if(checkHorizWin(pos, p)) return true;
                else if(checkVertWin(pos, p)) return true;
                else if(checkDiagWin(pos, p)) return true;
                else break;
            }
            else if(i == 0){
                BoardPosition pos = new BoardPosition(i, c);
                char p = whatsAtPos(pos);
                if(checkHorizWin(pos, p)) return true;
                else if(checkVertWin(pos, p)) return true;
                else if(checkDiagWin(pos, p)) return true;
                else break;
            }//end if else

        }//end for

        return false;
    }//end checkForWin

    /**
     * @return true if the GameBoard results in a tie game, return false otherwise
     * @pre checkForWin = false
     * @post true iff all Characters in GameBoard do not equal a blank character, self = #self
     */
    public default boolean checkTie()
    {
        for(int i = 0; i < getNumRows(); i++)
            for(int j = 0; j < getNumColumns(); j++){
                BoardPosition pos = new BoardPosition(i, j);
                if(whatsAtPos(pos) == ' ') return false;
            }
        return true;
    }//end checkTie

    /**
     * @param pos is the current board position
     * @param p is the specific token to check a win for
     * @return true if last token placed results in getNumToWin() in a row horizontally
     * @pre
     * @post true iff char p has #getNumToWin() in a row horizontally, self = #self
     */
    public default boolean checkHorizWin(BoardPosition pos, char p)
    {
        int r = pos.getRow();
        int c = pos.getColumn()-1;
        int count = 1;

        //searches to the left of the last token
        while(c > -1){
            BoardPosition temp = new BoardPosition(r, c);
            if(isPlayerAtPos(temp, p)) count++;
            else if(count == getNumToWin()) return true;
            else break;
            if(count == getNumToWin()) return true;
            c--;
        }

        c = pos.getColumn()+1;

        //searches to the right of the last token
        while(c < getNumColumns()){
            BoardPosition temp = new BoardPosition(r, c);
            if(isPlayerAtPos(temp, p)) count++;
            else break;
            if(count == getNumToWin()) return true;
            c++;
        }

        return false;
    }//end checkHorizWin

    /**
     * @param pos is the current board position
     * @param p is the specific token to check for a win
     * @return true if the last token placed results in getNumToWin in a row vertically
     * @pre
     * @post true iff char p has #getNumToWin in a row vertically, self = #self
     */
    public default boolean checkVertWin(BoardPosition pos, char p)
    {
        int r = pos.getRow()+1;
        int c = pos.getColumn();
        int count = 1;

        //searches down the board
        while(r < getNumRows()){
            BoardPosition temp = new BoardPosition(r, c);
            if(isPlayerAtPos(temp, p)) count++;
            else break;
            r++;
        }
        if(count == getNumToWin()) return true;

        return false;
    }//end checkVertWin

    /**
     * @param pos is the current board position
     * @param p is the specific token to check for a win
     * @return true if the last token placed results in getNumToWin in a row diagonally
     * @pre
     * @post true iff char p has #getNumToWin in a row diagonally, self = #self
     */
    public default boolean checkDiagWin(BoardPosition pos, char p)
    {
        int r = pos.getRow();
        int c = pos.getColumn();
        int count = 0;

        //this first looks for a \ direction win
        //this while loop traces r and c to the top left of the board
        while(r > 0 && c > 0){ r--; c--; }

        //this loop keeps count of diagonal characters
        while(r < getNumRows() && c < getNumColumns()){
            BoardPosition temp = new BoardPosition(r, c);
            if(isPlayerAtPos(temp, p)) count++;
            if(count == getNumToWin()) return true;
            if(!isPlayerAtPos(temp, p)) count = 0;
            r++;
            c++;
        }

        //if \ direction didn't win, this step looks for a / direction win
        r = pos.getRow();
        c = pos.getColumn();
        count = 0;
        //this while loop traces r and c to the bottom left of the board
        while(r < getNumRows()-1 && c > 0){ r++; c--; }

        //this loop keeps count of diagonal characters
        while(r > -1 && c < getNumColumns()){
            BoardPosition temp = new BoardPosition(r, c);
            if(isPlayerAtPos(temp, p)) count++;
            if(count == getNumToWin()) return true;
            if(!isPlayerAtPos(temp, p)) count = 0;
            r--;
            c++;
        }

        return false;
    }//end checkDiagWin

    /**
     * @param pos is the board position
     * @param player is the token that is being checked for equivalency
     * @return true if player is at that position, otherwise return false
     * @pre
     * @post true iff the token at pos = player, self = #self
     */
    public default boolean isPlayerAtPos(BoardPosition pos, char player)
    {
        Character c = whatsAtPos(pos);
        if(c == player) return true;
        return false;
    }//end isPlayerAtPos

    /**
     * @param p the char representing token 'X' or 'O'
     * @param c the column number to be placed in
     * @pre 0<=c<=getNumColumns() AND checkIfFree = true
     * @post self = #self, except p will appear in column c in the lowest available row
     */
    public void placeToken(char p, int c);

    /**
     * @param pos is the board position
     * @return the character that is at pos, If there is no token a blank space ' ' will be returned.
     * @post whatsAtPos = character at board position pos, self = #self
     */
    public char whatsAtPos(BoardPosition pos);

}//end IGameBoard class
