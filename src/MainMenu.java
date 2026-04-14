public class MainMenu {
    private InputValidator inputValidator = new InputValidator();
    private ConsolePrinter consolePrinter = new ConsolePrinter();

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
        rider.printRider();
        return rider;
    }
}
