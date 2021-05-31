import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    private String name;
    private School school;
    private List<School> interestedSchools;
    private Map<School, Integer> preferences;
    private int actualPreference = 1;

    public Student(String name) {
        this.name = name;
        this.school = null;
        this.preferences = new HashMap<>();
        this.interestedSchools = new ArrayList<>();
    }

    public void addSchool(School school){
        this.school = school;
    }

    public void removeSchool(School school) {this.school = null;}

    public Map<School, Integer> getPreferences() {
        return preferences;
    }

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

    public boolean checkCapacity() {
        return getInterestedSchools().size() <= 1;
    }
}
