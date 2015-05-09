
/*
package cutting.object;

import com.sun.istack.internal.Nullable;

import java.awt.*;

/**
 * Created by TD on 5/1/2015.
 /
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

    public Node getFirstNode() {
        return FirstNode;
    }

    public void setFirstNode(Node nodeFirst) {
        FirstNode = nodeFirst;
    }

    public Node getSecondNode() {
        return SecondNode;
    }

    public void setSecondNode(Node nodeSecond) {
        SecondNode = nodeSecond;
    }
//endregion

    public Node(Rectangle rec) {
        this.rc = rec;
        this.setNewNode();
        actif = true;
    }

    public Node() {
        this.rc = null;
        actif = true;
    }

    public void setNewNode() {
        this.FirstNode = new Node();
        this.SecondNode = new Node();
    }

    @Nullable
    public boolean checkEmtpyNodeFirst() {
        try {
            if (this.getFirstNode().rc == null) return true;
        } catch (Exception e) {
            System.out.print(e.toString());
            return true;
        }
        return false;
    }

    @Nullable
    public boolean checkEmtpyNodeSecond() {
        if (this.getSecondNode().rc == null) return true;
        return false;
    }


    public String toString(int count) {
        String display;
        String tmp = "";
        for (int i = 0; i < count + 1; ++i) {
            tmp += "\t";
        }

        display = " height : " + rc.getHeight() + " width : " + rc.getWidth() + " etat : " + actif + "\n";
        if (!checkEmtpyNodeFirst()) display += tmp + this.getFirstNode().toString(++count);
        if (!checkEmtpyNodeSecond()) display += tmp + this.getSecondNode().toString(count);
        return display;
    }


    public Node addItem(int width, int height) {

        float DW;
        float DH;
        Node result;

        if (!this.checkEmtpyNodeFirst() && !this.checkEmtpyNodeSecond()) {
            result = getFirstNode().addItem(width, height);
            if(result != null) return result;
            return getSecondNode().addItem(width,height);
        }
        else {

            //  if there's already a item here, return
            if (actif) return null;

            //if we're too small, return
            if (width > getRc().width || height >getRc().height) return null;

            //if we're just right, accept
            if(width == getRc().width && height == getRc().height) return result;

            this.FirstNode = new Node();
            this.SecondNode = new Node();

            //(otherwise, gotta split this node and create some kids)

        }
        float coef;
        // calcule du coefficien

        if (this.getRc().width > this.getRc().height) coef = height / width;
        else coef = width / height;

        DW = this.getRc().width - width * coef;
        DH = this.getRc().height - height * coef;

        this.getFirstNode().setNewNode();
        this.getSecondNode().setNewNode();

        if (DW > DH) {
            this.getFirstNode().setRc(new Rectangle(this.getRc().width - width, this.getRc().height));
            this.getSecondNode().setRc(new Rectangle(width, this.getRc().height - height));
        } else {
            this.getFirstNode().setRc(new Rectangle(this.getRc().width - width, height));
            this.getSecondNode().setRc(new Rectangle(this.getRc().width, this.getRc().height - height));
        }
        return this;
    }
}

    public boolean setActifNode(int level, int objectif) {
        boolean statFirst;
        boolean statSecond;

        if (level == objectif) {
            if (this.getRc().getHeight() < 0 || this.getRc().getWidth() < 0) {
                this.actif = false;
            }
        } else {
            ++level;
            statFirst = this.getFirstNode().setActifNode(level, objectif);
            statSecond = this.getSecondNode().setActifNode(level, objectif);
            if (statFirst == false) {
                this.getSecondNode().actif = false;
            }
            if (statSecond == false) {
                this.getFirstNode().actif = false;
            }
        }
        return this.actif;
    }


    public double checkActif(int level, int objectif, double result) {

        if (level == objectif) {
            if (this.actif == false) {
                --result;
            }
        } else {
            ++level;
            result = this.getFirstNode().checkActif(level, objectif, result);
            result = this.getSecondNode().checkActif(level, objectif, result);
        }
        return result;
    }


    public int[] getCord(int level, int objectif) {
        int[] result = new int[2];
        if (this.actif) {
            if (level == objectif) {
                if (this.getFirstNode().actif) {
                    result[0] =  1400 - this.getRc().width;
                    result[1] = 700 - this.getRc().height;
                }
                if (this.getSecondNode().actif) {
                    result[0] = 1400 - this.getRc().width;
                    result[1] = 700 - this.getRc().height;
                }
            } else {
                ++level;
                if (this.getFirstNode().actif) result = this.getFirstNode().getCord(level, objectif);
                if (this.getSecondNode().actif) result = this.getSecondNode().getCord(level, objectif);
            }
        }
        return result;
    }

    public Node clone(Node result) {

        if (!checkEmtpyNodeFirst())
            result.setFirstNode(this.getFirstNode().clone(new Node(this.getFirstNode().getRc())));
        if (!checkEmtpyNodeSecond())
            result.setSecondNode(this.getSecondNode().clone(new Node(this.getSecondNode().getRc())));
        return result;
    }


    public boolean bestPathNode() {

        boolean actifFirst;
        boolean actifSecond;
        //node = this;

        if (!checkEmtpyNodeFirst()) {
            actifFirst = getFirstNode().bestPathNode();
            if (!checkEmtpyNodeSecond()) {
                actifSecond = getSecondNode().bestPathNode();
                if (actifFirst == false && actifSecond == false) {
                    actif = false;
                }
            }
        }
        return actif;
    }



/*
    public Node addItem(int width, int height) {

        float DW;
        float DH;

        if (!this.checkEmtpyNodeFirst() && !this.checkEmtpyNodeSecond()) {


            this.getFirstNode().addItem(width, height);
            this.getSecondNode().addItem(width, height);
            return this;


        } else {
            float coef;
            // calcule du coefficien

            if (this.getRc().width > this.getRc().height) coef = height / width;
            else coef = width / height;

            DW = this.getRc().width - width * coef;
            DH = this.getRc().height - height * coef;

            this.getFirstNode().setNewNode();
            this.getSecondNode().setNewNode();

            if (DW > DH) {
                this.getFirstNode().setRc(new Rectangle(this.getRc().width - width, this.getRc().height));
                this.getSecondNode().setRc(new Rectangle(width, this.getRc().height - height));
            } else {
                this.getFirstNode().setRc(new Rectangle(this.getRc().width - width, height));
                this.getSecondNode().setRc(new Rectangle(this.getRc().width, this.getRc().height - height));
            }
            return this;
        }
    }
}

*/




//Pattern
/*
 public boolean addItem(int randNum, int width, int height ){

        Node nodetmp;
        boolean result;

        nodetmp = this.getNode().clone(new Node(this.getNode().getRc()));
        this.listItem[numberCurrentItem][2] += width;
        this.listItem[numberCurrentItem][3] += height;
        ++numberCurrentItem;

        this.getNode().addItem(width, height);
        this.getNode().setActifNode(0, numberCurrentItem);


        // Si on peux insere la piece on l insere
        // et on retourne true
        // sinon on reviens a l etat précedent et on renvoie false
        if ( this.getNode().checkActif(0, numberCurrentItem, Math.pow(2, numberCurrentItem)) != 0) {
            this.listItemWanted[randNum][0] += 1;
            result = true;
        }
        else {
            this.setNode(nodetmp);
            --numberCurrentItem;
            this.getNode().setActifNode(0, numberCurrentItem);
            this.getNode().bestPathNode();
            this.setPositionItem();
            result = false;
        }
        return result;
    }

 */