
public class Reader {

    private int id;
    private String firstName;
    private String lastName;

    public Reader(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void displayInfo(){
        System.out.println("ID: " + id);
        System.out.println("Imię: " + firstName);
        System.out.println("Nazwisko: " + lastName);
    }

}
