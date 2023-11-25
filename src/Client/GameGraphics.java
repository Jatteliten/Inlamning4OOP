package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import Utilities.Category;
import Utilities.Question;

public class GameGraphics extends JFrame {
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
    private ObjectOutputStream out;

    public void setObjectOutputStream(ObjectOutputStream out) {
        this.out = out;
    }

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
        questionsPanel.setBackground(new Color(88, 168, 134));
        gamePieces.add(questionsPanel, BorderLayout.CENTER);
        setTitle();
        JButton giveUpButton = new JButton("Ge upp");
        giveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!questions.isEmpty() && counter < questions.size()) {
                    Question currentQuestion = questions.get(counter);
                    int correctAnswerIndex = 0;

                    String correctAnswer = currentQuestion.getAnswer(correctAnswerIndex).getAnswerText();


                    JOptionPane.showMessageDialog(null, "Rätt svar är: " + correctAnswer);


                    endRoundForPlayer(true, out);
                }
            }
        });
        gamePieces.add(giveUpButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void endRoundForPlayer(boolean gaveUp, ObjectOutputStream out) {
        if (gaveUp) {
            try {
                this.out.writeObject(0); // Skicka 0 poäng till motståndaren
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        waiting();
    }

    /**
     * Sets attributes for panel that displays question above answers
     */
    private void initializeQuestionDisplay() {
        question.setIcon(new ImageIcon("src/Client/images/QuestionTitle.png"));
        question.setFont(new Font("Arial", Font.BOLD, 15));
        question.setOpaque(true);
        question.setBackground(new Color(88, 168, 134));
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
        title.setBackground(new Color(121, 197, 173));
        add(title, BorderLayout.NORTH);
    }

    /**
     * Displays categories and adds mouseListeners to those categories
     */
    public void displayCategoryChoice(Category c1, Category c2, Category c3, ObjectOutputStream out) {
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(3, 1));

        ClickableLabel categoryOne = new ClickableLabel(c1.getCategoryText(), answerIcon);
        addCategoryMouseListener(categoryOne, c1, out);
        ClickableLabel categoryTwo = new ClickableLabel(c2.getCategoryText(), answerIcon);
        addCategoryMouseListener(categoryTwo, c2, out);
        ClickableLabel categoryThree = new ClickableLabel(c3.getCategoryText(), answerIcon);
        addCategoryMouseListener(categoryThree, c3, out);

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
    private void addCategoryMouseListener(ClickableLabel j, Category c, ObjectOutputStream out){
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

    public void addQuestions(Question q){
        questions.add(q);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Displays questions on the frame.
     * Changes the question text displayed and adds four answer alternatives.
     * First answer created is always correct. Shuffles the answers before displaying them.
     */
    public void displayQuestions(ArrayList<Question> ql, ObjectOutputStream out)  {
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
            addAnswerMouseListener(ql, out, j, answerOne);
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

    private void addAnswerMouseListener(ArrayList<Question> ql, ObjectOutputStream out,
                                        ClickableLabel j, ClickableLabel answerOne) {
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
                                displayQuestions(ql, out);
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
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(totalPoints.size() + 1, 2));

        JPanel yourPointsPanel = createPointsPanel("Dina poäng");
        JPanel opponentPointsPanel = createPointsPanel("Motståndarens poäng");

        questionsPanel.add(yourPointsPanel);
        questionsPanel.add(opponentPointsPanel);

        for (int i = 0; i < totalPoints.size(); i++) {
            JPanel yourPointsPanelItem = createScorePanel(i, false);
            JPanel opponentPointsPanelItem = createScorePanel(i, true);

            questionsPanel.add(yourPointsPanelItem);
            questionsPanel.add(opponentPointsPanelItem);
        }

        revalidate();
        repaint();
    }

    private JPanel createPointsPanel (String labelText){
        JLabel pointsLabel = new JLabel(labelText);
        pointsLabel.setFont(new Font("Arial", Font.BOLD, 26));

        JPanel pointsPanel = new JPanel();
        pointsPanel.setBackground(new Color(121,197,173));
        pointsPanel.setLayout(new BorderLayout());
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pointsPanel.add(pointsLabel, BorderLayout.CENTER);

        return pointsPanel;

    }
    private JPanel createScorePanel(int points, boolean isOpponent) {
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(new Color(121, 197, 173));
        scorePanel.setLayout(new BorderLayout());

        JLabel scoreLabel;
        if (isOpponent) {
            scoreLabel = new JLabel(opponentPoints.size() > points ? String.valueOf(opponentPoints.get(points)) : "?");
        } else {
            scoreLabel = new JLabel(String.valueOf(totalPoints.get(points)));
        }

        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 25));

        scorePanel.add(scoreLabel, BorderLayout.NORTH);

        return scorePanel;
    }

    public void addPointsToOpponent(int points){
        this.opponentPoints.add(points);
    }


}