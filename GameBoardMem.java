//package cpsc2150.extendedConnectX;

import java.util.Map;
import java.util.*;

public class GameBoardMem extends AbsGameBoard{
    /**
     * @invariant only characters can occupy the map key
     *            AND 3<=row<=100 AND 3<=col<=100 AND 3<=w<=25 AND w<=r AND w<=c
     * @correspondence self = map<Character,List<BoardPosition>>
     */
    private Map<Character,List<BoardPosition>> map;
    private int row;
    private int col;
    private int win;

    /**
     * @pre 3<=r<=100 AND 3<=c<=100 AND 3<=w<=25 AND w<=r AND w<=c
     * @post row = r AND col = c AND win = w
     *       AND map is initialized as an empty new HashMap
     */
    public GameBoardMem(int r, int c, int w)
    {
        row = r;
        col = c;
        win = w;
        map = new HashMap<>();
    }//end constructor

    public int getNumRows(){return row;}

    public int getNumColumns(){return col;}

    public int getNumToWin(){return win;}

    /**
     * @post board = p added to the lowest row available in #map
     */
    public void placeToken(char p, int c)
    {
        //r represents the row
        int r = row-1;
        BoardPosition pos = new BoardPosition(r, c);

        //this loop traverses through the map and determines the lowest column
        //available to place the token by updating pos
        for(Map.Entry<Character,List<BoardPosition>> m : map.entrySet()){
            List<BoardPosition> list = m.getValue();

            for(int i = 0; i < list.size(); i++){
                BoardPosition temp = list.get(i);
                if(temp.getColumn() == pos.getColumn()){
                    r--;
                    pos = new BoardPosition(r, c);
                }//end if
            }//end for
        }//end for

        //this is the first token placed by player p
        if(!map.containsKey(p)){
            List<BoardPosition> list = new ArrayList<>();
            list.add(pos);
            map.put(p, list);
        }
        //this is not the first token placed by player p
        else{
            List<BoardPosition> list = map.get(p);
            list.add(pos);
            map.put(p, list);
        }

    }//end placeToken

    /**
     * @post whatsAtPos = the character at pos.getRow(), pos.getColumn() AND map = #map
     */
    public char whatsAtPos(BoardPosition pos)
    {
        char p = ' ';

        //for loop to search the map for the value pos
        for(Map.Entry<Character,List<BoardPosition>> m : map.entrySet()){
            List<BoardPosition> list = m.getValue();
            if(list.contains(pos)){
                p = m.getKey();
                break;
            }//end if
        }//end for

        return p;
    }//end whatsAtPos

    /**
     * @post true iff the token at pos = player, map = #map
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player)
    {
        //checks if the map contains the key
        if(map.containsKey(player)){
            List<BoardPosition> list = map.get(player);
            if(list.contains(pos)) return true;
        }

        return false;
    }//end isPlayerAtPos

}//end GameBoardMem
