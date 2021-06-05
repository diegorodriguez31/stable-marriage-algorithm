import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the school in the stable marriage algorithm
 */
public class School {
    // School name
    private String name;
    // School list of the student that bid for this school
    private List<Student> students;
    // School ascending student preferences (1 is the best)
    private Map<Student, Integer> preferences;
    // Variable that increase when the school is refused in his actual preferred student.
    private int actualPreference = 1;
    // School capacity
    private int capacity;

    /**
     * Creates a school with his name and capacity
     * @param name school name
     * @param capacity school capacity
     */
    public School(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.students = new ArrayList<>();
        this.preferences = new HashMap<>();
    }

    /**
     * Adds a student that just bid this school
     * @param student the student that wants this school
     */
    public void addStudent(Student student){
        this.students.add(student);
    }

    /**
     * Removes a student because the school can't accept him
     * @param student the removed student
     */
    public void removeStudent(Student student){
        this.students.remove(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public Map<Student, Integer> getPreferences() {
        return preferences;
    }

    /**
     * Add a student with a value representing the preference compared to other students
     * @param student the student to add
     * @param preference the preference assigned to this student
     */
    public void addPreference(Student student, int preference) {
        preferences.put(student, preference);
    }

    public int getActualPreference() {
        return actualPreference;
    }

    public void increaseActualPreference() {
        actualPreference++;
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Checks if the students that want to be assigned to this school
     * are less or equals thant the school capacity
     * @return boolean whether the capacity is respected or not
     */
    public boolean checkCapacity(){
        return getStudents().size() <= getCapacity();
    }

    public String getName() {
        return name;
    }
}
