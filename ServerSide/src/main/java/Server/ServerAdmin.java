package Server;

import java.util.Scanner;
import java.util.Set;

public class ServerAdmin {
    private static final Authenticator authenticator = new Authenticator("users");
    private static final Scanner in = new Scanner(System.in);

    private enum Action {
        add("Add a new user"),
        delete("Delete a user"),
        list("List all users"),
        exit("Exit");

        String actionDescription;
        Action(String actionDescription) {
            this.actionDescription = actionDescription;
        }

        @Override
        public String toString() {
            return name() + ": " + actionDescription;
        }
    }

    public static void main(String[] args) {
        printPrompt();
        while(true) {
            System.out.print(">>> ");
            String actionString = in.nextLine().trim();
            try {
                Action action = Action.valueOf(actionString);
                performAction(action);
            } catch (InvalidCredentialsException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid operation: " + actionString);
            }
        }
    }

    private static void performAction(Action action) {
        switch (action) {
            case add:
                addUser();
                break;
            case delete:
                removeUser();
                break;
            case list:
                listUsers();
                break;
            case exit:
                System.exit(0);
        }
    }

    private static void printPrompt() {
        System.out.println("What action do you want to perform?");
        for(Action action : Action.values()) {
            System.out.println(action);
        }
    }

    private static void addUser() {
        System.out.println("Enter a new username: ");
        String username = in.nextLine().trim();
        System.out.println("Enter a password: ");
        String password = in.nextLine().trim();
        authenticator.addUser(username, password);
    }

    private static void removeUser() {
        System.out.println("Enter the username of the user to be deleted: ");
        String username = in.nextLine().trim();
        authenticator.deleteUser(username);
    }

    private static void listUsers() {
        Set<String> users = authenticator.getAllUsernames();
        for(String username : users)
            System.out.println(username);
    }
}
