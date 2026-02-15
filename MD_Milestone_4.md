# IY4113 Milestone 4

| Assessment Details | Please Complete All Details                                      |
| ------------------ | ---------------------------------------------------------------- |
| Group              | A                                                                |
| Module Title       | Applied Software Engineering using Object Orientated Programming |
| Assessment Type    | Java Fundamentals Part 4                                         |
| Module Tutor Name  | Jonathan Shore                                                   |
| Student ID Number  | P479574                                                          |
| Date of Submission | 15/02/2026                                                       |
| Word Count         | 4554                                                             |
| GItHub Link        | https://github.com/assemT0479574-umapyoi/T0479574_IY4113_java    |

- [x] *I confirm that this assignment is my own work. Where I have referred to academic sources, I have provided in-text citations and included the sources in
  the final reference list.*

- [x] *Where I have used AI, I have cited and referenced appropriately.

------------------------------------------------------------------------------------------------------------------------------

### Program Code

---

CityRideDataset

```java
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

final class CityRideDataset {

    private CityRideDataset() {}

    public static final int MIN_ZONE = 1;
    public static final int MAX_ZONE = 5;

    public enum TimeBand {
        PEAK,
        OFF_PEAK
    }

    public enum PassengerType {
        ADULT,
        STUDENT,
        CHILD,
        SENIOR_CITIZEN
    }

    public static final Map<PassengerType, BigDecimal> DISCOUNT_RATE = Map.of(
            PassengerType.ADULT, new BigDecimal("0.00"),
            PassengerType.STUDENT, new BigDecimal("0.25"),
            PassengerType.CHILD, new BigDecimal("0.50"),
            PassengerType.SENIOR_CITIZEN, new BigDecimal("0.30")
    );

    public static final Map<PassengerType, BigDecimal> DAILY_CAP = Map.of(
            PassengerType.ADULT, new BigDecimal("8.00"),
            PassengerType.STUDENT, new BigDecimal("6.00"),
            PassengerType.CHILD, new BigDecimal("4.00"),
            PassengerType.SENIOR_CITIZEN, new BigDecimal("7.00")
    );

    public static final Map<String, BigDecimal> BASE_FARE = buildBaseFare();

    public static BigDecimal getBaseFare(int fromZone, int toZone, TimeBand timeBand) {
        return BASE_FARE.get(key(fromZone, toZone, timeBand));
    }

    public static String key(int fromZone, int toZone, TimeBand timeBand) {
        return fromZone + "-" + toZone + "-" + timeBand.name();
    }

    private static BigDecimal money(String amount) {
        return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
    }

    private static Map<String, BigDecimal> buildBaseFare() {
        Map<String, BigDecimal> m = new HashMap<>();

        // Peak fares
        put(m,1,1,TimeBand.PEAK,"2.50"); put(m,1,2,TimeBand.PEAK,"3.20");
        put(m,1,3,TimeBand.PEAK,"3.80"); put(m,1,4,TimeBand.PEAK,"4.40");
        put(m,1,5,TimeBand.PEAK,"5.00");

        put(m,2,1,TimeBand.PEAK,"3.20"); put(m,2,2,TimeBand.PEAK,"2.30");
        put(m,2,3,TimeBand.PEAK,"3.10"); put(m,2,4,TimeBand.PEAK,"3.80");
        put(m,2,5,TimeBand.PEAK,"4.50");

        put(m,3,1,TimeBand.PEAK,"3.80"); put(m,3,2,TimeBand.PEAK,"3.10");
        put(m,3,3,TimeBand.PEAK,"2.10"); put(m,3,4,TimeBand.PEAK,"3.00");
        put(m,3,5,TimeBand.PEAK,"3.70");

        put(m,4,1,TimeBand.PEAK,"4.40"); put(m,4,2,TimeBand.PEAK,"3.80");
        put(m,4,3,TimeBand.PEAK,"3.00"); put(m,4,4,TimeBand.PEAK,"2.00");
        put(m,4,5,TimeBand.PEAK,"2.90");

        put(m,5,1,TimeBand.PEAK,"5.00"); put(m,5,2,TimeBand.PEAK,"4.50");
        put(m,5,3,TimeBand.PEAK,"3.70"); put(m,5,4,TimeBand.PEAK,"2.90");
        put(m,5,5,TimeBand.PEAK,"1.90");

        // Off-peak fares
        put(m,1,1,TimeBand.OFF_PEAK,"2.00"); put(m,1,2,TimeBand.OFF_PEAK,"2.70");
        put(m,1,3,TimeBand.OFF_PEAK,"3.20"); put(m,1,4,TimeBand.OFF_PEAK,"3.70");
        put(m,1,5,TimeBand.OFF_PEAK,"4.20");

        put(m,2,1,TimeBand.OFF_PEAK,"2.70"); put(m,2,2,TimeBand.OFF_PEAK,"1.90");
        put(m,2,3,TimeBand.OFF_PEAK,"2.60"); put(m,2,4,TimeBand.OFF_PEAK,"3.20");
        put(m,2,5,TimeBand.OFF_PEAK,"3.80");

        put(m,3,1,TimeBand.OFF_PEAK,"3.20"); put(m,3,2,TimeBand.OFF_PEAK,"2.60");
        put(m,3,3,TimeBand.OFF_PEAK,"1.70"); put(m,3,4,TimeBand.OFF_PEAK,"2.50");
        put(m,3,5,TimeBand.OFF_PEAK,"3.10");

        put(m,4,1,TimeBand.OFF_PEAK,"3.70"); put(m,4,2,TimeBand.OFF_PEAK,"3.20");
        put(m,4,3,TimeBand.OFF_PEAK,"2.50"); put(m,4,4,TimeBand.OFF_PEAK,"1.60");
        put(m,4,5,TimeBand.OFF_PEAK,"2.40");

        put(m,5,1,TimeBand.OFF_PEAK,"4.20"); put(m,5,2,TimeBand.OFF_PEAK,"3.80");
        put(m,5,3,TimeBand.OFF_PEAK,"3.10"); put(m,5,4,TimeBand.OFF_PEAK,"2.40");
        put(m,5,5,TimeBand.OFF_PEAK,"1.50");

        return Map.copyOf(m);
    }

    private static void put(Map<String, BigDecimal> m, int from, int to, TimeBand band, String amount) {
        m.put(key(from, to, band), money(amount));
    }
}
```

