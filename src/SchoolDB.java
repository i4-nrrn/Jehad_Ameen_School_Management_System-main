import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Database class for managing SQLite operations
 * Handles all database interactions with proper exception handling
 */
public class SchoolDB {
    private static final String DB_URL = "jdbc:sqlite:school.db";
    private Connection connection;
    
    // Constructor - establishes database connection
    public SchoolDB() throws SQLException {
        connectDB();
        createTables();
    }
    
    // Connect to SQLite database
    public void connectDB() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to SQLite database successfully.");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage(), e);
        }
    }
    
    // Create necessary tables
    private void createTables() throws SQLException {
        String createStudentsTable = "CREATE TABLE IF NOT EXISTS students (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "age INTEGER NOT NULL, " +
                "grade_level INTEGER NOT NULL" +
                ")";
        
        String createTeachersTable = "CREATE TABLE IF NOT EXISTS teachers (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "age INTEGER NOT NULL, " +
                "subject TEXT NOT NULL" +
                ")";
        
        String createCoursesTable = "CREATE TABLE IF NOT EXISTS courses (" +
                "course_id INTEGER PRIMARY KEY, " +
                "title TEXT NOT NULL, " +
                "teacher_id INTEGER, " +
                "max_capacity INTEGER NOT NULL, " +
                "FOREIGN KEY (teacher_id) REFERENCES teachers (id)" +
                ")";
        
        String createEnrollmentsTable = "CREATE TABLE IF NOT EXISTS enrollments (" +
                "student_id INTEGER, " +
                "course_id INTEGER, " +
                "PRIMARY KEY (student_id, course_id), " +
                "FOREIGN KEY (student_id) REFERENCES students (id), " +
                "FOREIGN KEY (course_id) REFERENCES courses (course_id)" +
                ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createStudentsTable);
            stmt.execute(createTeachersTable);
            stmt.execute(createCoursesTable);
            stmt.execute(createEnrollmentsTable);
            System.out.println("Database tables created successfully.");
        }
    }
    
    // Insert student into database
    public boolean insertStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (id, name, age, grade_level) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getAge());
            pstmt.setInt(4, student.getGradeLevel());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) { // SQLITE_CONSTRAINT
                throw new SQLException("Student with ID " + student.getId() + " already exists", e);
            }
            throw new SQLException("Failed to insert student: " + e.getMessage(), e);
        }
    }
    
    // Insert teacher into database
    public boolean insertTeacher(Teacher teacher) throws SQLException {
        String sql = "INSERT INTO teachers (id, name, age, subject) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, teacher.getId());
            pstmt.setString(2, teacher.getName());
            pstmt.setInt(3, teacher.getAge());
            pstmt.setString(4, teacher.getSubject());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) { // SQLITE_CONSTRAINT
                throw new SQLException("Teacher with ID " + teacher.getId() + " already exists", e);
            }
            throw new SQLException("Failed to insert teacher: " + e.getMessage(), e);
        }
    }
    
    // Insert course into database
    public boolean insertCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses (course_id, title, teacher_id, max_capacity) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, course.getCourseId());
            pstmt.setString(2, course.getTitle());
            if (course.getTeacher() != null) {
                pstmt.setInt(3, course.getTeacher().getId());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }
            pstmt.setInt(4, course.getMaxCapacity());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) { // SQLITE_CONSTRAINT
                throw new SQLException("Course with ID " + course.getCourseId() + " already exists", e);
            }
            throw new SQLException("Failed to insert course: " + e.getMessage(), e);
        }
    }
    
    // Get all students from database
    public List<Student> getStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getInt("grade_level")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve students: " + e.getMessage(), e);
        }
        
        return students;
    }
    
    // Get all teachers from database
    public List<Teacher> getTeachers() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String sql = "SELECT * FROM teachers";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Teacher teacher = new Teacher(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("subject")
                );
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve teachers: " + e.getMessage(), e);
        }
        
        return teachers;
    }
    
    // Get all courses from database
    public List<Course> getCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.course_id, c.title, c.max_capacity, " +
                "t.id as teacher_id, t.name as teacher_name, t.age as teacher_age, t.subject " +
                "FROM courses c " +
                "LEFT JOIN teachers t ON c.teacher_id = t.id";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("course_id"),
                    rs.getString("title"),
                    rs.getInt("max_capacity")
                );
                
                // Set teacher if exists
                if (rs.getInt("teacher_id") != 0) {
                    Teacher teacher = new Teacher(
                        rs.getInt("teacher_id"),
                        rs.getString("teacher_name"),
                        rs.getInt("teacher_age"),
                        rs.getString("subject")
                    );
                    course.setTeacher(teacher);
                }
                
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve courses: " + e.getMessage(), e);
        }
        
        return courses;
    }
    
    // Enroll student in course
    public boolean enrollStudentInCourse(int studentId, int courseId) throws SQLException {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) { // SQLITE_CONSTRAINT
                throw new SQLException("Student is already enrolled in this course", e);
            }
            throw new SQLException("Failed to enroll student: " + e.getMessage(), e);
        }
    }
    
    // Get student by ID
    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getInt("grade_level")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve student: " + e.getMessage(), e);
        }
    }
    
    // Get teacher by ID
    public Teacher getTeacherById(int id) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Teacher(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("subject")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve teacher: " + e.getMessage(), e);
        }
    }
    
    // Update student in database
    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET name = ?, age = ?, grade_level = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setInt(3, student.getGradeLevel());
            pstmt.setInt(4, student.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Failed to update student: " + e.getMessage(), e);
        }
    }
    
    // Update teacher in database
    public boolean updateTeacher(Teacher teacher) throws SQLException {
        String sql = "UPDATE teachers SET name = ?, age = ?, subject = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, teacher.getName());
            pstmt.setInt(2, teacher.getAge());
            pstmt.setString(3, teacher.getSubject());
            pstmt.setInt(4, teacher.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Failed to update teacher: " + e.getMessage(), e);
        }
    }
    
    // Update course in database
    public boolean updateCourse(Course course) throws SQLException {
        String sql = "UPDATE courses SET title = ?, teacher_id = ?, max_capacity = ? WHERE course_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, course.getTitle());
            if (course.getTeacher() != null) {
                pstmt.setInt(2, course.getTeacher().getId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            pstmt.setInt(3, course.getMaxCapacity());
            pstmt.setInt(4, course.getCourseId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Failed to update course: " + e.getMessage(), e);
        }
    }
    
    // Search students by name
    public List<Student> searchStudentsByName(String name) throws SQLException {
        String sql = "SELECT * FROM students WHERE name LIKE ?";
        List<Student> students = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getInt("grade_level")
                ));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to search students: " + e.getMessage(), e);
        }
        
        return students;
    }
    
    // Search teachers by name
    public List<Teacher> searchTeachersByName(String name) throws SQLException {
        String sql = "SELECT * FROM teachers WHERE name LIKE ?";
        List<Teacher> teachers = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                teachers.add(new Teacher(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("subject")
                ));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to search teachers: " + e.getMessage(), e);
        }
        
        return teachers;
    }
    
    // Search courses by title
    public List<Course> searchCoursesByTitle(String title) throws SQLException {
        String sql = "SELECT c.course_id, c.title, c.max_capacity, " +
                "t.id as teacher_id, t.name as teacher_name, t.age as teacher_age, t.subject " +
                "FROM courses c " +
                "LEFT JOIN teachers t ON c.teacher_id = t.id " +
                "WHERE c.title LIKE ?";
        List<Course> courses = new ArrayList<>();
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("course_id"),
                    rs.getString("title"),
                    rs.getInt("max_capacity")
                );
                
                // Set teacher if exists
                if (rs.getInt("teacher_id") != 0) {
                    Teacher teacher = new Teacher(
                        rs.getInt("teacher_id"),
                        rs.getString("teacher_name"),
                        rs.getInt("teacher_age"),
                        rs.getString("subject")
                    );
                    course.setTeacher(teacher);
                }
                
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to search courses: " + e.getMessage(), e);
        }
        
        return courses;
    }
    
    // Delete student from database
    public boolean deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Failed to delete student: " + e.getMessage(), e);
        }
    }
    
    // Delete teacher from database
    public boolean deleteTeacher(int id) throws SQLException {
        String sql = "DELETE FROM teachers WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Failed to delete teacher: " + e.getMessage(), e);
        }
    }
    
    // Delete course from database
    public boolean deleteCourse(int courseId) throws SQLException {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Failed to delete course: " + e.getMessage(), e);
        }
    }
    
    // Close database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    // Check if database is connected
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
