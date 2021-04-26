package cpsc2150.extendedConnectX;

public abstract class AbsGameBoard implements IGameBoard{

    /**
     * @return a string representation of the GameBoard
     * @post toString = current GameBoard in printable String format
     */
    @Override
    public String toString() {
        String s = "|";
        //oneSpace is used to determine when the number is >= 10
        //so the spacing can properly adjust
        int oneSpace = 10;

        for(int i = 0; i < getNumColumns(); i++){
            if(i >= oneSpace) s += " " + i + " |";
            else s += "  " + i + " |";
        }
        s+= "\n";

        for(int i = 0; i < getNumRows(); i++) {
            for(int j = 0; j < getNumColumns(); j++) {
                BoardPosition pos = new BoardPosition(i, j);
                s += "| " + whatsAtPos(pos) + "  ";
            }
            s += "|\n";
        }//end for

        return s;
    }//end toString

}//end AbsGameBoard class
