package cutting;

import cutting.packing.Engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws IOException {
        String nomFile = "data_test.txt";
        String pathData ="C:\\Users\\TD\\IdeaProjects\\2DCuttingProject\\Data\\"+ nomFile;
        Engine engine = new Engine(readData(pathData));
        engine.run();
    }



    public static ArrayList<String> readData (String path){
        BufferedReader br = null;
        ArrayList<String> lCurrentLines = new ArrayList<String>();
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));
            while ((sCurrentLine = br.readLine()) != null) {
                lCurrentLines.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return lCurrentLines;
    }
}
