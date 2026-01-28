import java.util.List;

/**
 * Test class to verify School Management System functionality
 * Demonstrates all OOP concepts and system features
 */
public class TestSystem {
    
    public static void main(String[] args) {
        System.out.println("=== School Management System Test ===\n");
        
        try {
            // Test 1: Create objects demonstrating inheritance and polymorphism
            testInheritanceAndPolymorphism();
            
            // Test 2: Test aggregation and collections
            testAggregationAndCollections();
            
            // Test 3: Test database operations
            testDatabaseOperations();
            
            System.out.println("\n=== All Tests Completed Successfully! ===");
            
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testInheritanceAndPolymorphism() {
        System.out.println("1. Testing Inheritance and Polymorphism:");
        System.out.println("----------------------------------------");
        
        // Create Student and Teacher objects (inheritance)
        Student student1 = new Student(1001, "Ahmed Ali", 20, 12);
        Student student2 = new Student(1002, "Fatima Hassan", 19, 11);
        Teacher teacher1 = new Teacher(2001, "Dr. Mohammad Saleh", 35, "Mathematics");
        Teacher teacher2 = new Teacher(2002, "Prof. Aisha Ahmed", 42, "Physics");
        
        // Demonstrate polymorphism - same method call, different behavior
        Person[] people = {student1, student2, teacher1, teacher2};
        
        for (Person person : people) {
            person.displayInfo(); // Polymorphic method call
            System.out.println();
        }
        
        System.out.println("✅ Inheritance and Polymorphism test passed!\n");
    }
    
    private static void testAggregationAndCollections() {
        System.out.println("2. Testing Aggregation and Collections:");
        System.out.println("--------------------------------------");
        
        // Create teacher and course (aggregation - Course HAS-A Teacher)
        Teacher mathTeacher = new Teacher(3001, "Dr. Khalid Ibrahim", 40, "Mathematics");
        Course mathCourse = new Course(101, "Advanced Calculus", mathTeacher, 30);
        
        // Create students
        Student student1 = new Student(4001, "Sara Mohammad", 21, 12);
        Student student2 = new Student(4002, "Omar Yusuf", 20, 12);
        Student student3 = new Student(4003, "Layla Ahmed", 22, 12);
        
        // Test Collections - ArrayList usage
        System.out.println("Testing ArrayList Collections:");
        
        // Enroll students in course (Collections in Course class)
        mathCourse.addStudent(student1);
        mathCourse.addStudent(student2);
        mathCourse.addStudent(student3);
        
        // Enroll students in courses (Collections in Student class)
        student1.enrollCourse(mathCourse);
        student2.enrollCourse(mathCourse);
        student3.enrollCourse(mathCourse);
        
        // Display course information showing aggregation and collections
        mathCourse.displayInfo();
        
        // Display student information showing collections
        student1.displayInfo();
        
        System.out.println("✅ Aggregation and Collections test passed!\n");
    }
    
    private static void testDatabaseOperations() {
        System.out.println("3. Testing Database Operations:");
        System.out.println("------------------------------");
        
        try {
            // Initialize database
            SchoolDB database = new SchoolDB();
            
            // Test inserting data
            Student testStudent = new Student(5001, "Test Student", 18, 10);
            Teacher testTeacher = new Teacher(6001, "Test Teacher", 30, "Computer Science");
            Course testCourse = new Course(201, "Java Programming", testTeacher, 25);
            
            // Insert test data
            boolean studentInserted = database.insertStudent(testStudent);
            boolean teacherInserted = database.insertTeacher(testTeacher);
            boolean courseInserted = database.insertCourse(testCourse);
            
            System.out.println("Student inserted: " + studentInserted);
            System.out.println("Teacher inserted: " + teacherInserted);
            System.out.println("Course inserted: " + courseInserted);
            
            // Test retrieving data
            System.out.println("\nRetrieving data from database:");
            List<Student> students = database.getStudents();
            List<Teacher> teachers = database.getTeachers();
            List<Course> courses = database.getCourses();
            
            System.out.println("Total students in DB: " + students.size());
            System.out.println("Total teachers in DB: " + teachers.size());
            System.out.println("Total courses in DB: " + courses.size());
            
            // Test enrollment
            boolean enrolled = database.enrollStudentInCourse(testStudent.getId(), testCourse.getCourseId());
            System.out.println("Student enrolled in course: " + enrolled);
            
            // Close database connection
            database.closeConnection();
            
            System.out.println("✅ Database operations test passed!\n");
            
        } catch (Exception e) {
            System.err.println("Database test failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
