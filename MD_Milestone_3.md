# IY4113 Milestone 3

| Assessment Details | Please Complete All Details                                      |
| ------------------ | ---------------------------------------------------------------- |
| Group              | A                                                                |
| Module Title       | Applied Software Engineering using Object Orientated Programming |
| Assessment Type    | Java Fundamentals Part 3                                         |
| Module Tutor Name  | Jonathan Shore                                                   |
| Student ID Number  | P479574                                                          |
| Date of Submission | 08/02/2026                                                       |
| Word Count         | 3224                                                             |

- [x] *I confirm that this assignment is my own work. Where I have referred to academic sources, I have provided in-text citations and included the sources in
  the final reference list.*

- [x] *Where I have used AI, I have cited and referenced appropriately.

------------------------------------------------------------------------------------------------------------------------------

### Research (minimum of 2, at least 3)



------------------------------------------------------------------------------------------------------------------------------

##### Current Date & Time automatic assignment

Title of research: Handling automatic assignment of date and time with LocalDateTime for the created journeys

Reference (link): Bro Code. (2024, December 17). *Learn DATES & TIMES with Java in 8 minutes! 📆.* [YouTube Video]. Retrieved from: https://youtu.be/F2bZ1fkAQx0?si=YqrQhLJXz9YmA0rK [Accessed 8 February 2026].

How does the research help with coding practise?: The video demonstrates how to manipulate date, time, datetime and UTC timestamps in Java. It teaches how to get and store the current date and/or time and how to format it for outputting. The video also shows how to compare dates or create custom dates.

Key coding ideas you could reuse in your program: My program needs to automatically assign datetime with the same format every time a new instance of class Journey is creted. Just like in the video, the field for the datetime will be stored as an instance of class LocalDateTime and will be created using the method now() of that class in the constructor. Storing the datetime in such a way, instead of storing it as a String will allow for easier date manipulation later in the program when a method to sort journeys by the date and time will be created. The built in method for formatting the date will be incredibly useful for outputting journeys information.

Screenshot of research:

LocalDateTime creation:

![](/Users/assemyeleussiz/Library/Application%20Support/marktext/images/2026-02-08-15-52-53-image.png)

LocalDateTime comparison:

![](/Users/assemyeleussiz/Library/Application%20Support/marktext/images/2026-02-08-15-54-29-image.png)

------------------------------------------------------------------------------------------------------------------------------

##### Integer validation with range

Title of research: Integer input validation with checking that the number is in range with scanner.hasNextInt()

Reference (link): Kvasnovsky, O. (2025). *Java 101 — Lesson 14: Input Validation & Robustness.* Retrieved from: https://ondrej-kvasnovsky.medium.com/java-101-lesson-14-input-validation-robustness-908331a56f3d [Accessed 8 February 2026].

How does the research help with coding practise?: The webpage shows the development of a function to validate integers entered by the user step by step. In the final step, the function has the functionality to check if the entered integer is within a certain range. The explanations provided are beginner-friendly and the code is properly commented to further aid the reader.

Key coding ideas you could reuse in your program: Being console based, my program's main flow will consistently include the process of asking the user to enter an integer corresponding to a certain option printed in the console. For example, in the main menu when the user has options numbering from 0 to 5. Considering that, having a function that will handle both non numeric inputs and numeric inputs that are out of range is incredibly useful. Such a function will be added to a special input validating class.

Screenshot of research:

Handle non numeric inputs

![](/Users/assemyeleussiz/Library/Application%20Support/marktext/images/2026-02-08-19-06-55-image.png)

Handle non numeric inputs & inputs out of range

![](/Users/assemyeleussiz/Library/Containers/ru.keepcoder.Telegram/Data/tmp/telegram-cloud-photo-size-2-5206368487771675066-y.jpg)



------------------------------------------------------------------------------------------------------------------------------

### Program Code

---

the program should run and be able to handle 

1. add Journey

2. list all journeys (without filters)

3. view daily summary (for one passenger type)



##### InputValidator

