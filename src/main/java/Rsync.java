package main.java;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;

/**
 * Simulated Rsync-like file sync using rolling + strong checksums.
 */
public class Rsync {

    private static final int BLOCK_SIZE = 1024;  // 1KB block size

    /**
     * Represents a checksum pair: weak (rolling hash) and strong (MD5).
     */
    static class Checksum {
        int weakHash;
        String strongHash;

        public Checksum(int weakHash, String strongHash) {
            this.weakHash = weakHash;
            this.strongHash = strongHash;
        }

        @Override
        public String toString() {
            return "Weak: " + weakHash + ", Strong: " + strongHash;
        }
    }

    /**
     * Computes rolling + strong checksums for each block of a file.
     */
    public static List<Checksum> computeChecksums(File file) throws Exception {
        List<Checksum> checksums = new ArrayList<>();

        try (InputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[BLOCK_SIZE];
            int read;
            while ((read = in.read(buffer)) != -1) {
                int weak = rollingChecksum(buffer, read);
                String strong = strongChecksum(buffer, read);
                checksums.add(new Checksum(weak, strong));
            }
        }

        return checksums;
    }

    /**
     * Rolling checksum (Adler32-like).
     * Fast and good enough for initial match.
     */
    public static int rollingChecksum(byte[] block, int length) {
        int a = 0, b = 0;
        for (int i = 0; i < length; i++) {
            a += block[i] & 0xff;
            b += a;
        }
        return (b << 16) | a;
    }

    /**
     * Strong checksum using MD5.
     * Confirms true block match.
     */
    public static String strongChecksum(byte[] block, int length) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(block, 0, length);
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();

        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    /**
     * Compares blocks between original and modified files and prints changed indexes.
     */
    public static void compare(File original, File modified) throws Exception {
        List<Checksum> originalChecksums = computeChecksums(original);
        List<Checksum> modifiedChecksums = computeChecksums(modified);

        System.out.println("Modified Blocks:");

        for (int i = 0; i < modifiedChecksums.size(); i++) {
            if (i >= originalChecksums.size() ||
                    !modifiedChecksums.get(i).strongHash.equals(originalChecksums.get(i).strongHash)) {
                System.out.println("→ Block " + i + " differs");
            }
        }
    }

    /**
     * Example demo comparing two sample files.
     */
    public static void main(String[] args) throws Exception {
        File original = new File("original.txt");
        File modified = new File("modified.txt");

        System.out.println("Comparing files...");
        compare(original, modified);
    }
}
