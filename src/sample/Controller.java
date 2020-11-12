package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {

    // Used to display the imported Students.
    public TableView tableStudents;
    public TableColumn tableColumnStudentID;
    public TableColumn tableColumnStudentName;
    public TableColumn tableColumnStudentCity;

    // Used to display the imported Courses.
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
    public Button buttonStartSearch;
    public TextArea textAreaInfoCourse;

    ObservableList<Student> students = FXCollections.observableArrayList();
    ObservableList<Course> courses = FXCollections.observableArrayList();

    // Enables Scenebuilder to set up the GUI with necessary relationships.
    public void initialize(){

        DatabaseManipulator DB = new DatabaseManipulator();

        // Store the arraylists containing data in the appropriate tableViews and comboBoxes.
        tableStudents.setItems(students);
        comboBoxSearchStudentID.setItems(students);
        tableCourses.setItems(courses);
        comboBoxSearchCourseID.setItems(courses);

        // Attempt to connect to a database with a set url and retrieve the data necessary for setup.
        try{
            String url = "jdbc:sqlite:C:\\Users\\Kata\\Documents\\RUC stuff from small lenovo\\Ruc\\Philipp RUC" +
                    "\\5th Semester\\Portfolio3_SoftwareDevelopment\\src\\Student_Database\\Student_Database";
            DB.connect(url);
            DB.createStatement();

            // Imports the Student and Course tables from the Database to use for selection in the comboBoxes.
            students = DB.studentQueryStatement(students);
            courses = DB.courseQueryStatement(courses);
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error in try-catch line 37");
        }

        // Display the student objects in the different columns of the tableView.
        tableColumnStudentID.setCellValueFactory(new PropertyValueFactory<Student, String>("ID"));
        tableColumnStudentName.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));
        tableColumnStudentCity.setCellValueFactory(new PropertyValueFactory<Student, String>("City"));

        // Display the course objects in the different columns of the tableView.
        tableColumnCourseID.setCellValueFactory(new PropertyValueFactory<Student, String>("ID"));
        tableColumnCourseName.setCellValueFactory(new PropertyValueFactory<Student, String>("Name"));
        tableColumnCourseTeachers.setCellValueFactory(new PropertyValueFactory<Student, String>("Teacher"));
        tableColumnCourseSemester.setCellValueFactory(new PropertyValueFactory<Student, String>("Semester"));
        tableColumnCourseYear.setCellValueFactory(new PropertyValueFactory<Student, String>("Year"));

    }

    // Activates on pressing the button to fetch the info for a selected student
    // and displays it in the appropriate textArea.
    public void searchForInfoStudent(ActionEvent actionEvent) {
        textAreaInfoStudent.clear();
        // Error handling in case the button is pressed while no item is selected yet.
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

    // Activates on pressing the button to fetch the average grade for a selected course
    // and displays it in the appropriate textArea.
    public void searchForInfoCourse(ActionEvent actionEvent) {
        textAreaInfoCourse.clear();
        // Error handling in case the button is pressed while no item is selected yet.
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