Formatter

```java
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

public class Formatter {
    public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    public BigDecimal formatBigDecimal2dp(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    // getting names of enums
    public String getNameOfTimeBand(CityRideDataset.TimeBand timeBand) {
        String timeBandName = "Unknown";
        if (timeBand == CityRideDataset.TimeBand.PEAK) {
            timeBandName = "Peak";
        }
        else if (timeBand == CityRideDataset.TimeBand.OFF_PEAK) {
            timeBandName = "Off-peak";
        }
        return timeBandName;
    }

    public String getNameOfPassengerType(CityRideDataset.PassengerType passengerType) {
        String passengerTypeName = "Unknown";
        if (passengerType == CityRideDataset.PassengerType.ADULT) {
            passengerTypeName = "Adult";
        }
        else if (passengerType == CityRideDataset.PassengerType.STUDENT) {
            passengerTypeName = "Student";
        }
        else if (passengerType == CityRideDataset.PassengerType.CHILD) {
            passengerTypeName = "Child";
        }
        else if (passengerType == CityRideDataset.PassengerType.SENIOR_CITIZEN) {
            passengerTypeName = "Senior Citizen";
        }
        return passengerTypeName;
    }
}

```

InputValidator

```java
import java.util.Scanner;

public class InputValidator {
    Scanner scanner = new Scanner(System.in);

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
}

```

Journey

```java
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Journey {
    // states
    private int journeyId;
    // Bro Code (2024)
    private LocalDateTime datetime;
    private CityRideDataset.TimeBand timeBand;
    private Zone startZone;
    private Zone endZone;
    private CityRideDataset.PassengerType passengerType;
    private BigDecimal baseFare;
    private BigDecimal discountedFare;
    private BigDecimal chargedFare;
    private boolean dailyCapReached;
    // money formatter
    Formatter formatter = new Formatter();

    // constructors
    public Journey() {
        this.journeyId = 0;
        this.datetime = LocalDateTime.now();
        this.timeBand = null;
        this.startZone = null;
        this.endZone = null;
        this.passengerType = null;
        this.baseFare = new BigDecimal("0.0");
        this.discountedFare = new BigDecimal("0.0");
        this.chargedFare = new BigDecimal("0.0");
        this.dailyCapReached = false;
    }
    public Journey(int journeyID, CityRideDataset.TimeBand timeBand, Zone startZone, Zone endZone, CityRideDataset.PassengerType passengerType, BigDecimal baseFare, BigDecimal discountedFare, BigDecimal chargedFare, boolean dailyCapReached) {
        this.journeyId = journeyID;
        this.datetime = LocalDateTime.now();
        this.timeBand = timeBand;
        this.startZone = startZone;
        this.endZone = endZone;
        this.passengerType = passengerType;
        this.baseFare = baseFare;
        this.discountedFare = discountedFare;
        this.chargedFare = chargedFare;
        this.dailyCapReached = dailyCapReached;
    }


    // getters
    public int getJourneyId() {
        return journeyId;
    }
    public CityRideDataset.PassengerType getPassengerType() {
        return passengerType;
    }
    public BigDecimal getBaseFare() {
        return baseFare;
    }
    public BigDecimal getDiscountedFare() {
        return discountedFare;
    }
    public BigDecimal getChargedFare() {
        return chargedFare;
    }
    public Zone getStartZone() {
        return startZone;
    }
    public Zone getEndZone() {
        return endZone;
    }
    public CityRideDataset.TimeBand getTimeBand() {
        return timeBand;
    }
    public boolean getDailyCapReached() {
        return dailyCapReached;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }

    // other methods
    public void printJourneyDetails() {
        System.out.println("--------------------------");
        System.out.println("Journey ID: " + this.journeyId);
        String formattedDatetime = this.datetime.format(formatter.dateTimeFormatter);
        System.out.println("Datetime: " + formattedDatetime);
        System.out.print("Timeband: ");
        System.out.println(formatter.getNameOfTimeBand(this.timeBand));
        System.out.println("Start zone: " + this.startZone.getZoneId() + ". " + this.startZone.getZoneName());
        System.out.println("End zone: " + this.endZone.getZoneId() + ". " + this.endZone.getZoneName());
        System.out.println("Passenger type: " + formatter.getNameOfPassengerType(passengerType));

        System.out.println("Base fare: £" + formatter.formatBigDecimal2dp(this.baseFare));
        System.out.println("Discounted fare: £" + formatter.formatBigDecimal2dp(this.discountedFare));
        System.out.println("Charged fare: £" + formatter.formatBigDecimal2dp(this.chargedFare));

        System.out.println("Daily cap reached: " + this.dailyCapReached);
    }
}

```

