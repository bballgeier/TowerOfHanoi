// TowersAnim.java
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TowersAnim extends JFrame implements ActionListener {
    TowersCanvas towersCanvas = new TowersCanvas();
    
    private int numDisks = Towers.initNumDisks;
    private int moves = 0;
    private int minMoves = (int)Math.pow(2, numDisks) - 1;
    
    // we make moves when the To buttons are pressed
    // so we keep up with the From button last pressed
    private String fromString = "A";
    // to pass into undo stack, we also keep up with toString
    private String toString;
    
    JButton btnFromA = new JButton("A");
    JButton btnToA = new JButton("A");
    JButton btnFromB = new JButton("B");
    JButton btnToB = new JButton("B");
    JButton btnFromC = new JButton("C");
    JButton btnToC = new JButton("C");
    JButton btnUp = new JButton("\u2191");
    JButton btnDown = new JButton("\u2193");
    JButton btnRestart = new JButton("Restart");
    JButton btnUndo = new JButton("Undo");
    JButton btnRedo = new JButton("Redo");
    JButton btnSave = new JButton("Save");
    JButton btnLoad = new JButton("Load");
    JButton btnAnim = new JButton("Animate Algorithm");
    
    Color aColor = new Color(0x007FFF); // azure as in UCLA blue
    Color bColor = new Color(0xFFFF00);  // how to set with Color.Yellow?
    
    JLabel lblDisks;
    JLabel lblMoves;
    JLabel lblMinMoves;
    
    Stack undo = new Stack("undo");
    Stack redo = new Stack("redo");
    
    public static void main(String[] args) {
        new TowersAnim();
    } // end main
    
    public TowersAnim(){
        
        super("Towers of Hanoi");
        Container pnlMain = this.getContentPane();
        pnlMain.setBackground(Color.GREEN); // don't see any effect
        //pnlMain.setLayout(new BorderLayout());     // default layout
        
        // Center panel
        pnlMain.add(towersCanvas, BorderLayout.CENTER);
        
        // West side
        JPanel pnlWest = new JPanel();
        pnlMain.add(pnlWest, BorderLayout.WEST);
        pnlWest.setLayout(new GridLayout(0,2));
        pnlWest.setBackground(aColor);
        
        JLabel lblMoveFrom = new JLabel("From", SwingConstants.CENTER);
        JLabel lblMoveTo = new JLabel("To", SwingConstants.CENTER);
        lblMoveFrom.setForeground(bColor);
        lblMoveTo.setForeground(bColor);
        lblMoveFrom.setFont(lblMoveFrom.getFont().deriveFont(16f));
        lblMoveTo.setFont(lblMoveTo.getFont().deriveFont(16f));
        pnlWest.add(lblMoveFrom);
        pnlWest.add(lblMoveTo);
        
        pnlWest.add(btnFromA);
        pnlWest.add(btnToA);
        pnlWest.add(btnFromB);
        pnlWest.add(btnToB);
        pnlWest.add(btnFromC);
        pnlWest.add(btnToC);
        pnlWest.add(Box.createVerticalGlue());
        
        // North side
        JPanel pnlNorth = new JPanel();
        pnlMain.add(pnlNorth, BorderLayout.NORTH);
        pnlNorth.setLayout(new BoxLayout(pnlNorth, BoxLayout.Y_AXIS));
        pnlNorth.setBackground(aColor);
        
        JLabel lblInstr1 = new JLabel("Move all of the disks to Tower C.");
        JLabel lblInstr2 = new JLabel("But you cannot place a larger disk onto a smaller disk.");
        lblInstr1.setFont(lblInstr1.getFont().deriveFont(16f));
        lblInstr2.setFont(lblInstr2.getFont().deriveFont(16F));
        lblInstr1.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInstr2.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblInstr1.setForeground(bColor);
        lblInstr2.setForeground(bColor);
        pnlNorth.add(lblInstr1);
        pnlNorth.add(lblInstr2);
        
        // East side
        JPanel pnlEast = new JPanel();
        pnlMain.add(pnlEast, BorderLayout.EAST);
        pnlEast.setLayout(new BoxLayout(pnlEast, BoxLayout.Y_AXIS));
        pnlEast.setBackground(aColor);
        
        pnlEast.add(Box.createVerticalGlue());
        lblDisks = new JLabel("Disks: " + numDisks);  // would like this to be centered with buttons below
        pnlEast.add(lblDisks);
        lblDisks.setForeground(bColor);
        lblDisks.setFont(lblDisks.getFont().deriveFont(16f));
        
        // create a panel with flowlayout to display up and down buttons
        JPanel pnlArrows = new JPanel();
        pnlArrows.setLayout(new FlowLayout());
        pnlEast.add(pnlArrows);
        pnlArrows.setBackground(aColor);
        //BasicArrowButton btnUp = new BasicArrowButton(BasicArrowButton.NORTH);  // not working
        
        pnlArrows.add(btnDown);
        pnlArrows.add(btnUp);
        pnlEast.add(Box.createVerticalGlue());
        
        // create a panel to display moves
        JPanel pnlMoves = new JPanel();
        pnlMoves.setLayout(new GridLayout(0,1));
        pnlMoves.setBackground(aColor);
        pnlEast.add(pnlMoves);
        
        lblMoves = new JLabel("Moves: " + moves);
        lblMoves.setForeground(bColor);
        lblMoves.setFont(lblMoves.getFont().deriveFont(16f));
        //lblMoves.setAlignmentX(Component.LEFT_ALIGNMENT);  
        pnlMoves.add(lblMoves);
        
        lblMinMoves = new JLabel("Minimum moves: " + minMoves);
        //lblminMoves.setAlignmentX(Component.LEFT_ALIGNMENT); 
        pnlMoves.add(lblMinMoves);
        lblMinMoves.setForeground(bColor);
        lblMinMoves.setFont(lblMinMoves.getFont().deriveFont(16f));
        pnlEast.add(Box.createVerticalGlue());
        
        // South side
        JPanel pnlSouth = new JPanel();
        pnlMain.add(pnlSouth, BorderLayout.SOUTH);
        pnlSouth.setLayout(new FlowLayout());
        pnlSouth.setBackground(aColor);
        
        pnlSouth.add(btnRestart);
        pnlSouth.add(btnUndo);
        pnlSouth.add(btnRedo);
        //pnlSouth.add(btnSave);
        //pnlSouth.add(btnLoad);
        pnlSouth.add(btnAnim);
        
        // actionListeners
        btnFromA.addActionListener(this);
        btnToA.addActionListener(this);
        btnFromB.addActionListener(this);
        btnToB.addActionListener(this);
        btnFromC.addActionListener(this);
        btnToC.addActionListener(this);
        btnUp.addActionListener(this);
        btnDown.addActionListener(this);
        btnRestart.addActionListener(this);
        btnUndo.addActionListener(this);
        btnRedo.addActionListener(this);
        btnSave.addActionListener(this);
        btnAnim.addActionListener(this);
        
        // handle window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.pack();
        this.setSize(900, 600);
        this.setVisible(true);
    } // end
    
     private void updateDisks(int disksChange){
        // update numDisks
        if (disksChange == 1){              // for btnUp
            numDisks++;
            System.out.println("Up");
        } else if (disksChange == -1){      // for btnDown
            numDisks--;
            System.out.println("Down");
        } else if (disksChange == 0){       // for btnRestart
            System.out.println("Restart");
        } else {
            System.out.println("Invalid input for TowersAnim.updateDisks");
        } // end if/else
        
        // restart game with appropriate number of disks
        moves = 0;
        towersCanvas.restart(numDisks);
        undo = new Stack("undo");
        redo = new Stack("redo");
        
        // reset all labels -- note some changes aren't necessary for some cases
        minMoves = (int)Math.pow(2, numDisks) - 1;
        lblMoves.setText("Moves: " + moves);
        lblMinMoves.setText("Minimum moves: " + minMoves);
        lblDisks.setText("Disks: " + numDisks);
    } // end updateLabels
    
    private void updateMoves(){
        moves++;                           // this wouldn't work for undo
        lblMoves.setText("Moves: " + moves);
        // store the undo data
        this.undo.push(fromString + ", " + toString);
        System.out.println("Most recent move: " + this.undo.getTopData());
    } // end displayMoveDiskFromTo
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == btnFromA) {
            this.fromString = "A";
            System.out.println("move from A was selected");
        } else if (e.getSource() == btnToA) {
            this.toString = "A";
             System.out.println("move To A was selected");
             // check that this is a legal move before incrementing moves
            if (this.towersCanvas.moveDiskFromTo(fromString, "A")) {
                this.updateMoves();
            } // end if
            
        } else if (e.getSource() == btnFromB) {
            this.fromString = "B";
            System.out.println("move from B was selected");
        } else if (e.getSource() == btnToB) {
            this.toString = "B";
            System.out.println("move to B was selected");
            if (this.towersCanvas.moveDiskFromTo(fromString, "B")) {
                this.updateMoves();
            } // end if
            
        } else if (e.getSource() == btnFromC) {
            this.fromString = "C";
            System.out.println("move from C was selected");
        } else if (e.getSource() == btnToC) {
            this.toString = "C";
            System.out.println("move to C was selected");
            if (this.towersCanvas.moveDiskFromTo(fromString, "C")) {
                this.updateMoves();
            } // end if
            
        } else if (e.getSource() == btnRestart){
            this.updateDisks(0);
            
        } else if (e.getSource() == btnUp){
            this.updateDisks(1);
            
        } else if (e.getSource() == btnDown){
            if (this.numDisks > 1){    // want no action if numDisks is 1
                this.updateDisks(-1);
            } // end if
            
        } else if (e.getSource() == btnUndo){
            if (this.undo.getTop() != null){
                // reduce moves by 1 since we are undoing step
                moves--;
                lblMoves.setText("Moves: " + moves);
                // pop move data from undo Stack and parse data
                String move = this.undo.pop();
                String[] moveArray = move.split("\\s*, \\s*");  // split at comma
                                                                // \s is for white space and * says 0 or more
                String from = moveArray[0];
                String to = moveArray[1];
                // push this move data to redo
                this.redo.push(move);
                // make move -- note that we swap from and to
                towersCanvas.moveDiskFromTo(to, from);
            } // end if
            
        } else if (e.getSource() == btnRedo){
            if (this.redo.getTop() != null){
                // increment moves by 1 since we are redoing step
                moves++;
                lblMoves.setText("Moves: " + moves);
                // pop move data from redo Stack and parse data
                String move = this.redo.pop();
                String[] moveArray = move.split("\\s*, \\s*");  // split at comma
                                                                // \s is for white space and * says 0 or more
                String from = moveArray[0];
                String to = moveArray[1];
                // push this move data to undo
                this.undo.push(move);
                // make move -- note that perform the original move -- no swap
                towersCanvas.moveDiskFromTo(from, to);
            } // end if
        }
    } // end actionPerformed
} // end TowersAnim

