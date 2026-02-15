// make this into utility/static/final class

import java.math.BigDecimal;
import java.util.Map;

public class MainMenu {
    Formatter formatter = new Formatter();

    // methods
    public void printWelcome() {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("++   Welcome to CityRide Lite!   ++");
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    public void printMainMenu() {
        System.out.println("\n");
        System.out.println("Please choose from the following:");
        System.out.println("1. Add journey");
        System.out.println("2. List journeys");
        System.out.println("3. View summaries");
        System.out.println("4. Remove journey");
        System.out.println("5. Reset day");
        System.out.println("0. Exit \n");
    }

    // asking for certain input
    public CityRideDataset.PassengerType askForPassengerType(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("What is the passenger type?");
        journeyTracker.listAllPassengerTypes();
        int passengerTypeId = inputValidator.readIntInRange(1, 4);

        return CityRideDataset.PassengerType.values()[passengerTypeId - 1];
    }

    public Zone askForZone(String zoneDescription, JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("What is the ID of the " + zoneDescription + " zone?");
        journeyTracker.listAllZones();
        int zoneId = inputValidator.readIntInRange(CityRideDataset.MIN_ZONE, CityRideDataset.MAX_ZONE);
        return journeyTracker.findZoneById(zoneId);
    }

    public CityRideDataset.TimeBand askForTimeBand(InputValidator inputValidator) {
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
    public void actionAddJourney(InputValidator inputValidator, JourneyTracker journeyTracker) {
        System.out.println("+++++++ Adding a journey +++++++");
        System.out.println("Was the journey on peak?");
        CityRideDataset.TimeBand timeBand = this.askForTimeBand(inputValidator);
        Zone startZone = this.askForZone("start", journeyTracker, inputValidator);
        Zone endZone = this.askForZone("end", journeyTracker, inputValidator);
        CityRideDataset.PassengerType passengerType = this.askForPassengerType(journeyTracker, inputValidator);

        // calculations
        BigDecimal baseFare = CityRideDataset.getBaseFare(startZone.getZoneId(), endZone.getZoneId(), timeBand);
        BigDecimal discountedFare = journeyTracker.calculateDiscountFare(startZone.getZoneId(), endZone.getZoneId(), timeBand, passengerType);
        BigDecimal chargedFare = journeyTracker.calculateChargedFare(discountedFare, passengerType);
        boolean dailyCapReached = journeyTracker.calculateDailyCapReached(discountedFare, passengerType);

        // adding journey
        Journey newJourney = new Journey(journeyTracker.getJourneyIdCounter(), timeBand, startZone, endZone, passengerType, baseFare, discountedFare, chargedFare, dailyCapReached);
        journeyTracker.addJourney(newJourney);
        journeyTracker.setJourneyIdCounter(journeyTracker.getJourneyIdCounter() + 1);
    }

    // listing journeys
    // private functions for asking for filters
    private void printListingFilters() {
        System.out.println("\nChoose filter:");
        System.out.println("1. Passenger type");
        System.out.println("2. Time band");
        System.out.println("3. Start zone");
        System.out.println("4. End zone");
        System.out.println("5. Time (hours)");
    }

    private void askForFilterByTimeBand(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("Do you wish to see journeys on Peak time?");
        System.out.println("'Yes' for Peak, 'No' for Off-peak");
        CityRideDataset.TimeBand timeBand = this.askForTimeBand(inputValidator);
        journeyTracker.listJourneysByTimeBand(timeBand);
    }

    private void askForFilterByTime(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("-- Sorting by the time period of when the journey was created --");
        System.out.println("What is the start hour?");
        int startHour = inputValidator.readIntInRange(1, 24);
        System.out.println("What is the end hour?");
        int endHour = inputValidator.readIntInRange(1, 24);
        journeyTracker.listJourneysByHours(startHour, endHour);
    }

    public void actionListJourneys(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("Apply listing filters?");
        boolean applyFilters = inputValidator.readYesNo();
        if (applyFilters) {
            printListingFilters();
            int choice = inputValidator.readIntInRange(1, 5);
            if (choice == 1) {
                CityRideDataset.PassengerType passengerType = askForPassengerType(journeyTracker, inputValidator);
                journeyTracker.listJourneysByPassengerType(passengerType);
            }
            else if (choice == 2) {
                askForFilterByTimeBand(journeyTracker, inputValidator);
            }
            else if (choice == 3) {
                Zone startZone = this.askForZone("start", journeyTracker, inputValidator);
                journeyTracker.listJourneysByZone(startZone, true);
            }
            else if (choice == 4) {
                Zone endZone = this.askForZone("end", journeyTracker, inputValidator);
                journeyTracker.listJourneysByZone(endZone, false);
            }
            else if (choice == 5) {
                askForFilterByTime(journeyTracker, inputValidator);
            }
        }
        else {
            journeyTracker.listAllJourneys();
        }
    }

    // viewing summary
    public void actionViewSummary(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("What type of summary to view?");
        System.out.println("1. Daily summaries");
        System.out.println("2. Totals by passenger type");
        System.out.println("3. Category counts");

        int choice = inputValidator.readIntInRange(1, 3);
        if (choice == 1) {
            dailySummaries(journeyTracker);
        }
        else if (choice == 2) {
            totalsByPassengerType(journeyTracker, inputValidator);
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

        System.out.println("Most expensive journey: ");
        Journey mostExpensiveJourney = journeyTracker.getMostExpensiveJourney();
        if (mostExpensiveJourney == null) {
            System.out.println("Most expensive journey not found.");
        }
        else {
            mostExpensiveJourney.printJourneyDetails();
        }
        System.out.println("------------------------------");
    }

    private void totalsByPassengerType(JourneyTracker journeyTracker, InputValidator inputValidator) {
        CityRideDataset.PassengerType passengerType = this.askForPassengerType(journeyTracker, inputValidator);
        String passengerTypeName = formatter.getNameOfPassengerType(passengerType);
        System.out.println("---- Daily Summary for " + passengerTypeName + " journeys ----");
        SummaryForPassengerType summary = journeyTracker.getSummaryObjForPassengerType(passengerType);
        summary.printSummary();
    }

    private void printZonesCount(JourneyTracker journeyTracker) {
        Map<Integer, Integer> zonesCountMap = journeyTracker.getZoneCounts();
        // REFERENCE!!
        // https://www.geeksforgeeks.org/java/iterate-map-java/
        // GeekForGeeks (2025)
        System.out.println("------ Zone counts ------");
        for (Map.Entry<Integer, Integer> zone : zonesCountMap.entrySet())
            System.out.println("Zone " + zone.getKey() +
                    ": " + zone.getValue() + " counts.");
        System.out.println("-------------------------");
    }

    private void printPeakVsOffPeakCounts(JourneyTracker journeyTracker) {
        int[] peakVsOffPeakArray = journeyTracker.getPeakVsOffPeakCount();
        System.out.println("Peak counts: " + peakVsOffPeakArray[0]);
        System.out.println("Off-peak counts: " + peakVsOffPeakArray[1]);
    }

    private void categoryCounts(JourneyTracker journeyTracker) {
        System.out.println("----- Category counts -----");
        printPeakVsOffPeakCounts(journeyTracker);
        printZonesCount(journeyTracker);
    }

    // private method to break down logic of actionRemoveJourney
    // gets called after the journey is confirmed to be removed
    private void actionRemoveJourneyConfirmed(Journey foundJourney, InputValidator inputValidator, JourneyTracker journeyTracker) {
        System.out.println("Found journey:");
        foundJourney.printJourneyDetails();
        System.out.println("\nAre you sure you want to delete this journey?");
        boolean isConfirmed = inputValidator.readYesNo();

        if (isConfirmed) {
            Journey removedJourney = journeyTracker.removeJourneyById(foundJourney.getJourneyId());
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

    public void actionRemoveJourney(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("What is the Journey's ID you want to remove?");
        int journeyId = inputValidator.readIntInRange(1, 99999); // arbitrary max value since journey ID has no limit
        Journey foundJourney = journeyTracker.findJourneyById(journeyId);
        if (foundJourney == null) {
            System.out.println("Not found! The journey with ID " + journeyId + " does not exist!");
        } else {
            actionRemoveJourneyConfirmed(foundJourney, inputValidator, journeyTracker);
        }
    }

    public void actionResetDay(JourneyTracker journeyTracker, InputValidator inputValidator) {
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