JourneyTracker

```java
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JourneyTracker {
    // states
    private int journeyIdCounter = 1;
    Formatter formatter = new Formatter();
    // lists
    private List<Journey> journeysList = new ArrayList<>();
    // arrays
    private Zone[] zonesArr = {new Zone(1, "Central"),
            new Zone(2, "Inner"),
            new Zone(3, "Suburban"),
            new Zone(4, "Outer"),
            new Zone(5,"Rural")};

    // daily running total for each Passenger Type
    SummaryForPassengerType adultSummary = new SummaryForPassengerType();
    SummaryForPassengerType studentSummary = new SummaryForPassengerType();
    SummaryForPassengerType childSummary = new SummaryForPassengerType();
    SummaryForPassengerType seniorCitizenSummary = new SummaryForPassengerType();

    // methods
    // getters
    public int getJourneyIdCounter() {
        return journeyIdCounter;
    }
    public SummaryForPassengerType getSummaryObjForPassengerType(CityRideDataset.PassengerType passengerType) {
        SummaryForPassengerType foundSummary;
        if (passengerType == CityRideDataset.PassengerType.ADULT) {
            foundSummary = adultSummary;
        }
        else if (passengerType == CityRideDataset.PassengerType.STUDENT) {
            foundSummary = studentSummary;
        }
        else if (passengerType == CityRideDataset.PassengerType.CHILD) {
            foundSummary = childSummary;
        }
        else if (passengerType == CityRideDataset.PassengerType.SENIOR_CITIZEN) {
            foundSummary = seniorCitizenSummary;
        }
        else {
            foundSummary = null;
        }
        return foundSummary;
    }

    // setters
    public void setJourneyIdCounter(int journeyIdCounter) {
        this.journeyIdCounter = journeyIdCounter;
    }

    // adding Journey
    public void addJourney(Journey journey) {
        journeysList.add(journey);
        System.out.println("Journey with Journey ID of " + journey.getJourneyId() + " has been added.");

        SummaryForPassengerType summary = getSummaryObjForPassengerType(journey.getPassengerType());
        summary.addJourneyToSum(journey);
        journey.getStartZone().incrementCount();
        journey.getEndZone().incrementCount();
    }

    public Zone findZoneById(int zoneId) {
        for (Zone z : zonesArr) {
            if (z.getZoneId() == zoneId) {
                return z;
            }
        }
        return null;
    }

    // listing
    public void listAllZones() {
        if (zonesArr.length == 0) {
            System.out.println("No zones available!");
        }
        else {
            System.out.println("------   Zones   ------");
            for (Zone z : zonesArr) {
                System.out.println(z.getZoneId() + ". " + z.getZoneName());
            }
            System.out.println("-----------------------");
        }
    }

    public void listAllPassengerTypes() {
        System.out.println("------   Passenger Types   ------");
        for (int i = 0; i < CityRideDataset.PassengerType.values().length; i++) {
            String passengerTypeName = formatter.getNameOfPassengerType(CityRideDataset.PassengerType.values()[i]);
            System.out.println((i + 1) + ". " + passengerTypeName);
        }
        System.out.println("---------------------------------");
    }

    // listing journeys
    public void listAllJourneys() {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            System.out.println("------   Journeys   ------");
            for (Journey j : journeysList) {
                j.printJourneyDetails();
            }
            System.out.println("--------------------------");
        }
    }

    public void listJourneysByPassengerType(CityRideDataset.PassengerType passengerType) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            List<Journey> passengerTypeJourneysList = new ArrayList<>();
            for (Journey j : journeysList) {
                if (j.getPassengerType() == passengerType) {
                    passengerTypeJourneysList.add(j);
                }
            }
            if (passengerTypeJourneysList.isEmpty()) {
                System.out.println("There are no journeys for this passenger type yet.");
            }
            else {
                System.out.println("------ " + formatter.getNameOfPassengerType(passengerType) + " Journeys ------");

                for (Journey j : passengerTypeJourneysList) {
                    j.printJourneyDetails();
                }
                System.out.println("--------------------------");
            }
        }
    }

    public void listJourneysByTimeBand(CityRideDataset.TimeBand timeBand) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            List<Journey> timeBandJourneysList = new ArrayList<>();
            for (Journey j : journeysList) {
                if (j.getTimeBand() == timeBand) {
                    timeBandJourneysList.add(j);
                }
            }
            if (timeBandJourneysList.isEmpty()) {
                System.out.println("There are no journeys for this timeband yet.");
            }
            else {
                System.out.println("------ " + formatter.getNameOfTimeBand(timeBand) + " Journeys ------");

                for (Journey j : timeBandJourneysList) {
                    j.printJourneyDetails();
                }
                System.out.println("--------------------------");
            }
        }
    }

    public void listJourneysByZone(Zone zone, boolean isStartZone) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            List<Journey> zonesJourneysList = new ArrayList<>();
            for (Journey j : journeysList) {
                Zone zoneToCompare;
                if (isStartZone) {
                    zoneToCompare = j.getStartZone();
                }
                else {
                    zoneToCompare = j.getEndZone();
                }
                if (zoneToCompare == zone) {
                    zonesJourneysList.add(j);
                }
            }
            String zoneDescription = isStartZone ? "Start" : "End";
            if (zonesJourneysList.isEmpty()) {
                System.out.println("There are no journeys for this " + zoneDescription + " zone yet.");
            }
            else {
                System.out.println("------ " + zoneDescription + " zone Journeys ------");

                for (Journey j : zonesJourneysList) {
                    j.printJourneyDetails();
                }
                System.out.println("--------------------------");
            }
        }
    }

    public void listJourneysByHours(int startHour, int endHour) {
        if (journeysList.isEmpty()) {
            System.out.println("No journeys have been added yet.");
        }
        else {
            List<Journey> hoursJourneysList = new ArrayList<>();
            for (Journey j : journeysList) {
                if (j.getDatetime().getHour() >= startHour && j.getDatetime().getHour() <= endHour) {
                    hoursJourneysList.add(j);
                }
            }
            if (hoursJourneysList.isEmpty()) {
                System.out.println("There are no journeys for the " + startHour + "-" + endHour + " hours yet.");
            }
            else {
                System.out.println("------ " + startHour + "-" + endHour + " hours Journeys ------");

                for (Journey j : hoursJourneysList) {
                    j.printJourneyDetails();
                }
                System.out.println("--------------------------");
            }
        }
    }

    // Summary: daily summaries
    public int getTotalJourneysNum() {
        return adultSummary.getTotalJourneyNum() + studentSummary.getTotalJourneyNum() + childSummary.getTotalJourneyNum() + seniorCitizenSummary.getTotalJourneyNum();
    }

    public BigDecimal getTotalCostsCharged() {
        BigDecimal totalCostsCharged = adultSummary.getTotalChargedFare().add(studentSummary.getTotalChargedFare()).add(childSummary.getTotalChargedFare()).add(seniorCitizenSummary.getTotalChargedFare());
        return formatter.formatBigDecimal2dp(totalCostsCharged);
    }

    public BigDecimal getAverageCostPerJourney() {
        BigDecimal totalChargedFare = getTotalCostsCharged();
        BigDecimal averageCost;
        if (getTotalJourneysNum() == 0) {
            averageCost = new BigDecimal("0.00");
        }
        else {
            BigDecimal totalJourneysNum = new BigDecimal(getTotalJourneysNum());
            averageCost = totalChargedFare.divide(totalJourneysNum, RoundingMode.HALF_UP);
        }
        return formatter.formatBigDecimal2dp(averageCost);
    }

    public Journey getMostExpensiveJourney() {
        BigDecimal mostExpensiveCharge = new BigDecimal("0.00");
        int mostExpensiveJourneyIndex = -1;
        Journey mostExpensiveJourney;

        for (Journey j : journeysList) {
            if (j.getChargedFare().compareTo(mostExpensiveCharge) > 0) {
                mostExpensiveJourneyIndex = journeysList.indexOf(j);
                mostExpensiveCharge = j.getChargedFare();
            }
        }
        if (mostExpensiveJourneyIndex != -1) {
            mostExpensiveJourney = journeysList.get(mostExpensiveJourneyIndex);
        }
        else {
            mostExpensiveJourney = null;
        }
        return mostExpensiveJourney;
    }

    // Summary: category counts
    public int[] getPeakVsOffPeakCount() {
        int peakCounter = 0;
        int offPeakCounter = 0;
        for (Journey j : journeysList) {
            if (j.getTimeBand() == CityRideDataset.TimeBand.PEAK) {
                peakCounter++;
            }
            else {
                offPeakCounter++;
            }
        }
        return new int[]{peakCounter, offPeakCounter};
    }

    public Map<Integer, Integer> getZoneCounts() {
        Map<Integer, Integer> zoneCountsMap = new HashMap<>();
        for (Zone zone : zonesArr) {
            zoneCountsMap.put(zone.getZoneId(), zone.getCount());
        }
        return zoneCountsMap;
    }

    // fare calculations
    public BigDecimal calculateDiscountFare(int startZoneId, int endZoneId, CityRideDataset.TimeBand timeBand, CityRideDataset.PassengerType passengerType) {
        BigDecimal baseFare = CityRideDataset.getBaseFare(startZoneId, endZoneId, timeBand);
        BigDecimal discount = CityRideDataset.DISCOUNT_RATE.get(passengerType);
        BigDecimal discountOfBaseFare = baseFare.multiply(discount);
        return baseFare.subtract(discountOfBaseFare);
    }

    public BigDecimal calculateChargedFare(BigDecimal discountedFare, CityRideDataset.PassengerType passengerType) {
        BigDecimal chargedFare;

        SummaryForPassengerType summary = getSummaryObjForPassengerType(passengerType);
        BigDecimal totalDiscountedFare = summary.getTotalDiscountedFare();
        BigDecimal newDiscountedTotal = discountedFare.add(summary.getTotalDiscountedFare());
        BigDecimal dailyCap = CityRideDataset.DAILY_CAP.get(passengerType);

        boolean dailyCapReached = summary.getDailyCapReached();

        if (!dailyCapReached) {
            if (newDiscountedTotal.compareTo(dailyCap) > 0) {
                chargedFare = dailyCap.subtract(totalDiscountedFare);
            } else {
                chargedFare = discountedFare;
            }
        }
        else {
            chargedFare = new BigDecimal("0.00");
        }
        return chargedFare;
    }

    public boolean calculateDailyCapReached(BigDecimal discountedFare, CityRideDataset.PassengerType passengerType) {
        SummaryForPassengerType summary = getSummaryObjForPassengerType(passengerType);
        BigDecimal newDailyTotal = discountedFare.add(summary.getTotalChargedFare());
        BigDecimal dailyCap = CityRideDataset.DAILY_CAP.get(passengerType);

        return newDailyTotal.compareTo(dailyCap) >= 0;
    }

    // removing journey
    public Journey findJourneyById(int journeyId) {
        for(Journey journey : journeysList) {
            if(journey.getJourneyId() == journeyId) {
                return journey;
            }
        }
        return null;
    }

    public Journey removeJourneyById(int journeyId) {
        Journey removedJourney = null;
        for(int i = 0; i < journeysList.size(); i++) {
            if(journeysList.get(i).getJourneyId() == journeyId) {
                removedJourney = journeysList.remove(i);
                break;
            }
        }
        return removedJourney;
    }

    public void recalculateSummariesForRemovedJourney(Journey removedJourney) {
        CityRideDataset.PassengerType passengerType = removedJourney.getPassengerType();
        SummaryForPassengerType summary = getSummaryObjForPassengerType(passengerType);
        summary.subtractJourneyFromSum(removedJourney);
        removedJourney.getStartZone().decrementCount();
        removedJourney.getEndZone().decrementCount();
    }

    // resetting the day
    private void resetZoneCounts() {
        for (Zone z : zonesArr) {
            z.setCount(0);
        }
    }
    public void resetDay() {
        journeysList.clear();
        adultSummary.clearAllTotals();
        studentSummary.clearAllTotals();
        childSummary.clearAllTotals();
        seniorCitizenSummary.clearAllTotals();
        resetZoneCounts();
    }
}
```

