import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Journey {
    // states
    private int journeyID;
    // Bro Code (2024)
    private LocalDateTime datetime;
    private boolean isPeak;
    private Zone startZone;
    private Zone endZone;
    private PassengerType passengerType;
    private double baseFare;
    private double discountedFare;
    private double chargedFare;
    private boolean dailyCapReached;
    // formatter
    DecimalFormat decimalPlaces2 = new DecimalFormat( "#.00" );

    // constructors
    public Journey() {
        this.journeyID = 0;
        this.datetime = LocalDateTime.now();
        this.isPeak = false;
        this.startZone = null;
        this.endZone = null;
        this.passengerType = null;
        this.baseFare = 0.0;
        this.discountedFare = 0.0;
        this.chargedFare = 0.0;
        this.dailyCapReached = false;
    }
    public Journey(int journeyID, boolean isPeak, Zone startZone, Zone endZone, PassengerType passengerType, double baseFare, double discountedFare, double chargedFare, boolean dailyCapReached) {
        this.journeyID = journeyID;
        this.datetime = LocalDateTime.now();
        this.isPeak = isPeak;
        this.startZone = startZone;
        this.endZone = endZone;
        this.passengerType = passengerType;
        this.baseFare = baseFare;
        this.discountedFare = discountedFare;
        this.chargedFare = chargedFare;
        this.dailyCapReached = dailyCapReached;
    }

    // getters
    public int getJourneyID() {
        return journeyID;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }
    public boolean isPeak() {
        return isPeak;
    }
    public Zone getStartZone() {
        return startZone;
    }
    public Zone getEndZone() {
        return endZone;
    }
    public PassengerType getPassengerType() {
        return passengerType;
    }
    public double getBaseFare() {
        return baseFare;
    }
    public double getDiscountedFare() {
        return discountedFare;
    }
    public double getChargedFare() {
        return chargedFare;
    }
    public boolean getDailyCapReached() {
        return dailyCapReached;
    }

    // setters
    public void setJourneyID(int journeyID) {
        this.journeyID = journeyID;
    }
    public void setIsPeak(boolean isPeak) {
        this.isPeak = isPeak;
    }
    public void setStartZone(Zone startZone) {
        this.startZone = startZone;
    }
    public void setEndZone(Zone endZone) {
        this.endZone = endZone;
    }
    public void setPassengerType(PassengerType passengerType) {
        this.passengerType = passengerType;
    }
    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }
    public void setDiscountedFare(double discountedFare) {
        this.discountedFare = discountedFare;
    }
    public void setChargedFare(double chargedFare) {
        this.chargedFare = chargedFare;
    }
    public void setDailyCapReached(boolean dailyCapReached) {
        this.dailyCapReached = dailyCapReached;
    }

    // other methods
    public void printJourneyDetails() {
        System.out.println("--------------------------");
        System.out.println("Journey ID: " + this.journeyID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String formattedDatetime = this.datetime.format(formatter);
        System.out.println("Datetime: " + formattedDatetime);
        System.out.print("Timeband: ");
        if (isPeak) {
            System.out.println("peak");
        } else {
            System.out.println("off-peak");
        }
        System.out.println("Start zone: " + this.startZone.getZoneID() + ". " + this.startZone.getZoneName());
        System.out.println("End zone: " + this.endZone.getZoneID() + ". " + this.endZone.getZoneName());
        System.out.println("Passenger type: " + this.passengerType.getPassengerTypeName());
        System.out.println("Base fare: £" + decimalPlaces2.format(this.baseFare));
        System.out.println("Discounted fare: £" + decimalPlaces2.format(this.discountedFare));
        System.out.println("Charged fare: £" + decimalPlaces2.format(this.chargedFare));
        System.out.println("Daily cap reached: " + this.dailyCapReached);
    }
}
