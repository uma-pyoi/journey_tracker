import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class AdminPanel {
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private InputValidator inputValidator = new InputValidator();
    private Formatter formatter = new Formatter();
    private MainMenu mainMenu = new MainMenu();
    private CityRideDataset cityRideDataset;

    private String adminPassword = "meow";
    private String CONFIG_FILENAME = "config.json";
    private Gson gson;

    // constructor
    public AdminPanel() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

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
        int exitNum = 4;

        do {
            consolePrinter.printAdminMenu();
            userInput = inputValidator.readPosIntInRange(1, exitNum, true, false);

            if (userInput == 1) {
                cityRideDataset.printRules(journeyTracker, formatter);
            } else if (userInput == 2) {
                editRules(journeyTracker);
            } else if (userInput == 3) {
                resetConfig();
            } else if (userInput == 4) {
                System.out.println("Thank you. :)");
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
            System.out.println("\nEnter discount rate for " + passengerTypeName + " (current: " + discountRatePercent.intValue() + "%).");
            int userDiscountRate = inputValidator.readPosIntInRange(0, 100, true, true);

            if (userDiscountRate != -1) {
                BigDecimal newDiscountRate = new BigDecimal(userDiscountRate);
                cityRideDataset.setDiscountRate(passengerType, newDiscountRate.divide(new BigDecimal(100)));
            }
        }
    }

    public void editDailyCaps() {
        System.out.println("+++ Editing daily caps +++");
        System.out.println("Leave the field empty if you want to keep the current value.\n");

        for (CityRideDataset.PassengerType passengerType : cityRideDataset.getDailyCapMap().keySet()) {
            String passengerTypeName = formatter.getNameOfPassengerType(passengerType);

            BigDecimal dailyCap = cityRideDataset.getDailyCapMap().get(passengerType);
            System.out.println("\nEnter daily cap for " + passengerTypeName + " (current: £" + formatter.formatBigDecimal2dp(dailyCap) + ").");
            float userDailyCap = inputValidator.readPosFloat(true);

            if (userDailyCap != -1) {
                BigDecimal newDailyCap = new BigDecimal(userDailyCap);
                cityRideDataset.setDailyCap(passengerType, newDailyCap);
            }
        }
    }

    public void editBaseFares(JourneyTracker journeyTracker) {
        System.out.println("What is the START zone?");
        journeyTracker.listAllZones();
        int startZoneId = inputValidator.readPosIntInRange(1, 5, true, false);

        System.out.println("What is the END zone?");
        journeyTracker.listAllZones();
        int endZoneId = inputValidator.readPosIntInRange(1, 5, true, false);

        System.out.println("What is the timeband?");
        CityRideDataset.TimeBand timeband = mainMenu.askForTimeBand();

        BigDecimal currentBaseFare = cityRideDataset.getBaseFare(startZoneId, endZoneId, timeband);
        System.out.println("Current base fare: £" + formatter.formatBigDecimal2dp(currentBaseFare));
        System.out.println("What will be the new base fare?");
        System.out.println("(Leave empty to keep current base fare)");
        float userBaseFare = inputValidator.readPosFloat(true);

        if (userBaseFare != -1) {
            BigDecimal newBaseFare = new BigDecimal(userBaseFare);
            String baseFareKey = CityRideDataset.key(startZoneId, endZoneId, timeband);
            cityRideDataset.setBaseFare(baseFareKey, newBaseFare);
        }
    }

    public void editRules(JourneyTracker journeyTracker) {
        consolePrinter.printRulesEdit();

        int userChoice = inputValidator.readPosIntInRange(1, 4, true, false);
        if (userChoice == 1) {
            editPeakHours();
        }
        else if (userChoice == 2) {
            editDiscountRates();
        }
        else if (userChoice == 3) {
            editDailyCaps();
        }
        else if (userChoice == 4) {
            editBaseFares(journeyTracker);
        }
        System.out.println("lets try savibg config");
        saveConfig();
    }

    public void saveConfig() {
        try {
            FileWriter writer = new FileWriter(CONFIG_FILENAME);
            gson.toJson(cityRideDataset, writer);
            writer.close();
            System.out.println("Configuration saved to file!");
        } catch (Exception e) {
            System.err.println("Error saving configuration file.");
            e.printStackTrace(); // TODO: remove
        }
    }

    public CityRideDataset loadConfig() {
        CityRideDataset loadedCityRideDataset = new CityRideDataset();
        try {
            FileReader reader = new FileReader(CONFIG_FILENAME);
            Type cityRideDatasetType = new TypeToken<CityRideDataset>() {
            }.getType();
            loadedCityRideDataset = gson.fromJson(reader, cityRideDatasetType);
            System.out.println("Configuration file successfully loaded!");
        } catch (Exception e) {
            System.err.println("No existing configuration file found. Starting with default values.");
            loadedCityRideDataset.resetRules();
            e.printStackTrace(); // TODO: remove
        }

        return loadedCityRideDataset;
    }

    public void resetConfig() {
        cityRideDataset.resetRules();
        saveConfig();
        System.out.println("Configuration file rules successfully reset!");
    }
}
