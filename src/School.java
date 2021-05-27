import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class School {
    private String name;
    private List<Student> students;
    private Map<Student, Integer> preferences;

    public School(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.preferences = new HashMap<>();
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public void removeStudent(Student student){
        this.students.remove(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public Map<Student, Integer> getPreferences() {
        return preferences;
    }

    public void addPreference(Student student, int preference) {
        preferences.put(student, preference);
    }
}
