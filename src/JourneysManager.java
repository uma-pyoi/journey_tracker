import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JourneysManager {
    private int journeyIdCounter = 1;
    private Formatter formatter = new Formatter();
    private List<Journey> journeysList = new ArrayList<>();

    // methods
    public List<Journey> getJourneysList() {
        return journeysList;
    }
    public int getJourneyIdCounter() {
        return journeyIdCounter;
    }
    public void setJourneyIdCounter(int journeyIdCounter) {
        this.journeyIdCounter = journeyIdCounter;
    }

    // adding Journey
    public void addJourney(Journey journey, JourneyTracker journeyTracker) {
        journeysList.add(journey);
        System.out.println("Journey with Journey ID of " + journey.getJourneyId() + " has been added.");

        SummaryForPassengerType summary = journeyTracker.getSummaryObjForPassengerType(journey.getPassengerType());
        summary.addJourneyToSum(journey);
        journey.getStartZone().incrementCount();
        journey.getEndZone().incrementCount();
    }

    // listing journeys
    public void listAllJourneys() {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            System.out.println("------   Journeys   ------");
            for (Journey j : journeysList) {
                j.printJourneyDetails();
            }
            System.out.println("--------------------------");
        }
    }

    public void listJourneysByPassengerType(CityRideDataset.PassengerType passengerType) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            List<Journey> passengerTypeJourneysList = new ArrayList<>();
            for (Journey j : journeysList) {
                if (j.getPassengerType() == passengerType) {
                    passengerTypeJourneysList.add(j);
                }
            }
            if (passengerTypeJourneysList.isEmpty()) {
                System.out.println("There are no journeys for this passenger type yet.");
            }
            else {
                System.out.println("------ " + formatter.getNameOfPassengerType(passengerType) + " Journeys ------");

                for (Journey j : passengerTypeJourneysList) {
                    j.printJourneyDetails();
                }
                System.out.println("--------------------------");
            }
        }
    }

    public void listJourneysByTimeBand(CityRideDataset.TimeBand timeBand) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            List<Journey> timeBandJourneysList = new ArrayList<>();
            for (Journey j : journeysList) {
                if (j.getTimeBand() == timeBand) {
                    timeBandJourneysList.add(j);
                }
            }
            if (timeBandJourneysList.isEmpty()) {
                System.out.println("There are no journeys for this timeband yet.");
            }
            else {
                System.out.println("------ " + formatter.getNameOfTimeBand(timeBand) + " Journeys ------");

                for (Journey j : timeBandJourneysList) {
                    j.printJourneyDetails();
                }
                System.out.println("--------------------------");
            }
        }
    }

    public void listJourneysByZone(Zone zone, boolean isStartZone) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            List<Journey> zonesJourneysList = new ArrayList<>();
            for (Journey j : journeysList) {
                Zone zoneToCompare;
                if (isStartZone) {
                    zoneToCompare = j.getStartZone();
                }
                else {
                    zoneToCompare = j.getEndZone();
                }
                if (zoneToCompare == zone) {
                    zonesJourneysList.add(j);
                }
            }
            String zoneDescription = isStartZone ? "Start" : "End";
            if (zonesJourneysList.isEmpty()) {
                System.out.println("There are no journeys for this " + zoneDescription + " zone yet.");
            }
            else {
                System.out.println("------ " + zoneDescription + " zone Journeys ------");

                for (Journey j : zonesJourneysList) {
                    j.printJourneyDetails();
                }
                System.out.println("--------------------------");
            }
        }
    }

    public void listJourneysByHours(int startHour, int endHour) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            List<Journey> hoursJourneysList = new ArrayList<>();
            for (Journey j : journeysList) {
                if (j.getTime().getHour() >= startHour && j.getTime().getHour() <= endHour) {
                    hoursJourneysList.add(j);
                }
            }
            if (hoursJourneysList.isEmpty()) {
                System.out.println("There are no journeys for the " + startHour + "-" + endHour + " hours yet.");
            }
            else {
                System.out.println("------ " + startHour + "-" + endHour + " hours Journeys ------");

                for (Journey j : hoursJourneysList) {
                    j.printJourneyDetails();
                }
                System.out.println("--------------------------");
            }
        }
    }

    // daily summaries
    public List<Journey> getMostExpensiveJourneysList() {
        BigDecimal mostExpensiveCharge = new BigDecimal("0.00");
        List<Journey> expensiveJourneysList = new ArrayList<>();

        for (Journey j : journeysList) {
            if (j.getChargedFare().compareTo(mostExpensiveCharge) > 0) {
                expensiveJourneysList.clear();
                expensiveJourneysList.add(j);
                mostExpensiveCharge = j.getChargedFare();
            }
            else if (j.getChargedFare().compareTo(mostExpensiveCharge) == 0) {
                expensiveJourneysList.add(j);
            }
        }
        return expensiveJourneysList;
    }

    // removing journey
    public Journey findJourneyById(int journeyId) {
        for(Journey journey : journeysList) {
            if(journey.getJourneyId() == journeyId) {
                return journey;
            }
        }
        return null;
    }

    public Journey removeJourneyById(int journeyId) {
        Journey removedJourney = null;
        for(int i = 0; i < journeysList.size(); i++) {
            if(journeysList.get(i).getJourneyId() == journeyId) {
                removedJourney = journeysList.remove(i);
                break;
            }
        }
        return removedJourney;
    }

    // resetting the day
    public void clearJourneysList() {
        journeysList.clear();
    }

}
