package sample;

public class Grade {
    private String studentID;
    private String courseID;
    private Integer grade;

    public Grade (String studentID, String courseID, Integer grade){
        this.studentID = studentID;
        this.courseID = courseID;
        this.grade = grade;
    }

    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        studentID = studentID;
    }
    public String getCourseID() {
        return courseID;
    }
    public void setCourseID(String courseID) {
        courseID = courseID;
    }
    public Integer getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        grade = grade;
    }

    @Override
    public String toString() {
        if (grade == 0){
            return "00";
        }
        else if (grade == 2){
            return "02";
        }
        else{
            return "" + grade;
        }
    }
}
