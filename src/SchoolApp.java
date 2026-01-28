import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Main GUI application for School Management System
 * Demonstrates Swing GUI components and event handling
 */
public class SchoolApp extends JFrame {
    private SchoolDB database;
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
    
    // Enrollment tab components
    private JComboBox<String> enrollStudentCombo, enrollCourseCombo;
    
    public SchoolApp() {
        initializeDatabase();
        initializeGUI();
        loadData();
    }
    
    private void initializeDatabase() {
        try {
            database = new SchoolDB();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Failed to connect to database: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    private void initializeGUI() {
        setTitle("School Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Students", createStudentPanel());
        tabbedPane.addTab("Teachers", createTeacherPanel());
        tabbedPane.addTab("Courses", createCoursePanel());
        tabbedPane.addTab("Enrollment", createEnrollmentPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Add menu bar
        setJMenuBar(createMenuBar());
        
        // Set window properties
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem refreshItem = new JMenuItem("Refresh Data");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        refreshItem.addActionListener(e -> loadData());
        exitItem.addActionListener(e -> {
            database.closeConnection();
            System.exit(0);
        });
        
        fileMenu.add(refreshItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        return menuBar;
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
        String[] columnNames = {"ID", "Name", "Age", "Grade Level", "Actions"};
        studentsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Actions column is editable
            }
        };
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
        String[] columnNames = {"ID", "Name", "Age", "Subject", "Actions"};
        teachersTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Actions column is editable
            }
        };
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
        inputPanel.add(courseTeacherCombo, gbc);
        
        // Add button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton addCourseBtn = new JButton("Add Course");
        addCourseBtn.addActionListener(new AddCourseListener());
        inputPanel.add(addCourseBtn, gbc);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        
        // Courses table
        String[] columnNames = {"Course ID", "Title", "Teacher", "Max Capacity", "Actions"};
        coursesTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Actions column is editable
            }
        };
        coursesTable = new JTable(coursesTableModel);
        JScrollPane scrollPane = new JScrollPane(coursesTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createEnrollmentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Student selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Select Student:"), gbc);
        gbc.gridx = 1;
        enrollStudentCombo = new JComboBox<>();
        panel.add(enrollStudentCombo, gbc);
        
        // Course selection
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Select Course:"), gbc);
        gbc.gridx = 1;
        enrollCourseCombo = new JComboBox<>();
        panel.add(enrollCourseCombo, gbc);
        
        // Enroll button
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton enrollBtn = new JButton("Enroll Student");
        enrollBtn.addActionListener(new EnrollStudentListener());
        panel.add(enrollBtn, gbc);
        
        return panel;
    }
    
    private void loadData() {
        loadStudents();
        loadTeachers();
        loadCourses();
        updateComboBoxes();
    }
    
    private void loadStudents() {
        try {
            List<Student> students = database.getStudents();
            studentsTableModel.setRowCount(0);
            
            for (Student student : students) {
                Object[] row = {
                    student.getId(),
                    student.getName(),
                    student.getAge(),
                    student.getGradeLevel(),
                    "Edit|Delete"
                };
                studentsTableModel.addRow(row);
            }
        } catch (SQLException e) {
            showErrorDialog("Failed to load students: " + e.getMessage());
        }
    }
    
    private void loadTeachers() {
        try {
            List<Teacher> teachers = database.getTeachers();
            teachersTableModel.setRowCount(0);
            
            for (Teacher teacher : teachers) {
                Object[] row = {
                    teacher.getId(),
                    teacher.getName(),
                    teacher.getAge(),
                    teacher.getSubject(),
                    "Edit|Delete"
                };
                teachersTableModel.addRow(row);
            }
        } catch (SQLException e) {
            showErrorDialog("Failed to load teachers: " + e.getMessage());
        }
    }
    
    private void loadCourses() {
        try {
            List<Course> courses = database.getCourses();
            coursesTableModel.setRowCount(0);
            
            for (Course course : courses) {
                Object[] row = {
                    course.getCourseId(),
                    course.getTitle(),
                    course.getTeacher() != null ? course.getTeacher().getName() : "Not assigned",
                    course.getMaxCapacity(),
                    "Edit|Delete"
                };
                coursesTableModel.addRow(row);
            }
        } catch (SQLException e) {
            showErrorDialog("Failed to load courses: " + e.getMessage());
        }
    }
    
    private void updateComboBoxes() {
        // Update teacher combo box
        courseTeacherCombo.removeAllItems();
        courseTeacherCombo.addItem("No Teacher");
        
        // Update enrollment combo boxes
        enrollStudentCombo.removeAllItems();
        enrollCourseCombo.removeAllItems();
        
        try {
            List<Teacher> teachers = database.getTeachers();
            for (Teacher teacher : teachers) {
                courseTeacherCombo.addItem(teacher.getId() + " - " + teacher.getName());
            }
            
            List<Student> students = database.getStudents();
            for (Student student : students) {
                enrollStudentCombo.addItem(student.getId() + " - " + student.getName());
            }
            
            List<Course> courses = database.getCourses();
            for (Course course : courses) {
                enrollCourseCombo.addItem(course.getCourseId() + " - " + course.getTitle());
            }
        } catch (SQLException e) {
            showErrorDialog("Failed to update combo boxes: " + e.getMessage());
        }
    }
    
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showAboutDialog() {
        String message = "School Management System\n\n" +
                        "Features:\n" +
                        "• Student Management\n" +
                        "• Teacher Management\n" +
                        "• Course Management\n" +
                        "• Student Enrollment\n" +
                        "• SQLite Database Integration\n\n" +
                        "Developed using Java Swing and OOP principles";
        
        JOptionPane.showMessageDialog(this, message, "About", JOptionPane.INFORMATION_MESSAGE);
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
                
                if (database.insertStudent(student)) {
                    showSuccessDialog("Student added successfully!");
                    clearStudentFields();
                    loadStudents();
                    updateComboBoxes();
                } else {
                    showErrorDialog("Failed to add student");
                }
                
            } catch (NumberFormatException ex) {
                showErrorDialog("Please enter valid numbers for ID, age, and grade level");
            } catch (IllegalArgumentException ex) {
                showErrorDialog(ex.getMessage());
            } catch (SQLException ex) {
                showErrorDialog("Database error: " + ex.getMessage());
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
                
                if (database.insertTeacher(teacher)) {
                    showSuccessDialog("Teacher added successfully!");
                    clearTeacherFields();
                    loadTeachers();
                    updateComboBoxes();
                } else {
                    showErrorDialog("Failed to add teacher");
                }
                
            } catch (NumberFormatException ex) {
                showErrorDialog("Please enter valid numbers for ID and age");
            } catch (IllegalArgumentException ex) {
                showErrorDialog(ex.getMessage());
            } catch (SQLException ex) {
                showErrorDialog("Database error: " + ex.getMessage());
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
                    Teacher teacher = database.getTeacherById(teacherId);
                    if (teacher != null) {
                        course.setTeacher(teacher);
                    }
                }
                
                if (database.insertCourse(course)) {
                    showSuccessDialog("Course added successfully!");
                    clearCourseFields();
                    loadCourses();
                    updateComboBoxes();
                } else {
                    showErrorDialog("Failed to add course");
                }
                
            } catch (NumberFormatException ex) {
                showErrorDialog("Please enter valid numbers for course ID and capacity");
            } catch (IllegalArgumentException ex) {
                showErrorDialog(ex.getMessage());
            } catch (SQLException ex) {
                showErrorDialog("Database error: " + ex.getMessage());
            }
        }
    }
    
    private class EnrollStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selectedStudent = (String) enrollStudentCombo.getSelectedItem();
                String selectedCourse = (String) enrollCourseCombo.getSelectedItem();
                
                if (selectedStudent == null || selectedCourse == null) {
                    showErrorDialog("Please select both student and course");
                    return;
                }
                
                int studentId = Integer.parseInt(selectedStudent.split(" - ")[0]);
                int courseId = Integer.parseInt(selectedCourse.split(" - ")[0]);
                
                if (database.enrollStudentInCourse(studentId, courseId)) {
                    showSuccessDialog("Student enrolled successfully!");
                } else {
                    showErrorDialog("Failed to enroll student");
                }
                
            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid selection");
            } catch (SQLException ex) {
                showErrorDialog("Database error: " + ex.getMessage());
            }
        }
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
    
    // Event Listeners for Edit/Delete actions
    private class EditDeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String action = source.getName();
            
            if (action.startsWith("edit_")) {
                String[] parts = action.split("_");
                String type = parts[1];
                int id = Integer.parseInt(parts[2]);
                
                if (type.equals("student")) {
                    editStudent(id);
                } else if (type.equals("teacher")) {
                    editTeacher(id);
                } else if (type.equals("course")) {
                    editCourse(id);
                }
            } else if (action.startsWith("delete_")) {
                String[] parts = action.split("_");
                String type = parts[1];
                int id = Integer.parseInt(parts[2]);
                
                int confirm = JOptionPane.showConfirmDialog(
                    SchoolApp.this,
                    "Are you sure you want to delete this " + type + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean success = false;
                        if (type.equals("student")) {
                            success = database.deleteStudent(id);
                        } else if (type.equals("teacher")) {
                            success = database.deleteTeacher(id);
                        } else if (type.equals("course")) {
                            success = database.deleteCourse(id);
                        }
                        
                        if (success) {
                            showSuccessDialog(type + " deleted successfully!");
                            loadData();
                        } else {
                            showErrorDialog("Failed to delete " + type);
                        }
                    } catch (SQLException ex) {
                        showErrorDialog("Database error: " + ex.getMessage());
                    }
                }
            }
        }
    }
    
    private void editStudent(int id) {
        try {
            Student student = database.getStudentById(id);
            if (student != null) {
                studentIdField.setText(String.valueOf(student.getId()));
                studentNameField.setText(student.getName());
                studentAgeField.setText(String.valueOf(student.getAge()));
                studentGradeField.setText(String.valueOf(student.getGradeLevel()));
                tabbedPane.setSelectedIndex(0); // Switch to Students tab
            }
        } catch (SQLException e) {
            showErrorDialog("Failed to load student: " + e.getMessage());
        }
    }
    
    private void editTeacher(int id) {
        try {
            Teacher teacher = database.getTeacherById(id);
            if (teacher != null) {
                teacherIdField.setText(String.valueOf(teacher.getId()));
                teacherNameField.setText(teacher.getName());
                teacherAgeField.setText(String.valueOf(teacher.getAge()));
                teacherSubjectField.setText(teacher.getSubject());
                tabbedPane.setSelectedIndex(1); // Switch to Teachers tab
            }
        } catch (SQLException e) {
            showErrorDialog("Failed to load teacher: " + e.getMessage());
        }
    }
    
    private void editCourse(int id) {
        try {
            List<Course> courses = database.getCourses();
            for (Course course : courses) {
                if (course.getCourseId() == id) {
                    courseIdField.setText(String.valueOf(course.getCourseId()));
                    courseTitleField.setText(course.getTitle());
                    courseCapacityField.setText(String.valueOf(course.getMaxCapacity()));
                    
                    // Set teacher combo
                    if (course.getTeacher() != null) {
                        String teacherText = course.getTeacher().getId() + " - " + course.getTeacher().getName();
                        courseTeacherCombo.setSelectedItem(teacherText);
                    } else {
                        courseTeacherCombo.setSelectedIndex(0);
                    }
                    
                    tabbedPane.setSelectedIndex(2); // Switch to Courses tab
                    break;
                }
            }
        } catch (SQLException e) {
            showErrorDialog("Failed to load course: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SchoolApp();
        });
    }
}
