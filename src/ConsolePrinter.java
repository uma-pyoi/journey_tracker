import java.util.Arrays;
import java.util.List;

public class ConsolePrinter {
    private Formatter formatter = new Formatter();

    public void printMenuWithNumbers(List<String> menuOptionsList) {
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

    // rider related
    public void printRiderMenu() {
        System.out.println("\n");
        System.out.println("Please choose from the following:");
        List<String> riderMenuOptions = Arrays.asList("Edit profile", "Load journeys", "Add journey",
                "List journeys", "View summaries", "Remove journey", "Reset day", "Save", "Export report", "Exit");
        printMenuWithNumbers(riderMenuOptions);
        System.out.println();
    }

    public void printRiderSaveMenu() {
        System.out.println("\nChoose what to save:");
        List<String> menuOptions = Arrays.asList("Rider profile", "Journeys");
        printMenuWithNumbers(menuOptions);
    }

    public void printListingFilters() {
        System.out.println("\nChoose filter:");
        List<String> menuOptions = Arrays.asList("Passenger type", "Time band", "Start zone", "End zone", "Time (hours)");
        printMenuWithNumbers(menuOptions);
    }

    public void printSummaryTypes() {
        System.out.println("What type of summary to view?");
        List<String> menuOptions = Arrays.asList("Daily summaries", "Totals by passenger type", "Category counts");
        printMenuWithNumbers(menuOptions);
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

    // admin related
    public void printAdminMenu() {
        System.out.println("\n");
        System.out.println("****** Admin Panel ******");
        System.out.println("Please choose from the following:");
        List<String> menuOptions = Arrays.asList("View rules", "Edit rules", "Reset rules", "Exit");
        printMenuWithNumbers(menuOptions);
        System.out.println();
    }

    public void printRulesEdit() {
        System.out.println("Which rule to edit?");
        List<String> menuOptions = Arrays.asList("Peak time window", "Discount rates", "Daily caps", "Base fares");
        printMenuWithNumbers(menuOptions);
    }
}
