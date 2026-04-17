import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.customconverter.ConvertGermanToBoolean;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Journey {
    // states
    private String journeyId;
    private String riderId;
    // REFERENCE: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html
    // Oracle (n.d.)
    private String time;
    private CityRideDataset.TimeBand timeBand;
    //    private Zone startZone;
//    private Zone endZone;
    private int startZoneId;
    private int endZoneId;
    private CityRideDataset.PassengerType passengerType;
    private BigDecimal baseFare;
    private BigDecimal discountedFare;
    private BigDecimal chargedFare;
    private boolean dailyCapReached;
    // money formatter
//    private Formatter formatter = new Formatter();

    // constructors
    public Journey() {
        this.journeyId = "None";
        this.riderId = "None";
        this.time = "0:00";
        this.timeBand = null;
        this.startZoneId = 0;
        this.endZoneId = 0;
        this.passengerType = null;
        this.baseFare = new BigDecimal("0.0");
        this.discountedFare = new BigDecimal("0.0");
        this.chargedFare = new BigDecimal("0.0");
        this.dailyCapReached = false;
    }

    public Journey(String journeyID, String riderId, LocalTime time, int startZoneId, int endZoneId, CityRideDataset.PassengerType passengerType, BigDecimal baseFare, BigDecimal discountedFare, BigDecimal chargedFare, boolean dailyCapReached) {
        this.journeyId = journeyID;
        this.riderId = riderId;
        this.time = time.toString();
        this.startZoneId = startZoneId;
        this.endZoneId = endZoneId;
        this.passengerType = passengerType;
        this.baseFare = baseFare;
        this.discountedFare = discountedFare;
        this.chargedFare = chargedFare;
        this.dailyCapReached = dailyCapReached;
    }


    // getters
    public String getJourneyId() {
        return journeyId;
    }

    public String getRiderId() {
        return riderId;
    }

    public CityRideDataset.PassengerType getPassengerType() {
        return passengerType;
    }

    public BigDecimal getBaseFare() {
        return baseFare;
    }

    public BigDecimal getDiscountedFare() {
        return discountedFare;
    }

    public BigDecimal getChargedFare() {
        return chargedFare;
    }

    public int getStartZoneId() {
        return startZoneId;
    }

    public int getEndZoneId() {
        return endZoneId;
    }

    public CityRideDataset.TimeBand getTimeBand() {
        return timeBand;
    }

    public boolean getDailyCapReached() {
        return dailyCapReached;
    }

    public String getTime() {
        return time;
    }

    // setters
    public void setChargedFare(BigDecimal chargedFare) {
        this.chargedFare = chargedFare;
    }

    public void setDailyCapReached(boolean dailyCapReached) {
        this.dailyCapReached = dailyCapReached;
    }

    // other methods
    public String buildJourneyDetailsString(Formatter formatter, JourneyTracker journeyTracker) {
        Zone startZone = journeyTracker.findZoneById(startZoneId);
        Zone endZone = journeyTracker.findZoneById(endZoneId);

        String journeyDetails = "--------------------------" +
                "\nJourney ID: " + this.journeyId +
                "\nRider ID: " + this.riderId +
                "\nTime: " + time +
                "\nTimeband: " + formatter.getNameOfTimeBand(this.timeBand) +
                "\nStart zone: " + startZoneId + ". " + startZone.getZoneName() +
                "\nEnd zone: " + endZoneId + ". " + endZone.getZoneName() +
                "\nPassenger type: " + formatter.getNameOfPassengerType(passengerType) +
                "\nBase fare: £" + formatter.formatBigDecimal2dp(this.baseFare) +
                "\nDiscounted fare: £" + formatter.formatBigDecimal2dp(this.discountedFare) +
                "\nCharged fare: £" + formatter.formatBigDecimal2dp(this.chargedFare) +
                "\nDaily cap reached: " + this.dailyCapReached;
        return journeyDetails;
    }
}
