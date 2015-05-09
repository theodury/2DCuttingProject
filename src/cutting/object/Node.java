package cutting.object;

import com.sun.istack.internal.Nullable;

import java.awt.*;

/**
 * Created by TD on 5/1/2015.
 */
public class Node {

    Node FirstNode;
    Node SecondNode;
    Rectangle rc;
    boolean actif;
    int itemId;

    //region Getter-Setter
    public Rectangle getRc() {
        return rc;
    }

    public void setRc(Rectangle rc) {
        this.rc = rc;
    }


//endregion

    public Node(Rectangle rec) {
        this.rc = rec;
    }

    public Node() {
        this.rc = null;
    }

    @Nullable
    public boolean checkEmtpyNodeFirst() {
        try {
            if (this.FirstNode.getRc() == null) return true;
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    @Nullable
    public boolean checkEmtpyNodeSecond() {
        try {
            if (this.SecondNode.getRc() == null) return true;
        }
        catch (Exception e){
            return true;
        }
        return false;
    }


    public String toString(int count) {
        String display;
        String tmp = "";
        for (int i = 0; i < count + 1; ++i) {
            tmp += "\t";
        }

        display = " height : " + rc.getHeight() + " width : " + rc.getWidth() + " etat : " + actif + "\n";
        if (!checkEmtpyNodeFirst()) display += tmp + this.FirstNode.toString(++count);
        if (!checkEmtpyNodeSecond()) display += tmp + this.SecondNode.toString(count);
        return display;
    }


    public Node addItem(int width, int height) {

        float DW;
        float DH;
        Node result;

        if (!this.checkEmtpyNodeFirst() && !this.checkEmtpyNodeSecond() ) {
            result = FirstNode.addItem(width, height);
            if (result != null) return result;
            return SecondNode.addItem(width, height);
        } else {

            //  if there's already a item here, return
            if (actif) return null;

            //if we're too small, return
            if (width > getRc().width || height > getRc().height) return null;

            //if we're just right, accept
            if (width == getRc().width && height == getRc().height) {
                actif = true;
                return this;
            }

            //(otherwise, gotta split this node and create some kids)
            this.FirstNode = new Node();
            this.SecondNode = new Node();

            DW = this.getRc().width - width;
            DH = this.getRc().height - height;

            this.FirstNode = new Node();
            this.SecondNode = new Node();

            if (DW > DH) {

                this.FirstNode.setRc(new Rectangle(getRc().x, getRc().y, width, getRc().height));
                this.SecondNode.setRc(new Rectangle(getRc().x + width, getRc().y, getRc().width - width, getRc().height));
            } else {
                this.FirstNode.setRc(new Rectangle(getRc().x,getRc().y, getRc().width, height));
                this.SecondNode.setRc(new Rectangle(getRc().x, getRc().y+ height, getRc().width, getRc().height - height ));
            }

            return this.FirstNode.addItem(width, height);
        }

    }

}

