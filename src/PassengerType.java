import java.text.DecimalFormat;

public class PassengerType {
    // states
    private int passengerTypeID;
    private String passengerTypeName;
    private int discountPercentage;
    private double dailyCap;
    DecimalFormat decimalPlaces2 = new DecimalFormat( "#.00" );

    // constructors
    public PassengerType() {
        this.passengerTypeID = 0;
        this.passengerTypeName = "Unknown";
        this.discountPercentage = 0;
        this.dailyCap = 0;
    }
    public PassengerType(int passengerTypeID, String passengerTypeName, int discountPercentage, double dailyCap) {
        this.passengerTypeID = passengerTypeID;
        this.passengerTypeName = passengerTypeName;
        this.discountPercentage = discountPercentage;
        this.dailyCap = dailyCap;
    }

    // getters
    public int getPassengerTypeID() {
        return this.passengerTypeID;
    }
    public String getPassengerTypeName() {
        return this.passengerTypeName;
    }
    public int getDiscountPercentage() {
        return this.discountPercentage;
    }
    public double getDailyCap() {
        return this.dailyCap;
    }

    // setters
    public void setPassengerTypeID(int passengerTypeID) {
        this.passengerTypeID = passengerTypeID;
    }
    public void setPassengerTypeName(String passengerTypeName) {
        this.passengerTypeName = passengerTypeName;
    }
    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    public void setDailyCap(double dailyCap) {
        this.dailyCap = dailyCap;
    }

    // other methods
    public void printPassengerTypeDetails() {
        System.out.println(this.passengerTypeID + ". " + this.passengerTypeName + " | " + this.discountPercentage + "% | £" + decimalPlaces2.format(this.dailyCap));
    }
}
