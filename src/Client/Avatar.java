package Client;

import javax.swing.*;
import java.awt.*;

public class Avatar extends JLayeredPane{
    private final static int IMAGE_SIZE = 300;
    private int cat = 0;
    private int eyes = 0;
    private int mouth = 0;
    private int pattern = 0;
    private int accessory = 0;
    private int headWear = 0;
    private final JLabel catLabel = new JLabel(new ImageIcon("src/Client/Layers/Cat/0.png"));
    private final JLabel eyesLabel = new JLabel(new ImageIcon("src/Client/Layers/Eyes or eyewear/0.png"));
    private final JLabel mouthLabel = new JLabel(new ImageIcon("src/Client/Layers/Mouth/0.png"));
    private final JLabel patternLabel = new JLabel(new ImageIcon("src/Client/Layers/Pattern/0.png"));
    private final JLabel accessoryLabel = new JLabel(new ImageIcon("src/Client/Layers/Accessories/0.png"));
    private final JLabel headWearLabel = new JLabel(new ImageIcon("src/Client/Layers/Headwear/0.png"));

    public Avatar(){
        setPreferredSize(new Dimension(IMAGE_SIZE,IMAGE_SIZE));

        catLabel.setBounds(0, 0, IMAGE_SIZE, IMAGE_SIZE);
        accessoryLabel.setBounds(0, 0, IMAGE_SIZE, IMAGE_SIZE);
        eyesLabel.setBounds(0, 0, IMAGE_SIZE, IMAGE_SIZE);
        headWearLabel.setBounds(0, 0, IMAGE_SIZE, IMAGE_SIZE);
        mouthLabel.setBounds(0, 0, IMAGE_SIZE, IMAGE_SIZE);
        patternLabel.setBounds(0, 0, IMAGE_SIZE, IMAGE_SIZE);

        setLayer(catLabel, 0);
        setLayer(accessoryLabel, 1);
        setLayer(eyesLabel, 1);
        setLayer(headWearLabel, 1);
        setLayer(mouthLabel, 1);
        setLayer(patternLabel, 1);

        add(catLabel);
        add(eyesLabel);
        add(mouthLabel);
        add(patternLabel);
        add(accessoryLabel);
        add(headWearLabel);
    }

    public int getCat() {
        return cat;
    }

    public int getEyes() {
        return eyes;
    }

    public int getMouth() {
        return mouth;
    }

    public int getPattern() {
        return pattern;
    }

    public int getAccessory() {
        return accessory;
    }

    public int getHeadWear() {
        return headWear;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public void setEyes(int eyes) {
        this.eyes = eyes;
    }

    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    public void setAccessory(int accessory) {
        this.accessory = accessory;
    }

    public void setHeadWear(int headWear) {
        this.headWear = headWear;
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
        setPreferredSize(new Dimension(IMAGE_SIZE / 3,IMAGE_SIZE / 3));
        resizeLabelImage(catLabel, "src/Client/Layers/Cat/" + cat % 8 + ".png");
        resizeLabelImage(accessoryLabel, "src/Client/Layers/Accessories/" + accessory % 30 + ".png");
        resizeLabelImage(eyesLabel, "src/Client/Layers/Eyes or eyeWear/" + eyes % 26 + ".png");
        resizeLabelImage(headWearLabel, "src/Client/Layers/Headwear/" + headWear % 27 + ".png");
        resizeLabelImage(mouthLabel, "src/Client/Layers/Mouth/" + mouth % 5 + ".png");
        resizeLabelImage(patternLabel, "src/Client/Layers/Pattern/" + pattern % 4 + ".png");
        revalidateAndRepaint();
    }
    private void resizeLabelImage(JLabel label, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(IMAGE_SIZE / 3, IMAGE_SIZE / 3, Image.SCALE_DEFAULT);
        label.setBounds(125,0, IMAGE_SIZE / 3, IMAGE_SIZE / 3);
        label.setIcon(new ImageIcon(image));
    }

}