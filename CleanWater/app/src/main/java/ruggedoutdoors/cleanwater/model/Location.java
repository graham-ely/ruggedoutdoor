package ruggedoutdoors.cleanwater.model;

/**
 * Created by Austin Dunn on 3/13/2017.
 */

public class Location {
    private double latitude;
    private double longitude;

    /**
     * Constructs new Location object
     * @param lat latitude parameter
     * @param lon longitude
     */
    public Location(double lat, double lon) {
        latitude = lat;
        longitude = lon;
    }

    /**
     * gets the latitude
     * @return latitude
     */
    public double getLatitude() { return latitude; }

    /**
     * gets the longitude
     * @return the longitude
     */
    public double get_longitude() { return longitude; }

    public String toString() {
        return String.format("%.4f", latitude) + ", " + String.format("%.4f", longitude);
    }
}
