package NewPhoneBook;

public class Person {

    protected String name;
    protected String number;

//    Person(String name, String number) {
//     setName(name);
//     setNumber(number);
//    }

    public String getName() {
        return this.name;
    }

    void setName(String newName) {
        this.name = newName;
    }


    public String getNumber() {
        return this.number;
    }

    void setNumber(String newNumber) {
        this.number = newNumber;
    }
}



