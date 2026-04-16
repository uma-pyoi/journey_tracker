public class JourneyTrackerApp {

    public static void main(String[] args) {
        JourneysManager journeysManager = new JourneysManager();
        JourneyTracker journeyTracker = new JourneyTracker(journeysManager);
        RiderMenu riderMenu = new RiderMenu();
        InputValidator inputValidator = new InputValidator();
        ConsolePrinter consolePrinter = new ConsolePrinter();
        MainMenu mainMenu = new MainMenu();
        Rider rider;

        int userInput;

        consolePrinter.printWelcome();

        int mainMenuExitNum = 4;
        do {
            consolePrinter.printMainMenu();
            userInput = inputValidator.readPosIntInRange(1, mainMenuExitNum, true, false);
            if (userInput == 1) {
                rider = mainMenu.actionNewRider(riderMenu);
//                rider = new Rider("kai", CityRideDataset.PassengerType.STUDENT, CityRideDataset.PaymentOption.RAIL_CARD);
                riderMenu.setRider(rider, journeysManager);
                riderMenu.runRiderMenu(journeyTracker);
            }
        }
        while (userInput != mainMenuExitNum);
    }
}
