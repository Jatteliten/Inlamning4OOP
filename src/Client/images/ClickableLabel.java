package Client.images;

import javax.swing.*;
import java.awt.*;

public class ClickableLabel extends JLabel{
    String text;
    ImageIcon icon;

    public ClickableLabel(String text, ImageIcon icon){
        String newText = createLineBreakIfStringIsTooLong(text);
        this.text = newText;
        this.icon = icon;
        setHorizontalAlignment(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setText(newText);
        setIcon(icon);
    }

    private String createLineBreakIfStringIsTooLong(String text){
        if (text.length() > 42){
            String newText = "<html>";
            int counter = 42;
            while(true) {
                if(text.charAt(counter) == ' '){
                    newText = newText + text.substring(0, counter);
                    break;
                }
                counter--;
            }
            newText = newText + "<br>";
            newText = newText + text.substring(counter) + "</html>";
            return newText;
        }
        return text;
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y){
        if (icon != null) {
            int imageX = (getWidth() - icon.getIconWidth()) / 2;
            int imageY = (getHeight() - icon.getIconHeight()) / 2;
            return x >= imageX && x < imageX + icon.getIconWidth() && y >= imageY && y < imageY + icon.getIconHeight();
        }
        return super.contains(x, y);
    }
}