JourneyTrackerApp

```java
import java.util.Scanner;

public class JourneyTrackerApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JourneyTracker journeyTracker = new JourneyTracker();
        MainMenu mainMenu = new MainMenu();
        InputValidator inputValidator = new InputValidator();

        int userInput;

        mainMenu.printWelcome();

        do {
            mainMenu.printMainMenu();
            userInput = inputValidator.readIntInRange(0, 5);

            if (userInput == 1) {
                mainMenu.actionAddJourney(inputValidator, journeyTracker);
            }
            else if (userInput == 2) {
                mainMenu.actionListJourneys(journeyTracker, inputValidator);
            }
            else if (userInput == 3) {
                mainMenu.actionViewSummary(journeyTracker, inputValidator);
            }
            else if (userInput == 4) {
                mainMenu.actionRemoveJourney(journeyTracker, inputValidator);
            }
            else if (userInput == 5) {
                mainMenu.actionResetDay(journeyTracker, inputValidator);
            }
        }
        while (userInput != 0);
        scanner.close();
    }
}

```

MainMenu

```java
import java.math.BigDecimal;
import java.util.Map;

public class MainMenu {
    Formatter formatter = new Formatter();

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

    // asking for certain input
    public CityRideDataset.PassengerType askForPassengerType(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("What is the passenger type?");
        journeyTracker.listAllPassengerTypes();
        int passengerTypeId = inputValidator.readIntInRange(1, 4);

        return CityRideDataset.PassengerType.values()[passengerTypeId - 1];
    }

    public Zone askForZone(String zoneDescription, JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("What is the ID of the " + zoneDescription + " zone?");
        journeyTracker.listAllZones();
        int zoneId = inputValidator.readIntInRange(CityRideDataset.MIN_ZONE, CityRideDataset.MAX_ZONE);
        return journeyTracker.findZoneById(zoneId);
    }

    public CityRideDataset.TimeBand askForTimeBand(InputValidator inputValidator) {
        boolean isPeak = inputValidator.readYesNo();
        CityRideDataset.TimeBand timeBand;
        if (isPeak) {
            timeBand = CityRideDataset.TimeBand.PEAK;
        }
        else {
            timeBand = CityRideDataset.TimeBand.OFF_PEAK;
        }
        return timeBand;
    }

    // menu actions
    public void actionAddJourney(InputValidator inputValidator, JourneyTracker journeyTracker) {
        System.out.println("+++++++ Adding a journey +++++++");
        System.out.println("Was the journey on peak?");
        CityRideDataset.TimeBand timeBand = this.askForTimeBand(inputValidator);
        Zone startZone = this.askForZone("start", journeyTracker, inputValidator);
        Zone endZone = this.askForZone("end", journeyTracker, inputValidator);
        CityRideDataset.PassengerType passengerType = this.askForPassengerType(journeyTracker, inputValidator);

        // calculations
        BigDecimal baseFare = CityRideDataset.getBaseFare(startZone.getZoneId(), endZone.getZoneId(), timeBand);
        BigDecimal discountedFare = journeyTracker.calculateDiscountFare(startZone.getZoneId(), endZone.getZoneId(), timeBand, passengerType);
        BigDecimal chargedFare = journeyTracker.calculateChargedFare(discountedFare, passengerType);
        boolean dailyCapReached = journeyTracker.calculateDailyCapReached(discountedFare, passengerType);

        // adding journey
        Journey newJourney = new Journey(journeyTracker.getJourneyIdCounter(), timeBand, startZone, endZone, passengerType, baseFare, discountedFare, chargedFare, dailyCapReached);
        journeyTracker.addJourney(newJourney);
        journeyTracker.setJourneyIdCounter(journeyTracker.getJourneyIdCounter() + 1);
    }

    // listing journeys
    // private functions for asking for filters
    private void printListingFilters() {
        System.out.println("\nChoose filter:");
        System.out.println("1. Passenger type");
        System.out.println("2. Time band");
        System.out.println("3. Start zone");
        System.out.println("4. End zone");
        System.out.println("5. Time (hours)");
    }

    private void askForFilterByTimeBand(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("Do you wish to see journeys on Peak time?");
        System.out.println("'Yes' for Peak, 'No' for Off-peak");
        CityRideDataset.TimeBand timeBand = this.askForTimeBand(inputValidator);
        journeyTracker.listJourneysByTimeBand(timeBand);
    }

    private void askForFilterByTime(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("-- Sorting by the time period of when the journey was created --");
        System.out.println("What is the start hour?");
        int startHour = inputValidator.readIntInRange(1, 24);
        System.out.println("What is the end hour?");
        int endHour = inputValidator.readIntInRange(1, 24);
        journeyTracker.listJourneysByHours(startHour, endHour);
    }

    public void actionListJourneys(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("Apply listing filters?");
        boolean applyFilters = inputValidator.readYesNo();
        if (applyFilters) {
            printListingFilters();
            int choice = inputValidator.readIntInRange(1, 5);
            if (choice == 1) {
                CityRideDataset.PassengerType passengerType = askForPassengerType(journeyTracker, inputValidator);
                journeyTracker.listJourneysByPassengerType(passengerType);
            }
            else if (choice == 2) {
                askForFilterByTimeBand(journeyTracker, inputValidator);
            }
            else if (choice == 3) {
                Zone startZone = this.askForZone("start", journeyTracker, inputValidator);
                journeyTracker.listJourneysByZone(startZone, true);
            }
            else if (choice == 4) {
                Zone endZone = this.askForZone("end", journeyTracker, inputValidator);
                journeyTracker.listJourneysByZone(endZone, false);
            }
            else if (choice == 5) {
                askForFilterByTime(journeyTracker, inputValidator);
            }
        }
        else {
            journeyTracker.listAllJourneys();
        }
    }

    // viewing summary
    public void actionViewSummary(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("What type of summary to view?");
        System.out.println("1. Daily summaries");
        System.out.println("2. Totals by passenger type");
        System.out.println("3. Category counts");

        int choice = inputValidator.readIntInRange(1, 3);
        if (choice == 1) {
            dailySummaries(journeyTracker);
        }
        else if (choice == 2) {
            totalsByPassengerType(journeyTracker, inputValidator);
        }
        else if (choice == 3) {
            categoryCounts(journeyTracker);
        }
    }

    // private functions to view certain summaries
    private void dailySummaries(JourneyTracker journeyTracker) {
        System.out.println("\n------- Daily Summary ------");
        System.out.println("Total number of journeys: " + journeyTracker.getTotalJourneysNum());
        System.out.println("Total costs charged: £" + journeyTracker.getTotalCostsCharged());
        System.out.println("Average cost per journey: £" + journeyTracker.getAverageCostPerJourney());

        System.out.println("Most expensive journey: ");
        Journey mostExpensiveJourney = journeyTracker.getMostExpensiveJourney();
        if (mostExpensiveJourney == null) {
            System.out.println("Most expensive journey not found.");
        }
        else {
            mostExpensiveJourney.printJourneyDetails();
        }
        System.out.println("------------------------------");
    }

    private void totalsByPassengerType(JourneyTracker journeyTracker, InputValidator inputValidator) {
        CityRideDataset.PassengerType passengerType = this.askForPassengerType(journeyTracker, inputValidator);
        String passengerTypeName = formatter.getNameOfPassengerType(passengerType);
        System.out.println("---- Daily Summary for " + passengerTypeName + " journeys ----");
        SummaryForPassengerType summary = journeyTracker.getSummaryObjForPassengerType(passengerType);
        summary.printSummary();
    }

    private void printZonesCount(JourneyTracker journeyTracker) {
        Map<Integer, Integer> zonesCountMap = journeyTracker.getZoneCounts();
        System.out.println("------ Zone counts ------");
        // GeekForGeeks (2025)
        for (Map.Entry<Integer, Integer> zone : zonesCountMap.entrySet())
            System.out.println("Zone " + zone.getKey() +
                    ": " + zone.getValue() + " counts.");
        System.out.println("-------------------------");
    }

    private void printPeakVsOffPeakCounts(JourneyTracker journeyTracker) {
        int[] peakVsOffPeakArray = journeyTracker.getPeakVsOffPeakCount();
        System.out.println("Peak counts: " + peakVsOffPeakArray[0]);
        System.out.println("Off-peak counts: " + peakVsOffPeakArray[1]);
    }

    private void categoryCounts(JourneyTracker journeyTracker) {
        System.out.println("----- Category counts -----");
        printPeakVsOffPeakCounts(journeyTracker);
        printZonesCount(journeyTracker);
    }

    // remove journey
    private void actionRemoveJourneyConfirmed(Journey foundJourney, InputValidator inputValidator, JourneyTracker journeyTracker) {
        System.out.println("Found journey:");
        foundJourney.printJourneyDetails();
        System.out.println("\nAre you sure you want to delete this journey?");
        boolean isConfirmed = inputValidator.readYesNo();

        if (isConfirmed) {
            Journey removedJourney = journeyTracker.removeJourneyById(foundJourney.getJourneyId());
            if (removedJourney == null) {
                System.out.println("An error happened! The journey was not removed.");
            } else {
                journeyTracker.recalculateSummariesForRemovedJourney(removedJourney);
                System.out.println("The journey with ID " + foundJourney.getJourneyId() + " was successfully removed.");
            }
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    public void actionRemoveJourney(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("What is the Journey's ID you want to remove?");
        int journeyId = inputValidator.readIntInRange(1, 99999); // arbitrary max value since journey ID has no limit
        Journey foundJourney = journeyTracker.findJourneyById(journeyId);
        if (foundJourney == null) {
            System.out.println("Not found! The journey with ID " + journeyId + " does not exist!");
        } else {
            actionRemoveJourneyConfirmed(foundJourney, inputValidator, journeyTracker);
        }
    }

    // reset day
    public void actionResetDay(JourneyTracker journeyTracker, InputValidator inputValidator) {
        System.out.println("Are you sure you want to reset the day?");
        boolean isConfirmed = inputValidator.readYesNo();
        if (isConfirmed) {
            journeyTracker.resetDay();
            System.out.println("The day was successfully reset.");
        }
        else {
            System.out.println("Resetting cancelled.");
        }
    }
}

```

