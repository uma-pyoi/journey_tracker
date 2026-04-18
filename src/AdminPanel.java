public class AdminPanel {
    private ConsolePrinter consolePrinter = new ConsolePrinter();
    private InputValidator inputValidator = new InputValidator();


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
