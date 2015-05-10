package cutting.gui;

import cutting.packing.Solution;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by TD on 5/6/2015.
 */

public class GUI extends JFrame{

    private javax.swing.JPanel rootPanel;
    private JSlider sliderPattern;
    private JPanel pattern;
    private JLabel jLabelPattern;
    private cutting.gui.Rect rect;
    private Solution s;
    private int valuePattern;

    public GUI(final Solution s){

        this.s=s;

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new DimensionUIResource(1050, 525));
        sliderPattern.setMinimum(0);
        sliderPattern.setMaximum(s.getListPattern().size() - 1);
        sliderPattern.setValue(0);
        valuePattern = 0;
        jLabelPattern.setText(" Pattern : " + valuePattern);

        pattern.setBackground(Color.getColor("3C3F41"));
        sliderPattern.setBackground(Color.getColor("3C3F41"));

        sliderPattern.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                valuePattern = ((JSlider) e.getSource()).getValue();
                jLabelPattern.setText(" Pattern : " +
                        valuePattern);
                rect = new Rect(s, valuePattern);
                rect.paint(pattern.getGraphics());

            }
        });
        addKeyListener(new CustomKeyListener());
        setFocusable(true);


        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rect = new Rect(s, 0);
    }


    //Move pattern
    class CustomKeyListener implements KeyListener{
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_RIGHT ){
                sliderPattern.setValue(sliderPattern.getValue() + 1);
            }
            if(e.getKeyCode() == KeyEvent.VK_LEFT ) {
                sliderPattern.setValue(sliderPattern.getValue() - 1);
            }
        }
        public void keyReleased(KeyEvent e) {
        }
    }









}
