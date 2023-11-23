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
import Utilities.Answers;

public class GameGraphics extends JFrame {
    ArrayList<Integer> totalPoints = new ArrayList<>();
    ArrayList<Integer> opponentPoints = new ArrayList<>();
    Integer points = 0;
    int counter = 0;
    JLabel title = new JLabel();
    JPanel questionsPanel = new JPanel();
    ImageIcon icon = new ImageIcon("src/Client/images/Answer.png");
    ArrayList<Question> questions = new ArrayList<>();

    GameGraphics(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,700);
        setTitle("Quizkampen");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        questionsPanel.setOpaque(true);
        questionsPanel.setBackground(new Color(88, 168, 134));
        add(questionsPanel);
        setTitle();

        setVisible(true);
    }

    private void setTitle(){
        ImageIcon image = new ImageIcon("src/Client/images/Title.png");
        title.setIcon(image);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setOpaque(true);
        title.setBackground(new Color(121, 197, 173));
        add(title, BorderLayout.NORTH);
    }

    public void categoryChoice(Category c1, Category c2, Category c3, ObjectOutputStream out) {
        ArrayList<JLabel> categories = new ArrayList<>();
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(3, 1));

        JLabel categoryOne = new JLabel(c1.getCategoryText());
        addMouseListener(categoryOne, c1, out);
        JLabel categoryTwo = new JLabel(c2.getCategoryText());
        addMouseListener(categoryTwo, c2, out);
        JLabel categoryThree = new JLabel(c3.getCategoryText());
        addMouseListener(categoryThree, c3, out);

        categories.add(categoryOne);
        categories.add(categoryTwo);
        categories.add(categoryThree);

        for(JLabel j: categories){
            j.setIcon(icon);
            j.setHorizontalTextPosition(SwingConstants.CENTER);
            j.setHorizontalAlignment(SwingConstants.CENTER);
        }
        questionsPanel.add(categoryOne);
        questionsPanel.add(categoryTwo);
        questionsPanel.add(categoryThree);
        revalidate();
        repaint();
    }

    private void addMouseListener(JLabel j, Category c, ObjectOutputStream out){
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

    public void questions(ArrayList<Question> ql, ObjectOutputStream out)  {
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(2, 2));
        ArrayList<JLabel> answers = new ArrayList<>();

        JLabel answerOne = new JLabel(ql.get(counter).getAnswer(0).getAnswerText());
        answers.add(answerOne);
        for(int i = 1; i < 4; i++){
            JLabel answer = new JLabel(ql.get(counter).getAnswer(i).getAnswerText());
            answers.add(answer);
        }

        for (JLabel j : answers) {
            j.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getSource() == answerOne) {
                        points++;
                    }
                    counter++;
                    if(counter == ql.size()){
                        counter = 0;
                        totalPoints.add(points);
                        try {
                            waiting();

                            // Skriv ut poängen innan du skickar till servern
                            System.out.println("Sending points to server: " + points);

                            out.writeObject(points);
                            points = 0;
                            questions.clear();
                            System.out.println("mina poäng");
                            for (int i = 0; i < totalPoints.size(); i++) {
                                System.out.println(totalPoints.get(i));
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else{
                        questions(ql, out);
                    }
                }
            });
        }

        Collections.shuffle(answers);

        for (JLabel j : answers) {
            j.setIcon(icon);
            j.setHorizontalTextPosition(SwingConstants.CENTER);
            j.setHorizontalAlignment(SwingConstants.CENTER);
            questionsPanel.add(j);
        }
        revalidate();
        repaint();
    }

    public void waiting(){
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(totalPoints.size(), 2));
        for(int i = 0; i < totalPoints.size(); i++){
            JLabel yourPoints = new JLabel(String.valueOf(totalPoints.get(i)));
            yourPoints.setHorizontalTextPosition(SwingConstants.CENTER);
            yourPoints.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel theirPoints;
            if(opponentPoints.size() > i) {
                theirPoints = new JLabel(String.valueOf(opponentPoints.get(i)));
            }else{
                theirPoints = new JLabel("?");
            }
            theirPoints.setHorizontalTextPosition(SwingConstants.CENTER);
            theirPoints.setHorizontalAlignment(SwingConstants.CENTER);
            questionsPanel.add(yourPoints);
            questionsPanel.add(theirPoints);
        }
        revalidate();
        repaint();
    }
    public void addPointsToOpponent(int points){
        this.opponentPoints.add(points);
    }

}