SummaryForPassengerType

```java
import java.math.BigDecimal;

public class SummaryForPassengerType {
    // states
    private BigDecimal totalBaseFare;
    private BigDecimal totalDiscountedFare;
    private BigDecimal totalChargedFare;
    private int totalJourneyNum;
    private boolean dailyCapReached;
    Formatter formatter = new Formatter();

    // constructor
    public SummaryForPassengerType() {
        totalBaseFare = new BigDecimal("0.0");
        totalDiscountedFare = new BigDecimal("0.0");
        totalChargedFare = new BigDecimal("0.0");
        totalJourneyNum = 0;
        dailyCapReached = false;
    }

    // methods
    // getters
    public BigDecimal getTotalChargedFare() {
        return totalChargedFare;
    }
    public BigDecimal getTotalDiscountedFare() {
        return totalDiscountedFare;
    }
    public boolean getDailyCapReached() {
        return dailyCapReached;
    }
    public int getTotalJourneyNum() {
        return totalJourneyNum;
    }

    // adding journey
    public void addJourneyToSum(Journey journey) {
        totalBaseFare = totalBaseFare.add(journey.getBaseFare());
        totalDiscountedFare = totalDiscountedFare.add(journey.getDiscountedFare());
        totalChargedFare = totalChargedFare.add(journey.getChargedFare());
        dailyCapReached = journey.getDailyCapReached();
        totalJourneyNum++;
    }

    // viewing summary
    public void printSummary() {
        System.out.println("Total base fare: £" + formatter.formatBigDecimal2dp(totalBaseFare));
        System.out.println("Total discounted fare: £" + formatter.formatBigDecimal2dp(totalDiscountedFare));
        System.out.println("Total charged fare: £" + formatter.formatBigDecimal2dp(totalChargedFare));
        System.out.println("Total number of journeys: " + totalJourneyNum);
        System.out.println("Daily cap reached: " + dailyCapReached);
    }

    // removing journey
    private boolean calculateDailyCapReached(CityRideDataset.PassengerType passengerType) {
        BigDecimal dailyCap = CityRideDataset.DAILY_CAP.get(passengerType);
        return totalDiscountedFare.compareTo(dailyCap) >= 0;
    }

    public void subtractJourneyFromSum(Journey journey) {
        totalBaseFare = totalBaseFare.subtract(journey.getBaseFare());
        totalDiscountedFare = totalDiscountedFare.subtract(journey.getDiscountedFare());
        totalChargedFare = totalChargedFare.subtract(journey.getChargedFare());
        dailyCapReached = calculateDailyCapReached(journey.getPassengerType());
        totalJourneyNum--;
    }

    // resetting day
    public void clearAllTotals() {
        totalBaseFare = new BigDecimal("0.0");
        totalDiscountedFare = new BigDecimal("0.0");
        totalChargedFare = new BigDecimal("0.0");
        totalJourneyNum = 0;
        dailyCapReached = false;
    }
}

```

