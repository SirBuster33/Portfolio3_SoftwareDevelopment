package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
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

    public TableView tableCourses;
    public TableColumn tableColumnCourseID;
    public TableColumn tableColumnCourseName;
    public TableColumn tableColumnCourseTeachers;
    public TableColumn tableColumnCourseSemester;
    public TableColumn tableColumnCourseYear;

    public ComboBox comboBoxSearchStudentID;
    public ComboBox comboBoxSearchCourseID;

    ObservableList<Student> students = FXCollections.observableArrayList();
    ObservableList<Course> courses = FXCollections.observableArrayList();

    public void initialize(){DatabaseManipulator DB = new DatabaseManipulator();

        tableStudents.setItems(students);
        comboBoxSearchStudentID.setItems(students);

        tableCourses.setItems(courses);
        comboBoxSearchCourseID.setItems(courses);

        try{
            String url = "jdbc:sqlite:C:\\Users\\Kata\\Documents\\RUC stuff from small lenovo\\Ruc\\Philipp RUC" +
                    "\\5th Semester\\Portfolio3_SoftwareDevelopment\\src\\Student_Database\\Student_Database";
            DB.connect(url);
            DB.createStatement();

            ResultSet rs;

            students = DB.studentQueryStatement(students);
            courses = DB.courseQueryStatement(courses);
            /*rs = namedObject.getRs();
            students = namedObject.getStudents();*/
            // System.out.println("Resultset is: " + rs);

            // rs = DB.studentInputStatement();
            // System.out.println("Resultset is: " + rs);

            // rs = DB.sqlPlanPreparedStatement("C1", "C3", 7);
            // System.out.println("Resultset is: " + rs);
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

        tableColumnCourseID.setCellValueFactory(new PropertyValueFactory<Student, String>("ID"));
        tableColumnCourseName.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));
        tableColumnCourseTeachers.setCellValueFactory(new PropertyValueFactory<Student, String>("Teacher"));
        tableColumnCourseSemester.setCellValueFactory(new PropertyValueFactory<Student, String>("Semester"));
        tableColumnCourseYear.setCellValueFactory(new PropertyValueFactory<Student, String>("Year"));

    }


    public void searchForInfo(ActionEvent actionEvent) {
    }
}
