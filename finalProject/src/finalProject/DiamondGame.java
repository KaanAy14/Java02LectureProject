package finalProject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class DiamondGame extends JFrame {
	private WindowSwitcher switcher;
    private JPanel gamePanel;
    private JButton[][] matrixButtons;
    private JButton magicToolButton;
    private JButton upButton = new JButton("↑");
    private JButton downButton = new JButton("↓");
    private JButton leftButton = new JButton("←");
    private JButton rightButton = new JButton("→");
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel basketLabel;
    private JLabel collectedPointsLabel;
    private int score;
    private int diamondsCount;
    private Timer timer;
    private int timeLeft;
    private int magicToolX;
    private int magicToolY;

    public DiamondGame(int gridSize, int diamonds, WindowSwitcher switcher) {
        this.switcher = switcher;

        setTitle("Diamond Miner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        score = 0;
        diamondsCount = diamonds;
        
        basketLabel = new JLabel("Basket: 0");
        add(basketLabel, BorderLayout.LINE_START);
        
        collectedPointsLabel = new JLabel(" Points: 0");
        add(collectedPointsLabel, BorderLayout.LINE_END);

        gamePanel = new JPanel(new GridLayout(gridSize, gridSize));
        matrixButtons = new JButton[gridSize][gridSize];
        gamePanel.setBackground(new Color(204,0,102));
        
        
        upButton.setBackground(new Color(255,255,255));
        upButton.setForeground(new Color(0,0,0));
        downButton.setBackground(new Color(255,255,255));
        downButton.setForeground(new Color(0,0,0));
        leftButton.setBackground(new Color(255,255,255));
        leftButton.setForeground(new Color(0,0,0));
        rightButton.setBackground(new Color(255,255,255));
        rightButton.setForeground(new Color(0,0,0));
        
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(80, 80));
                matrixButtons[i][j] = button;
                gamePanel.add(button);
            }
        }

        changeMatrixButtonsColor(Color.DARK_GRAY);

        JPanel controlPanel = new JPanel(new GridLayout(3, 3)); // Panel for directional buttons
        controlPanel.add(new JLabel()); // placeholder
        controlPanel.add(upButton);
        controlPanel.add(new JLabel()); // placeholder
        controlPanel.add(leftButton);
        controlPanel.add(new JLabel()); // placeholder
        controlPanel.add(rightButton);
        controlPanel.add(new JLabel()); // placeholder
        controlPanel.add(downButton);
        controlPanel.add(new JLabel()); // placeholder

        upButton.setPreferredSize(new Dimension(50, 50));
        downButton.setPreferredSize(new Dimension(50, 50));
        leftButton.setPreferredSize(new Dimension(50, 50));
        rightButton.setPreferredSize(new Dimension(50, 50));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(controlPanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Score: " + score);

        add(scoreLabel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        placeDiamonds(diamondsCount);
      
        
        magicToolButton = new JButton("Magic Tool");

        magicToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton currentButton = matrixButtons[magicToolX][magicToolY];
                collectDiamond(currentButton);
            }
        });

        PointsManager pointsManager = PointsManager.getInstance();
        int duration = (pointsManager.getTotalPoints())/4;
        timeLeft = duration;

        timeLabel = new JLabel("Time Left: " + timeLeft + " seconds");
        add(timeLabel, BorderLayout.NORTH);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    timeLabel.setText("Time Left: " + timeLeft + " seconds");
                } else {
                    endGame();
                }
            }
        });
        timer.start();

        // Initialize magic tool position
        magicToolX = gridSize / 2;
        magicToolY = gridSize / 2;
        updateMagicToolPosition();

        // Add ActionListener for directional buttons
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveMagicTool(0); // 0 represents moving Up
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveMagicTool(1); // 1 represents moving Down
            }
        });

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveMagicTool(2); // 2 represents moving Left
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveMagicTool(3); // 3 represents moving Right
            }
        });
    }

    private void updateMagicToolPosition() {
        matrixButtons[magicToolX][magicToolY].setText(""); // Remove magic tool from the current position
        ImageIcon magicToolIcon = new ImageIcon("pickaxe.png"); // Replace with your magic tool icon file path
        matrixButtons[magicToolX][magicToolY].setIcon(magicToolIcon);
    }

    private void placeDiamonds(int count) {
        Random rand = new Random();
        ArrayList<Point> usedPositions = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(matrixButtons.length);
            int y = rand.nextInt(matrixButtons[0].length);
            Point position = new Point(x, y);

            if (!usedPositions.contains(position)) {
                usedPositions.add(position);
                JButton button = matrixButtons[x][y];
                ImageIcon diamondIcon = new ImageIcon("diamond.png"); // Replace with your diamond icon file path
                button.setIcon(diamondIcon);
            } else {
                i--; // try again if the position is already used
            }
        }
    }

    private void moveMagicTool(int direction) {
        int newMagicToolX = magicToolX;
        int newMagicToolY = magicToolY;

     // Clear the icon from the current position
        matrixButtons[magicToolX][magicToolY].setIcon(null);


        switch (direction) {
            case 0: // Move Up
                newMagicToolX = Math.max(0, magicToolX - 1);
                break;
            case 1: // Move Down
                newMagicToolX = Math.min(matrixButtons.length - 1, magicToolX + 1);
                break;
            case 2: // Move Left
                newMagicToolY = Math.max(0, magicToolY - 1);
                break;
            case 3: // Move Right
                newMagicToolY = Math.min(matrixButtons[0].length - 1, magicToolY + 1);
                break;
        }

        // Update the magic tool position
        magicToolX = newMagicToolX;
        magicToolY = newMagicToolY;

        // Check for diamond collision
        JButton currentButton = matrixButtons[magicToolX][magicToolY];
        if (currentButton.getIcon() != null) {
            collectDiamond(currentButton);
        }

        // Set magic tool icon at the new position
        updateMagicToolPosition();
    }

    private void collectDiamond(JButton button) {
        if (button.getIcon() != null) {
            int buttonX = -1;
            int buttonY = -1;
            // Find the position of the clicked button
            for (int i = 0; i < matrixButtons.length; i++) {
                for (int j = 0; j < matrixButtons[0].length; j++) {
                    if (matrixButtons[i][j] == button) {
                        buttonX = i;
                        buttonY = j;
                        break;
                    }
                }
            }

            // If the clicked button is the magic tool, collect the diamond
            if (buttonX == magicToolX && buttonY == magicToolY) {
                score += 100;
                diamondsCount--;
                scoreLabel.setText("Score: " + score);
                button.setIcon(null); // Remove diamond icon from button

                if (diamondsCount == 0) {
                    endGame();
                }

                // Update the basket label to show the number of collected diamonds
                int diamondsCollected = score / 100;
                basketLabel.setText("  Basket:  " + diamondsCollected+"  ");
                
                collectedPointsLabel.setText("  Points:  " + diamondsCollected*100+"    ");
            
            } else {
                // If the clicked button is not the magic tool, make the diamond disappear without collecting
                button.setIcon(null);
            }
        }
    }
    
    private void changeMatrixButtonsColor(Color color) {
        for (int i = 0; i < matrixButtons.length; i++) {
            for (int j = 0; j < matrixButtons[0].length; j++) {
                matrixButtons[i][j].setBackground(color);
            }
        }
    }

    private void endGame() {
        timer.stop();
        PointsManager pointsManager = PointsManager.getInstance();
		pointsManager.addDiamondPoints(score);
        JOptionPane.showMessageDialog(this, "Congrats! You earn : " + score +" Diamond Points :)");
		this.dispose(); // Close the Diamond Game window
        switcher.showMainPanel();
        
    } }