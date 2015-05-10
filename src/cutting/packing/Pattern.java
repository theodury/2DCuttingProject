package cutting.packing;

import java.awt.*;

/**
 * Created by TD on 4/13/2015.
 */
public class Pattern {

    private int [][] listItem;
    // [0] x [1] y [2] width [3] height [4] rand
    private double [][] listItemWanted;
    private Node node;
    public int LX = 1400;
    public int LY = 700;
    public static int settingItem = 5;
    public static int numberItem = 99;
    public int numberCurrentItem;
    public Color[] cols;


    public int getNumberCurrentItem() {
        return numberCurrentItem;
    }

    public int[][] getListItem() {
        return listItem;
    }

    public double[][] getListItemWanted() {
        return listItemWanted;
    }

    //region ACCESSEUR
    public Node getNode() {
        return node;
    }

    public Pattern(int length) {
        //this.listItem = listItem;
        this.node = new Node(Engine.sizePattern);
        this.numberCurrentItem = 0;
        this.listItem = new int[numberItem][settingItem];
        this.listItemWanted = new double[length][1];
        this.cols = new Color[length];

        for(int i=0; i<numberItem;i++){
            for(int j=0; j<settingItem; j++){
                this.listItem[i][j]=0;
            }
        }
        for(int i=0; i<length;i++){
            listItemWanted[i][0] = 0;
        }
    }

    public boolean addItem(int rand, int width, int height ){

        boolean result;
        Node nodetmp ;

        result = false;
        nodetmp = this.getNode().addItem(width, height);

        if( nodetmp != null) {
            result = true ;
            listItemWanted[rand][0] += 1;
            this.listItem[numberCurrentItem][0] = nodetmp.getRc().x;
            this.listItem[numberCurrentItem][1] = nodetmp.getRc().y;
            this.listItem[numberCurrentItem][2] = (int) nodetmp.getRc().getWidth();
            this.listItem[numberCurrentItem][3] = (int) nodetmp.getRc().getHeight();
            // just for color
            this.listItem[numberCurrentItem][4] = rand ;
            ++numberCurrentItem;
        }
        return result;
    }

    public int calculateListItems(){
        return 42;
    }


}
