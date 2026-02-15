import java.util.Scanner;

public class JourneyTrackerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JourneyTracker journeyTracker = new JourneyTracker();
        MainMenu mainMenu = new MainMenu();
        InputValidator inputValidator = new InputValidator();

        int userInput;

        mainMenu.printWelcome();

        do {
            mainMenu.printMainMenu();
            userInput = inputValidator.readIntInRange(0, 5);

            if (userInput == 1) {
                mainMenu.actionAddJourney(inputValidator, journeyTracker);
            }
            else if (userInput == 2) {
                mainMenu.actionListJourneys(journeyTracker, inputValidator);
            }
            else if (userInput == 3) {
                mainMenu.actionViewSummary(journeyTracker, inputValidator);
            }
            else if (userInput == 4) {
                mainMenu.actionRemoveJourney(journeyTracker, inputValidator);
            }
            else if (userInput == 5) {
                mainMenu.actionResetDay(journeyTracker, inputValidator);
            }
        }
        while (userInput != 0);
        scanner.close();
    }
}
