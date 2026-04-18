import java.math.BigDecimal;
import java.math.RoundingMode;

public class AdminPanel {
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private InputValidator inputValidator = new InputValidator();
    private Formatter formatter = new Formatter();
    private CityRideDataset cityRideDataset;

    private String adminPassword = "1234";

    public void setCityRideDataset(CityRideDataset cityRideDataset) {
        this.cityRideDataset = cityRideDataset;
    }

    public boolean grantAdminAccess() {
        boolean isGranted = false;
        boolean keepAsking = true;
        System.out.println("\nLeave the password field empty if you wish to exit.");

        do {
            String userPassword = inputValidator.readString("\nEnter admin password (or blank to exit): ", true);
            if (userPassword.isEmpty()) {
                keepAsking = false;
            }
            else if (userPassword.equals(adminPassword)) {
                System.out.println("Admin access granted.");
                isGranted = true;
                keepAsking = false;
            }
            else {
                System.out.println("Wrong password. Try again.");
            }
        }
        while (keepAsking);
        return isGranted;
    }


    public void runAdminMenu(JourneyTracker journeyTracker) {
        int userInput;
        int exitNum = 3;

        do {
            consolePrinter.printAdminMenu();
            userInput = inputValidator.readPosIntInRange(1, exitNum, true, false);

            if (userInput == 1) {
                cityRideDataset.printRules(journeyTracker);
            } else if (userInput == 2) {
                editRules();
            } else if (userInput == 3) {
                System.out.println("thank u bai bai");
            }
        }
        while (userInput != exitNum);
    }

    private void editPeakHours() {
        System.out.println("+++ Editing peak time windows +++");
        System.out.println("Leave the field empty if you want to keep the current value.\n");
        System.out.println("Enter the START hour of peak time. (current: " + cityRideDataset.getPeakStart() + ")");
        int startHour = inputValidator.readPosIntInRange(0, 24, true, true);
        if (startHour == -1) {
            startHour = cityRideDataset.getPeakStart();
        }
        cityRideDataset.setPeakStart(startHour);

        System.out.println("Enter the END hour of peak time. (current: " + cityRideDataset.getPeakEnd() + ")");
        int endHour = inputValidator.readPosIntInRange(0, 24, true, true);
        if (endHour == -1) {
            endHour = cityRideDataset.getPeakEnd();
        }
        cityRideDataset.setPeakEnd(endHour);
    }

    public void editDiscountRates() {
        System.out.println("+++ Editing discount rates +++");
        System.out.println("Leave the field empty if you want to keep the current value.\n");

        for (CityRideDataset.PassengerType passengerType : cityRideDataset.getDiscountRateMap().keySet()) {
            String passengerTypeName = formatter.getNameOfPassengerType(passengerType);

            BigDecimal discountRatePercent = cityRideDataset.getDiscountRateMap().get(passengerType).multiply(new BigDecimal("100"));
            System.out.println("Enter discount rate for " + passengerTypeName + " (current: " + discountRatePercent.intValue() + "%).");
            int userDiscountRate = inputValidator.readPosIntInRange(0, 100, true, true);

            if (userDiscountRate != -1) {
                BigDecimal newDiscountRate = new BigDecimal(userDiscountRate);
                cityRideDataset.setDiscountRate(passengerType, newDiscountRate.divide(new BigDecimal(100)));
            }
        }
    }

    public void editRules() {
        consolePrinter.printRulesEdit();

        int userChoice = inputValidator.readPosIntInRange(1, 4, true, false);
        if (userChoice == 1) {
            editPeakHours();
        }
        else if (userChoice == 2) {
            editDiscountRates();
        }
    }
}
