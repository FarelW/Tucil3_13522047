package utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public abstract class WordLadder {
    protected Set<String> dictionary;
    private Set<Character> availableCharacters;

    protected abstract SearchResult search(String startWord, String endWord);

    public WordLadder(String filePath) {
        this.dictionary = new HashSet<>();
        this.availableCharacters = new HashSet<>();
        loadWords(filePath);
    }

    private void loadWords(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    dictionary.add(line.trim());
                    for (char c : line.trim().toCharArray()) {
                        availableCharacters.add(c);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public boolean isLetterAvailable(char letter) {
        return availableCharacters.contains(letter);
    }

    protected List<String> getNextWords(String word) {
        List<String> nextWords = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char originalChar = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) {
                    chars[i] = c;
                    String nextWord = new String(chars);
                    if (dictionary.contains(nextWord)) {
                        nextWords.add(nextWord);
                    }
                }
            }
            chars[i] = originalChar; // Restore original character
        }
        return nextWords;
    }

    protected List<String> reconstructPath(Node endNode) {
        LinkedList<String> path = new LinkedList<>();
        Node current = endNode;
        while (current != null) {
            path.addFirst(current.word);
            current = current.parent;
        }
        return path;
    }

    protected static class Node {
        String word;
        Node parent;
        int cost;

        Node(String word, Node parent, int cost) {
            this.word = word;
            this.parent = parent;
            this.cost = cost;
        }
    }
}
