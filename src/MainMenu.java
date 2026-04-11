public class MainMenu {
    private InputValidator inputValidator = new InputValidator();
    private RiderMenu riderMenu = new RiderMenu();
    private ConsolePrinter consolePrinter = new ConsolePrinter();

    public CityRideDataset.PaymentOption askForPaymentOption() {
        System.out.println("What is the payment option?");
        consolePrinter.printPaymentOptions();
        int paymentOptionId = inputValidator.readIntInRange(1, CityRideDataset.PaymentOption.values().length, true);

        return CityRideDataset.PaymentOption.values()[paymentOptionId - 1];
    }

    public void actionNewRider() {
        System.out.println("+++++++ New Rider +++++++");
        String name = inputValidator.readString("Enter your name: ", false);
        System.out.println("name: " + name);
        CityRideDataset.PassengerType passengerType = riderMenu.askForPassengerType();
        System.out.println("passengerType: " + passengerType);
        CityRideDataset.PaymentOption paymentOption = askForPaymentOption();
        System.out.println("paymentOption: " + paymentOption);
        Rider rider = new Rider(name, passengerType, paymentOption);
//        ridersManager.addRider(rider);
        rider.printRider();
    }
}