Zone

```java
public class Zone {
    // states
    private int zoneId;
    private String zoneName;
    private int count;

    // constructors
    public Zone() {
        this.zoneId = 0;
        this.zoneName = "Unknown";
        this.count = 0;
    }
    public Zone(int zoneId, String zoneName) {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.count = 0;
    }

    // getters
    public int getZoneId() {
        return zoneId;
    }
    public String getZoneName() {
        return zoneName;
    }
    public int getCount() {
        return count;
    }

    // setters
    public void setCount(int count) {
        this.count = count;
    }

    // other methods
    public void incrementCount() {
        this.count++;
    }
    public void decrementCount() {
        this.count--;
    }
    public void printZoneDetails() {
        System.out.println(this.zoneId + ". " + this.zoneName);
    }
}

```





------------------------------------------------------------------------------------------------------------------------------

### Updated Gantt Chart

------------------------------------------------------------------------------------------------------------------------------

![](/Users/assemyeleussiz/IdeaProjects/T0479574_IY4113_java/Gantt%20chart%20history/milestone4_ganttchart.png)

------------------------------------------------------------------------------------------------------------------------------

### Diary Entries

------------------------------------------------------------------------------------------------------------------------------

### 12/02/2026 - Diary Entry 10 - Incorporating CityRide Dataset into the Program

