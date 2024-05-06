package resources;
import utils.AStarSearch;
import utils.Generate2Words;
import utils.GreedyBestFirstSearch;
import utils.SearchResult;
import utils.UniformCostSearch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.awt.Desktop;
import java.net.URI;

public class MainController {

    @FXML private HBox startWordBox, endWordBox;
    @FXML private VBox userInputs;
    @FXML private TextFlow resultTextFlow;
    @FXML private VBox ucsResultsBox, greedyResultsBox, aStarResultsBox;
    @FXML private CheckBox ucsCheckBox;
    @FXML private CheckBox gbfsCheckBox;
    @FXML private CheckBox aStarCheckBox;

    private Generate2Words generator = new Generate2Words();
    private Set<String> dictionary = new HashSet<>();
    private String currentWord;

    private boolean showUCSResults = false;
    private boolean showGBFSResults = false;
    private boolean showAStarResults = false;

    private boolean ucsResultCalculated = false;
    private boolean gbfsResultCalculated = false;
    private boolean aStarResultCalculated = false;

    @FXML
    public void initialize() {
        loadDictionary();
        shuffleWords(null);
    }

    private void loadDictionary() {
        String filePath = "src/words.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            generator.organizeWords(lines);
            dictionary.addAll(lines);
        } catch (IOException e) {
            resultTextFlow.getChildren().add(new Text("Failed to read words from file: " + e.getMessage()));
        }
    }

    private void generateRandomWords() {
        String[] randomWords = generator.getRandomWords();
        if (randomWords != null && randomWords.length == 2) {
            populateWordBoxes(startWordBox, randomWords[0]);
            populateWordBoxes(endWordBox, randomWords[1]);
            currentWord = randomWords[0];
            resetInputRows(currentWord.length());
            clearResults();
        } else {
            resultTextFlow.getChildren().clear();
            resultTextFlow.getChildren().add(new Text("Failed to generate valid start and end words. Please try again."));
            clearResults();
        }
    }

    private void populateWordBoxes(HBox wordBox, String word) {
        wordBox.getChildren().clear();
        for (char c : word.toCharArray()) {
            TextField tf = createSingleCharTextField();
            tf.setText(String.valueOf(c));
            tf.setEditable(false);
            wordBox.getChildren().add(tf);
        }
    }

    private TextField createSingleCharTextField() {
        TextField textField = new TextField();
        textField.setMaxWidth(30);
        textField.getStyleClass().addAll("text-field", "user-inputs-text-field");  // Add specific class
        return textField;
    }

    private void resetInputRows(int length) {
        userInputs.getChildren().clear();
        addInputRow(length);
    }

    @FXML
    void shuffleWords(ActionEvent event) {
        generateRandomWords();
        clearResults(); 
        resultTextFlow.getChildren().clear();

        ucsCheckBox.setSelected(false);
        gbfsCheckBox.setSelected(false);
        aStarCheckBox.setSelected(false);
        
        ucsResultCalculated = false;
        gbfsResultCalculated = false;
        aStarResultCalculated = false;
    }

    @FXML
    void checkGuess(ActionEvent event) {
        HBox lastInputRow = (HBox) userInputs.getChildren().get(userInputs.getChildren().size() - 1);
        StringBuilder newWordBuilder = new StringBuilder();
        boolean isComplete = true;

        for (javafx.scene.Node node : lastInputRow.getChildren()) {
            if (node instanceof TextField) {
                String text = ((TextField) node).getText().trim();
                if (text.isEmpty()) {
                    isComplete = false;
                    break;
                }
                newWordBuilder.append(text);
            }
        }

        if (!isComplete) {
            resultTextFlow.getChildren().clear();
            resultTextFlow.getChildren().add(new Text("Please fill all fields before submitting."));
            return;
        }

        String newWord = newWordBuilder.toString();
        processWordGuess(newWord);
    }

    @FXML
    private void handleAlgorithmCheckbox(ActionEvent event) {
        showUCSResults = ucsCheckBox.isSelected();
        showGBFSResults = gbfsCheckBox.isSelected();
        showAStarResults = aStarCheckBox.isSelected();
        updateResultsVisibility();
        provideHint(null); // Call provideHint to update visibility based on checkbox selection
    }

    private void updateResultsVisibility() {
        ucsResultsBox.setVisible(showUCSResults);
        greedyResultsBox.setVisible(showGBFSResults);
        aStarResultsBox.setVisible(showAStarResults);
    }

    @FXML
    void openCustomWordDialog(ActionEvent event) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Custom Words Input");
        dialog.setHeaderText("Enter startword and endword of the same length:");

        // Apply styles to dialog pane
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("dialog-pane");

        // Set the button types.
        ButtonType doneButtonType = new ButtonType("Done", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(doneButtonType, cancelButtonType);

        // Apply style to buttons
        Button doneButton = (Button) dialog.getDialogPane().lookupButton(doneButtonType);
        doneButton.getStyleClass().addAll("dialog-button", "dialog-button-done");
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);
        cancelButton.getStyleClass().addAll("dialog-button", "dialog-button-cancel");

        // Create labels and text fields for the start and end words
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField startWordField = new TextField();
        startWordField.getStyleClass().add("dialog-text-field");
        startWordField.setPromptText("Start word");

        TextField endWordField = new TextField();
        endWordField.getStyleClass().add("dialog-text-field");
        endWordField.setPromptText("End word");

        Label startLabel = new Label("Start word:");
        startLabel.getStyleClass().add("dialog-label");
        Label endLabel = new Label("End word:");
        endLabel.getStyleClass().add("dialog-label");

        grid.add(startLabel, 0, 0);
        grid.add(startWordField, 1, 0);
        grid.add(endLabel, 0, 1);
        grid.add(endWordField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Disable the Done button initially
        doneButton.setDisable(true);

        // Validation for enabling the Done button
        startWordField.textProperty().addListener((obs, oldText, newText) -> {
            doneButton.setDisable(newText.trim().isEmpty() || endWordField.getText().trim().isEmpty() || newText.trim().length() != endWordField.getText().trim().length());
        });

        endWordField.textProperty().addListener((obs, oldText, newText) -> {
            doneButton.setDisable(newText.trim().isEmpty() || startWordField.getText().trim().isEmpty() || newText.trim().length() != startWordField.getText().trim().length());
        });

        // Convert the result to a pair when the done button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == doneButtonType) {
                return new Pair<>(startWordField.getText().trim(), endWordField.getText().trim());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(words -> {
            populateWordBoxes(startWordBox, words.getKey());
            populateWordBoxes(endWordBox, words.getValue());
            currentWord = words.getKey();
            resetInputRows(currentWord.length());
            clearResults();
        });
    }

    private void processWordGuess(String guessedWord) {
        if (!dictionary.contains(guessedWord)) {
            resultTextFlow.getChildren().clear();
            resultTextFlow.getChildren().add(new Text("Invalid word. Try again."));
            return;
        }
    
        if (!isOneLetterChanged(currentWord, guessedWord)) {
            resultTextFlow.getChildren().clear();
            resultTextFlow.getChildren().add(new Text("More than one letter changed. Try again."));
            return;
        }
    
        currentWord = guessedWord;
        if (currentWord.equals(getEndWord())) {
            resultTextFlow.getChildren().clear();
            resultTextFlow.getChildren().add(new Text("Congratulations! You win the game"));
            resetGame();
        } else {
            addInputRow(currentWord.length());
        }
    }

    private String getEndWord() {
        StringBuilder endWordBuilder = new StringBuilder();
        for (javafx.scene.Node node : endWordBox.getChildren()) {
            if (node instanceof TextField) {
                endWordBuilder.append(((TextField) node).getText().trim());
            }
        }
        return endWordBuilder.toString();
    }

    private void resetGame() {
   
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Play again?", ButtonType.YES, ButtonType.NO);
    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
            shuffleWords(null);
        }
    });
}

    private boolean isOneLetterChanged(String oldWord, String newWord) {
        int diffCount = 0;
        for (int i = 0; i < oldWord.length(); i++) {
            if (oldWord.charAt(i) != newWord.charAt(i)) {
                diffCount++;
            }
            if (diffCount > 1) {
                return false;
            }
        }
        return diffCount == 1;
    }

    private void addInputRow(int length) {
        HBox inputRow = new HBox(5); 
        inputRow.setAlignment(Pos.CENTER); 
        
        for (int i = 0; i < length; i++) {
            TextField tf = new TextField();
            tf.setPrefWidth(30); 
            tf.setMaxWidth(TextField.USE_PREF_SIZE); 
            tf.setEditable(true); 
            tf.getStyleClass().add("text-field"); 
    
            tf.setOnKeyTyped(event -> { 
                if (tf.getText().length() > 1) { 
                    tf.setText(tf.getText().substring(0, 1));
                    event.consume();
                }
                if (!tf.getText().isEmpty()) { 
                    int index = inputRow.getChildren().indexOf(tf);
                    if (index < inputRow.getChildren().size() - 1) {
                        TextField nextField = (TextField) inputRow.getChildren().get(index + 1);
                        nextField.requestFocus();
                    } else { 
                        String word = inputRow.getChildren().stream()
                                .map(node -> ((TextField) node).getText().trim())
                                .reduce("", String::concat);
                        System.out.println("Current Word: " + currentWord);
                        System.out.println("New Word: " + word);
                        if (isValidWord(word)) {
                            if (!word.equals(getEndWord()) && isOneLetterChanged(currentWord, word)) {
                                currentWord = word;
                                addInputRow(length);
                                HBox newRow = (HBox) userInputs.getChildren().get(userInputs.getChildren().size() - 1);
                                ((TextField) newRow.getChildren().get(0)).requestFocus();
                            } else {
                                if (word.equals(getEndWord())) {
                                    resultTextFlow.getChildren().clear();
                                    resultTextFlow.getChildren().add(new Text("Congratulations! You've reached the end word!"));
                                    resetGame();
                                }
                            }
                        } else {
                            resultTextFlow.getChildren().clear();
                            resultTextFlow.getChildren().add(new Text("Invalid word: " + word));
                        }
                    }
                }
            });
            
            tf.setOnKeyPressed(event -> {
                if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                    int index = inputRow.getChildren().indexOf(tf);
                    if (index > 0) {
                        TextField previousField = (TextField) inputRow.getChildren().get(index - 1);
                        previousField.requestFocus();
                        previousField.clear();
                    }
                }
            });
            
            inputRow.getChildren().add(tf);
        }
        
        userInputs.getChildren().add(inputRow); 
    }

    private boolean isValidWord(String word) {
        return dictionary.contains(word);
    }

    @FXML
    void provideHint(ActionEvent event) {
        if ((showUCSResults && !ucsResultCalculated) || (showGBFSResults && !gbfsResultCalculated) || (showAStarResults && !aStarResultCalculated)) {
            if (currentWord != null && !getEndWord().isEmpty()) {
                if (showUCSResults && !ucsResultCalculated) {
                    SearchResult ucsResult = new UniformCostSearch("src/words.txt").search(currentWord, getEndWord());
                    displayResult(ucsResultsBox, ucsResult);
                    ucsResultCalculated = true;
                }

                if (showGBFSResults && !gbfsResultCalculated) {
                    SearchResult greedyBFSResult = new GreedyBestFirstSearch("src/words.txt").search(currentWord, getEndWord());
                    displayResult(greedyResultsBox, greedyBFSResult);
                    gbfsResultCalculated = true;
                }

                if (showAStarResults && !aStarResultCalculated) {
                    SearchResult aStarResult = new AStarSearch("src/words.txt").search(currentWord, getEndWord());
                    displayResult(aStarResultsBox, aStarResult);
                    aStarResultCalculated = true;
                }

                ucsResultsBox.setVisible(showUCSResults);
                greedyResultsBox.setVisible(showGBFSResults);
                aStarResultsBox.setVisible(showAStarResults);
            } else {
                resultTextFlow.getChildren().clear();
                resultTextFlow.getChildren().add(new Text("Please shuffle words before requesting hints."));
            }
        }
    }

    private void displayResult(VBox resultsBox, SearchResult searchResult) {
        resultsBox.getChildren().clear();
    
        // Use the mapping function to get the formatted title based on the VBox ID
        String titleLabelText = getFormattedTitle(resultsBox.getId());
        Label titleLabel = new Label(titleLabelText);
        titleLabel.getStyleClass().add("label-title");
        resultsBox.getChildren().add(titleLabel);
    
        // Always display the time and words checked, regardless of whether a path was found
        Label timeLabel = new Label(String.format("Time: %.2f ms", searchResult.getElapsedTime() / 1_000_000.0));
        timeLabel.getStyleClass().add("time-label");
        resultsBox.getChildren().add(timeLabel);
    
        Label wordsCheckedLabel = new Label("Words Checked: " + searchResult.getWordCheckCount());
        wordsCheckedLabel.getStyleClass().add("result-label");
        resultsBox.getChildren().add(wordsCheckedLabel);
    
        // Check if a path was found and display accordingly
        if (searchResult.getFirstFoundPath().isEmpty()) {
            Label notFoundLabel = new Label("No results found.");
            notFoundLabel.getStyleClass().add("result-label");
            resultsBox.getChildren().add(notFoundLabel);
        } else {
            Label resLabel = new Label("Result Path:");
            resLabel.getStyleClass().add("result-label");
            resultsBox.getChildren().add(resLabel);
    
            searchResult.getFirstFoundPath().forEach(word -> {
                Label resultLabel = new Label(word);
                resultLabel.getStyleClass().add("result-label");
                resultsBox.getChildren().add(resultLabel);
            });
        }
    }

    private String getFormattedTitle(String vboxId) {
        switch (vboxId) {
            case "ucsResultsBox":
                return "UCS Results:";
            case "greedyResultsBox":
                return "Greedy BFS Results:";
            case "aStarResultsBox":
                return "A* Results:";
            default:
                return "Results:"; // Default case to handle unexpected IDs
        }
    }

    private void clearResults() {
        ucsResultsBox.getChildren().clear();
        greedyResultsBox.getChildren().clear();
        aStarResultsBox.getChildren().clear();
    
        ucsResultsBox.setVisible(false);
        greedyResultsBox.setVisible(false);
        aStarResultsBox.setVisible(false);
    }

    public void openWebLink(ActionEvent event) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/FarelW"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
