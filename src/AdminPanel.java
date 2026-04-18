public class AdminPanel {
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private InputValidator inputValidator = new InputValidator();

    private String adminPassword = "1234";

    public boolean grantAdminAccess() {
        boolean isGranted = false;
        boolean keepAsking = true;
        System.out.println("\nLeave the password field empty if you wish to exit.");

        do {
            String userPassword = inputValidator.readString("\nEnter admin password (or blank to exit): ", true);
            if (userPassword.isEmpty()) {
                keepAsking = false;
            }
            else if (userPassword.equals(adminPassword)) {
                System.out.println("Admin access granted.");
                isGranted = true;
                keepAsking = false;
            }
            else {
                System.out.println("Wrong password. Try again.");
            }
        }
        while (keepAsking);
        return isGranted;
    }


    public void runAdminMenu() {
        int userInput;
        int exitNum = 3;

        do {
            consolePrinter.printAdminMenu();
            userInput = inputValidator.readPosIntInRange(1, exitNum, true, false);

            if (userInput == 1) {
                System.out.println("view rules");
            } else if (userInput == 2) {
                System.out.println("edit rules");
            } else if (userInput == 3) {
                System.out.println("thank u bai bai");
            }
        }
        while (userInput != exitNum);
    }
}
