package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class GameGraphics extends JFrame {
    Integer points;
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
        categoryOne.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    out.writeObject(c1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JLabel categoryTwo = new JLabel(c2.getCategoryText());
        categoryTwo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    out.writeObject(c2);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JLabel categoryThree = new JLabel(c3.getCategoryText());
        categoryTwo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    out.writeObject(c3);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
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

    public void questions(Question q, ObjectOutputStream out){
        questionsPanel.removeAll();
        questionsPanel.setLayout(new GridLayout(2,2));
        ArrayList<JLabel> answers = new ArrayList<>();

        JLabel answerOne = new JLabel(q.getAnswer(0).getAnswerText());
        JLabel answerTwo = new JLabel(q.getAnswer(1).getAnswerText());
        JLabel answerThree = new JLabel(q.getAnswer(2).getAnswerText());
        JLabel answerFour = new JLabel(q.getAnswer(3).getAnswerText());
        answers.add(answerOne);
        answers.add(answerTwo);
        answers.add(answerThree);
        answers.add(answerFour);

        for(JLabel j: answers){
            j.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getSource() == answerOne){
                        points++;
                    }
                    try {
                        out.writeObject(points);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        Collections.shuffle(answers);

        for(JLabel j: answers){
            j.setIcon(icon);
            j.setHorizontalTextPosition(SwingConstants.CENTER);
            j.setHorizontalAlignment(SwingConstants.CENTER);
            questionsPanel.add(j);
        }
        revalidate();
        repaint();
    }

}