package quiz;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Quiz implements ActionListener {
    //You can add Question here... Use the same pattern.
    public static String[] Questions = {"Which is not a loop in Java?",
    									"Which is a valid varible in Java?",
    									"Which is a reserved key word in Java?",
    									"Which is a method call in Java?",
                                    };
    //You can add Options here... Use the same pattern. (Add 4 options for each question - must)
    public static String[] Options = {"for","while","do while","for while",
    									"Double 33;","String apples;","int 3.1;","boolean 19;",
    									"was","if","the","loop",
    									"methodCall;","methodCall<>;","methodCall()","methodCall();",
                                    };
    //You can add Answers here... Use the same pattern.
    public static String[] Answers = {"for while",
    									"String apples;",
    									"if",
    									"methodCall();"
                                    };
    public int Qcounter = 0, Ocounter = 0;
    public int score = 0, total = Questions.length;
    public JButton playBtn, stopBtn, hintBtn, nextBtn, submitBtn;
    public JLabel questionLbl; 
    public JLabel questionLb2;
    public JLabel questionLb3;
    ButtonGroup optionsGroup;
    public JRadioButton op1,op2,op3,op4;
    public JTextField answerField, scoreField;
    public JTextArea textArea;
    public Thread th;
    public static void main(String[] args) {
        //Building the start frame
        JFrame frame = new JFrame();
        frame.setTitle("JAVA QUIZ v1.0");
        frame.setSize(375,375);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        //Components for the start frame
        JLabel lbl1 = new JLabel("Start Menu");
        lbl1.setSize(150,25);
        lbl1.setLocation(145, 20);
        lbl1.setToolTipText("Beginning of Game");
        panel.add(lbl1);
        JLabel lbl2 = new JLabel("DIRECTIONS");
        lbl2.setSize(150,25);
        lbl2.setLocation(140, 40);
        lbl2.setToolTipText("Directions to the game");
        panel.add(lbl2);
        //You can add your text here..
        JTextArea text = new JTextArea("1) Read the question\n2) Listen to mp3 while you take the quiz\n"
            	+ "3) Determine which is the best answer\n4) Select the best answer\n"
            	+ "5) Click the Hint button if you need help\n"
            	+ "6) Click the 'Submit' button\n7) See if you got the answer right!\n"
            	+ "8) Click the 'NEXT' button to continue\n"+""
            			+ "                            HAPPY LEARNING!!!");
        text.setSize(275,200);
        text.setLocation(50,70);
        text.setEditable(false);
        panel.add(text);
        JButton play = new JButton("PLAY");
        play.setSize(80,25);
        play.setLocation(140,275);
        play.setToolTipText("Start Game");
        panel.add(play);
        //When user clicks the PLAY button, it goes to next window(quiz window)
        play.addActionListener((ActionEvent event) -> {
            new Quiz().initUI();
            frame.dispose();    //the current window is closed
        });
        frame.add(panel);
        frame.setVisible(true);
    }
    JFrame main;
    //Method to build the main frame
    public void initUI(){
        //build the frame
        main = new JFrame();
        main.setTitle("Quiz");
        main.setSize(400,400);
        main.setResizable(false);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
        //build a panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        //add components to the panel
        //this is the question statement at the top
        questionLbl = new JLabel("Question 1 goes here?");
        questionLbl.setSize(400,50);
        questionLbl.setLocation(40,10);
        panel.add(questionLbl);
        /*questionLb2 = new JLabel("Music brought to you by GospelBeats.com");
        questionLb2.setSize(400,50);
        questionLb2.setLocation(40,310);
        panel.add(questionLb2);*/
        questionLb3 = new JLabel("Listen to some tunes!");
        questionLb3.setSize(400,50);
        questionLb3.setLocation(40,40);
        panel.add(questionLb3);
        
        //These are some buttons for mp3 file
        playBtn = new JButton("PLAY mp3");
        playBtn.setSize(80,30);
        playBtn.setLocation(40,80);
        panel.add(playBtn);
        playBtn.addActionListener(this);
        stopBtn = new JButton("STOP");
        stopBtn.setSize(80,30);
        stopBtn.setLocation(125,80);
        panel.add(stopBtn);
        stopBtn.addActionListener(this);
        //Hint button to show user a hint about the question
        hintBtn = new JButton("HINT");
        hintBtn.setSize(80,30);
        hintBtn.setLocation(270,80);
        panel.add(hintBtn);
        hintBtn.addActionListener(this);
        //Four option fields, Radio Buttons so that user select an answer from them
        op1 = new JRadioButton("Answer 1 goes here");
        op2 = new JRadioButton("Answer 2 goes here");
        op3 = new JRadioButton("Answer 3 goes here");
        op4 = new JRadioButton("Answer 4 goes here");
        optionsGroup = new ButtonGroup();
        optionsGroup.add(op1);
        optionsGroup.add(op2);
        optionsGroup.add(op3);
        optionsGroup.add(op4);
        op1.setSize(140,20);
        op1.setLocation(40,130);
        op1.setFont(new Font("Serif",Font.BOLD,14));
        op2.setSize(140,20);
        op2.setLocation(40,160);
        op2.setFont(new Font("Serif",Font.BOLD,14));
        op3.setSize(140,20);
        op3.setLocation(40,190);
        op3.setFont(new Font("Serif",Font.BOLD,14));
        op4.setSize(140,20);
        op4.setLocation(40,220);
        op4.setFont(new Font("Serif",Font.BOLD,14));
        panel.add(op1);
        panel.add(op2);
        panel.add(op3);
        panel.add(op4);
        //Result about the answer (whether it is correct or incorrect)
        JLabel answerLbl = new JLabel("Your Answer is ");
        answerLbl.setSize(100,20);
        answerLbl.setLocation(40,260);
        panel.add(answerLbl);
        answerField = new JTextField();
        answerField.setSize(120,20);
        answerField.setLocation(130,260);
        answerField.setEditable(false);
        panel.add(answerField);
        //Score field, to display the current score
        JLabel scoreLbl = new JLabel("Score ");
        scoreLbl.setSize(50,20);
        scoreLbl.setLocation(90,280);
        panel.add(scoreLbl);
        scoreField = new JTextField("0/"+total);    //initially its set to zero
        scoreField.setSize(120,20);
        scoreField.setLocation(130,280);
        scoreField.setFont(new Font("Serif",Font.BOLD,14));
        scoreField.setEditable(false);
        panel.add(scoreField);
        //submit button the submit the answer and to see the results
        submitBtn = new JButton("SUBMIT");
        submitBtn.setSize(80,30);
        submitBtn.setLocation(270,250);
        panel.add(submitBtn);
        submitBtn.addActionListener(this);
        //Next button to go to the next question
        nextBtn = new JButton("NEXT");
        nextBtn.setSize(80,30);
        nextBtn.setLocation(270,280);
        nextBtn.setEnabled(false);
        panel.add(nextBtn);
        nextBtn.addActionListener(this);
        textArea = new JTextArea();
        textArea.setSize(90,95);
        textArea.setLocation(270,130);
        textArea.setEditable(false);
        //Text Area to show the hint
        textArea.setText("Press the\nhint button,\nhint is displayed\nin this text field");
        textArea.setEditable(false);
        panel.add(textArea);
        //setting the fields for first run
        questionLbl.setText(Questions[Qcounter++]);
        op1.setActionCommand(Options[Ocounter]);
        op1.setText(Options[Ocounter++]);
        op2.setActionCommand(Options[Ocounter]);
        op2.setText(Options[Ocounter++]);
        op3.setActionCommand(Options[Ocounter]);
        op3.setText(Options[Ocounter++]);
        op4.setActionCommand(Options[Ocounter]);
        op4.setText(Options[Ocounter++]);
        main.add(panel);
        main.setVisible(true);
    }
    //This is the action listener method for all the buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //recording, which buttons is clicked by the user
        switch(e.getActionCommand()){
            //if he click Next Button
            case "NEXT":
                submitBtn.setEnabled(true);
                nextBtn.setEnabled(false);
                if(Qcounter==Questions.length-1){
                    try {
                        next(); //call next method to perform the action
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                            nextBtn.setText("FINISH");
                        }else{
                    try {
                        next();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case"SUBMIT":
                submit();   //call submit method to submit the answer
                break;
            case "FINISH":
                finish();   //if its the last question, call this method to perform action
                break;
            case "PLAY mp3":
                play(); //call method to play the mp3 file
                break;
            case "STOP":
                th.stop();  //built-in method to stop the mp3 file
                break;
           case "HINT":
                //display the hint for answer in the text area
                textArea.setText("It may be\n\n   "+Answers[Qcounter-1]);
                break;            
        }
    }
    //Method to go to the next question
    public void next() throws InterruptedException{
        //incrementing and setting the fields
        questionLbl.setText(Questions[Qcounter++]);
        op1.setActionCommand(Options[Ocounter]);
        op1.setText(Options[Ocounter++]);
        op2.setActionCommand(Options[Ocounter]);
        op2.setText(Options[Ocounter++]);
        op3.setActionCommand(Options[Ocounter]);
        op3.setText(Options[Ocounter++]);
        op4.setActionCommand(Options[Ocounter]);
        op4.setText(Options[Ocounter++]);
        optionsGroup.clearSelection();
        textArea.setText("Press the\nhint button,\nhint is displayed\nin this text field");
    }
    //method  to submit the answer and display the results
    public void submit(){
        
        try{
            //getting the selected radio button
            String option = optionsGroup.getSelection().getActionCommand().toString();
            if(option.equals(Answers[Qcounter-1])){
                score++;
                answerField.setText("correct");
            }
            else{
                answerField.setText("incorrect");
            }          
            scoreField.setText(score+"/"+total);
            submitBtn.setEnabled(false);
            nextBtn.setEnabled(true);
        }
        //display error message if nothing is selected
        catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Please select an answer!!");
        }        
    }
    //Method to react to last question
    public void finish(){
        scoreField.setText(score+"/"+total);
        JFrame frame = new JFrame();
        frame.setTitle("FINISH");
        frame.setSize(300,300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        //go to the last window, 
        JLabel lbl1 = new JLabel("End of Game");
        lbl1.setSize(160,25);
        lbl1.setLocation(80, 40);
        lbl1.setFont(new Font("Serif",Font.BOLD,24));
        panel.add(lbl1);
        JLabel lbl2 = new JLabel("Great Try!");
        lbl2.setSize(150,25);
        lbl2.setLocation(110, 80);
        lbl2.setFont(new Font("Serif",Font.BOLD,18));
        panel.add(lbl2);
        JButton again = new JButton("PLAY AGAIN");
        again.setSize(120,40);
        again.setLocation(90,150);
        panel.add(again);
        //action listener for the play again button
        again.addActionListener((ActionEvent event) -> {
            new Quiz().initUI();
            frame.dispose();    //dispose off the last windows
            main.dispose(); 
        });
        frame.add(panel);
        frame.setVisible(true);
    }
    //method to play the mp3 file in background
    public void play(){
        th = new Thread(new Runnable() {
                    public void run() {
                        FileInputStream fis;
                    Player playMP3;
                    try {
                        fis = new FileInputStream("I DONT CARE.mp3");  //set name of mp3 file here
                        playMP3 = new Player(fis);
                        playMP3.play(); //calling built in method to play it
                    } catch (IOException ex) {
                        Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JavaLayerException ex) {
                        Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                });
                th.start(); //starting the thread in background            
                }
    }   


