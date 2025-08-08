

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LockTile implements ActionListener {
    private LockTheTile ltt;

    public LockTile(LockTheTile ltt){
        this.ltt = ltt;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedTileSum = ltt.tilesSelectedSum;
        int luduTotal = ltt.ludu1+ltt.ludu2;

        if(selectedTileSum == luduTotal){
            ltt.lockTilesSelected();
        }else{
            JOptionPane.showMessageDialog(ltt,"Selected tiles do not equal to the ludu sum. Next Player's Turn.");
            for(int tileNumber : ltt.tilesSelected){
                ltt.tiles[ltt.currPlayer][tileNumber-1].setEnabled(true);
            }

            ltt.tilesSelected.clear();
            ltt.tilesSelectedSum = 0;
            ltt.goToNextPlayer();
        }
    }
}
