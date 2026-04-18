import java.util.Objects;

public class JourneyTrackerApp {
    public static void main(String[] args) {
        CityRideDataset cityRideDataset = new CityRideDataset();
        JourneysManager journeysManager = new JourneysManager();
        JourneyTracker journeyTracker = new JourneyTracker(journeysManager, cityRideDataset);
        RiderMenu riderMenu = new RiderMenu();
        InputValidator inputValidator = new InputValidator();
        ConsolePrinter consolePrinter = new ConsolePrinter();
        MainMenu mainMenu = new MainMenu();
        AdminPanel adminPanel = new AdminPanel();
        Rider rider = new Rider();

        int userInput;

        consolePrinter.printWelcome();

        int mainMenuExitNum = 4;
        do {
            consolePrinter.printMainMenu();
            userInput = inputValidator.readPosIntInRange(1, mainMenuExitNum, true, false);
            if (userInput == 1) {
                rider = mainMenu.actionNewRider(journeyTracker);
            } else if (userInput == 2) {
                rider = mainMenu.actionLoadRider(riderMenu, journeyTracker);
            } else if (userInput == 3) {
                boolean isAdmin = adminPanel.grantAdminAccess();
                if (isAdmin) {
                    adminPanel.setCityRideDataset(cityRideDataset);
                    adminPanel.runAdminMenu(journeyTracker);
                    userInput = mainMenuExitNum; // terminate program when admin menu exits
                }
            }

            // starts rider menu if user is logged in
            if (!Objects.equals(rider.getId(), "-1")) {
                riderMenu.setRider(rider, journeysManager);
                riderMenu.runRiderMenu(journeyTracker, cityRideDataset);
                userInput = mainMenuExitNum; // terminate program when rider menu exits
            }
        }
        while (userInput != mainMenuExitNum);
    }
}
