import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class StableMarriage {

    static String filePath;
    static int nbRounds = 0;
    static HashMap<String, Integer> schoolsCapacities;
    static Object[][] schoolsCapacitiesTab;

    public static void main(String[] args) {
        Scanner userScanner = new Scanner(System.in);
        System.out.println("\nWhich file do you want to use ?");
        System.out.println("1) 3 students and 3 schools - school capacity of 1");
        System.out.println("2) 3 students and 4 schools - variable schools capacities");
        System.out.println("3) 16 students and 3 schools - variable schools capacities");
        int fileUsed = userScanner.nextInt();
        filePath = "src/main/resources/preferencesFile" + fileUsed + ".csv";

        System.out.println("\nWho does the bidding ?");
        System.out.println("1) Students");
        System.out.println("2) Schools");
        boolean studentsAreBidding = userScanner.nextInt() == 1;
        System.out.println("\n\n\n");

        schoolsCapacities = new HashMap<>();
        Object[][] choicesMatrix = parseCSVFile(filePath);

        School[] schools = getSchools(choicesMatrix);
        Student[] students = getStudents(choicesMatrix);

        Pair[][] preferencesPairs = extractRawData(choicesMatrix);

        parsePreferencesPairs(schools, students, preferencesPairs);

       if (studentsAreBidding) {
            applyStudentBidding(students, schools);
        } else {
            applySchoolBidding(students, schools);
        }
    }

    private static Object[][] parseCSVFile(String filePath) {
        Object[][] choicesMatrix = {{}};

        int nbColumns = 0;
        int nbRows = 0;

        List<List<String>> records = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            int i = 0;
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
            BufferedReader br2 = new BufferedReader(new FileReader(filePath));
            while ((line = br2.readLine()) != null) {
                if (i == 0) {
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                    choicesMatrix[0] = values;

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

    private static boolean marriageIsStable(School[] schools){
        for (School school : schools) {
            if (!school.checkCapacity()) {
                return false;
            }
        }
        return true;
    }

    private static boolean marriageIsStable(Student[] students){
        for (Student student : students) {
            if (!student.checkCapacity()) {
                return false;
            }
        }
        return true;
    }

    private static void applyStudentBidding(Student[] students, School[] schools) {
        studentsBiddingSchools(students);
        nbRounds = 1;
        while (!marriageIsStable(schools)) {
            List<Student> studentsRemaining = selectTheWantedSudents(schools);
            Student[] remainingStudents = new Student[students.length];
            int j = 0;
            for (Student student : studentsRemaining) {
                remainingStudents[j] = student;
                j++;
            }
            studentsBiddingSchools(remainingStudents);
            nbRounds++;
        }
        displayResult(students);
    }

    private static void applySchoolBidding(Student[] students, School[] schools) {
        schoolsBiddingStudents(schools);
        nbRounds = 1;
        while (!marriageIsStable(students)) {
            List<School> schoolsRemaining = selectTheWantedSchool(students);
            School[] remainingSchools = new School[schools.length];
            int j = 0;
            for (School school : schoolsRemaining) {
                remainingSchools[j] = school;
                j++;
            }
            schoolsBiddingStudents(remainingSchools);
            nbRounds++;
        }
        displayResult(schools);
    }

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

    private static List<Student> selectTheWantedSudents(School[] schools) {
        List<Student> remainingStudents = new ArrayList<>();
        for (int i = 0; i < schools.length; i++) {
            List<Student> studentsList = schools[i].getStudents();
            if (studentsList.size() > schools[i].getCapacity()) {
                Map<Student, Integer> schoolPreferences = schools[i].getPreferences();
                List<Integer> preferencesOrder = new ArrayList<>(schoolPreferences.values());
                Collections.sort(preferencesOrder);

                Map<Student, Integer> map = sortByValue(schoolPreferences);

                List<Student> tmpList = new ArrayList<>();
                // Trier la liste pour meme ordre que la map
                for (Map.Entry mapEntry : map.entrySet()) {
                    Student tmpStudent = (Student) mapEntry.getKey();
                    for (Student student : schools[i].getStudents()) {
                        if (student.getName().equals(tmpStudent.getName())) {
                            tmpList.add(student);
                            break;
                        }
                    }
                }

                int actualCapacity = 1;
                for (Student student : tmpList) {
                    if (actualCapacity <= schools[i].getCapacity()) {
                        actualCapacity++;
                    } else {
                        student.increaseActualPreference();
                        remainingStudents.add(student);
                        schools[i].removeStudent(student);
                    }
                }
            }
        }
        return remainingStudents;
    }

    private static List<School> selectTheWantedSchool(Student[] students) {
        List<School> remainingSchools = new ArrayList<>();
        for (int i = 0; i < students.length; i++) {
            List<School> schoolsList = students[i].getInterestedSchools();
            if (schoolsList.size() > 1) {
                Map<School, Integer> studentPreferences = students[i].getPreferences();
                List<Integer> preferencesOrder = new ArrayList<>(studentPreferences.values());
                Collections.sort(preferencesOrder);

                Map<School, Integer> map = sortByValue(studentPreferences);

                List<School> tmpList = new ArrayList<>();
                // Trier la liste pour meme ordre que la map
                for (Map.Entry mapEntry : map.entrySet()) {
                    School tmpSchool = (School) mapEntry.getKey();
                    for (School school : students[i].getInterestedSchools()) {
                        if (school.getName().equals(tmpSchool.getName())) {
                            tmpList.add(school);
                            break;
                        }
                    }
                }

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

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

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

    private static void displayResult(Student[] students) {
        System.out.println("Number of Rounds : " + nbRounds + "\n");
        System.out.print("\nStudents : Schools\n");
        System.out.print("____________________\n");
        for (Student student : students) {
            System.out.print("\n" + student.getName() + " : " + student.getSchool().getName());
            System.out.print("\n--------------------------------------");
        }
    }

    // Parse the raw pair matrix and associate the schools and students preferences
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

    // Build schools based on the school names
    private static School[] getSchools(Object[][] choicesMatrix) {
        School[] schools = new School[schoolsCapacitiesTab.length];
        for (int i = 0; i < schoolsCapacitiesTab.length; i++) {
            School school = new School((String) schoolsCapacitiesTab[i][0], (int) schoolsCapacitiesTab[i][1]);
            schools[i] = school;
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
        String[] column = new String[matrix.length];
        for (int i = 0; i < column.length; i ++) {
            column[i] = matrix[i][0].toString();
        }
        return column;
    }

    // Extract a Pair array without the school and students names
    public static Pair[][] extractRawData(Object[][] matrix) {
        Pair[][] arrayWithoutTitles = new Pair[matrix.length-1][matrix[0].length-1];
        for (int i = 1; i < matrix.length; i ++) {
            for (int j = 1; j < matrix[1].length; j++) {
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