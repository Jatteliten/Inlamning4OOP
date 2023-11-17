package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class GameGraphics extends JFrame {
    static final int CORRECT_ANSWER = 1;
    static final int WRONG_ANSWER = 2;
    int answer;
    JLabel title = new JLabel("Quizkampen");
    JLabel categoryChoice;
    JLabel category;
    JPanel questionsPanel = new JPanel();
    GameGraphics(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        setLocationRelativeTo(null);
        categoryChoice("Hej1", "Hej2", "Hej3");
        add(questionsPanel);

        setVisible(true);
    }

    public void categoryChoice(String category1, String category2, String category3){
        ArrayList<JLabel> categories = new ArrayList<>();
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(3, 1));

        JLabel categoryOne = new JLabel("hej1");
        JLabel categoryTwo = new JLabel("hej2");
        JLabel categoryThree = new JLabel("hej3");
        categories.add(categoryOne);
        categories.add(categoryTwo);
        categories.add(categoryThree);

        for(JLabel j: categories){
            j.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    questions("Hej", "Hej2", "Hej3", "Hej4");
                }
            });
        }

        questionsPanel.add(categoryOne);
        questionsPanel.add(categoryTwo);
        questionsPanel.add(categoryThree);
        repaint();
    }

    private void questions(String question1, String question2, String question3, String question4){
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(2,2));
        ArrayList<JLabel> questions = new ArrayList<>();

        JLabel questionOne = new JLabel(question1);
        questionOne.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                answer = CORRECT_ANSWER;
            }
        });

        JLabel questionTwo = new JLabel(question2);
        JLabel questionThree = new JLabel(question3);
        JLabel questionFour = new JLabel(question4);
        questions.add(questionTwo);
        questions.add(questionThree);
        questions.add(questionFour);

        for(JLabel j: questions){
            j.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    answer = WRONG_ANSWER;
                }
            });
        }

        questions.add(questionOne);
        Collections.shuffle(questions);

        for(JLabel j: questions){
            questionsPanel.add(j);
        }
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new GameGraphics();
    }

}