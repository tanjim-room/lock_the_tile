
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TileManage implements ActionListener {
    private LockTheTile ltt;
    private int playerIndex;
    private int tileNumber;

    public TileManage(LockTheTile ltt,int playerIndex,int tileNumber) {
        this.ltt = ltt;
        this.playerIndex = playerIndex;
        this.tileNumber = tileNumber;
    }

    @Override
    
    public void actionPerformed(ActionEvent e){
        boolean[][] lockedTile = ltt.lockedTile;
        List<Integer> selectedTiles = ltt.tilesSelected;
        

        if(!lockedTile[playerIndex][tileNumber-1]){
            if(selectedTiles.contains(tileNumber)){
                selectedTiles.remove(tileNumber);
                ltt.tilesSelectedSum -= tileNumber;
                ltt.tiles[playerIndex][tileNumber-1].setEnabled(false);
            }
            else{
                selectedTiles.add(tileNumber);
                ltt.tilesSelectedSum += tileNumber;
                ltt.tiles[playerIndex][tileNumber-1].setEnabled(false);
            }
        }
    }
    
}
