package sample;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import static java.sql.DriverManager.getConnection;

public class DatabaseManipulator {
    Connection conn = null;
    Statement stmt;

    public void createStatement() throws SQLException {
        stmt = conn.createStatement();
    }

    public void connect(String url) throws SQLException {
        conn = getConnection(url);
    }

    public void closeConnection() throws SQLException {
        conn.close();
    }

    public ObservableList<Student> studentQueryStatement(ObservableList<Student> students) throws SQLException {
        System.out.println("Running method studentQueryStatement...");

        String sql = "SELECT * FROM student;";
        ResultSet rs = stmt.executeQuery(sql);

        if (rs == null){
            System.out.println("No records retrieved.");
        }
        while (rs != null && rs.next()){
            String ID = rs.getString(1);
            String Name = rs.getString(2);
            String City = rs.getString(3);
            System.out.println(ID + " " + Name + " " + City);
            Student student = new Student(ID, Name, City);
            students.add(student);
        }
        // NamedObject namedObject = new NamedObject(rs,students);
        return students;
    }

    public ObservableList<Course> courseQueryStatement(ObservableList<Course> courses) throws SQLException {
        System.out.println("Running method courseQueryStatement...");

        String sql = "SELECT * FROM course;";
        ResultSet rs = stmt.executeQuery(sql);

        if (rs == null){
            System.out.println("No records retrieved.");
        }
        // ID, Name, Teacher, Semester, Year
        while (rs != null && rs.next()){
            String ID = rs.getString(1);
            String Name = rs.getString(2);
            String Teacher = rs.getString(3);
            String Semester = rs.getString(4);
            Integer Year = rs.getInt(5);

            System.out.println(ID + " " + Name + " " + Teacher + " " + Semester + " " + Year);
            Course course = new Course(ID, Name, Teacher, Semester, Year);
            courses.add(course);
        }
        return courses;
    }

    public void studentInputStatement() throws SQLException {
        System.out.println("\nRunning method studentInputStatement...");

        System.out.println("Type the student ID you want info for: ");
        Scanner scanner = new Scanner(System.in);
        String studentID = scanner.nextLine();

        String sql = "SELECT * FROM student WHERE ID = '" + studentID + "';";
        ResultSet rs = stmt.executeQuery(sql);

        if (rs == null){
            System.out.println("No records retrieved.");
        }
        while (rs != null && rs.next()){
            String ID = rs.getString(1);
            String Name = rs.getString(2);
            String City = rs.getString(3);
            System.out.println("Info for Student with ID " + ID + ": " + Name + ": " + City + ".");
        }
    }

    // This method contains two queries that activate on button press, resulting in courses taken,
    // grades gotten and average grade being printed for a selected student.
    public String preparedStatementButtonStudentCourses(String StudentID) {

        String textAreaMessage = "";

        // conn is null so we connect to the database again to be sure we have a connection.
        Connect();

        System.out.println("\nRunning Button method preparedStatementButtonSearchForInfoStudent...");
        // SQL code for selecting the courses a student has taken, plus the grad they got in that course.
        String sqlInfo =    "SELECT S1.Name, G1.CourseID, G1.Grade " +
                            "FROM student AS S1 " +
                            "JOIN grade AS G1 on S1.ID = G1.StudentID " +
                            "WHERE StudentID = ?;";


        // SQL code for selecting the average grade from the database.
        String sqlAVG = "SELECT AVG (Grade) " +
                        "FROM grade " +
                        "WHERE StudentID = ?;";
        try {

            // Query for the courses taken and the grades gotten
            PreparedStatement pstmtInfo = conn.prepareStatement(sqlInfo);
            pstmtInfo.setString(1, StudentID);
            ResultSet rsInfo = pstmtInfo.executeQuery();
            System.out.println("Printing out courses taken...");

            if (rsInfo == null){
                textAreaMessage += "No records for courses retrieved.\n";
            }
            while (rsInfo != null && rsInfo.next()){
                if (rsInfo.getString(3) == null){
                    textAreaMessage += rsInfo.getString(1) + " " + rsInfo.getString(2) + " (not graded yet)\n";
                }
                else{
                    textAreaMessage += rsInfo.getString(1) + " " + rsInfo.getString(2) + " "
                            + rsInfo.getInt(3) + "\n";
                }
            }

            // Query for average grade
            PreparedStatement pstmtAVG = conn.prepareStatement(sqlAVG);
            pstmtAVG.setString(1, StudentID);
            ResultSet rsAVG = pstmtAVG.executeQuery();
            System.out.println("Printing out average grade...");

            if (rsAVG == null){
                textAreaMessage += "No records for average grade retrieved.\n";
            }
            while (rsAVG != null && rsAVG.next()){
                if (rsAVG.getString(1) == null){
                    textAreaMessage += "Student has not received grades yet.\n";
                }
                else{
                    textAreaMessage += "Student " + StudentID + " has an average grade of: "
                            + rsAVG.getInt(1) + "\n";
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return textAreaMessage;
    }

    public String preparedStatementButtonCourseGrade(String CourseID){
        String textAreaMessage = "";

        Connect();

        System.out.println("\nRunning Button for courses");


        String sqlCourseGrade = "SELECT AVG(Grade) " +
                                "FROM grade " +
                                "WHERE CourseID = ?;";

        try{
            PreparedStatement pstmtCourse = conn.prepareStatement(sqlCourseGrade);
            pstmtCourse.setString(1, CourseID);
            ResultSet rsCourse = pstmtCourse.executeQuery();

            if (rsCourse == null){
                textAreaMessage += "No records for course " + CourseID + " retrieved.\n";
            }
            while (rsCourse != null && rsCourse.next()){
                if (rsCourse.getString(1) == null){
                    textAreaMessage += "Course " + CourseID + " has not given grades yet.\n";
                }
                else {
                    textAreaMessage += "Course " + CourseID + " has an average grade of " + rsCourse.getInt(1) + "\n";
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return textAreaMessage;
    }

    private void Connect() {
        try{
            this.connect("jdbc:sqlite:C:\\Users\\Kata\\Documents\\RUC stuff from small lenovo\\Ruc\\Philipp RUC" +
                    "\\5th Semester\\Portfolio3_SoftwareDevelopment\\src\\Student_Database\\Student_Database");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
