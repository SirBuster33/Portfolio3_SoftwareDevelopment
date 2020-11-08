package sample;

public class Course {
    private String ID;
    private String name;
    private String teacher;
    private String semester;
    private String year;

    public Course(String ID, String name, String teacher, String semester, String year){
        this.setID(ID);
        this.setName(name);
        this.setTeacher(teacher);
        this.setSemester(semester);
        this.setYear(year);
    }

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
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
}
