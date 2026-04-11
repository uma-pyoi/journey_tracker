import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class RiderMenu {
    private Formatter formatter = new Formatter();
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private InputValidator inputValidator = new InputValidator();
    private JourneysManager journeysManager = new JourneysManager();


    public void runRiderMenu(JourneyTracker journeyTracker, Rider rider) {
        int userInput;
        do {
            consolePrinter.printRiderMenu();
            System.out.println("+++ Logged in as " + rider.getName() + " +++");
            userInput = inputValidator.readIntInRange(0, 5, true);

            if (userInput == 1) {
                actionAddJourney(journeyTracker);
            }
            else if (userInput == 2) {
                actionListJourneys(journeyTracker);
            }
            else if (userInput == 3) {
                actionViewSummary(journeyTracker);
            }
            else if (userInput == 4) {
                actionRemoveJourney(journeyTracker);
            }
            else if (userInput == 5) {
                actionResetDay(journeyTracker);
            }
        }
        while (userInput != 0);
    }

    // asking for certain input
    public CityRideDataset.PassengerType askForPassengerType() {
        System.out.println("What is the passenger type?");
        consolePrinter.printPassengerTypes();
        int passengerTypeId = inputValidator.readIntInRange(1, CityRideDataset.PassengerType.values().length, true);

        return CityRideDataset.PassengerType.values()[passengerTypeId - 1];
    }

    public Zone askForZone(String zoneDescription, JourneyTracker journeyTracker) {
        System.out.println("What is the ID of the " + zoneDescription + " zone?");
        journeyTracker.listAllZones();
        int zoneId = inputValidator.readIntInRange(CityRideDataset.MIN_ZONE, CityRideDataset.MAX_ZONE, true);
        return journeyTracker.findZoneById(zoneId);
    }

    public CityRideDataset.TimeBand askForTimeBand() {
        boolean isPeak = inputValidator.readYesNo();
        CityRideDataset.TimeBand timeBand;
        if (isPeak) {
            timeBand = CityRideDataset.TimeBand.PEAK;
        }
        else {
            timeBand = CityRideDataset.TimeBand.OFF_PEAK;
        }
        return timeBand;
    }

    // menu actions
    public void actionAddJourney(JourneyTracker journeyTracker) {
        System.out.println("+++++++ Adding a journey +++++++");
        // TODO: remove asking user for timeband, calculate automatically
        System.out.println("Was the journey on peak?");
        CityRideDataset.TimeBand timeBand = this.askForTimeBand();
        System.out.println("What is the time of the journey?");


        Zone startZone = this.askForZone("start", journeyTracker);
        Zone endZone = this.askForZone("end", journeyTracker);
        CityRideDataset.PassengerType passengerType = this.askForPassengerType();

        // calculations
        BigDecimal baseFare = CityRideDataset.getBaseFare(startZone.getZoneId(), endZone.getZoneId(), timeBand);
        BigDecimal discountedFare = journeyTracker.calculateDiscountFare(startZone.getZoneId(), endZone.getZoneId(), timeBand, passengerType);
        BigDecimal chargedFare = journeyTracker.calculateChargedFare(discountedFare, passengerType);
        boolean dailyCapReached = journeyTracker.calculateDailyCapReached(discountedFare, passengerType);

        // adding journey
        // TODO: remove
        LocalTime time = LocalTime.now();
        Journey newJourney = new Journey(journeysManager.getJourneyIdCounter(), time, startZone, endZone, passengerType, baseFare, discountedFare, chargedFare, dailyCapReached);
        journeysManager.addJourney(newJourney, journeyTracker);
        journeysManager.setJourneyIdCounter(journeysManager.getJourneyIdCounter() + 1);
    }

    // listing journeys
    private void askForFilterByTimeBand(InputValidator inputValidator) {
        System.out.println("Do you wish to see journeys on Peak time?");
        System.out.println("'Yes' for Peak, 'No' for Off-peak");
        CityRideDataset.TimeBand timeBand = this.askForTimeBand();
        journeysManager.listJourneysByTimeBand(timeBand);
    }

    private void askForFilterByTime(InputValidator inputValidator) {
        System.out.println("-- Sorting by the time period of when the journey was created --");
        System.out.println("What is the start hour?");
        int startHour = inputValidator.readIntInRange(1, 24, true);
        System.out.println("What is the end hour?");
        int endHour = inputValidator.readIntInRange(1, 24, true);
        journeysManager.listJourneysByHours(startHour, endHour);
    }

    public void actionListJourneys(JourneyTracker journeyTracker) {
        System.out.println("Apply listing filters?");
        boolean applyFilters = inputValidator.readYesNo();
        if (applyFilters) {
            consolePrinter.printListingFilters();
            int choice = inputValidator.readIntInRange(1, 5, true);
            if (choice == 1) {
                CityRideDataset.PassengerType passengerType = askForPassengerType();
                journeysManager.listJourneysByPassengerType(passengerType);
            }
            else if (choice == 2) {
                askForFilterByTimeBand(inputValidator);
            }
            else if (choice == 3) {
                Zone startZone = this.askForZone("start", journeyTracker);
                journeysManager.listJourneysByZone(startZone, true);
            }
            else if (choice == 4) {
                Zone endZone = this.askForZone("end", journeyTracker);
                journeysManager.listJourneysByZone(endZone, false);
            }
            else if (choice == 5) {
                askForFilterByTime(inputValidator);
            }
        }
        else {
            journeysManager.listAllJourneys();
        }
    }

    // viewing summary
    public void actionViewSummary(JourneyTracker journeyTracker) {
        consolePrinter.printSummaryTypes();

        int choice = inputValidator.readIntInRange(1, 3, true);
        if (choice == 1) {
            dailySummaries(journeyTracker);
        }
        else if (choice == 2) {
            totalsByPassengerType(journeyTracker);
        }
        else if (choice == 3) {
            categoryCounts(journeyTracker);
        }
    }

    // private functions to view certain summaries
    private void dailySummaries(JourneyTracker journeyTracker) {
        System.out.println("\n------- Daily Summary ------");
        System.out.println("Total number of journeys: " + journeyTracker.getTotalJourneysNum());
        System.out.println("Total costs charged: £" + journeyTracker.getTotalCostsCharged());
        System.out.println("Average cost per journey: £" + journeyTracker.getAverageCostPerJourney());

        System.out.println("Most expensive journey(s): ");
        List<Journey> mostExpensiveJourneysList = journeysManager.getMostExpensiveJourneysList();
        if (mostExpensiveJourneysList.isEmpty()) {
            System.out.println("Most expensive journey not found.");
        }
        else {
            for (Journey j : mostExpensiveJourneysList) {
                j.printJourneyDetails();
            }
        }
        System.out.println("------------------------------");
    }

    private void totalsByPassengerType(JourneyTracker journeyTracker) {
        CityRideDataset.PassengerType passengerType = this.askForPassengerType();
        String passengerTypeName = formatter.getNameOfPassengerType(passengerType);
        System.out.println("---- Daily Summary for " + passengerTypeName + " journeys ----");
        SummaryForPassengerType summary = journeyTracker.getSummaryObjForPassengerType(passengerType);
        summary.printSummary();
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
        foundJourney.printJourneyDetails();
        System.out.println("\nAre you sure you want to delete this journey?");
        boolean isConfirmed = inputValidator.readYesNo();

        if (isConfirmed) {
            Journey removedJourney = journeysManager.removeJourneyById(foundJourney.getJourneyId());
            if (removedJourney == null) {
                System.out.println("An error happened! The journey was not removed.");
            } else {
                journeyTracker.recalculateSummariesForRemovedJourney(removedJourney);
                System.out.println("The journey with ID " + foundJourney.getJourneyId() + " was successfully removed.");
            }
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    public void actionRemoveJourney(JourneyTracker journeyTracker) {
        System.out.println("What is the Journey's ID you want to remove?");
        int journeyId = inputValidator.readIntInRange(1, 99999, false); // arbitrary max value since journey ID has no limit
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
        }
        else {
            System.out.println("Resetting cancelled.");
        }
    }
}
