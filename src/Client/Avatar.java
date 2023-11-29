package Client;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Avatar extends JLayeredPane{
    private final static int IMAGE_SIZE = 300;
    private int cat = 0;
    private static final int AMOUNT_OF_CATS = 8;
    private int eyes = 0;
    private static final int AMOUNT_OF_EYES = 26;
    private int mouth = 0;
    private static final int AMOUNT_OF_MOUTHS = 5;
    private int pattern = 0;
    private static final int AMOUNT_OF_PATTERNS = 4;
    private int accessory = 0;
    private static final int AMOUNT_OF_ACCESSORIES = 30;
    private int headWear = 0;
    private final JLabel catLabel = new JLabel(new ImageIcon("src/Client/CatLayers/Cat/0.png"));
    private final JLabel eyesLabel = new JLabel(new ImageIcon("src/Client/CatLayers/Eyes or eyewear/0.png"));
    private final JLabel mouthLabel = new JLabel(new ImageIcon("src/Client/CatLayers/Mouth/0.png"));
    private final JLabel patternLabel = new JLabel(new ImageIcon("src/Client/CatLayers/Pattern/0.png"));
    private final JLabel accessoryLabel = new JLabel(new ImageIcon("src/Client/CatLayers/Accessories/0.png"));
    private final JLabel headWearLabel = new JLabel(new ImageIcon("src/Client/CatLayers/Headwear/0.png"));

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

    public void changeCat(int increment){
        cat += increment;
        catLabel.setIcon(new ImageIcon("src/Client/CatLayers/Cat/" +
                cat % AMOUNT_OF_CATS + ".png"));
        revalidateAndRepaint();
    }
    public void changeEyes(int increment){
        eyes += increment;
        eyesLabel.setIcon(new ImageIcon("src/Client/CatLayers/Eyes or eyeWear/" +
                eyes % AMOUNT_OF_EYES + ".png"));
        revalidateAndRepaint();
    }
    public void changeMouth(int increment){
        mouth += increment;
        mouthLabel.setIcon(new ImageIcon("src/Client/CatLayers/Mouth/" +
                mouth % AMOUNT_OF_MOUTHS + ".png"));
        revalidateAndRepaint();
    }
    public void changePattern(int increment){
        pattern += increment;
        patternLabel.setIcon(new ImageIcon("src/Client/CatLayers/Pattern/" +
                pattern % AMOUNT_OF_PATTERNS + ".png"));
        revalidateAndRepaint();
    }
    public void changeAccessory(int increment){
        accessory += increment;
        accessoryLabel.setIcon(new ImageIcon("src/Client/CatLayers/Accessories/" +
                accessory % AMOUNT_OF_ACCESSORIES + ".png"));
        revalidateAndRepaint();
    }
    public void changeHeadWear(int increment){
        headWear += increment;
        headWearLabel.setIcon(new ImageIcon("src/Client/CatLayers/Headwear/" +
                headWear % AMOUNT_OF_ACCESSORIES + ".png"));
        revalidateAndRepaint();
    }
    public void randomizeCat(){
        Random rand = new Random();
        cat = rand.nextInt(AMOUNT_OF_CATS);
        eyes = rand.nextInt(AMOUNT_OF_EYES);
        mouth = rand.nextInt(AMOUNT_OF_MOUTHS);
        pattern = rand.nextInt(AMOUNT_OF_PATTERNS);
        accessory = rand.nextInt(AMOUNT_OF_ACCESSORIES);
        int AMOUNT_OF_HEAD_WEAR = 27;
        headWear = rand.nextInt(AMOUNT_OF_HEAD_WEAR);
        changeCat(0);
        changeEyes(0);
        changeMouth(0);
        changePattern(0);
        changeAccessory(0);
        changeHeadWear(0);
        revalidateAndRepaint();
    }

    private void revalidateAndRepaint(){
        revalidate();
        repaint();
    }


    public void shrinkImage() {
        setPreferredSize(new Dimension(IMAGE_SIZE / 3,IMAGE_SIZE / 3));
        resizeLabelImage(catLabel, "src/Client/CatLayers/Cat/" + cat % 8 + ".png");
        resizeLabelImage(accessoryLabel, "src/Client/CatLayers/Accessories/" + accessory % 30 + ".png");
        resizeLabelImage(eyesLabel, "src/Client/CatLayers/Eyes or eyeWear/" + eyes % 26 + ".png");
        resizeLabelImage(headWearLabel, "src/Client/CatLayers/Headwear/" + headWear % 27 + ".png");
        resizeLabelImage(mouthLabel, "src/Client/CatLayers/Mouth/" + mouth % 5 + ".png");
        resizeLabelImage(patternLabel, "src/Client/CatLayers/Pattern/" + pattern % 4 + ".png");
        revalidateAndRepaint();
    }
    private void resizeLabelImage(JLabel label, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(IMAGE_SIZE / 3, IMAGE_SIZE / 3, Image.SCALE_DEFAULT);
        label.setBounds(67,0, IMAGE_SIZE / 3, IMAGE_SIZE / 3);
        label.setIcon(new ImageIcon(image));
    }

}