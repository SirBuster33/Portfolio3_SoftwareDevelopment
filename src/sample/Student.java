package sample;

// Allows to save the data from the database by creating student objects,
// which is needed for displaying them in the comboBox.

public class Student {
    private String ID;
    private String Name;
    private String City;

    // Constructor
    public Student(String ID, String Name, String City){
        this.setID(ID);
        this.setName(Name);
        this.setCity(City);
    }

    // Getters and Setters.
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getCity() {
        return City;
    }
    public void setCity(String city) {
        City = city;
    }

    // Adjusted toString method that is used to display student objects in the comboBox for students.
    @Override
    public String toString() {
        return ID + ": " + Name;
    }
}