Today I looked through the CityRide Dataset that I will need to use for my program. At first the dataset’s code looked scary, as it used a lot of the Java features that were new to me, such as Hashmap, BigDecimal, keywords ‘final’ and ‘static’, enums and others. 

BigDecimal was especially scary, as looking at the dataset made me realise that I will have to rewrite my program code to store money as BigDecimal instead of double, as i did before. I had to look up how to instantiate BigDecimal, format it, perform arithmetic operations with it and convert other data types like integer or string to BigDecimal. I was building the logic of calculating the charged fare and applying the daily cap when adding a new journey and my logic was flawed. But because BigDecimal uses methods to perform arithmetic calculations (for example .add() instead of ‘+’) it was harder for me to notice the exact part of my calculations that was causing the problem since methods .add(), subtract(), divide() were confusing to read and made the code’s logic harder to follow.

As I was building the program, I also realised that my program needed a formatter to format BigDecimal to 2 decimal points. The provided dataset has a method called ‘money’ that takes String as an argument and returns formatted BigDecimal. Since I cannot modify the dataset, I used the method as an example and built my own formatter method. I did not know where to place it, I was choosing between classes MainMenu and InputValidator, but neither of them seemed like a good enough fit. Then, I noticed that the dataset had time bands and passenger types as enums, but did not have a human-friendly readable version of the names of the enums. That led to me creating functions that would take the enums and return the prettily written name of the values (for example, instead of “SENIOR_CITIZEN” it would be “Senior Citizen”). The functions could also be considered a type of formatting, but for the enums. Having already 3 methods that fall under the category of formatting, I made the decision to create a separate “Formatter” class. I also placed my date time formatter to format the journeys’ local date time into readable text into that class. 

