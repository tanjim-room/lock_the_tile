import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RollLudu implements ActionListener {
    private LockTheTile ltt;

    public RollLudu(LockTheTile ltt){
        this.ltt = ltt;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        ltt.luduRoll();
        ltt.enableTiles(ltt.currPlayer);
        ltt.tilesSelected.clear();
        ltt.tilesSelectedSum = 0;
    }

    
}
