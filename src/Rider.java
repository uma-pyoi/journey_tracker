public class Rider {
    private int id;
    private String name;
    CityRideDataset.PassengerType passengerType;
    CityRideDataset.PaymentOption paymentOption;

    // constructors
    public Rider() {
        this.id = -1; // -1 signifies that rider has been default created
        this.name = "Anonymous";
        this.passengerType = CityRideDataset.PassengerType.ADULT;
        this.paymentOption = CityRideDataset.PaymentOption.VISA_CARD;
    }

    public Rider(String name, CityRideDataset.PassengerType passengerType, CityRideDataset.PaymentOption paymentOption) {
        this.id = 0; // TODO: make id unique
        this.name = name;
        this.passengerType = passengerType;
        this.paymentOption = paymentOption;
    }

    // getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public CityRideDataset.PassengerType getPassengerType() {
        return passengerType;
    }
    public CityRideDataset.PaymentOption getPaymentOption() {
        return paymentOption;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassengerType(CityRideDataset.PassengerType passengerType) {
        this.passengerType = passengerType;
    }
    public void setPaymentOption(CityRideDataset.PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
    }

    // other methods
    public void printRider(Formatter formatter) {
        System.out.println("**********   Rider Profile   **********");
        System.out.println("Name: " + name);
        System.out.println("Passenger type: " + formatter.getNameOfPassengerType(passengerType));
        System.out.println("Payment Option: " + formatter.getNameOfPaymentOption(paymentOption));
        System.out.println("***************************************\n");
    }
}