Ideally, I think the formatter class should be similar to the provided dataset - it does not need to be instantiated, needs to be accessible from any part of the program, and does not need to change. But for the sake of this assignment, given the strict deadlines, I decided not to concern myself with this just yet, as it would require more time to do  research and learn how to properly implement that.

I also took another look at my previously written getters and setters that were unused and asked my teacher if I should leave them in my code in case they will be needed in the future (even if the current program specification does not require them), or get rid of them. My teacher suggested I comment them out, which I did at first, but as my codebase kept growing the huge multi-line code comments started to confuse me and were distracting, so, in the end, I decided to only keep the ones that will be actually used in my program so as to not clutter my code.

### 14/02/2026 - Diary Entry 11 - Continuing to code the program

At multiple points in my program I kept creating functions that felt like they were doing the same exact logic, but needed to return 2 different values. For example, in my original class diagram I planned to have a single function to calculate the charged fare and if the daily cap was reached upon adding a new journey. However, as I started to implement that, I realised Java does not have a simple way of returning BigDecimal (charged fare) and boolean (if the daily cap was reached) together. As I was trying to look into ways of returning them together, I remembered the NTIC Good Guide to Programming saying how “if you need the word ‘and’ to describe what your function is doing, it does not follow the single responsibility principle”. Thus, I created 2 separate functions to calculate and return the two values.

As I kept rereading the assignment sheet, I noticed that in one of the program specifications it said that the discounted fare for each journey should be stored in memory “if applicable”. Unfortunately, by the time I noticed that, I already implemented the majority of the logic of my program, especially the parts carrying out printing and calculations, and having almost ran out of time I decided to leave it as it currently is (discounted fare always gets stored).

In the function removeJourneyById of class JourneyTracker i loop through the list to find the journey to delete and subsequently remove it. In this one case, I decided to use break, even if my tutor advised us to be wary of using break, because in my case I want the loop to stop since there would be no point in continuing looping when the journey that we were looking for was already found and deleted. In this case, I believe I used break appropriately.

### 15/02/2026 - Diary Entry 12 - Finishing the program code

When asking the user to enter the ID of the journey to remove, i put an arbitrarily big value since journey IDs have no set limits, but this arbitrary number also gets printed to the console when prompting the user due to how my InputValidator for reading int values function works. I could have modified the method to accept a boolean flag that will control whether the range of integer gets printed or not, but I did not have enough time to focus on that as I was rushing to complete the core features.

In summary for each passenger class, when creating the method clearAllTotals() for resetting the day I noticed that it was executing the same exact statements as the constructor, so at first I thought I would just call the clearAllTotals() method inside of the constructor, but after doing some research, apparently the call to the method of the instance inside of the constructor can happen before the instance is fully initialised, which makes it dangerous. Thus, I was left with duplicate code in the clearAllTotals() method and the constructor.

The assignment says to allow filtering by the date, but the program’s run only accounts for a session of one single day (since we have running daily totals for each passenger type and the ‘reset day’ action that clears everything). Rewriting my entire program to also account for multiple days would require a lot of time, and would also contradict to the assignment sheet asking for “a program that will help the user keep track of their public transport journeys for a **single day**”. This is why I decided to change filtering by date to filtering by hours of the day.

When creating category counts by zone, at first I thought of looping through the list of journeys to count all the zone instances, but then I realised that it would be better to have each zone have a counter that increments whenever a new journey that mentions it (whether as the start zone, or the end zone) is created. This approach does not waste computing power to count all the zones, and instead simply fetches the counts from memory for each zone.



------------------------------------------------------------------------------------------------------------------------------

### Code References

Bro Code. (2024, December 17). *Learn DATES & TIMES with Java in 8 minutes! 📆.* [YouTube Video]. Retrieved from: https://youtu.be/F2bZ1fkAQx0?si=YqrQhLJXz9YmA0rK [Accessed 8 February 2026].



GeeksforGeeks. (2025). *How to Iterate Any Map in Java?.* [online] Retrieved from: https://www.geeksforgeeks.org/java/iterate-map-java [Accessed 15 February 2026].



Kvasnovsky, O. (2025). *Java 101 — Lesson 14: Input Validation & Robustness.* Retrieved from: https://ondrej-kvasnovsky.medium.com/java-101-lesson-14-input-validation-robustness-908331a56f3d [Accessed 8 February 2026].