```java
import java.util.Scanner;

public class InputValidator {
    Scanner scanner = new Scanner(System.in);
    JourneyTracker journeyTracker = new JourneyTracker();

    // Kvasnovsky (2025)
    int readIntInRange(int min, int max) {
        int userInput = min - 1;
        while (userInput < min || userInput > max) {
            System.out.print("Enter a whole number (" + min + "-" + max + "): ");

            while (!scanner.hasNextInt()) {
                System.out.print("That's not a whole number! Try again: ");
                scanner.next();
            }

            userInput = scanner.nextInt();
            if (userInput < min || userInput > max) {
                System.out.print("Invalid! Enter a number between " + min + " and " + max + ": ");
            }
        }
        scanner.nextLine();
        return userInput;
    }

    boolean readYesNo() {
        boolean isValid = false;
        boolean userChoice = false;

        while (!isValid) {
            System.out.print("Enter Y/N: ");
            String userInput = scanner.nextLine();
            userInput = userInput.toLowerCase().strip();
            if (userInput.equals("y") || userInput.equals("yes")) {
                userChoice = true;
                isValid = true;
            }
            else if (userInput.equals("n") || userInput.equals("no")) {
                isValid = true;
            }
            else {
                System.out.println("Invalid! Enter Yes or No!");
            }
        }

        return userChoice;
    }

    public PassengerType askForPassengerTypeID() {
        System.out.println("What is the passenger type?");
        journeyTracker.listAllPassengerTypes();
        int passengerTypeID = this.readIntInRange(1, 4); // hard coded, will change

        return journeyTracker.findPassengerTypeByID(passengerTypeID);
    }
}

```

##### Journey

```java
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Journey {
    // states
    private int journeyID;
    // Bro Code (2024)
    private LocalDateTime datetime;
    private boolean isPeak;
    private Zone startZone;
    private Zone endZone;
    private PassengerType passengerType;
    private double baseFare;
    private double discountedFare;
    private double chargedFare;
    private boolean dailyCapReached;
    // formatter
    DecimalFormat decimalPlaces2 = new DecimalFormat( "#.00" );

    // constructors
    public Journey() {
        this.journeyID = 0;
        this.datetime = LocalDateTime.now();
        this.isPeak = false;
        this.startZone = null;
        this.endZone = null;
        this.passengerType = null;
        this.baseFare = 0.0;
        this.discountedFare = 0.0;
        this.chargedFare = 0.0;
        this.dailyCapReached = false;
    }
    public Journey(int journeyID, boolean isPeak, Zone startZone, Zone endZone, PassengerType passengerType, double baseFare, double discountedFare, double chargedFare, boolean dailyCapReached) {
        this.journeyID = journeyID;
        this.datetime = LocalDateTime.now();
        this.isPeak = isPeak;
        this.startZone = startZone;
        this.endZone = endZone;
        this.passengerType = passengerType;
        this.baseFare = baseFare;
        this.discountedFare = discountedFare;
        this.chargedFare = chargedFare;
        this.dailyCapReached = dailyCapReached;
    }

    // getters
    public int getJourneyID() {
        return journeyID;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }
    public boolean isPeak() {
        return isPeak;
    }
    public Zone getStartZone() {
        return startZone;
    }
    public Zone getEndZone() {
        return endZone;
    }
    public PassengerType getPassengerType() {
        return passengerType;
    }
    public double getBaseFare() {
        return baseFare;
    }
    public double getDiscountedFare() {
        return discountedFare;
    }
    public double getChargedFare() {
        return chargedFare;
    }
    public boolean getDailyCapReached() {
        return dailyCapReached;
    }

    // setters
    public void setJourneyID(int journeyID) {
        this.journeyID = journeyID;
    }
    public void setIsPeak(boolean isPeak) {
        this.isPeak = isPeak;
    }
    public void setStartZone(Zone startZone) {
        this.startZone = startZone;
    }
    public void setEndZone(Zone endZone) {
        this.endZone = endZone;
    }
    public void setPassengerType(PassengerType passengerType) {
        this.passengerType = passengerType;
    }
    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }
    public void setDiscountedFare(double discountedFare) {
        this.discountedFare = discountedFare;
    }
    public void setChargedFare(double chargedFare) {
        this.chargedFare = chargedFare;
    }
    public void setDailyCapReached(boolean dailyCapReached) {
        this.dailyCapReached = dailyCapReached;
    }

    // other methods
    public void printJourneyDetails() {
        System.out.println("--------------------------");
        System.out.println("Journey ID: " + this.journeyID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        String formattedDatetime = this.datetime.format(formatter);
        System.out.println("Datetime: " + formattedDatetime);
        System.out.print("Timeband: ");
        if (isPeak) {
            System.out.println("peak");
        } else {
            System.out.println("off-peak");
        }
        System.out.println("Start zone: " + this.startZone.getZoneID() + ". " + this.startZone.getZoneName());
        System.out.println("End zone: " + this.endZone.getZoneID() + ". " + this.endZone.getZoneName());
        System.out.println("Passenger type: " + this.passengerType.getPassengerTypeName());
        System.out.println("Base fare: £" + decimalPlaces2.format(this.baseFare));
        System.out.println("Discounted fare: £" + decimalPlaces2.format(this.discountedFare));
        System.out.println("Charged fare: £" + decimalPlaces2.format(this.chargedFare));
        System.out.println("Daily cap reached: " + this.dailyCapReached);
    }
}

```

