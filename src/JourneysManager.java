import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JourneysManager {
    private int journeyIdCounter = 1;
    private Formatter formatter = new Formatter();
    private List<Journey> journeysList = new ArrayList<>();

    // file handling
    private String journeysFilename = "journeys.csv";

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
        SummaryForPassengerType summary = journeyTracker.getSummaryObjForPassengerType(journey.getPassengerType());
        summary.addJourneyToSum(journey);

        Zone startZone = journeyTracker.findZoneById(journey.getStartZoneId());
        Zone endZone = journeyTracker.findZoneById(journey.getEndZoneId());
        startZone.incrementCount();
        endZone.incrementCount();
    }

    // listing journeys
    public void listAllJourneys(JourneyTracker journeyTracker) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            System.out.println("------   Journeys   ------");
            for (Journey j : journeysList) {
                j.printJourneyDetails(formatter, journeyTracker);
            }
            System.out.println("--------------------------");
        }
    }

    public void listJourneysByPassengerType(CityRideDataset.PassengerType passengerType, JourneyTracker journeyTracker) {
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
                    j.printJourneyDetails(formatter, journeyTracker);
                }
                System.out.println("--------------------------");
            }
        }
    }

    public void listJourneysByTimeBand(CityRideDataset.TimeBand timeBand, JourneyTracker journeyTracker) {
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
                    j.printJourneyDetails(formatter, journeyTracker);
                }
                System.out.println("--------------------------");
            }
        }
    }

    // TODO: test
    public void listJourneysByZone(int zoneId, boolean isStartZone, JourneyTracker journeyTracker) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            List<Journey> zonesJourneysList = new ArrayList<>();
            for (Journey j : journeysList) {
                int zoneIdToCompare;
                if (isStartZone) {
                    zoneIdToCompare = j.getStartZoneId();
                }
                else {
                    zoneIdToCompare = j.getEndZoneId();
                }
                if (zoneIdToCompare == zoneId) {
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
                    j.printJourneyDetails(formatter, journeyTracker);
                }
                System.out.println("--------------------------");
            }
        }
    }

    public void listJourneysByHours(int startHour, int endHour, JourneyTracker journeyTracker) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            List<Journey> hoursJourneysList = new ArrayList<>();
            for (Journey j : journeysList) {
                int journeyHour = LocalTime.parse(j.getTime()).getHour(); // TODO: Test
                if (journeyHour >= startHour && journeyHour <= endHour) {
                    hoursJourneysList.add(j);
                }
            }
            if (hoursJourneysList.isEmpty()) {
                System.out.println("There are no journeys for the " + startHour + "-" + endHour + " hours yet.");
            }
            else {
                System.out.println("------ " + startHour + "-" + endHour + " hours Journeys ------");

                for (Journey j : hoursJourneysList) {
                    j.printJourneyDetails(formatter, journeyTracker);
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

    public void recalculateAllJourneysFares(JourneyTracker journeyTracker) {
        List<Journey> journeysListCopy = new ArrayList<>(journeysList);
        journeyTracker.resetDay();
        for (Journey journey : journeysListCopy) {
            BigDecimal chargedFare = journeyTracker.calculateChargedFare(journey.getDiscountedFare(), journey.getPassengerType());
            boolean dailyCapReached = journeyTracker.calculateDailyCapReached(journey.getDiscountedFare(), journey.getPassengerType());
            journey.setChargedFare(chargedFare);
            journey.setDailyCapReached(dailyCapReached);
            addJourney(journey, journeyTracker);
        }
    }

    // resetting the day
    public void clearJourneysList() {
        journeysList.clear();
    }

    // file handling
//    private List<Journey> reformatJourneysToWrite() {
//        List<Journey> newJourneysList = new ArrayList<>();
//        for (Journey journey : journeysList) {
//            journey.getStartZone().getZoneId();
//        }
//    }

    public void saveJourneys() {
        try {
            // REFERENCE: https://opencsv.sourceforge.net/#writing
            //
            Writer writer = new FileWriter(journeysFilename);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(journeysList);
            writer.close();
            System.out.println("Journeys saved to file!");
        }
        catch (Exception e) {
            System.err.println("Error saving journeys.");
        }
    }

    public void loadJourneys() {
        List<Journey> loadedJourneys = new ArrayList<>();
        try {
            // REFERENCE: https://opencsv.sourceforge.net/#quick_start
            loadedJourneys = new CsvToBeanBuilder(new FileReader(journeysFilename))
                    .withType(Journey.class).build().parse();
            journeysList = new ArrayList<>(loadedJourneys);
            System.out.println("Journeys successfully loaded!");
        }
        catch (Exception e) {
            System.err.println("No existing file for journeys found.");
        }
    }
}
