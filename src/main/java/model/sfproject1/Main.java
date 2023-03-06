package model.sfproject1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * the Javadocs file is located via file path  /SF-project1/javadoc
 * FUTURE ENHANCEMENT:
 * I'd like to improve this program by not using FXML. I believe FXML limits the UI/UX  and GUI significantly.
 * Instead, I'd use HTML, CSS to create a GUI and manipulate the data in the back-end with Java. Or use JavaScript instead.
 * If I had to use the same technologies, I'd add a vendors section.
 * The Vendor section would provide further information on the parts. EX: who to contact if running low on stock
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, RuntimeException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
