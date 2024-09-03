import java.util.HashMap;
import java.util.Map;

public class GradeManager {
    private final Map<String, Course> courses = new HashMap<>();

    public void addCourse(Course course) {
        courses.put(course.getCourseName(), course);
    }

    public Course getCourse(String courseName) {
        return courses.get(courseName);
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public double getStudentAverage(Student student) {
        double total = 0;
        int count = 0;
        for (Course course : courses.values()) {
            Double grade = course.getGrade(student);
            if (grade != null) {
                total += grade;
                count++;
            }
        }
        return count > 0 ? total / count : 0;
    }
}
