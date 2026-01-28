import java.util.ArrayList;
import java.util.List;

/**
 * Student class that extends Person
 * Demonstrates inheritance, polymorphism, and Collections usage
 */
public class Student extends Person {
    private int gradeLevel;
    private List<Course> courses; // ArrayList for Collections requirement
    
    // Constructor
    public Student(int id, String name, int age, int gradeLevel) {
        super(id, name, age);
        this.gradeLevel = gradeLevel;
        this.courses = new ArrayList<>();
    }
    
    // Getter and Setter for gradeLevel
    public int getGradeLevel() {
        return gradeLevel;
    }
    
    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
    
    // Get enrolled courses
    public List<Course> getCourses() {
        return new ArrayList<>(courses); // Return copy for encapsulation
    }
    
    // Enroll in a course
    public boolean enrollCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        
        // Check if already enrolled
        for (Course enrolledCourse : courses) {
            if (enrolledCourse.getCourseId() == course.getCourseId()) {
                return false; // Already enrolled
            }
        }
        
        courses.add(course);
        return true;
    }
    
    // Remove from a course
    public boolean dropCourse(int courseId) {
        return courses.removeIf(course -> course.getCourseId() == courseId);
    }
    
    // Get number of enrolled courses
    public int getEnrolledCoursesCount() {
        return courses.size();
    }
    
    // Check if enrolled in a specific course
    public boolean isEnrolledIn(int courseId) {
        return courses.stream().anyMatch(course -> course.getCourseId() == courseId);
    }
    
    // Implementation of abstract method from Person (Polymorphism)
    @Override
    public void displayInfo() {
        System.out.println("=== Student Information ===");
        System.out.println(getBasicInfo());
        System.out.println("Grade Level: " + gradeLevel);
        System.out.println("Enrolled Courses: " + courses.size());
        
        if (!courses.isEmpty()) {
            System.out.println("Course List:");
            for (Course course : courses) {
                System.out.println("  - " + course.getTitle() + " (ID: " + course.getCourseId() + ")");
            }
        }
        System.out.println("========================");
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Grade: " + gradeLevel + ", Courses: " + courses.size();
    }
}
