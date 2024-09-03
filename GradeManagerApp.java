import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class GradeManagerApp extends JFrame {
    private final GradeManager gradeManager = new GradeManager();

    public GradeManagerApp() {
        setTitle("Grade Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Course", createAddCoursePanel());
        tabbedPane.addTab("Add Grade", createAddGradePanel());
        tabbedPane.addTab("Show Student Details", createShowStudentDetailsPanel());
        tabbedPane.addTab("List Courses", createListCoursesPanel());

        add(tabbedPane);
    }

    private JPanel createAddCoursePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel courseLabel = new JLabel("Course Name:");
        JTextField courseNameField = new JTextField(20);
        JButton addCourseButton = new JButton("Add");

        addCourseButton.addActionListener(e -> {
            String courseName = courseNameField.getText().trim();
            if (!courseName.isEmpty()) {
                Course course = new Course(courseName);
                gradeManager.addCourse(course);
                JOptionPane.showMessageDialog(this, "Course added successfully.");
                courseNameField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Course name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(courseLabel, gbc);

        gbc.gridx = 1;
        panel.add(courseNameField, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(addCourseButton, gbc);

        return panel;
    }

    private JPanel createAddGradePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Student Name:");
        JLabel idLabel = new JLabel("Student ID:");
        JLabel courseLabel = new JLabel("Course Name:");
        JLabel gradeLabel = new JLabel("Grade:");

        JTextField studentNameField = new JTextField(15);
        JTextField studentIdField = new JTextField(5);
        JTextField courseNameField = new JTextField(15);
        JTextField gradeField = new JTextField(5);
        JButton addGradeButton = new JButton("Add");

        addGradeButton.addActionListener(e -> {
            String studentName = studentNameField.getText().trim();
            int studentId;
            double grade;
            String courseName = courseNameField.getText().trim();

            try {
                studentId = Integer.parseInt(studentIdField.getText().trim());
                grade = Double.parseDouble(gradeField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Enter numeric values for ID and Grade.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!studentName.isEmpty() && !courseName.isEmpty()) {
                Student student = new Student(studentName, studentId);
                Course course = gradeManager.getCourse(courseName);

                if (course != null) {
                    course.addGrade(student, grade);
                    JOptionPane.showMessageDialog(this, "Grade added successfully.");
                    studentNameField.setText("");
                    studentIdField.setText("");
                    courseNameField.setText("");
                    gradeField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Course not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        panel.add(studentNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        panel.add(studentIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(courseLabel, gbc);

        gbc.gridx = 1;
        panel.add(courseNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(gradeLabel, gbc);

        gbc.gridx = 1;
        panel.add(gradeField, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(addGradeButton, gbc);

        return panel;
    }

    private JPanel createShowStudentDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Student Name:");
        JLabel idLabel = new JLabel("Student ID:");
        JTextField studentNameField = new JTextField(15);
        JTextField studentIdField = new JTextField(5);
        JButton showDetailsButton = new JButton("Show Details");
        JTextArea detailsArea = new JTextArea(5, 30);
        detailsArea.setEditable(false);

        showDetailsButton.addActionListener(e -> {
            String studentName = studentNameField.getText().trim();
            int studentId;

            try {
                studentId = Integer.parseInt(studentIdField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid student ID. Enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!studentName.isEmpty()) {
                Student student = new Student(studentName, studentId);
                double average = gradeManager.getStudentAverage(student);
                detailsArea.setText(String.format("Student: %s (ID: %d)\nAverage Grade: %.2f",
                        student.getName(), student.getId(), average));
            } else {
                JOptionPane.showMessageDialog(this, "Student name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        panel.add(studentNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        panel.add(studentIdField, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(showDetailsButton, gbc);

        gbc.gridy = 3;
        panel.add(new JScrollPane(detailsArea), gbc);

        return panel;
    }

    private JPanel createListCoursesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea courseListArea = new JTextArea();
        courseListArea.setEditable(false);
        JButton listCoursesButton = new JButton("List Courses");

        listCoursesButton.addActionListener(e -> {
            Map<String, Course> courses = gradeManager.getCourses();
            StringBuilder sb = new StringBuilder("Courses:\n");
            for (Course course : courses.values()) {
                sb.append(course.getCourseName()).append("\n");
            }
            courseListArea.setText(sb.toString());
        });

        panel.add(listCoursesButton, BorderLayout.NORTH);
        panel.add(new JScrollPane(courseListArea), BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GradeManagerApp app = new GradeManagerApp();
            app.setVisible(true);
        });
    }
}
