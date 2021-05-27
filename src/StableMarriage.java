import java.util.List;
import java.util.Map;

public class StableMarriage {

    static String filePath;

    public static void main(String[] args) {
        //filePath = "/home/n7student/Bureau/Theorie_des_Graphes_Thomas_NADAL-Diego_RODRIGEZ/src/preferencesFile1.csv";
     //   displayFile();
/*System.out.println("\n\nWho does the bidding ?");
        System.out.println("1 : Schools");
        System.out.println("2 : Students");
        Scanner userScanner = new Scanner(System.in);
        boolean schoolsBidding = userScanner.nextInt() == 1;
        buildMatrix(schoolsBidding);*/


        // Create pairs
        Pair pair1 = new Pair(1, 2);
        Pair pair2 = new Pair(2, 3);
        Pair pair3 = new Pair(3, 1);
        Pair pair4 = new Pair(2, 1);
        Pair pair5 = new Pair(3, 1);
        Pair pair6 = new Pair(1, 2);
        Pair pair7 = new Pair(3, 3);
        Pair pair8 = new Pair(2, 2);
        Pair pair9 = new Pair(1, 3);

        // Create the matrix with schools and students
        Object[][] choicesMatrix = {{" ", "ENSEEIHT", "INSA", "POLYTECH"},{"Diego", pair1, pair2, pair3},
                {"Killian", pair4, pair5, pair6},
                {"Thomas", pair7, pair8, pair9}};

        School[] schools = getSchools(choicesMatrix);
        Student[] students = getStudents(choicesMatrix);

        Pair[][] preferencesPairs = extractRawData(choicesMatrix);

        parsePreferencesPairs(schools, students, preferencesPairs);

        putBidderIntoSchoolList(students);

        selectTheChosenOnes(schools);
    }

    // According to the max capacity, chose the students who can stay in the school at least for this turn
    private static void selectTheChosenOnes(School[] schools) {
        for (int i = 0; i < schools.length; i++) {
            List<Student> studentsList = schools[i].getStudents();
            if (studentsList.size() > schools[i].getCapacity()) {
                Student chosenOne = studentsList.get(0);
                for (Student student :
                        studentsList) {
                    if (schools[i].getPreferences().get(student) > schools[i].getPreferences().get(chosenOne)) {
                        chosenOne = student;
                    }
                }
            }
        }
    }

    // The bidder who does the bidding is put into the school list
    private static void putBidderIntoSchoolList(Student[] students) {
        for (int i = 0; i < students.length; i++) {
            Map<School, Integer> preferences = students[i].getPreferences();
            for (Map.Entry<School, Integer> mapEntry : preferences.entrySet()) {
                if (mapEntry.getValue().equals(students[i].getActualPreference())) {
                    mapEntry.getKey().addStudent(students[i]);
                }
            }
        }
    }

    // Parse the raw pair matrix and associate the schools and students preferences
    private static void parsePreferencesPairs(School[] schools, Student[] students, Pair[][] preferencesPairs) {
        for (int i = 0; i < preferencesPairs.length; i++) {
            for (int j = 0; j < preferencesPairs.length; j++) {
                students[i].addPreference(schools[j], preferencesPairs[i][j].getFirst());
                schools[j].addPreference(students[i], preferencesPairs[i][j].getSecond());
            }
        }
    }

    // Build schools based on the school names
    private static School[] getSchools(Object[][] choicesMatrix) {
        School[] schools = new School[choicesMatrix.length - 1];
        String[] schoolsName = retrieveSchoolsNames(choicesMatrix);
        for (int i = 1; i < schoolsName.length; i++) {
            School school = new School(schoolsName[i]);
            schools[i-1] = school;
        }
        return schools;
    }

    // Build students based on the students names
    private static Student[] getStudents(Object[][] choicesMatrix) {
        Student[] students = new Student[choicesMatrix.length - 1];
        String[] studentsName = retrieveStudentsNames(choicesMatrix);
        for (int i = 1; i < studentsName.length; i++) {
            Student student = new Student(studentsName[i]);
            students[i-1] = student;
        }
        return students;
    }

    // Retrieve the schools names from the matrix
    public static String[] retrieveSchoolsNames(Object[][] matrix) {
        String[] line = new String[matrix[0].length];
        for (int i = 0; i < line.length; i ++) {
            line[i] = matrix[0][i].toString();
        }
        return line;
    }

    // Retrieve the students names from the matrix
    public static String[] retrieveStudentsNames(Object[][] matrix) {
        String[] column = new String[matrix[0].length];
        for (int i = 0; i < column.length; i ++) {
            column[i] = matrix[i][0].toString();
        }
        return column;
    }

    // Extract a Pair array without the school and students names
    public static Pair[][] extractRawData(Object[][] matrix) {
        Pair[][] arrayWithoutTitles = new Pair[matrix.length-1][matrix.length-1];
        for (int i = 1; i < matrix.length; i ++) {
            for (int j = 1; j < matrix.length; j++) {
                arrayWithoutTitles[i-1][j-1] = (Pair) matrix[i][j];
            }
        }
        return arrayWithoutTitles;
    }


    // Methode de test (a voir si on l'utilise comme ça)
    // Checks if the school prefer the first (current engagment) over the second
    public static boolean whichPrefers(School school, Student first, Student second) {
        // If first comes before m in list of w, then w prefers her fcurrent engagement
        for (Student student :
                school.getStudents()) {
            Map<Student, Integer> preferences = school.getPreferences();
            if (preferences.containsKey(first)) {
                if (preferences.containsKey(second)) {
                    return preferences.get(first) < preferences.get(second);
                }
                return true;
            } else {
                return preferences.containsKey(second);
            }
        }
        return true;
    }
}

/*
public static void displayFile() {
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

        } catch (Exception e) {
        }
        String[][] matrix = new String[rowList.size()][];
        for (int i = 0; i < rowList.size(); i++) {
            String[] row = rowList.get(i);
            matrix[i] = row;
        }




        try {
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter(";");
            while (scanner.hasNext())
            {
                System.out.print(scanner.next() + "    ");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void buildMatrix(boolean schoolsBidding) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            scanner.useDelimiter(";");
            parseFile(scanner, schoolsBidding);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void parseFile(Scanner scanner, boolean schoolsBidding) {
        String[] schools = {};
        String[] students = {};

        while (scanner.hasNext()) {
            System.out.println(scanner.next() + "    ");
            //Pair pair = new Pair();

        }
    }
 */
