public class Student {
    private String name;
    private School school;

    public Student(String name) {
        this.name = name;
        this.school = null;
    }

    public void addSchool(School school){
        this.school = school;
    }
}
