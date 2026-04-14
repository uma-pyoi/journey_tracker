import java.time.LocalTime;
import java.util.Scanner;

public class InputValidator {
    private Scanner scanner = new Scanner(System.in);

    // returns -1 if input empty
    public int readPosIntInRange(int min, int max, boolean isPrintingRange, boolean canEmpty) {
        int userInt = -1;
        boolean isValid = false;
        do {
            String rangePrompt = isPrintingRange ? " (" + min + "-" + max + ")" : "";
            String blankPrompt = canEmpty ? " or leave empty" : "";
            String prompt = "Enter a whole number" + rangePrompt + blankPrompt + ": ";
            String userIntString = readString(prompt, canEmpty);

            if (!userIntString.isEmpty()) {
                int parsedUserInt = parseStringInteger(userIntString, "Invalid! You must enter a whole number!");
                if (parsedUserInt != -1) {
                    if (parsedUserInt < min || parsedUserInt > max) {
                        System.out.println("Invalid! The number must be between " + min + " and " + max + "!");
                    } else {
                        userInt = parsedUserInt;
                        isValid = true;
                    }
                }
            } else {
                isValid = true;
            }
        }
        while (!isValid);
        return userInt;
    }

    public boolean readYesNo() {
        boolean isValid = false;
        boolean userChoice = false;

        while (!isValid) {
            System.out.print("Enter Y/N: ");
            String userInput = scanner.nextLine();
            userInput = userInput.toLowerCase().strip();
            if (userInput.equals("y") || userInput.equals("yes")) {
                userChoice = true;
                isValid = true;
            } else if (userInput.equals("n") || userInput.equals("no")) {
                isValid = true;
            } else {
                System.out.println("Invalid! Enter Yes or No!");
            }
        }
        return userChoice;
    }

    // read time helpers
    private int findColonIndex(String userInput) {
        int index_of_colon = -1;
        for (int i = 0; i < userInput.length(); i++) {
            char userChar = userInput.charAt(i);
            if (userChar == ':') {
                index_of_colon = i;
                break;
            }
        }
        return index_of_colon;
    }

    // returns -1 if invalid integer
    private int parseStringInteger(String str, String errorMessage) {
        int userInt = -1;
        // REFERENCE: https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html#parseInt-java.lang.String-
        // Oracle (n.d.)
        try {
            userInt = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println(errorMessage);
        }
        return userInt;
    }

    public LocalTime readTime() {
        boolean isValid = false;
        int hours = -1;
        int minutes = -1;

        while (!isValid) {
            System.out.print("Enter time (in format 'hours:minutes'): ");
            String userInput = scanner.nextLine().strip();

            int index_of_colon = findColonIndex(userInput);
            if (index_of_colon == -1) {
                System.out.println("You must put a colon (:) in between the hours and minutes!");
            }
            else {
                String hoursString = userInput.substring(0, index_of_colon).strip();
                String minutesString = userInput.substring(index_of_colon + 1).strip();
                hours = parseStringInteger(hoursString, "The hours entered were incorrect!");
                minutes = parseStringInteger(minutesString, "The minutes entered were incorrect!");

                boolean areValidIntegers = (hours != -1 && minutes != -1);
                if (areValidIntegers) {
                    if (hours < 0 || hours > 24) {
                        System.out.println("The hours must be within the 0-24 range!");
                        hours = -1;
                    }
                    if (minutes < 0 || minutes > 60) {
                        System.out.println("The minutes must be within the 0-60 range!");
                        minutes = -1;
                    }

                    if (hours != -1 && minutes != -1) {
                        isValid = true;
                    }
                }
            }
        }
        return LocalTime.of(hours, minutes);
    }


    public String readString(String prompt, boolean canEmpty) {
        boolean isValid;
        String userString;
        do {
            System.out.print(prompt);
            userString = scanner.nextLine().strip();
            isValid = true;
            if (userString.isEmpty() && !canEmpty) {
                System.out.println("You cannot leave this field empty!");
                isValid = false;
            }
        }
        while (!isValid);
        return userString;
    }
}
