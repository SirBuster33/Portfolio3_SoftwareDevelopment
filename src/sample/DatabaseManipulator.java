package sample;
import javafx.scene.control.cell.PropertyValueFactory;

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

    public ResultSet studentQueryStatement() throws SQLException {

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
}
