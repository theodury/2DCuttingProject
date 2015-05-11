package cutting.packing;

import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by TD on 5/8/2015.
 */


public class Solution implements Comparable<Solution>{ //pour le tri


    List<Pattern> listPattern = new ArrayList<Pattern>();
    public int[][] listItem; //passé en public pour des raisons pratiques
    private double fitness;
    private double propFitness; //bestFitness proportionnelle au reste de la population
    private double accFitness; // bestFitness proportionnelle accumulée
    private int[] nbPrintPattern;


    //tri vu sur http://www.mkyong.com/java/java-object-sorting-example-comparable-and-comparator/

    @Override
    public int compareTo(Solution sol) { // il faut lui déterminer sur quel attribut on se base pour le tri
        int compareFitness = (int) ((Solution) sol).getPropFitness();
        return (int) (this.propFitness - compareFitness);
    }

    public int[][] getListItemWanted() {
        return listItem;
    }

    public void setListItemWanted(int[][] listItemWanted) {
        this.listItem = listItemWanted;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getPropFitness() {
        return propFitness;
    }

    public void setPropFitness(double propFitness) {
        this.propFitness = propFitness;
    }

    public double getAccFitness() {
        return accFitness;
    }

    public void setAccFitness(double accFitness) {
        this.accFitness = accFitness;
    }

    public int[] getNbPrintPattern() {
        return nbPrintPattern;
    }

    public List<Pattern> getListPattern() {
        return listPattern;
    }

    public Solution(int[][] listItemWanted) {
        this.listItem = listItemWanted;

    }

    public void generatePatterns() {
        Pattern p;
        int width, height, length, tmp;
        boolean checker;

        checker = true;
        length = this.listItem.length;
        p = new Pattern(length);

        for (int compteur = 0; compteur < listItem.length; compteur++) {
            tmp = compteur;
            do {
                listItem[compteur][3] = listItem[compteur][3] + 1;
                width = listItem[compteur][0];
                height = listItem[compteur][1];
                if (!p.addItem(compteur, width, height)) {
                    ++compteur;
                }
                if (compteur == listItem.length) {
                    checker = false;
                }
            } while (checker);

            checker = true;
            compteur = tmp;
            listPattern.add(p);
            // maybe sort listItem

            p = new Pattern(length);
        }
    }


    public double calculFitness()
    {
        // describe the optimization problem : x1 + x2 + x3 + xn
        // xi est le nombre d'impression du pattern i
        int nbPattern = this.listPattern.size();
        this.nbPrintPattern = new int[nbPattern];

        double[] coefficients = new double[nbPattern];
        for (int i=0; i<nbPattern ; ++i)
            coefficients[i] = 1; //init

        LinearObjectiveFunction f = new LinearObjectiveFunction(coefficients,
                (double)this.listPattern.size()* Engine.pricePattern);

        //constraints selon les quantites  distribuer
        Collection<LinearConstraint> constraints = new ArrayList<>();

        for (int i=0; i<listItem.length;i++)
        {
            double[] coefs = new double[nbPattern];

            for (int j=0; j < nbPattern; j++)
            {
                coefs[j] = listPattern.get(j).getListItemWanted()[i][0];
            }

            constraints.add(new LinearConstraint(coefs , Relationship.GEQ, listItem[i][2] ));
        }


        // create and run the solver
        SimplexSolver solver = new SimplexSolver();

        try {
            PointValuePair optSolution = solver.optimize(new MaxIter(9999), f,
                    new LinearConstraintSet(constraints),
                    GoalType.MINIMIZE,
                    new NonNegativeConstraint(true)
            );

         //   fitness = optSolution.getValue();
            fitness = 0;

            double[] solution = optSolution.getPoint();
            for (int i=0; i<solution.length; i++)
            {
                //prendre la valeur superieur
                int s = (int) Math.ceil(solution[i]);

                if (0 != s) {
                    fitness += Engine.pricePattern;
                    fitness += solution[i] * Engine.priceSheet;
                }

                nbPrintPattern[i] = s;
            }


        } catch (NoFeasibleSolutionException ex) {
            fitness = Double.MAX_VALUE;
        }

        return fitness;
    }


    /*
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
        return (listItem[i][0] * listItem[i][1] * listItem[i][2]) - (listItem[i][0] * listItem[i][1] * listItem[i][3]);
    }
*/
}