package cutting.packing;


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

    public static int price;
    public static Rectangle sizePattern;
    public double fitness;


    public Engine(ArrayList<String>  data){

        this.listItemWanted = new int[data.size()-3][4];
        this.loadData(data);
        this.sort();
        this.numberPattern =  Math.rint(this.surfaceTotal / (this.LY * this.LX));
        sizePattern = new Rectangle(0,0,LX, LY);


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
            else if(line.toLowerCase().startsWith("m=")) this.price = Integer.parseInt(line.substring(2));
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

        s.generateBestPatterns();
        fitness = s.calculFitness();
        displaySolution(s);
        draw(s);

    }

    private void displaySolution(Solution s){
        System.out.print("\n");
        System.out.println("____ Debut Display cutting.packing.Solution ____");
        System.out.print("\n");
        for(int i=0;i<s.getListPattern().size();i++){
            System.out.print(" {  ");
            for (int j = 0 ; j<s.getListPattern().get(i).getListItemWanted().length ; j++){
                System.out.print(s.getListPattern().get(i).getListItemWanted()[j][0] + "   " );
            }
            System.out.println("} " +  " : " + s.getNbPrintPattern()[i]);
        }
        System.out.print("\n");
        System.out.println(" cout : " + fitness);
        System.out.print("\n");
        System.out.println("____ Fin Display ____");
        System.out.print("\n");
    }


    private void draw(Solution s){
        GUI myGUI = new GUI(s);
    }
}
