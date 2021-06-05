import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the student in the stable marriage algorithm
 */
public class Student {
    // Student name
    private String name;
    // Student assigned school
    private School school;
    // School list that are interested by the student
    private List<School> interestedSchools;
    // Student ascending school preferences (1 is the best)
    private Map<School, Integer> preferences;
    // Variable that increase when the student is refused in his actual preferred School.
    private int actualPreference = 1;

    /**
     * Creates a student with his name and no school
     * @param name the student name
     */
    public Student(String name) {
        this.name = name;
        this.school = null;
        this.preferences = new HashMap<>();
        this.interestedSchools = new ArrayList<>();
    }

    /**
     * Removes the school assigned with the student
     * @param school the school to remove
     */
    public void removeSchool(School school) {this.school = new School("No School", 10000);}

    public Map<School, Integer> getPreferences() {
        return preferences;
    }

    /**
     * Adds a school with a value representing the preference compared to other schools
     * @param school the school to add
     * @param preference the preference assigned to this school
     */
    public void addPreference(School school, int preference) {
        preferences.put(school, preference);
    }

    public int getActualPreference() {
        return actualPreference;
    }

    public void increaseActualPreference() {
        actualPreference++;
    }

    public String getName() {
        return name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<School> getInterestedSchools() {
        return interestedSchools;
    }

    /**
     * Checks if the schools that want to be assigned to this student
     * are less or equals or equals thant the student capacity
     * @return boolean whether the capacity is respected or not
     */
    public boolean checkCapacity() {
        return getInterestedSchools().size() <= 1;
    }
}
