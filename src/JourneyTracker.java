import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JourneyTracker {
    // states
    private int journeyIdCounter = 1;
    private Formatter formatter = new Formatter();
    // lists
    private List<Journey> journeysList = new ArrayList<>();
    // arrays
    private Zone[] zonesArr = {new Zone(1, "Central"),
            new Zone(2, "Inner"),
            new Zone(3, "Suburban"),
            new Zone(4, "Outer"),
            new Zone(5,"Rural")};

    // daily running total for each Passenger Type
    private SummaryForPassengerType adultSummary = new SummaryForPassengerType();
    private SummaryForPassengerType studentSummary = new SummaryForPassengerType();
    private SummaryForPassengerType childSummary = new SummaryForPassengerType();
    private SummaryForPassengerType seniorCitizenSummary = new SummaryForPassengerType();

    // methods
    // getters
    public int getJourneyIdCounter() {
        return journeyIdCounter;
    }
    public SummaryForPassengerType getSummaryObjForPassengerType(CityRideDataset.PassengerType passengerType) {
        SummaryForPassengerType foundSummary;
        if (passengerType == CityRideDataset.PassengerType.ADULT) {
            foundSummary = adultSummary;
        }
        else if (passengerType == CityRideDataset.PassengerType.STUDENT) {
            foundSummary = studentSummary;
        }
        else if (passengerType == CityRideDataset.PassengerType.CHILD) {
            foundSummary = childSummary;
        }
        else if (passengerType == CityRideDataset.PassengerType.SENIOR_CITIZEN) {
            foundSummary = seniorCitizenSummary;
        }
        else {
            foundSummary = null;
        }
        return foundSummary;
    }

    // setters
    public void setJourneyIdCounter(int journeyIdCounter) {
        this.journeyIdCounter = journeyIdCounter;
    }

    // adding Journey
    public void addJourney(Journey journey) {
        journeysList.add(journey);
        System.out.println("Journey with Journey ID of " + journey.getJourneyId() + " has been added.");

        SummaryForPassengerType summary = getSummaryObjForPassengerType(journey.getPassengerType());
        summary.addJourneyToSum(journey);
        journey.getStartZone().incrementCount();
        journey.getEndZone().incrementCount();
    }

    public Zone findZoneById(int zoneId) {
        for (Zone z : zonesArr) {
            if (z.getZoneId() == zoneId) {
                return z;
            }
        }
        return null;
    }

    // listing
    public void listAllZones() {
        if (zonesArr.length == 0) {
            System.out.println("No zones available!");
        }
        else {
            System.out.println("------   Zones   ------");
            for (Zone z : zonesArr) {
                System.out.println(z.getZoneId() + ". " + z.getZoneName());
            }
            System.out.println("-----------------------");
        }
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

    // Summary: daily summaries
    public int getTotalJourneysNum() {
        return adultSummary.getTotalJourneyNum() + studentSummary.getTotalJourneyNum() + childSummary.getTotalJourneyNum() + seniorCitizenSummary.getTotalJourneyNum();
    }

    public BigDecimal getTotalCostsCharged() {
        BigDecimal totalCostsCharged = adultSummary.getTotalChargedFare().add(studentSummary.getTotalChargedFare()).add(childSummary.getTotalChargedFare()).add(seniorCitizenSummary.getTotalChargedFare());
        return formatter.formatBigDecimal2dp(totalCostsCharged);
    }

    public BigDecimal getAverageCostPerJourney() {
        BigDecimal totalChargedFare = getTotalCostsCharged();
        BigDecimal averageCost;
        if (getTotalJourneysNum() == 0) {
            averageCost = new BigDecimal("0.00");
        }
        else {
            BigDecimal totalJourneysNum = new BigDecimal(getTotalJourneysNum());
            averageCost = totalChargedFare.divide(totalJourneysNum, RoundingMode.HALF_UP);
        }
        return formatter.formatBigDecimal2dp(averageCost);
    }

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

    // Summary: category counts
    public int[] getPeakVsOffPeakCount() {
        int peakCounter = 0;
        int offPeakCounter = 0;
        for (Journey j : journeysList) {
            if (j.getTimeBand() == CityRideDataset.TimeBand.PEAK) {
                peakCounter++;
            }
            else {
                offPeakCounter++;
            }
        }
        return new int[]{peakCounter, offPeakCounter};
    }

    public Map<Integer, Integer> getZoneCounts() {
        Map<Integer, Integer> zoneCountsMap = new HashMap<>();
        for (Zone zone : zonesArr) {
            zoneCountsMap.put(zone.getZoneId(), zone.getCount());
        }
        return zoneCountsMap;
    }

    // fare calculations
    public BigDecimal calculateDiscountFare(int startZoneId, int endZoneId, CityRideDataset.TimeBand timeBand, CityRideDataset.PassengerType passengerType) {
        BigDecimal baseFare = CityRideDataset.getBaseFare(startZoneId, endZoneId, timeBand);
        BigDecimal discount = CityRideDataset.DISCOUNT_RATE.get(passengerType);
        BigDecimal discountOfBaseFare = baseFare.multiply(discount);
        return baseFare.subtract(discountOfBaseFare);
    }

    public BigDecimal calculateChargedFare(BigDecimal discountedFare, CityRideDataset.PassengerType passengerType) {
        BigDecimal chargedFare;

        SummaryForPassengerType summary = getSummaryObjForPassengerType(passengerType);
        BigDecimal totalDiscountedFare = summary.getTotalDiscountedFare();
        BigDecimal newDiscountedTotal = discountedFare.add(summary.getTotalDiscountedFare());
        BigDecimal dailyCap = CityRideDataset.DAILY_CAP.get(passengerType);

        boolean dailyCapReached = summary.getDailyCapReached();

        if (!dailyCapReached) {
            if (newDiscountedTotal.compareTo(dailyCap) > 0) {
                chargedFare = dailyCap.subtract(totalDiscountedFare);
            } else {
                chargedFare = discountedFare;
            }
        }
        else {
            chargedFare = new BigDecimal("0.00");
        }
        return chargedFare;
    }

    public boolean calculateDailyCapReached(BigDecimal discountedFare, CityRideDataset.PassengerType passengerType) {
        SummaryForPassengerType summary = getSummaryObjForPassengerType(passengerType);
        BigDecimal newDailyTotal = discountedFare.add(summary.getTotalChargedFare());
        BigDecimal dailyCap = CityRideDataset.DAILY_CAP.get(passengerType);

        return newDailyTotal.compareTo(dailyCap) >= 0;
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

    public void recalculateSummariesForRemovedJourney(Journey removedJourney) {
        CityRideDataset.PassengerType passengerType = removedJourney.getPassengerType();
        SummaryForPassengerType summary = getSummaryObjForPassengerType(passengerType);
        summary.subtractJourneyFromSum(removedJourney);
        removedJourney.getStartZone().decrementCount();
        removedJourney.getEndZone().decrementCount();
    }

    // resetting the day
    private void resetZoneCounts() {
        for (Zone z : zonesArr) {
            z.setCount(0);
        }
    }
    public void resetDay() {
        journeysList.clear();
        adultSummary.clearAllTotals();
        studentSummary.clearAllTotals();
        childSummary.clearAllTotals();
        seniorCitizenSummary.clearAllTotals();
        resetZoneCounts();
    }
}