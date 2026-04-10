public class JourneyTrackerApp {

    public static void main(String[] args) {
        JourneyTracker journeyTracker = new JourneyTracker();
        RiderMenu riderMenu = new RiderMenu();
        InputValidator inputValidator = new InputValidator();
        ConsolePrinter consolePrinter = new ConsolePrinter();

        int userInput;

        consolePrinter.printWelcome();
        do {
            consolePrinter.printMainMenu();
            userInput = inputValidator.readIntInRange(0, 3, true);
            if (userInput == 1) {
                riderMenu.actionAddJourney(inputValidator, journeyTracker);
            }
        }
        while (userInput != 0);

//        do {
//            mainMenu.printMainMenu();
//            userInput = inputValidator.readIntInRange(0, 5, true);
//
//            if (userInput == 1) {
//                mainMenu.actionAddJourney(inputValidator, journeyTracker);
//            }
//            else if (userInput == 2) {
//                mainMenu.actionListJourneys(journeyTracker, inputValidator);
//            }
//            else if (userInput == 3) {
//                mainMenu.actionViewSummary(journeyTracker, inputValidator);
//            }
//            else if (userInput == 4) {
//                mainMenu.actionRemoveJourney(journeyTracker, inputValidator);
//            }
//            else if (userInput == 5) {
//                mainMenu.actionResetDay(journeyTracker, inputValidator);
//            }
//        }
//        while (userInput != 0);
    }
}
