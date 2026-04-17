import java.math.BigDecimal;

public class SummaryForPassengerType {
    private String passengerTypeName;
    private BigDecimal totalBaseFare;
    private BigDecimal totalDiscountedFare;
    private BigDecimal totalChargedFare;
    private int totalJourneyNum;
    private boolean dailyCapReached;

    public SummaryForPassengerType() {
        passengerTypeName = "None";
        totalBaseFare = new BigDecimal("0.0");
        totalDiscountedFare = new BigDecimal("0.0");
        totalChargedFare = new BigDecimal("0.0");
        totalJourneyNum = 0;
        dailyCapReached = false;
    }

    public SummaryForPassengerType(String passengerTypeName) {
        this.passengerTypeName = passengerTypeName;
        totalBaseFare = new BigDecimal("0.0");
        totalDiscountedFare = new BigDecimal("0.0");
        totalChargedFare = new BigDecimal("0.0");
        totalJourneyNum = 0;
        dailyCapReached = false;
    }

    // getters
    public BigDecimal getTotalChargedFare() {
        return totalChargedFare;
    }
    public BigDecimal getTotalDiscountedFare() {
        return totalDiscountedFare;
    }
    public boolean getDailyCapReached() {
        return dailyCapReached;
    }
    public int getTotalJourneyNum() {
        return totalJourneyNum;
    }

    // adding journey
    public void addJourneyToSum(Journey journey) {
        totalBaseFare = totalBaseFare.add(journey.getBaseFare());
        totalDiscountedFare = totalDiscountedFare.add(journey.getDiscountedFare());
        totalChargedFare = totalChargedFare.add(journey.getChargedFare());
        dailyCapReached = journey.getDailyCapReached();
        totalJourneyNum++;
    }

    // viewing summary
    public void printSummary(Formatter formatter) {
        System.out.println("Total base fare: £" + formatter.formatBigDecimal2dp(totalBaseFare));
        System.out.println("Total discounted fare: £" + formatter.formatBigDecimal2dp(totalDiscountedFare));
        System.out.println("Total charged fare: £" + formatter.formatBigDecimal2dp(totalChargedFare));
        System.out.println("Total number of journeys: " + totalJourneyNum);
        System.out.println("Daily cap reached: " + dailyCapReached);
    }

    // removing journey
    private boolean calculateDailyCapReached(CityRideDataset.PassengerType passengerType) {
        BigDecimal dailyCap = CityRideDataset.DAILY_CAP.get(passengerType);
        return totalDiscountedFare.compareTo(dailyCap) >= 0;
    }

    public void subtractJourneyFromSum(Journey journey) {
        totalBaseFare = totalBaseFare.subtract(journey.getBaseFare());
        totalDiscountedFare = totalDiscountedFare.subtract(journey.getDiscountedFare());
        totalChargedFare = totalChargedFare.subtract(journey.getChargedFare());
        dailyCapReached = calculateDailyCapReached(journey.getPassengerType());
        totalJourneyNum--;
    }

    // resetting day
    public void clearAllTotals() {
        totalBaseFare = new BigDecimal("0.0");
        totalDiscountedFare = new BigDecimal("0.0");
        totalChargedFare = new BigDecimal("0.0");
        totalJourneyNum = 0;
        dailyCapReached = false;
    }
}
