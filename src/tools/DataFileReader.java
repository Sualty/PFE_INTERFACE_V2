package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by user on 20/02/17.
 */
public class DataFileReader {
    private String path_file;

    public DataFileReader(String path_file) {
        this.path_file = path_file;
    }

    public String[][] getData () throws IOException {
        /* Le premier BufferedReader pour compter le nb de lignes */
        BufferedReader reader1 = new BufferedReader(new FileReader(path_file));
        int nbLines = 0;

        while (reader1.readLine() != null) nbLines++;
        reader1.close();

		/* Définir le format du tableau */
        String[] columnNames = {"Time","roll","pitch","yaw","accel_x","accel_y","accel_z","posx","posy","posz"};
        String[][] data = new String[nbLines][columnNames.length];

		/* Le deuxième BufferedReader pour lire les données du fichier log */
        BufferedReader reader2 = new BufferedReader(new FileReader(path_file));
        int indexLine = 0;
        String thisLine;

        while ((thisLine = reader2.readLine()) != null) {
            if (indexLine != 0) {
                String[] dataOfLine = thisLine.split(",");

                for (int i=0; i<dataOfLine.length; i++) {
                    data[indexLine][i] = dataOfLine[i];
                }
            }
            indexLine++;
        }
        reader2.close();

        return data;
    }
}