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
