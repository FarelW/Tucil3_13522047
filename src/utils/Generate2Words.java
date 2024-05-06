package utils;
import java.util.*;

public class Generate2Words {
    private Map<Integer, List<String>> wordsByLength = new HashMap<>();
    private Random random = new Random();

    public void organizeWords(List<String> lines) {
        for (String word : lines) {
            int length = word.length();
            if (length >= 2 && length <= 20) {
                wordsByLength.putIfAbsent(length, new ArrayList<>());
                wordsByLength.get(length).add(word);
            }
        }
    }
    
    public String[] getRandomWords() {
        return getRandomWordsFromMap(null);
    }
    
    public String[] getRandomWords(int length) {
        return getRandomWordsFromMap(length);
    }
    
    private String[] getRandomWordsFromMap(Integer specifiedLength) {
        List<Integer> validLengths = new ArrayList<>(wordsByLength.keySet());
        
        validLengths.removeIf(length -> wordsByLength.get(length).size() < 2 || length < 3 || length > 15);
        
        if (specifiedLength != null) {
            if (!validLengths.contains(specifiedLength) || specifiedLength < 3 || specifiedLength > 15) {
                return null; 
            }
            validLengths.retainAll(Collections.singleton(specifiedLength));
        }

        if (validLengths.isEmpty()) {
            return null; 
        }

        int selectedLength = validLengths.get(random.nextInt(validLengths.size()));
        List<String> wordsOfSelectedLength = wordsByLength.get(selectedLength);

        Collections.shuffle(wordsOfSelectedLength);
        String startWord = wordsOfSelectedLength.get(0);
        String endWord = wordsOfSelectedLength.get(1); 

        return new String[]{startWord, endWord};
    }
}
