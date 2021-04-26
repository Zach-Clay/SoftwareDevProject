package cpsc2150.extendedConnectX;

public class BoardPosition {
    /**
     * @invariant 0<=row<=IGameBoard.getNumRows() AND 0<=col<=IGameBoard.getNumColumns()
     */
    private int row;
    private int col;

    /**
     * @param r is the row number
     * @param c is the column number
     * @pre 0<=r<=IGameBoard.getNumRows() AND 0<=c<=IGameBoard.getNumColumns()
     * @post row = r and col = c
     */
    public BoardPosition(int r, int c)
    {
        row = r;
        col = c;
    }//end BoardPosition constructor

    /**
     * @return the row number
     * @post getRow = row AND row = #row
     */
    public int getRow()
    {
        return row;
    }//end getRow

    /**
     * @return the column number
     * @post getColumn = col AND col = #col
     */
    public int getColumn()
    {
        return col;
    }//end getColumn

    /**
     * @param o is the Object being evaluated for equivalency
     * @return True if the object's data fields are equal, false otherwise
     * @post true iff the object's data fields are equivalent AND row = #row AND col = #col
     */
    @Override
    public boolean equals(Object o)
    {
        if(o == this) return true;
        if(!(o instanceof BoardPosition)) return false;
        BoardPosition pos = (BoardPosition) o;
        return this.row == pos.getRow() && this.col == pos.getColumn();
    }//end equals

    /**
     * @return a string representation of BoardPosition
     * @post toString = "<row>,<col>" AND row = #row AND col = #col
     */
    @Override
    public String toString()
    {
        String s = row + "," + col;
        return s;
    }//end toString

}//end BoardPosition class
