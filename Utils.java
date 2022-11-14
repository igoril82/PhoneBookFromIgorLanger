package NewPhoneBook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.reverseOrder;

public class Utils extends Person {


    /**
     * It includes the path where a file with all the data is saved.
     */
    private final String DATA_PATH = "D:/Java/contacts.csv";


    /**
     * This is a function that saves all data in an external file
     */
    protected void saveContacts(Map<String, List<String>> contacts) {
        try (PrintWriter writer = new PrintWriter(DATA_PATH)) {
            if (!contacts.isEmpty()) {
                for (Map.Entry<String, List<String>> entry : contacts.entrySet()) {
                    String line = String.format("%s,\"%s\"",
                            entry.getKey(), entry.getValue().toString().replaceAll("\\[|]", ""));
                    writer.println(line);
                }
            }

        } catch (IOException ioex) {
            System.err.println(ioex.getMessage());
        }
    }

    /**
     * This is a function that loads the data from an external file.
     * During the process, the integrity of the file is also checked
     */
    protected void loadContacts(Map<String, List<String>> contacts) {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_PATH))) {

            Pattern pattern = Pattern.compile("^([^,\"]{4,30}),\"([0-9+, ]+)\"$");

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String[] numbers = matcher.group(2).split(",\\s*");
                    contacts.put(matcher.group(1), Arrays.asList(numbers));
                }
            }

        } catch (IOException ioex) {
            System.err.println("Could not load contacts, phone book is empty!");
        }
    }

    /**
     * This is a function that returns a main menu.
     * It's shows all the commands that a user can run in the program.
     */
    protected void listCommands() {
        System.out.println("list - lists all saved contacts in alphabetical  order");
        System.out.println("show - finds a contact by name");
        System.out.println("find - searches for a contact by number");
        System.out.println("add - saves a new contact entry into the phone book");
        System.out.println("edit - modifies an existing contact");
        System.out.println("delete - removes a contact from the phone book");
        System.out.println("help - lists all valid commands");
        System.out.println("---------------------------");
    }

    /**
     * This is a function that returns a list of all entries in the book.
     * In case there is no record in the book, a detailed  message appears.
     * In addition, there is a function of sorting the name values in reverse order.
     */

    protected void listContacts(Map<String, List<String>> contacts) {
        System.out.println("Below are all the contacts that appear in the phone book in alphabetical  order.");
        if (!contacts.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : contacts.entrySet()) {
                if (contacts.equals(contacts.entrySet())) {
                    System.out.println("These are doubles values in phone book");
                    System.out.println(contacts.entrySet());
                }
                System.out.println("Name: " + entry.getKey());

                for (String number : entry.getValue()) {
                    System.out.println("Number: " + number);
                }
                System.out.println();
            }
        } else {
            System.out.println("No records found, the phone book is empty!");
        }
        try {
            confirm:
            while (true) {
                System.out.println("Do you want arrangement the contacts in reverse order [Y/N] ?");
                Scanner input = new Scanner(System.in);
                String confirmation = input.nextLine().trim().toLowerCase();
                switch (confirmation) {
                    case "y":
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(contacts.entrySet().toString());
                        Collections.sort(list, reverseOrder());
                        System.out.println("Records of names after arrangement in reverse order");
                        System.out.println(list);
                        break confirm;
                    case "n":
                        System.out.println();
                        break confirm;
                }
            }

        } catch (Exception e) {
            System.out.println("You typed an invalid parameter, returns to the main menu");
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    /**
     * This is a function that displays the phone number by contact name
     */
    protected void showContact(Map<String, List<String>> contacts, Scanner input) {
        System.out.println("Enter the name you are looking for:");
        setName(input.nextLine().trim());

        if (contacts.containsKey(getName())) {
            System.out.println(getName());
            for (String number : contacts.get(getName())) {
                System.out.println(number);
            }
        } else {
            System.out.println("Sorry, nothing found!");
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    /**
     * This is a function where you can search a name by user number.
     * And also what a user wants to do with the value that found after a search
     */

    protected void findContact(Map<String, List<String>> contacts, Scanner input) {
        System.out.println("Enter a number to see to whom does it belong:");
        String number = input.nextLine().trim();

        while (!number.matches("^\\+?[0-9 ]{3,25}$")) {
            System.out.println("Invalid number! May contain only digits, spaces and '+'. Min length 3, max length 25.");
            System.out.println("Enter number:");
            number = input.nextLine().trim();
        }

        for (Map.Entry<String, List<String>> entry : contacts.entrySet()) {
            if (entry.getValue().contains(number)) {
                System.out.println("For  number " + getNumber() + " was found contact name " + entry.getKey() + " in the phone book");
            }
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    /**
     * His is a function that adds a new number.
     * At this stage, we check how many characters are in the name
     * In addition, a typed phone number is also checked
     */
    protected void addContact(Map<String, List<String>> contacts, Scanner input) {

        System.out.println("You are about to add a new contact to the phone book.");
        int counterOfName = 0;
        int counterOfNumber = 0;


        while (true) {
            System.out.println("Enter contact name:");
            setName(input.nextLine().trim());


            if (getName().matches("^.{2,30}$")) {
                break;
            } else {
                counterOfName++;
                System.out.println("Name must be in range 2 - 30 symbols.");
                if (counterOfName >= 3) {
                    System.out.println("You have tried to enter an incorrect name more than 3 times. Phone Book is terminated now");
                    System.exit(0);
                }
            }
        }

        while (true) {
            System.out.println("Enter contact number:");
            setNumber(input.nextLine().trim());
            if (getNumber().matches("^\\+?[0-9 ]{3,15}$")) {
                break;
            } else {
                counterOfNumber++;
                System.out.println("Number may contain only '+', spaces and digits. Min length 3, max length 15.");
                if (counterOfNumber >= 3) {
                    System.out.println("You have tried to enter an incorrect number more than 3 times. Phone Book is terminated now");
                    System.exit(0);
                }
            }
        }

        try {
            if (contacts.containsKey(getName())) {
                System.out.printf("'%s' already exists in the phone book!\n", getName());

                if (contacts.get(getName()).contains(getNumber())) {
                    System.out.printf("Number %s already available for contact '%s'.\n", getNumber(), getName());
                } else {
                    contacts.get(getName()).add(getNumber());
                    saveContacts(contacts);
                    System.out.printf("Successfully added number %s for contact '%s'.\n", getNumber(), getName());
                }

            } else {
                List<String> numbers = new ArrayList<>();
                numbers.add(getNumber());
                contacts.put(getName(), numbers);
                saveContacts(contacts);
                System.out.printf("Successfully added contact '%s' !\n", getName());
            }
        } catch (Exception e) {
            System.out.println("Return to main menu now");
        }
        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }


    /**
     * This is a function where you can edit a number by name.
     * In addition, it is checked whether a typed name exists in the file
     * And also what a user wants to do with the value that found after a search
     */
    protected void editContact(Map<String, List<String>> contacts, Scanner input) {
        System.out.println("Enter name of the contact you would like to modify:");
        setName(input.nextLine().trim());

        if (contacts.containsKey(getName())) {
            List<String> numbers = new ArrayList<>(contacts.get(getName()));
            System.out.printf("Current number(s) for %s:\n", getName());
            for (String number : numbers) {
                System.out.println(number);
            }
            System.out.println();
            System.out.println("Would you like to add a new number or delete an existing number for this contact? [add/delete/cancel]");
            String editOption = input.nextLine().trim().toLowerCase();
            boolean addNumber = false;
            boolean delNumber = false;

            option:
            while (true) {
                switch (editOption) {
                    case "add":
                        addNumber = true;
                        break option;
                    case "delete":
                        delNumber = true;
                        break option;
                    case "cancel":
                        System.out.println("Contact was not modified!");
                        break option;
                    default:
                        System.out.println("Use 'add' to save a new number, 'delete' to remove an existing number or 'cancel' to go back.");
                        editOption = input.nextLine().trim().toLowerCase();
                        break;
                }
            }

            if (addNumber) {
                while (true) {
                    System.out.println("Enter new number:");
                    String number = input.nextLine().trim();
                    if (number.matches("^\\+?[0-9 ]{3,25}$")) {
                        contacts.get(getNumber()).add(number);
                        saveContacts(contacts);
                        System.out.printf("Number %s was successfully added, record updated!\n", getName());
                        break;
                    } else {
                        System.out.println("Number may contain only '+', spaces and digits. Min length 3, max length 25.");
                    }
                }
            }

            if (delNumber) {
                while (true) {
                    System.out.println("Enter the number you want to delete:");
                    setNumber(input.nextLine().trim());
                    if (numbers.contains(getNumber())) {
                        numbers.remove(getNumber());
                        contacts.put(getName(), numbers);
                        saveContacts(contacts);
                        System.out.printf("Number %s was removed from the record for '%s'\n", getNumber(), getName());
                        break;
                    } else {
                        System.out.printf("Number does not exist! Current number(s) for %s:\n", getName());
                        for (String num : numbers) {
                            System.out.println(num);
                        }
                    }
                }
            }

        } else {
            System.out.println("Sorry, name not found!");
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }


    /**
     * This is a function that deletes a number from the phone book.
     * There is a validation check to see if the name exists in the phone book.
     */
    protected void deleteContact(Map<String, List<String>> contacts, Scanner input) {
        System.out.println("Enter name of the contact to be deleted:");
        setName(input.nextLine().trim());

        if (contacts.containsKey(getName())) {
            System.out.printf("Contact '%s' will be deleted. Are you sure? [Y/N]:\n", getName());
            String confirmation = input.nextLine().trim().toLowerCase();
            confirm:
            while (true) {
                switch (confirmation) {
                    case "y":
                        contacts.remove(getName());
                        saveContacts(contacts);
                        System.out.println("Contact was deleted successfully!");
                        break confirm;
                    case "n":
                        System.out.printf("Contact '%s' was not deleted from phone book.\n", getName());
                        break confirm;
                    default:
                        System.out.println("Delete contact? [Y/N]:");
                        break;
                }
                confirmation = input.nextLine().trim().toLowerCase();
            }

        } else {
            System.out.printf("Sorry, the name '%s' not found in the phone book:\n", getName());
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    protected void main() {
        /**
         * This is a function that returns a main menu.
         * It's shows all the commands that a user can run in the program.
         */

        System.out.println("        PHONE BOOK");
        System.out.println("===============================");
        System.out.println("Type a command or 'exit' to quit:");
        listCommands();
        System.out.print("> ");

        Map<String, List<String>> contacts = new TreeMap<>();
        loadContacts(contacts);

        Scanner input = new Scanner(System.in);
        String line = input.nextLine().trim();

        while (!line.equals("exit")) {

            switch (line) {
                case "list":
                    listContacts(contacts);
                    break;
                case "show":
                    showContact(contacts, input);
                    break;
                case "find":
                    findContact(contacts, input);
                    break;
                case "add":
                    addContact(contacts, input);
                    break;
                case "edit":
                    editContact(contacts, input);
                    break;
                case "delete":
                    deleteContact(contacts, input);
                    break;
                case "help":
                    listCommands();
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
            System.out.print("\n> ");
            line = input.nextLine().trim();
        }
        System.out.println("'Phone Book is terminated now.");
    }
}



