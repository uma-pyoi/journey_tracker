public class ConsolePrinter {
    public void printWelcome() {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("++   Welcome to CityRide Lite!   ++");
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    public void printMainMenu() {
        System.out.println("\n");
        System.out.println("Please choose from the following:");
        System.out.println("1. New rider");
        System.out.println("2. Load rider");
        System.out.println("3. Admin panel");
        System.out.println("0. Exit \n");
    }

    public void printRiderMenu() {
        System.out.println("\n");
        System.out.println("Please choose from the following:");
        System.out.println("1. Add journey");
        System.out.println("2. List journeys");
        System.out.println("3. View summaries");
        System.out.println("4. Remove journey");
        System.out.println("5. Reset day");
        System.out.println("0. Exit \n");
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
}
