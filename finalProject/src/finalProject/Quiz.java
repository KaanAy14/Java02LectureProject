package finalProject;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Quiz implements ActionListener {
	private WindowSwitcher switcher;
	
	boolean skipQuestion = false;
	
	String[][] questions = {  
							  {"Which one is the name of the first cloned sheep in Turkey?", "Hard"},
							  {"Which company created Java?", "Easy"},
							  {"How tall is the worlds tallest tree?", "Hard"},
							  {"Which is the following is not an Prime number?", "Easy"},
							  {"Which country has the most people?","Medium"},
							  {"What was Java originally called?", "Hard"},
							  {"What country does the football player Ronaldo come from?","Easy"},
							  {"Which one is the world's first digital currency?", "Medium"},
							  {"Which of the following diseases caused Mozart's death?", "Hard"},
							  {"How many strings does a violin have?","Medium"},
							  {"Which physicist wrote a book called 'A Brief History of Time'?","Hard"},
							  {"Which year did Türkiye come 3rd in the FIFA World Cup?","Medium"},
							  {"Which is the largest planet in the solar system?","Easy"},
							  {"Who is the tallest living person in the world?","Medium"},
							  {"Which animal is not a mammal?","Easy"},
		};

	String[][] options =  {		
							   {"Kinali","Oyali","Boyali","Binali","Seftali"},
					           {"Sun Microsystems","Starbucks","Microsoft","Alphabet","Tesla"},
					           {"47 meter","115 meter","187 meter","209 meter","447 meter"},
					           {"2","13","37","51","241"},
					           {"Russia","India","China","Mexico","Spain"},
					           {"Apple","Latte","Oak","Coffee","Milk"},
					           {"Brazil","England","Spain","Portugal","Uruguay"},
					           {"Solana","Ripple","Bitcoin","Ethereum","Dogecoin"},
					           {"Cholera","Aneurysm","Malaria","Rabies","Trichinosis"},
					           {"3","4","5","6","None"},
					           {"Paul Dirac","Albert Einstein","Ernest Rutherford","Stephen Hawking","Marie Curie"},
					           {"2002","1994","2022","2014","1998"},
					           {"Mercury","Mars","Saturn","Venus","Jupiter"},
					           {"Brahim Takioullah","Vikas Uppal","Sultan Kosen","Malgorzata Dydek","Gul Mohammed"},
					           {"Monkey","Rat","Tiger","Bat","Penguin"},
            
        };

	char[] answers =      {
							  'B',
							  'A',
							  'B',
							  'D',
							  'B',
							  'C',
							  'D',					
							  'C',
							  'E',
							  'B',
							  'D',	
							  'A',
							  'E',
							  'C',
							  'E',
        };
	
	char guess;
	char answer;
	int index;
	int correct_guesses = 0;
	int total_questions = questions.length;
	int result;
	int seconds = 15;

	
	JFrame frame = new JFrame();
	JTextField textfield = new JTextField();
	JTextArea textarea = new JTextArea();
	JButton buttonA = new JButton();
	JButton buttonB = new JButton();
	JButton buttonC = new JButton();
	JButton buttonD = new JButton();
	JButton buttonE = new JButton();
	JButton skipButton = new JButton("Skip");
	JButton exitButton = new JButton("Exit");
	JLabel answer_labelA = new JLabel();
	JLabel answer_labelB = new JLabel();
	JLabel answer_labelC = new JLabel();
	JLabel answer_labelD = new JLabel();
	JLabel answer_labelE = new JLabel();
	JLabel time_label = new JLabel();
	JLabel seconds_left = new JLabel();
	JTextField number_right = new JTextField();
	JTextField collected_points = new JTextField();
	JTextField diamond_time = new JTextField();
	
	Timer timer = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			seconds--;
			seconds_left.setText(String.valueOf(seconds));
			if(seconds<=0) {
				displayAnswer();
			}
		}
	});

	private String chosenDifficulty;
	
	public Quiz(String difficulty, WindowSwitcher switcher) {
		this.switcher = switcher;
	    this.chosenDifficulty = difficulty;


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.getContentPane().setBackground(new Color(96,96,96));
		frame.setLayout(null);
		frame.setResizable(false);
		
		textfield.setBounds(0,0,800,100);
		textfield.setBackground(new Color(64,64,64));
		textfield.setForeground(new Color(255,255,0));
		textfield.setFont(new Font("Ink Free",Font.BOLD,30));
		textfield.setBorder(BorderFactory.createBevelBorder(1));
		textfield.setHorizontalAlignment(JTextField.CENTER);
		textfield.setEditable(false);
		
		textarea.setBounds(0,100,800,100);
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		textarea.setBackground(new Color(64,64,64));
		textarea.setForeground(new Color(255,255,0));
		textarea.setFont(new Font("MV Boli",Font.BOLD,25));
		textarea.setBorder(BorderFactory.createBevelBorder(1));
		textarea.setEditable(false);
		
		exitButton.setBounds(410,700, 80, 50);
		exitButton.setFont(new Font("SansSerif",Font.BOLD,19));
		exitButton.setBackground(new Color(232,232,236));
		exitButton.addActionListener(this);
		exitButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        frame.dispose(); // Close the quiz frame
		        Main mainPage = new Main(); // Create a new instance of the Main class
		    }
		});
		
		skipButton.setBounds(310, 700, 80, 50);
		skipButton.setFont(new Font("SansSerif",Font.BOLD,19));
		skipButton.setBackground(new Color(232,232,236));
		skipButton.addActionListener(this);
		skipButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        skipQuestion = true;
		        nextQuestion();
		    }
		});
		
		
		buttonA.setBounds(0,200,100,100);
		buttonA.setFont(new Font("MV Boli",Font.BOLD,35));
		buttonA.setBackground(new Color(232,232,236));
		buttonA.setForeground(new Color(0,0,0));
		buttonA.setFocusable(false);
		buttonA.addActionListener(this);
		buttonA.setText("A)");
		
		buttonB.setBounds(0,300,100,100);
		buttonB.setFont(new Font("MV Boli",Font.BOLD,35));
		buttonB.setBackground(new Color(232,232,236));
		buttonB.setForeground(new Color(0,0,0));
		buttonB.setFocusable(false);
		buttonB.addActionListener(this);
		buttonB.setText("B)");

		
		buttonC.setBounds(0,400,100,100);
		buttonC.setFont(new Font("MV Boli",Font.BOLD,35));
		buttonC.setBackground(new Color(232,232,236));
		buttonC.setForeground(new Color(0,0,0));
		buttonC.setFocusable(false);
		buttonC.addActionListener(this);
		buttonC.setText("C)");

		
		buttonD.setBounds(0,500,100,100);
		buttonD.setFont(new Font("MV Boli",Font.BOLD,35));
		buttonD.setBackground(new Color(232,232,236));
		buttonD.setForeground(new Color(0,0,0));
		buttonD.setFocusable(false);
		buttonD.addActionListener(this);
		buttonD.setText("D)");

		
		buttonE.setBounds(0,600,100,100);
		buttonE.setFont(new Font("MV Boli",Font.BOLD,35));
		buttonE.setBackground(new Color(232,232,236));
		buttonE.setForeground(new Color(0,0,0));
		buttonE.setFocusable(false);
		buttonE.addActionListener(this);
		buttonE.setText("E)");

		answer_labelA.setBounds(125,200,500,100);
		answer_labelA.setBackground(new Color(50,50,50));
		answer_labelA.setForeground(new Color(25,225,0));
		answer_labelA.setFont(new Font("MV Boli",Font.PLAIN,35));
		
		answer_labelB.setBounds(125,300,500,100);
		answer_labelB.setBackground(new Color(50,50,50));
		answer_labelB.setForeground(new Color(25,225,0));
		answer_labelB.setFont(new Font("MV Boli",Font.PLAIN,35));

		
		answer_labelC.setBounds(125,400,500,100);
		answer_labelC.setBackground(new Color(50,50,50));
		answer_labelC.setForeground(new Color(25,225,0));
		answer_labelC.setFont(new Font("MV Boli",Font.PLAIN,35));

		
		answer_labelD.setBounds(125,500,500,100);
		answer_labelD.setBackground(new Color(50,50,50));
		answer_labelD.setForeground(new Color(25,225,0));
		answer_labelD.setFont(new Font("MV Boli",Font.PLAIN,35));

		
		answer_labelE.setBounds(125,600,500,100);
		answer_labelE.setBackground(new Color(50,50,50));
		answer_labelE.setForeground(new Color(25,225,0));
		answer_labelE.setFont(new Font("MV Boli",Font.PLAIN,35));

		seconds_left.setBounds(686,663,100,100);
		seconds_left.setBackground(new Color(25,25,25));
		seconds_left.setForeground(new Color(255,0,0));
		seconds_left.setFont(new Font("Ink Free",Font.BOLD,60));
		seconds_left.setBorder(BorderFactory.createBevelBorder(1));
		seconds_left.setOpaque(true);
		seconds_left.setHorizontalAlignment(JTextField.CENTER);
		seconds_left.setText(String.valueOf(seconds));
		
		time_label.setBounds(686,640,100,25);
		time_label.setBackground(new Color(50,50,50));
		time_label.setForeground(new Color(255,0,0));
		time_label.setFont(new Font("MV Boli",Font.PLAIN,17));
		time_label.setHorizontalAlignment(JTextField.CENTER);
		time_label.setText("Timer >:D");
		
		number_right.setBounds(200,200,400,250);
		number_right.setBackground(new Color(25,25,25));
		number_right.setForeground(new Color(25,255,0));
		number_right.setFont(new Font("Ink Free",Font.BOLD,50));
		number_right.setBorder(BorderFactory.createBevelBorder(1));
		number_right.setHorizontalAlignment(JTextField.CENTER);
		number_right.setEditable(false);
		
		collected_points.setBounds(200,400,400,250);
		collected_points.setBackground(new Color(25,25,25));
		collected_points.setForeground(new Color(25,255,0));
		collected_points.setFont(new Font("Ink Free",Font.BOLD,50));
		collected_points.setBorder(BorderFactory.createBevelBorder(1));
		collected_points.setHorizontalAlignment(JTextField.CENTER);
		collected_points.setEditable(false);


		
		frame.add(time_label);
		frame.add(seconds_left);
		
		frame.add(answer_labelA);
		frame.add(answer_labelB);
		frame.add(answer_labelC);
		frame.add(answer_labelD);
		frame.add(answer_labelE);

		frame.add(buttonA);
		frame.add(buttonB);
		frame.add(buttonC);
		frame.add(buttonD);
		frame.add(buttonE);
		frame.add(skipButton);
		frame.add(exitButton);
		
		frame.add(textarea);
		frame.add(textfield);
		frame.setVisible(true);

		
		nextQuestion();
	}
	
	
	public void nextQuestion() {
	    try {
	        if (skipQuestion && index >= total_questions) {
	        	results();
	            throw new IndexOutOfBoundsException("No more questions available.");
	        } else {
	            if (index >= total_questions) {
	                results();
	            } else {
	                // Find a question with the chosen difficulty
	                while (!questions[index][1].equals(chosenDifficulty)) {
	                    index++;
	                    if (index >= total_questions) {
	                        results(); // No more questions of the chosen difficulty
	                        return;
	                    }
	                }
	                // Display the question
	                textfield.setText(chosenDifficulty + " Questions");
	                textarea.setText(questions[index][0]);
	                answer_labelA.setText(options[index][0]);
	                answer_labelB.setText(options[index][1]);
	                answer_labelC.setText(options[index][2]);
	                answer_labelD.setText(options[index][3]);
	                answer_labelE.setText(options[index][4]);
	                timer.start();
	            }
	        }
	    } catch (IndexOutOfBoundsException e) {
	        JOptionPane.showMessageDialog(frame, "No more questions available.", "Error", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace(); // or handle the error in any other way you prefer
	    }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);
		buttonE.setEnabled(false);

		if(e.getSource() == buttonA) {
			answer = 'A';
			if(answer == answers[index]) {
				correct_guesses++;
			}
		}
		if(e.getSource() == buttonB) {
			answer = 'B';
			if(answer == answers[index]) {
				correct_guesses++;
			}
		}
		if(e.getSource() == buttonC) {
			answer = 'C';
			if(answer == answers[index]) {
				correct_guesses++;
			}
		}
		if(e.getSource() == buttonD) {
			answer = 'D';
			if(answer == answers[index]) {
				correct_guesses++;
			}
		}
		if(e.getSource() == buttonE) {
			answer = 'E';
			if(answer == answers[index]) {
				correct_guesses++;
			}
		}
		displayAnswer();
		
	}
	
	public void displayAnswer() {
		timer.stop();
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);
		buttonE.setEnabled(false);
		
		
		if(answers[index] != 'A')
			answer_labelA.setForeground(new Color(255,0,0));
		if(answers[index] != 'B')
			answer_labelB.setForeground(new Color(255,0,0));
		if(answers[index] != 'C')
			answer_labelC.setForeground(new Color(255,0,0));
		if(answers[index] != 'D')
			answer_labelD.setForeground(new Color(255,0,0));
		if(answers[index] != 'E')
			answer_labelE.setForeground(new Color(255,0,0));
		
		Timer pause = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				answer_labelA.setForeground(new Color(25,255,0));
				answer_labelB.setForeground(new Color(25,225,0));
				answer_labelC.setForeground(new Color(25,225,0));
				answer_labelD.setForeground(new Color(25,225,0));
				answer_labelE.setForeground(new Color(25,225,0));
				
				answer = ' ';
				seconds = 15;
				seconds_left.setText(String.valueOf(seconds));
				buttonA.setEnabled(true);
				buttonB.setEnabled(true);
				buttonC.setEnabled(true);
				buttonD.setEnabled(true);
				buttonE.setEnabled(true);
				index++;
				nextQuestion();
			}
		});
		pause.setRepeats(false);
		pause.start();
	}
	
	private void displayResults() {
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);
		buttonE.setEnabled(false);
		
		if(chosenDifficulty == "Easy") {
		result =correct_guesses*10;
		PointsManager pointsManager = PointsManager.getInstance();
		pointsManager.addPoints(result);
		}else if(chosenDifficulty == "Medium") {
			result =correct_guesses*20;
			PointsManager pointsManager = PointsManager.getInstance();
			pointsManager.addPoints(result);
		}else if(chosenDifficulty == "Hard") {
			result =correct_guesses*30;
			PointsManager pointsManager = PointsManager.getInstance();
			pointsManager.addPoints(result);
		}
		
		textfield.setText("RESULTS!");
		textarea.setText("");
		answer_labelA.setText("");
		answer_labelB.setText("");
		answer_labelC.setText("");
		answer_labelD.setText("");
		answer_labelE.setText("");
		
		number_right.setText("True Answers :"+correct_guesses);
		collected_points.setText("Total Points :"+result);
		
		frame.add(number_right);
		frame.add(collected_points);
    }
	
	public void results() {
		 displayResults();
	}
	
}
