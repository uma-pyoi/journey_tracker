import java.util.Objects;

public class MainMenu {
    private InputValidator inputValidator = new InputValidator();
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private Formatter formatter = new Formatter();

    // asking for certain input
    public CityRideDataset.PassengerType askForPassengerType() {
        System.out.println("What is the passenger type?");
        consolePrinter.printPassengerTypes();
        int passengerTypeId = inputValidator.readPosIntInRange(1, CityRideDataset.PassengerType.values().length, true, false);

        return CityRideDataset.PassengerType.values()[passengerTypeId - 1];
    }

    public Zone askForZone(String zoneDescription, JourneyTracker journeyTracker) {
        System.out.println("What is the ID of the " + zoneDescription + " zone?");
        journeyTracker.listAllZones();
        int zoneId = inputValidator.readPosIntInRange(CityRideDataset.MIN_ZONE, CityRideDataset.MAX_ZONE, true, false);
        return journeyTracker.findZoneById(zoneId);
    }

    public CityRideDataset.TimeBand askForTimeBand() {
        boolean isPeak = inputValidator.readYesNo();
        CityRideDataset.TimeBand timeBand;
        if (isPeak) {
            timeBand = CityRideDataset.TimeBand.PEAK;
        } else {
            timeBand = CityRideDataset.TimeBand.OFF_PEAK;
        }
        return timeBand;
    }

    public CityRideDataset.PaymentOption askForPaymentOption() {
        System.out.println("What is the payment option?");
        consolePrinter.printPaymentOptions();
        int paymentOptionId = inputValidator.readPosIntInRange(1, CityRideDataset.PaymentOption.values().length, true, false);

        return CityRideDataset.PaymentOption.values()[paymentOptionId - 1];
    }

    // menu actions
    public Rider actionNewRider(JourneyTracker journeyTracker) {
        System.out.println("+++++++ New Rider +++++++");
        String name = inputValidator.readString("Enter your name: ", false);
        CityRideDataset.PassengerType passengerType = askForPassengerType();
        CityRideDataset.PaymentOption paymentOption = askForPaymentOption();

        Rider rider = new Rider(journeyTracker.generateUUID(), name, passengerType, paymentOption);
        rider.printRider(formatter);
        return rider;
    }

    public Rider actionLoadRider(RiderMenu riderMenu, JourneyTracker journeyTracker) {
        Rider rider = riderMenu.loadRiderProfile();
        if (Objects.equals(rider.getId(), "-1")) {
            System.out.println("You will need to create a new rider profile.");
            rider = actionNewRider(journeyTracker);
        }
        return rider;
    }
}
