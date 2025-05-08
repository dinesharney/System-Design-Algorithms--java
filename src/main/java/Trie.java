package main.java;

import java.util.HashMap;
import java.util.Map;

/**
 * A node in the Trie structure.
 * Each node contains a map of children and a flag indicating if it's the end of a word.
 */
class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();  // Children nodes for each character
    boolean isEndOfWord = false;  // Marks end of a valid word
}

/**
 * Trie data structure implementation.
 * Supports insert, search, and prefix matching.
 */
public class Trie {

    private final TrieNode root;  // Root node (empty char)

    /**
     * Initializes the Trie.
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * Inserts a word into the Trie.
     * Iteratively adds each character to the Trie, creating nodes as needed.
     *
     * @param word The word to be inserted
     */
    public void insert(String word) {
        TrieNode current = root;

        for (char ch : word.toCharArray()) {
            // Create new node if char is not present
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }

        // Mark the last node as the end of the word
        current.isEndOfWord = true;
    }

    /**
     * Searches for a full word in the Trie.
     *
     * @param word The word to search
     * @return True if the word exists, false otherwise
     */
    public boolean search(String word) {
        TrieNode node = findNode(word);  // Traverse to the final node of the word
        return node != null && node.isEndOfWord;  // Check if it's a valid word
    }

    /**
     * Checks if any word in the Trie starts with the given prefix.
     *
     * @param prefix The prefix to check
     * @return True if at least one word starts with the prefix
     */
    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    /**
     * Helper method to traverse the Trie based on input string (word or prefix).
     *
     * @param s The input string to find
     * @return The final node reached, or null if path breaks
     */
    private TrieNode findNode(String s) {
        TrieNode current = root;

        for (char ch : s.toCharArray()) {
            current = current.children.get(ch);  // Traverse character by character
            if (current == null) {
                return null;  // Char not found → stop
            }
        }

        return current;
    }

    /**
     * Example usage and test cases for the Trie.
     */
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("hello");
        trie.insert("helium");
        trie.insert("heat");

        System.out.println("Search 'hello': " + trie.search("hello"));  // true
        System.out.println("Search 'helix': " + trie.search("helix"));  // false
        System.out.println("StartsWith 'he': " + trie.startsWith("he")); // true
        System.out.println("StartsWith 'ho': " + trie.startsWith("ho")); // false
    }
}
