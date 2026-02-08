import java.util.Scanner;

public class JourneyTrackerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JourneyTracker journeyTracker = new JourneyTracker();
        MainMenu mainMenu = new MainMenu();
        InputValidator inputValidator = new InputValidator();

        // counters logic needs to be fixed
        int journeyIDCounter = 1;
        int zoneIDCounter = 1;
        int passengerTypeIDCounter = 1;

        int userInput;

        mainMenu.printWelcome();

        do {
            mainMenu.printMainMenu();
            userInput = inputValidator.readIntInRange(0, 5);

            if (userInput == 1) {
                System.out.println("+++++++ Adding a journey +++++++");
                System.out.println("Was the journey on peak?");
                boolean isPeak = inputValidator.readYesNo();

                System.out.println("What is the ID of the start zone?");
                journeyTracker.listAllZones();
                int startZoneID = inputValidator.readIntInRange(1, 5); // hard coded, will change
                Zone startZone = journeyTracker.findZoneByID(startZoneID);

                System.out.println("What is the ID of the end zone?");
                journeyTracker.listAllZones();
                int endZoneID = inputValidator.readIntInRange(1, 5); // hard coded, will change
                Zone endZone = journeyTracker.findZoneByID(endZoneID);

                PassengerType passengerType = inputValidator.askForPassengerTypeID();

                // calculations should happen here, will be added
                // when java class for the assignment will be provided.
                // will use hard coded values for now
                double baseFare = passengerType.getDailyCap() / 2;
                double discountedFare = baseFare - (baseFare * passengerType.getDiscountPercentage() / 100);
                double chargedFare = Math.min(discountedFare, passengerType.getDailyCap());
                boolean dailyCapReached = chargedFare == passengerType.getDailyCap();

                Journey newJourney = new Journey(journeyIDCounter, isPeak, startZone, endZone, passengerType, baseFare, discountedFare, chargedFare, dailyCapReached);
                journeyTracker.addJourney(newJourney);
            }
            else if (userInput == 2) {
                journeyTracker.listAllJourneys();
            }
            else if (userInput == 3) {
                PassengerType passengerType = inputValidator.askForPassengerTypeID();
                journeyTracker.printSummaryForPassengerTypeByID(passengerType.getPassengerTypeID());
            }
        }
        while (userInput != 0);
        scanner.close();
    }
}
