package utils;
import java.util.*;

public class GreedyBestFirstSearch extends WordLadder {
    public GreedyBestFirstSearch(String filePath) {
        super(filePath);
    }

    @Override
    public SearchResult search(String startWord, String endWord) {
        long startTime = System.nanoTime();
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(n -> getHeuristic(n.word, endWord)));
        Set<String> visited = new HashSet<>();
        frontier.add(new Node(startWord, null, 0));

        List<String> firstFoundPath = new ArrayList<>();
        boolean found = false;
        int wordCheckCount = 0;

        while (!frontier.isEmpty() && !found) {  // Stop loop once goal is found
            Node current = frontier.poll();
            wordCheckCount++;

            if (current.word.equals(endWord)) {
                firstFoundPath = reconstructPath(current);
                found = true;
                break;  // Exit the loop after finding the goal
            }

            if (!visited.contains(current.word)) {
                visited.add(current.word);
                for (String next : getNextWords(current.word)) {
                    if (!visited.contains(next)) {
                        frontier.add(new Node(next, current, 0));
                    }
                }
            }
        }
        long elapsedTime = System.nanoTime() - startTime;
        return new SearchResult(firstFoundPath, wordCheckCount, elapsedTime);
    }

    private int getHeuristic(String word, String endWord) {
        int mismatch = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != endWord.charAt(i)) {
                mismatch++;
            }
        }
        return mismatch;
    }
}