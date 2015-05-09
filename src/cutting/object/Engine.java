package cutting.object;


import cutting.gui.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TD on 4/13/2015.
 */


public class Engine {


    private List<Solution> currentSolution = new ArrayList<Solution>();

    private Solution bestSolution;
    private List<Solution> populationSolutions = new ArrayList<Solution>();
    private int[][] listItemWanted;
    private double surfaceTotal;
    private double numberPattern;
    private int gapNumberPattern;
    private int LX;
    private int LY;
    private int m;
    public static Rectangle sizePattern;


    public Engine(ArrayList<String>  data){
        this.listItemWanted = new int[data.size()-3][4];
        this.loadData(data);
        this.sort();
        this.numberPattern =  Math.rint(this.surfaceTotal / (this.LY * this.LX));
        sizePattern = new Rectangle(0,0,LX, LY);

/*

        private List<IndexedRectangle> generatedRectangles;
        List<IndexedRectangle> randomRectangles = new ArrayList<IndexedRectangle>();
        IndexedRectangle r = new IndexedRectangle(width, height);

        Algorithm selectedAlgorithm = packingSettingsPanel.getSelectedAlgorithm();
        List<IndexedRectangle> rectangles = Packer.pack(generatedRectangles, selectedAlgorithm, BIN_WIDTH);
        */
    }

    public void sort() {
        int length = listItemWanted.length;
        int k;
        for (int c = length; c >= 0; c--) {
            for (int i = 0; i < length - 1; i++) {
                k = i + 1;
                if (getArea(i) < getArea(k)) {
                    swapNumbers(i, k);
                }
            }
        }
    }

    private void swapNumbers(int i, int j) {
        int [] temp;
        temp = listItemWanted [i];
        listItemWanted [i] = listItemWanted[j];
        listItemWanted[j] = temp;
    }


    private int getArea(int i){
        return this.listItemWanted[i][0] * this.listItemWanted[i][1];
    }

    private void loadData(ArrayList<String> data){
        int c=0;
        for(String line : data){
            if(line.toLowerCase().startsWith("lx=")) this.LX = Integer.parseInt(line.substring(3));
            else if(line.toLowerCase().startsWith("ly=")) this.LY = Integer.parseInt(line.substring(3));
            else if(line.toLowerCase().startsWith("m=")) this.m = Integer.parseInt(line.substring(2));
            else {
                String elements[] = line.split("\\s+");
                int width =  (int)Double.parseDouble(elements[0]);
                int  height = (int)Double.parseDouble(elements[1]);
                int number = (int)Double.parseDouble(elements[2]);
                this.listItemWanted[c][0] = width;
                this.listItemWanted[c][1] = height;
                this.listItemWanted[c][2] = number;
                this.listItemWanted[c][3] = 0;
                c++;
                this.surfaceTotal += width*height*number;
            }
        }
    }

    public void run(){
        gapNumberPattern = 3;
        Random r = new Random();
        Solution s = new Solution(this.listItemWanted);

        //s.generatePatterns();
        s.generateBestPatterns();
        displaySolution(s);
        draw(s);
      //  System.out.print(s.listPattern.get(0).getNode().toString(0));

    }

    private void displaySolution(Solution s){
        System.out.print("\n");
        System.out.println("____ Debut Display cutting.object.Solution ____");
        System.out.print("\n");
        for(int i=0;i<s.getListPattern().size();i++){
            System.out.print(" {  ");
            for (int j = 0 ; j<s.getListPattern().get(i).getListItemWanted().length ; j++){
                System.out.print(s.getListPattern().get(i).getListItemWanted()[j][0] + "  ");
            }
            System.out.print("} ");
        }
        System.out.print("\n\n");
        System.out.println("____ Fin Display ____");
        System.out.print("\n");
    }


    private void draw(Solution s){

        GUI myGUI = new GUI(s);
    }
}