##### JourneyTracker

```java
import java.util.ArrayList;
import java.util.List;

public class JourneyTracker {
    // lists
    private List<Journey> journeysList = new ArrayList<>();
    // passenger type & zones are hard coded zones since java class needed
    // for the assignment has not been provided yet
    private PassengerType[] passengerTypesArr = {new PassengerType(1, "Adult", 0, 8.0), new PassengerType(2, "Student", 25, 6.0), new PassengerType(3, "Child", 50, 4.0), new PassengerType(4, "Senior Citizen", 30, 7.0)};
    private Zone[] zonesArr = {new Zone(1, "Nottingham"), new Zone(2, "London"), new Zone(3, "Birmingham"), new Zone(4, "Frankfurt"), new Zone(5, "Edinburgh")};

    // daily running total for each Passenger Type
    private double adultTotalBaseFare = 0.0;
    private double studentTotalBaseFare = 0.0;
    private double childTotalBaseFare = 0.0;
    private double seniorCitizenTotalBaseFare = 0.0;

    private double adultTotalDiscountedFare = 0.0;
    private double studentTotalDiscountedFare = 0.0;
    private double childTotalDiscountedFare = 0.0;
    private double seniorCitizenTotalDiscountedFare = 0.0;

    private double adultTotalChargedFare = 0.0;
    private double studentTotalChargedFare = 0.0;
    private double childTotalChargedFare = 0.0;
    private double seniorCitizenTotalChargedFare = 0.0;

    private int adultTotalJourneyNum = 0;
    private int studentTotalJourneyNum = 0;
    private int childTotalJourneyNum = 0;
    private int seniorCitizenTotalJourneyNum = 0;


    // methods
    // find by ID
    public Journey findJourneyByID(int journeyID) {
        for (Journey j : journeysList) {
            if (j.getJourneyID() == journeyID) {
                return j;
            }
        }
        return null;
    }

    public PassengerType findPassengerTypeByID(int passengerTypeID) { // needs testing
        for (PassengerType pt : passengerTypesArr) {
            if (pt.getPassengerTypeID() == passengerTypeID) {
                return pt;
            }
        }
        return null;
    }

    public Zone findZoneByID(int zoneID) { // needs testing
        for (Zone z : zonesArr) {
            if (z.getZoneID() == zoneID) {
                return z;
            }
        }
        return null;
    }

    // listing
    public void listAllJourneys() {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys has been added yet.");
        }
        else {
            System.out.println("------   Journeys   ------");
            for (Journey j : journeysList) {
                j.printJourneyDetails();
            }
            System.out.println("--------------------------");
        }
    }

    public void listAllZones() {
        if (zonesArr.length == 0) {
            System.out.println("No zones are available.");
        }
        else {
            System.out.println("------   Zones   ------");
            for (Zone z : zonesArr) {
                z.printZoneDetails();
            }
            System.out.println("------------------------");

        }
    }

    public void listAllPassengerTypes() {
        if (passengerTypesArr.length == 0) {
            System.out.println("No passenger types are available.");
        }
        else {
            System.out.println("------   Passenger Types   ------");
            for (PassengerType pt : passengerTypesArr) {
                pt.printPassengerTypeDetails();
            }
            System.out.println("---------------------------------");

        }
    }

    // methods for adding Journey
    // maybe create a function to further optimise this
    public void addToPassengerTypeDailyTotal(Journey journey) {
        if (journey.getPassengerType().getPassengerTypeID() == 1) {
            this.adultTotalBaseFare += journey.getBaseFare();
            this.adultTotalDiscountedFare += journey.getDiscountedFare();
            this.adultTotalChargedFare += journey.getChargedFare();
            this.adultTotalJourneyNum++;
        }
        else if (journey.getPassengerType().getPassengerTypeID() == 2) {
            this.studentTotalBaseFare += journey.getBaseFare();
            this.studentTotalDiscountedFare += journey.getDiscountedFare();
            this.studentTotalChargedFare += journey.getChargedFare();
            this.studentTotalJourneyNum++;
        }
        else if (journey.getPassengerType().getPassengerTypeID() == 3) {
            this.childTotalBaseFare += journey.getBaseFare();
            this.childTotalDiscountedFare += journey.getDiscountedFare();
            this.childTotalChargedFare += journey.getChargedFare();
            this.childTotalJourneyNum++;
        }
        else if (journey.getPassengerType().getPassengerTypeID() == 4) {
            this.seniorCitizenTotalBaseFare += journey.getBaseFare();
            this.seniorCitizenTotalDiscountedFare += journey.getDiscountedFare();
            this.seniorCitizenTotalChargedFare += journey.getChargedFare();
            this.seniorCitizenTotalJourneyNum++;
        }
    }

    public void addJourney(Journey journey) {
        journeysList.add(journey);
        System.out.println("Journey with Journey ID of " + journey.getJourneyID() + " has been added.");

        addToPassengerTypeDailyTotal(journey);
    }

    private void printSummaryForPassengerType(double baseFare, double discountedFare, double chargedFare, int journeysNum) {
        System.out.println("Total base fare: " + baseFare);
        System.out.println("Total discounted fare: " + discountedFare);
        System.out.println("Total charged fare: " + chargedFare);
        System.out.println("Total number of journeys: " + journeysNum);
    }

    public void printSummaryForPassengerTypeByID(int passengerTypeID) {
        PassengerType passengerType = this.findPassengerTypeByID(passengerTypeID);
        System.out.println("---- Daily Summary for " + passengerType.getPassengerTypeName() + " journeys ----");
        if (passengerTypeID == 1) {
            printSummaryForPassengerType(adultTotalBaseFare, adultTotalDiscountedFare, adultTotalChargedFare, adultTotalJourneyNum);
        }
        else if (passengerTypeID == 2) {
            printSummaryForPassengerType(studentTotalBaseFare, studentTotalDiscountedFare, studentTotalChargedFare, studentTotalJourneyNum);
        }
        else if (passengerTypeID == 3) {
            printSummaryForPassengerType(childTotalBaseFare, childTotalDiscountedFare, childTotalChargedFare, childTotalJourneyNum);
        }
        else if (passengerTypeID == 4) {
            printSummaryForPassengerType(seniorCitizenTotalBaseFare, seniorCitizenTotalDiscountedFare, seniorCitizenTotalChargedFare, seniorCitizenTotalJourneyNum);
        }
    }
}
```

