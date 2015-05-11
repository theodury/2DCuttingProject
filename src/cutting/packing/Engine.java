package cutting.packing;


import cutting.genetic.Genetic;
import cutting.gui.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TD on 4/13/2015.
 */


public class Engine {


    private List<Solution> currentSolution = new ArrayList<Solution>();

    private Solution bestSolution;
    public List<Solution> listSolutions = new ArrayList<Solution>();
    // [0] width, [1] height, [2] number, [3] reel number
    private int[][] listItemWanted;

    private double surfaceTotal;
    private int LX;
    private int LY;
    public double bestFitness;

    public static int pricePattern = 20;
    public static int priceSheet = 1;
    public static int population = 50;
    public static Rectangle sizePattern;




    public Engine(ArrayList<String>  data){

        this.listItemWanted = new int[data.size()-3][4];
        this.loadData(data);

   //     this.numberPattern =  Math.rint(this.surfaceTotal / (this.LY * this.LX));
        sizePattern = new Rectangle(0,0,LX, LY);

    }

    public void run(){

        generateSolution();

        Genetic g = new Genetic();
        g.evolution(listSolutions, 1.5);


        bestSolution = findBestSolution();
        bestFitness = bestSolution.getFitness();
        displaySolution(bestSolution);
        draw(bestSolution);

    }



    public void generateSolution(){

        for (int i = 0; i < Engine.population; i++) {
            int[][] listItem = getRandomList();
            this.sort(listItem);
            Solution s = new Solution(listItem);
            s.generatePatterns();
            s.calculFitness();
            this.listSolutions.add(s);
        }
    }

    public void sort(int[][] listItem) {
        int length = listItem.length;
        int k;
        for (int c = length; c >= 0; c--) {
            for (int i = 0; i < length - 1; i++) {
                k = i + 1;
                if (getArea(i, listItem) < getArea(k, listItem)) {
                    swapNumbers(i, k, listItem);
                }
            }
        }
    }

    private void swapNumbers(int i, int j, int [][] listItem) {
        int [] temp;
        temp = listItem [i];
        listItem [i] = listItem[j];
        listItem[j] = temp;
    }


    private int getArea(int i, int [][] listItem){
        return listItem[i][0] * listItem[i][1] * listItem[i][2];
    }


    public Solution findBestSolution(){
        double tmp = 0;
        Solution result = new Solution(null);
        for (Solution s : listSolutions) {
            if(tmp < s.getFitness()){
                tmp = s.getFitness();
                result = s;
            }
        }
        return result;
    }

    public int [][] getRandomList(){
        int [][] result = new int[listItemWanted.length][4];
        for(int i = 0; i<listItemWanted.length; i++) {
            int minimum, maximum, randNum;
            minimum = 0;
            maximum = listItemWanted[i][2];
            randNum = minimum + (int) (Math.random() * maximum);
            result[i][0] = listItemWanted[i][0];
            result[i][1] = listItemWanted[i][1];
           // result[i][2] = listItemWanted[i][2] + randNum;
            result[i][2] = listItemWanted[i][2];
            result[i][3] = listItemWanted[i][3];
        }
        return  result;
    }




    private void loadData(ArrayList<String> data){
        int c=0;
        for(String line : data){
            if(line.toLowerCase().startsWith("lx=")) this.LX = Integer.parseInt(line.substring(3));
            else if(line.toLowerCase().startsWith("ly=")) this.LY = Integer.parseInt(line.substring(3));
            else if(line.toLowerCase().startsWith("m="));
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
        System.out.println(" cout : " + bestFitness);
        System.out.print("\n");
        System.out.println("____ Fin Display ____");
        System.out.print("\n");
    }


    private void draw(Solution s){
        GUI myGUI = new GUI(s);
    }
}
