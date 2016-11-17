// TowersCanvas.java
import java.awt.*;
import javax.swing.*;
import java.util.Map; // these two are for dictionary support
import java.util.HashMap;

public class TowersCanvas extends JPanel {
    Towers towers = new Towers(); // defaults to numDisks number of disks
    TowerPanel pnlTowerA;
    TowerPanel pnlTowerB;
    TowerPanel pnlTowerC;
    Map<String, TowerPanel> towerPanelNames = new HashMap<String, TowerPanel>(3);
    
    public TowersCanvas(){
        this(Towers.initNumDisks); // ensures that we start with same number of disks as Towers does
    } // end constructor
    
    public TowersCanvas(int numDisks){
        // not sure why, but making this constructor
        // essentially the same as restart doesn't quite work
        // maybe it could if we repaint or validate some stuff
        // in TowersAnim. But this is fine and
        // I like not having to repaint or validate outside of this file
        
        this.setLayout(new GridLayout(0,3));
        // first time through we are simply starting
        // but we can use same method for restart button -- see comments above
        this.restart(numDisks);
        
    } // end constructor
    
    public void restart(int numDisks){
        this.removeAll();             // in case we are restarting, clear out TowersCanvas panel
        // could remove disk items from the pnlTowers, but seems worth it to just reset variables
        pnlTowerA = new TowerPanel("A"); 
        pnlTowerB = new TowerPanel("B");
        pnlTowerC = new TowerPanel("C");
        this.add(pnlTowerA);
        this.add(pnlTowerB);
        this.add(pnlTowerC);
        // refresh stack data        
        towers = new Towers(numDisks);
        
        // display disks on TowerA
        for (int i = 1; i <= numDisks; i++){  // note going from 1 to numDisks, inclusive
            // for convenience, use makeDisk method from towers
            pnlTowerA.addDisk(towers.makeDisk(i), i); // index 0 is the verticalGlue
        } // end for
        
        this.validate(); // important
        // don't seem to be necessary
        //pnlTowerA.repaint();
        //pnlTowerB.repaint();
        //pnlTowerB.repaint();
        
        // add TowerPanels to hashMap
        towerPanelNames.put("A", pnlTowerA);
        towerPanelNames.put("B", pnlTowerB);
        towerPanelNames.put("C", pnlTowerC);
    } // end restart
    
    // an analogous method from Towers
    // and calls that method to determine if to proceed
    // its purpose is to handle displaying disk changes as Towers disk changes happen
    public boolean moveDiskFromTo(String fromString, String toString){
        TowerPanel fromTowerPanel = towerPanelNames.get(fromString);
        TowerPanel toTowerPanel = towerPanelNames.get(toString);
        // the disk in Towers has already been moved. So we access the top disk
        // in toStack to know what to display in toTower
        Stack toStack = towers.stackNames.get(toString);
        
        // check if move is legal by calling analogous method from Towers
        boolean isLegalMove = towers.moveDiskFromTo(fromString, toString);
        if (isLegalMove){
            toTowerPanel.addDisk(toStack.getTopData());
            fromTowerPanel.deleteDisk();
        } // end if
        return isLegalMove;
    } // end moveDiskFromTo

} // end TowersCanvas

// helper class
class TowerPanel extends JPanel {
    public TowerPanel(String name){
        
        this.setBackground(Color.YELLOW);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue()); // pushes tower to bottom
        // Display tower labels
        JLabel line = new JLabel("-----------------");
        line.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(line);
        JLabel lblName = new JLabel(name);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(lblName);
    } // end constructor
    
    public void addDisk(String disk, int index){
        System.out.println("attempt to add " + disk);
        JLabel lblDisk = new JLabel(disk);
        lblDisk.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDisk.setFont(lblDisk.getFont().deriveFont(24f));
        this.add(lblDisk, index);
        this.validate(); // validate?  // not sure if necessary
        this.repaint(); // not sure if necesssry -- was working fine without -- but not deleteDisk
    } // end addDisk with index
    
    public void addDisk(String disk){
        this.addDisk(disk, 1);
    } // end addDisk with index default to 1
    
     public void deleteDisk(){ // remove index 1 -- after the VerticalGlue
        System.out.println("attempt to delete a disk");
        this.remove(1);
        this.validate(); // not sure if necessary
        this.repaint();  // this is the key to having the disks disappear without manually resizing
    } // end deleteDisk
} // end TowerPanel

