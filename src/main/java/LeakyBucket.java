package main.java;

import java.util.concurrent.atomic.AtomicInteger;

public class LeakyBucket {

    private final int capacity;             // Max bucket size
    private final int leakRatePerSecond;    // Leak rate (tokens/requests per second)
    private double currentFill = 0;         // Current water level
    private long lastLeakTimestamp;         // Last time the bucket leaked

    public LeakyBucket(int capacity, int leakRatePerSecond) {
        this.capacity = capacity;
        this.leakRatePerSecond = leakRatePerSecond;
        this.lastLeakTimestamp = System.nanoTime(); // Initialize time
    }

    /**
     * Tries to allow a request into the bucket.
     * @return true if request is accepted, false if rejected (bucket overflowed)
     */
    public synchronized boolean allowRequest() {
        leak(); // First leak the bucket based on elapsed time

        if (currentFill < capacity) {
            currentFill++; // Accept the request
            return true;
        } else {
            return false; // Bucket overflow → reject request
        }
    }

    /**
     * Calculate how much water to leak based on elapsed time.
     */
    private void leak() {
        long now = System.nanoTime();
        double elapsedSeconds = (now - lastLeakTimestamp) / 1_000_000_000.0;
        double leakedAmount = elapsedSeconds * leakRatePerSecond;

        if (leakedAmount > 0) {
            currentFill = Math.max(0, currentFill - leakedAmount);
            lastLeakTimestamp = now;
        }
    }

    /**
     * Test usage of the leaky bucket.
     */
    public static void main(String[] args) throws InterruptedException {
        LeakyBucket bucket = new LeakyBucket(10, 2); // capacity = 10, leak rate = 2 req/sec

        for (int i = 1; i <= 20; i++) {
            boolean allowed = bucket.allowRequest();
            System.out.printf("Request %02d: %s%n", i, allowed ? "ACCEPTED" : "REJECTED");

            Thread.sleep(200); // simulate 5 requests/sec (faster than leak rate)
        }
    }
}
