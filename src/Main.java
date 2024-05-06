import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/MainScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            // System.out.println(getClass().getResource("resources/style.css"));
            String css = this.getClass().getResource("resources/style.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setTitle("Word Ladder GUI");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
