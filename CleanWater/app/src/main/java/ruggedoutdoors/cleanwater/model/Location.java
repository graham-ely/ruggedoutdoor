package ruggedoutdoors.cleanwater.model;

/**
 * Created by Austin Dunn on 3/13/2017.
 */

public class Location {
    private double latitude;
    private double longitude;

    public Location(double lat, double lon) {
        latitude = lat;
        longitude = lon;
    }

    public double getLatitude() { return latitude; }
    public double get_longitude() { return longitude; }
}
