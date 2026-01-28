import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * نسخة مبسطة من تطبيق إدارة المدرسة بدون قاعدة بيانات
 * لاختبار الواجهة الرسومية والوظائف الأساسية
 */
public class SimpleSchoolApp extends JFrame {
    // قوائم لحفظ البيانات في الذاكرة
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    
    private JTabbedPane tabbedPane;
    
    // Student tab components
    private JTextField studentIdField, studentNameField, studentAgeField, studentGradeField;
    private JTable studentsTable;
    private DefaultTableModel studentsTableModel;
    
    // Teacher tab components
    private JTextField teacherIdField, teacherNameField, teacherAgeField, teacherSubjectField;
    private JTable teachersTable;
    private DefaultTableModel teachersTableModel;
    
    // Course tab components
    private JTextField courseIdField, courseTitleField, courseCapacityField;
    private JComboBox<String> courseTeacherCombo;
    private JTable coursesTable;
    private DefaultTableModel coursesTableModel;
    
    public SimpleSchoolApp() {
        initializeGUI();
        loadSampleData();
    }
    
    private void initializeGUI() {
        setTitle("School Management System - Demo Version");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Students", createStudentPanel());
        tabbedPane.addTab("Teachers", createTeacherPanel());
        tabbedPane.addTab("Courses", createCoursePanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Add status bar
        JLabel statusBar = new JLabel("School Management System - Ready");
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        add(statusBar, BorderLayout.SOUTH);
        
        // Set window properties
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Student ID
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        studentIdField = new JTextField(10);
        inputPanel.add(studentIdField, gbc);
        
        // Student Name
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        studentNameField = new JTextField(20);
        inputPanel.add(studentNameField, gbc);
        
        // Student Age
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        studentAgeField = new JTextField(10);
        inputPanel.add(studentAgeField, gbc);
        
        // Grade Level
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Grade Level:"), gbc);
        gbc.gridx = 1;
        studentGradeField = new JTextField(10);
        inputPanel.add(studentGradeField, gbc);
        
        // Add button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.addActionListener(new AddStudentListener());
        inputPanel.add(addStudentBtn, gbc);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        
        // Students table
        String[] columnNames = {"ID", "Name", "Age", "Grade Level"};
        studentsTableModel = new DefaultTableModel(columnNames, 0);
        studentsTable = new JTable(studentsTableModel);
        JScrollPane scrollPane = new JScrollPane(studentsTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTeacherPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Teacher ID
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Teacher ID:"), gbc);
        gbc.gridx = 1;
        teacherIdField = new JTextField(10);
        inputPanel.add(teacherIdField, gbc);
        
        // Teacher Name
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        teacherNameField = new JTextField(20);
        inputPanel.add(teacherNameField, gbc);
        
        // Teacher Age
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        teacherAgeField = new JTextField(10);
        inputPanel.add(teacherAgeField, gbc);
        
        // Subject
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Subject:"), gbc);
        gbc.gridx = 1;
        teacherSubjectField = new JTextField(20);
        inputPanel.add(teacherSubjectField, gbc);
        
        // Add button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton addTeacherBtn = new JButton("Add Teacher");
        addTeacherBtn.addActionListener(new AddTeacherListener());
        inputPanel.add(addTeacherBtn, gbc);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        
        // Teachers table
        String[] columnNames = {"ID", "Name", "Age", "Subject"};
        teachersTableModel = new DefaultTableModel(columnNames, 0);
        teachersTable = new JTable(teachersTableModel);
        JScrollPane scrollPane = new JScrollPane(teachersTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Input form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Course ID
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        courseIdField = new JTextField(10);
        inputPanel.add(courseIdField, gbc);
        
        // Course Title
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        courseTitleField = new JTextField(20);
        inputPanel.add(courseTitleField, gbc);
        
        // Max Capacity
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Max Capacity:"), gbc);
        gbc.gridx = 1;
        courseCapacityField = new JTextField(10);
        inputPanel.add(courseCapacityField, gbc);
        
        // Teacher
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Teacher:"), gbc);
        gbc.gridx = 1;
        courseTeacherCombo = new JComboBox<>();
        courseTeacherCombo.addItem("No Teacher");
        inputPanel.add(courseTeacherCombo, gbc);
        
        // Add button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton addCourseBtn = new JButton("Add Course");
        addCourseBtn.addActionListener(new AddCourseListener());
        inputPanel.add(addCourseBtn, gbc);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        
        // Courses table
        String[] columnNames = {"Course ID", "Title", "Teacher", "Max Capacity"};
        coursesTableModel = new DefaultTableModel(columnNames, 0);
        coursesTable = new JTable(coursesTableModel);
        JScrollPane scrollPane = new JScrollPane(coursesTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadSampleData() {
        // إضافة بيانات تجريبية
        Student student1 = new Student(1001, "أحمد علي", 20, 12);
        Student student2 = new Student(1002, "فاطمة حسن", 19, 11);
        students.add(student1);
        students.add(student2);
        
        Teacher teacher1 = new Teacher(2001, "د. محمد صالح", 35, "الرياضيات");
        Teacher teacher2 = new Teacher(2002, "أ. عائشة أحمد", 42, "الفيزياء");
        teachers.add(teacher1);
        teachers.add(teacher2);
        
        Course course1 = new Course(101, "الجبر المتقدم", teacher1, 30);
        Course course2 = new Course(102, "الفيزياء النووية", teacher2, 25);
        courses.add(course1);
        courses.add(course2);
        
        refreshTables();
    }
    
    private void refreshTables() {
        // تحديث جدول الطلاب
        studentsTableModel.setRowCount(0);
        for (Student student : students) {
            Object[] row = {student.getId(), student.getName(), student.getAge(), student.getGradeLevel()};
            studentsTableModel.addRow(row);
        }
        
        // تحديث جدول المدرسين
        teachersTableModel.setRowCount(0);
        for (Teacher teacher : teachers) {
            Object[] row = {teacher.getId(), teacher.getName(), teacher.getAge(), teacher.getSubject()};
            teachersTableModel.addRow(row);
        }
        
        // تحديث جدول المواد
        coursesTableModel.setRowCount(0);
        for (Course course : courses) {
            Object[] row = {
                course.getCourseId(),
                course.getTitle(),
                course.getTeacher() != null ? course.getTeacher().getName() : "غير محدد",
                course.getMaxCapacity()
            };
            coursesTableModel.addRow(row);
        }
        
        // تحديث قائمة المدرسين في المواد
        courseTeacherCombo.removeAllItems();
        courseTeacherCombo.addItem("No Teacher");
        for (Teacher teacher : teachers) {
            courseTeacherCombo.addItem(teacher.getId() + " - " + teacher.getName());
        }
    }
    
    // Event Listeners
    private class AddStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(studentIdField.getText().trim());
                String name = studentNameField.getText().trim();
                int age = Integer.parseInt(studentAgeField.getText().trim());
                int gradeLevel = Integer.parseInt(studentGradeField.getText().trim());
                
                if (name.isEmpty()) {
                    throw new IllegalArgumentException("Name cannot be empty");
                }
                
                Student student = new Student(id, name, age, gradeLevel);
                students.add(student);
                
                JOptionPane.showMessageDialog(SimpleSchoolApp.this, "Student added successfully!");
                clearStudentFields();
                refreshTables();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(SimpleSchoolApp.this, "Please enter valid numbers for ID, age, and grade level", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(SimpleSchoolApp.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private class AddTeacherListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(teacherIdField.getText().trim());
                String name = teacherNameField.getText().trim();
                int age = Integer.parseInt(teacherAgeField.getText().trim());
                String subject = teacherSubjectField.getText().trim();
                
                if (name.isEmpty() || subject.isEmpty()) {
                    throw new IllegalArgumentException("Name and subject cannot be empty");
                }
                
                Teacher teacher = new Teacher(id, name, age, subject);
                teachers.add(teacher);
                
                JOptionPane.showMessageDialog(SimpleSchoolApp.this, "Teacher added successfully!");
                clearTeacherFields();
                refreshTables();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(SimpleSchoolApp.this, "Please enter valid numbers for ID and age", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(SimpleSchoolApp.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private class AddCourseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int courseId = Integer.parseInt(courseIdField.getText().trim());
                String title = courseTitleField.getText().trim();
                int maxCapacity = Integer.parseInt(courseCapacityField.getText().trim());
                
                if (title.isEmpty()) {
                    throw new IllegalArgumentException("Course title cannot be empty");
                }
                
                Course course = new Course(courseId, title, maxCapacity);
                
                // Set teacher if selected
                String selectedTeacher = (String) courseTeacherCombo.getSelectedItem();
                if (selectedTeacher != null && !selectedTeacher.equals("No Teacher")) {
                    int teacherId = Integer.parseInt(selectedTeacher.split(" - ")[0]);
                    Teacher teacher = findTeacherById(teacherId);
                    if (teacher != null) {
                        course.setTeacher(teacher);
                    }
                }
                
                courses.add(course);
                
                JOptionPane.showMessageDialog(SimpleSchoolApp.this, "Course added successfully!");
                clearCourseFields();
                refreshTables();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(SimpleSchoolApp.this, "Please enter valid numbers for course ID and capacity", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(SimpleSchoolApp.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private Teacher findTeacherById(int id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId() == id) {
                return teacher;
            }
        }
        return null;
    }
    
    private void clearStudentFields() {
        studentIdField.setText("");
        studentNameField.setText("");
        studentAgeField.setText("");
        studentGradeField.setText("");
    }
    
    private void clearTeacherFields() {
        teacherIdField.setText("");
        teacherNameField.setText("");
        teacherAgeField.setText("");
        teacherSubjectField.setText("");
    }
    
    private void clearCourseFields() {
        courseIdField.setText("");
        courseTitleField.setText("");
        courseCapacityField.setText("");
        courseTeacherCombo.setSelectedIndex(0);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimpleSchoolApp();
        });
    }
}
