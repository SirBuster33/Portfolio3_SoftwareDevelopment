package sample;
import javafx.collections.ObservableList;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class DatabaseManipulator {
    Connection conn = null;
    Statement stmt;

    // Allows to create statements which are required for a query of SQL code.
    public void createStatement() throws SQLException {
        stmt = conn.createStatement();
    }

    // Enables the connection to a database.
    public void connect(String url) throws SQLException {
        conn = getConnection(url);
    }

    // Enables a connection and is used just within this class.
    private void Connect() {
        try{
            this.connect("jdbc:sqlite:C:\\Users\\Kata\\Documents\\RUC_stuff_from_small_lenovo\\Ruc\\Philipp_RUC" +
                    "\\5th_Semester\\Portfolio3_SoftwareDevelopment\\src\\Student_Database\\Student_Database");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Allows to close the connection to the database, which is not needed in the program since we want a connection
    // for as long as the gui is running.
    public void closeConnection() throws SQLException {
        conn.close();
    }

    // Updates the course data to fit the initial dataset.
    public void clearCourseUpdate() throws SQLException {
        System.out.println("\nClearing course grades...");

        String sql = "UPDATE grade SET Grade = NULL WHERE CourseID = 'C003' AND Grade IS NOT NULL;";
        Integer rsClearCourse = stmt.executeUpdate(sql);

        if (rsClearCourse != null){
            System.out.println("Changed " + rsClearCourse +  " rows.");
        }
    }

    // Imports the student data where the students have not received a grade yet.
    public ObservableList<Student> nullStudentQueryStatement(ObservableList<Student> nullStudents) throws SQLException {
        System.out.println("\nFetching nullStudents...");

        String sql = "SELECT S1.ID, S1.Name, S1.City, G1.Grade " +
                "FROM student AS S1 " +
                "JOIN grade AS G1 ON S1.ID = G1.StudentID " +
                "WHERE Grade IS NULL;";
        ResultSet rsNullStudents = stmt.executeQuery(sql);

        if (rsNullStudents == null){
            System.out.println("No records retrieved.");
        }
        while (rsNullStudents != null && rsNullStudents.next()){
            String ID = rsNullStudents.getString(1);
            String Name = rsNullStudents.getString(2);
            String City = rsNullStudents.getString(3);
            System.out.println(ID + " " + Name + " " + City);
            Student student = new Student(ID, Name, City);
            nullStudents.add(student);
        }
        return nullStudents;
    }

    // Imports the Course data where a course has not given grades yet.
    public ObservableList<Course> nullCourseQueryStatement(ObservableList<Course> nullCourses) throws SQLException {
        System.out.println("\nFetching nullCourses...");

        String sql = "SELECT DISTINCT C1.ID, C1.Name, C1.Teacher, C1.Semester, C1.Year " +
                     "FROM course AS C1 " +
                     "JOIN grade G1 ON C1.ID = G1.CourseID " +
                     "WHERE Grade IS NULL;";
        ResultSet rsNullCourses = stmt.executeQuery(sql);

        if (rsNullCourses == null){
            System.out.println("No records retrieved.");
        }
        while (rsNullCourses != null && rsNullCourses.next()){
            String ID = rsNullCourses.getString(1);
            String Name = rsNullCourses.getString(2);
            String Teacher = rsNullCourses.getString(3);
            String Semester = rsNullCourses.getString(4);
            Integer Year = rsNullCourses.getInt(5);

            System.out.println(ID + " " + Name + " " + Teacher + " " + Semester + " " + Year);
            Course course = new Course(ID, Name, Teacher, Semester, Year);
            nullCourses.add(course);
        }
        return nullCourses;
    }

    // Imports the student data in Initialize.
    public ObservableList<Student> studentQueryStatement(ObservableList<Student> students) throws SQLException {
        System.out.println("\nRunning method studentQueryStatement. Importing student data. Creating student objects...");

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
        return students;
    }

    // Imports the course data in Initialize.
    public ObservableList<Course> courseQueryStatement(ObservableList<Course> courses) throws SQLException {
        System.out.println("\nRunning method courseQueryStatement. Importing course data. Creating course objects...");

        String sql = "SELECT * FROM course;";
        ResultSet rs = stmt.executeQuery(sql);

        if (rs == null){
            System.out.println("No records retrieved.");
        }
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

    // Allows for retrieval of the student info (Student name, Courses taken, grade and average grade) based on
    // a chosen student in the comboBox for students.
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
                            + rsInfo.getFloat(3) + "\n";
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

    // Allows for retrieval of the average grade based on a chosen course in the comboBox for courses.
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
                    textAreaMessage += "Course " + CourseID + " has an average grade of " + rsCourse.getFloat(1) + "\n";
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return textAreaMessage;
    }

    // Updates a grade based on the comboBox choices when inserting a grade.
    public String preparedStatementButtonInsertGrade(String StudentID, String CourseID, Integer Grade){
        String textAreaMessage = "";

        Connect();

        System.out.println("\nRunning Button for inserting grades...");


        String sqlInsertGrade = "UPDATE grade SET Grade = ? WHERE StudentID = ? AND CourseID = ? AND Grade IS NULL;";

        try{
            PreparedStatement pstmtInsertGrade = conn.prepareStatement(sqlInsertGrade);
            pstmtInsertGrade.setInt(1, Grade);
            pstmtInsertGrade.setString(2, StudentID);
            pstmtInsertGrade.setString(3, CourseID);
            Integer rsInsertGrade = pstmtInsertGrade.executeUpdate();

            if (rsInsertGrade == 0){
                textAreaMessage += "Student " + StudentID + " has already received a grade in course "
                        + CourseID + ".\n";
            }
            if (rsInsertGrade == 1){
                textAreaMessage += "Added new grade for Student " + StudentID + " in course " + CourseID
                        + ": " + Grade + "\n" ;
            }
            else if (rsInsertGrade > 1){
                textAreaMessage += "Error: Too many grades added. " +
                        "Please save the settings above and consult your IT department.\n";
            }
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return textAreaMessage;
    }

}