##### JourneyTrackerApp

```java
import java.util.Scanner;

public class JourneyTrackerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JourneyTracker journeyTracker = new JourneyTracker();
        MainMenu mainMenu = new MainMenu();
        InputValidator inputValidator = new InputValidator();

        // counters logic needs to be fixed
        int journeyIDCounter = 1;
        int zoneIDCounter = 1;
        int passengerTypeIDCounter = 1;

        int userInput;

        mainMenu.printWelcome();

        do {
            mainMenu.printMainMenu();
            userInput = inputValidator.readIntInRange(0, 5);

            if (userInput == 1) {
                System.out.println("+++++++ Adding a journey +++++++");
                System.out.println("Was the journey on peak?");
                boolean isPeak = inputValidator.readYesNo();

                System.out.println("What is the ID of the start zone?");
                journeyTracker.listAllZones();
                int startZoneID = inputValidator.readIntInRange(1, 5); // hard coded, will change
                Zone startZone = journeyTracker.findZoneByID(startZoneID);

                System.out.println("What is the ID of the end zone?");
                journeyTracker.listAllZones();
                int endZoneID = inputValidator.readIntInRange(1, 5); // hard coded, will change
                Zone endZone = journeyTracker.findZoneByID(endZoneID);

                PassengerType passengerType = inputValidator.askForPassengerTypeID();

                // calculations should happen here, will be added
                // when java class for the assignment will be provided.
                // will use hard coded values for now
                double baseFare = passengerType.getDailyCap() / 2;
                double discountedFare = baseFare - (baseFare * passengerType.getDiscountPercentage() / 100);
                double chargedFare = Math.min(discountedFare, passengerType.getDailyCap());
                boolean dailyCapReached = chargedFare == passengerType.getDailyCap();

                Journey newJourney = new Journey(journeyIDCounter, isPeak, startZone, endZone, passengerType, baseFare, discountedFare, chargedFare, dailyCapReached);
                journeyTracker.addJourney(newJourney);
            }
            else if (userInput == 2) {
                journeyTracker.listAllJourneys();
            }
            else if (userInput == 3) {
                PassengerType passengerType = inputValidator.askForPassengerTypeID();
                journeyTracker.printSummaryForPassengerTypeByID(passengerType.getPassengerTypeID());
            }
        }
        while (userInput != 0);
        scanner.close();
    }
}

```

