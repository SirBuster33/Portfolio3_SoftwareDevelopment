package sample;

public class Course {
    private String ID;
    private String name;
    private String teacher;
    private String semester;
    private Integer year;

    public Course(String ID, String name, String teacher, String semester, Integer year){
        this.ID = ID;
        this.name = name;
        this.teacher = teacher;
        this.semester = semester;
        this.year = year;
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
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return ID;
    }
}
