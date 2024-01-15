package finalProject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements WindowSwitcher {
    private static JFrame initialFrame;
    private String chosenDifficulty;
    private JLabel pointsLabel;
    private JLabel diamondPointsLabel;

    private PointsManager pointsManager;

    public Main() {
        pointsManager = PointsManager.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        initialFrame = new JFrame("Q&D Game");
        initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialFrame.setSize(800, 800);
        initialFrame.setLocationRelativeTo(null);

        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBackground(new Color(173, 216, 230));

        pointsLabel = new JLabel("Quiz Points: " + pointsManager.getTotalPoints());
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel diamondLabel = new JLabel("Diamond Points:");
        diamondLabel.setFont(new Font("Arial", Font.BOLD, 28));

        diamondPointsLabel = new JLabel("" + pointsManager.getDiamondPoints());
        diamondPointsLabel.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(173, 216, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 150); // Set insets for space between labels

        topPanel.add(pointsLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 100, 0, 0); // Set insets for space between labels
        topPanel.add(diamondLabel, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 0, 0); // Reset insets for the final label
        topPanel.add(diamondPointsLabel, gbc);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        initialFrame.setContentPane(mainPanel);
        initialFrame.setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createVerticalStrut(230));

        JButton playQButton = createButton("Start Quiz Game", e -> startQuiz());
        JButton playDButton = createButton("Start Diamond Game", e -> startDiamondGame());
        JButton howToPlayButton = createButton("How to Play", e -> showInstructions());
        JButton exitButton = createButton("Exit", e -> initialFrame.dispose());

        addComponentWithSpace(buttonPanel, playQButton);
        addComponentWithSpace(buttonPanel, playDButton);
        addComponentWithSpace(buttonPanel, howToPlayButton);
        addComponentWithSpace(buttonPanel, exitButton);

        return buttonPanel;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("SansSerif", Font.BOLD, 25));
        button.setBackground(new Color(232, 232, 236));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action);
        return button;
    }

    private void addComponentWithSpace(Container container, Component component) {
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        container.add(component);
    }

    private void startQuiz() {
        chosenDifficulty = (String) JOptionPane.showInputDialog(initialFrame,
                "Choose a difficulty level:", "Difficulty Selection",
                JOptionPane.QUESTION_MESSAGE, null, new String[]{"Easy", "Medium", "Hard"}, "Easy");
        if (chosenDifficulty != null && !chosenDifficulty.isEmpty()) {
            initialFrame.setVisible(false); // Hide the main frame
            Quiz quiz = new Quiz(chosenDifficulty,this);
            // Perform necessary actions with the quiz instance
        }
    }

    private void startDiamondGame() {
        initialFrame.setVisible(false); // Hide the main frame
        DiamondGame diamondGame = new DiamondGame(6, 10,this);
        diamondGame.setVisible(true);
          
    }
    
    @Override
    public void showMainPanel() {
    	pointsLabel.setText("Total Points: " + pointsManager.getTotalPoints()); // Update total points label
        diamondPointsLabel.setText(""+pointsManager.getDiamondPoints()); // Update diamond points label
        initialFrame.setVisible(true);
    }

    private void showInstructions() {
        JOptionPane.showMessageDialog(initialFrame, "Welcome to the Q&D Game!\n\n"
                + "Instructions:\n"
                + "1. Click the 'Start Quiz Game' button to begin.\n"
                + "2. Choose a difficulty level and try to solve the questions.\n"
                + "3. Use 'Skip' to move to the next question.\n"
                + "4. After finished Quiz Game you will see your points.\n"
                + "5. You will have time for Diamond Game according to collected points in Quiz Game.\n\n"
                + "Tip: More points = More time");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}