// old method used -- became obsolete once started to use HashMap
// using HashMap for dictionary like abilities -- also modified moveDiskFromTo methods
// to take strings as arguments
    
    //private Stack getStack(String name){
    //    if (name == "A"){
    //        return this.towersCanvas.towers.stackA;
    //    } else if (name == "B") {
    //        return this.towersCanvas.towers.stackB;
    //    } else if (name == "C"){
    //        return this.towersCanvas.towers.stackC;
    //    } else {
    //        System.out.println("Something went wrong with getStack()");
    //        return new Stack(); // not sure what to do here -- but I have to return something
    //                            // try returning null
    //    }
    //} // end getStack
    
    //private TowerPanel getTowerPanel(String name){
    //    if (name == "A"){
    //        return this.towersCanvas.pnlTowerA;
    //    } else if (name =="B"){
    //        return this.towersCanvas.pnlTowerB;
    //    } else if (name == "C"){
    //        return this.towersCanvas.pnlTowerC;
    //    } else {
    //        System.out.println("Something went wrong with getTowerPanel");
    //        return new TowerPanel("D");  // not sure what to do here either
    //    }
    //} // end getTowerPanel
    
    //private void updateFrom(String name){
    //    fromString = name;
    //    fromStack = this.getStack(fromString);
    //    fromTowerPanel = this.getTowerPanel(fromString);
    //} // end updateFrom
    
    //private void updateTo(String name){
    //    toString = name;
    //    toStack = this.getStack(toString);
    //    toTowerPanel = this.getTowerPanel(toString);
    //} // end updateTo