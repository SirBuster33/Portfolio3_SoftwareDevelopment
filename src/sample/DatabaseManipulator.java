package sample;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.transform.Result;
import java.sql.*;
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

    public ResultSet studentQueryStatement() throws SQLException {
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
        }
        return rs;
    }

    public ResultSet studentInputStatement() throws SQLException {
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
        return rs;
    }

    public ResultSet sqlPlanPreparedStatement(String courseIDS, String courseSD, Integer grade) throws SQLException {
        System.out.println("Running preparedStatement...");
        String sql = "SELECT G1.StudentID, G1.CourseID, G1.Grade, G2.CourseID, G2.Grade " +
                "FROM grade as G1 " +
                "JOIN grade as G2 on G1.StudentID = G2.StudentID " +
                "WHERE G1.CourseID = ? AND G2.CourseID = ? AND G1.Grade > ? " +
                "AND G1.Grade = G2.Grade;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, courseIDS);
        pstmt.setString(2, courseSD);
        pstmt.setInt(3, grade);
        ResultSet rs = pstmt.executeQuery();
        if (rs == null){
            System.out.println("No records retrieved");
        }
        while (rs != null && rs.next()){
            System.out.println("Printing out rs.getStuff...");
            System.out.println(rs.getString(1) + " " + rs.getString(2) + " "
                    + rs.getInt(3) + " " + rs.getString(4) + " "
                    + rs.getInt(5));
            System.out.println("Done printing out rs.getStuff.");
        }
        return rs;
    }
}
