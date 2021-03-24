//package cpsc2150.extendedConnectX;

public class GameBoard extends AbsGameBoard{
    /**
     * @invariant only characters can occupy the GameBoard
     *           AND 3<=row<=100 AND 3<=col<=100 AND 3<=w<=25 AND w<=r AND w<=c
     * @correspondence self = board[0...max_num_rows][0...max_num_cols]
     */
    private char[][] board;
    private int row;
    private int col;
    private int win;

    /**
     * @pre 3<=r<=100 AND 3<=c<=100 AND 3<=w<=25 AND w<=r AND w<=c
     * @post row = r AND col = c AND win = w
     *       AND board is initialized as a 2D char array with r x c in size
     */
    public GameBoard(int r, int c, int w)
    {
        row = r;
        col = c;
        win = w;
        board  = new char[row][col];
    }//end constructor

    public int getNumRows(){return row;}

    public int getNumColumns(){return col;}

    public int getNumToWin(){return win;}

    /**
     * @post board = p added to the lowest row available in #board
     */
    public void placeToken(char p, int c)
    {
        for(int i = row-1; i >= 0; i--){
            if(board[i][c] == '\0'){
                board[i][c] = p;
                break;
            }
        }
    }//end placeToken

    /**
     * @post whatsAtPos = the character at pos.getRow() and pos.getColumn() AND board = #board
     */
    public char whatsAtPos(BoardPosition pos)
    {
        char p = board[pos.getRow()][pos.getColumn()];
        if(p == '\0') p = ' ';
        return p;
    }//end whatsAtPos

}//end GameBoard class