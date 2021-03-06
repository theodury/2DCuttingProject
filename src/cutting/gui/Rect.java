package cutting.gui;

import cutting.packing.Solution;

import javax.swing.*;
import java.awt.*;

/**
 * Created by TD on 5/7/2015.
 */
public class Rect extends JComponent {

    private int[][] listItem;
    private int numberTotalItem;
    private int numberItem;
    private JSlider sliderPattern;

    public Rect(Solution s, int numberCurrentPattern) {
        super();
        this.listItem = s.getListPattern().get(numberCurrentPattern).getListItem();
        this.numberItem = s.getListPattern().get(numberCurrentPattern).getNumberCurrentItem();


    }

    public void paint(Graphics g) {

        int x, y, width, height;
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 700, 350);


        Color[] cols = new Color[numberItem];
        for (int i = 0; i < numberItem; i++) {
            cols[i] = Color.getHSBColor(((float) i / ((float) listItem[i][4] + 5)) , 0.15f, 0.35f);
        }
        for (
                int i = 0;
                i < numberItem; i++)

        {
            g.setColor(cols[i]);
            x = listItem[i][0] / 2;
            y = listItem[i][1] / 2;
            width = listItem[i][2] / 2;
            height = listItem[i][3] / 2;
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.drawString("P" + listItem[i][4], (x + 15), (y + 15));

        }
    }


    public void bla() {
        System.out.print("bla");
    }
}

