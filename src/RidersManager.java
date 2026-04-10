import java.util.ArrayList;
import java.util.List;

public class RidersManager {
    private int riderIdCounter = 1;
    private List<Rider> ridersList = new ArrayList<>();

    public int getRiderIdCounter() {
        return riderIdCounter;
    }
    public void addRider(Rider rider) {
        ridersList.add(rider);
        System.out.println("Rider with ID of " + rider.getId() + " has been added.");
    }
}
