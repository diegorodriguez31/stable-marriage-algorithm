import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StableMarriage {

    static String filePath;

    public static void main(String[] args) {
        filePath = "/home/n7student/Bureau/Theorie_des_Graphes_Thomas_NADAL-Diego_RODRIGEZ/src/preferencesFile1.csv";
        displayFile();

        /*System.out.println("\n\nWho does the bidding ?");
        System.out.println("1 : Schools");
        System.out.println("2 : Students");
        Scanner userScanner = new Scanner(System.in);
        boolean schoolsBidding = userScanner.nextInt() == 1;

        buildMatrix(schoolsBidding);*/

    }

    public static void displayFile(){
        List<String[]> rowList = new ArrayList<String[]>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineItems = line.split(";");
                rowList.add(lineItems);

            }

            // récupère la première ligne
            System.out.println("première ligne\n");
            for (String s : rowList.get(0)) {
                System.out.println(s);
            }

            // récupère la taille de la première ligne
            System.out.println("\ntaille ligne 1\n");
            System.out.println(rowList.get(0).length);

            // recupère la première colone
            System.out.println("\ntaille colonne 1\n");
            System.out.println(lineItems[0]);
            for (String s : rowList.get(0)) {
                for (String s : rowList.get(0)) {
                    System.out.println(s);
                }
            }

            // taille colone 1
            System.out.println("\ntaille colonne 1\n");
            System.out.println();

        }
        catch(Exception e){
        }
        /*String[][] matrix = new String[rowList.size()][];
        for (int i = 0; i < rowList.size(); i++) {
            String[] row = rowList.get(i);
            matrix[i] = row;
        }*/




        /*try {
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter(";");
            while (scanner.hasNext())
            {
                System.out.print(scanner.next() + "    ");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public static void buildMatrix(boolean schoolsBidding){
        try {
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter(";");
            parseFile(scanner, schoolsBidding);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void parseFile(Scanner scanner, boolean schoolsBidding){



        String[] schools = {};
        String[] students = {};

        while (scanner.hasNext())
        {
            System.out.println(scanner.next() + "    ");



            //Pair pair = new Pair();


        }
    }
}
