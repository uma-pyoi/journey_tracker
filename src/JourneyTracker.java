import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class JourneyTracker {
    private Formatter formatter = new Formatter();
    private JourneysManager journeysManager;
    private CityRideDataset cityRideDataset;

    private String PASSENGER_SUMMARIES_FILENAME = "summary.csv";

    private Zone[] zonesArr = {new Zone(1, "Central"),
            new Zone(2, "Inner"),
            new Zone(3, "Suburban"),
            new Zone(4, "Outer"),
            new Zone(5, "Rural")};

    // daily running total for each Passenger Type
    private SummaryForPassengerType adultSummary = new SummaryForPassengerType(formatter.getNameOfPassengerType(CityRideDataset.PassengerType.ADULT));
    private SummaryForPassengerType studentSummary = new SummaryForPassengerType(formatter.getNameOfPassengerType(CityRideDataset.PassengerType.STUDENT));
    private SummaryForPassengerType childSummary = new SummaryForPassengerType(formatter.getNameOfPassengerType(CityRideDataset.PassengerType.CHILD));
    private SummaryForPassengerType seniorCitizenSummary = new SummaryForPassengerType(formatter.getNameOfPassengerType(CityRideDataset.PassengerType.SENIOR_CITIZEN));

    JourneyTracker(JourneysManager journeysManager, CityRideDataset cityRideDataset) {
        this.journeysManager = journeysManager;
        this.cityRideDataset = cityRideDataset;
    }

    // methods
    public String generateUUID() {
        // TransparenTech LLC (n.d.)
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public SummaryForPassengerType getSummaryObjForPassengerType(CityRideDataset.PassengerType passengerType) {
        SummaryForPassengerType foundSummary;
        if (passengerType == CityRideDataset.PassengerType.ADULT) {
            foundSummary = adultSummary;
        } else if (passengerType == CityRideDataset.PassengerType.STUDENT) {
            foundSummary = studentSummary;
        } else if (passengerType == CityRideDataset.PassengerType.CHILD) {
            foundSummary = childSummary;
        } else if (passengerType == CityRideDataset.PassengerType.SENIOR_CITIZEN) {
            foundSummary = seniorCitizenSummary;
        } else {
            foundSummary = null;
        }
        return foundSummary;
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
        } else {
            System.out.println("------   Zones   ------");
            for (Zone z : zonesArr) {
                System.out.println(z.getZoneId() + ". " + z.getZoneName());
            }
            System.out.println("-----------------------");
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
        } else {
            BigDecimal totalJourneysNum = new BigDecimal(getTotalJourneysNum());
            averageCost = totalChargedFare.divide(totalJourneysNum, RoundingMode.HALF_UP);
        }
        return formatter.formatBigDecimal2dp(averageCost);
    }

    // Summary: category counts
    public int[] getPeakVsOffPeakCount(List<Journey> journeysList) {
        int peakCounter = 0;
        int offPeakCounter = 0;
        for (Journey j : journeysList) {
            if (j.getTimeBand() == CityRideDataset.TimeBand.PEAK) {
                peakCounter++;
            } else {
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
        BigDecimal baseFare = cityRideDataset.getBaseFare(startZoneId, endZoneId, timeBand);
        BigDecimal discount = cityRideDataset.getDiscountRateMap().get(passengerType);
        BigDecimal discountOfBaseFare = baseFare.multiply(discount);
        return baseFare.subtract(discountOfBaseFare);
    }

    public BigDecimal calculateChargedFare(BigDecimal discountedFare, CityRideDataset.PassengerType passengerType) {
        BigDecimal chargedFare;

        SummaryForPassengerType summary = getSummaryObjForPassengerType(passengerType);
        BigDecimal totalDiscountedFare = summary.getTotalDiscountedFare();
        BigDecimal newDiscountedTotal = discountedFare.add(summary.getTotalDiscountedFare());
        BigDecimal dailyCap = cityRideDataset.getDailyCapMap().get(passengerType);

        boolean dailyCapReached = summary.getDailyCapReached();

        if (!dailyCapReached) {
            if (newDiscountedTotal.compareTo(dailyCap) > 0) {
                chargedFare = dailyCap.subtract(totalDiscountedFare);
            } else {
                chargedFare = discountedFare;
            }
        } else {
            chargedFare = new BigDecimal("0.00");
        }
        return chargedFare;
    }

    public boolean calculateDailyCapReached(BigDecimal discountedFare, CityRideDataset.PassengerType passengerType) {
        SummaryForPassengerType summary = getSummaryObjForPassengerType(passengerType);
        BigDecimal newDailyTotal = discountedFare.add(summary.getTotalChargedFare());
        BigDecimal dailyCap = cityRideDataset.getDailyCapMap().get(passengerType);

        return newDailyTotal.compareTo(dailyCap) >= 0;
    }

    // resetting the day
    private void resetZoneCounts() {
        for (Zone z : zonesArr) {
            z.setCount(0);
        }
    }

    public void resetDay() {
        journeysManager.clearJourneysList();
        adultSummary.clearAllTotals();
        studentSummary.clearAllTotals();
        childSummary.clearAllTotals();
        seniorCitizenSummary.clearAllTotals();
        resetZoneCounts();
    }

    // exporting report
    public List<SummaryForPassengerType> getPassengerSummariesList() {
        List<SummaryForPassengerType> passengerSummaries = new ArrayList<>();
        passengerSummaries.add(adultSummary);
        passengerSummaries.add(studentSummary);
        passengerSummaries.add(childSummary);
        passengerSummaries.add(seniorCitizenSummary);
        return passengerSummaries;
    }

    public void exportPassengerSummaries() {
        try {
            Writer writer = new FileWriter(PASSENGER_SUMMARIES_FILENAME);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

            List<SummaryForPassengerType> passengerSummaries = getPassengerSummariesList();
            beanToCsv.write(passengerSummaries);
            writer.close();
            System.out.println("Summary (CSV file) exported!");
        } catch (Exception e) {
            System.out.println("Error exporting summary file.");
        }
    }
}