##### MainMenu

```java
public class MainMenu {
    // methods
    public void printWelcome() {
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("++   Welcome to CityRide Lite!   ++");
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    public void printMainMenu() {
        System.out.println("\n");
        System.out.println("Please choose from the following:");
        System.out.println("1. Add journey");
        System.out.println("2. List journeys");
        System.out.println("3. View summaries");
        System.out.println("4. Remove journey");
        System.out.println("5. Reset day");
        System.out.println("0. Exit \n");
    }
}

```

##### PassengerType

```java
import java.text.DecimalFormat;

public class PassengerType {
    // states
    private int passengerTypeID;
    private String passengerTypeName;
    private int discountPercentage;
    private double dailyCap;
    DecimalFormat decimalPlaces2 = new DecimalFormat( "#.00" );

    // constructors
    public PassengerType() {
        this.passengerTypeID = 0;
        this.passengerTypeName = "Unknown";
        this.discountPercentage = 0;
        this.dailyCap = 0;
    }
    public PassengerType(int passengerTypeID, String passengerTypeName, int discountPercentage, double dailyCap) {
        this.passengerTypeID = passengerTypeID;
        this.passengerTypeName = passengerTypeName;
        this.discountPercentage = discountPercentage;
        this.dailyCap = dailyCap;
    }

    // getters
    public int getPassengerTypeID() {
        return this.passengerTypeID;
    }
    public String getPassengerTypeName() {
        return this.passengerTypeName;
    }
    public int getDiscountPercentage() {
        return this.discountPercentage;
    }
    public double getDailyCap() {
        return this.dailyCap;
    }

    // setters
    public void setPassengerTypeID(int passengerTypeID) {
        this.passengerTypeID = passengerTypeID;
    }
    public void setPassengerTypeName(String passengerTypeName) {
        this.passengerTypeName = passengerTypeName;
    }
    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    public void setDailyCap(double dailyCap) {
        this.dailyCap = dailyCap;
    }

    // other methods
    public void printPassengerTypeDetails() {
        System.out.println(this.passengerTypeID + ". " + this.passengerTypeName + " | " + this.discountPercentage + "% | £" + decimalPlaces2.format(this.dailyCap));
    }
}

```

##### Zone

```java
public class Zone {
    // states
    private int zoneID;
    private String zoneName;

    // constructors
    public Zone() {
        this.zoneID = 0;
        this.zoneName = "Unknown";
    }
    public Zone(int zoneID, String zoneName) {
        this.zoneID = zoneID;
        this.zoneName = zoneName;
    }

    // getters
    public int getZoneID() {
        return this.zoneID;
    }
    public String getZoneName() {
        return this.zoneName;
    }

    // setters
    // no setter for zoneID since it will not change as per the assignment sheet
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    // other methods
    public void printZoneDetails() {
        System.out.println(this.zoneID + ". " + this.zoneName);
    }
}

```





