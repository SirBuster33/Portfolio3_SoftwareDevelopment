package sample;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class NamedObject<T> {
    private final ResultSet rs;
    private final ObservableList<Student> students;

    public NamedObject(ResultSet rs, ObservableList<Student> students) {
            this.rs = rs;
            this.students = students;
    }


    public ResultSet getRs() {
        return rs;
    }
    public ObservableList<Student> getStudents() {
        return students;
    }
}
