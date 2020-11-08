package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class Controller {


    public TableView tableStudents;
    public TableColumn tableColumnStudentID;
    public TableColumn tableColumnStudentName;
    public TableColumn tableColumnStudentCity;

    ObservableList<Student> students = FXCollections.observableArrayList();

    public void initialize(){DatabaseManipulator DB = new DatabaseManipulator();

        tableStudents.setItems(students);
        try{
            String url = "jdbc:sqlite:C:\\Users\\Kata\\Documents\\RUC stuff from small lenovo\\Ruc\\Philipp RUC" +
                    "\\5th Semester\\Portfolio3_SoftwareDevelopment\\src\\Student_Database\\Student_Database";
            DB.connect(url);
            DB.createStatement();

            ResultSet rs;
            System.out.println("Running method studentQueryStatement...");
            NamedObject namedObject = DB.studentQueryStatement(students);
            rs = namedObject.getRs();
            students = namedObject.getStudents();
            System.out.println("\nRunning method studentInputStatement...");
            rs = DB.studentInputStatement();
            System.out.println("\nRunning method sqlPlanPreparedStatement... (Not working as intended)");
            rs = DB.sqlPlanPreparedStatement("C1", "C3", 7);
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error in try-catch line 37");
        }
        finally {
            if (DB.conn != null){
                try{
                    System.out.println("Closing connection...");
                    DB.closeConnection();
                } catch (SQLException e){
                    e.printStackTrace();
                    System.out.println("Error in 'finally'!");
                }
            }
        }


        tableColumnStudentID.setCellValueFactory(new PropertyValueFactory<Student, String>("ID"));
        tableColumnStudentName.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));
        tableColumnStudentCity.setCellValueFactory(new PropertyValueFactory<Student, String>("City"));

    }
}
