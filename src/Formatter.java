import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

public class Formatter {
    private String defaultUnknownName = "Unknown";

    public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");

    public BigDecimal formatBigDecimal2dp(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, RoundingMode.HALF_UP);
    }

    // getting names of enums
    public String getNameOfTimeBand(CityRideDataset.TimeBand timeBand) {
        String timeBandName = defaultUnknownName;
        if (timeBand == CityRideDataset.TimeBand.PEAK) {
            timeBandName = "Peak";
        }
        else if (timeBand == CityRideDataset.TimeBand.OFF_PEAK) {
            timeBandName = "Off-peak";
        }
        return timeBandName;
    }

    public String getNameOfPassengerType(CityRideDataset.PassengerType passengerType) {
        String passengerTypeName = defaultUnknownName;
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

    public String getNameOfPaymentOption(CityRideDataset.PaymentOption paymentOption) {
        String paymentOptionName = defaultUnknownName;
        if (paymentOption == CityRideDataset.PaymentOption.VISA_CARD) {
            paymentOptionName = "Visa card";
        }
        else if (paymentOption == CityRideDataset.PaymentOption.MASTER_CARD) {
            paymentOptionName = "Master card";
        }
        else if (paymentOption == CityRideDataset.PaymentOption.RAIL_CARD) {
            paymentOptionName = "Rail card";
        }
        return paymentOptionName;
    }
}
