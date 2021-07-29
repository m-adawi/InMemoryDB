package Common;

import java.util.Scanner;

public class UserInput {
    private final Scanner in = new Scanner(System.in);

    public String prompt() {
        System.out.print(">>> ");
        return in.nextLine().trim();
    }

    public String prompt(String promptMessage) {
        System.out.println(promptMessage);
        return in.nextLine().trim();
    }
}
