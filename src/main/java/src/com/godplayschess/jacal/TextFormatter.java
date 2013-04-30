package src.com.godplayschess.jacal;

import java.awt.*;


public class TextFormatter {

    public void writeText(int fontSize, int leftBorder, int rightBorder, int topBorder, String text, Graphics2D g) {
        g.setFont(new Font("Arial", Font.PLAIN, fontSize));

        int char_per_string = (rightBorder - leftBorder) / fontSize;
        int strings_num = text.length() / char_per_string + 1;
        for (int i = 0; i < strings_num; i++) {
            if (i != strings_num - 1) {
                g.drawString(text.substring(i * char_per_string, (i + 1) * char_per_string), leftBorder, topBorder + (int)(i * 1.5 * fontSize));
            } else g.drawString(text.substring(i * char_per_string), leftBorder, topBorder + (int)(i * 1.5 * fontSize));
        }


    }


}
