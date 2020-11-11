package sample;

// Allows to save the data from the database by creating course objects,
// which is needed for displaying them in the comboBox.

public class Course {
    private String ID;
    private String name;
    private String teacher;
    private String semester;
    private Integer year;

    // Constructor
    public Course(String ID, String name, String teacher, String semester, Integer year){
        this.ID = ID;
        this.name = name;
        this.teacher = teacher;
        this.semester = semester;
        this.year = year;
    }

    // Getters and Setters.
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public String getSemester() {
        return semester;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    // Adjusted toString method that is used to display course objects in the comboBox for courses.
    @Override
    public String toString() {
        return ID + ": " + name;
    }
}
