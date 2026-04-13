import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsolePrinter {
    private Formatter formatter = new Formatter();

    private void printMenuWithNumbers(List<String> menuOptionsList) {
        for (int i = 0; i < menuOptionsList.size(); i++) {
            System.out.println((i + 1) + ". " + menuOptionsList.get(i));
        }
    }

    public void printWelcome() {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("++   Welcome to CityRide Lite!   ++");
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    public void printMainMenu() {
        System.out.println("\n");
        System.out.println("Please choose from the following:");
        // REFERENCE: https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList-T...-
        // Oracle (n.d.)
        List<String> mainMenuOptions = Arrays.asList("New rider", "Load rider", "Admin panel", "Exit");
        printMenuWithNumbers(mainMenuOptions);
        System.out.println();
    }

    public void printRiderMenu() {
        System.out.println("\n");
        System.out.println("Please choose from the following:");
        List<String> riderMenuOptions = Arrays.asList("Edit profile", "Load journeys", "Add journey",
                "List journeys", "View summaries", "Remove journey", "Reset day", "Save", "Export summary", "Exit");
        printMenuWithNumbers(riderMenuOptions);
        System.out.println();
    }

    public void printListingFilters() {
        System.out.println("\nChoose filter:");
        System.out.println("1. Passenger type");
        System.out.println("2. Time band");
        System.out.println("3. Start zone");
        System.out.println("4. End zone");
        System.out.println("5. Time (hours)");
    }

    public void printSummaryTypes() {
        System.out.println("What type of summary to view?");
        System.out.println("1. Daily summaries");
        System.out.println("2. Totals by passenger type");
        System.out.println("3. Category counts");
    }

    public void printPassengerTypes() {
        System.out.println("------   Passenger Types   ------");
        for (int i = 0; i < CityRideDataset.PassengerType.values().length; i++) {
            String passengerTypeName = formatter.getNameOfPassengerType(CityRideDataset.PassengerType.values()[i]);
            System.out.println((i + 1) + ". " + passengerTypeName);
        }
        System.out.println("---------------------------------");
    }

    public void printPaymentOptions() {
        System.out.println("------   Payment Options   ------");
        for (int i = 0; i < CityRideDataset.PaymentOption.values().length; i++) {
            String paymentOptionName = formatter.getNameOfPaymentOption(CityRideDataset.PaymentOption.values()[i]);
            System.out.println((i + 1) + ". " + paymentOptionName);
        }
        System.out.println("---------------------------------");

    }
}
