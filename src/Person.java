/**
 * Abstract base class representing a person in the school system
 * Demonstrates inheritance and polymorphism concepts
 */
public abstract class Person {
    protected int id;
    protected String name;
    protected int age;
    
    // Constructor
    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    // Abstract method to be implemented by subclasses (Polymorphism)
    public abstract void displayInfo();
    
    // Common method that can be overridden
    public String getBasicInfo() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age;
    }
    
    @Override
    public String toString() {
        return getBasicInfo();
    }
}
