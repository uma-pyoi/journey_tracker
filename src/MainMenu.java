import java.util.Objects;

public class MainMenu {
    private InputValidator inputValidator = new InputValidator();
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private Formatter formatter = new Formatter();

    public CityRideDataset.PaymentOption askForPaymentOption() {
        System.out.println("What is the payment option?");
        consolePrinter.printPaymentOptions();
        int paymentOptionId = inputValidator.readPosIntInRange(1, CityRideDataset.PaymentOption.values().length, true, false);

        return CityRideDataset.PaymentOption.values()[paymentOptionId - 1];
    }

    public Rider actionNewRider(RiderMenu riderMenu, JourneyTracker journeyTracker) {
        System.out.println("+++++++ New Rider +++++++");
        String name = inputValidator.readString("Enter your name: ", false);
        CityRideDataset.PassengerType passengerType = riderMenu.askForPassengerType();
        CityRideDataset.PaymentOption paymentOption = askForPaymentOption();

        Rider rider = new Rider(journeyTracker.generateUUID(), name, passengerType, paymentOption);
        rider.printRider(formatter);
        return rider;
    }

    public Rider actionLoadRider(RiderMenu riderMenu, JourneyTracker journeyTracker) {
        Rider rider = riderMenu.loadRiderProfile();
        if (Objects.equals(rider.getId(), "-1")) {
            System.out.println("You will need to create a new rider profile.");
            rider = actionNewRider(riderMenu, journeyTracker);
        }
        return rider;
    }
}
