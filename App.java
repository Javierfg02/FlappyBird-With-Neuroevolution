package Flappy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), FlapConstants.APP_WIDTH, FlapConstants.APP_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Flap Flap");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] argv) {
        launch(argv);
    }
}
