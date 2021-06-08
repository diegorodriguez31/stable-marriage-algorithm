import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class StableMarriage {

    static String filePath;
    // Variable to count the number of rounds the algorithm do
    static int nbRounds = 0;
    static HashMap<String, Integer> schoolsCapacities;
    static Object[][] schoolsCapacitiesTab;

    public static void main(String[] args) {
        Scanner userScanner = new Scanner(System.in);
        System.out.println("\nWhich file do you want to use ?");
        System.out.println("1) 3 students and 3 schools - school capacity of 1");
        System.out.println("2) 3 students and 4 schools - variable schools capacities");
        System.out.println("3) 16 students and 3 schools - variable schools capacities - as much students as total capacity");
        System.out.println("4) 16 students and 3 schools - variable schools capacities - more students than total capacity");
        int fileUsed = userScanner.nextInt();
        filePath = "preferencesFile" + fileUsed + ".csv";

        System.out.println("\nWho does the bidding ?");
        System.out.println("1) Students");
        System.out.println("2) Schools");
        boolean studentsAreBidding = userScanner.nextInt() == 1;
        System.out.println("\n\n\n");

        schoolsCapacities = new HashMap<>();
        // Matrix of the schools and students choices according to the CSV File
        Object[][] choicesMatrix = parseCSVFile(filePath);
        // Get the schools from this matrix
        School[] schools = getSchools(choicesMatrix);
        // Get the students from this matrix
        Student[] students = getStudents(choicesMatrix);
        // Create a matrix with only the preferences pairs of each school and student
        Pair[][] preferencesPairs = extractRawData(choicesMatrix);
        // Parse this new matrix to assign the values into the school and student objets
        parsePreferencesPairs(schools, students, preferencesPairs);

        // Whether the students or the school do the bidding, we don't use the same method
       if (studentsAreBidding) {
            applyAlgorithmWithStudentsDoingTheBidding(students, schools);
        } else {
            applyAlgorithmWithSchoolsDoingTheBidding(students, schools);
        }
    }

    /**
     * Parse the given CSV file
     * @param filePath the CSV file path
     * @return a matrix of objects containing all the file information
     */
    private static Object[][] parseCSVFile(String filePath) {
        Object[][] choicesMatrix = {{}};

        int nbColumns = 0;
        int nbRows = 0;

        List<List<String>> records = new ArrayList<>();
        try {
            // Read the file
            InputStream in = StableMarriage.class.getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            int i = 0;
            // Get the students and schools names and capacities
            while ((line = br.readLine()) != null) {
                if (i == 0) {
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                } else {
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                    nbColumns = values.length;
                }
                i++;
            }
            nbRows = i;

            choicesMatrix = new Object[nbRows][nbColumns];

            i = 0;
            InputStream in2 = StableMarriage.class.getResourceAsStream(filePath);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
            while ((line = br2.readLine()) != null) {
                if (i == 0) {
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                    choicesMatrix[0] = values;

                    // The 4 loops are used to re-size because the capacities shift the matrix
                    List<String> a = new ArrayList<>();
                    for (int k = 1; k < values.length; k = k + 2) {
                        a.add(values[k]);
                    }

                    schoolsCapacitiesTab = new Object[(values.length-1)/2][2];
                    for (int k = 0; k < a.size(); k++) {
                        schoolsCapacitiesTab[k][0] = a.get(k);
                    }

                    for (int k = 1; k < values.length; k = k + 2) {
                        schoolsCapacities.put(values[k], Integer.valueOf(values[k+1]));
                    }

                    for (int k = 0; k < schoolsCapacitiesTab.length; k++) {
                        schoolsCapacitiesTab[k][1] = Integer.valueOf(schoolsCapacities.get(schoolsCapacitiesTab[k][0]));
                    }

                } else {
                    // Fill the choices matrix with the pairs
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                    choicesMatrix[i][0] = values[0];
                    for (int j = 1; j < nbColumns; j++) {
                        String[] parts = values[j].split(",");
                        String part1 = parts[0];
                        String part2 = parts[1];
                        Pair pair = new Pair(Integer.parseInt(part1), Integer.parseInt(part2));
                        choicesMatrix[i][j] = pair;
                    }
                }
                i++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return choicesMatrix;
    }

    /**
     * Checks if we can stop the algorithm or not
     * @param schools the schools that choice among the bidders
     * @return true if we can stop the algorithm and false if not
     */
    private static boolean marriageIsStable(School[] schools){
        for (School school : schools) {
            if (!school.checkCapacity()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if we can stop the algorithm or not
     * @param students the students that choice among the bidders
     * @return true if we can stop the algorithm and false if not
     */
    private static boolean marriageIsStable(Student[] students){
        for (Student student : students) {
            if (!student.checkCapacity()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Applies the students bidding. Each free student choose the school he wants to have
     * according to his actual preference
     * @param students the students
     * @param schools the schools
     */
    private static void applyAlgorithmWithStudentsDoingTheBidding(Student[] students, School[] schools) {
        // Applies the students bidding
        studentsBiddingSchools(students);
        nbRounds = 1;
        while (!marriageIsStable(schools)) {
            // Select the wanted students and return a list of free students
            List<Student> studentsRemaining = selectTheWantedSudents(schools);
            Student[] remainingStudents = new Student[students.length];
            int j = 0;
            // Use the list to fill the remaining students
            for (Student student : studentsRemaining) {
                remainingStudents[j] = student;
                j++;
            }
            // Applies the students bidding
            studentsBiddingSchools(remainingStudents);
            nbRounds++;
        }
        // Display the algorithm result
        displayResult(students);
    }

    /**
     * Applies the schools bidding. Each free school choose the student she wants to have
     * according to her actual preference
     * @param students the students
     * @param schools the schools
     */
    private static void applyAlgorithmWithSchoolsDoingTheBidding(Student[] students, School[] schools) {
        // Applies the schools bidding
        schoolsBiddingStudents(schools);
        nbRounds = 1;
        while (!marriageIsStable(students)) {
            // Select the wanted schools and return a list of free schools
            List<School> schoolsRemaining = selectTheWantedSchool(students);
            School[] remainingSchools = new School[schools.length];
            int j = 0;
            // Use the list to fill the remaining schools
            for (School school : schoolsRemaining) {
                remainingSchools[j] = school;
                j++;
            }
            // Applies the schools bidding
            schoolsBiddingStudents(remainingSchools);
            nbRounds++;
        }
        // Display the algorithm result
        displayResult(schools);
    }

    /**
     * Each free student chooses the school he wants to have according to his actual preference
     * @param students the students
     */
    private static void studentsBiddingSchools(Student[] students) {
        for (int i = 0; i < students.length; i++) {
            if (students[i] != null) {
                Map<School, Integer> preferences = students[i].getPreferences();
                for (Map.Entry<School, Integer> mapEntry : preferences.entrySet()) {
                    if (mapEntry.getValue().equals(students[i].getActualPreference())) {
                        mapEntry.getKey().addStudent(students[i]);
                        students[i].setSchool(mapEntry.getKey());
                        break;
                    }
                }
            }
        }
    }

    /**
     * Each free school chooses the student she wants to have according to her actual preference
     * @param schools the schools
     */
    private static void schoolsBiddingStudents(School[] schools) {
        for (int i = 0; i < schools.length; i++) {
            if (schools[i] != null) {
                Map<Student, Integer> preferences = schools[i].getPreferences();
                for (Map.Entry<Student, Integer> mapEntry : preferences.entrySet()) {
                    if (mapEntry.getValue().equals(schools[i].getActualPreference())) {
                        mapEntry.getKey().getInterestedSchools().add(schools[i]);
                        schools[i].addStudent(mapEntry.getKey());
                        break;
                    }
                }
            }
        }
    }

    /**
     * The schools choose the students they want to keep according to their preferences and capacity
     * @param schools the schools
     * @return a student list representing the free students that have been ejected
     */
    private static List<Student> selectTheWantedSudents(School[] schools) {
        List<Student> remainingStudents = new ArrayList<>();
        for (int i = 0; i < schools.length; i++) {
            List<Student> studentsList = schools[i].getStudents();
            if (studentsList.size() > schools[i].getCapacity()) {
                Map<Student, Integer> schoolPreferences = schools[i].getPreferences();
                List<Integer> preferencesOrder = new ArrayList<>(schoolPreferences.values());
                Collections.sort(preferencesOrder);

                // Sort the map with the preferences order
                Map<Student, Integer> map = sortByValue(schoolPreferences);

                List<Student> tmpList = new ArrayList<>();
                // Sort the list. This list needs to have the same order as the map
                for (Map.Entry mapEntry : map.entrySet()) {
                    Student tmpStudent = (Student) mapEntry.getKey();
                    for (Student student : schools[i].getStudents()) {
                        if (student.getName().equals(tmpStudent.getName())) {
                            tmpList.add(student);
                            break;
                        }
                    }
                }

                // Remove the students that can't be accepted
                int actualCapacity = 1;
                for (Student student : tmpList) {
                    if (actualCapacity <= schools[i].getCapacity()) {
                        actualCapacity++;
                    } else {
                        student.increaseActualPreference();
                        remainingStudents.add(student);
                        student.removeSchool(schools[i]);
                        schools[i].removeStudent(student);
                    }
                }
            }
        }
        return remainingStudents;
    }

    /**
     * The students choose the schools they want to keep according to their preferences and capacity
     * @param students the students
     * @return a school list representing the free schools that have been ejected
     */
    private static List<School> selectTheWantedSchool(Student[] students) {
        List<School> remainingSchools = new ArrayList<>();
        for (int i = 0; i < students.length; i++) {
            List<School> schoolsList = students[i].getInterestedSchools();
            if (schoolsList.size() > 1) {
                Map<School, Integer> studentPreferences = students[i].getPreferences();
                List<Integer> preferencesOrder = new ArrayList<>(studentPreferences.values());
                Collections.sort(preferencesOrder);

                // Sort the map with the preferences order
                Map<School, Integer> map = sortByValue(studentPreferences);

                List<School> tmpList = new ArrayList<>();
                // Sort the list. This list needs to have the same order as the map
                for (Map.Entry mapEntry : map.entrySet()) {
                    School tmpSchool = (School) mapEntry.getKey();
                    for (School school : students[i].getInterestedSchools()) {
                        if (school.getName().equals(tmpSchool.getName())) {
                            tmpList.add(school);
                            break;
                        }
                    }
                }

                // Remove the students that can't be accepted
                int actualCapacity = 1;
                for (School school : tmpList) {
                    if (actualCapacity <= 1) {
                        actualCapacity++;
                    } else {
                        school.increaseActualPreference();
                        remainingSchools.add(school);
                        students[i].getInterestedSchools().remove(school);
                        school.removeStudent(students[i]);
                    }
                }
            }
        }
        return remainingSchools;
    }

    /**
     * Sort the given map in an ascending order according to the map values
     * @param map the given map
     * @param <K> the key
     * @param <V> the value
     * @return a new sorted map
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    /**
     * Display the algorithm result
     * @param schools the schools that made the bidding
     */
    private static void displayResult(School[] schools) {
        System.out.println("Number of Rounds : " + nbRounds + "\n");
        System.out.print("\nSchools : Students\n");
        System.out.print("____________________\n");
        for (School school : schools) {
            System.out.print("\n" + school.getName() + " : ");
            school.getStudents().forEach(student -> System.out.print(student.getName() + ", "));
            System.out.print("\n--------------------------------------");
        }
    }

    /**
     * Display the algorithm result
     * @param students the students that made the bidding
     */
    private static void displayResult(Student[] students) {
        System.out.println("Number of Rounds : " + nbRounds + "\n");
        System.out.print("\nStudents : Schools\n");
        System.out.print("____________________\n");
        for (Student student : students) {
            System.out.print("\n" + student.getName() + " : " + student.getSchool().getName());
            System.out.print("\n--------------------------------------");
        }
    }

    /**
     * Parses the raw pair matrix and associate the schools and students preferences
     * @param schools the schools
     * @param students the students
     * @param preferencesPairs all the preferences pairs from the matrix
     */
    private static void parsePreferencesPairs(School[] schools, Student[] students, Pair[][] preferencesPairs) {
        for (int i = 0; i < students.length; i++) {
            for (int j = 0; j < schools.length; j++) {
                int prefStudent = preferencesPairs[i][j].getFirst();
                int prefSchool = preferencesPairs[i][j].getSecond();
                students[i].addPreference(schools[j], prefStudent);
                schools[j].addPreference(students[i], prefSchool);
            }
        }
    }

    /**
     * Builds schools based on the schools names
     * @param choicesMatrix the object matrix from the source file
     * @return a schools array
     */
    private static School[] getSchools(Object[][] choicesMatrix) {
        School[] schools = new School[schoolsCapacitiesTab.length];
        for (int i = 0; i < schoolsCapacitiesTab.length; i++) {
            School school = new School((String) schoolsCapacitiesTab[i][0], (int) schoolsCapacitiesTab[i][1]);
            schools[i] = school;
        }
        return schools;
    }

    /**
     * Builds students based on the students names
     * @param choicesMatrix the object matrix from the source file
     * @return a students array
     */
    private static Student[] getStudents(Object[][] choicesMatrix) {
        Student[] students = new Student[choicesMatrix.length - 1];
        String[] studentsName = retrieveStudentsNames(choicesMatrix);
        for (int i = 1; i < studentsName.length; i++) {
            Student student = new Student(studentsName[i]);
            students[i-1] = student;
        }
        return students;
    }

    /**
     * Retrieves the schools names from the matrix
     * @param matrix the given matrix from the source file
     * @return a schools names array
     */
    public static String[] retrieveSchoolsNames(Object[][] matrix) {
        String[] line = new String[matrix[0].length];
        for (int i = 0; i < line.length; i ++) {
            line[i] = matrix[0][i].toString();
        }
        return line;
    }

    /**
     * Retrieves the students names from the matrix
     * @param matrix the given matrix from the source file
     * @return a students names array
     */    public static String[] retrieveStudentsNames(Object[][] matrix) {
        String[] column = new String[matrix.length];
        for (int i = 0; i < column.length; i ++) {
            column[i] = matrix[i][0].toString();
        }
        return column;
    }

    /**
     * Extracts a pair array without the school and students names
     * @param matrix the given matrix from the source file
     * @return a new matrix without the schools and students names and capacities
     */
    public static Pair[][] extractRawData(Object[][] matrix) {
        Pair[][] arrayWithoutTitles = new Pair[matrix.length-1][matrix[0].length-1];
        for (int i = 1; i < matrix.length; i ++) {
            for (int j = 1; j < matrix[1].length; j++) {
                arrayWithoutTitles[i-1][j-1] = (Pair) matrix[i][j];
            }
        }
        return arrayWithoutTitles;
    }
}