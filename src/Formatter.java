import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

public class Formatter {
    public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

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
