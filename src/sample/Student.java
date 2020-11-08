package sample;

public class Student {
    private String ID;
    private String Name;
    private String City;

    public Student(String ID, String Name, String City){
        this.setID(ID);
        this.setName(Name);
        this.setCity(City);
    }

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
}
