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
public class Solution {



    List<Pattern> listPattern = new ArrayList<Pattern>();
    private int[][] listItemWanted;
    private double fitness;
    private int[] nbPrintPattern;


    public int[] getNbPrintPattern() {
        return nbPrintPattern;
    }

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


    public double calculFitness()
    {


        // describe the optimization problem : x1 + x2 + x3 + xn
        //où xi est le nombre d'impression du pattern i
        int nbPattern = this.listPattern.size();
        this.nbPrintPattern = new int[nbPattern];

        double[] coefficients = new double[nbPattern];
        for (int i=0; i<nbPattern ; ++i)
            coefficients[i] = 1; //init

        LinearObjectiveFunction f = new LinearObjectiveFunction(coefficients,
                (double)this.listPattern.size()* Engine.price);

        //constraints selon les quantités à distribuer
        Collection<LinearConstraint> constraints = new ArrayList<>();

        for (int i=0; i<listItemWanted.length;i++)
        {
            double[] coefs = new double[nbPattern];

            for (int j=0; j < nbPattern; j++)
            {
                coefs[j] = listPattern.get(j).getListItemWanted()[i][0];
            }

            constraints.add(new LinearConstraint(coefs , Relationship.GEQ, listItemWanted[i][2] ));
        }


        // create and run the solver
        SimplexSolver solver = new SimplexSolver();

        try {
            PointValuePair optSolution = solver.optimize(new MaxIter(9999), f,
                    new LinearConstraintSet(constraints),
                    GoalType.MINIMIZE,
                    new NonNegativeConstraint(true)
            );

            fitness = optSolution.getValue();

            double[] solution = optSolution.getPoint();
            for (int i=0; i<solution.length; ++i)
            {
                int s = (int) Math.ceil(solution[i]); //prendre la valeur superieur

                if (s == 0)
                    fitness -= Engine.price;

                nbPrintPattern[i] = s;
            }


        } catch (NoFeasibleSolutionException ex) {
            fitness = Double.MAX_VALUE;
        }

        return fitness;
    }

}

