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
    public String getPassengerTypeName() {
        return passengerTypeName;
    }
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
    public String buildSummaryString(Formatter formatter) {
        String summary = "Total base fare: £" + formatter.formatBigDecimal2dp(totalBaseFare) +
                "\nTotal discounted fare: £" + formatter.formatBigDecimal2dp(totalDiscountedFare) +
                "\nTotal charged fare: £" + formatter.formatBigDecimal2dp(totalChargedFare) +
                "\nTotal number of journeys: " + totalJourneyNum +
                "\nDaily cap reached: " + dailyCapReached + "\n";
        return summary;
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
