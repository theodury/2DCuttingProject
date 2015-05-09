package cutting.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TD on 5/8/2015.
 */
public class Solution {



    List<Pattern> listPattern = new ArrayList<Pattern>();
    private int[][] listItemWanted;

    public List<Pattern> getListPattern() {
        return listPattern;
    }

    public Solution(int[][] listItemWanted) {
        this.listItemWanted = listItemWanted;
    }

    public void generatePatterns() {
        Pattern p;
        int minimum, maximum, randNum, width, height, length;

        minimum = 0;
        maximum = listItemWanted.length;
        randNum = minimum + (int) (Math.random() * maximum);
        width = listItemWanted[randNum][0];
        height = listItemWanted[randNum][1];
        length = this.listItemWanted.length;

        while (checkSolutionTrue()) {
            p = new Pattern(length);
            while (p.addItem(randNum, width, height)) {
                listItemWanted[randNum][3] = listItemWanted[randNum][3] + 1;
                randNum = minimum + (int) (Math.random() * maximum);
                width = listItemWanted[randNum][0];
                height = listItemWanted[randNum][1];
            }
            listPattern.add(p);
        }
    }

    public void generateBestPatterns() {
        Pattern p;
        int minimum, maximum, randNum, width, height, length, tmp;
        boolean checker;

        checker = true;
        length = this.listItemWanted.length;
        p = new Pattern(length);

        for (int compteur = 0; compteur < listItemWanted.length; compteur++) {
            tmp = compteur;
            do {
                listItemWanted[compteur][3] = listItemWanted[compteur][3] + 1;
                width = listItemWanted[compteur][0];
                height = listItemWanted[compteur][1];
                if (!p.addItem(compteur, width, height)) {
                    ++compteur;
                }
                if (compteur == listItemWanted.length) {
                    checker = false;
                }
            } while (checker);

            checker = true;
            compteur = tmp;
            listPattern.add(p);
            p = new Pattern(length);
        }
    }

    public boolean checkSolutionTrue() {
        boolean result = false;
        for (int i = 0; i < listItemWanted.length; i++) {
            if (listItemWanted[i][2] > listItemWanted[i][3]) {
                result = result || true;
            }
        }
        return result;
    }


}

