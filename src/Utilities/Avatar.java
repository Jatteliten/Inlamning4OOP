package Utilities;

import javax.swing.*;
import java.awt.*;

public class Avatar extends JLayeredPane{
    int cat;
    int eyes;
    int mouth;
    int pattern;
    int accessory;
    int headWear;
    JLabel catLabel;
    JLabel eyesLabel;
    JLabel mouthLabel;
    JLabel patternLabel;
    JLabel accessoryLabel;
    JLabel headWearLabel;

    public Avatar(){
        setPreferredSize(new Dimension(300,300));
        this.cat = 0;
        this.eyes = 0;
        this.mouth = 0;
        this.pattern = 0;
        this.accessory = 0;
        this.headWear = 0;
        this.catLabel = new JLabel(new ImageIcon("src/Client/Layers/Cat/0.png"));
        this.eyesLabel = new JLabel(new ImageIcon("src/Client/Layers/Eyes or eyewear/0.png"));
        this.mouthLabel = new JLabel(new ImageIcon("src/Client/Layers/Mouth/0.png"));
        this.patternLabel = new JLabel(new ImageIcon("src/Client/Layers/Pattern/0.png"));
        this.accessoryLabel = new JLabel(new ImageIcon("src/Client/Layers/Accessories/0.png"));
        this.headWearLabel = new JLabel(new ImageIcon("src/Client/Layers/Headwear/0.png"));
        catLabel.setBounds(0, 0, 300, 300);
        accessoryLabel.setBounds(0, 0, 300, 300);
        eyesLabel.setBounds(0, 0, 300, 300);
        headWearLabel.setBounds(0, 0, 300, 300);
        mouthLabel.setBounds(0, 0, 300, 300);
        patternLabel.setBounds(0, 0, 300, 300);

        setLayer(catLabel, 1);
        setLayer(accessoryLabel, 2);
        setLayer(eyesLabel, 2);
        setLayer(headWearLabel, 2);
        setLayer(mouthLabel, 2);
        setLayer(patternLabel, 2);
        add(catLabel);
        add(eyesLabel);
        add(mouthLabel);
        add(patternLabel);
        add(accessoryLabel);
        add(headWearLabel);
    }

    public void changeCat(){
        cat++;
        catLabel.setIcon(new ImageIcon("src/Client/Layers/Cat/" + cat % 8 + ".png"));
        revalidateAndRepaint();
    }
    public void changeEyes(){
        eyes++;
        eyesLabel.setIcon(new ImageIcon("src/Client/Layers/Eyes or eyeWear/" + eyes % 26 + ".png"));
        revalidateAndRepaint();
    }
    public void changeMouth(){
        mouth++;
        mouthLabel.setIcon(new ImageIcon("src/Client/Layers/Mouth/" + mouth % 5 + ".png"));
        revalidateAndRepaint();
    }
    public void changePattern(){
        pattern++;
        patternLabel.setIcon(new ImageIcon("src/Client/Layers/Pattern/" + pattern % 4 + ".png"));
        revalidateAndRepaint();
    }
    public void changeAccessory(){
        accessory++;
        accessoryLabel.setIcon(new ImageIcon("src/Client/Layers/Accessories/" + accessory % 30 + ".png"));
        revalidateAndRepaint();
    }
    public void changeHeadWear(){
        headWear++;
        headWearLabel.setIcon(new ImageIcon("src/Client/Layers/Headwear/" + headWear % 27 + ".png"));
        revalidateAndRepaint();
    }

    private void revalidateAndRepaint(){
        revalidate();
        repaint();
    }

}