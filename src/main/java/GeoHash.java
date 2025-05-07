package main.java;

import java.util.*;

// Main class containing geohash methods
public class GeoHash {

    // Base32 character set for geohashing
    private static final String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";

    // Reverse mapping of characters to their 5-bit values
    private static final Map<Character, Integer> BASE32_MAP = new HashMap<>();

    // Populate the reverse map on class load
    static {
        for (int i = 0; i < BASE32.length(); i++) {
            BASE32_MAP.put(BASE32.charAt(i), i);
        }
    }

    /**
     * Encode a given latitude and longitude into a geohash string.
     *
     * @param latitude  Latitude coordinate
     * @param longitude Longitude coordinate
     * @param precision Number of geohash characters to generate
     * @return Geohash string
     */
    public static String encode(double latitude, double longitude, int precision) {
        StringBuilder geohash = new StringBuilder();
        boolean isEven = true; // Toggle between longitude and latitude
        int bit = 0, ch = 0;

        // Latitude and longitude ranges
        double[] latRange = {-90.0, 90.0};
        double[] lonRange = {-180.0, 180.0};

        while (geohash.length() < precision) {
            double mid;

            // Alternate between longitude and latitude
            if (isEven) {
                mid = (lonRange[0] + lonRange[1]) / 2;
                if (longitude >= mid) {
                    ch |= 1 << (4 - bit); // Set the bit
                    lonRange[0] = mid;    // Shift lower bound
                } else {
                    lonRange[1] = mid;    // Shift upper bound
                }
            } else {
                mid = (latRange[0] + latRange[1]) / 2;
                if (latitude >= mid) {
                    ch |= 1 << (4 - bit);
                    latRange[0] = mid;
                } else {
                    latRange[1] = mid;
                }
            }

            isEven = !isEven;

            // Every 5 bits, convert to a Base32 char
            if (++bit == 5) {
                geohash.append(BASE32.charAt(ch));
                bit = 0;
                ch = 0;
            }
        }

        return geohash.toString();
    }

    /**
     * Decode a geohash string to its center point (latitude, longitude).
     *
     * @param geohash The geohash string
     * @return Array of two doubles: [latitude, longitude]
     */
    public static double[] decode(String geohash) {
        boolean isEven = true;
        double[] latRange = {-90.0, 90.0};
        double[] lonRange = {-180.0, 180.0};

        for (int i = 0; i < geohash.length(); i++) {
            int currentBits = BASE32_MAP.get(geohash.charAt(i));

            // Decode each of the 5 bits
            for (int mask = 16; mask != 0; mask >>= 1) {
                if (isEven) {
                    refineInterval(lonRange, (currentBits & mask) != 0);
                } else {
                    refineInterval(latRange, (currentBits & mask) != 0);
                }
                isEven = !isEven;
            }
        }

        // Return center of the box
        double lat = (latRange[0] + latRange[1]) / 2;
        double lon = (lonRange[0] + lonRange[1]) / 2;
        return new double[]{lat, lon};
    }

    /**
     * Decode a geohash string to its full bounding box.
     *
     * @param geohash The geohash string
     * @return BoundingBox object with min/max lat/lon
     */
    public static BoundingBox decodeBoundingBox(String geohash) {
        boolean isEven = true;
        double[] latRange = {-90.0, 90.0};
        double[] lonRange = {-180.0, 180.0};

        for (int i = 0; i < geohash.length(); i++) {
            int currentBits = BASE32_MAP.get(geohash.charAt(i));
            for (int mask = 16; mask != 0; mask >>= 1) {
                if (isEven) {
                    refineInterval(lonRange, (currentBits & mask) != 0);
                } else {
                    refineInterval(latRange, (currentBits & mask) != 0);
                }
                isEven = !isEven;
            }
        }

        // Construct the bounding box
        return new BoundingBox(latRange[0], latRange[1], lonRange[0], lonRange[1]);
    }

    /**
     * Refines the interval based on the current bit.
     *
     * @param range     The latitude or longitude range
     * @param bitIsOne  Whether the current bit is 1
     */
    private static void refineInterval(double[] range, boolean bitIsOne) {
        double mid = (range[0] + range[1]) / 2;
        if (bitIsOne) {
            range[0] = mid; // Move lower bound up
        } else {
            range[1] = mid; // Move upper bound down
        }
    }

    /**
     * Helper class representing a bounding box.
     */
    public static class BoundingBox {
        public final double minLat;
        public final double maxLat;
        public final double minLon;
        public final double maxLon;

        public BoundingBox(double minLat, double maxLat, double minLon, double maxLon) {
            this.minLat = minLat;
            this.maxLat = maxLat;
            this.minLon = minLon;
            this.maxLon = maxLon;
        }

        @Override
        public String toString() {
            return String.format("Lat: [%.6f, %.6f], Lon: [%.6f, %.6f]",
                    minLat, maxLat, minLon, maxLon);
        }
    }

    /**
     * Main method for testing the geohash functions.
     */
    public static void main(String[] args) {
        double latitude = 37.7749;
        double longitude = -122.4194;
        int precision = 9;

        // Encode to geohash
        String geohash = encode(latitude, longitude, precision);
        System.out.println("Encoded Geohash: " + geohash);

        // Decode to center point
        double[] decodedCenter = decode(geohash);
        System.out.printf("Decoded Center: Lat = %.6f, Lon = %.6f%n", decodedCenter[0], decodedCenter[1]);

        // Decode to bounding box
        BoundingBox box = decodeBoundingBox(geohash);
        System.out.println("Bounding Box: " + box);
    }
}
