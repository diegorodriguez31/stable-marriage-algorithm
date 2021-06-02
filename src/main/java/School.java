import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class School {
    private String name;
    private List<Student> students;
    private Map<Student, Integer> preferences;
    private int actualPreference = 1;
    private int capacity;

    public School(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
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

    public int getActualPreference() {
        return actualPreference;
    }

    public void increaseActualPreference() {
        actualPreference++;
    }

    public School getSchool(String name) {
        if (name.equals(this.name)) {
            return this;
        }
        return null;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean checkCapacity(){
        return getStudents().size() <= getCapacity();
    }

    public String getName() {
        return name;
    }
}