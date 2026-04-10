public class MainMenu {
    public void actionNewRider(InputValidator inputValidator) {
        System.out.println("+++++++ New Rider +++++++");
        String name = inputValidator.readString("Enter your name: ", false);
        System.out.println("name: " + name);
    }
}
