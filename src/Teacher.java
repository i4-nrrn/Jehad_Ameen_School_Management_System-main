import java.util.ArrayList;
import java.util.List;

/**
 * Teacher class that extends Person
 * Demonstrates inheritance and polymorphism
 */
public class Teacher extends Person {
    private String subject;
    private List<Course> assignedCourses; // Courses taught by this teacher
    
    // Constructor
    public Teacher(int id, String name, int age, String subject) {
        super(id, name, age);
        this.subject = subject;
        this.assignedCourses = new ArrayList<>();
    }
    
    // Getter and Setter for subject
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    // Get assigned courses
    public List<Course> getAssignedCourses() {
        return new ArrayList<>(assignedCourses);
    }
    
    // Assign a course to this teacher
    public boolean assignCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        
        // Check if already assigned
        for (Course assignedCourse : assignedCourses) {
            if (assignedCourse.getCourseId() == course.getCourseId()) {
                return false; // Already assigned
            }
        }
        
        assignedCourses.add(course);
        course.setTeacher(this); // Set this teacher as the course teacher
        return true;
    }
    
    // Remove course assignment
    public boolean unassignCourse(int courseId) {
        return assignedCourses.removeIf(course -> course.getCourseId() == courseId);
    }
    
    // Get number of assigned courses
    public int getAssignedCoursesCount() {
        return assignedCourses.size();
    }
    
    // Implementation of abstract method from Person (Polymorphism)
    @Override
    public void displayInfo() {
        System.out.println("=== Teacher Information ===");
        System.out.println(getBasicInfo());
        System.out.println("Subject: " + subject);
        System.out.println("Assigned Courses: " + assignedCourses.size());
        
        if (!assignedCourses.isEmpty()) {
            System.out.println("Teaching:");
            for (Course course : assignedCourses) {
                System.out.println("  - " + course.getTitle() + " (ID: " + course.getCourseId() + ")");
            }
        }
        System.out.println("=========================");
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Subject: " + subject + ", Teaching: " + assignedCourses.size() + " courses";
    }
}
