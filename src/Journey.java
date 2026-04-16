import java.math.BigDecimal;
import java.time.LocalTime;

public class Journey {
    // states
    private int journeyId;
    private int riderId;
    // REFERENCE: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html
    // Oracle (n.d.)
    private LocalTime time;
    private CityRideDataset.TimeBand timeBand;
    private Zone startZone;
    private Zone endZone;
    private CityRideDataset.PassengerType passengerType;
    private BigDecimal baseFare;
    private BigDecimal discountedFare;
    private BigDecimal chargedFare;
    private boolean dailyCapReached;
    // money formatter
    private Formatter formatter = new Formatter();

    // constructors
    public Journey() {
        this.journeyId = 0;
        this.riderId = 0;
        this.time = LocalTime.now();
        this.timeBand = null;
        this.startZone = null;
        this.endZone = null;
        this.passengerType = null;
        this.baseFare = new BigDecimal("0.0");
        this.discountedFare = new BigDecimal("0.0");
        this.chargedFare = new BigDecimal("0.0");
        this.dailyCapReached = false;
    }

    public Journey(int journeyID, int riderId, LocalTime time, Zone startZone, Zone endZone, CityRideDataset.PassengerType passengerType, BigDecimal baseFare, BigDecimal discountedFare, BigDecimal chargedFare, boolean dailyCapReached) {
        this.journeyId = journeyID;
        this.riderId = riderId;
        this.time = time;
        this.startZone = startZone;
        this.endZone = endZone;
        this.passengerType = passengerType;
        this.baseFare = baseFare;
        this.discountedFare = discountedFare;
        this.chargedFare = chargedFare;
        this.dailyCapReached = dailyCapReached;
    }


    // getters
    public int getJourneyId() {
        return journeyId;
    }

    public int getRiderId() {return riderId;}

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

    public Zone getStartZone() {
        return startZone;
    }

    public Zone getEndZone() {
        return endZone;
    }

    public CityRideDataset.TimeBand getTimeBand() {
        return timeBand;
    }

    public boolean getDailyCapReached() {
        return dailyCapReached;
    }

    public LocalTime getTime() {
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
    public void printJourneyDetails() {
        System.out.println("--------------------------");
        System.out.println("Journey ID: " + this.journeyId);
        System.out.println("Rider ID: " + this.riderId);
        String formattedTime = this.time.format(formatter.dateTimeFormatter);
        System.out.println("Datetime: " + formattedTime);
        System.out.print("Timeband: ");
        System.out.println(formatter.getNameOfTimeBand(this.timeBand));
        System.out.println("Start zone: " + this.startZone.getZoneId() + ". " + this.startZone.getZoneName());
        System.out.println("End zone: " + this.endZone.getZoneId() + ". " + this.endZone.getZoneName());
        System.out.println("Passenger type: " + formatter.getNameOfPassengerType(passengerType));

        System.out.println("Base fare: £" + formatter.formatBigDecimal2dp(this.baseFare));
        System.out.println("Discounted fare: £" + formatter.formatBigDecimal2dp(this.discountedFare));
        System.out.println("Charged fare: £" + formatter.formatBigDecimal2dp(this.chargedFare));

        System.out.println("Daily cap reached: " + this.dailyCapReached);
    }
}
