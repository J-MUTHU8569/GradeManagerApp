import java.util.HashMap;
import java.util.Map;

public class Course {
    private String courseName;
    private Map<Student, Double> grades;

    public Course(String courseName) {
        this.courseName = courseName;
        this.grades = new HashMap<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public void addGrade(Student student, double grade) {
        grades.put(student, grade);
    }

    public double getGrade(Student student) {
        return grades.getOrDefault(student, 0.0);
    }

    public double getAverageGrade() {
        if (grades.isEmpty()) return 0.0;
        double sum = 0.0;
        for (double grade : grades.values()) {
            sum += grade;
        }
        return sum / grades.size();
    }

    public Map<Student, Double> getGrades() {
        return grades;
    }
}
