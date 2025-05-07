package main.java;

import java.util.*;

/**
 * Generic implementation of Consistent Hashing with virtual nodes.
 * This helps in distributing keys evenly and allows minimal reshuffling
 * when nodes are added or removed.
 *
 * @param <T> The type of the node (e.g., server or cache name)
 */
public class ConsistentHash<T> {

    // Hash function to use (can be customized)
    private final HashFunction hashFunction;

    // Number of virtual nodes per physical node
    private final int numberOfReplicas;

    // Sorted map representing the hash ring (hash -> node)
    private final SortedMap<Integer, T> circle = new TreeMap<>();

    /**
     * Constructor to create a consistent hash ring.
     *
     * @param hashFunction      Hash function to generate consistent keys
     * @param numberOfReplicas  Number of virtual nodes per real node
     * @param nodes             Initial collection of real nodes
     */
    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        // Add all initial nodes to the hash ring
        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * Adds a physical node along with its virtual nodes to the hash ring.
     */
    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            // Create a unique identifier for each virtual node
            int hash = hashFunction.hash(node.toString() + i);
            circle.put(hash, node);
        }
    }

    /**
     * Removes a node and its virtual nodes from the hash ring.
     */
    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            int hash = hashFunction.hash(node.toString() + i);
            circle.remove(hash);
        }
    }

    /**
     * Gets the node responsible for the given key.
     *
     * @param key The key to route (e.g., cache key, user ID)
     * @return Node responsible for the key
     */
    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }

        // Compute hash of the key
        int hash = hashFunction.hash(key.toString());

        // Get tail map: all nodes with hash >= key hash
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            // Wrap around if needed
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }

        return circle.get(hash);
    }

    /**
     * Prints all virtual nodes in the hash ring for inspection/debugging.
     */
    public void printCircle() {
        for (Map.Entry<Integer, T> entry : circle.entrySet()) {
            System.out.println("Hash: " + entry.getKey() + " => Node: " + entry.getValue());
        }
    }

    /**
     * Functional interface to abstract hash functions.
     */
    public interface HashFunction {
        int hash(String key);
    }

    /**
     * A basic hash function using Java's hashCode (can be replaced with MurmurHash or MD5 for better distribution).
     */
    public static class DefaultHashFunction implements HashFunction {
        public int hash(String key) {
            // Mask to ensure a non-negative hash value
            return key.hashCode() & 0x7fffffff;
        }
    }

    /**
     * Sample demo usage of ConsistentHash.
     */
    public static void main(String[] args) {
        // Sample set of servers
        List<String> servers = Arrays.asList("NodeA", "NodeB", "NodeC");

        // Create consistent hash ring with 100 virtual nodes per real node
        ConsistentHash<String> ch = new ConsistentHash<>(new DefaultHashFunction(), 100, servers);

        // Route a few keys
        System.out.println("Key1 => " + ch.get("Key1"));
        System.out.println("Key2 => " + ch.get("Key2"));
        System.out.println("Key3 => " + ch.get("Key3"));

        // Add a new node
        System.out.println("\nAdding NodeD...\n");
        ch.add("NodeD");

        // Re-check routing (minimal keys should change)
        System.out.println("Key1 => " + ch.get("Key1"));
        System.out.println("Key2 => " + ch.get("Key2"));
        System.out.println("Key3 => " + ch.get("Key3"));

        // Visualize the full hash ring
        System.out.println("\nHash Ring Snapshot:");
        ch.printCircle();
    }
}
