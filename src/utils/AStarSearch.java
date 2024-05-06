package utils;
import java.util.*;

public class AStarSearch extends WordLadder {
    public AStarSearch(String filePath) {
        super(filePath);
    }

    @Override
    public SearchResult search(String startWord, String endWord) {
        long startTime = System.nanoTime();
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost + getHeuristic(n.word, endWord)));
        Map<String, Integer> costSoFar = new HashMap<>();
        frontier.add(new Node(startWord, null, 0));
        costSoFar.put(startWord, 0);

        List<String> firstFoundPath = new ArrayList<>();
        boolean found = false;
        int wordCheckCount = 0;

        while (!frontier.isEmpty() && !found) {
            Node current = frontier.poll();
            wordCheckCount++;

            if (current.word.equals(endWord)) {
                firstFoundPath = reconstructPath(current);
                found = true;
                break;  // Exit the loop after finding the goal
            }

            for (String next : getNextWords(current.word)) {
                int newCost = current.cost + 1;
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    frontier.add(new Node(next, current, newCost));
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