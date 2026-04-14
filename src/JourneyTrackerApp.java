public class JourneyTrackerApp {

    public static void main(String[] args) {
//        JourneyTracker journeyTracker = new JourneyTracker();
//        RiderMenu riderMenu = new RiderMenu();
        InputValidator inputValidator = new InputValidator();
//        ConsolePrinter consolePrinter = new ConsolePrinter();
//        MainMenu mainMenu = new MainMenu();
//        Rider rider;


        int userInt = inputValidator.readPosIntInRange(1, 3, true, true);
        System.out.println("userInt: " + userInt);

//        int userInput;
//
//        consolePrinter.printWelcome();
//
//        int mainMenuExitNum = 4;
//        do {
//            consolePrinter.printMainMenu();
//            userInput = inputValidator.readIntInRange(1, mainMenuExitNum, true);
//            if (userInput == 1) {
//                rider = mainMenu.actionNewRider(riderMenu);
//                riderMenu.setRider(rider);
//                riderMenu.runRiderMenu(journeyTracker);
//            }
//        }
//        while (userInput != mainMenuExitNum);


    }
}
