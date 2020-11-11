package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
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

    public Button buttonSearchForInfoStudent;
    public TextArea textAreaInfoStudent;
    public String InfoStudentText;
    public Button buttonStartSearch;
    public TextArea textAreaInfoCourse;
    public String InfoCourseText;

    ObservableList<Student> students = FXCollections.observableArrayList();
    ObservableList<Course> courses = FXCollections.observableArrayList();

    public void initialize(){

        DatabaseManipulator DB = new DatabaseManipulator();

        tableStudents.setItems(students);
        comboBoxSearchStudentID.setItems(students);

        tableCourses.setItems(courses);
        comboBoxSearchCourseID.setItems(courses);

        // textAreaInfoStudent.appendText(InfoStudentText);
        // textAreaInfoCourse.appendText(InfoCourseText);

        try{
            String url = "jdbc:sqlite:C:\\Users\\Kata\\Documents\\RUC stuff from small lenovo\\Ruc\\Philipp RUC" +
                    "\\5th Semester\\Portfolio3_SoftwareDevelopment\\src\\Student_Database\\Student_Database";
            DB.connect(url);
            DB.createStatement();

            ResultSet rs;

            students = DB.studentQueryStatement(students);
            courses = DB.courseQueryStatement(courses);
            // System.out.println("Resultset is: " + rs);

            // rs = DB.studentInputStatement();
            // System.out.println("Resultset is: " + rs);

            // DB.sqlPlanPreparedStatement("C001", "C002", 12);
            // System.out.println("Resultset is: " + rs);
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error in try-catch line 37");
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

    public void searchForInfoStudent(ActionEvent actionEvent) {
        textAreaInfoStudent.clear();
        if (comboBoxSearchStudentID.getSelectionModel().getSelectedItem() == null) {
            textAreaInfoStudent.appendText("Please select a student from the list above.\n");
            return;
        }
        textAreaInfoStudent.appendText("You chose Student "
                + comboBoxSearchStudentID.getSelectionModel().getSelectedItem() + ".\n"
                + "(Student Name, Course ID, Grade)\n");
        Student student = (Student)comboBoxSearchStudentID.getSelectionModel().getSelectedItem();
        DatabaseManipulator DB = new DatabaseManipulator();
        textAreaInfoStudent.appendText(DB.preparedStatementButtonStudentCourses(student.getID()));
    }

    public void searchForInfoCourse(ActionEvent actionEvent) {
        textAreaInfoCourse.clear();
        if (comboBoxSearchCourseID.getSelectionModel().getSelectedItem() == null) {
            textAreaInfoCourse.appendText("Please select a course from the List above.\n");
            return;
        }
        textAreaInfoCourse.appendText("You chose Course "
                + comboBoxSearchCourseID.getSelectionModel().getSelectedItem() + ".\n"
                + "(Course ID, Average Grade)\n");
        Course course = (Course)comboBoxSearchCourseID.getSelectionModel().getSelectedItem();
        DatabaseManipulator DB = new DatabaseManipulator();
        textAreaInfoCourse.appendText(DB.preparedStatementButtonCourseGrade(course.getID()));
    }

}
