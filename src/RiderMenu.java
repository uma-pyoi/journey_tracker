import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RiderMenu {
    private Formatter formatter = new Formatter();
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private InputValidator inputValidator = new InputValidator();

    private JourneysManager journeysManager;
    private Rider rider;

    // file handling
    private String RIDER_PROFILE_FILENAME = "profile.json";
    private String REPORT_FILENAME = "report.txt";
    private Gson gson;

    // constructor
    public RiderMenu() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void setRider(Rider rider, JourneysManager journeysManager) {
        this.rider = rider;
        this.journeysManager = journeysManager;
    }

    public void runRiderMenu(JourneyTracker journeyTracker) {
        int userInput;
        int exitNum = 10;
        do {
            consolePrinter.printRiderMenu();
            System.out.println("+++ Logged in as " + rider.getName() + " +++");
            userInput = inputValidator.readPosIntInRange(1, exitNum, true, false);

            if (userInput == 1) {
                actionEditProfile();
            } else if (userInput == 2) {
                journeysManager.loadJourneys(rider, journeyTracker);
            } else if (userInput == 3) {
                actionAddJourney(journeyTracker);
            } else if (userInput == 4) {
                actionListJourneys(journeyTracker);
            } else if (userInput == 5) {
                actionViewSummary(journeyTracker);
            } else if (userInput == 6) {
                actionRemoveJourney(journeyTracker);
            } else if (userInput == 7) {
                actionResetDay(journeyTracker);
            } else if (userInput == 8) {
                actionSave();
            } else if (userInput == 9) {
                actionExportReport(journeyTracker);
            } else if (userInput == 10) {
                actionExit();
            }
        }
        while (userInput != exitNum);
    }

    // asking for certain input
    public CityRideDataset.PassengerType askForPassengerType() {
        System.out.println("What is the passenger type?");
        consolePrinter.printPassengerTypes();
        int passengerTypeId = inputValidator.readPosIntInRange(1, CityRideDataset.PassengerType.values().length, true, false);

        return CityRideDataset.PassengerType.values()[passengerTypeId - 1];
    }

    public Zone askForZone(String zoneDescription, JourneyTracker journeyTracker) {
        System.out.println("What is the ID of the " + zoneDescription + " zone?");
        journeyTracker.listAllZones();
        int zoneId = inputValidator.readPosIntInRange(CityRideDataset.MIN_ZONE, CityRideDataset.MAX_ZONE, true, false);
        return journeyTracker.findZoneById(zoneId);
    }

    public CityRideDataset.TimeBand askForTimeBand() {
        boolean isPeak = inputValidator.readYesNo();
        CityRideDataset.TimeBand timeBand;
        if (isPeak) {
            timeBand = CityRideDataset.TimeBand.PEAK;
        } else {
            timeBand = CityRideDataset.TimeBand.OFF_PEAK;
        }
        return timeBand;
    }

    public CityRideDataset.PassengerType askForPassengerTypeLoggedIn() {
        System.out.println("Use the profile passenger type (" + formatter.getNameOfPassengerType(rider.getPassengerType()) + ") ?");
        System.out.println("Enter 'yes' if so, 'no' if you wish to choose a different passenger type.");
        boolean useProfilePassengerType = inputValidator.readYesNo();
        CityRideDataset.PassengerType passengerType;
        if (!useProfilePassengerType) {
            passengerType = this.askForPassengerType();
        } else {
            passengerType = rider.getPassengerType();
        }
        return passengerType;
    }

    // menu actions
    public void actionEditProfile() {
        rider.printRider(formatter);
        System.out.println("To update the field, enter a value.");
        System.out.println("Leave the field empty if you want to keep the current value.");

        String namePrompt = "Name (current: " + rider.getName() + "): ";
        String newName = inputValidator.readString(namePrompt, true);
        if (!newName.isEmpty()) {
            rider.setName(newName);
        }

        consolePrinter.printPassengerTypes();
        System.out.println("Passenger type (current: " + formatter.getNameOfPassengerType(rider.getPassengerType()) + ")");
        int newPassengerTypeId = inputValidator.readPosIntInRange(1, 4, true, true);
        if (newPassengerTypeId != -1) {
            CityRideDataset.PassengerType newPassengerType = CityRideDataset.PassengerType.values()[newPassengerTypeId - 1];
            rider.setPassengerType(newPassengerType);
        }

        consolePrinter.printPaymentOptions();
        System.out.println("Payment option (current: " + formatter.getNameOfPaymentOption(rider.getPaymentOption()) + ")");
        int newPaymentOptionId = inputValidator.readPosIntInRange(1, 3, true, true);
        if (newPaymentOptionId != -1) {
            CityRideDataset.PaymentOption newPaymentOption = CityRideDataset.PaymentOption.values()[newPaymentOptionId - 1];
            rider.setPaymentOption(newPaymentOption);
        }
        System.out.println("\nUPDATED PROFILE: ");
        rider.printRider(formatter);
    }

    public void actionAddJourney(JourneyTracker journeyTracker) {
        System.out.println("+++++++ Adding a journey +++++++");
        // TODO: remove asking user for timeband, calculate automatically
        System.out.println("Was the journey on peak?");
        CityRideDataset.TimeBand timeBand = this.askForTimeBand();
        System.out.println("What is the time of the journey?");
        LocalTime time = inputValidator.readTime();
        Zone startZone = this.askForZone("start", journeyTracker);
        Zone endZone = this.askForZone("end", journeyTracker);
        CityRideDataset.PassengerType passengerType = this.askForPassengerTypeLoggedIn();

        // calculations
        BigDecimal baseFare = CityRideDataset.getBaseFare(startZone.getZoneId(), endZone.getZoneId(), timeBand);
        BigDecimal discountedFare = journeyTracker.calculateDiscountFare(startZone.getZoneId(), endZone.getZoneId(), timeBand, passengerType);
        BigDecimal chargedFare = journeyTracker.calculateChargedFare(discountedFare, passengerType);
        boolean dailyCapReached = journeyTracker.calculateDailyCapReached(discountedFare, passengerType);

        // adding journey
        Journey newJourney = new Journey(journeyTracker.generateUUID(), rider.getId(), time, startZone.getZoneId(), endZone.getZoneId(), passengerType, baseFare, discountedFare, chargedFare, dailyCapReached);
        journeysManager.addJourney(newJourney, journeyTracker);
        System.out.println("Journey with Journey ID of " + newJourney.getJourneyId() + " has been added.");
        journeysManager.setJourneyIdCounter(journeysManager.getJourneyIdCounter() + 1);
    }

    // listing journeys
    private void askForFilterByTimeBand(JourneyTracker journeyTracker) {
        System.out.println("Do you wish to see journeys on Peak time?");
        System.out.println("'Yes' for Peak, 'No' for Off-peak");
        CityRideDataset.TimeBand timeBand = this.askForTimeBand();
        journeysManager.listJourneysByTimeBand(timeBand, journeyTracker);
    }

    private void askForFilterByTime(JourneyTracker journeyTracker) {
        System.out.println("-- Sorting by the time period of when the journey was created --");
        System.out.println("What is the start hour?");
        int startHour = inputValidator.readPosIntInRange(1, 24, true, false);
        System.out.println("What is the end hour?");
        int endHour = inputValidator.readPosIntInRange(1, 24, true, false);
        journeysManager.listJourneysByHours(startHour, endHour, journeyTracker);
    }

    public void actionListJourneys(JourneyTracker journeyTracker) {
        System.out.println("Apply listing filters?");
        boolean applyFilters = inputValidator.readYesNo();
        if (applyFilters) {
            consolePrinter.printListingFilters();
            int choice = inputValidator.readPosIntInRange(1, 5, true, false);
            if (choice == 1) {
                CityRideDataset.PassengerType passengerType = askForPassengerType();
                journeysManager.listJourneysByPassengerType(passengerType, journeyTracker);
            } else if (choice == 2) {
                askForFilterByTimeBand(journeyTracker);
            } else if (choice == 3) {
                Zone startZone = this.askForZone("start", journeyTracker);
                journeysManager.listJourneysByZone(startZone.getZoneId(), true, journeyTracker);
            } else if (choice == 4) {
                Zone endZone = this.askForZone("end", journeyTracker);
                journeysManager.listJourneysByZone(endZone.getZoneId(), false, journeyTracker);
            } else if (choice == 5) {
                askForFilterByTime(journeyTracker);
            }
        } else {
            journeysManager.listAllJourneys(journeyTracker);
        }
    }

    // viewing summary
    public void actionViewSummary(JourneyTracker journeyTracker) {
        consolePrinter.printSummaryTypes();

        int choice = inputValidator.readPosIntInRange(1, 3, true, false);
        if (choice == 1) {
            String dailySummary = buildDailySummaryString(journeyTracker);
            System.out.println(dailySummary);
        } else if (choice == 2) {
            totalsByPassengerType(journeyTracker);
        } else if (choice == 3) {
            categoryCounts(journeyTracker);
        }
    }

    // private functions to view certain summaries
    private String buildDailySummaryString(JourneyTracker journeyTracker) {
        String dailySummary = "\n------- Daily Summary ------" +
                "\nTotal number of journeys: " + journeyTracker.getTotalJourneysNum() +
                "\nTotal costs charged: £" + journeyTracker.getTotalCostsCharged() +
                "\nAverage cost per journey: £" + journeyTracker.getAverageCostPerJourney() +
                "\nMost expensive journey(s): \n";

        List<Journey> mostExpensiveJourneysList = journeysManager.getMostExpensiveJourneysList();
        if (mostExpensiveJourneysList.isEmpty()) {
            dailySummary += "\nMost expensive journey not found.";
        } else {
            for (Journey j : mostExpensiveJourneysList) {
                String journeyDetails = j.buildJourneyDetailsString(formatter, journeyTracker);
                dailySummary += journeyDetails;
            }
        }
        dailySummary += "\n------------------------------";

        return dailySummary;
    }

    private void totalsByPassengerType(JourneyTracker journeyTracker) {
        CityRideDataset.PassengerType passengerType = this.askForPassengerType();
        String passengerTypeName = formatter.getNameOfPassengerType(passengerType);
        System.out.println("---- Daily Summary for " + passengerTypeName + " journeys ----");
        SummaryForPassengerType summary = journeyTracker.getSummaryObjForPassengerType(passengerType);
        summary.printSummary(formatter);
    }

    private void printZonesCount(JourneyTracker journeyTracker) {
        Map<Integer, Integer> zonesCountMap = journeyTracker.getZoneCounts();
        System.out.println("------ Zone counts ------");
        // GeekForGeeks (2025)
        for (Map.Entry<Integer, Integer> zone : zonesCountMap.entrySet())
            System.out.println("Zone " + zone.getKey() +
                    ": " + zone.getValue() + " counts.");
        System.out.println("-------------------------");
    }

    private void printPeakVsOffPeakCounts(JourneyTracker journeyTracker) {
        int[] peakVsOffPeakArray = journeyTracker.getPeakVsOffPeakCount(journeysManager.getJourneysList());
        System.out.println("Peak counts: " + peakVsOffPeakArray[0]);
        System.out.println("Off-peak counts: " + peakVsOffPeakArray[1]);
    }

    private void categoryCounts(JourneyTracker journeyTracker) {
        System.out.println("----- Category counts -----");
        printPeakVsOffPeakCounts(journeyTracker);
        printZonesCount(journeyTracker);
    }

    // remove journey
    private void actionRemoveJourneyConfirmed(Journey foundJourney, JourneyTracker journeyTracker) {
        System.out.println("Found journey:");
        String journeyDetails = foundJourney.buildJourneyDetailsString(formatter, journeyTracker);
        System.out.println(journeyDetails);
        System.out.println("\nAre you sure you want to delete this journey?");
        boolean isConfirmed = inputValidator.readYesNo();

        if (isConfirmed) {
            Journey removedJourney = journeysManager.removeJourneyById(foundJourney.getJourneyId());
            if (removedJourney == null) {
                System.out.println("An error happened! The journey was not removed.");
            } else {
                journeysManager.recalculateAllJourneysFares(journeyTracker);
                System.out.println("The journey with ID " + foundJourney.getJourneyId() + " was successfully removed.");
            }
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    public void actionRemoveJourney(JourneyTracker journeyTracker) {
        String journeyId = inputValidator.readString("Journey ID to remove: ", false);
        Journey foundJourney = journeysManager.findJourneyById(journeyId);
        if (foundJourney == null) {
            System.out.println("Not found! The journey with ID " + journeyId + " does not exist!");
        } else {
            actionRemoveJourneyConfirmed(foundJourney, journeyTracker);
        }
    }

    // reset day
    public void actionResetDay(JourneyTracker journeyTracker) {
        System.out.println("Are you sure you want to reset the day?");
        boolean isConfirmed = inputValidator.readYesNo();
        if (isConfirmed) {
            journeyTracker.resetDay();
            System.out.println("The day was successfully reset.");
        } else {
            System.out.println("Resetting cancelled.");
        }
    }

    public void saveRiderProfile() {
        try {
            FileWriter writer = new FileWriter(RIDER_PROFILE_FILENAME);
            gson.toJson(rider, writer);
            writer.close();
            System.out.println("Rider profile saved to file!");
        }
        catch (Exception e) {
            System.err.println("Error saving rider profile.");
        }
    }

    public Rider loadRiderProfile() {
        Rider loadedRider = new Rider();
        try {
            FileReader reader = new FileReader(RIDER_PROFILE_FILENAME);
            Type riderProfileType = new TypeToken<Rider>() {}.getType();
            loadedRider = gson.fromJson(reader, riderProfileType);
            System.out.println("Rider successfully loaded!");
        }
        catch (Exception e) {
            System.err.println("No existing file for rider profile found.");
        }

        return loadedRider;
    }

    public void actionSave() {
        consolePrinter.printRiderSaveMenu();
        int userChoice = inputValidator.readPosIntInRange(1, 2, true, false);
        if (userChoice == 1) {
            saveRiderProfile();
        } else if (userChoice == 2) {
            journeysManager.saveJourneys();
        }
    }

    public void exportReport(JourneyTracker journeyTracker) {
        // REFERENCE: https://www.w3schools.com/java/java_files_write.asp
        try {
            FileWriter myWriter = new FileWriter(REPORT_FILENAME);
            myWriter.write(buildDailySummaryString(journeyTracker));
            myWriter.close();
            System.out.println("Report (TXT file) exported!");
        } catch (IOException e) {
            System.err.println("Error exporting report file.");
            e.printStackTrace();
        }
    }

    public void actionExportReport(JourneyTracker journeyTracker) {
        journeyTracker.exportPassengerSummaries();
        exportReport(journeyTracker);
    }

    public void actionExit() {
        System.out.println("Do you wish to save the day? (Will save Rider Profile and Journeys)");
        boolean userChoice = inputValidator.readYesNo();
        if (userChoice) {
            saveRiderProfile();
            journeysManager.saveJourneys();
        }
        System.out.println("Thank you for using our program :3");
    }
}
