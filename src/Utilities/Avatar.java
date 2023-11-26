package Utilities;

import javax.swing.*;
import java.awt.*;

public class Avatar extends JLayeredPane{
    int cat = 0;
    int eyes = 0;
    int mouth = 0;
    int pattern = 0;
    int accessory= 0;
    int headWear = 0;
    JLabel catLabel = new JLabel(new ImageIcon("src/Client/Layers/Cat/0.png"));
    JLabel eyesLabel = new JLabel(new ImageIcon("src/Client/Layers/Eyes or eyewear/0.png"));
    JLabel mouthLabel = new JLabel(new ImageIcon("src/Client/Layers/Mouth/0.png"));
    JLabel patternLabel = new JLabel(new ImageIcon("src/Client/Layers/Pattern/0.png"));
    JLabel accessoryLabel = new JLabel(new ImageIcon("src/Client/Layers/Accessories/0.png"));
    JLabel headWearLabel = new JLabel(new ImageIcon("src/Client/Layers/Headwear/0.png"));

    public Avatar(){
        setPreferredSize(new Dimension(300,300));
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

    public void shrinkImage() {
        setPreferredSize(new Dimension(100,100));
        resizeImageForLabel(catLabel, "src/Client/Layers/Cat/" + cat % 8 + ".png");
        resizeImageForLabel(accessoryLabel, "src/Client/Layers/Accessories/" + accessory % 30 + ".png");
        resizeImageForLabel(eyesLabel, "src/Client/Layers/Eyes or eyeWear/" + eyes % 26 + ".png");
        resizeImageForLabel(headWearLabel, "src/Client/Layers/Headwear/" + headWear % 27 + ".png");
        resizeImageForLabel(mouthLabel, "src/Client/Layers/Mouth/" + mouth % 5 + ".png");
        resizeImageForLabel(patternLabel, "src/Client/Layers/Pattern/" + pattern % 4 + ".png");
        revalidateAndRepaint();
    }
    private void resizeImageForLabel(JLabel label, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        label.setBounds(125,0, 100, 100);
        label.setIcon(new ImageIcon(image));
    }

}