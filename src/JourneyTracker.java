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