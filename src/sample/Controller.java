package sample;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {


    public TableView tableStudents;
    public TableColumn tableColumnStudentID;
    public TableColumn tableColumnStudentName;
    public TableColumn tableColumnStudentCity;

    public void initialize(){DatabaseManipulator DB = new DatabaseManipulator();

        try{
            String url = "jdbc:sqlite:C:\\Users\\Kata\\Documents\\RUC stuff from small lenovo\\Ruc\\Philipp RUC" +
                    "\\5th Semester\\Portfolio3_SoftwareDevelopment\\src\\Student_Database\\Student_Database";
            DB.connect(url);
            DB.createStatement();

            ResultSet rs = DB.studentQueryStatement();
        }
        catch (SQLException e){
            e.printStackTrace();
        }


        tableColumnStudentID.setCellValueFactory(new PropertyValueFactory<Student, String>("ID"));
        tableColumnStudentName.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));
        tableColumnStudentCity.setCellValueFactory(new PropertyValueFactory<Student, String>("City"));

    }
}
