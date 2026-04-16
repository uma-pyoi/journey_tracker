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

    public Rider actionNewRider(RiderMenu riderMenu) {
        System.out.println("+++++++ New Rider +++++++");
        String name = inputValidator.readString("Enter your name: ", false);
        CityRideDataset.PassengerType passengerType = riderMenu.askForPassengerType();
        CityRideDataset.PaymentOption paymentOption = askForPaymentOption();

        Rider rider = new Rider(name, passengerType, paymentOption);
        rider.printRider(formatter);
        return rider;
    }

    public Rider actionLoadRider(RiderMenu riderMenu) {
        Rider rider = riderMenu.loadRiderProfile();
        if (rider.getId() == -1) {
            System.out.println("You will need to create a new rider profile.");
            rider = actionNewRider(riderMenu);
        }
        return rider;
    }
}
