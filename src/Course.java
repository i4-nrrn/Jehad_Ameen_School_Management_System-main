import java.util.ArrayList;
import java.util.List;

/**
 * Course class demonstrating aggregation relationship (Has-a)
 * A course has a teacher and can have multiple enrolled students
 */
public class Course {
    private int courseId;
    private String title;
    private Teacher teacher; // Aggregation - Course HAS-A Teacher
    private List<Student> enrolledStudents;
    private int maxCapacity;
    
    // Constructor
    public Course(int courseId, String title, int maxCapacity) {
        this.courseId = courseId;
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.enrolledStudents = new ArrayList<>();
    }
    
    // Constructor with teacher
    public Course(int courseId, String title, Teacher teacher, int maxCapacity) {
        this(courseId, title, maxCapacity);
        this.teacher = teacher;
    }
    
    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public int getMaxCapacity() {
        return maxCapacity;
    }
    
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    public List<Student> getEnrolledStudents() {
        return new ArrayList<>(enrolledStudents);
    }
    
    // Add student to course
    public boolean addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        
        if (enrolledStudents.size() >= maxCapacity) {
            return false; // Course is full
        }
        
        // Check if student is already enrolled
        for (Student enrolledStudent : enrolledStudents) {
            if (enrolledStudent.getId() == student.getId()) {
                return false; // Already enrolled
            }
        }
        
        enrolledStudents.add(student);
        return true;
    }
    
    // Remove student from course
    public boolean removeStudent(int studentId) {
        return enrolledStudents.removeIf(student -> student.getId() == studentId);
    }
    
    // Get current enrollment count
    public int getCurrentEnrollment() {
        return enrolledStudents.size();
    }
    
    // Check if course is full
    public boolean isFull() {
        return enrolledStudents.size() >= maxCapacity;
    }
    
    // Check if student is enrolled
    public boolean isStudentEnrolled(int studentId) {
        return enrolledStudents.stream().anyMatch(student -> student.getId() == studentId);
    }
    
    // Get course information
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Course ID: ").append(courseId).append("\n");
        info.append("Title: ").append(title).append("\n");
        info.append("Teacher: ").append(teacher != null ? teacher.getName() : "Not assigned").append("\n");
        info.append("Enrollment: ").append(enrolledStudents.size()).append("/").append(maxCapacity).append("\n");
        
        if (!enrolledStudents.isEmpty()) {
            info.append("Enrolled Students:\n");
            for (Student student : enrolledStudents) {
                info.append("  - ").append(student.getName()).append(" (ID: ").append(student.getId()).append(")\n");
            }
        }
        
        return info.toString();
    }
    
    // Display course information
    public void displayInfo() {
        System.out.println("=== Course Information ===");
        System.out.println(getInfo());
        System.out.println("========================");
    }
    
    @Override
    public String toString() {
        return "Course{" +
                "ID=" + courseId +
                ", title='" + title + '\'' +
                ", teacher=" + (teacher != null ? teacher.getName() : "None") +
                ", enrollment=" + enrolledStudents.size() + "/" + maxCapacity +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseId == course.courseId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(courseId);
    }
}
