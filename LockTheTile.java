/*
 Name   :   Md. Tanjim Arafat
 ID     :   ASH2225026M
 Session:   2021-2022
 */

import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class LockTheTile extends JFrame {


       
        public JLabel                   playerName;
        public ArrayList<Integer>       tilesSelected;
        public JButton[][]              tiles;
        public int                      playerNum;
        public String[]                 playerNames;
        public int                      currPlayer;
        public JButton                  rollBtn;
        public JButton                  lockBtn;
        public boolean[][]              lockedTile;
        public int                      tilesSelectedSum;
        public JLabel                   luduImg1;
        public JLabel                   luduImg2;
        public int                      ludu1, ludu2;


        Font smallFont  = new Font("Arial",Font.BOLD,16);
        Font mediumFont = new Font("Arial",Font.BOLD,20);
        Font largeFont  = new Font("Arial",Font.BOLD,36);

        Color greenBgColor      = new Color(44,157,84);
        Color lightGreenColor   = new Color(198,238,174);

        public LockTheTile(){

            playerNumIn();

        }


        /*
         Creating a Dialog Box for taking 
         Player Number as a input a from user
         */
        public void playerNumIn(){

        JDialog playerNumDialog = new JDialog(this,"Number of Players",true);
        playerNumDialog.setSize(500,300);
        playerNumDialog.setLayout(new GridLayout(4,1));
        playerNumDialog.getContentPane().setBackground(greenBgColor);

        JLabel playerNumLabel = new JLabel("Enter number of players (2-4):");
        playerNumLabel.setFont(mediumFont);
        

         /*
         Using JTextField for a Player Number Input Field 
         for taking player number input from user
         */
        
        JTextField playerNumInputField =new JTextField();
        playerNumInputField.setBackground(lightGreenColor);
        playerNumInputField.setFont(mediumFont);
       
        /*
         A Button for playing with computer 
         which go to computer vs human mode
         */
       
        JButton comBtn =new JButton("Play With Computer");
        comBtn.setBackground(greenBgColor);
        comBtn.setFont(mediumFont);
       

        /*
         Button for going to next step for taking player name 
         */
        JButton nxtBtn = new JButton("Next");
        nxtBtn.setBackground(greenBgColor);
        nxtBtn.setFont(largeFont);
        
      
        /*
        implement Exception Handling & Next Button Action
        */   
        nxtBtn.addActionListener(e ->{
            try{
                    playerNum = Integer.parseInt(playerNumInputField.getText());
                    if(playerNum <2 || playerNum >4){
                        throw new NumberFormatException();
                    }
                    playerNumDialog.dispose();
                    playerNameInput();

            }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog( playerNumDialog,"Enter players number between (2-4).");

            }
        });


        //implement play with computer button Action
        comBtn.addActionListener(e ->{
           
            playerNum = 2;  
            playerNames =new String[playerNum];
            playerNames[0] = "Human"; 
            playerNames[1] = "Computer"; 
            
            playerNumDialog.dispose();  
            gameBoardUI();  
           
        });
        

    //Here Adding Component for player number input & computer mode button to The Dialog Box
            playerNumDialog.add(playerNumLabel);
            playerNumDialog.add(playerNumInputField);
            playerNumDialog.add(nxtBtn);
            playerNumDialog.add(comBtn);
            playerNumDialog.setLocationRelativeTo(null);
            playerNumDialog.setVisible(true);


    }

    //Here a Dialog Box for taking Player Name Input from user
    public void playerNameInput(){

        JDialog playerNameDialog =new JDialog(this,"Player Names",true);
        playerNameDialog.setSize(500,200*playerNum);
        playerNameDialog.setLayout(new GridLayout(playerNum+1,2));
        
        

        //Here Player Name Input Field as JTextField from user
        playerNames = new String[playerNum];
        JTextField[] playerNameInputField = new JTextField[playerNum];

        for (int i=0;i <playerNum;i++) {
                JLabel nameLabel =new JLabel("Player "+(i+1)+" name :");
                nameLabel.setFont(mediumFont);
                
                playerNameInputField[i] = new JTextField();
                playerNameInputField[i].setFont(mediumFont);
                playerNameInputField[i].setBackground(lightGreenColor);
               
                playerNameDialog.add(nameLabel);
                playerNameDialog.add(playerNameInputField[i]);
        }

        playerNameDialog.getContentPane().setBackground(greenBgColor);

            //Here a  Start Game Button for starting the game to play
            JButton startBtn =new JButton("Start Game");
            startBtn.setFont(mediumFont);
            startBtn.setBackground(greenBgColor);
            

        //Here a Back Button to go to the previous Dialog Box
        JButton backBtn = new JButton("Back");

        backBtn.setFont(mediumFont);
        backBtn.setBackground(greenBgColor);
       
        //Here Back Button action for going to previous 
        backBtn.addActionListener(e ->{
            
            playerNameDialog.dispose();
            playerNumIn();
   
        });


        // Start Game Button Action to play game
        startBtn.addActionListener(e ->{
                for (int i=0;i<playerNum;i++){
                    playerNames[i] = playerNameInputField[i].getText();
                    if(playerNames[i].isEmpty()){
                        JOptionPane.showMessageDialog(playerNameDialog,"Please enter name for player "+(i+1)+".");
                        return;
                    }
                }
                playerNameDialog.dispose();
                
                gameBoardUI();
        });

       
        //Here Adding Componenent in Player Name Dialog Box

            playerNameDialog.add(backBtn);
            playerNameDialog.add(startBtn);
        
            playerNameDialog.setLocationRelativeTo(null);
            playerNameDialog.setVisible(true);
    }

    // Game Board UI Design
    public void gameBoardUI(){

        currPlayer = 0;
        lockedTile = new boolean[playerNum][10];
        tilesSelected = new ArrayList<>();
        tilesSelectedSum = 0;

        setTitle("Lock The Tile");
        setSize(800,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainBoard = new JPanel(new BorderLayout(10,10));
        mainBoard.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        tiles = new JButton[playerNum][10];

        // Player Tiles Position in game board
        JPanel topPanel    = new JPanel(new GridLayout(1,10,5,5));
        JPanel leftPanel   = new JPanel(new GridLayout(10,1,5,5));
        JPanel bottomPanel = new JPanel(new GridLayout(1,10,5,5));
        JPanel rightPanel  = new JPanel(new GridLayout(10,1,5,5));
        Dimension tileSize = new Dimension(80,80);

        // Loop of player and his tiles Management
        for(int i=0; i <playerNum;i++){

            for(int j=0;j<10;j++){
                tiles[i][j] = new JButton(String.valueOf(j+1));
                tiles[i][j].setPreferredSize(tileSize);
                tiles[i][j].setFont(smallFont);
                tiles[i][j].addActionListener(new TileManage(this,i,j+1));
          
                if(playerNum ==2){
                   
                    if(i ==0){
                        tiles[i][j].setBackground(new Color(239,90,111));
                        tiles[i][j].setForeground(Color.WHITE);
                        topPanel.add(tiles[i][j]);

                    }else{
                        tiles[i][j].setBackground(new Color(55,149,189));
                        tiles[i][j].setForeground(Color.WHITE);
                        bottomPanel.add(tiles[i][j]);
                    }
                }else if(playerNum == 3){
                    if(i ==0){
                        tiles[i][j].setBackground(new Color(239,90,111));
                        tiles[i][j].setForeground(Color.WHITE);
                        topPanel.add(tiles[i][j]);
                    }else if(i ==1){
                        tiles[i][j].setBackground(new Color(21,179,146));
                        tiles[i][j].setForeground(Color.WHITE);
                        leftPanel.add(tiles[i][j]);
                    }else{
                        tiles[i][j].setBackground(new Color(55,149,189));
                        tiles[i][j].setForeground(Color.WHITE);
                        bottomPanel.add(tiles[i][j]);
                    }
                }else if(playerNum ==4){
                    if(i ==0){
                        tiles[i][j].setBackground(new Color(239,90,111));
                        tiles[i][j].setForeground(Color.WHITE);
                        topPanel.add(tiles[i][j]);
                    }else if(i == 1){
                        tiles[i][j].setBackground(new Color(21,179,146));
                        tiles[i][j].setForeground(Color.WHITE);
                        leftPanel.add(tiles[i][j]);
                    }else if(i == 2){
                        tiles[i][j].setBackground(new Color(55,149,189));
                        tiles[i][j].setForeground(Color.WHITE);
                        bottomPanel.add(tiles[i][j]);

                    }else{
                        tiles[i][j].setBackground(new Color(255,143,0));
                        tiles[i][j].setForeground(Color.WHITE);
                        rightPanel.add(tiles[i][j]);
                    }
                }
            }
        }

        // Roll Button for rolling ludu
        rollBtn = new JButton("Roll Ludu");
        rollBtn.setFont(mediumFont);
        rollBtn.setBackground(lightGreenColor);
        rollBtn.addActionListener(new RollLudu(this));

        // lock Button to lock tiles
        lockBtn =new JButton("Lock Tile");
        lockBtn.setFont(mediumFont);
        lockBtn.setBackground(lightGreenColor);
        lockBtn.addActionListener(new LockTile(this));
        lockBtn.setEnabled(false);


        //New Game Button , start a new game
        JButton newGame =new JButton("New Game");
        newGame.setFont(smallFont);
        newGame.setBackground(lightGreenColor);
        newGame.addActionListener(e ->{
            
            resetGame();
           
        });

        playerName = new JLabel(playerNames[currPlayer]+"'s Turn");
        playerName.setFont(mediumFont);
     
        
        JPanel controlPanel =new JPanel();
        controlPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx           = 0;
        gbc.gridy           = 0;
        gbc.gridwidth       = 1;
        gbc.gridheight      = 1;
        gbc.weightx         = 1.0;
        gbc.weighty         = 1.0;
        gbc.fill            = GridBagConstraints.CENTER;

        gbc.fill            = GridBagConstraints.NONE;
        gbc.anchor          = GridBagConstraints.CENTER;

        controlPanel.add(rollBtn, gbc);
     

        luduImg1 = new JLabel();
        luduImg2 = new JLabel();

        luduImg1.setIcon(loadluduImg(1));
        luduImg2.setIcon(loadluduImg(1));

        JPanel luduPanel =new JPanel();
        luduPanel.add(luduImg1);
        luduPanel.add(luduImg2);

        gbc.gridy++;
        controlPanel.add(luduPanel, gbc);

        
        gbc.gridy++;
        controlPanel.add(playerName, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        controlPanel.add(lockBtn, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        controlPanel.add(newGame, gbc);

        mainBoard.add(controlPanel, BorderLayout.CENTER);

        mainBoard.add(topPanel, BorderLayout.NORTH);
        mainBoard.add(leftPanel, BorderLayout.WEST);
        mainBoard.add(bottomPanel, BorderLayout.SOUTH);
        mainBoard.add(rightPanel, BorderLayout.EAST);
        mainBoard.add(controlPanel, BorderLayout.CENTER);

        add(mainBoard);
        setLocationRelativeTo(null);
        setVisible(true);

        enableTiles(currPlayer);
    
    
        add(mainBoard); 

    }


    // Ludu Roll Method & Random Number Making 
    public void luduRoll(){
        Random randomNum =new Random();
        ludu1 = randomNum.nextInt(6)+1;
        ludu2 = randomNum.nextInt(6)+1;

        
        luduImg1.setIcon(loadluduImg(ludu1));
        luduImg2.setIcon(loadluduImg(ludu2));

        lockBtn.setEnabled(true);
        rollBtn.setEnabled((false));
        

        if(!matchLuduSum(ludu1+ludu2)){
            JOptionPane.showMessageDialog(LockTheTile.this,
            playerNames[currPlayer]+" cannot match the ludu sum. Next Player's Turn.");
            goToNextPlayer();
        }

    }

    // Match ludu Number Sum
    public boolean matchLuduSum(int luduSum){
        return matchSum(luduSum, 0);
    }

    // Match ludu Number Sum
    public boolean matchSum(int sum,int startIndex){
        if(sum == 0)
            return true;
        if(sum < 0)
            return false;
        for(int i=startIndex;i <10;i++){
            if(!lockedTile[currPlayer][i] && matchSum(sum-(i+1),i+1)) {
                return true;
            }
        }
        return false;
    }

    public void enableTiles(int playerIndex){
        
        for(int i =0; i <playerNum;i++){
            for(int j= 0;j <10;j++){
                tiles[i][j].setEnabled(i ==playerIndex && !lockedTile[i][j]);
            }
        }
    }

    public void lockTilesSelected(){
        for(int tileNumber : tilesSelected){
            tiles[currPlayer][tileNumber-1].setEnabled(false);
            tiles[currPlayer][tileNumber-1].setBackground(Color.BLACK);
            tiles[currPlayer][tileNumber-1].setText("");
            lockedTile[currPlayer][tileNumber-1] = true;
        }
        tilesSelected.clear();
        tilesSelectedSum = 0;
        lockBtn.setEnabled(false);

        goToNextPlayer();
    }

    

    // Load Ludu Image
    public ImageIcon loadluduImg(int luduValue){
        String imgPath ="images/"+luduValue+".jpg";
        return new ImageIcon(imgPath);
    }
    
 
    public void computerMode(){
      
        playerNum   = 2;
        playerNames = new String[]{"Human","Computer"};
        currPlayer  = 0; 
        tilesSelected.clear();
        tilesSelectedSum = 0;
        lockedTile = new boolean[2][10];  
    
        enableTiles(currPlayer);
    
        rollBtn.setEnabled(true);
        lockBtn.setEnabled(false);

    }

    public void computerTurn(){

        Timer timer = new Timer(2000,e ->{
            luduRoll();  
     
            computerSelection(ludu1+ludu2);
            if(tilesSelectedSum == (ludu1+ludu2)){
                lockTilesSelected();
            } 
                    
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void computerSelection(int sum){
        tilesSelected.clear();
        tilesSelectedSum = 0;
    
        for(int i=9;i >=0;i--){
            if (!lockedTile[1][i] && tilesSelectedSum+(i+1)<=sum) {
                tilesSelected.add(i+1);  
                tilesSelectedSum +=(i+1);  
            }
            if(tilesSelectedSum == sum){
                break;  
            }
        }
    }

    public void goToNextPlayer(){
        if(playerNames[0] =="Human" || playerNames[1] == "Computer"){
            if(currPlayer ==1){ 
            currPlayer = 0;  
            enableTiles(currPlayer);
            rollBtn.setEnabled(true);
            lockBtn.setEnabled(false);
        }else{  
            currPlayer =1;  
            computerTurn(); 
        }
        }
        else{
            currPlayer =(currPlayer+1) % playerNum;
            
            rollBtn.setEnabled(true);
            lockBtn.setEnabled(false);
            enableTiles(currPlayer);
            
        }
        
    
        playerName.setText(playerNames[currPlayer]+"'s Turn");
        isGameOver();  
        
    }
    public void isGameOver(){

        for(int i =0;i <playerNum;i++){
            boolean allTilesClosed = true;
            for(int j =0;j <10;j++){
                if(!lockedTile[i][j]){
                    allTilesClosed = false;
                    break;
                }
            }
    
            if(allTilesClosed){
                String winner = (i == 1 && playerNames[1].equals("Computer")) ? "Computer" : playerNames[i];
            
                JDialog winnerDialog =new JDialog(this,"Winner",true);
                winnerDialog.setBackground(Color.ORANGE);
                winnerDialog.setSize(500,300);
    
                JLabel winnerLabel =new JLabel(winner+" wins!");
                winnerLabel.setFont(largeFont);
                winnerDialog.getContentPane().setBackground(greenBgColor);
                winnerLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE,2), 
                    BorderFactory.createEmptyBorder(10,50,10,10)
                ));

                winnerDialog.add(winnerLabel);
                winnerDialog.setLocationRelativeTo(null);
                winnerDialog.setVisible(true);
            }
        }
    }
    public void resetGame(){
     
        getContentPane().removeAll(); 
        revalidate(); 
        repaint(); 
        dispose();

        playerNumIn(); 
    }
                         


    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new LockTheTile());
    }
}
