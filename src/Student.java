import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private School school;
    private Map<School, Integer> preferences;

    public Student(String name) {
        this.name = name;
        this.school = null;
        this.preferences = new HashMap<>();
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
}
