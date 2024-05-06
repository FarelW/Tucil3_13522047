package utils;

import java.util.List;

public class SearchResult {
    private List<String> firstFoundPath;
    private int wordCheckCount;
    private long elapsedTime;

    public SearchResult(List<String> firstFoundPath, int wordCheckCount, long elapsedTime) {
        this.firstFoundPath = firstFoundPath;
        this.wordCheckCount = wordCheckCount;
        this.elapsedTime = elapsedTime;
    }

    public List<String> getFirstFoundPath() {
        return firstFoundPath;
    }

    public int getWordCheckCount() {
        return wordCheckCount;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
