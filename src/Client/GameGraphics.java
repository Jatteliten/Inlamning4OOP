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
    JLabel title = new JLabel();
    JLabel categoryChoice;
    JLabel category;
    JPanel questionsPanel = new JPanel();
    ImageIcon icon = new ImageIcon("src/Client/images/Answer.png");
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
        categoryChoice("Kategori1", "Kategori2", "Kategori3");

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

    public void categoryChoice(String category1, String category2, String category3){
        ArrayList<JLabel> categories = new ArrayList<>();
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(3, 1));

        JLabel categoryOne = new JLabel(category1);
        JLabel categoryTwo = new JLabel(category2);
        JLabel categoryThree = new JLabel(category3);
        categories.add(categoryOne);
        categories.add(categoryTwo);
        categories.add(categoryThree);

        for(JLabel j: categories){
            j.setIcon(icon);
            j.setHorizontalTextPosition(SwingConstants.CENTER);
            j.setHorizontalAlignment(SwingConstants.CENTER);
            j.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    questions(j.getText(), "Svar1", "Svar2", "Svar3", "Svar4");
                }
            });
        }
        questionsPanel.add(categoryOne);
        questionsPanel.add(categoryTwo);
        questionsPanel.add(categoryThree);
        revalidate();
        repaint();
    }

    private void questions(String category, String question1, String question2, String question3, String question4){
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(2,2));
        ArrayList<JLabel> questions = new ArrayList<>();

        categoryChoice = new JLabel(category);
        categoryChoice.setHorizontalAlignment(SwingConstants.CENTER);
        add(categoryChoice, BorderLayout.SOUTH);

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
            j.setIcon(icon);
            j.setHorizontalTextPosition(SwingConstants.CENTER);
            j.setHorizontalAlignment(SwingConstants.CENTER);
            questionsPanel.add(j);
        }
        revalidate();
        repaint();
    }

}