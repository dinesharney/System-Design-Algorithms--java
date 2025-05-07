package main.java;

public class TokenBucket {

    private final int capacity;              // Max tokens bucket can hold
    private final int refillRatePerSecond;   // Tokens added per second
    private double tokens;                   // Current tokens in the bucket
    private long lastRefillTimestamp;        // Last refill time (in nanoseconds)

    /**
     * Initialize the token bucket.
     *
     * @param capacity            Maximum number of tokens
     * @param refillRatePerSecond Tokens added per second
     */
    public TokenBucket(int capacity, int refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.tokens = capacity;
        this.lastRefillTimestamp = System.nanoTime();
    }

    /**
     * Attempts to consume 1 token.
     *
     * @return true if request is allowed, false if rate limited
     */
    public synchronized boolean allowRequest() {
        refill();

        if (tokens >= 1) {
            tokens -= 1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Refill the bucket based on the time elapsed.
     */
    private void refill() {
        long now = System.nanoTime();
        double elapsedSeconds = (now - lastRefillTimestamp) / 1_000_000_000.0;
        double tokensToAdd = elapsedSeconds * refillRatePerSecond;

        if (tokensToAdd > 0) {
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefillTimestamp = now;
        }
    }

    /**
     * Test usage of TokenBucket.
     */
    public static void main(String[] args) throws InterruptedException {
        TokenBucket bucket = new TokenBucket(5, 2); // capacity 5 tokens, 2 tokens/sec

        for (int i = 1; i <= 15; i++) {
            boolean allowed = bucket.allowRequest();
            System.out.printf("Request %02d: %s%n", i, allowed ? "ACCEPTED" : "REJECTED");

            Thread.sleep(300); // 3.3 requests/sec > 2 token/sec → will throttle
        }
    }
}
