package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import Utilities.AvatarProperties;
import Utilities.Category;
import Utilities.Question;

public class GameGraphics extends JFrame {
    Avatar avatar;
    Avatar opponentAvatar = new Avatar();
    ArrayList<Integer> totalPoints = new ArrayList<>();
    ArrayList<Integer> opponentPoints = new ArrayList<>();
    Integer points = 0;
    int counter = 0;
    JLabel title = new JLabel();
    JPanel gamePieces = new JPanel();
    JPanel questionsPanel = new JPanel();
    JLabel question = new JLabel();
    ImageIcon answerIcon = new ImageIcon("src/Client/images/Question.png");
    ImageIcon wrongAnswerIcon = new ImageIcon("src/Client/images/QuestionWrongAnswer.png");
    ImageIcon correctAnswerIcon = new ImageIcon("src/Client/images/QuestionCorrectAnswer.png");
    ArrayList<Question> questions = new ArrayList<>();
    boolean timerActive = false;
    boolean textInputIsEmpty = true;
    Color lighterGreen = new Color(121, 197, 173);
    Color darkerGreen = new Color(88, 168, 134);
    int numberOfRounds;
    ObjectOutputStream out;

    GameGraphics(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,700);
        setTitle("Quizkampen");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        gamePieces.setLayout(new BorderLayout());
        add(gamePieces);
        initializeQuestionDisplay();
        questionsPanel.setOpaque(true);
        questionsPanel.setBackground(darkerGreen);
        gamePieces.add(questionsPanel, BorderLayout.CENTER);
        setTitle();

