import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityRideDataset {
    private Formatter formatter = new Formatter();
    // TODO: add feature to have multiple peak time windows
    private int peakStart = 16;
    private int peakEnd = 20;

    public static final int MIN_ZONE = 1;
    public static final int MAX_ZONE = 5;

    public enum TimeBand {
        PEAK,
        OFF_PEAK
    }

    public enum PaymentOption {
        VISA_CARD,
        MASTER_CARD,
        RAIL_CARD
    }

    public enum PassengerType {
        ADULT,
        STUDENT,
        CHILD,
        SENIOR_CITIZEN
    }

    private Map<PassengerType, BigDecimal> discountRateMap = new HashMap<>(Map.of(
            PassengerType.ADULT, new BigDecimal("0.00"),
            PassengerType.STUDENT, new BigDecimal("0.25"),
            PassengerType.CHILD, new BigDecimal("0.50"),
            PassengerType.SENIOR_CITIZEN, new BigDecimal("0.30"))
    );

    public Map<PassengerType, BigDecimal> dailyCapMap = new HashMap<>(Map.of(
            PassengerType.ADULT, new BigDecimal("8.00"),
            PassengerType.STUDENT, new BigDecimal("6.00"),
            PassengerType.CHILD, new BigDecimal("4.00"),
            PassengerType.SENIOR_CITIZEN, new BigDecimal("7.00"))
    );

    public int getPeakStart() {
        return peakStart;
    }

    public Map<PassengerType, BigDecimal> getDiscountRateMap() {
        return discountRateMap;
    }

    public Map<PassengerType, BigDecimal> getDailyCapMap() {
        return dailyCapMap;
    }

    public int getPeakEnd() {
        return peakEnd;
    }

    public void setPeakStart(int peakStart) {
        this.peakStart = peakStart;
    }

    public void setPeakEnd(int peakEnd) {
        this.peakEnd = peakEnd;
    }

    public void setDiscountRate(PassengerType passengerType, BigDecimal discountRate) {
        discountRateMap.replace(passengerType, discountRate);
    }

    public void setDailyCap(PassengerType passengerType, BigDecimal dailyCap) {
        dailyCapMap.replace(passengerType, dailyCap);
    }


    public static final Map<String, BigDecimal> BASE_FARE = buildBaseFare();

    public static BigDecimal getBaseFare(int fromZone, int toZone, TimeBand timeBand) {
        return BASE_FARE.get(key(fromZone, toZone, timeBand));
    }

    public static String key(int fromZone, int toZone, TimeBand timeBand) {
        return fromZone + "-" + toZone + "-" + timeBand.name();
    }

    private static BigDecimal money(String amount) {
        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
    }

    private static Map<String, BigDecimal> buildBaseFare() {
        Map<String, BigDecimal> m = new HashMap<>();

        // Peak fares
        put(m,1,1,TimeBand.PEAK,"2.50"); put(m,1,2,TimeBand.PEAK,"3.20");
        put(m,1,3,TimeBand.PEAK,"3.80"); put(m,1,4,TimeBand.PEAK,"4.40");
        put(m,1,5,TimeBand.PEAK,"5.00");

        put(m,2,1,TimeBand.PEAK,"3.20"); put(m,2,2,TimeBand.PEAK,"2.30");
        put(m,2,3,TimeBand.PEAK,"3.10"); put(m,2,4,TimeBand.PEAK,"3.80");
        put(m,2,5,TimeBand.PEAK,"4.50");

        put(m,3,1,TimeBand.PEAK,"3.80"); put(m,3,2,TimeBand.PEAK,"3.10");
        put(m,3,3,TimeBand.PEAK,"2.10"); put(m,3,4,TimeBand.PEAK,"3.00");
        put(m,3,5,TimeBand.PEAK,"3.70");

        put(m,4,1,TimeBand.PEAK,"4.40"); put(m,4,2,TimeBand.PEAK,"3.80");
        put(m,4,3,TimeBand.PEAK,"3.00"); put(m,4,4,TimeBand.PEAK,"2.00");
        put(m,4,5,TimeBand.PEAK,"2.90");

        put(m,5,1,TimeBand.PEAK,"5.00"); put(m,5,2,TimeBand.PEAK,"4.50");
        put(m,5,3,TimeBand.PEAK,"3.70"); put(m,5,4,TimeBand.PEAK,"2.90");
        put(m,5,5,TimeBand.PEAK,"1.90");

        // Off-peak fares
        put(m,1,1,TimeBand.OFF_PEAK,"2.00"); put(m,1,2,TimeBand.OFF_PEAK,"2.70");
        put(m,1,3,TimeBand.OFF_PEAK,"3.20"); put(m,1,4,TimeBand.OFF_PEAK,"3.70");
        put(m,1,5,TimeBand.OFF_PEAK,"4.20");

        put(m,2,1,TimeBand.OFF_PEAK,"2.70"); put(m,2,2,TimeBand.OFF_PEAK,"1.90");
        put(m,2,3,TimeBand.OFF_PEAK,"2.60"); put(m,2,4,TimeBand.OFF_PEAK,"3.20");
        put(m,2,5,TimeBand.OFF_PEAK,"3.80");

        put(m,3,1,TimeBand.OFF_PEAK,"3.20"); put(m,3,2,TimeBand.OFF_PEAK,"2.60");
        put(m,3,3,TimeBand.OFF_PEAK,"1.70"); put(m,3,4,TimeBand.OFF_PEAK,"2.50");
        put(m,3,5,TimeBand.OFF_PEAK,"3.10");

        put(m,4,1,TimeBand.OFF_PEAK,"3.70"); put(m,4,2,TimeBand.OFF_PEAK,"3.20");
        put(m,4,3,TimeBand.OFF_PEAK,"2.50"); put(m,4,4,TimeBand.OFF_PEAK,"1.60");
        put(m,4,5,TimeBand.OFF_PEAK,"2.40");

        put(m,5,1,TimeBand.OFF_PEAK,"4.20"); put(m,5,2,TimeBand.OFF_PEAK,"3.80");
        put(m,5,3,TimeBand.OFF_PEAK,"3.10"); put(m,5,4,TimeBand.OFF_PEAK,"2.40");
        put(m,5,5,TimeBand.OFF_PEAK,"1.50");

        return Map.copyOf(m);
    }

    private static void put(Map<String, BigDecimal> m, int from, int to, TimeBand band, String amount) {
        m.put(key(from, to, band), money(amount));
    }

    // printing
    public void printDiscountRates() {
        System.out.println("---------- Discount rates ----------");
        for (PassengerType passengerType : discountRateMap.keySet()) {
            String passengerTypeName = formatter.getNameOfPassengerType(passengerType);
            int discountRatePercent = discountRateMap.get(passengerType).multiply(new BigDecimal("100")).intValue();
//            int discountRatePercent = formatter.formatPercentage(DISCOUNT_RATE.get(passengerType));
            System.out.println(passengerTypeName + ": " + discountRatePercent + "%");
        }
        System.out.println("------------------------------------");
    }

    public void printDailyCaps() {
        System.out.println("---------- Daily caps ----------");
        for (PassengerType passengerType : dailyCapMap.keySet()) {
            String passengerTypeName = formatter.getNameOfPassengerType(passengerType);
            System.out.println(passengerTypeName + ": £" + formatter.formatBigDecimal2dp(dailyCapMap.get(passengerType)));
        }
        System.out.println("--------------------------------");
    }

    private List<String> splitBaseFareKey(String baseFareKey) {
        int dashEncounters = 0;

        // initialised so that ide won't complain
        String startZone = "None";
        String endZone = "None";
        String timeband = "None";

        int dashIndex0 = -1;
        int dashIndex1 = -1;

        for (int i = 0; i < baseFareKey.length(); i++) {
            if (baseFareKey.charAt(i) == '-') {
                switch (dashEncounters) {
                    case 0:
                        startZone = baseFareKey.substring(0, i);
                        dashIndex0 = i;
                        break;
                    case 1:
                        endZone = baseFareKey.substring(dashIndex0 + 1, i);
                        dashIndex1 = i;
                        break;
                }
                dashEncounters++;
            }
        }

        if (dashIndex1 != -1) {
            timeband = baseFareKey.substring(dashIndex1 + 1);
        }

        List<String> baseFareKeyList = new ArrayList<>();
        baseFareKeyList.add(startZone);
        baseFareKeyList.add(endZone);
        baseFareKeyList.add(timeband);

        return baseFareKeyList;
    }

    public void printBaseFares(JourneyTracker journeyTracker) {
        System.out.println("---------- Base Fares ----------");
        for (String baseFareKey : BASE_FARE.keySet()) {
            List<String> baseFareKeyList = splitBaseFareKey(baseFareKey);

            String startZoneName = journeyTracker.findZoneById(Integer.parseInt(baseFareKeyList.get(0))).getZoneName();
            String endZoneName = journeyTracker.findZoneById(Integer.parseInt(baseFareKeyList.get(1))).getZoneName();

            System.out.println(startZoneName + " (" + baseFareKeyList.get(0) + ") - " + endZoneName + " (" + baseFareKeyList.get(1) + ") | " + baseFareKeyList.get(2) + ": " + BASE_FARE.get(baseFareKey));
        }
        System.out.println("--------------------------------");
    }

    public void printRules(JourneyTracker journeyTracker) {
        System.out.println("***********   CityRide Lite Rules   ***********");
        System.out.println("Peak hours: " + peakStart + "-" + peakEnd);

        printDiscountRates();
        printDailyCaps();
        printBaseFares(journeyTracker);
    }
}