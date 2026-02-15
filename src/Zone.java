public class Zone {
    // states
    private int zoneId;
    private String zoneName;

    // constructors
    public Zone() {
        this.zoneId = 0;
        this.zoneName = "Unknown";
    }
    public Zone(int zoneId, String zoneName) {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
    }

    // getters
    public int getZoneId() {
        return this.zoneId;
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
        System.out.println(this.zoneId + ". " + this.zoneName);
    }
}
