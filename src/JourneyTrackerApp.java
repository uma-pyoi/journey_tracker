import java.util.Objects;

public class JourneyTrackerApp {

    public static void main(String[] args) {
        JourneysManager journeysManager = new JourneysManager();
        JourneyTracker journeyTracker = new JourneyTracker(journeysManager);
        RiderMenu riderMenu = new RiderMenu();
        InputValidator inputValidator = new InputValidator();
        ConsolePrinter consolePrinter = new ConsolePrinter();
        MainMenu mainMenu = new MainMenu();
        Rider rider = new Rider();

        int userInput;

        consolePrinter.printWelcome();

        int mainMenuExitNum = 4;
        do {
            consolePrinter.printMainMenu();
            userInput = inputValidator.readPosIntInRange(1, mainMenuExitNum, true, false);
            if (userInput == 1) {
                rider = mainMenu.actionNewRider(riderMenu, journeyTracker);
            } else if (userInput == 2) {
                rider = mainMenu.actionLoadRider(riderMenu, journeyTracker);
            } else if (userInput == 3) {
                System.out.println("Oops! need to make admin panel still");
            }

            // starts rider menu if user is logged in
            if (!Objects.equals(rider.getId(), "-1")) {
                riderMenu.setRider(rider, journeysManager);
                riderMenu.runRiderMenu(journeyTracker);
                userInput = mainMenuExitNum; // terminate program when rider menu exits
            }
        }
        while (userInput != mainMenuExitNum);
    }
}