        setVisible(true);
    }

    public void addQuestions(Question q){
        questions.add(q);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Sets attributes for panel that displays question above answers
     */
    private void initializeQuestionDisplay() {
        question.setIcon(new ImageIcon("src/Client/images/QuestionTitle.png"));
        question.setFont(new Font("Arial", Font.BOLD, 15));
        question.setOpaque(true);
        question.setBackground(darkerGreen);
        question.setHorizontalAlignment(SwingConstants.CENTER);
        question.setHorizontalTextPosition(SwingConstants.CENTER);
    }

    /**
     * Sets title image
     */
    private void setTitle(){
        ImageIcon image = new ImageIcon("src/Client/images/Title.png");
        title.setIcon(image);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setOpaque(true);
        title.setBackground(lighterGreen);
        add(title, BorderLayout.NORTH);
    }

    /**
     * asks for name and avatar
     */
    public void nameAndAvatarEntry(){
        questionsPanel.setLayout(new GridBagLayout());

        avatar = new Avatar();
        opponentAvatar.shrinkImage();

        JLabel waitingForOpponent = new JLabel("Waiting...");
        waitingForOpponent.setFont(new Font("Arial", Font.BOLD, 40));

        JPanel buttons = implementButtons();

        JTextField nameEntry = implementTextField(waitingForOpponent);

        questionsPanel.add(avatar);
        questionsPanel.add(buttons);
        questionsPanel.add(nameEntry);
        revalidate();
        repaint();
    }

    /**
     * Asks player for a name.
     * Sends player avatar to server when name has been given.
     */
    private JTextField implementTextField(JLabel waitingForOpponent) {
        JTextField nameEntry = new JTextField("Enter name", 15);
        nameEntry.setFont(new Font("Arial", Font.BOLD, 20));
        nameEntry.setForeground(Color.GRAY);
        nameEntry.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(textInputIsEmpty) {
                    nameEntry.setText("");
                    nameEntry.setForeground(Color.BLACK);
                    textInputIsEmpty = false;
                }
            }
        });
        nameEntry.addActionListener(e -> {
            try {
                AvatarProperties avatarProperties = new AvatarProperties(avatar.getCat(), avatar.getEyes(), avatar.getMouth(),
                        avatar.getPattern(),avatar.getAccessory(), avatar.getHeadWear());
                out.writeObject(avatarProperties);
                out.writeObject(nameEntry.getText());
                questionsPanel.removeAll();
                questionsPanel.add(waitingForOpponent);
                avatar.shrinkImage();
                revalidate();
                repaint();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return nameEntry;
    }

    /**
     * Buttons for controlling avatar appearance
     */
    private JPanel implementButtons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(8, 1));
        buttons.setBackground(darkerGreen);
        JButton changeCat = new JButton("Cat color");
        changeCat.addActionListener(e -> avatar.changeCat(1));
        JButton changeEyes = new JButton("Eyes");
        changeEyes.addActionListener(e -> avatar.changeEyes(1));
        JButton changeMouth = new JButton("Mouth");
        changeMouth.addActionListener(e -> avatar.changeMouth(1));
        JButton changePattern = new JButton("Pattern");
        changePattern.addActionListener(e -> avatar.changePattern(1));
        JButton changeAccessory = new JButton("Accessory");
        changeAccessory.addActionListener(e -> avatar.changeAccessory(1));
        JButton changeHeadWear = new JButton("Hat");
        changeHeadWear.addActionListener(e -> avatar.changeHeadWear(1));
        JButton randomize = new JButton("Randomize");
        randomize.addActionListener(e -> avatar.randomizeCat());
        buttons.add(changeCat);
        buttons.add(changeEyes);
        buttons.add(changeMouth);
        buttons.add(changePattern);
        buttons.add(changeAccessory);
        buttons.add(changeHeadWear);
        buttons.add(randomize);
        return buttons;
    }

    /**
     * Displays categories and adds mouseListeners to those categories
     */
    public void displayCategoryChoice(Category c1, Category c2, Category c3) {
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(3, 1));

        ClickableLabel categoryOne = new ClickableLabel(c1.getCategoryText(), answerIcon);
        addCategoryMouseListener(categoryOne, c1);
        ClickableLabel categoryTwo = new ClickableLabel(c2.getCategoryText(), answerIcon);
        addCategoryMouseListener(categoryTwo, c2);
        ClickableLabel categoryThree = new ClickableLabel(c3.getCategoryText(), answerIcon);
        addCategoryMouseListener(categoryThree, c3);

        questionsPanel.add(categoryOne);
        questionsPanel.add(categoryTwo);
        questionsPanel.add(categoryThree);
        revalidate();
        repaint();
    }

    /**
     * Adds mouse listener to category label.
     * Each category sends itself to the server.
     */
    private void addCategoryMouseListener(ClickableLabel j, Category c){
        j.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    out.writeObject(c);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    /**
     * Displays questions on the frame.
     * Changes the question text displayed and adds four answer alternatives.
     * First answer created is always correct. Shuffles the answers before displaying them.
     */
    public void displayQuestions(ArrayList<Question> ql)  {
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(2, 2));
        ArrayList<ClickableLabel> answers = new ArrayList<>();

        question.setText(ql.get(counter).getQuestionText());
        gamePieces.add(question, BorderLayout.NORTH);

        ClickableLabel answerOne = new ClickableLabel(ql.get(counter).getAnswer(0).getAnswerText(), answerIcon);
        answers.add(answerOne);
        for(int i = 1; i < 4; i++){
            ClickableLabel answer = new ClickableLabel(ql.get(counter).getAnswer(i).getAnswerText(), answerIcon);
            answers.add(answer);
        }

        for (ClickableLabel j : answers) {
            addAnswerMouseListener(ql, j, answerOne);
        }

        Collections.shuffle(answers);

        for (JLabel j : answers) {
            questionsPanel.add(j);
        }
        revalidate();
        repaint();
    }

    /**
     * Adds mouse listener to answers.
     * When the correct amount of questions has been answered, moves to waiting(), which displays results.
     */

    private void addAnswerMouseListener(ArrayList<Question> ql,ClickableLabel j, ClickableLabel answerOne) {
        j.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!timerActive) {
                    timerActive = true;

                    if (e.getSource() == answerOne) {
                        points++;
                        j.setIcon(correctAnswerIcon);
                    } else {
                        j.setIcon(wrongAnswerIcon);
                    }
                    revalidate();
                    repaint();
                    Timer timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            timerActive = false;

                            counter++;
                            if (counter == ql.size()) {
                                counter = 0;
                                totalPoints.add(points);

                                gamePieces.remove(question);
                                try {
                                    waiting();
                                    out.writeObject(points);
                                    points = 0;
                                    questions.clear();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else {
                                displayQuestions(ql);
                            }

                            ((Timer) evt.getSource()).stop();
                        }
                    });

                    timer.setRepeats(false);
                    timer.start();
                }
            }
        });
    }

    /**
     * Displays results.
     */
    public void waiting() {
        System.out.println(opponentPoints.size());
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(totalPoints.size() + 3, 3));

        JPanel winLoseOrTiePanel;
        if(numberOfRounds == opponentPoints.size()) {
            winLoseOrTiePanel = createPointsPanel(winnerOrLoser());
        }else{
            winLoseOrTiePanel = createPointsPanel("");
        }
        winLoseOrTiePanel.setBackground(darkerGreen);
        questionsPanel.add(avatar);
        questionsPanel.add(winLoseOrTiePanel);
        questionsPanel.add(opponentAvatar);

        JPanel yourPointsPanel = createPointsPanel("Dina poäng");
        JPanel TotalPointsPanel = createPointsPanel(totalPlayerPoints(totalPoints) + " - " + totalPlayerPoints(opponentPoints));
        JPanel opponentPointsPanel = createPointsPanel("Motståndarens poäng");

        questionsPanel.add(yourPointsPanel);
        questionsPanel.add(TotalPointsPanel);
        questionsPanel.add(opponentPointsPanel);

        for (int i = 0; i < totalPoints.size(); i++) {
            JPanel yourPointsPanelItem = createScorePanel(i, false);
            JPanel opponentPointsPanelItem = createScorePanel(i, true);
            JPanel gameRoundPanel = createPointsPanel("Runda " + (i + 1));
            questionsPanel.add(yourPointsPanelItem);
            questionsPanel.add(gameRoundPanel);
            questionsPanel.add(opponentPointsPanelItem);
        }

        if(numberOfRounds == opponentPoints.size()){
            JButton playAgainButton = new JButton("Spela igen?");
            playAgainButton.addActionListener(e -> {
                try {
                    out.writeObject(Client.NEW_GAME_REQUEST);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            JPanel emptyPanel = createPointsPanel("");
            JButton exitButton = new JButton("Avsluta");
            exitButton.addActionListener(e -> System.exit(0));
            questionsPanel.add(playAgainButton);
            questionsPanel.add(emptyPanel);
            questionsPanel.add(exitButton);
        }

        revalidate();
        repaint();
    }

    private JPanel createPointsPanel (String labelText){
        JLabel pointsLabel = new JLabel(labelText);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 26));

        JPanel pointsPanel = new JPanel();
        pointsPanel.setBackground(lighterGreen);
        pointsPanel.setLayout(new BorderLayout());
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pointsPanel.add(pointsLabel, BorderLayout.CENTER);

        return pointsPanel;

    }
    private JPanel createScorePanel(int points, boolean isOpponent) {
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(lighterGreen);
        scorePanel.setLayout(new BorderLayout());

        JLabel scoreLabel;
        if (isOpponent) {
            scoreLabel = new JLabel(opponentPoints.size() > points ? String.valueOf(opponentPoints.get(points)) : "?");
        } else {
            scoreLabel = new JLabel(String.valueOf(totalPoints.get(points)));
        }

        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));

        scorePanel.add(scoreLabel, BorderLayout.CENTER);

        return scorePanel;
    }

    public String winnerOrLoser(){
        if (totalPlayerPoints(totalPoints) > totalPlayerPoints(opponentPoints)){
            return "Du vann!";
        } else if (totalPlayerPoints(totalPoints) == totalPlayerPoints(opponentPoints)) {
            return "Oavgjort";
        }
        return "Du förlorade";
    }

    public int totalPlayerPoints(ArrayList<Integer> playerPoints){
        int totalPoints = 0;
        for (Integer playerPoint : playerPoints) {
            totalPoints += playerPoint;
        }
        return totalPoints;
    }

    public void addPointsToOpponent(int points){
        this.opponentPoints.add(points);
    }

    public void setOpponentAvatar(Avatar opponentAvatar) {
        this.opponentAvatar = opponentAvatar;
    }
    public void clearAllPointArrays(){
        this.totalPoints.clear();
        this.opponentPoints.clear();
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }
}
