package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    public ComboBox comboBoxStudentsInsertGrade;
    public ComboBox comboBoxCoursesInsertGrade;
    public ComboBox comboBoxGradesInsertGrade;

    public Button buttonSearchForInfoStudent;
    public TextArea textAreaInfoStudent;
    public Button buttonStartSearch;
    public TextArea textAreaInfoCourse;
    public Button buttonInsertGrade;
    public TextArea textAreaInsertGrade;


    ObservableList<Student> students = FXCollections.observableArrayList();
    ObservableList<Course> courses = FXCollections.observableArrayList();
    ObservableList<Student> nullGradeStudents = FXCollections.observableArrayList();
    ObservableList<Course> nullGradeCourses = FXCollections.observableArrayList();
    ObservableList<Grade> danishGradingScale = FXCollections.observableArrayList();

    // Enables Scenebuilder to set up the GUI with necessary relationships.
    public void initialize(){

        DatabaseManipulator DB = new DatabaseManipulator();

        // Store the arraylists containing data in the appropriate tableViews and comboBoxes.
        tableStudents.setItems(students);
        comboBoxSearchStudentID.setItems(students);
        tableCourses.setItems(courses);
        comboBoxSearchCourseID.setItems(courses);
        comboBoxStudentsInsertGrade.setItems(nullGradeStudents);
        comboBoxCoursesInsertGrade.setItems(nullGradeCourses);
        // Adds the danish grades as an option for the grade comboBox.
        comboBoxGradesInsertGrade.setItems(danishGradingScale);
        danishGradingScale = addDanishGrades(danishGradingScale);


        // Attempt to connect to a database with a set url and retrieve the data necessary for setup.
        try{
            String url = "jdbc:sqlite:C:\\Users\\Kata\\Documents\\RUC_stuff_from_small_lenovo\\Ruc\\Philipp_RUC" +
                    "\\5th_Semester\\Portfolio3_SoftwareDevelopment\\src\\Student_Database\\Student_Database";
            DB.connect(url);
            DB.createStatement();

            // Resets the grades in C003 back to null (Default state of the data).
            DB.clearCourseUpdate();

            // Imports the Student and Course tables from the Database to use for selection in the comboBoxes.
            students = DB.studentQueryStatement(students);
            courses = DB.courseQueryStatement(courses);
            nullGradeStudents = DB.nullStudentQueryStatement(nullGradeStudents);
            nullGradeCourses = DB.nullCourseQueryStatement(nullGradeCourses);

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
        Student student = (Student) comboBoxSearchStudentID.getSelectionModel().getSelectedItem();
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
        Course course = (Course) comboBoxSearchCourseID.getSelectionModel().getSelectedItem();
        DatabaseManipulator DB = new DatabaseManipulator();
        textAreaInfoCourse.appendText(DB.preparedStatementButtonCourseGrade(course.getID()));
    }

    public void insertGrade(ActionEvent actionEvent) {
        textAreaInsertGrade.clear();
        if (comboBoxStudentsInsertGrade.getSelectionModel().getSelectedItem() == null ||
                comboBoxCoursesInsertGrade.getSelectionModel().getSelectedItem() == null  ||
                comboBoxGradesInsertGrade.getSelectionModel().getSelectedItem() == null){
            textAreaInsertGrade.appendText("Please make sure that all boxes have an item selected.\n");
            return;
        }
        textAreaInsertGrade.appendText("You chose Student "
                + comboBoxStudentsInsertGrade.getSelectionModel().getSelectedItem() + "\nand course "
                + comboBoxCoursesInsertGrade.getSelectionModel().getSelectedItem() + " to receive grade "
                + comboBoxGradesInsertGrade.getSelectionModel().getSelectedItem() + ".\n");
        Student student = (Student) comboBoxStudentsInsertGrade.getSelectionModel().getSelectedItem();
        Course course = (Course) comboBoxCoursesInsertGrade.getSelectionModel().getSelectedItem();
        Grade grade = (Grade) comboBoxGradesInsertGrade.getSelectionModel().getSelectedItem();
        DatabaseManipulator DB = new DatabaseManipulator();
        textAreaInsertGrade.appendText(DB.preparedStatementButtonInsertGrade(
                student.getID(), course.getID(), grade.getGrade()));

    }

    public ObservableList<Grade> addDanishGrades(ObservableList<Grade> danishGradingScale){
        Grade unacceptable = new Grade("","", -3);
        danishGradingScale.add(unacceptable);
        Grade inadequate = new Grade("","", 00);
        danishGradingScale.add(inadequate);
        Grade adequate = new Grade("","", 02);
        danishGradingScale.add(adequate);
        Grade fair = new Grade("","", 4);
        danishGradingScale.add(fair);
        Grade good = new Grade("","", 7);
        danishGradingScale.add(good);
        Grade veryGood = new Grade("","", 10);
        danishGradingScale.add(veryGood);
        Grade excellent = new Grade("","", 12);
        danishGradingScale.add(excellent);
        return danishGradingScale;
    }
}
