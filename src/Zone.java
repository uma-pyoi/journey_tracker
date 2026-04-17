public class Zone {
    private int zoneId;
    private String zoneName;
    private int count;

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
    public void printZoneDetails() {
        System.out.println(this.zoneId + ". " + this.zoneName);
    }
}
