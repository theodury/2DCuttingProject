package cutting.object;

/**
 * Created by TD on 4/13/2015.
 */
public class Item {

    private int itemId;
    private int x;
    private int y;
    private  int height;
    private  int width;

    private boolean horizontal;

    public Item(){

    }

    public Item(int y, int x, boolean horizontal) {
        this.y = y;
        this.x = x;
        this.horizontal = horizontal;
    }

}
