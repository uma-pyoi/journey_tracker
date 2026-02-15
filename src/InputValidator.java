import java.util.Scanner;

public class InputValidator {
    Scanner scanner = new Scanner(System.in);

    // Kvasnovsky (2025)
    int readIntInRange(int min, int max) {
        int userInput = min - 1;
        while (userInput < min || userInput > max) {
            System.out.print("Enter a whole number (" + min + "-" + max + "): ");

            while (!scanner.hasNextInt()) {
                System.out.print("That's not a whole number! Try again: ");
                scanner.next();
            }

            userInput = scanner.nextInt();
            if (userInput < min || userInput > max) {
                System.out.print("Invalid! Enter a number between " + min + " and " + max + ": ");
            }
        }
        scanner.nextLine();
        return userInput;
    }

    boolean readYesNo() {
        boolean isValid = false;
        boolean userChoice = false;

        while (!isValid) {
            System.out.print("Enter Y/N: ");
            String userInput = scanner.nextLine();
            userInput = userInput.toLowerCase().strip();
            if (userInput.equals("y") || userInput.equals("yes")) {
                userChoice = true;
                isValid = true;
            }
            else if (userInput.equals("n") || userInput.equals("no")) {
                isValid = true;
            }
            else {
                System.out.println("Invalid! Enter Yes or No!");
            }
        }

        return userChoice;
    }
}
