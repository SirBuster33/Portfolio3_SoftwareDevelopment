package sample;

import javafx.collections.ObservableList;

import java.sql.ResultSet;


// This class serves the purpose of returning multiple objects as one object.
// This is a workaround to the limitation of the return command that allows for just one object to be returned.
// Inspired by Joachim Sauer (First answer to the problem):
// https://stackoverflow.com/questions/457629/how-to-return-multiple-objects-from-a-java-method

public class NamedObject<T> {
    private final ResultSet rs;
    private final ObservableList<Student> students;
    private final ObservableList<Course> courses;
    private final ObservableList<Grade> grades;

    public NamedObject(ResultSet rs, ObservableList<Student> students) {
        this.rs = rs;
        this.students = students;
        this.courses = null;
        this.grades = null;
    }/*
    public NamedObject(ResultSet rs, ObservableList<Course> courses) {
        this.rs = rs;
        this.students = null;
        this.courses = courses;
        this.grades = null;
    }
    public NamedObject(ResultSet rs, ObservableList<Grade> grades) {
        this.rs = rs;
        this.students = null;
        this.courses = null;
        this.grades = grades;
    }*/


    public ResultSet getRs() {
        return rs;
    }
    public ObservableList<Student> getStudents() {
        return students;
    }
}
