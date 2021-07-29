package Server;

import Common.UserInput;

import java.util.Scanner;
import java.util.Set;

public class ServerAdmin {
    private static final Authenticator authenticator = new Authenticator(ServerConfigurations.getConfigurations().getUsersDirectory());
    private static final UserInput in = new UserInput();

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
            String actionString = in.prompt();
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
        String username = in.prompt("Enter a new username:");
        String password = in.prompt("Enter a password:");
        authenticator.addUser(username, password);
    }

    private static void removeUser() {
        String username = in.prompt("Enter the username of the user to be deleted:");
        authenticator.deleteUser(username);
    }

    private static void listUsers() {
        Set<String> users = authenticator.getAllUsernames();
        for(String username : users)
            System.out.println(username);
    }
}