------------------------------------------------------------------------------------------------------------------------------

### Updated Gantt Chart

------------------------------------------------------------------------------------------------------------------------------

![](/Users/assemyeleussiz/Documents/Ganttchart_milestone3.png)

------------------------------------------------------------------------------------------------------------------------------

### Diary Entries

------------------------------------------------------------------------------------------------------------------------------

### 02/02/2026 - Diary Entry 7 - Beginning of Coding, Classes

Today I started coding the program. During one of our previous Java classes, our teacher showed us an example of a program that consisted of the class Main and the class Car. The Car class was a demonstration of all of the fundamental components of a typical Java class - states, constructors, getters, setters and other methods. Having that knowledge, I started creating my own classes for the program. Immediately, a handful of questions appeared in my mind. I was unsure if every class needed their own .java file, if money needed to be stored using double data type, if every state needed their own set of getters and setters. I used my class diagram drawn during milestone 2 as a reference, only to find out that I had thought about the money question before but forgot to ask my teacher. Feeling uncertain, I created empty classes as per my class diagram and ended the day.

### 06/02/2026 - Diary Entry 8 - Implementing basic features

Yesterday was another Java class with my teacher, where the teacher continued to develop the Car program using list arrays and 2 new classes - Garage and CarApp. The list array confused me at first with its odd syntax, it seems like ‘List’ is an interface that can be implemented using the ‘ArrayList’ class. I decided to use this for my journeys array since new elements will be added to it continuously, whereas for the list of zones and passenger types I used a simple java array under the assumption that the latter cannot be changed while the program runs. In some parts of the program I had to code my own temporary implementation of the logic that the java class that was supposed to be provided to us should be responsible for due to the absence of said java class (for example, the existence of the Zone class in itself could be a proof of that, but I would not know since I am still very unsure as to what the provided java class will look like). The temporary implementation was often hardcoded, but such code will be deleted once we get provided with the java class for the assignment.

As my code grew bigger, I started struggling with the placement of my methods more. Being totally new to the Object Oriented Programming Paradigm, I am still unsure what should be made into a class, what kind of structure needs to exist between classes and what classes certain methods should belong to. While classes such as Journey, JourneyTracker, PassengerType are pretty straightforward, some other classes I decided to create like InputValidator and MainMenu are a bit more experimental. Same goes for the methods, take for example the method askForPassengerTypeID() in the class InputValidator. The reason this function needed to be created was because this exact block of code needed to be reused in the main method of the application of class JourneyTrackerApp. I was unsure if the method should belong to the JourneyTrackerApp class or the InputValidator, but decided to put it into the latter since the idea of asking the user for a certain kind of input seemed to fit more into the InputValidator class. 

### 08/02/2026 - Diary Entry 9 - Coding the logic of core features

Today, I carried on with the programming of my application. While implementing logic across multiple files of classes, I realized that my program could largely benefit from global variables for things such as an instance of a class to format numbers to 2 decimal places, or a global variable for the number of how many different zones and passenger types exist in the program, so that when calling the InputValidator method for reading Integers within a certain range, instead of the hard coded value I could pass the variable. I believe a solution for my problem already exists within the standard Java library, but I will need to learn more about the language to solve it. In the future, I will try to get rid of all of the hard coded values. Another task that will be performed in the future is creating methods to break down my bigger methods, as the reason why I have not yet done this is because I am unsure where to put said methods. The most obvious example is the code block that activates upon the user choosing to add a journey in the JourneyTrackerApp class. It needs to be separated into its own function, but due to the lack of my knowledge I will leave that for the next milestone. The addToPassengerTypeDailyTotal method in the JourneyTracker class is also overly verbose, and with this particular function I am rather lost as to how to fix it. If I fail to come up with a solution myself, I plan to ask my teacher about it. I will also ask my tutor about creating IDs for the instances of the classes Journey, PassengerType and Zone as right now my program automatically assigns an ID of 1 to all of the newly created Journeys.

As of now, my program has the basic features of adding a journey, listing all journeys and viewing a daily summary for a certain passenger type. Removing a journey, resetting the day and all of the filters for the listing and summaries will be implemented later, in milestone 4.

------------------------------------------------------------------------------------------------------------------------------
